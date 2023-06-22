package me.pepsi.xeros.powerredwood;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import simple.api.actions.SimpleItemActions;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;


@ScriptManifest(author = "Pepsiplaya", name = "Redwood Power Woocutter", category = Category.WOODCUTTING, version = "1.0D",
        description = "Powercuts Redwood", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class RedwoodPowerWoodcutter extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {
	
    public String status;
    public boolean started;
    public boolean drop;
    private long startExp;
    public long startTime;
    private long lastAnimation = -1;
    
    @Override
    public boolean onExecute() {
    	ctx.skills.getRealLevel(SimpleSkills.Skill.WOODCUTTING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.WOODCUTTING);
        startTime = System.currentTimeMillis();
		status = "READ INSTRUCTIONS!";
		drop = false;
        return true;
    }

    @Override
    public void onProcess() {
    	SimpleItem redwoodLogs = ctx.inventory.populate().filter(19669).next();
    	SimpleItem birdsNest = ctx.inventory.populate().filter(5075).next();
    	if (redwoodLogs != null) {
    		if (ctx.inventory.inventoryFull()) {
    			drop = true;
    		}
    		if (drop == true) {
    			redwoodLogs.interact(SimpleItemActions.DROP);
        		return;
    		}
    	}
    	if (birdsNest != null) {
    		if (ctx.inventory.inventoryFull()) {
    			drop = true;
    		}
    		if (drop == true) {
    			birdsNest.interact(SimpleItemActions.DROP);
        		return;
    		}
    	}
    	if (birdsNest == null && redwoodLogs == null) {
    		drop = false;
    	}
    	if (ctx.players.getLocal().isAnimating()) {
    		lastAnimation = System.currentTimeMillis();
    	}
    	SimpleGameObject redwood = (SimpleGameObject) ctx.objects.populate().filter(29668).nearest().next();
	    if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && System.currentTimeMillis() > (lastAnimation + 2000)) {
	    	if (redwood != null) {
	    		if (ctx.pathing.distanceTo(redwood) <= 5) {
	                status = "Woodcutting";
	                redwood.interact(502);
                	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 10);
    			}
    		}
        }
    }

    @Override
    public void onTerminate() {
    }
    
	public final String format(final long t) {
	    long s = t;
	    return String.format("%", s);
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
		
		final long wcingExp = ctx.skills.getExperience(SimpleSkills.Skill.WOODCUTTING) - startExp;
		
		 g.setColor(color2);
	     g.fillRect(7, 273, 155, 47);
	     g.setColor(color3);
	     g.setStroke(stroke1);
	     g.drawRect(7, 273, 155, 47);
	     g.setFont(font1);
	     g.setColor(color1);
	     g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	     g.drawString("Exp / Per Hour: " + formatValue(wcingExp) + " / " + formatValue(ctx.paint.valuePerHour((int) wcingExp, startTime)), 10, 300);
	     g.drawString("Status: " + status, 10, 313);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
    
    @Override
	public int loopDuration() {
		return 150;
	}
}