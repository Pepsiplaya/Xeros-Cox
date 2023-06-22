package me.pepsi.xeros.crafting;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.actions.SimpleItemActions;
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


@ScriptManifest(author = "Pepsiplaya", name = "AIO Gem Cutter", category = Category.CRAFTING, version = "1.1D",
        description = "Cutting gems at ::skill", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class AIOGemCutter extends Script implements SimplePaintable, SimpleMessageListener {
	
    public String status;
    private long startLevel;
	private long startExp;
    public long startTime;
    public int gemId;
    public boolean started;
    private long lastAnimation = -1;
    
    public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
    
    public static final WorldArea SKILLING_AREA = new WorldArea(
            new WorldPoint(3700, 3400, 0),
            new WorldPoint(3900, 3600, 0));
    
    public GUI gui;
    
    @Override
    public boolean onExecute() {
    	startLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.CRAFTING);
        startTime = System.currentTimeMillis();
		status = "Setup Gui";
		gemId = -1;
		 try {
	            AIOGemCutter script = this;
	            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
	                gui = new GUI(script);
	            }});

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return true;
    }
    
    void bank() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter("Chisel").isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositInventory();
			ctx.onCondition(() -> ctx.inventory.isEmpty(), 250, 3);
			ctx.bank.withdraw(1755, 1);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1755).isEmpty(), 250, 3);
			ctx.bank.withdraw(gemId, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(gemId).isEmpty(), 250, 3);
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		} else if (bank != null && !ctx.inventory.populate().filter("Chisel").isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(gemId, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(gemId).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankOpal() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1625).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1625, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1625).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankJade() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1627).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1627, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1627).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankSapphire() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1623).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1623, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1623).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankEmerald() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1621).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1621, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1621).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankRuby() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1619).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1619, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1619).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankDiamond() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1617).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1617, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1617).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}
    
    void bankDragonstone() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
		
		if (bank != null && ctx.inventory.populate().filter(1631).isEmpty()) {
			bank.interact(SimpleObjectActions.FIRST);
			status = "Banking";
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
			ctx.bank.depositAllExcept(1755);
			ctx.sleep(500);
			ctx.bank.withdraw(1631, SimpleBank.Amount.ALL);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1631).isEmpty(), 250, 3);
			ctx.bank.closeBank();
			ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		}
	}

    @Override
    public void onProcess() {

    	if (!SKILLING_AREA.containsPoint(ctx.players.getLocal())) {
    		return;
        }
    	
    	if (gemId == -1) {
    		return;
        }
    	
    	if (ctx.players.getLocal().isAnimating()) {
            lastAnimation = System.currentTimeMillis();
        }
    	
    	if (gemId == -2) {
    		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(20325, 25808).nearest().next();
    		
    		if (bank != null && ctx.inventory.populate().filter("Chisel").isEmpty()) {
    			bank.interact(SimpleObjectActions.FIRST);
    			status = "Banking";
    			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 3);
    			ctx.bank.depositInventory();
    			ctx.sleep(500);
    			ctx.bank.withdraw(1755, 1);
    			ctx.sleep(500);
    			ctx.bank.closeBank();
				ctx.sleep(500);
    		}
    		
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 13) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut o").isEmpty()) {
            		bankOpal();
            	}
        		final SimpleItem opal = ctx.inventory.populate().filterContains("Uncut o").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();

        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (opal != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			opal.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 13 && ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 20) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut j").isEmpty()) {
            		bankJade();
            	}
        		final SimpleItem jade = ctx.inventory.populate().filterContains("Uncut j").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();

        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (jade != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			jade.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 20 && ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 27) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut s").isEmpty()) {
            		bankSapphire();
            	}
        		final SimpleItem sapphire = ctx.inventory.populate().filterContains("Uncut s").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();

        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (sapphire != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			sapphire.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 27 && ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 34) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut e").isEmpty()) {
            		bankEmerald();
            	}
        		final SimpleItem emerald = ctx.inventory.populate().filterContains("Uncut e").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();
            	
        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (emerald != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			emerald.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 34 && ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 43) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut r").isEmpty()) {
            		bankRuby();
            	}
        		final SimpleItem ruby = ctx.inventory.populate().filterContains("Uncut r").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();
        		
        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (ruby != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			ruby.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 43 && ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) < 54) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut d").isEmpty()) {
            		bankDiamond();
            	}
        		final SimpleItem diamond = ctx.inventory.populate().filterContains("Uncut d").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();
        		
        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (diamond != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			diamond.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
            
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) >= 55) {
            	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filterContains("Uncut d").isEmpty()) {
            		bankDragonstone();
            	}
        		final SimpleItem dragonstone = ctx.inventory.populate().filterContains("Uncut d").next();
        		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();
        		
        		if (ctx.players.getLocal().getAnimation() == -1) {
        			if (dragonstone != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
        				status = "Cutting Gems";
            			ctx.sleep(500);
            			dragonstone.interact(SimpleItemActions.USE);
            			ctx.sleep(500);
            			chisel1.interact(SimpleItemActions.USE_WITH);
            			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
            			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
        			}
        		}
            }
    	} else {
            SimpleItem gem = (SimpleItem) ctx.inventory.populate().filter(gemId).next();
	    	if (ctx.inventory.populate().filter("Chisel").isEmpty() || ctx.inventory.populate().filter(gemId).isEmpty()) {
	    		bank();
	    	}
	    		final SimpleItem chisel1 = ctx.inventory.populate().filter("Chisel").next();
	            if (ctx.players.getLocal().getAnimation() == -1) {
	        		if (gem != null && chisel1 != null && System.currentTimeMillis() > (lastAnimation + 3000)) {  
	        			status = "Cutting Gems";
	        			gem.interact(SimpleItemActions.USE);
	        			chisel1.interact(SimpleItemActions.USE_WITH);
	        			ctx.sleep(600);
            			if (ctx.widgets.getBackDialogId() == 27510) {
            				ctx.menuActions.sendAction(646, 0, 0, 27523);
            			}
	        			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 14);
	                }
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
		
		final int cachedExp = ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING);
		int catches = 0;
		final long craftingLevels = ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING) - startLevel;
		final long craftingExp = ctx.skills.getExperience(SimpleSkills.Skill.CRAFTING) - startExp;
		
		if (cachedExp != ctx.skills.getRealLevel(SimpleSkills.Skill.CRAFTING)) { 
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
	     g.drawString("Exp / Per Hour: " + formatValue(craftingExp) + " / " + formatValue(ctx.paint.valuePerHour((int) craftingExp, startTime)), 10, 300);
	     g.drawString("Levels / Per Hour: " + craftingLevels + " / " + formatValue(ctx.paint.valuePerHour((int) craftingLevels, startTime)), 10, 313);
	     g.drawString("Status: " + status, 10, 326);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	
    }

    public void setupGems(int gemId) {
        this.gemId = gemId;
    }
    
}