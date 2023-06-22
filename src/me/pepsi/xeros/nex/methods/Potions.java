package me.pepsi.xeros.nex.methods;

import java.util.Random;

import me.pepsi.xeros.nex.core.Nex;
import me.pepsi.xeros.raids.Raids;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.filters.SimpleSkills;
import simple.api.wrappers.SimpleItem;

public class Potions {
	
	final static ClientContext ctx = ClientContext.instance();
	private static long lastOverload = -1;

    public static void pot() {
		int brew = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
		if ((brew <= 0) && (ctx.prayers.points() <= 2 || ctx.players.getLocal().getHealth() <= 40)) {
			Nex.status = "PANIC TELE";
			ctx.prayers.disableAll();
			ctx.chat.sendCommand("raids");
			ctx.onCondition(() -> Nex.RAIDS_WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
		}
    }
    
	public static void rangePotion() {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			Raids.status = "Using Overload";
			ctx.log("Using Overload");
			overload.interact(74);
			lastOverload = System.currentTimeMillis();
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9 ) {
			Nex.status = "Drinking Ranging Potion";
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedLevel - realLevel <= 2) {
			Raids.status = "Using Focused Heart";
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
			ctx.sleep(600);
		}
	}
	
	public static void handleDrinkingPrayer() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super restore").isEmpty() && ctx.prayers.points() <= Nex.prayer) {
			Nex.status = "Drinking Restore Potion";
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Nex.prayer = 30 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer potion").isEmpty() && ctx.prayers.points() <= Nex.prayer) {
			Nex.status = "Drinking Prayer Potion";
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Nex.prayer = 30 + random;
		} else if (!ctx.inventory.populate().filterContains("Sanfew serum").isEmpty() && ctx.prayers.points() <= Nex.prayer) {
			Nex.status = "Drinking Sanfew Serum";
			ctx.inventory.next().interact(74);
			Nex.prayer = 30 + random;
		}
	}
	
	public static void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			Nex.status = "Dropping Vial";
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}
}
