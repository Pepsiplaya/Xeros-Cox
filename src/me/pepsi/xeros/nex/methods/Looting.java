package me.pepsi.xeros.nex.methods;

import java.util.Random;

import me.pepsi.xeros.nex.core.Nex;
import simple.api.ClientContext;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGroundItem;

public class Looting {
	
	final static ClientContext ctx = ClientContext.instance();
	
	final public static String[] lootNames = {  "Torva", "Ancient hilt", "Nihil", "Zaryte vambraces", "Blood rune", "Death rune", "Cannonball", "Onyx", "Runite ore", "Saradomin brew", "Super restore", "Clue", "Nex", "Coins", "coin", "Resource", "Crystal", "Coal", "Dragon bolts", "Soul rune", "Blood rune", "Death rune", "Cannonball", "Onyx", "Runite ore", "Saradomin brew", "Super restore", "Clue", "Nex", "coin", "Resource" };
	
    public static void Loot() {
    	SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
    	if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty() && item != null && ctx.inventory.canPickupItem(item)) {
    		Nex.status = "Looting " + item.getName();
    		item.interact();
    		return;
    	}
    	final WorldPoint nexWalk = new WorldPoint(3309, 5336, 1);
    	ctx.pathing.walkToTile(nexWalk.derrive(between(-2, 2), between(-2, 2)));
    }

	private static  int between(final int min, final int max) {
        	final Random random = new Random();
            try {
                return min + (max == min ? 0 : random.nextInt(max - min));
            } catch (Exception e) {
                return min + (max - min);
            }
	}
}
