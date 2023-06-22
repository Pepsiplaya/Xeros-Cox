package me.pepsi.xeros.zulrah.methods;

import java.util.Random;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.filters.SimpleSkills;
import simple.api.wrappers.SimpleItem;

public class Potions {
	
	private static long lastFocused = -1;
	private static long lastOverload = -1;
	
	public static void pot(ClientContext ctx) {
    	int ppots = ctx.inventory.populate().filterContains("Prayer potion").population(true);
		int foodCount = ctx.inventory.populate().filterHasAction("Eat").population(true);
		int brewCount = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
    	int restore = ctx.inventory.populate().filterContains("Super restore").population(true);
    	SimpleItem sanfew = ctx.inventory.populate().filterContains("Sanfew serum").next();
    	SimpleItem venomPotion = ctx.inventory.populate().filterContains("venom").next();
    	if (ctx.combat.isPoisoned() && sanfew != null) {
			sanfew.interact(74);
			Zulrah.status = "Drinking Sanfew Potion";
			ctx.log("Drinking Sanfew Potion");
		}
		if (ppots <= 0 && restore <= 0 && ctx.prayers.points() <= 2) {
			Zulrah.status = "Out Of Prayer";
			ctx.prayers.disableAll();
			ctx.keyboard.sendKeys("::raids");
			ctx.log("Out Of Prayer");
			ctx.onCondition(() -> Zulrah.WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
		}
		if (ctx.combat.health() < 20 && ctx.combat.health() != 10) {
			Zulrah.status = "Low Health Panic TP";
			ctx.prayers.disableAll();
			ctx.keyboard.sendKeys("::raids");
			ctx.log("Low Health Panic TP");
			ctx.onCondition(() -> Zulrah.WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
		}
		if ((foodCount == 0 && brewCount == 0) && ctx.combat.health() < 40) {
			Zulrah.status = "Out Of Food";
			ctx.prayers.disableAll();
			ctx.keyboard.sendKeys("::raids");
			ctx.log("Out Of Food");
			ctx.onCondition(() -> Zulrah.WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
		}
		if (ctx.varpbits.varpbit(1359) == 2) {
			if (venomPotion != null ) {
				Zulrah.status = "Drinking Venom Potion";
				ctx.log("Drinking Venom Potion");
				venomPotion.interact(74);
			} else if (sanfew != null) {
				sanfew.interact(74);
				Zulrah.status = "Drinking Sanfew Potion";
				ctx.log("Drinking Sanfew Potion");
			}
		}
    }
    
	public static void rangePotion(ClientContext ctx) {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			Zulrah.status = "Using Overload";
			ctx.log("Using Overload");
			overload.interact(74);
			lastOverload = System.currentTimeMillis();
			ctx.sleep(600);
		}
		if (boostedLevel - realLevel <= 6) {
			if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9) {
				Zulrah.status = "Drinking Ranging Potion";
				ctx.log("Drinking Ranging Potion");
				ctx.inventory.next().interact(SimpleItemActions.DRINK);
			}
		}
        if (focusHeart != null && System.currentTimeMillis() > (lastFocused + 430000)) {
            Zulrah.status = "Using Focused Heart";
            ctx.log("Using Focused Heart");
            focusHeart.interact(74);
            ctx.sleep(600);
            focusHeart.interact(74);
            lastFocused = System.currentTimeMillis();
        }
	}
	
	public static void handleDrinkingPrayer(ClientContext ctx) {
		Random rn = new Random();
		int random = rn.nextInt(5);
		if (!ctx.inventory.populate().filterContains("Super restore").isEmpty() && ctx.prayers.points() <= Zulrah.prayer && !ctx.pathing.inMotion()) {
			Zulrah.status = "Drinking Restore Potion";
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Zulrah.prayer = 10 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer potion").isEmpty() && ctx.prayers.points() <= Zulrah.prayer && !ctx.pathing.inMotion()) {
			Zulrah.status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Zulrah.prayer = 10 + random;
		}
	}
	
	public static void dropVial(ClientContext ctx) {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			Zulrah.status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}
}
