package me.pepsi.xeros.hunter.falcon;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Falconry", category = Category.HUNTER, version = "1.0D",
        description = "Start script in falconry area with 500k", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Falconty extends Script implements SimplePaintable, SimpleMessageListener {
	
    public String status;
    private static long lastFalcon = -1;
    public boolean started;
    public boolean pickup;
    private long startExp;
    public long startTime;
    public boolean full = false;
    int coins;
    static ClientContext ctx = ClientContext.instance();
    public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
    
    public static final WorldArea FALCONRY = new WorldArea(new WorldPoint(2363, 3575, 0), new WorldPoint(2395, 3625, 0));

    @Override
    public boolean onExecute() {
    	if (ctx.inventory.populate().filter(995).next() != null) {
    		coins = ctx.inventory.populate().filter(995).population(true);
    	}
    	pickup = false;
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.HUNTER);
        startTime = System.currentTimeMillis();
        return true;
    }
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}

    @Override
    public void onProcess() {
    	SimpleNpc mat = ctx.npcs.populate().filter(1340).nearest().next();
    	SimpleNpc interacting = (SimpleNpc) ctx.players.getLocal().getInteracting();
    	SimpleNpc k1 = spotted().nearest().next();
		SimpleNpc spotted = k1 != null ? k1 : spotted().nearest().next();
		SimpleNpc k3 = dark().nearest().next();
		SimpleNpc dark = k3 != null ? k3 : dark().nearest().next();
		SimpleNpc k5 = dashing().nearest().next();
		SimpleNpc dashing = k5 != null ? k5 : dashing().nearest().next();
    	SimpleItem falcon = ctx.inventory.populate().filter(10024).next();
    	if (!ctx.inventory.populate().filter("Bones").isEmpty()) {
			ctx.log("Burying bones");
			status = "Burying bones";
			ctx.inventory.next().interact(SimpleItemActions.CONSUME);
		}
    	if (FALCONRY.containsPoint(ctx.players.getLocal())) {
    		if (lastFalcon <= System.currentTimeMillis() - 10000) { // here make it so falcon isn't instaltly shop
    			pickup = false;
    		}
    		if (interacting != null) {
    			interacting.interact(20);
    			if (!ctx.pathing.inMotion()) {
    				ctx.log("Retrieving falcon");
            		status = "Retrieving falcon";
    			}
    		}
    		if (ctx.players.getLocal().getAnimation() == 827) {
    			ctx.sleep(1000);
    			pickup = false;
    		}
        	if (!ctx.equipment.populate().filter(10024).isEmpty() || falcon != null) {
        		if (falcon != null) {
        			falcon.interact(454);
        			ctx.log("Equipping falcon");
        			status = "Equipping falcon";
        		}
        		if (ctx.skills.getRealLevel(SimpleSkills.Skill.HUNTER) >= 43 && ctx.skills.getRealLevel(SimpleSkills.Skill.HUNTER) < 57 && pickup == false) {
        			if (spotted != null) {
        				spotted.interact(20);
        				lastFalcon = System.currentTimeMillis();
        				ctx.log("Sending falcon");
            			status = "Sending falcon";
        			}
        		} else if (ctx.skills.getRealLevel(SimpleSkills.Skill.HUNTER) >= 57 && ctx.skills.getRealLevel(SimpleSkills.Skill.HUNTER) < 69 && pickup == false) {
        			if (dark != null) {
        				dark.interact(20);
        				lastFalcon = System.currentTimeMillis();
        				ctx.log("Sending falcon");
            			status = "Sending falcon";
        			}
        		} else if (ctx.skills.getRealLevel(SimpleSkills.Skill.HUNTER) >= 69 && pickup == false) {
        			if (dashing != null) {
        				dashing.interact(20);
        				lastFalcon = System.currentTimeMillis();
        				ctx.log("Sending falcon");
            			status = "Sending falcon";
        			}
        		}
        	} else {
        		coins = ctx.inventory.populate().filter(995).population(true);
        		if (mat != null && coins >= 500000 && !ctx.dialogue.dialogueOpen() && lastFalcon <= System.currentTimeMillis() - 60000) {
        			mat.interact(20);
        			lastFalcon = System.currentTimeMillis();
        			ctx.log("Renting falcon");
        			status = "Renting falcon";
            		ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 4882, 250, 3);
        		}
            	if (ctx.widgets.getBackDialogId() == 4882) {
            		ctx.dialogue.clickContinue();
            	} else if (ctx.widgets.getBackDialogId() == 2459) {
            		ctx.menuActions.sendAction(315, 0, 0, 2461);
            	}
        	}
    		if (getNearbyStaff(ctx) != null) {
    			status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName();
    			Toolkit.getDefaultToolkit().beep();
    		}
    	}
    }
    
	public final static SimpleEntityQuery<SimpleNpc> spotted() {
		return ctx.npcs.populate().filter(5531).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> dark() {
		return ctx.npcs.populate().filter(5532).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> dashing() {
		return ctx.npcs.populate().filter(5533).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}

    @Override
    public void onTerminate() {
    }
    
	public final String formatValue(final long l) {
	    return (l > 1_000_000) ? String.format("%.2fm", ((double) l / 1_000_000))
	           : (l > 1000) ? String.format("%.1fk", ((double) l / 1000)) 
	           : l + "";
	}
	
    private final Color color1 = new Color(255, 255, 255);
    private final Font font1 = new Font("Arial", 1, 10);
    private final Color color2 = new Color(29, 3, 3, 94);
    private final Color color3 = new Color(0, 0, 0);
    private final BasicStroke stroke1 = new BasicStroke(1);
    
	@Override
    public void onPaint(Graphics2D g) {
		
		final long hunterExp = ctx.skills.getExperience(SimpleSkills.Skill.HUNTER) - startExp;
		
		g.setColor(color2);
	    g.fillRect(7, 273, 155, 47);
	    g.setColor(color3);
	    g.setStroke(stroke1);
	    g.drawRect(7, 273, 155, 47);
	    g.setFont(font1);
	    g.setColor(color1);
	    g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	    g.drawString("Exp / Per Hour: " + formatValue(hunterExp) + " / " + formatValue(ctx.paint.valuePerHour((int) hunterExp, startTime)), 10, 300);
	    g.drawString("Status: " + status, 10, 313);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
}