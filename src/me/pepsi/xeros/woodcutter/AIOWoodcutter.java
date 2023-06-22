package me.pepsi.xeros.woodcutter;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import javax.swing.SwingUtilities;


@ScriptManifest(author = "Pepsiplaya", name = "AIO Woodcutter", category = Category.WOODCUTTING, version = "1.1D",
        description = "Woodcut On Skilling Island Till 99. Make sure to have an axe equipped", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class AIOWoodcutter extends Script implements SimplePaintable, SimpleMessageListener {
	
    public String status;
    public boolean started;
    private long startExp;
    public long startTime;
    public int[] treeId;
    public boolean staffNearby = false;
    public boolean full = false;
    private long lastAnimation = -1;
    
    public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
    
    public GUI gui;
    
    @Override
    public boolean onExecute() {
    	ctx.skills.getRealLevel(SimpleSkills.Skill.WOODCUTTING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.WOODCUTTING);
        startTime = System.currentTimeMillis();
		status = "READ INSTRUCTIONS!";
        try {
            AIOWoodcutter script = this;
            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
                gui = new GUI(script);
            }});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
    
    void bank() {
    	SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filterContains("Bank").nearest().next();
		if (bank != null) {
			status = "Banking";
			bank.interact(SimpleObjectActions.FIRST);
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 24);
			ctx.bank.depositAllExcept("axe");
			ctx.sleep(500);
			ctx.bank.closeBank();
			full = false;
		}
	}
    

    @Override
    public void onProcess() {
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
        if (ctx.inventory.inventoryFull() || full) {
        	status = "Sleeping";
        	ctx.sleep(100, 5000);
            bank();
        }
        SimpleGameObject tree = (SimpleGameObject) ctx.objects.populate().filter(treeId).nearest().next();
        if (tree != null && !ctx.inventory.inventoryFull()  && System.currentTimeMillis() > (lastAnimation + 2000)) {
            if (ctx.pathing.distanceTo(tree) <= 20) {
            	status = "Sleeping";
                ctx.sleep(100, 3000);
            	status = "Chopping";
                tree.interact(SimpleObjectActions.CHOP_DOWN);
                ctx.onCondition(() -> ctx.players.getLocal().getAnimation() != 867, 250, 10);
                lastAnimation = System.currentTimeMillis();
                full = false;
            } else {
				status = "Sleeping";
    			ctx.sleep(3000, 15000);
    			return;
            }
        }
    }

    @Override
    public void onTerminate() {
        if (gui != null) {
            gui.onCloseGUI();
        }
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

    public void setupTrees(int[] treeId) {
        this.treeId = treeId;
    }
}