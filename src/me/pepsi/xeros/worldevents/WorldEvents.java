package me.pepsi.xeros.worldevents;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleBank;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleWidget;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros World Events", category = Category.OTHER, version = "1.0D", description = "Does the world events when it's time", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class WorldEvents extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	public static String status;
	private boolean chiselStone = false;
	public static boolean hesporiStarted = false;
	public static boolean teleported = false;
	public static boolean staff = false;
	public static boolean walked = false;
	private boolean hesporiSpawned = false;
	private boolean avatarSpawned = false;
	private boolean full = false;
	private long startTime;
	private long lastAnimation = -1;
	public static int slot = 1;
	private int phase = 0;
	private Image staffImage;
	final static WorldArea HESPORI_WAITING = new WorldArea(new WorldPoint(3069, 3490),
			new WorldPoint(3085, 3520));
	final static WorldArea HESPORI_INSIDE = new WorldArea(new WorldPoint(3000, 3474),
			new WorldPoint(3069, 3510));
	final static WorldArea HOME = new WorldArea(new WorldPoint(3086, 3468, 0), new WorldPoint(3110, 3505, 0));
	private final WorldPoint start = new WorldPoint(3067, 3499, 0);
	private final WorldPoint vineWalk = new WorldPoint(3063, 3486, 0);
	private final WorldPoint phase5Bug = new WorldPoint(3050, 3495, 0);
	private long lastOverload = -1;
	public static int prayer = 20;
	public static int prayerType;
	private final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);

	@Override
	public boolean onExecute() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 20 + random;
		staff = false;
		hesporiStarted = false;
		startTime = System.currentTimeMillis();
		phase = 0;
		full = false;
		teleported = false;
		walked = false;
		avatarSpawned = false;
		hesporiSpawned = false;
		status = "Waiting On World Event";
		chiselStone = false;
		try {
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onProcess() {
		Staff.antiStaff();
		if (ctx.players.getLocal().isAnimating() && !ctx.pathing.inMotion()) { // need to make a check so it does't do animation count when converting runes.
			lastAnimation = System.currentTimeMillis();
		}
		SimpleWidget hesporiSpawn1 = ctx.widgets.populate().filter(1000004).next();
		SimpleWidget hesporiSpawn2 = ctx.widgets.populate().filter(1000005).next();
		handleEatingFood();
		handleDrinkingRange();
		handleDrinkingPrayer();
		if (hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Hespori spawns in 2m") || hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Hespori spawns in 2m")) {
			hesporiStarted = true;
		}
		/*int[] dialogIds = {4267, 4416, 4282, 4267, 4272, 6221, 6263, 306, 4882, 2459, 23753, 356, 4893, 363, 2469, 968};
		if (ctx.dialogue.dialogueOpen()) {
			int backDialogId = ctx.widgets.getBackDialogId();
			for (int id : dialogIds) {
				if (ctx.dialogue.dialogueOpen() && backDialogId != id) {
					ctx.log("Dialogue Open: " + ctx.widgets.getBackDialogId());
					ctx.log("Unknown dialogue open react quickly!");
					status = "Unknown dialogue open react quickly!";
					ctx.sleep(1000);
					Toolkit.getDefaultToolkit().beep();
					return;
				}
			}
		}*/
		if (avatarSpawned) {
			if (ctx.bank.bankOpen()) {
				ctx.bank.closeBank();
			}
			SimpleNpc avatar1 = getAvatar(ctx, 10531);
			SimpleNpc avatar2 = getAvatar(ctx, 10532);
			if (HOME.containsPoint(ctx.players.getLocal())) {
				ctx.prayers.disableAll();
				ctx.sleep(2000, 7000);
				ctx.keyboard.sendKeys("");
				ctx.sleep(600);
				ctx.log("Teleporting To Avatar");
				ctx.keyboard.sendKeys("::crashevent");
				ctx.onCondition(() -> avatar1 != null || avatar2 != null, 250, 20);
			} else {
				if (avatar1 != null && !avatar1.isDead()) {
					//handlePrayer();
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
					}
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Attacking Avatar");
						status = "Attacking Avatar";
						avatar1.interact(SimpleNpcActions.ATTACK);
					}
				} else if (avatar2 != null && !avatar2.isDead()) {
					//handlePrayer();
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
					}
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Attacking Avatar");
						status = "Attacking Avatar";
						avatar2.interact(SimpleNpcActions.ATTACK);
					}
				} else {
					if (!HOME.containsPoint(ctx.players.getLocal()) && ctx.pathing.distanceTo(homeBank) > 50) {
						ctx.sleep(1, 4600);
						ctx.log("Avatar's Dead, Teleporting Home");
						status = "Avatar's Dead, Teleporting Home";
						ctx.prayers.disableAll();
						ctx.magic.castHomeTeleport();
						avatarSpawned = false;
						ctx.onCondition(() -> HOME.containsPoint(ctx.players.getLocal()), 250, 24);
					}
				}
			}
		} else if (hesporiStarted) {
			if (ctx.inventory.getFreeSlots() >= 2 && full) {
				full = false;
			}
			if (HOME.containsPoint(ctx.players.getLocal())) {
				ctx.log("0-1 minute sleep");
				status = "0-1 minute sleep";
				ctx.sleep(1, 60000);
				if (ctx.bank.bankOpen()) {
					ctx.bank.closeBank();
				}
				ctx.log("Teleporting To Hespori");
				status = "Teleporting To Hespori";
				ctx.keyboard.sendKeys("");
				ctx.sleep(600);
				ctx.keyboard.sendKeys("::slayer");
				teleported = true;
				walked = false;
				ctx.onCondition(() -> HESPORI_WAITING.containsPoint(ctx.players.getLocal()), 250, 36);
			} else if (HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
				SimpleGameObject gate = (SimpleGameObject) ctx.objects.populate().filter("Gate").nearest().next();
				if (gate != null && hesporiSpawned) {
					if (ctx.bank.bankOpen()) {
						ctx.bank.closeBank();
						return;
					}
					status = "Opening Gate";
					ctx.log("Opening Gate");
					ctx.sleep(600, 3600);
					gate.interact(SimpleObjectActions.OPEN);
					ctx.onCondition(() -> ctx.players.getLocal().getLocation().equals(start), 250, 20);
					return;
				}
				if (!ctx.bank.bankOpen() && !walked) {
					ctx.log("Opening Bank");
					status = "Opening Bank";
					ctx.bank.openBank();
					walked = true;
					ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 36);
				}
				if (!teleported && !hesporiStarted && !hesporiSpawned) {
					ctx.sleep(1, 4600);
					ctx.log("Teleporting Home");
					status = "Teleporting Home";
					ctx.magic.castHomeTeleport();
					ctx.onCondition(() -> HOME.containsPoint(ctx.players.getLocal()), 250, 24);
				} else if (!ctx.pathing.onTile(start)) {
					phase = 0;
					if (!ctx.bank.bankOpen() ) {
						ctx.log("Opening Bank");
						status = "Opening Bank";
						ctx.bank.openBank();
						ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 36);
					}
				}
			}
			if (HESPORI_INSIDE.containsPoint(ctx.players.getLocal())) {
				if (ctx.combat.health() <= (ctx.combat.maxHealth() - 70) && !ctx.prayers.prayerActive(SimplePrayers.Prayers.REDEMPTION)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.REDEMPTION, true);
				}
				walked = false;
				if (phase == 0) {
					if (ctx.players.getLocal().getLocation().equals(start)) {
						hesporiSpawned = false;
						chiselStone = false;
						if (ctx.pathing.distanceTo(vineWalk) >= 2) {
							ctx.pathing.walkToTile(vineWalk);
							ctx.onCondition(() -> ctx.pathing.onTile(vineWalk), 250, 24);
							ctx.sleep(600);
						}
						if (!hesporiSpawned) {
							phase = 1;
						}
					}
				} else if (phase == 1) {
					SimpleGameObject hesporiVines = (SimpleGameObject) ctx.objects.populate().filter("Hespori Vines").nearest()
							.next();
					if (!ctx.inventory.inventoryFull() && !full) {
						if (hesporiVines != null) {
							if (ctx.pathing.distanceTo(vineWalk) <= 2) {
								hesporiVines.interact(502);
								if (!ctx.pathing.inMotion()) {
									ctx.log("Collecting Vines");
								}
								status = "Collecting Vines";
							} else {
								ctx.pathing.walkToTile(vineWalk.derrive(between(-1, 0), between(-1, 0)));
								if (!ctx.pathing.inMotion()) {
									ctx.log("Walking To Vines");
								}
								status = "Walking To Vines";
							}
						} else {
							phase = 5;
						}
					} else {
						SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
						SimpleGameObject fire = (SimpleGameObject) ctx.objects.populate().filterHasAction("Burn-essence").nearest()
								.next();
						if (fire != null) {
							fire.interact(502);
							status = "Burning Essence";
							if (!ctx.pathing.inMotion()) {
								ctx.log("Burning Essence");
							}
						} else if (burningOre != null) {
							ctx.pathing.step(burningOre);
						} else {
							ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(-10, 0));
						}
					}
				} else if (phase == 2) {
					SimpleItem toxicGem = ctx.inventory.populate().filter("Toxic gem").next();
					SimpleItem chisel = ctx.inventory.populate().filter(1755).next();
					SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
					SimpleGameObject enchantedStone = (SimpleGameObject) ctx.objects.populate().filter("Enchanted stone").nearest()
							.next();
					SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
					SimpleNpc hespori = ctx.npcs.populate().filter(8583).next();
					SimpleItem hesporiEssence = ctx.inventory.populate().filter("Hespori essence").reverse().next();
					if ((ctx.inventory.inventoryFull() || full) && toxicGem == null) {
						chiselStone = true;
					}
					if (chiselStone) {
						if (ctx.inventory.populate().filter("Uncut toxic gem").isEmpty()) {
							chiselStone = false;
						} else if (uncutToxicGem != null && chisel != null) {
							status = "Chiseling";
							ctx.log("Chiseling");
							uncutToxicGem.interact(SimpleItemActions.USE);
							ctx.sleep(600);
							chisel.interact(SimpleItemActions.USE_WITH);
							ctx.onCondition(() -> ctx.inventory.populate().filter("Uncut toxic gem").isEmpty(), 250, 16);
						}
					} else if (toxicGem != null && ctx.inventory.populate().filterContains("Uncut toxic gem").isEmpty()) {
						status = "Attacking";
						ctx.log("Attacking - Toxic");
						hespori.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> ctx.inventory.populate().filter("Toxic gem").isEmpty(), 250, 3);
						return;
					}
					if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full
							&& System.currentTimeMillis() > (lastAnimation + 2000) && !chiselStone && hesporiEssence == null) {
						status = "Mining";
						ctx.log("Mining - Enchanted Stone");
						if (enchantedStone != null) {
							enchantedStone.interact("Mine");
						} else {
							if (burningOre != null && !ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full && System.currentTimeMillis() > (lastAnimation + 2000)) {
								burningOre.interact("Mine");
								lastAnimation = System.currentTimeMillis();
							}
						}
						ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
					}
					if (hesporiEssence != null) {
						hesporiEssence.interact(SimpleItemActions.DROP);
					}
				} else if (phase == 3) {
					SimpleItem hesporiEssence = ctx.inventory.populate().filter("Hespori essence").reverse().next();
					SimpleItem toxicGem = ctx.inventory.populate().filter("Toxic gem").next();
					SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
					SimpleItem burningRune = ctx.inventory.populate().filter("Burning rune").next();
					SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
					SimpleNpc hespori = ctx.npcs.populate().filter(8583).next();
					SimpleGameObject fire = (SimpleGameObject) ctx.objects.populate().filterHasAction("Burn-essence").nearest()
							.next();
					if (hespori != null && hespori.getHealth() <= 500) {
						phase = 4;
					}
					if (chiselStone) {
						chiselStone = false;
					}
					if (hesporiEssence != null) {
						hesporiEssence.interact(SimpleItemActions.DROP);
					}
					if (toxicGem != null) {
						status = "Attacking";
						if (!ctx.pathing.inMotion()) {
							ctx.log("Attacking - Toxic - Phase 3");
						}
						if (hespori != null) {
							hespori.interact(SimpleNpcActions.ATTACK);
							ctx.onCondition(() -> ctx.inventory.populate().filter("Toxic gem").isEmpty(), 250, 3);
						}
					} else if (uncutToxicGem != null) {
						uncutToxicGem.interact(SimpleItemActions.DROP);
						return;
					}
					if (burningRune != null) {
						if (hespori != null) {
							hespori.interact(SimpleNpcActions.ATTACK);
							ctx.onCondition(() -> ctx.inventory.populate().filter("Toxic gem").isEmpty(), 250, 3);
						}
						status = "Attacking";
						if (!ctx.pathing.inMotion()) {
							ctx.log("Attacking - Burning");
						}
						return;
					}
					if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full && System.currentTimeMillis() > (lastAnimation + 2000)) {
						if (burningOre != null) {
							status = "Mining";
							if (!ctx.pathing.inMotion()) {
								ctx.log("Mining - Burning Ore");
							}
							if (ctx.pathing.distanceTo(burningOre) >= 8) {
								ctx.pathing.step(burningOre);
							} else {
								burningOre.interact("Mine");
								lastAnimation = System.currentTimeMillis();
								ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
							}
						}
					} else if ((ctx.inventory.inventoryFull() || full) && burningOre != null && fire != null && ctx.pathing.distanceTo(fire) >= 3) {
						status = "Converting Runes";
						ctx.log("Converting Runes");
						fire.interact(900);
						ctx.onCondition(() -> ctx.inventory.populate().filter(9698).isEmpty(), 250, 8);
						return;
					}
				} else if (phase == 4) {
					SimpleGameObject deadHespori = (SimpleGameObject) ctx.objects.populate().filter("Hespori").nearest().next();
					if (deadHespori != null) {
						deadHespori.interact(502);
						status = "Attacking";
						ctx.log("Attacking - Dead");
						ctx.onCondition(() -> HESPORI_WAITING.containsPoint(ctx.players.getLocal()), 250, 36);
						if (HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
							phase = 0;
							hesporiSpawned = false;
							hesporiStarted = false;
							walked = false;
							ctx.sleep(1200, 4600);
						}
					}
				} else if (phase == 5) {
					int uncutGemCount = ctx.inventory.populate().filterContains("Uncut toxic").population(true);
					SimpleItem chisel = ctx.inventory.populate().filter(1755).next();
					SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
					SimpleGameObject enchantedStone = (SimpleGameObject) ctx.objects.populate().filter("Enchanted stone").nearest()
							.next();
					teleported = false;
					if (uncutGemCount >= 20) {
						chiselStone = true;
					}
					if (chiselStone) {
						if (ctx.inventory.populate().filter("Uncut toxic gem").isEmpty()) {
							chiselStone = false;
						} else if (uncutToxicGem != null && chisel != null) {
							status = "Chiseling";
							ctx.log("Chiseling");
							uncutToxicGem.interact(SimpleItemActions.USE);
							ctx.sleep(600);
							chisel.interact(SimpleItemActions.USE_WITH);
							ctx.onCondition(() -> ctx.inventory.populate().filter("Uncut toxic gem").isEmpty(), 250, 16);
						}
					}
					if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full
							&& System.currentTimeMillis() > (lastAnimation + 2000)) {
						status = "Mining";
						ctx.log("Mining - ");
						if (enchantedStone != null) {
							enchantedStone.interact("Mine");
							lastAnimation = System.currentTimeMillis();
						} else {
							ctx.pathing.walkToTile(phase5Bug);
						}
						ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
					}
				}
			}
		} else {
			if (!HOME.containsPoint(ctx.players.getLocal())) {
				ctx.sleep(1, 4600);
				ctx.log("Teleporting Home");
				ctx.magic.castHomeTeleport();
				ctx.onCondition(() -> HOME.containsPoint(ctx.players.getLocal()), 250, 24);
			} else if (!avatarSpawned && !hesporiStarted) {
				ctx.prayers.disableAll();
				heal(ctx);
				if (ctx.pathing.distanceTo(homeBank) >= 1) {
					ctx.log("Pathing To Bank");
					status = "Pathing To Bank";
					ctx.pathing.step(homeBank);
					ctx.onCondition(() -> ctx.pathing.onTile(homeBank), 250, 36);
				} else {
					if (!ctx.bank.bankOpen()) {
						ctx.log("Opening Bank");
						status = "Opening Bank";
						ctx.bank.openBank();
						ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 36);
					} else {
						SimpleItem key = ctx.inventory.populate().filter(23502).next();
						SimpleItem hesporiKey = ctx.inventory.populate().filter("Hespori key").next();
						if (ctx.inventory.populate().filter(11180).next() != null) {
							ctx.log("Depositing Ancient Coin");
							status = "Depositing Ancient Coin";
							ctx.bank.deposit(11180, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(19712).next() != null) {
							ctx.log("Depositing Easy Birds Nest");
							status = "Depositing Easy Birds Nest";
							ctx.bank.deposit(19712, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(19714).next() != null) {
							ctx.log("Depositing Medium Birds Nest");
							status = "Depositing Medium Birds Nest";
							ctx.bank.deposit(19714, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(19716).next() != null) {
							ctx.log("Depositing Hard Birds Nest");
							status = "Depositing Hard Birds Nest";
							ctx.bank.deposit(19716, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(20358).next() != null) {
							ctx.log("Depositing Easy Geode");
							status = "Depositing Easy Geode";
							ctx.bank.deposit(20358, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(20360).next() != null) {
							ctx.log("Depositing Medium Geode");
							status = "Depositing Medium Geode";
							ctx.bank.deposit(20360, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(20362).next() != null) {
							ctx.log("Depositing Hard Geode");
							status = "Depositing Hard Geode";
							ctx.bank.deposit(20362, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(681).next() != null) {
							ctx.log("Depositing Ancient Talisman");
							status = "Depositing Ancient Talisman";
							ctx.bank.deposit(681, SimpleBank.Amount.ALL);
						}
						if (ctx.inventory.populate().filter(25968).next() != null) {
							ctx.log("Depositing Hespori Bark");
							status = "Depositing Hespori Bark";
							ctx.bank.deposit(25968, SimpleBank.Amount.ALL);
						}
						if (hesporiKey != null) {
							ctx.log("Depositing Hespori Key");
							status = "Depositing Hespori Key";
							ctx.bank.deposit(22374, SimpleBank.Amount.ALL);
						} else if (key != null) {
							ctx.log("Depositing Avatar Key");
							status = "Depositing Avatar Key";
							ctx.bank.deposit(23502, SimpleBank.Amount.ALL);
						} else {
							status = "Afking Till Next Event";
							ctx.onCondition(() -> hesporiStarted || hesporiSpawned || avatarSpawned, 1000, 30);
							if (ctx.pathing.distanceTo(homeBank) > 1) {
								ctx.log("Moved, Possibly Teleported? Stopping Script");
								ctx.stopScript();
							}
						}
					}
				}
			}
		}
	}

	private static SimpleNpc getAvatar(ClientContext ctx, int npcId) {
		return ctx.npcs.populate().filter(npcId).filter(n -> n != null).nearest().next();
	}

	private static int between(final int min, final int max) {
		final Random random = new Random();
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}
	
	public static void heal(ClientContext ctx) {
		final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
		if (ctx.pathing.distanceTo(homeBank) <= 10) {
			SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filterContains("Box of Health").nearest().next();
	    	if (heal != null && ((ctx.combat.health() < ctx.combat.maxHealth()) || (ctx.prayers.points() < ctx.prayers.maxPoints()))) {
				status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}	
		}
    }
	
	private void handleDrinkingRange() {
		final int realRangeLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedRangeLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		final int realStrengthLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
		final int boostedStrengthLevel = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			ctx.log("Using Overload");
			overload.interact(74);
			ctx.sleep(600);
			lastOverload = System.currentTimeMillis();
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedRangeLevel - realRangeLevel <= 9) {
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedRangeLevel - realRangeLevel <= 2) {
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Super combat").isEmpty() && boostedStrengthLevel - realStrengthLevel <= 9) {
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
	}
	
	public void handleDrinkingPrayer() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super r").isEmpty() && ctx.prayers.points() <= 20) {
			status = "Drinking Restore Potion";
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			prayer = 20 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= 20) {
			status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			prayer = 20 + random;
		}
	}

	public void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.healthPercent() <= 65 && ctx.combat.health() != 10) {
			status = "Eating Food";
			ctx.log("Eating Food");
			food.interact(74);
		} else if (brew != null && ctx.combat.healthPercent() <= 70 && ctx.combat.health() != 10) {
			status = "Drinking Brew";
			ctx.log("Drainking Brew");
			brew.interact(74);
		}
	}

	@Override
	public void onTerminate() {
	}

	@Override
	public void onPaint(Graphics2D g) {
		SimpleWidget hesporiSpawn1 = ctx.widgets.populate().filter(1000004).next();
		SimpleWidget hesporiSpawn2 = ctx.widgets.populate().filter(1000005).next();
		if (hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Hespori spawns")) {
			g.setColor(new Color(29, 3, 3, 94));
			g.fillRect(7, 273, 180, 45);
			g.setColor(new Color(0, 0, 0));
			g.setStroke(new BasicStroke(1));
			g.drawRect(7, 273, 180, 45);
		} else if (hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Hespori spawns")) {
			g.setColor(new Color(29, 3, 3, 94));
			g.fillRect(7, 273, 180, 45);
			g.setColor(new Color(0, 0, 0));
			g.setStroke(new BasicStroke(1));
			g.drawRect(7, 273, 180, 45);
		} else if (hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Avatar spawns")) {
			g.setColor(new Color(29, 3, 3, 94));
			g.fillRect(7, 273, 180, 45);
			g.setColor(new Color(0, 0, 0));
			g.setStroke(new BasicStroke(1));
			g.drawRect(7, 273, 180, 45);
		} else if (hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Avatar spawns")) {
			g.setColor(new Color(29, 3, 3, 94));
			g.fillRect(7, 273, 180, 45);
			g.setColor(new Color(0, 0, 0));
			g.setStroke(new BasicStroke(1));
			g.drawRect(7, 273, 180, 45);
		} else {
			g.setColor(new Color(29, 3, 3, 94));
			g.fillRect(7, 273, 180, 33);
			g.setColor(new Color(0, 0, 0));
			g.setStroke(new BasicStroke(1));
			g.drawRect(7, 273, 180, 33);
		}
		g.setFont(new Font("Arial", 1, 10));
		g.setColor(new Color(255, 255, 255));
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
		g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
		g.drawString("Status: " + status, 10, 300);
		if (hesporiSpawned) {
			g.drawString(ctx.paint.formatTime(System.currentTimeMillis() - startTime).replaceAll("00:", ""), 445, 325);
			g.drawString("Spawned: Hespori", 10, 313);
		} else if (avatarSpawned) {
			g.drawString("Spawned: Avatar", 10, 313);
		} else {
			if (hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Hespori spawns")) {
				g.drawString("" + hesporiSpawn1.getText().replaceAll("@or1@- ", ""), 10, 313);
			} else if (hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Hespori spawns")) {
				g.drawString("" + hesporiSpawn2.getText().replaceAll("@or1@- ", ""), 10, 313);
			} else if (hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Avatar spawns")) {
				g.drawString("" + hesporiSpawn1.getText().replaceAll("@or1@- ", ""), 10, 313);
			} else if (hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Avatar spawns")) {
				g.drawString("" + hesporiSpawn2.getText().replaceAll("@or1@- ", ""), 10, 313);
			}
		}
	}
	
	public void setupPrayer(int prayerType) {
		WorldEvents.prayerType = prayerType;
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0) {
			if (event.getMessage().contains("is causing havoc")) {
				avatarSpawned = true;
			} else if (event.getMessage().contains("You have run out of inventory space.") || event.getMessage().contains("You have no more free")) {
				full = true;
			} else if (event.getMessage().contains("Hespori world boss has spawned")) {
				hesporiSpawned = true;
			} else if (event.getMessage().contains("Hespori is now extremely weak")) {
				phase = 3;
			} else if (event.getMessage().contains("Enough essence has burned") || event.getMessage().contains("essence are still required to be burned") || event.getMessage().contains("Enough essence has already") || event.getMessage().contains("Hespori's defence has been lowered") || event.getMessage().contains("enough essence")) {
				phase = 2;
			} else if (event.getMessage().contains("You cannot do this right now")) {
				phase = 4;
			} else if (event.getMessage().contains("You must have at least one free inventory")) {
				if (ctx.inventory.populate().filter("Unfired burning rune").next() != null) {
					ctx.inventory.populate().filter("Unfired burning rune").next().interact(SimpleItemActions.DROP);
				}
			} else if (event.getMessage().contains("Hespori's magic prevents you from")) {
				phase = 0;
			}
		}
	}

	@Override
	public int loopDuration() {
		return 150;
	}
}