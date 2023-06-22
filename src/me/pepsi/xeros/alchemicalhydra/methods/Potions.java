package me.pepsi.xeros.alchemicalhydra.methods;

import java.util.Random;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.filters.SimpleSkills;
import simple.api.wrappers.SimpleItem;

public class Potions {
	
	public static long lastHeart = -1;
	private static long lastOverload = -1;
	
	final static ClientContext ctx = ClientContext.instance();
	
	public static void rangePotion() {
		int realRangeLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		int boostedRangeLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		int realStrLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
		int boostedStrLevel = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			AlchemicalHydra.status = "Using Overload";
			ctx.log("Using Overload");
			overload.interact(74);
			lastOverload = System.currentTimeMillis();
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedRangeLevel - realRangeLevel <= 9) {
			AlchemicalHydra.status = "Drinking Ranging Potion";
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (!ctx.inventory.populate().filterContains("Combat").isEmpty() && boostedStrLevel - realStrLevel <= 9) {
			AlchemicalHydra.status = "Drinking Combat Potion";
			ctx.log("Drinking Combat Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
	}
    
	public static void rangeHeart() {
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
	    if (focusHeart != null && System.currentTimeMillis() > (lastHeart + 430000)) {
	    	AlchemicalHydra.status = "Using Focused Heart";
	    	ctx.log("Using Focused Heart");
	    	focusHeart.interact(74);
	    	ctx.sleep(600);
	    }
	}
	
	public static void meleeHeart() {
		SimpleItem corruptHeart = ctx.inventory.populate().filter(280).next();
	    if (corruptHeart != null && System.currentTimeMillis() > (lastHeart + 430000)) {
	    	AlchemicalHydra.status = "Using Corrupt Heart";
	    	ctx.log("Using Corrupt Heart");
	    	corruptHeart.interact(74);
	    	ctx.sleep(600);
	    }
	}
	
	public static void handleDrinkingPrayer() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super restore").isEmpty() && ctx.prayers.points() <= AlchemicalHydra.prayer) {
			AlchemicalHydra.status = "Drinking Super Restore";
			ctx.log("Drinking Super Restore");
			ctx.inventory.next().interact(74);
			ctx.sleep(600);
			AlchemicalHydra.prayer = 20 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer potion").isEmpty() && ctx.prayers.points() <= AlchemicalHydra.prayer) {
			AlchemicalHydra.status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(74);
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Sanfew serum").isEmpty() && ctx.prayers.points() <= AlchemicalHydra.prayer) {
			AlchemicalHydra.status = "Drinking Sanfew Serum";
			ctx.log("Drinking Sanfew Serum");
			ctx.inventory.next().interact(74);
			ctx.sleep(600);
		}
	}
	
	public static void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			AlchemicalHydra.status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}
}
