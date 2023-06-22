package me.pepsi.xeros.cerberus.core;

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
import java.util.Random;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import me.pepsi.xeros.cerberus.methods.Banking;
import me.pepsi.xeros.cerberus.methods.Combat;
import me.pepsi.xeros.cerberus.methods.Looting;
import me.pepsi.xeros.cerberus.methods.Potions;
import me.pepsi.xeros.cerberus.methods.Staff;
import me.pepsi.xeros.cerberus.methods.Task;
import me.pepsi.xeros.cerberus.util.GUI;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.events.ProjectileSpawnedEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.listeners.SimpleProjectileSpawnedListener;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Cerberus", category = Category.MONEYMAKING, version = "1.2D",
        description = "Kills Cerberus while getting more gold and tasks when needed.", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Cerberus extends Script implements SimpleProjectileSpawnedListener, MouseListener, LoopingScript, SimplePaintable, SimpleMessageListener {
	
	public static String status;
	public static String antibot;
    public long startTime;
    public static boolean started;
    private Image x;
    public Image staffImage;
    private Image image;
    static public boolean rangeProjectile;
	static public boolean mageProjectile;
    boolean hide = false;
    public static boolean presetLoaded = false;
    public static boolean teleport = false;
    public static boolean getTask = false;
    public static boolean staff = false;
    public static int prayer;
    public static int drops = 0;
    public int kills = 0;
    public int deaths = 0;
    public static int prayerType;
	public static int eatAt;
	public static int slot;
	public static boolean special;
	public static boolean dead;
    public GUI gui;
    Point p;
    
    public static final WorldArea RAIDS = new WorldArea(new WorldPoint(1220, 3552), new WorldPoint(1241, 3575));
    
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	public static final WorldArea CERBERUS = new WorldArea(
			new WorldPoint(1223, 1222, 0),
            new WorldPoint(1262, 1259, 0)
    );
	
	public static final WorldArea OUTSIDE_CERBERUS = new WorldArea(
			new WorldPoint(1250, 1240),
			new WorldPoint(1340, 1275)
	);
	
    @Override
    public boolean onExecute() {
    	Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 35 + random;
    	presetLoaded = false;
    	getTask = false;
    	rangeProjectile = false;
		mageProjectile = false;
    	drops = 0;
    	dead = false;
    	teleport = false;
    	status = "Setup GUI";
    	antibot = "No Staff Nearby";
        startTime = System.currentTimeMillis();
        try {
        	image = ImageIO.read(new URL("https://i.imgur.com/V7Kd8bu.png"));
        	staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
        	x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
        } catch (IOException e) {
        	ctx.log("ERROR LOADING PAINT");
        }
        try {
        	Cerberus script = this;
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
    	if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		Cerberus.antibot = "Unknown dialogue open";
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
    	if (!started) {
			return;
		} else {
			if (CERBERUS.containsPoint(ctx.players.getLocal())) {
				Combat.handleEatingFood();
				Potions.handleDrinkingPrayer();
	    		Combat.handlePrayers();
	    		Combat.doCombat();
	    		Looting.alch();
	    		if (dead) {
	    			Looting.loot();
	    			return;
	    		}
	    		Potions.dropVial();
	    		Task.teleport();
			} else if (OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal())) {
	    		presetLoaded = false;
	    		dead = false;
				Task.getTask();
	    	} else if (HOME.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		ctx.prayers.disableAll();
	    		dead = false;
	    		Banking.heal();
	    		Banking.loadPreset();
	    	} else if (RAIDS.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		ctx.prayers.disableAll();
	    		dead = false;
	    		Banking.heal();
	    		Banking.loadPreset();
	    		return;
	    	} else {
	    		status = "How'd We Get Here?";
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
		     g.drawString("DEATHS: " + deaths, 40, 459);
		     g.drawString("KILLS/HR: " + ctx.paint.valuePerHour((int) kills, startTime), 40, 420);
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
            if (event.getMessage().contains("Your Cerberus kill count is:")) {
            	kills += 1;
            	dead = true;
            } else if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
            } else if (event.getMessage().contains("You must have an active cerberus or hellhound task to enter this cave") || event.getMessage().contains("You need a Cerberus or Hellhound") || event.getMessage().contains("You have completed your slayer task,") || event.getMessage().contains("You must have an active cerberus or hellhound task to use this")) {
				getTask = true;
            } else if (event.getMessage().contains("You need to have used a preset recently")) {
				presetLoaded = false;
			} else if (event.getMessage().contains("You haven't teleported anywhere recently")) {
				teleport = false;
			} else if (event.getMessage().contains("Oh dear, you")) {
				Potions.lastOverload = System.currentTimeMillis() - 601000;
				deaths = deaths + 1;
			} else if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (CERBERUS.containsPoint(ctx.players.getLocal())) {
				if (event.getMessage().contains(ctx.players.getLocal().getName()) && !event.getMessage().contains(ctx.players.getLocal().getName() + " has reached") && !event.getMessage().contains(ctx.players.getLocal().getName() + " has joined the party.")) {
					drops = drops + 1;
				}
			}
        }
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + event.getMessage());
		}
    }
	
	public void setupPrayer(int prayerType) {
		Cerberus.prayerType = prayerType;
	}

	@Override
	public int loopDuration() {
		return 250;
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
	public void onProjectileSpawned(ProjectileSpawnedEvent projectile) {
		if (CERBERUS.containsPoint(ctx.players.getLocal())) {
			if (projectile.getProjectileId() == 1245) {
				rangeProjectile = true;
				mageProjectile = false;
			}
			if (projectile.getProjectileId() == 1242) {
				mageProjectile = true;
				rangeProjectile = false;
			}
		}
	}
}