package me.pepsi.xeros.nex.core;

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
import me.pepsi.xeros.nex.methods.Banking;
import me.pepsi.xeros.nex.methods.Combat;
import me.pepsi.xeros.nex.methods.Pathing;
import me.pepsi.xeros.nex.methods.Potions;
import me.pepsi.xeros.nex.methods.Staff;
import me.pepsi.xeros.nex.util.GUI;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleWidget;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Nex", category = Category.MONEYMAKING, version = "1.2D",
        description = "Currently only tested with Twisted Bow, make sure to have sara brews, sanfews, overloads, and restores.", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Nex extends Script implements MouseListener, LoopingScript, SimplePaintable, SimpleMessageListener {
	
	public static String status;
	public static int phase = 0;
	public static String antibot;
    public long startTime;
    public boolean started;
    private Image x;
    private Image image;
    public static int prayer;
    boolean hide = false;
    public static boolean presetLoaded = false;
    public static int drops = 0;
    public int kills = 0;
	public boolean isLeader = false;
    public static int prayerType;
	public static int eatAt;
	public static int slot;
	public static boolean special;
    public GUI gui;
    Point p;
	
    WorldPoint[] PATH_TO_DOOR = { new WorldPoint(2863, 5219, 0), new WorldPoint(2871, 5217, 0),
			new WorldPoint(2879, 5215, 0), new WorldPoint(2883, 5209, 0), new WorldPoint(2890, 5206, 0) };
    
    final WorldPoint nex1 = new WorldPoint(2883, 5280, 2);
    public final static WorldPoint nex2 = new WorldPoint(2855, 5227);
    public final static WorldPoint nex3 = new WorldPoint(2900, 5203);
    
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	public static final WorldArea NEX = new WorldArea(
			new WorldPoint(2950, 5250, 0),
            new WorldPoint(2908, 5150, 0)
    );
	
	public static final WorldArea RAIDS_WAITING_AREA = new WorldArea(
			new WorldPoint(1220, 3552),
			new WorldPoint(1241, 3575)
	);
	
	public static final WorldArea NEX_WAITING_AREA2 = new WorldArea(			
			new WorldPoint(2862, 5207),
			new WorldPoint(2889, 5220)
	);
	
	public static final WorldArea NEX_LOBBY = new WorldArea(
			new WorldPoint(2899, 5199),
			new WorldPoint(2908, 5207)
	);
	
    @Override
    public boolean onExecute() {
    	if (!ctx.combat.autoRetaliate()) {
    		ctx.combat.toggleAutoRetaliate(true);
    	}
    	Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 30 + random;
    	presetLoaded = false;
    	drops = 0;
    	isLeader = false;
    	phase = 0;
    	special = false;
    	status = "Setup GUI";
    	antibot = "No Staff Nearby";
        startTime = System.currentTimeMillis();
        try {
        	image = ImageIO.read(new URL("https://i.imgur.com/cuxVZ3H.png"));
        	x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
        } catch (IOException e) {
        	ctx.log("ERROR LOADING PAINT");
        }
        try {
            Nex script = this;
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
    	if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		Nex.antibot = "Unknown dialogue open";
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
    	if (!started) {
			return;
		} else {
			if (NEX.containsPoint(ctx.players.getLocal())) {
	    		Staff.antiStaff();
	    		if (presetLoaded) {
	    			presetLoaded = false;
	    		}
	    		Combat.doCombat();
	    		Combat.handleEatingFood();
	    		Potions.pot();
	    		Potions.rangePotion();
	    		Potions.dropVial();
	    		Potions.handleDrinkingPrayer();
	    	} else if (ctx.pathing.onTile(nex1)) {
	    		Nex.presetLoaded = false;
	    		Pathing.door1();
	    	} else if (ctx.pathing.onTile(nex2)) {
	    		Nex.presetLoaded = false;
	    		Pathing.door2();
	    	} else if (NEX_WAITING_AREA2.containsPoint(ctx.players.getLocal())) {
	    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
	    		ctx.pathing.walkPath(PATH_TO_DOOR);
	    		status = "Pathing To Door";
	    	}  else if (ctx.players.getLocal().getLocation().getRegionID() == 11601 && (!NEX.containsPoint(ctx.players.getLocal()) && !NEX_LOBBY.containsPoint(ctx.players.getLocal()))) {
	    		if (presetLoaded) {
	    			presetLoaded = false;
	    		}
	    		Pathing.door2();
	    	} else if (NEX_LOBBY.containsPoint(ctx.players.getLocal())) {
	    		if (ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, false);
				}
	    		phase = 0;
	    		Staff.antiStaff();
	    		SimpleWidget party = ctx.widgets.populate().filter(30372).next();
	    		SimpleWidget leader = ctx.widgets.populate().filter(30373).next();
	    		Banking.loadPreset();
	    		if (isLeader) {
					SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Ancient Barrier").nearest().next();
					if (barrier != null) {
						status = "Entering Nex";
						barrier.interact(SimpleObjectActions.SECOND);
						ctx.sleep(1500);
						ctx.dialogue.clickContinue();
						ctx.sleep(750);
						ctx.menuActions.sendAction(679, -1, -1, 367);
						ctx.sleep(750);
						ctx.menuActions.sendAction(315, -1, -1, 2461);
						ctx.onCondition(() -> !NEX_LOBBY.containsPoint(ctx.players.getLocal()), 250, 3);
					}
	    		} else if (!leader.getText().contains(ctx.players.getLocal().getName()) || leader.getText().equals("-") || party.getText().equals("Nex Party")) {
	    			if (isLeader) {
	    				isLeader = false;	
	    			}
					status = "Waiting On Leader";
					if (ctx.dialogue.dialogueOpen()) {
						ctx.menuActions.sendAction(315, -1, -1, 2461);
					}
	    		} else if (leader.getText().contains(ctx.players.getLocal().getName())) {
	    			isLeader = true;
				}
	    	} else if (RAIDS_WAITING_AREA.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		ctx.prayers.disableAll();
	    		Banking.heal();
	    		Banking.loadPreset();
	    	} else if (HOME.containsPoint(ctx.players.getLocal())) {
	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
	    		ctx.prayers.disableAll();
	    		Banking.heal();
	    		Banking.loadPreset();
	    	} else {
	    		Nex.status = "How'd We Get Here?";
				ctx.prayers.disableAll();
	    		ctx.sleep(2000, 5000);
				ctx.chat.sendCommand("raids");
				ctx.onCondition(() -> RAIDS_WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
	    	}
	    }
    }
    
    /**
     * 
     * @return - worldpoint that is both reachable and does not contain a shadow on it
     */

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
		     g.drawString("PHASE: " + phase, 40, 458);
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
            if (event.getMessage().contains("Your Nex kill count is:")) {
            	kills = kills + 1;
            } else if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
            } else if (event.getMessage().contains("You need to have used a preset recently")) {
				presetLoaded = false;
			} else if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (NEX.containsPoint(ctx.players.getLocal())) {
				if (event.getMessage().contains(ctx.players.getLocal().getName()) && !event.getMessage().contains(ctx.players.getLocal().getName() + " has reached") && !event.getMessage().contains(ctx.players.getLocal().getName() + " has joined the party.")) {
					drops = drops + 1;
				}
			}
        }
    	if (event.getMessage().contains("Fumus,")) {
			phase = 2;
		} else if (event.getMessage().contains("Umbra,")) {
			phase = 4;
		} else if (event.getMessage().contains("Cruor,")) {
			phase = 6;
		} else if (event.getMessage().contains("Glacies,")) {
			phase = 8;
		}
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + event.getMessage());
		}
    }
	
	public void setupPrayer(int prayerType) {
		Nex.prayerType = prayerType;
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
}