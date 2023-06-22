package me.pepsi.xeros.runecrafting;

import me.pepsi.xeros.runecrafting.inventory.InventoryChangedListener;
import me.pepsi.xeros.runecrafting.inventory.InventoryObserver;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleBank;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleSceneObject;

import java.awt.*;
import java.util.function.BooleanSupplier;

@ScriptManifest(name = "[P] Xeros ZMI", servers = { "Xeros" }, category = Category.RUNECRAFTING, discord = "", version = "1", description = "ZMI Altar Running, Supports Donor Rank Teleports", author = "Pepsiplaya")
public class Runecrafting extends Script implements SimplePaintable, SimpleMessageListener, InventoryChangedListener {
	private String status = "";

	private final int pureEssenceId = 7936;
	private final int smallPouchId = 5509;
	private final int mediumPouchId = 5510;
	private final int largePouchId = 5512;

	private boolean hasCheckedForPouches = false;
	private boolean hasSmallPouch = false;
	private boolean hasMediumPouch = false;
	private boolean hasLargePouch = false;

	private boolean hasCheckedSmallPouch = false;
	private boolean hasCheckedMediumPouch = false;
	private boolean hasCheckedLargePouch = false;

	private int smallPouchCount = 0;
	private int mediumPouchCount = 0;
	private int largePouchCount = 0;

	private String currentPouch = "";

	private final WorldArea LADDER = new WorldArea(new WorldPoint(2457, 3229), new WorldPoint(2451, 3252));
	private final WorldArea CAVE = new WorldArea(new WorldPoint(3004, 5631), new WorldPoint(3072, 5567));
	private final WorldArea BANK = new WorldArea(new WorldPoint(3026, 5613), new WorldPoint(3007, 5632));
	private final WorldArea BANKLZ = new WorldArea(new WorldPoint(2800, 5050), new WorldPoint(2900, 5100));

	InventoryObserver inventoryObserver;

	private long startTime;

	private long startLevel;

	private long startExp;

	WorldPoint[] PATH = { new WorldPoint(3015, 5618, 0), new WorldPoint(3015, 5612, 0), new WorldPoint(3015, 5606, 0),
			new WorldPoint(3015, 5601, 0), new WorldPoint(3016, 5593, 0), new WorldPoint(3017, 5585, 0),
			new WorldPoint(3020, 5579, 0), new WorldPoint(3026, 5578, 0), new WorldPoint(3032, 5580, 0),
			new WorldPoint(3038, 5582, 0), new WorldPoint(3043, 5579, 0), new WorldPoint(3048, 5578, 0),
			new WorldPoint(3054, 5578, 0) };

	@Override
	public void onPaint(Graphics2D g) {
		final long runTime = System.currentTimeMillis() - startTime;
		final long totalLevels = ctx.skills.getRealLevel(SimpleSkills.Skill.RUNECRAFT) - startLevel;
		final long totalExp = ctx.skills.getExperience(SimpleSkills.Skill.RUNECRAFT) - startExp;

		g.setFont(new Font("Bold", Font.BOLD, 11));

		g.drawString("Status: " + status, 10, 90);

		g.drawString("Runtime: " + ctx.paint.formatTime(runTime), 10, 110);
		g.drawString(String.format("XP Gained: %s (%s/hr)", ctx.paint.formatValue(totalExp),
				(ctx.paint.formatValue(ctx.paint.valuePerHour(
						(int) (ctx.skills.getExperience(SimpleSkills.Skill.RUNECRAFT) - startExp), startTime)))),
				10, 120);
		g.drawString("Levels Gained: " + ctx.paint.formatValue(totalLevels), 10, 130);

		g.drawString("Has Small Pouch: " + hasSmallPouch, 10, 150);
		g.drawString("Small Pouch Count: " + smallPouchCount, 10, 160);

		g.drawString("Has Medium Pouch: " + hasMediumPouch, 10, 180);
		g.drawString("Medium Pouch Count: " + mediumPouchCount, 10, 190);

		g.drawString("Has Large Pouch: " + hasLargePouch, 10, 210);
		g.drawString("Large Pouch Count: " + largePouchCount, 10, 220);
	}

	@Override
	public void onChatMessage(ChatMessageEvent m) {
		if (m.getMessageType() == 0) {
			String message = m.getMessage();

			if (message.contains("Your pouch currently contains")) {
				int amount = Integer.parseInt(message.replaceAll("\\D", "").trim());
				switch (currentPouch) {
				case "small":
					smallPouchCount = amount;
					hasCheckedSmallPouch = true;
					break;
				case "medium":
					mediumPouchCount = amount;
					hasCheckedMediumPouch = true;
					break;
				case "large":
					largePouchCount = amount;
					hasCheckedLargePouch = true;
					break;
				}
			} else if (message.contains("This pouch seems to be empty")) {
				switch (currentPouch) {
				case "small":
					smallPouchCount = 0;
					break;
				case "medium":
					mediumPouchCount = 0;
					break;
				case "large":
					largePouchCount = 0;
					break;
				}
			} else if (message.contains("Your pouch can not hold any more")) {
				switch (currentPouch) {
				case "small":
					smallPouchCount = 7;
					break;
				case "medium":
					mediumPouchCount = 16;
					break;
				case "large":
					largePouchCount = 25;
					break;
				}
			}
		}
	}

	@Override
	public void inventoryItemGained(int id, int count) {
		if (id == pureEssenceId && status.contains("Emptying")) {
			switch (currentPouch) {
			case "small":
				smallPouchCount = Math.max(0, smallPouchCount - count);
				break;
			case "medium":
				mediumPouchCount = Math.max(0, mediumPouchCount - count);
				break;
			case "large":
				largePouchCount = Math.max(0, largePouchCount - count);
				break;
			}
		}
	}

	@Override
	public void inventoryItemLost(int id, int count) {
		if (id == pureEssenceId && status.contains("Filling")) {
			switch (currentPouch) {
			case "small":
				smallPouchCount = Math.min(7, smallPouchCount + count);
				break;
			case "medium":
				mediumPouchCount = Math.min(16, mediumPouchCount + count);
				break;
			case "large":
				largePouchCount = Math.min(25, largePouchCount + count);
				break;
			}
		}
	}

	@Override
	public boolean onExecute() {
		this.startTime = System.currentTimeMillis();
		this.startLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RUNECRAFT);
		this.startExp = ctx.skills.getExperience(SimpleSkills.Skill.RUNECRAFT);

		this.inventoryObserver = new InventoryObserver(ctx);
		inventoryObserver.addListener(this);
		inventoryObserver.start();
		return true;
	}

	@Override
	public void onTerminate() {
		this.inventoryObserver.interrupt();
	}

	@Override
	public void onProcess() {
		if (!ctx.pathing.running()) {
			ctx.pathing.running(true);
		}

		if (atLadder()) {
			goDownLadder();
		}

		if (atBankLZ()) {
			if (shouldBank()) {
				if (!ctx.bank.bankOpen()) {
					openBank();
				}

				if (ctx.bank.bankOpen()) {
					if (hasRunes()) {
						depositItems();
					}

					if (shouldWithdrawPouches()) {
						withdrawPouches();
					}

					if (shouldWithdrawEssence()) {
						withdrawEssence();
					}
				}
				return;
			}

			if (shouldFillUpPouches()) {
				fillUpPouches();
				return;
			}

			status = "Using to Portal";
			enterPortal();
			sleepCondition(() -> !this.atBankLZ(), 2500);
		}

		if (inCave()) {
			if (!hasCheckedForPouches) {
				checkForPouches();
			}

			if (shouldCheckPouchCount()) {
				checkPouchCount();
				return;
			}

			if (atBank()) {
				if (shouldBank()) {
					if (!ctx.bank.bankOpen()) {
						openBank();
					}

					if (ctx.bank.bankOpen()) {
						if (hasRunes()) {
							depositItems();
						}

						if (shouldWithdrawPouches()) {
							withdrawPouches();
						}

						if (shouldWithdrawEssence()) {
							withdrawEssence();
						}
					}
					return;
				}

				if (shouldFillUpPouches()) {
					fillUpPouches();
					return;
				}

				status = "Walking to Altar";
				ctx.pathing.walkToTile(new WorldPoint(3015, 5610));
				sleepCondition(() -> !this.atBank(), 2500);
			}

			if (!atBank() || !atBankLZ()) {
				if (getTotalEssenceCount() > 0) {
					craftRunes();
				} else {
					status = "Walking to bank";
					ctx.pathing.walkPath(PATH, true);
				}
			}
		}

		if (!inCave() && !atLadder() && !atBankLZ()) {
			teleportToZMI();
		}
	}

	private boolean atLadder() {
		return LADDER.containsPoint(ctx.players.getLocal().getLocation());
	}

	private boolean inCave() {
		return CAVE.containsPoint(ctx.players.getLocal().getLocation());
	}

	private boolean atBankLZ() {
		return BANKLZ.containsPoint(ctx.players.getLocal().getLocation());
	}

	private boolean atBank() {
		return BANK.containsPoint(ctx.players.getLocal().getLocation());
	}

	private void checkForPouches() {
		SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
		SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
		SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

		if (smallPouch != null) {
			hasSmallPouch = true;
		}

		if (mediumPouch != null) {
			hasMediumPouch = true;
		}

		if (largePouch != null) {
			hasLargePouch = true;
		}

		if (atBank() && (!hasSmallPouch || !hasMediumPouch || !hasLargePouch)) {
			if (!ctx.bank.bankOpen()) {
				openBank();
			}

			if (ctx.bank.bankOpen()) {
				status = "Checking for pouches";
				smallPouch = ctx.bank.populate().filter(smallPouchId).next();
				mediumPouch = ctx.bank.populate().filter(mediumPouchId).next();
				largePouch = ctx.bank.populate().filter(largePouchId).next();

				if (smallPouch != null) {
					hasSmallPouch = true;
				}

				if (mediumPouch != null) {
					hasMediumPouch = true;
				}

				if (largePouch != null) {
					hasLargePouch = true;
				}

				hasCheckedForPouches = true;
			}
		}

		if (hasSmallPouch && hasMediumPouch && hasLargePouch) {
			hasCheckedForPouches = true;
		}
	}

	private void openBank() {
		SimpleSceneObject<?> bankBooth = ctx.objects.populate().filter("Bank Booth").nextNearest();

		if (bankBooth != null) {
			status = "Opening Bank";
			bankBooth.interact(SimpleObjectActions.FIRST);
			sleepCondition(ctx.bank::bankOpen, 2000);
		}
	}

	private boolean shouldWithdrawPouches() {
		SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
		SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
		SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

		return (hasSmallPouch && smallPouch == null) || (hasMediumPouch && mediumPouch == null)
				|| (hasLargePouch && largePouch == null);
	}

	private boolean shouldWithdrawEssence() {
		return ctx.inventory.getFreeSlots() > 0 || ctx.inventory.populate().filter(pureEssenceId).isEmpty();
	}

	private boolean hasRunes() {
		return !ctx.inventory.populate().omit(smallPouchId, mediumPouchId, largePouchId, pureEssenceId).isEmpty();
	}

	private boolean shouldBank() {
		return hasRunes() || shouldWithdrawPouches() || shouldWithdrawEssence();
	}

	private boolean shouldFillUpPouches() {
		return (hasSmallPouch && smallPouchCount < 7) || (hasMediumPouch && mediumPouchCount < 16)
				|| (hasLargePouch && largePouchCount < 25);
	}

	private boolean shouldCheckPouchCount() {
		return (hasSmallPouch && !hasCheckedSmallPouch && !ctx.inventory.populate().filter(smallPouchId).isEmpty())
				|| (hasMediumPouch && !hasCheckedMediumPouch
						&& !ctx.inventory.populate().filter(mediumPouchId).isEmpty())
				|| (hasLargePouch && !hasCheckedLargePouch && !ctx.inventory.populate().filter(largePouchId).isEmpty());
	}

	private void withdrawPouches() {
		SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
		SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
		SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

		ctx.bank.depositAllExcept(smallPouchId, mediumPouchId, largePouchId);

		if (hasSmallPouch && smallPouch == null) {
			status = "Withdrawing Small Pouch";
			ctx.bank.withdraw(smallPouchId, SimpleBank.Amount.ONE);
			sleepCondition(() -> !ctx.inventory.populate().filter(smallPouchId).isEmpty(), 1500);
		}

		if (hasMediumPouch && mediumPouch == null) {
			status = "Withdrawing Medium Pouch";
			ctx.bank.withdraw(mediumPouchId, SimpleBank.Amount.ONE);
			sleepCondition(() -> !ctx.inventory.populate().filter(mediumPouchId).isEmpty(), 1500);
		}

		if (hasLargePouch && largePouch == null) {
			status = "Withdrawing Large Pouch";
			ctx.bank.withdraw(largePouchId, SimpleBank.Amount.ONE);
			sleepCondition(() -> !ctx.inventory.populate().filter(largePouchId).isEmpty(), 1500);
		}
	}

	private void withdrawEssence() {
		status = "Withdrawing essence";

		if (ctx.bank.populate().filter(pureEssenceId).isEmpty()) {
			ctx.sendLogout();
			ctx.stopScript();
			return;
		}

		int cached = getInventoryEssenceCount();
		ctx.bank.withdraw(pureEssenceId, SimpleBank.Amount.ALL);
		sleepCondition(() -> getInventoryEssenceCount() > cached, 1500);
	}

	private void depositItems() {
		status = "Depositing items";
		ctx.bank.depositInventory();
		sleepCondition(() -> ctx.inventory.populate().isEmpty(), 1500);
	}

	private void fillUpPouches() {
		SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
		SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
		SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

		int essenceCount = getInventoryEssenceCount();

		if (ctx.bank.bankOpen()) {
			status = "Closing bank";
			ctx.bank.closeBank();
			sleepCondition(() -> !ctx.bank.bankOpen(), 1500);
		}

		if (!ctx.bank.bankOpen()) {
			if (essenceCount > 0 && smallPouch != null && smallPouchCount < 7) {
				status = "Filling up Small Pouch";
				this.currentPouch = "small";
				int cached = smallPouchCount;

				smallPouch.interact(74);
				sleepCondition(() -> smallPouchCount > cached, 1500);
				essenceCount = getInventoryEssenceCount();
			}

			if (essenceCount > 0 && mediumPouch != null && mediumPouchCount < 16) {
				status = "Filling up Medium Pouch";
				this.currentPouch = "medium";
				int cached = mediumPouchCount;

				mediumPouch.interact(74);
				sleepCondition(() -> mediumPouchCount > cached, 1500);
				essenceCount = getInventoryEssenceCount();
			}

			if (essenceCount > 0 && largePouch != null && largePouchCount < 25) {
				status = "Filling up Large Pouch";
				this.currentPouch = "large";
				int cached = largePouchCount;

				largePouch.interact(74);
				sleepCondition(() -> largePouchCount > cached, 1500);
			}
		}
	}

	private void checkPouchCount() {
		SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
		SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
		SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

		if (ctx.bank.bankOpen()) {
			status = "Closing bank";
			ctx.bank.closeBank();
			sleepCondition(() -> !ctx.bank.bankOpen(), 1500);
		}

		if (!ctx.bank.bankOpen()) {
			if (smallPouch != null && !hasCheckedSmallPouch) {
				status = "Checking Small Pouch count";
				this.currentPouch = "small";

				smallPouch.interact(539);
				sleepCondition(() -> this.hasCheckedSmallPouch, 1500);
			}

			if (mediumPouch != null && !hasCheckedMediumPouch) {
				status = "Checking Medium Pouch count";
				this.currentPouch = "medium";

				mediumPouch.interact(539);
				sleepCondition(() -> this.hasCheckedMediumPouch, 1500);
			}

			if (largePouch != null && !hasCheckedLargePouch) {
				status = "Checking Large Pouch count";
				this.currentPouch = "large";

				largePouch.interact(539);
				sleepCondition(() -> this.hasCheckedLargePouch, 1500);
			}
		}
	}

	private void goDownLadder() {
		SimpleSceneObject<?> ladder = ctx.objects.populate().filter(29635).nextNearest();

		if (ladder != null) {
			status = "Climbing down Ladder";
			ladder.interact(SimpleObjectActions.FIRST);
			sleepCondition(this::atBank, 2000);
		}
	}
	
	private void enterPortal() {
		SimpleSceneObject<?> portal = ctx.objects.populate().filter(10733).nextNearest();

		if (portal != null) {
			status = "Entering Portal";
			portal.interact(SimpleObjectActions.FIRST);
			sleepCondition(this::inCave, 2000);
		}
	}

	private void craftRunes() {
		SimpleSceneObject<?> altar = ctx.objects.populate().filter(29631).nextNearest();

		if (getInventoryEssenceCount() == 0) {
			SimpleItem smallPouch = ctx.inventory.populate().filter(smallPouchId).next();
			SimpleItem mediumPouch = ctx.inventory.populate().filter(mediumPouchId).next();
			SimpleItem largePouch = ctx.inventory.populate().filter(largePouchId).next();

			if (ctx.inventory.getFreeSlots() > 0 && smallPouch != null && smallPouchCount > 0) {
				status = "Emptying Small Pouch";
				this.currentPouch = "small";
				int cached = smallPouchCount;

				smallPouch.interact(454);
				sleepCondition(() -> smallPouchCount < cached, 1500);
			}

			if (ctx.inventory.getFreeSlots() > 0 && mediumPouch != null && mediumPouchCount > 0) {
				status = "Emptying Medium Pouch";
				this.currentPouch = "medium";
				int cached = mediumPouchCount;

				mediumPouch.interact(454);
				sleepCondition(() -> mediumPouchCount < cached, 1500);
			}

			if (ctx.inventory.getFreeSlots() > 0 && largePouch != null && largePouchCount > 0) {
				status = "Emptying Large Pouch";
				this.currentPouch = "large";
				int cached = largePouchCount;

				largePouch.interact(454);
				sleepCondition(() -> largePouchCount < cached, 1500);
			}
		}

		if (getInventoryEssenceCount() > 0) {
			if (altar != null) {
				status = "Crafting runes on " + altar.getName();
				int cached = getInventoryEssenceCount();
				altar.interact(SimpleObjectActions.CRAFT_RUNE);
				sleepCondition(() -> getInventoryEssenceCount() < cached, 2000);
			} else {
				status = "Walking to Altar";
				ctx.pathing.walkPath(PATH);
			}
		}
	}

	private void teleportToZMI() {
		status = "Teleporting to ZMI Altar";
		ctx.teleporter.teleportStringPath("Skilling", "ZMI Altar");
		sleepCondition(this::atLadder, 2000);
	}

	private int getTotalEssenceCount() {
		return getInventoryEssenceCount() + smallPouchCount + mediumPouchCount + largePouchCount;
	}

	private int getInventoryEssenceCount() {
		return ctx.inventory.populate().filter(pureEssenceId).population();
	}

	private void sleepCondition(BooleanSupplier condition, int ms) {
		long start = System.currentTimeMillis();

		while (!condition.getAsBoolean() && System.currentTimeMillis() < start + ms) {
			ctx.sleep(50);
		}
	}
}
