package me.pepsi.xeros.powerwoodcutter;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Power Cutter", category = Category.WOODCUTTING, version = "1.1D",
        description = "Power Cutter", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class PowerCutter extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {
	
    public String status;
    public boolean started;
    private long startExp;
    public long startTime;
    public boolean staffNearby = false;
    public boolean full = false;
    private long lastAnimation = -1;
    
    @Override
    public boolean onExecute() {
    	ctx.skills.getRealLevel(SimpleSkills.Skill.WOODCUTTING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.WOODCUTTING);
        startTime = System.currentTimeMillis();
		status = "READ INSTRUCTIONS!";
        return true;
    }

    @Override
    public void onProcess() {
    	SimpleItem logs = ctx.inventory.populate().filter(1519).next();
    	SimpleItem logsRandom = ctx.inventory.populate().shuffle().filter(1519).next();
    	if (ctx.players.getLocal().isAnimating()) {
    		lastAnimation = System.currentTimeMillis();
    	}
		if (getNearbyStaff(ctx) != null && staffNearby) {
			status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName();
			Toolkit.getDefaultToolkit().beep();
		}
		if ((!ctx.equipment.populate().filter("Dragon axe").isEmpty() || !ctx.equipment.populate().filter("Infernal axe").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 100) {
			ctx.combat.toggleSpecialAttack(true);
		}
        if (ctx.inventory.inventoryFull() || full || (!ctx.players.getLocal().isAnimating() && logs != null)) {
        	if (logs != null) {
    			if (logsRandom != null) {
    				logsRandom.interact(SimpleItemActions.DROP);
    			}
    		}
        }
        SimpleGameObject tree1 = (SimpleGameObject) ctx.objects.populate().filter(10831).nearest().next();
        SimpleGameObject tree2 = (SimpleGameObject) ctx.objects.populate().filter(10829).nearest().next();
        SimpleGameObject tree3 = (SimpleGameObject) ctx.objects.populate().filter(10819).nearest().next();
        SimpleGameObject tree4 = (SimpleGameObject) ctx.objects.populate().filter(10833).nearest().next();
        if ((tree1 != null || tree2 != null || tree3 != null || tree4 != null) && !ctx.inventory.inventoryFull()  && System.currentTimeMillis() > (lastAnimation + 2000) && logs == null) {
            status = "Chopping";
            if (tree1 != null && tree1.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 5) {
            	tree1.interact(SimpleObjectActions.CHOP_DOWN);
                ctx.onCondition(() -> ctx.players.getLocal().getAnimation() != 867, 250, 10);
            } else if (tree2 != null && tree2.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 5) {
            	tree2.interact(SimpleObjectActions.CHOP_DOWN);
            	ctx.onCondition(() -> ctx.players.getLocal().getAnimation() != 867, 250, 10);
            } else if (tree3 != null && tree3.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 5) {
            	tree3.interact(SimpleObjectActions.CHOP_DOWN);
            	ctx.onCondition(() -> ctx.players.getLocal().getAnimation() != 867, 250, 10);
            } else if (tree4 != null && tree4.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 5) {
            	tree4.interact(SimpleObjectActions.CHOP_DOWN);
            	ctx.onCondition(() -> ctx.players.getLocal().getAnimation() != 867, 250, 10);
            }
            lastAnimation = System.currentTimeMillis();
            full = false;
        }
    }
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
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
		
		final long woodcuttingExp = ctx.skills.getExperience(SimpleSkills.Skill.WOODCUTTING) - startExp;
		
		 g.setColor(color2);
	     g.fillRect(7, 273, 155, 47);
	     g.setColor(color3);
	     g.setStroke(stroke1);
	     g.drawRect(7, 273, 155, 47);
	     g.setFont(font1);
	     g.setColor(color1);
	     g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	     g.drawString("Exp / Per Hour: " + formatValue(woodcuttingExp) + " / " + formatValue(ctx.paint.valuePerHour((int) woodcuttingExp, startTime)), 10, 300);
	     g.drawString("Status: " + status, 10, 313);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You have run out of")) {
				full = true;
			}
		}
    }
    
    @Override
	public int loopDuration() {
		return 50;
	}
}