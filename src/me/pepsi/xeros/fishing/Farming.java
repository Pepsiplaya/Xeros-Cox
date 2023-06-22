package me.pepsi.xeros.fishing;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.actions.SimpleItemActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Torstol Farmer", category = Category.FARMING, version = "1.1D",
        description = "Farms Torstol", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Farming extends Script implements SimplePaintable, SimpleMessageListener {
	
	private boolean faladorPlanted;
	private boolean catherbyPlanted;
	private boolean ardougnePlanted;
	private boolean morytaniaPlanted;
	private boolean faladorGathered;
	private boolean catherbyGathered;
	private boolean ardougneGathered;
	private boolean morytaniaGathered;
	private boolean start;
	
	public static final WorldArea FALADOR = new WorldArea(
            new WorldPoint(3040, 3290, 0),
            new WorldPoint(3070, 3320, 0)
    );
	
	public static final WorldArea CATHERBY = new WorldArea(
            new WorldPoint(2800, 3450, 0),
            new WorldPoint(2830, 3480, 0)
    );
	
	public static final WorldArea ARDOUGNE = new WorldArea(
            new WorldPoint(2650, 3360, 0),
            new WorldPoint(2680, 3390, 0)
    );
	
	public static final WorldArea MORYTANIA = new WorldArea(
            new WorldPoint(3590, 3510, 0),
            new WorldPoint(3610, 3550, 0)
    );

    @Override
    public boolean onExecute() {
    	faladorPlanted = false;
    	catherbyPlanted = false;
    	ardougnePlanted = false;
    	morytaniaPlanted = false;
    	faladorGathered = false;
    	catherbyGathered = false;
    	ardougneGathered = false;
    	morytaniaGathered = false;
    	start = true;
        return true;
    }

    @Override
    public void onProcess() {
    	SimpleGameObject patch1 = (SimpleGameObject) ctx.objects.populate().filter(8150).nearest().next();
    	SimpleGameObject patch2 = (SimpleGameObject) ctx.objects.populate().filter(8151).nearest().next();
    	SimpleGameObject patch3 = (SimpleGameObject) ctx.objects.populate().filter(8152).nearest().next();
    	SimpleGameObject patch4 = (SimpleGameObject) ctx.objects.populate().filter(8153).nearest().next();
    	SimpleItem seed = ctx.inventory.populate().filter(5304).next();
    	SimpleItem torstol = ctx.inventory.populate().filter(219).next();
    	SimpleNpc farmer = ctx.npcs.populate().filter(5730).nearest().next();
    	SimpleNpc tele = ctx.npcs.populate().filter(3247).nearest().next();
    	
    	if (FALADOR.containsPoint(ctx.players.getLocal())) {
    		if (morytaniaGathered || morytaniaPlanted) {
    			morytaniaGathered = false;
    			morytaniaPlanted = false;
    		}
    		if (start) {
    			if (patch1 != null && seed != null && !faladorGathered) {
        			patch1.interact(502);
        			ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2282, 250, 24);
        			ctx.sleep(600);
        			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 70);
            	}
    			if (!faladorPlanted) {
    				faladorGathered = true;
    				seed.interact(SimpleItemActions.USE);
            		ctx.sleep(600);
            		patch1.interact(62);
            		ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2291, 250, 70);
    			}
        		if (ctx.players.getLocal().getAnimation() == 2291) {
        			faladorPlanted = true;
        		}
        		if (faladorPlanted && !ctx.players.getLocal().isAnimating() && patch1 != null && seed != null) {
            		if (torstol != null) {
            			torstol.interact(SimpleItemActions.USE);
            			ctx.sleep(600);
            			if (farmer != null) {
            				farmer.interact(582);
            				ctx.onCondition(() -> ctx.inventory.populate().filter(219).isEmpty(), 250, 24);
            			}
            		} else {
            			if (ctx.widgets.getOpenInterfaceId() != 45303) {
            				tele.interact(20);
            				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 45303, 250, 24);
            			} else {
            				ctx.menuActions.sendAction(10000, 0, 0, 45354);
            				ctx.onCondition(() -> CATHERBY.containsPoint(ctx.players.getLocal()), 250, 24);
            			}
            		}
        		}
    		}
    	} else if (CATHERBY.containsPoint(ctx.players.getLocal())) {
    		if (faladorGathered || faladorPlanted) {
    			faladorGathered = false;
    			faladorPlanted = false;
    		}
    		if (start) {
    			start = false;
    		}
    		if (patch2 != null && seed != null && !catherbyGathered) {
    			patch2.interact(502);
    			ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2282, 250, 24);
    			ctx.sleep(600);
    			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 70);
        	}
			if (!catherbyPlanted) {
				catherbyGathered = true;
				seed.interact(SimpleItemActions.USE);
        		ctx.sleep(600);
        		patch2.interact(62);
        		ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2291, 250, 70);
			}
    		if (ctx.players.getLocal().getAnimation() == 2291) {
    			catherbyPlanted = true;
    		}
    		if (catherbyPlanted && !ctx.players.getLocal().isAnimating() && patch2 != null && seed != null) {
        		if (torstol != null) {
        			torstol.interact(SimpleItemActions.USE);
        			ctx.sleep(600);
        			if (farmer != null) {
        				farmer.interact(582);
        				ctx.onCondition(() -> ctx.inventory.populate().filter(219).isEmpty(), 250, 24);
        			}
        		} else {
        			if (ctx.widgets.getOpenInterfaceId() != 45303) {
        				tele.interact(20);
        				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 45303, 250, 24);
        			} else {
        				ctx.menuActions.sendAction(10000, 0, 0, 45362);
        				ctx.onCondition(() -> ARDOUGNE.containsPoint(ctx.players.getLocal()), 250, 24);
        			}
        		}
    		}
    	} else if (ARDOUGNE.containsPoint(ctx.players.getLocal())) {
    		if (catherbyGathered || catherbyPlanted) {
    			catherbyGathered = false;
    			catherbyPlanted = false;
    		}
    		if (patch3 != null && seed != null && !ardougneGathered) {
    			patch3.interact(502);
    			ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2282, 250, 24);
    			ctx.sleep(600);
    			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 70);
        	}
			if (!ardougnePlanted) {
				ardougneGathered = true;
				seed.interact(SimpleItemActions.USE);
        		ctx.sleep(600);
        		patch3.interact(62);
        		ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2291, 250, 70);
			}
    		if (ctx.players.getLocal().getAnimation() == 2291) {
    			ardougnePlanted = true;
    		}
    		if (ardougnePlanted && !ctx.players.getLocal().isAnimating() && patch3 != null && seed != null) {
        		if (torstol != null) {
        			torstol.interact(SimpleItemActions.USE);
        			ctx.sleep(600);
        			if (farmer != null) {
        				farmer.interact(582);
        				ctx.onCondition(() -> ctx.inventory.populate().filter(219).isEmpty(), 250, 24);
        			}
        		} else {
        			if (ctx.widgets.getOpenInterfaceId() != 45303) {
        				tele.interact(20);
        				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 45303, 250, 24);
        			} else {
        				ctx.menuActions.sendAction(10000, 0, 0, 45370);
        				ctx.onCondition(() -> MORYTANIA.containsPoint(ctx.players.getLocal()), 250, 24);
        			}
        		}
    		}
    	} else if (MORYTANIA.containsPoint(ctx.players.getLocal())) {
    		if (ardougneGathered || ardougnePlanted) {
    			ardougneGathered = false;
    			ardougnePlanted = false;
    		}
    		if (patch4 != null && seed != null && !morytaniaGathered) {
    			patch4.interact(502);
    			ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2282, 250, 24);
    			ctx.sleep(600);
    			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 70);
        	}
			if (!morytaniaPlanted) {
				morytaniaGathered = true;
				seed.interact(SimpleItemActions.USE);
        		ctx.sleep(600);
        		patch4.interact(62);
        		ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == 2291, 250, 70);
			}
    		if (ctx.players.getLocal().getAnimation() == 2291) {
    			morytaniaPlanted = true;
    		}
    		if (morytaniaPlanted && !ctx.players.getLocal().isAnimating() && patch4 != null && seed != null) {
        		if (torstol != null) {
        			torstol.interact(SimpleItemActions.USE);
        			ctx.sleep(600);
        			if (farmer != null) {
        				farmer.interact(582);
        				ctx.onCondition(() -> ctx.inventory.populate().filter(219).isEmpty(), 250, 24);
        			}
        		} else {
        			if (ctx.widgets.getOpenInterfaceId() != 45303) {
        				tele.interact(20);
        				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 45303, 250, 24);
        			} else {
        				ctx.menuActions.sendAction(10000, 0, 0, 45346);
        				ctx.onCondition(() -> FALADOR.containsPoint(ctx.players.getLocal()), 250, 24);
        			}
        		}
    		}
    	}
    }

    @Override
    public void onTerminate() {
    }
    
	@Override
    public void onPaint(Graphics2D g) {
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
            if (event.getMessage().contains("Torstol at Falador has finished growing")) {
            	start = true;
            }
    	}
    }
}