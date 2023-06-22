package me.pepsi.xeros.zulrah.methods;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;

public class Looting {
	
	//final public static String[] lootNames = { "Coal", "bones", "Clue scroll", "Resource", "Crystal", "key", "coin", "Zulrah", "ore", "onyx", "Jar", "Tanzanite", "Serpentine", "Pet", "Magma", "fang" };
	
	final public static String[] lootNames = { "Grimy", "Green dragon", "bones", "Clue scroll", "Resource", "Crystal", "key", "coin", "bar", "Zulrah", "rune", "logs", "Coal", "ore", "shark", "onyx", "Jar", "Magic", "Tanzanite", "Serpentine", "Pet", "Magma" };
	
    public static void Loot(ClientContext ctx) {
    	int ppots = ctx.inventory.populate().filterContains("Prayer potion").population(true);
    	int foodCount = ctx.inventory.populate().filterHasAction("Eat").population(true);
    	int brewCount = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
		int restore = ctx.inventory.populate().filterContains("Super restore").population(true);
		SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
        SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		ctx.prayers.disableAll();
		ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(lootNames).isEmpty(), 250, 8);
		if (Zulrah.ZULRAH.containsPoint(ctx.players.getLocal())) {
			if (archerRing != null) {
    			archerRing.interact(SimpleItemActions.FIRST);
    		} else if (archerRingi != null) {
    			archerRingi.interact(SimpleItemActions.FIRST);
    		}
			if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
				SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
				if (item != null) {
					if (archerRing != null) {
						archerRing.interact(SimpleItemActions.WEAR);
					} else if (archerRingi != null) {
						archerRingi.interact(SimpleItemActions.WEAR);
					}
					if (ctx.inventory.canPickupItem(item)) {
						Zulrah.status = "Looting " + item.getName();
						ctx.log("Looting " + item.getName());
						item.interact();
					} else {
						if (food != null) {
							Zulrah.status = "Freeing Up Space";
							ctx.log("Freeing Up Space");
							if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
								Zulrah.status = "Dropping Vial";
								ctx.log("Dropping Vial");
								ctx.inventory.next().interact(SimpleItemActions.DROP);
							} else {
								food.interact(SimpleItemActions.CONSUME);
							}
						} else if (brew != null) {
							Zulrah.status = "Freeing Up Space";
							ctx.log("Freeing Up Space");
							if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
								Zulrah.status = "Dropping Vial";
								ctx.log("Dropping Vial");
								ctx.inventory.next().interact(SimpleItemActions.DROP);
							} else {
								brew.interact(SimpleItemActions.DRINK);
							}
						}
					}	
				}
			} else if (ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
		    	SimpleItem teleport = ctx.inventory.populate().filter(12938).next();
		    	if ((ppots >= 1 || restore >= 1) && (foodCount >= 2 || brewCount >= 2)) {
					Zulrah.status = "Teleporting To Zulrah";
					ctx.log("Teleporting To Zulrah");
					ctx.prayers.disableAll();
					if (teleport != null) {
						Zulrah.status = "Restocking";
						ctx.log("Restocking");
						ctx.prayers.disableAll();
						teleport.interact(74);
						ctx.sleep(100);
						teleport.interact(74);
						ctx.sleep(100);
						teleport.interact(74);
						ctx.sleep(4500, 6000);
						Zulrah.dead = false;
					} else {
						if (Zulrah.teleport) {
							ctx.teleporter.teleportPrevious();
							ctx.onCondition(() -> ctx.players.getLocal().getLocation().getRegionID() == 8751, 250, 36);
							Zulrah.dead = false;
						} else {
							ctx.teleporter.open();
							ctx.teleporter.teleportStringPath("Bosses", "Zulrah");
							ctx.onCondition(() -> ctx.players.getLocal().getLocation().getRegionID() == 8751, 250, 36);
							Zulrah.dead = false;
						}
					}
				} else {
					Zulrah.status = "Restocking";
					ctx.log("Restocking");
					ctx.prayers.disableAll();
					ctx.keyboard.sendKeys("::raids");
					ctx.log("Restocking");
					ctx.onCondition(() -> Zulrah.WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 6);
				}
			}
		}
    }
}
