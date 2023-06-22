package me.pepsi.xeros.powermining;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import simple.api.ClientContext;
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
import simple.api.wrappers.SimplePlayer;


@ScriptManifest(author = "Pepsiplaya", name = "Iron Powerminer", category = Category.MINING, version = "1.0D",
        description = "Powermine Iron Ore", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class IronPowerminer extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {
	
    public String status;
    public boolean started;
    private long startExp;
    public long startTime;
    private long lastAnimation = -1;
    
    @Override
    public boolean onExecute() {
    	ctx.skills.getRealLevel(SimpleSkills.Skill.MINING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.MINING);
        startTime = System.currentTimeMillis();
		status = "READ INSTRUCTIONS!";
        return true;
    }

    @Override
    public void onProcess() {
    	SimpleItem ironOre = ctx.inventory.populate().filter(440).shuffle().next();
    	SimpleItem ironOre1= ctx.inventory.populate().filter(440).next();
    	SimpleItem ironOre2 = ctx.inventory.populate().filter(440).reverse().next();
    	SimpleItem ironBar = ctx.inventory.populate().filter(2351).shuffle().next();
    	if (ironOre != null && !ctx.players.getLocal().isAnimating()) {
    		ironOre.interact(SimpleItemActions.DROP);
    	}
    	if (ironOre1 != null && !ctx.players.getLocal().isAnimating()) {
    		ironOre1.interact(SimpleItemActions.DROP);
    	}
    	if (ironOre2 != null && !ctx.players.getLocal().isAnimating()) {
    		ironOre2.interact(SimpleItemActions.DROP);
    	}
    	if (ironBar != null && !ctx.players.getLocal().isAnimating()) {
    		ironBar.interact(SimpleItemActions.DROP);
    	}
		if (getNearbyStaff(ctx) != null) {
			status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName();
			Toolkit.getDefaultToolkit().beep();
		}
    	if (ctx.players.getLocal().isAnimating()) {
    		lastAnimation = System.currentTimeMillis();
    	}
		if ((!ctx.equipment.populate().filter("Dragon pickaxe").isEmpty() || !ctx.equipment.populate().filter("Infernal pickaxe").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 100) {
			ctx.combat.toggleSpecialAttack(true);
		}
		SimpleGameObject ore = (SimpleGameObject) ctx.objects.populate().filter(11364).nearest().next();
		SimpleGameObject ore1 = (SimpleGameObject) ctx.objects.populate().filter(11365).nearest().next();
	    if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && System.currentTimeMillis() > (lastAnimation + 1000)) {
	    	if ((ore != null || ore1 != null) && ironOre == null) {
	    		if (ctx.pathing.distanceTo(ore) <= 5) {
	                status = "Mining";
	                ore.interact("Mine");
                	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 300, 2);
    			} else if (ctx.pathing.distanceTo(ore1) <= 5) {
	                status = "Mining";
	                ore1.interact("Mine");
	                ctx.sleep(600);
	                //ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 300, 2);
    			}
    		} else if (ironOre == null) {
    			status = "Waiting For Ore";
    		}
        }
    }
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
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
		
		final long miningExp = ctx.skills.getExperience(SimpleSkills.Skill.MINING) - startExp;
		
		 g.setColor(color2);
	     g.fillRect(7, 273, 155, 47);
	     g.setColor(color3);
	     g.setStroke(stroke1);
	     g.drawRect(7, 273, 155, 47);
	     g.setFont(font1);
	     g.setColor(color1);
	     g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	     g.drawString("Exp / Per Hour: " + formatValue(miningExp) + " / " + formatValue(ctx.paint.valuePerHour((int) miningExp, startTime)), 10, 300);
	     g.drawString("Status: " + status, 10, 313);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
    
    @Override
    public int loopDuration() {
        return 100;
    }
}