package me.pepsi.xeros.mithrildragons;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.script.Script;

@ScriptManifest(author = "Pepsiplaya", name = "Mithril Dragons", category = Category.MONEYMAKING, version = "1.0D",
        description = "Mithril Dragons", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class MithrilDragons extends Script {
	
	private static long lastFocused = -1;
	final public static String[] lootNames = { "Large coin bag", "Rune knife", "Resource", "Mithril bar", "Rune javelin", "Blood rune", "Rune dart", "Adamantite bar", "Rune arrow", "Runite bolts", "Dragon full helm", "Draconic visage" };
	public static final WorldArea DRAGON_AREA = new WorldArea(new WorldPoint(1745, 5349), new WorldPoint(1733, 5341));
	final static WorldPoint stand = new WorldPoint(1734, 5342, 0);
	
    @Override
    public boolean onExecute() {
        return true;
    }

    @Override
    public void onProcess() {
    	
    	SimpleNpc mithrilDragon = ctx.npcs.populate().filter(2919).next();

    	if (!DRAGON_AREA.containsPoint(ctx.players.getLocal())) {
    		return;
        }
    	
    	if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
			SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
			if (item != null && DRAGON_AREA.containsPoint(item)) {
				if (ctx.inventory.canPickupItem(item)) {
					item.interact();
					return;
				}
			}
    	} else if (!ctx.pathing.onTile(stand)) {
			ctx.pathing.walkToTile(stand);
		}
    	
    	if (mithrilDragon != null && DRAGON_AREA.containsPoint(mithrilDragon)) {
    		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
			}
    		if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= 20) {
    			ctx.inventory.next().interact(SimpleItemActions.DRINK);
    		}
            if (focusHeart != null && System.currentTimeMillis() > (lastFocused + 430000)) {
                focusHeart.interact(74);
                ctx.sleep(600);
                focusHeart.interact(74);
                lastFocused = System.currentTimeMillis();
            }
    		if (!ctx.pathing.onTile(stand)) {
    			ctx.pathing.walkToTile(stand);
    		} else if (ctx.players.getLocal().getInteracting() == null) {
    			ctx.sleep(1000, 2000);
    			mithrilDragon.interact(SimpleNpcActions.ATTACK);
    		}
		} else {
			ctx.prayers.disableAll();
		}
    }

    @Override
    public void onTerminate() {
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    }
}