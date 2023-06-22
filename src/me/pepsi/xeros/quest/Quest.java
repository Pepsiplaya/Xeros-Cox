package me.pepsi.xeros.quest;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Quest", category = Category.OTHER, version = "1.0D",
        description = "Helps with quest", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Quest extends Script implements SimplePaintable, SimpleMessageListener {

    @Override
    public boolean onExecute() {
	        return true;
    }

    @Override
    public void onProcess() {
    	SimpleItem bp = ctx.inventory.populate().filter(12926).next();
    	SimpleItem kodai = ctx.inventory.populate().filter(21006).next();
    	SimpleItem book = ctx.inventory.populate().filter(12612).next();
    	SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
    	SimpleItem mageHelm = ctx.inventory.populate().filter(11663).next();
    	SimpleItem rangeHelm = ctx.inventory.populate().filter(11664).next();
    	SimpleItem meleeHelm = ctx.inventory.populate().filter(11665).next();
    	SimpleNpc fmmelee = melee(ctx).nearest().next();
		SimpleNpc melee = fmmelee != null ? fmmelee : melee(ctx).nearest().next();
		SimpleNpc fmrange = range(ctx).nearest().next();
		SimpleNpc range = fmrange != null ? fmrange : range(ctx).nearest().next();
		SimpleNpc fmmage = mage(ctx).nearest().next();
		SimpleNpc mage = fmmage != null ? fmmage : mage(ctx).nearest().next();
		SimpleNpc fmboom = boom(ctx).nearest().next();
		SimpleNpc boom = fmboom != null ? fmboom : boom(ctx).nearest().next();
		SimpleNpc fmboss1 = boss1(ctx).nearest().next();
		SimpleNpc boss1 = fmboss1 != null ? fmboss1 : boss1(ctx).nearest().next();
    	
		handleEatingFood(ctx);
		handleDrinkingPrayer(ctx);
    	if (melee != null) {
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
			}
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
			}
    		if (meleeHelm != null) {
    			meleeHelm.interact(SimpleItemActions.WEAR);
			}
    		if (rapier != null) {
    			rapier.interact(SimpleItemActions.WEAR);
			} else {
				if (ctx.players.getLocal().getInteracting() == null) {
					melee.interact(SimpleNpcActions.ATTACK);
				}
			}
    	} else if (range != null) {
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
			}
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
			}
    		if (rangeHelm != null) {
    			rangeHelm.interact(SimpleItemActions.WEAR);
			} 
    		if (bp != null) {
    			bp.interact(SimpleItemActions.WEAR);
			} else {
				if (ctx.players.getLocal().getInteracting() == null) {
					range.interact(SimpleNpcActions.ATTACK);
				}
			}
    	} else if (mage != null) {
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
			}
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
			}
    		if (mageHelm != null) {
    			mageHelm.interact(SimpleItemActions.WEAR);
			} 
    		if (book != null) {
    			book.interact(SimpleItemActions.WEAR);
			}
    		if (kodai != null) {
    			kodai.interact(SimpleItemActions.WEAR);
			} else {
				if (ctx.players.getLocal().getInteracting() == null) {
					mage.interact(SimpleNpcActions.ATTACK);
				}
			}
    	} else if (boom != null) {
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
			}
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
			}
    		if (mageHelm != null) {
    			mageHelm.interact(SimpleItemActions.WEAR);
			}
    		if (book != null) {
    			book.interact(SimpleItemActions.WEAR);
			}
    		if (kodai != null) {
    			kodai.interact(SimpleItemActions.WEAR);
			} else {
				if (ctx.players.getLocal().getInteracting() == null) {
					boom.interact(SimpleNpcActions.ATTACK);
				}
			}
    	} else {
    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
			}
    		if (mageHelm != null) {
    			mageHelm.interact(SimpleItemActions.WEAR);
			} 
    		if (book != null) {
    			book.interact(SimpleItemActions.WEAR);
			}
    		if (kodai != null) {
    			kodai.interact(SimpleItemActions.WEAR);
			} else {
				if (ctx.players.getLocal().getInteracting() == null && boss1 != null) {
					boss1.interact(SimpleNpcActions.ATTACK);
				}
			}
    	}
    }
    
    public static void handleEatingFood(ClientContext ctx) {
		SimpleItem food = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.health() <= 60 && ctx.combat.health() != 10) {
			food.interact(74);
		}
	}
    
	public static void handleDrinkingPrayer(ClientContext ctx) {
		if (!ctx.inventory.populate().filterContains("Super restore").isEmpty() && ctx.prayers.points() <= 20) {
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
	}
    
	public final static SimpleEntityQuery<SimpleNpc> melee(ClientContext ctx) {
		return ctx.npcs.populate().filter(1282).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> range(ClientContext ctx) {
		return ctx.npcs.populate().filter(1277).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> mage(ClientContext ctx) {
		return ctx.npcs.populate().filter(1284).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> boom(ClientContext ctx) {
		return ctx.npcs.populate().filter(10589).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> boss1(ClientContext ctx) {
		return ctx.npcs.populate().filter(6339).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> boss2(ClientContext ctx) {
		return ctx.npcs.populate().filter(10529).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

    @Override
    public void onTerminate() {
    	
    }
    
	@Override
    public void onPaint(Graphics2D g) {
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
        }
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
}