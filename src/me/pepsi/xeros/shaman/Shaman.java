package me.pepsi.xeros.shaman;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Script;

import java.awt.*;

@ScriptManifest(author = "Pepsiplaya", name = "Shaman Killer", category = Category.COMBAT, version = "1.0D", description = "Kills shaman", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Shaman extends Script implements SimplePaintable, SimpleMessageListener {

	private static long lastOverload = -1; // variable to track the last time overload was used

	public static final String[] lootNames = { "coin", "Dragon warhammer", "Chaos rune", "Death rune", "Runite", "Coal", "Ore" }; // Array of loot names

	@Override
	public boolean onExecute() {
		return true; // This method is executed when the script starts running. We return true to continue script execution.
	}

	@Override
	public void onProcess() { // This method is executed repeatedly while the script is running.
		SimpleNpc shaman = shaman().nearest().next(); // Get the nearest shaman
		handlePrayer(); // Handle prayer actions
		handleEatingFood(); // Handle food eating actions
		handleDrinkingPrayer(); // Handle prayer potion drinking actions
		handleDrinkingRange(); // Handle range potion drinking actions
		openCoinBag(); // Open coin bag if there's any
		dropVial(); // Drop vial if there's any
		// If a shaman is available and there's no loot to pick up, attack the shaman
		if (shaman != null && lootAvailable().isEmpty()) {
			if (ctx.players.getLocal().getInteracting() == null) {
				ctx.log("Attacking " + shaman.getName());
				shaman.interact(SimpleNpcActions.ATTACK);
			}
		} else if (!lootAvailable().isEmpty() && ctx.players.getLocal().getInteracting() == null) {
			loot(); // If there's loot available, pick it up
		}
		// If the player is poisoned, use Sanfew serum
		if (ctx.combat.isPoisoned()) {
			SimpleItem sanfew = ctx.inventory.populate().filterContains("Sanfew serum").next();
			if (sanfew != null) {
				sanfew.interact(74);
			}
		}
	}

	public final SimpleEntityQuery<SimpleNpc> shaman() { // Method to get shaman npcs in range
		return ctx.npcs.populate().filter(6766).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 15) {
				return false;
			}
			return true;
		});
	}

	// Function to handle actions related to the player's ranged skill
	private void handleDrinkingRange() {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED); // Get player's real Ranged level
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED); // Get player's boosted Ranged level
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next(); // Get "Focus Heart" item from inventory
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next(); // Get "Overload" potion from inventory
		// If Overload potion exists in inventory, and it's time to drink (over 5 minutes have passed), and player's health is more than 65, then drink it
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			ctx.log("Using Overload");
			ctx.sleep(1200);
			overload.interact(74);
			ctx.sleep(600);
			lastOverload = System.currentTimeMillis();
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9) {
			ctx.log("Drinking Ranging Potion"); // If a Ranging potion exists in inventory, and the boosted Ranged level is no more than 9 levels higher than the real level, then drink the potion
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedLevel - realLevel <= 2) {
			ctx.log("Using Focused Heart"); // If Focus Heart exists in inventory, and the boosted Ranged level is no more than 2 levels higher than the real level, then use Focus Heart
			focusHeart.interact(74);
			ctx.sleep(600);
		}
	}

	// Function to handle prayer status
	private void handlePrayer() {
		// If protection from melee prayer isn't active, activate it
		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
			ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
		}
		// If piety prayer isn't active, activate it
		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
			ctx.prayers.prayer(SimplePrayers.Prayers.PIETY);
		}
	}

	// Function to handle opening coin bags
	private void openCoinBag() {
		// If coin bag exists in inventory and inventory isn't full, open it
		if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
			ctx.log("Opening Coin Bag");
			ctx.inventory.populate().filterContains("coin bag").next().interact(539);
		}
	}

	// Function to handle dropping vials
	public void dropVial() {
		// If vial exists in inventory, drop it
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}

	// Function to handle looting items
	public void loot() {
		SimpleGroundItem item = ctx.groundItems.nearest().peekNext(); // Get the nearest item
		dropVial(); // Drop vial if exists in inventory
		// If lootable items exist on the ground, player isn't in motion, player can pick the item, and player isn't interacting with anything, loot the item
		if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty() && item != null && !ctx.pathing.inMotion() && ctx.inventory.canPickupItem(item) && ctx.players.getLocal().getInteracting() == null) {
			ctx.log("Looting " + item.getName());
			item.interact();
			return;
		}
		// If player cannot pick the item and Saradomin brew exists in inventory, eat food to clear space
		if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty()
				&& !ctx.inventory.canPickupItem(item)
				&& ctx.inventory.populate().filterContains("Saradomin brew").next() != null) {
			ctx.log("Clearing Space, Eating Food");
			ctx.inventory.next().interact(SimpleItemActions.CONSUME);
			return;
		}
	}

	// Function to check if loot is available
	public final SimpleEntityQuery<SimpleGroundItem> lootAvailable() {
		return ctx.groundItems.populate().filterContains(lootNames).filter(i -> {
			if (i == null) {
				return false;
			}
			if (i.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 10) {
				return false;
			}
			return true;
		});
	}

	// Function to manage eating food for healing
	public void handleEatingFood() {
		// Find food item in the inventory
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		// Find Saradomin brew in the inventory
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		// If health is below or equal to 65 and food is available, eat it
		if (food != null && ctx.combat.health() <= 65) {
			ctx.log("Eating Food");
			food.interact(74);
		}
		// If health is below or equal to 70 and Saradomin brew is available, drink it
		else if (brew != null && ctx.combat.health() <= 70) {
			ctx.log("Drinking brew");
			brew.interact(74);
		}
	}


	// Function to manage drinking prayer potions or super restores
	public void handleDrinkingPrayer() {
		// If prayer points are below or equal to 20 and a super restore is available, drink it
		if (!ctx.inventory.populate().filterContains("Super r").isEmpty() && ctx.prayers.points() <= 20) {
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
		// If prayer points are below or equal to 20 and a prayer potion is available, drink it
		else if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= 20) {
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
	}

	// Method that is called when the script is terminated
	@Override
	public void onTerminate() {
		// Code to be executed on termination goes here
	}

	// Method that is called to render 2D graphics
	@Override
	public void onPaint(Graphics2D g) {
		// Code to be executed for painting graphics goes here
	}

	// Method that is called when a chat message event occurs
	public void onChatMessage(ChatMessageEvent event) {
		// Code to be executed on chat message goes here
	}
}