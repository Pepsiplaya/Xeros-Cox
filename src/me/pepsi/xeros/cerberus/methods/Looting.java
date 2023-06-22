package me.pepsi.xeros.cerberus.methods;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;

public class Looting {
	
	final static ClientContext ctx = ClientContext.instance();
	
	final public static String[] lootNames = {  "Key master teleport", "Clue", "Coins", "coin", "Resource", "Crystal", "Battlestaff", "Death rune", "Soul rune", "Blood rune", "Dragon bone", "Coal", "Rune 2h", "Rune halberd", "Torstol", "Rune full", "Rune chain", "Rune plate", "Runite", "Jar", "crystal", "Smouldering", "Hellpuppy" };
	//final public static String[] lootNames = {  "Clue", "Coins", "coin", "Resource", "Crystal", "Death rune", "Soul rune", "Blood rune", "Dragon bone", "Coal", "Torstol", "Runite", "Jar", "crystal", "Smouldering", "Hellpuppy" };
	
    public static void loot() {
    	SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
    	if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty() && item != null && ctx.inventory.canPickupItem(item)) {
    		Cerberus.status = "Looting " + item.getName();
    		ctx.log("Looting " + item.getName());
        	item.interact();
        	return;
        }
    	if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item)) {
        	alch();
        	ctx.sleep(600);
    	}
    	if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item) && ctx.inventory.populate().filterHasAction("Eat").population(true) >= 1) {
    		Cerberus.status = "Clearing Space";
            ctx.log("Clearing Space, Eating Food");
            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
    	} else if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item) && ctx.inventory.populate().filterContains("Saradomin Brew").population(true) >= 1) {
			Cerberus.status = "Clearing Space";
            ctx.log("Clearing Space, Eating Food");
            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
            return;
		} else if (ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
			Cerberus.status = "Restocking";
			ctx.log("Restocking");
			ctx.prayers.disableAll();
			ctx.magic.castHomeTeleport();
			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			return;
		}
    }
    
    public static void alch() {
        int naturerune = ctx.inventory.populate().filter(561).population(true);
    	int firerune = ctx.inventory.populate().filter(554).population(true);
    	SimpleItem runePouch = ctx.inventory.populate().filter(12791).next();
    	if (!ctx.inventory.populate().filterContains("Rune platebody").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Rune platebody";
            ctx.log("Alching Rune platebody");
            SimpleItem alch = ctx.inventory.populate().filter(1127).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Rune 2h").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Rune 2h sword";
            ctx.log("Alching Rune 2h sword");
            SimpleItem alch = ctx.inventory.populate().filter(1319).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Rune halberd").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Rune halberd";
            ctx.log("Alching Rune halberd");
            SimpleItem alch = ctx.inventory.populate().filter(3202).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Rune full").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Rune full helm";
            ctx.log("Alching Rune full helm");
            SimpleItem alch = ctx.inventory.populate().filter(1163).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Rune chainbody").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Rune chainbody";
            ctx.log("Alching Rune chainbody");
            SimpleItem alch = ctx.inventory.populate().filter(1113).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Lava battlestaff").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            Cerberus.status = "Alching Lava battlestaff";
            ctx.log("Alching Lava battlestaff");
            SimpleItem alch = ctx.inventory.populate().filter(3053).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    }
}
