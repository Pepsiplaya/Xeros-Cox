package me.pepsi.xeros.smithing;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleBank;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;

import javax.swing.SwingUtilities;


@ScriptManifest(author = "Pepsiplaya", name = "AIO Smelter", category = Category.SMITHING, version = "1.0D",
        description = "Smelts at ::skill", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class AIOSmelter extends Script implements SimplePaintable, SimpleMessageListener {
	
    public String status;
    private long startLevel;
	private long startExp;
    public long startTime;
    public int oreId;
    public int oreId2;
    public int itemQuantity;
    public int itemQuantity2;
    public int data;
    public boolean started;
    private long lastAnimation = -1;
    
    public static final WorldArea SKILLING_AREA = new WorldArea(
            new WorldPoint(3700, 3400, 0),
            new WorldPoint(3900, 3600, 0));
    
    public GUI gui;
    
    @Override
    public boolean onExecute() {
    	startLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.SMITHING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.SMITHING);
        startTime = System.currentTimeMillis();
		status = "Setup Gui";
		oreId = -1;
		 try {
	            AIOSmelter script = this;
	            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
	                gui = new GUI(script);
	            }});

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return true;
    }
    
    void bank() {
    	SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filterContains("Bank").nearest().next();
		SimpleItem ore = ctx.inventory.populate().filter(oreId).next();
		if (bank != null) {
			bank.interact(SimpleObjectActions.USE);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 8);
		    ctx.bank.depositInventory();
		    ctx.onCondition(() -> ctx.inventory.isEmpty(), 250, 8);
		    ctx.bank.withdraw(oreId, itemQuantity);
		    ctx.onCondition(() -> ctx.inventory.contains(ore), 250, 1);
			ctx.bank.withdraw(oreId2, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> ctx.inventory.inventoryFull(), 250, 2);
			ctx.bank.closeBank();
		}
	}
    
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}

    @Override
    public void onProcess() {
		if (getNearbyStaff(ctx) != null) {
			status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName();
			Toolkit.getDefaultToolkit().beep();
		}
    	if (oreId == -1) {
    		return;
        }
    	if (ctx.players.getLocal().isAnimating()) {
            lastAnimation = System.currentTimeMillis();
        }
    	SimpleItem ore = ctx.inventory.populate().filter(oreId).next();
    	SimpleItem ore2 = ctx.inventory.populate().filter(oreId2).next();
    	
	    if (ore == null || ore2 == null) {
	    	bank();
	    }
	    if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.populate().filter(oreId).isEmpty() && !ctx.inventory.populate().filter(oreId2).isEmpty() && System.currentTimeMillis() > (lastAnimation + 3300)) {
	    	SimpleGameObject furnace = (SimpleGameObject) ctx.objects.populate().filter(16469).nearest().next();
	    	SimpleGameObject furnace2 = (SimpleGameObject) ctx.objects.populate().filter(2030).nearest().next();
	        if (furnace != null) {
	    		status = "Smithing";
	    		furnace.interact(SimpleObjectActions.SMELT);
	    		ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    		ctx.menuActions.sendAction(646, 0, 27544, 0);
	    		lastAnimation = System.currentTimeMillis();
	        } else if (furnace2 != null) {
	    		status = "Smithing";
	    		furnace2.interact(SimpleObjectActions.SMELT);
	    		ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    		ctx.menuActions.sendAction(646, 0, 0, 27544);
	    		lastAnimation = System.currentTimeMillis();
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
		
		final int cachedExp = ctx.skills.getRealLevel(SimpleSkills.Skill.SMITHING);
		int catches = 0;
		final long smithingLevels = ctx.skills.getRealLevel(SimpleSkills.Skill.SMITHING) - startLevel;
		final long smithingExp = ctx.skills.getExperience(SimpleSkills.Skill.SMITHING) - startExp;
		
		if (cachedExp != ctx.skills.getRealLevel(SimpleSkills.Skill.SMITHING)) { 
			catches = catches + 1;
		}
		
		 g.setColor(color2);
	     g.fillRect(7, 273, 155, 60);
	     g.setColor(color3);
	     g.setStroke(stroke1);
	     g.drawRect(7, 273, 155, 60);
	     g.setFont(font1);
	     g.setColor(color1);
	     g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	     g.drawString("Exp / Per Hour: " + formatValue(smithingExp) + " / " + formatValue(ctx.paint.valuePerHour((int) smithingExp, startTime)), 10, 300);
	     g.drawString("Levels / Per Hour: " + smithingLevels + " / " + formatValue(ctx.paint.valuePerHour((int) smithingLevels, startTime)), 10, 313);
	     g.drawString("Status: " + status, 10, 326);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}	
    }

    public void setupOres(int oreId) {
        this.oreId = oreId;
    }
    
    public void setupOres2(int oreId2) {
        this.oreId2 = oreId2;
    }
    
    public void setupQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    public void setupQuantity2(int itemQuantity2) {
        this.itemQuantity2 = itemQuantity2;
    }
    
    public void setupData(int data) {
        this.data = data;
    }
    
}