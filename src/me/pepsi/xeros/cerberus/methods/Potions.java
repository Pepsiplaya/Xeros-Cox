package me.pepsi.xeros.cerberus.methods;

import java.util.Random;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.filters.SimpleSkills;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;

public class Potions {
	
	public static long lastOverload = -1;
	final static ClientContext ctx = ClientContext.instance();
	
	public static void rangePotion() {
		int realRangeLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		int boostedRangeLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		int realStrLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
		int boostedStrLevel = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem corruptHeart = ctx.inventory.populate().filter(280).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		SimpleNpc ghost = ctx.npcs.populate().filter(5868).nearest().next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ghost == null && ctx.combat.health() >= 65) {
			Cerberus.status = "Using Overload";
			ctx.log("Using Overload");
			ctx.sleep(1200);
			overload.interact(74);
			lastOverload = System.currentTimeMillis();
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedRangeLevel - realRangeLevel <= 9) {
			Cerberus.status = "Drinking Ranging Potion";
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (!ctx.inventory.populate().filterContains("Combat").isEmpty() && boostedStrLevel - realStrLevel <= 9) {
			Cerberus.status = "Drinking Combat Potion";
			ctx.log("Drinking Combat Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedRangeLevel == realRangeLevel) {
			Cerberus.status = "Using Focused Heart";
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
			ctx.sleep(600);
		} else if (corruptHeart != null && boostedStrLevel == realStrLevel) {
			Cerberus.status = "Using Corrupt Heart";
			ctx.log("Using Corrupt Heart");
			corruptHeart.interact(74);
			ctx.sleep(600);
		}
	}
	
	public static void handleDrinkingPrayer() {
		SimpleNpc ghost = ctx.npcs.populate().filter(5868).nearest().next();
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super restore").isEmpty() && ctx.prayers.points() <= Cerberus.prayer && ghost == null) {
			Cerberus.status = "Drinking Super Restore";
			ctx.log("Drinking Super Restore");
			ctx.inventory.next().interact(74);
			Cerberus.prayer = 35 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer potion").isEmpty() && ctx.prayers.points() <= Cerberus.prayer && ghost == null) {
			Cerberus.status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(74);
			Cerberus.prayer = 35 + random;
		}
	}
	
	public static void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			Cerberus.status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}
}
