package me.pepsi.xeros.alchemicalhydra.methods;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;

public class Looting {
	
	final static ClientContext ctx = ClientContext.instance();
	
	final public static String[] lootNames = {  "Hydra's ", "Clue", "Coins", "coin", "Resource", "Crystal", "Battlestaff", "Death rune", " eye", " heart", " claw", "Alchemic", "Chaos rune", "Astral rune", "Dragonstone bolts", "Blood rune", "Dragon bone", "Dragon longsword", "Dragon med helm", "Dragon battleaxe", "Rune platebody", "Rune platelegs", "Rune plateskirt", "Mystic", "Dragon bolts", "Onyx", "Hydra leather", "Magic seed" };
	
    public static void Loot() {
    	Alch();
    	SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
    	if (AlchemicalHydra.dead == true) {
    		if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty() && item != null && ctx.inventory.canPickupItem(item)) {
    			AlchemicalHydra.status = "Looting " + item.getName();
    			ctx.log("Looting " + item.getName());
        		item.interact();
        		AlchemicalHydra.walkBoolean = false;
        		ctx.prayers.disableAll();
        		SimpleItem berserkerRingi = ctx.inventory.populate().filter(11773).next();
    	        SimpleItem berserkerRing = ctx.inventory.populate().filter(6737).next();
    	        SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
    	        SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
    			if (berserkerRingi != null) {
        			AlchemicalHydra.status = "Switching Ring";
        			ctx.log("Switching Ring");
        			berserkerRingi.interact(SimpleItemActions.FIRST);
        		} else if (berserkerRing != null) {
        			AlchemicalHydra.status = "Switching Ring";
        			ctx.log("Switching Ring");
        			berserkerRing.interact(SimpleItemActions.FIRST);
        		} else if (archerRing != null) {
        			AlchemicalHydra.status = "Switching Ring";
        			ctx.log("Switching Ring");
        			archerRing.interact(SimpleItemActions.FIRST);
        		} else if (archerRingi != null) {
        			AlchemicalHydra.status = "Switching Ring";
        			ctx.log("Switching Ring");
        			archerRingi.interact(SimpleItemActions.FIRST);
        		}
        		return;
        	}
    		if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item)) {
                ctx.prayers.disableAll();
    			AlchemicalHydra.status = "Clearing Space";
                ctx.log("Clearing Space, Alching");
                Alch();
                AlchemicalHydra.walkBoolean = false;
                ctx.sleep(600);
    		}
    		if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item) && ctx.inventory.populate().filterHasAction("Eat").population(true) >= 1) {
    			ctx.prayers.disableAll();
    			AlchemicalHydra.status = "Clearing Space";
                ctx.log("Clearing Space, Eating Food");
                ctx.inventory.next().interact(SimpleItemActions.CONSUME);
                AlchemicalHydra.walkBoolean = false;
                return;
    		} else if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item) && ctx.inventory.populate().filterHasAction("Bury").population(true) >= 1) {
    			ctx.prayers.disableAll();
    			AlchemicalHydra.status = "Clearing Space";
                ctx.log("Clearing Space, Burying Bone");
                ctx.inventory.next().interact(74);
                AlchemicalHydra.walkBoolean = false;
                return;
    		} else if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty() && !ctx.inventory.canPickupItem(item) && ctx.inventory.populate().filterContains("Saradomin Brew").population(true) >= 1) {
    			ctx.prayers.disableAll();
    			AlchemicalHydra.status = "Clearing Space";
                ctx.log("Clearing Space, Eating Food");
                ctx.inventory.next().interact(SimpleItemActions.CONSUME);
                AlchemicalHydra.walkBoolean = false;
                return;
    		}
    		if (item == null || (item != null && ctx.groundItems.populate().filterContains(lootNames).isEmpty())) {
				AlchemicalHydra.dead = false;
			}
    	}
    }
    
    public static void Alch() {
        int naturerune = ctx.inventory.populate().filter(561).population(true);
    	int firerune = ctx.inventory.populate().filter(554).population(true);
    	SimpleItem runePouch = ctx.inventory.populate().filter(12791).next();
        if (!ctx.inventory.populate().filterContains("Rune platebody").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Rune platebody";
            ctx.log("Alching Rune platebody");
            SimpleItem alch = ctx.inventory.populate().filter(1127).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
        if (!ctx.inventory.populate().filterContains("Mystic fire").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Mystic fire staff";
            ctx.log("Alching Mystic fire staff");
            SimpleItem alch = ctx.inventory.populate().filter(1401).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
        if (!ctx.inventory.populate().filterContains("Mystic water").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Mystic water staff";
            ctx.log("Alching Mystic water staff");
            SimpleItem alch = ctx.inventory.populate().filter(1403).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
        if (!ctx.inventory.populate().filterContains("Mystic robe top (light)").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Mystic robe top (light)";
            ctx.log("Alching Mystic robe top (light)");
            SimpleItem alch = ctx.inventory.populate().filter(4111).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Mystic robe bottom (light)").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Mystic robe bottom (light)";
            ctx.log("Alching Mystic robe bottom (light)");
            SimpleItem alch = ctx.inventory.populate().filter(4113).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Dragon longsword").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Dragon longsword";
            ctx.log("Alching Dragon longsword");
            SimpleItem alch = ctx.inventory.populate().filter(1305).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Dragon med helm").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Dragon med helm";
            ctx.log("Alching Dragon med helm");
            SimpleItem alch = ctx.inventory.populate().filter(1149).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Rune platelegs").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Rune platelegs";
            ctx.log("Alching Rune platelegs");
            SimpleItem alch = ctx.inventory.populate().filter(1079).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Rune plateskirt").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Rune plateskirt";
            ctx.log("Alching Rune plateskirt");
            SimpleItem alch = ctx.inventory.populate().filter(1093).next();
            ctx.magic.castSpellOnItem(1178, alch);
        } 
        if (!ctx.inventory.populate().filterContains("Dragon battleaxe").isEmpty() && ((firerune >= 5 && naturerune >= 1) || runePouch != null)) {
            AlchemicalHydra.status = "Alching Dragon battleaxe";
            ctx.log("Alching Dragon battleaxe");
            SimpleItem alch = ctx.inventory.populate().filter(1377).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    }
}
