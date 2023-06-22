package me.pepsi.xeros.zulrah.core;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import me.pepsi.xeros.zulrah.methods.Banking;
import me.pepsi.xeros.zulrah.methods.Combat;
import me.pepsi.xeros.zulrah.methods.Looting;
import me.pepsi.xeros.zulrah.methods.Pathing;
import me.pepsi.xeros.zulrah.methods.Potions;
import me.pepsi.xeros.zulrah.methods.Prayers;
import me.pepsi.xeros.zulrah.methods.Staff;
import me.pepsi.xeros.zulrah.util.GUI;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Zulrah", category = Category.MONEYMAKING, version = "2.2D",
        description = "Welcome to Pepsiplaya's Zulrah script (REQUIRES PRAYER POTS/RESTORE AND FOOD)", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Zulrah extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener, MouseListener {
	
	public static String status;
	public static String antibot;
    public long startTime;
    private Image image;
    private Image x;
    public static boolean afk = false;
    public static boolean started = false;
    public static boolean recoil = false;
    public static boolean dead = false;
    public static boolean presetLoaded = false;
    public static boolean teleport = false;
    boolean hide = false;
    public static String onyx;
    public static String serp;
    public static String tanz;
    public static String magicFang;
    public static int refillPrayer;
    public static int prayer;
    public static int drops = 0;
    public static int deaths = 0;
    public int kills = 0;
    public static int foodType;
    public static int prayerType;
	public static int eatAt;
	public static int slot = 1;
    public GUI gui;
    Point p;
	
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	public static final WorldArea ZULRAH = new WorldArea(
            new WorldPoint(2259, 3066, 0),
            new WorldPoint(2279, 3080, 0)
    );
	
	public static final WorldArea WAITING_AREA = new WorldArea(new WorldPoint(1220, 3552), new WorldPoint(1241, 3575));
	
    @Override
    public boolean onExecute() {
    	Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 20 + random;
    	afk = false;
    	dead = false;
    	presetLoaded = false;
    	started = false;
    	drops = 0;
    	deaths = 0;
    	status = "Setup GUI";
    	teleport = false;
    	antibot = "No Staff Nearby";
        startTime = System.currentTimeMillis();
        
        try {
        	image = ImageIO.read(new URL("https://i.imgur.com/01IMTR7.png"));
        	x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
        } catch (IOException e) {
        	ctx.log("ERROR LOADING PAINT");
        }
        try {
            Zulrah script = this;
            SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() {
                gui = new GUI(script);
            }});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    @Override
    public void onProcess() {
    	/*SimpleWidget staff = ctx.widgets.populate().filter(5192).next();
    	if (staff.getText() == "@gre@Online" && started == true) {
    		if (!HOME.containsPoint(ctx.players.getLocal())) {
    			ctx.prayers.disableAll();
    			ctx.magic.castHomeTeleport();
    		}
			antibot = "Staff Online, afking";
			status = "Staff Online, afking";
			started = false;
		} else if (staff.getText() == "@red@Offline" && started == false) {
			started = true;
		}*/
    	Staff.antiStaff(ctx);
    	if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		Zulrah.antibot = "Unknown dialogue open";
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
    	if (!started) {
			return;
		} else {
			if (afk == true) {
		    	ctx.magic.castHomeTeleport();
				ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 6);
				ctx.prayers.disableAll();
				Banking.loadPreset(ctx);
				ctx.log("STAFF PMED YOU AFKING");
				ctx.sleep(750);
				ctx.stopScript();
			}
	    	if (WAITING_AREA.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		Zulrah.dead = false;
	    		Banking.heal(ctx);
	    		Banking.loadPreset(ctx);
	    		return;
	    	} else if (HOME.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		Zulrah.dead = false;
	    		ctx.prayers.disableAll();
	    		Banking.heal(ctx);
	    		Banking.loadPreset(ctx);
	    	} else if (ctx.players.getLocal().getLocation().getRegionID() == 8751) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		Zulrah.dead = false;
	    		Pathing.boat(ctx);
	    	} else if (ZULRAH.containsPoint(ctx.players.getLocal())) {
	    		if (presetLoaded) {
	    			presetLoaded = false;
	    		}
	    		if (Zulrah.dead) {
	    			Looting.Loot(ctx);
	    			return;
	    		}
	    		Potions.handleDrinkingPrayer(ctx);
	    		Prayers.pray(ctx);
	    		Combat.handleEatingFood(ctx);
	    		Combat.doCombat(ctx);
	    		Potions.pot(ctx);
	    		Potions.rangePotion(ctx);
	    		Potions.dropVial(ctx);
	    		Combat.recoil(ctx);
	    	} else {
				Zulrah.status = "How'd We Get Here?";
				ctx.log("How'd We Get Here?");
				ctx.prayers.disableAll();
	    		ctx.sleep(3000, 6000);
				ctx.keyboard.sendKeys("::raids");
				ctx.onCondition(() -> WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
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
		if (!hide) {
			if (image != null) {
		    	g.drawImage(image, 1, 311, null);
		    }
		     g.setFont(font1);
		     g.setColor(color1);
		     Font currentFont = g.getFont();
		     Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.8F);
		     g.setFont(newFont);
		     g.drawString("" + kills, 83, 384);
		     g.drawString("" + ctx.paint.valuePerHour((int) kills, startTime), 110, 420);
		     g.drawString("" + deaths, 98, 458);
		     g.drawString(antibot, 272, 384);
		     g.drawString(status, 254, 420);
		     g.drawString("" + drops, 247, 459);
		     Font currentFont1 = g.getFont();
		     Font newFont1 = currentFont1.deriveFont(currentFont1.getSize() * 1.4F);
		     g.setFont(newFont1);
		     g.drawString(ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 335);
		} else {
			if (x != null) {
		    	g.drawImage(x, 505, 339, null);
		    }
		}
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if (event.getMessageType() == 0 && event.getSender().equals("")) {
            if (event.getMessage().contains("Your Zulrah kill count is:")) {
            	Zulrah.dead = true;
            	kills = kills + 1;
            } else if (event.getMessage().contains("recoil shatters")) {
            	recoil = true;
            } else if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
			} else if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (event.getMessage().contains("You haven't teleported anywhere recently")) {
				teleport = false;
			} else if (event.getMessage().contains("You teleport to your most recent location")) {
				teleport = true;
			} else if (event.getMessage().contains("You need to have used a preset recently")) {
				presetLoaded = false;
			} else if (event.getMessage().contains("Oh dear, you")) {
				deaths = deaths + 1;
			} else if (ZULRAH.containsPoint(ctx.players.getLocal())) {
				if (event.getMessage().contains(ctx.players.getLocal().getName()) && !event.getMessage().contains(ctx.players.getLocal().getName() + " has reached")) {
					drops = drops + 1;
				}
			}
        }
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + event.getMessage());
			//afk = true;
		}
    }
	
	public void setupPrayer(int prayerType) {
		Zulrah.prayerType = prayerType;
	}

	Rectangle close = new Rectangle(505, 339, 18, 18);
	Rectangle open = new Rectangle(505, 339, 18, 18);
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
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

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public int loopDuration() {
		return 200;
	}
}