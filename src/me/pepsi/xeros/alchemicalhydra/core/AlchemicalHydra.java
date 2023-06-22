package me.pepsi.xeros.alchemicalhydra.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import me.pepsi.xeros.alchemicalhydra.methods.Banking;
import me.pepsi.xeros.alchemicalhydra.methods.Combat;
import me.pepsi.xeros.alchemicalhydra.methods.Looting;
import me.pepsi.xeros.alchemicalhydra.methods.Potions;
import me.pepsi.xeros.alchemicalhydra.methods.Staff;
import me.pepsi.xeros.alchemicalhydra.methods.Task;
import me.pepsi.xeros.alchemicalhydra.util.GUI;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.events.GraphicsObjectSpawnedEvent;
import simple.api.listeners.SimpleGraphicsObjectSpawnedListener;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleNpc;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Alchemical Hydra", category = Category.MONEYMAKING, version = "1.0D",
        description = "Test", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class AlchemicalHydra extends Script implements SimpleGraphicsObjectSpawnedListener, MouseListener, LoopingScript, SimplePaintable, SimpleMessageListener {
	
	public static String status;
	public static String antibot;
    public long startTime;
    public boolean started;
    private Image x;
    private Image image;
    public static int count;
    public Image staffImage;
    boolean hide = false;
    public static boolean presetLoaded = false;
    public static boolean staff = false;
    public static boolean dead = false;
    public static boolean special = false;
    public static boolean teleport = false;
    public static boolean walkBoolean = false;
    static public boolean onFire;
    public static boolean getTask = false;
    public static boolean weakened = false;
    public static int drops = 0;
    public static int prayer;
    public int kills = 0;
    public static int prayerType;
	public static int eatAt;
	public static int slot;
    public GUI gui;
    Point p;
    public static ArrayList<WorldPoint> fireTiles = new ArrayList<WorldPoint>();
    
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	public static final WorldArea HYDRA = new WorldArea(
			new WorldPoint(1356, 10256, 0),
            new WorldPoint(1378, 10278, 0)
    );
	
	public static final WorldArea OUTSIDE_HYDRA = new WorldArea(
			new WorldPoint(1345, 10255),
			new WorldPoint(1356, 10265)
	);
	
	public static final WorldArea RAIDS_WAITING_AREA = new WorldArea(
			new WorldPoint(3040, 6072),
			new WorldPoint(3027, 6064)
	);
	
	final WorldPoint walk = new WorldPoint(1365, 10266);
	
    @Override
    public boolean onExecute() {
    	if (ctx.combat.autoRetaliate()) {
    		ctx.combat.toggleAutoRetaliate(false);
    	}
    	Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 20 + random;
    	getTask = false;
    	presetLoaded = false;
    	drops = 0;
    	teleport = false;
    	staff = false;
    	walkBoolean = false;
    	status = "Setup GUI";
    	antibot = "No Staff Nearby";
        startTime = System.currentTimeMillis();
        try {
        	image = ImageIO.read(new URL("https://i.imgur.com/dkwjiuq.png"));
        	staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
        	x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
        } catch (IOException e) {
        	ctx.log("ERROR LOADING PAINT");
        }
        try {
        	AlchemicalHydra script = this;
            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
                gui = new GUI(script);
            }});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    @Override
    public void onProcess() {
    	Staff.antiStaff();
    	if (HYDRA.containsPoint(ctx.players.getLocal()) && fireTiles != null && fireTiles.size() >= 1) {
			count += 1;
			for (WorldPoint i : fireTiles) {
				if (ctx.pathing.onTile(i)) {
	            	onFire = true;
	            }
		        if (count >= 70) {
		        	fireTiles.removeAll(fireTiles);
		        	count = 0;
		        }
	        }
		}
    	if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		antibot = "Unknown dialogue open";
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
    	if (!started) {
			return;
		} else {
			SimpleNpc task = ctx.npcs.populate().filter(8605).nearest().next();
			if (getTask && task == null) {
	    		ctx.magic.castHomeTeleport();
	    		ctx.onCondition(() -> HOME.containsPoint(ctx.players.getLocal()), 250, 36);
	    	}
			if (HYDRA.containsPoint(ctx.players.getLocal())) {
	    		Combat.handlePrayers();
	    		Combat.handleEatingFood();
				Combat.doCombat();
	    		Potions.handleDrinkingPrayer();
	    		Potions.rangeHeart();
	    		Potions.rangePotion();
	    		Potions.meleeHeart();
	    		Potions.dropVial();
	    		Looting.Alch();
	    		Looting.Loot();
	    		Staff.getNearbyPlayers();
			} else if (OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal())) {
				Task.getTask();
	    	} else if (RAIDS_WAITING_AREA.containsPoint(ctx.players.getLocal())) {
	    		ctx.prayers.disableAll();
	    		Banking.heal();
	    		Banking.loadPreset();
	    	} else if (HOME.containsPoint(ctx.players.getLocal())) {
	    		ctx.prayers.disableAll();
	    		Banking.heal();
	    		Banking.loadPreset();
	    	} else {
	    		AlchemicalHydra.status = "How'd We Get Here?";
	    		ctx.log("How'd We Get Here? Stopping script for safety");
				ctx.prayers.disableAll();
	    		ctx.stopScript();
	    	}
	    }
    }

    @Override
    public void onTerminate() {
		if (gui != null) {
			gui.onCloseGUI();
		}
    }
	
    private final Color color1 = new Color(255, 255, 255);
    private final Font font1 = new Font("Arial", 1, 10);
    
	@Override
    public void onPaint(Graphics2D g) {
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
		SimpleNpc greenHydra = ctx.npcs.populate().filter(8615).nearest().next();
		SimpleNpc blueHydra = ctx.npcs.populate().filter(8619).nearest().next();
		SimpleNpc redHydra = ctx.npcs.populate().filter(8620).nearest().next();
		SimpleNpc blackHydra = ctx.npcs.populate().filter(8621).nearest().next();
	 	if (!hide) {
			if (image != null) {
		    	g.drawImage(image, 1, 337, null);
		    	g.drawImage(x, 508, 338, null);
		    }
		     g.setFont(font1);
		     g.setColor(color1);
		     Font currentFont = g.getFont();
		     Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.8F);
		     g.setFont(newFont);
		     g.drawString("KILLS: " + kills, 40, 384);
		     g.drawString("KILLS/HR: " + ctx.paint.valuePerHour((int) kills, startTime), 40, 420);
		     if (greenHydra != null) {
		    	 g.drawString("X | Y: " + greenHydra.getLocalX() + " | " +  greenHydra.getLocalY(), 40, 456); 
		     } else if (blueHydra != null) {
		    	 g.drawString("X | Y: " + blueHydra.getLocalX() + " | " +  blueHydra.getLocalY(), 40, 456); 
		     } else if (redHydra != null) {
		    	 g.drawString("X | Y: " + redHydra.getLocalX() + " | " +  redHydra.getLocalY(), 40, 456); 
		     } else if (blackHydra != null) {
		    	 g.drawString("X | Y: " + blackHydra.getLocalX() + " | " +  blackHydra.getLocalY(), 40, 456); 
		     }
		     g.drawString("ANTI-BOT: " + antibot, 240, 384);
		     g.drawString("STATUS: " + status, 240, 420);
		     g.drawString("DROPS: " + drops, 240, 459);
		     Font currentFont1 = g.getFont();
		     Font newFont1 = currentFont1.deriveFont(currentFont1.getSize() * 1.4F);
		     g.setFont(newFont1);
		     g.drawString(ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 335);
		} else {
			if (x != null) {
		    	g.drawImage(x, 508, 338, null);
		    }
		}
    }
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
            if (event.getMessage().contains("Your Alchemical Hydra kill count is:")) {
            	kills = kills + 1;
            } else  if (event.getMessage().contains("The Alchemical Hydra has not been weakened")) {
            	weakened = false;
            } else if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
            } else if (event.getMessage().contains("You can't hit Alchemical Hydra from this position")) {
				walkBoolean = true;
            } else if (event.getMessage().contains("You must have an active Hydra task to enter this cave") || event.getMessage().contains("You have completed your slayer task")) {
				getTask = true;
            } else if (event.getMessage().contains("You need to have used a preset recently")) {
				presetLoaded = false;
			} else if (event.getMessage().contains("You haven't teleported anywhere recently")) {
				teleport = false;
			} else if (event.getMessage().contains("has been temporarily raised")) {
		    	Potions.lastHeart = System.currentTimeMillis();
			} else if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (HYDRA.containsPoint(ctx.players.getLocal())) {
				if (event.getMessage().contains(ctx.players.getLocal().getName()) && !event.getMessage().contains(ctx.players.getLocal().getName() + " has reached") && !event.getMessage().contains(ctx.players.getLocal().getName() + " has joined the party.")) {
					drops = drops + 1;
				}
			}
        }
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + ": " + event.getMessage());
		}
    }
	
	public void setupPrayer(int prayerType) {
		AlchemicalHydra.prayerType = prayerType;
	}

	@Override
	public int loopDuration() {
		return 150;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	    p = e.getPoint();
	    if (close.contains(p) && !hide) {
	        hide = true;
	     } else if (open.contains(p) && hide) {
	       hide = false;
	     }
	}
	
	Rectangle close = new Rectangle(505, 339, 18, 18);
	Rectangle open = new Rectangle(505, 339, 18, 18);
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void onGraphicsObjectSpawned(GraphicsObjectSpawnedEvent object) {
        if (object.getGfxId() >= 1668  && HYDRA.containsPoint(ctx.players.getLocal())) {
        	fireTiles.add(new WorldPoint(object.getLocX(), object.getLocY(), 0));
        }
    }
}