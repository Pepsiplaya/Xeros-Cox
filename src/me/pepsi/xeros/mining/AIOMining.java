package me.pepsi.xeros.mining;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.wrappers.SimpleWallObject;


@ScriptManifest(author = "Pepsiplaya", name = "AIO Mining", category = Category.MINING, version = "1.5D",
        description = "Mining On Skilling Island Till 99. Make sure to have a pickaxe equipped", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class AIOMining extends Script implements SimplePaintable, SimpleMessageListener {
	
	public String status;
	public Image staffImage;
    public boolean started;
    public boolean full = false;
    public long startExp;
    public long startTime;
    public int oreId;
    public boolean nearbyPlayers = false;
    public long lastAnimation = -1;
    
    public static boolean staff = false;
    
    final static WorldPoint mine = new WorldPoint(3010, 9722, 0);
    
    public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
    
    public static final WorldArea SKILLING_AREA = new WorldArea(
            new WorldPoint(3700, 3400, 0),
            new WorldPoint(3900, 3600, 0));
    
    public static final WorldArea AMETHYST_MINE = new WorldArea(
            new WorldPoint(3000, 9690, 0),
            new WorldPoint(3030, 9730, 0));
    
    public GUI gui;
    
    @Override
    public boolean onExecute() {
    	staff = false;
    	full = false;
    	ctx.skills.getRealLevel(SimpleSkills.Skill.MINING);
    	startExp = ctx.skills.getExperience(SimpleSkills.Skill.MINING);
        startTime = System.currentTimeMillis();
		status = "Setup GUI!";
		try {
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		try {
            AIOMining script = this;
            SwingUtilities.invokeLater(new Runnable() {
            	@Override
            	public void run() {
            		gui = new GUI(script);
            	}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    void bank() {
    	SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filterContains("Bank").nearest().next();
    	status = "Banking";
    	if (bank != null) {
			bank.interact(SimpleObjectActions.FIRST);
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 24);
		}
    	if (ctx.bank.bankOpen()) {
    		ctx.bank.depositAllExcept("pickaxe");
    		ctx.sleep(500);
    		ctx.bank.closeBank();
    		full = false;
    	}
	}
    
    void amethystBank() {
		SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filter(4483).nearest().next();
		status = "Banking";
		if (!ctx.bank.bankOpen() && bank != null) {
			bank.interact(SimpleObjectActions.USE);
			ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 30);
		}
		if (ctx.inventory.populate().filter(1755).isEmpty()) {
			ctx.bank.withdraw(1755, 1);
			ctx.onCondition(() -> !ctx.inventory.populate().filter(1755).isEmpty(), 250, 3);
		}
		ctx.bank.depositAllExcept(1755);
		ctx.sleep(500);
		ctx.bank.closeBank();
		ctx.onCondition(() -> ctx.bank.closeBank(), 250, 3);
		full = false;
	}

    @Override
    public void onProcess() {
    	antiStaff();
    	if (oreId == -1) {
    		return;
    	}
    	if (ctx.players.getLocal().isAnimating()) {
    		lastAnimation = System.currentTimeMillis();
    	}
    	if (oreId == -2 && AMETHYST_MINE.containsPoint(ctx.players.getLocal())) {
    		/*SimpleItem talisman = ctx.inventory.populate().filter(681).next();
        	SimpleItem coin = ctx.inventory.populate().filter(11180).next();
        	SimpleItem clue = ctx.inventory.populate().filterContains("Clue").next();
        	if (coin != null) {
        		coin.interact(SimpleItemActions.DROP);
        	} else if (clue != null) {
        		clue.interact(SimpleItemActions.DROP);
        	} else if (talisman != null) {
        		talisman.interact(SimpleItemActions.DROP);
        	}*/
    		int amethystCount = ctx.inventory.populate().filter(21347).population(true);
    		SimpleWallObject  ore = (SimpleWallObject ) ctx.objects.populate().filter("Crystals").nearest().next();
    		if ((ctx.inventory.inventoryFull() || full) && amethystCount <= 15) {
    			amethystBank();
    		}
            if (full || ctx.inventory.inventoryFull()) {
            	if (getnearbyPlayers() != null) {
            		status = "Sleeping";
                	ctx.sleep(1000, 10000);
            	}
            	status = "Chiseling";
            	SimpleItem chisel = ctx.inventory.populate().filter(1755).next();
            	SimpleItem amethyst = ctx.inventory.populate().filter(21347).peekNext();
            	if (amethyst != null) {
            		amethyst.interact(SimpleItemActions.USE_WITH);
            		chisel.interact(SimpleItemActions.USE);
                	if (ctx.widgets.getBackDialogId() == 27510) {
                		ctx.menuActions.sendAction(646, -1, -1, 27532);
                		ctx.onCondition(() -> amethyst == null, 1000, 28);
                	}
            	}
            }
            if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && System.currentTimeMillis() > (lastAnimation + 3000)) {
                if (ore != null) {
                	if (getnearbyPlayers() != null) {
                		status = "Sleeping";
                    	ctx.sleep(1000, 10000);
                	}
                	if (!ctx.pathing.onTile(mine)) {
                		ctx.pathing.walkToTile(mine);
                	} else {
                		if ((!ctx.equipment.populate().filter("Dragon pickaxe").isEmpty() || !ctx.equipment.populate().filter("Infernal pickaxe").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 100) {
                			ctx.sleep(1000, 5000);
                			ctx.combat.toggleSpecialAttack(true);
                		}
                		status = "Mining";
                    	ore.interact("Mine");
                    	ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
                    	if (ctx.players.getLocal().isAnimating()) {
                    		lastAnimation = System.currentTimeMillis();
                    	}
                    	full = false;
                	}
                }
            }
    	} else if (oreId == 2005) {
    		if (full || ctx.inventory.inventoryFull()) {
    			status = "Sleeping";
	        	ctx.sleep(100, 5000);
	        	bank();
	        }
	        SimpleGameObject ore = (SimpleGameObject) ctx.objects.populate().filter("Rune Essence").nearest().next();
	        if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && System.currentTimeMillis() > (lastAnimation + 2000)) {
	    		if (ore != null) {
	    			if (ctx.pathing.distanceTo(ore) <= 40) {
	                	status = "Sleeping";
	                	ctx.sleep(100, 5000);
	                	status = "Mining";
	            		if ((!ctx.equipment.populate().filter("Dragon pickaxe").isEmpty() || !ctx.equipment.populate().filter("Infernal pickaxe").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 100) {
	            			ctx.sleep(1000, 5000);
	            			ctx.combat.toggleSpecialAttack(true);
	            		}
	                	ore.interact("Mine");
                		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 10);
                		lastAnimation = System.currentTimeMillis();
                		full = false;
    				} else {
    					status = "Sleeping";
	    				ctx.sleep(2000, 7000);
	    				return;
    				}
    			}
        	}
    	} else {
	        if (full || ctx.inventory.inventoryFull()) {
	        	ctx.sleep(100, 3000);
	        	bank();
	        }
	        SimpleGameObject ore = (SimpleGameObject) ctx.objects.populate().filter(oreId).nearest().next();
	        if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && System.currentTimeMillis() > (lastAnimation + 2000)) {
	    		if (ore != null) {
	    			if (ctx.pathing.distanceTo(ore) <= 40) {
	                	status = "Sleeping";
	                	ctx.sleep(100, 5000);
	                	status = "Mining";
	            		if ((!ctx.equipment.populate().filter("Dragon pickaxe").isEmpty() || !ctx.equipment.populate().filter("Infernal pickaxe").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 100) {
	            			ctx.sleep(1000, 5000);
	            			ctx.combat.toggleSpecialAttack(true);
	            		}
	                	ore.interact("Mine");
                		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 10);
                		lastAnimation = System.currentTimeMillis();
                		full = false;
    				} else {
    					status = "Sleeping";
	    				ctx.sleep(5000, 25000);
	    					return;
    				}
    			}
        	}
        }
    }
    
    void antiStaff() {
    	if (getNearbyStaff() != null) {
    		staff = true;
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!HOME_AREA.containsPoint(ctx.players.getLocal())) {
    			Toolkit.getDefaultToolkit().beep();
    		}
    	} else {
    		staff = false;
    	}
    }
    
    SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
    
    SimplePlayer getnearbyPlayers() {
    	if (ctx.players.nearest().next() != null) {
    		if (ctx.players.nearest().next().getName() == ctx.players.getLocal().getName()) {
    			return null;
    		} else {
    			return ctx.players.nearest().next();	
    		}
    	} else {
    		return null;
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
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
		
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
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You have run out of inventory space.")) {
				full = true;
			} else if (event.getMessage().contains("You have no more free slots")) {
				full = true;
			}
		}
    	if (event.getMessageType() == 2 && event.getMessage().toLowerCase().contains("eva")) {
			Toolkit.getDefaultToolkit().beep();
		}
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
		}
    }

    public void setupOres(int oreId) {
        this.oreId = oreId;
    }
}