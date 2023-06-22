package me.pepsi.xeros.farming;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleBank;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Rune Dart Maker", category = Category.SMITHING, version = "1.0D",
        description = "Smelts Mith Darts", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class RuneDartMaker extends Script implements SimplePaintable, SimpleMessageListener {
	
    public String status;
	private long startExp;
    public long startTime;
    private long lastAnimation = -1;
    private long darts = 0;
    public boolean staffNearby = false;
    
    public final WorldPoint walk = new WorldPoint(3814, 2831, 0);

    @Override
    public boolean onExecute() {
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.SMITHING);
        startTime = System.currentTimeMillis();
	        return true;
    }
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
    
    void bank() {
    	SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filterContains("Bank").nearest().next();
		SimpleItem runeBar = ctx.bank.populate().filter(2363).next();
		status = "Sleeping";
		ctx.sleep(500, 5000);
		if (!ctx.bank.bankOpen()) {
			status = "Banking";
			if (bank != null) {
				bank.interact(SimpleObjectActions.FIRST);
				ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 8);
			}
		} else {
			if (ctx.inventory.populate().filter(2347).isEmpty()) {
				ctx.bank.depositInventory();
				ctx.onCondition(() -> ctx.inventory.isEmpty(), 250, 2);
				ctx.bank.withdraw(2347, 1);
				ctx.onCondition(() -> !ctx.inventory.populate().filter(2347).isEmpty(), 250, 2);
			} else {
				ctx.bank.depositAllExcept(2347);
			}
			if (runeBar == null) {
				status = "Out Of Supplies!";
				return;
			} else {
				ctx.bank.withdraw(2363, SimpleBank.Amount.ALL);
				ctx.onCondition(() -> !ctx.inventory.populate().filter(2363).isEmpty(), 250, 2);
				ctx.bank.closeBank();
			}
		}
    }

    @Override
    public void onProcess() {
		if (getNearbyStaff(ctx) != null && staffNearby) {
			status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName();
			Toolkit.getDefaultToolkit().beep();
		}
    	if (ctx.players.getLocal().isAnimating()) {
            lastAnimation = System.currentTimeMillis();
        }
        SimpleItem runeBar = (SimpleItem) ctx.inventory.populate().filter(2363).next();
	    if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.populate().filter(2363).isEmpty() && (System.currentTimeMillis() > (lastAnimation + 2000))) {
	        if (runeBar != null) {
	        	SimpleGameObject anvil = (SimpleGameObject) ctx.objects.populate().filter(6150).nearest().next();
	    		status = "Smithing";
	    		ctx.pathing.step(walk);
	    		ctx.onCondition(() -> ctx.players.getLocal().getLocation().equals(walk), 250, 12);
	    		anvil.interact(SimpleObjectActions.SMELT);
	    		ctx.sleep(500, 1000);
	    		ctx.menuActions.sendAction(867, 824, 3, 1123, 824);
	    		lastAnimation = System.currentTimeMillis();
	        }
	    }
	    if (ctx.inventory.populate().filter(2363).isEmpty() || ctx.inventory.populate().filter(2347).isEmpty()) {
	    	bank();
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
		
		final long smithingExp = ctx.skills.getExperience(SimpleSkills.Skill.SMITHING) - startExp;
		
		 g.setColor(color2);
	     g.fillRect(7, 273, 165, 60);
	     g.setColor(color3);
	     g.setStroke(stroke1);
	     g.drawRect(7, 273, 165, 60);
	     g.setFont(font1);
	     g.setColor(color1);
	     g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	     g.drawString("Exp / Per Hour: " + formatValue(smithingExp) + " / " + formatValue(ctx.paint.valuePerHour((int) smithingExp, startTime)), 10, 300);
	     g.drawString("Darts / Per Hour: " + darts + " / " + formatValue(ctx.paint.valuePerHour((int) darts, startTime)), 10, 313);
	     g.drawString("Status: " + status, 10, 326);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
            if (event.getMessage().contains("You make a Rune dart tip.")) {
                darts = darts + 10;
            }
        }
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
}