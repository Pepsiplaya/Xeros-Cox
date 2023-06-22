package me.pepsi.xeros.tob;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleWidget;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.events.GraphicsObjectSpawnedEvent;
import simple.api.events.ProjectileSpawnedEvent;
import simple.api.listeners.SimpleGraphicsObjectSpawnedListener;
import simple.api.listeners.SimpleMessageListener;
import simple.api.listeners.SimpleProjectileSpawnedListener;
import simple.api.script.Script;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import me.pepsi.xeros.tob.rooms.Bloat;
import me.pepsi.xeros.tob.rooms.Maiden;
import me.pepsi.xeros.tob.rooms.Nylocas;
import me.pepsi.xeros.tob.rooms.Sotetseg;
import me.pepsi.xeros.tob.rooms.TreasureRoom;
import me.pepsi.xeros.tob.rooms.Verzik;
import me.pepsi.xeros.tob.rooms.WaitingRoom;
import me.pepsi.xeros.tob.rooms.Xarpus;
import me.pepsi.xeros.tob.util.GUI;
import me.pepsi.xeros.tob.util.Staff;
import me.pepsi.xeros.tob.util.Util;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros ToB Raids", category = Category.MONEYMAKING, version = "1.2D", description = "With Ultimate Instinct ToB, fly through both solo and team ToB with style!", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Tob extends Script implements SimpleProjectileSpawnedListener, SimpleGraphicsObjectSpawnedListener, MouseListener, LoopingScript, SimplePaintable, SimpleMessageListener {

	public static String status;
	public static String antibot;
	public long startTime;
	public long raidKills;
	static public boolean restock;
	static public boolean rangeWalk;
	static public boolean onAcid;
	static public boolean onBlood;
	static public boolean rangeProjectile;
	static public boolean mageProjectile;
	public static int count;
	public static int rangeType;
	public static int mageType;
	public static int mainType;
	private Image x;
	public Image staffImage;
	private Image image;
	private Image guiWrench;
	boolean hide = false;
	public boolean exit = false;
	public static boolean staff = false;
	public static boolean started = false;
	public static boolean dead = false;
	public static boolean hasMoved = false;
	public static boolean presetLoaded = false;
	public static long lastPotion = -1;
	public static int stage;
	public static int rares;
	public static boolean isLeader = false;
	public static String username;
	public static boolean staffBeep = false;
	public static boolean maidenStart = false;
	public static boolean pickupFood = false;
	public static long lastOverload = -1;
	public static int prayer;
	public static Integer leaderDelay;
	public static boolean special = false;
	public static boolean acidWalk;
	public static ArrayList<WorldPoint> bloodTiles = new ArrayList<WorldPoint>();
	public static ArrayList<WorldPoint> acidTiles = new ArrayList<WorldPoint>();
	public static ArrayList<WorldPoint> yellowTile = new ArrayList<WorldPoint>();

	public GUI gui;

	@Override
	public boolean onExecute() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		count = 0;
		prayer = 20 + random;
		lastOverload = System.currentTimeMillis() - 300000;
		startTime = System.currentTimeMillis();
		raidKills = 0;
		staff = false;
		hasMoved = false;
		rangeProjectile = false;
		mageProjectile = false;
		onAcid = false;
		onBlood = false;
		special = false;
		maidenStart = false;
		rangeWalk = true;
		presetLoaded = false;
		rares = 0;
		stage = 0;
		restock = true;
		Verzik.verzikRangeMove = false;
		exit = false;
		started = false;
		status = "Waiting to start...";
		antibot = "No Staff Nearby";
		try {
			image = ImageIO.read(new URL("https://i.imgur.com/eyWF9de.png"));
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
			guiWrench = ImageIO.read(new URL("https://i.imgur.com/aHfblqV.png"));
			x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		try {
			Tob script = this;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gui = new GUI(script, ctx);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onProcess() {
		if (ctx.client.isLoggedIn()) {
			if (Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal()) && bloodTiles != null && bloodTiles.size() >= 1) {
		        count++;
		        if (bloodTiles.stream().anyMatch(i -> ctx.pathing.onTile(i))) {
					onBlood = true;
				} else {
					onBlood = false;
				}
		        if (count >= 12) {
		            bloodTiles.clear();
		            count = 0;
		        }
		    }
			if (Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal()) && acidTiles != null && acidTiles.size() >= 1) {
				count += 1;
				if (acidTiles.stream().anyMatch(i -> ctx.pathing.onTile(i))) {
					onAcid = true;
				} else {
					onAcid = false;
				}
		        if (count >= 35) {
		        	acidTiles.clear();
		        	count = 0;
		        }
			}
			if (Util.ROOM_SIX.containsPoint(ctx.players.getLocal()) && yellowTile != null && yellowTile.size() >= 1) {
				count += 1;
				WorldPoint closestYellowTile = yellowTile.stream().min(Comparator.comparing(i -> i.distanceTo(ctx.players.getLocal().getLocation()))).orElse(null);
				if (closestYellowTile != null && !hasMoved) {
				    ctx.pathing.step(closestYellowTile);
				    status = "Walking To Yellow Tile";
				    ctx.log("Walking To Yellow Tile");
				    hasMoved = true;
				}
				if (count >= 10) {
					yellowTile.clear();
					hasMoved = false;
				}
			}
			if (ctx.players.getLocal().getAnimation() == 1378 || ctx.players.getLocal().getAnimation() == 401 || ctx.players.getLocal().getAnimation() == 4177) {
				special = false;
			}
			Staff.antiStaff();
			if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
	    		ctx.log("Unknown dialogue open react quickly!");
	    		Tob.antibot = "Unknown dialogue open";
	    		ctx.sleep(1000);
	    		staff = true;
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			if (!started) {
				return;
			}
			WaitingRoom.waitingRoom();
			Maiden.roomOne();
			Bloat.roomTwo();
			Nylocas.roomThree();
			Sotetseg.roomFour();
			Xarpus.roomFive();
			Verzik.roomSix();
			TreasureRoom.treasureRoom();	
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
	Rectangle guiOpen = new Rectangle(487, 339, 18, 18);
	Rectangle open = new Rectangle(505, 339, 18, 18);
	Rectangle close = new Rectangle(505, 339, 18, 18);
	Point p;

	@Override
	public void onPaint(Graphics2D g) {
		SimpleWidget leader = ctx.widgets.populate().filter(30373).next();
		g.drawImage(guiWrench, 479, 335, null);
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
			if (!isLeader) {
				g.drawString(leader.getText().replaceAll("(\\<.*?\\>)", "").replaceAll("(\\@.*?\\@)", "")
						.replaceAll("Extreme ", "").replaceAll("Donator ", "").replaceAll("Giant Slayer ", "")
						.replaceAll("#DOuEvenLift ", "").replaceAll("Completionist ", "")
						.replaceAll("Diamond Club ", "").replaceAll("Onyx Club ", "").replaceAll("Zenyte Club ", "")
						.replaceAll("Divine ", "").replaceAll("Helper ", "").replaceAll("Moderator ", "")
						.replaceAll("Fisherman ", "").replaceAll("Lumberjack ", "").replaceAll("Master Slayer ", "")
						.replaceAll("Grave Digger ", "").replaceAll("Ironman ", "").replaceAll("HC Ironman ", "")
						.replaceAll("PVM Fanatic ", "").replaceAll("MR Collector ", ""), 240, 456);
			} else {
				g.drawString("You Are The Leader", 240, 456);
			}
			g.drawString("Runs: " + raidKills, 40, 384);
			g.drawString("Runs/hr: " + ctx.paint.valuePerHour((int) raidKills, startTime), 40, 420);
			g.drawString("Rares: " + rares, 40, 456);
			g.drawString("Anti-Bot: " + antibot, 240, 384);
			g.drawString("Status: " + status, 240, 420);
			Font currentFont1 = g.getFont();
			Font newFont1 = currentFont1.deriveFont(currentFont1.getSize() * 0.7F);
			g.setFont(newFont1);
			g.drawString(ctx.paint.formatTime(System.currentTimeMillis() - startTime).replaceAll("00:", ""), 445, 335);
			g.drawImage(x, 508, 338, null);
		} else {
			if (x != null) {
				g.drawImage(x, 508, 338, null);
			}
		}
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + ": " + event.getMessage());
		}
		if (username != null && !username.isEmpty()) {
			if (event.getMessageType() == 2 && event.getMessage().toLowerCase().contains(username)
					&& username.toLowerCase() != ctx.players.getLocal().getName().toLowerCase()) {
				Toolkit.getDefaultToolkit().beep();
			}
		}
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
			}
			/*if (event.getMessage().contains("You have entered") || event.getMessage().contains("The fight has started")) {
				if (Util.ROOM_ONE.containsPoint(ctx.players.getLocal()) || Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal())) {
					maidenStart = true;
				}
			}*/
			if (event.getMessage().contains("You need to have used")) {
				ctx.log("NO RECENT PRESET LOADED, STOPPING SCRIPT");
				ctx.stopScript();
			}
			if (event.getMessage().contains("You are now the party leader")) {
				isLeader = true;
			}
			if (event.getMessage().contains("You left the party")) {
				isLeader = false;
			}
			if (event.getMessage().contains("have enough points to buy that!") || event.getMessage().contains("have enough inventory space.")) {
				restock = false;
			}
			if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			}
			if (event.getMessage().contains("Oh dear, you")) {
				dead = true;
				lastOverload = System.currentTimeMillis() - 601000;
			}
			if (event.getMessage().contains("You need to defeat the current")) {
				dead = false;
			}
			if (event.getMessage().contains("Verzik Vitur has died.")) {
				raidKills = raidKills + 1;
			}
			if (event.getMessage().contains("You now have complete resistance against dragon fire")) {
				lastPotion = System.currentTimeMillis();
			}
			if (event.getMessage().contains("You have run out of") || event.getMessage().contains("ammo")) {
				started = false;
			}
			if (event.getMessage().contains(ctx.players.getLocal().getName()) && !event.getMessage().contains(ctx.players.getLocal().getName() + " has reached") && !event.getMessage().contains(ctx.players.getLocal().getName() + " has joined the party.")) {
				rares += 1;
				ctx.log("RARE LOG " + event.getMessage());
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	@Override
	public int loopDuration() {
		return 200;
	}

	public void mousePressed(MouseEvent e) {
		p = e.getPoint();
		if (close.contains(p) && !hide) {
			hide = true;
		} else if (open.contains(p) && hide) {
			hide = false;
		}
		if (guiOpen.contains(p) && !GUI.frame.isVisible()) {

			if (GUI.frame.getState() == Frame.ICONIFIED)
				GUI.frame.setState(Frame.NORMAL);

			GUI.frame.setVisible(true);

		}
	}

	public void setupRange(int rangeType) {
		Tob.rangeType = rangeType;
	}

	public void setupMage(int mageType) {
		Tob.mageType = mageType;
	}
	
	public void setupMain(int mainType) {
		Tob.mainType = mainType;
	}

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
		if ((object.getGfxId() >= 1577 && object.getGfxId() <= 1581) && Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal())) {
	        bloodTiles.add(new WorldPoint(object.getLocX(), object.getLocY(), 0));
	    }
		if (object.getGfxId() == 1595 && Util.ROOM_SIX.containsPoint(ctx.players.getLocal())) {
			yellowTile.add(new WorldPoint(object.getLocX(), object.getLocY(), 0));
        }
		if ((object.getGfxId() >= 1652  && object.getGfxId() <= 1666) && Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
        	acidTiles.add(new WorldPoint(object.getLocX(), object.getLocY(), 0));
        }
    }

	@Override
	public void onProjectileSpawned(ProjectileSpawnedEvent projectile) {
		if (Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal())) {
			if (projectile.getProjectileId() == 1607) {
				mageProjectile = false;
				rangeProjectile = true;
			}
			if (projectile.getProjectileId() == 1606) {
				rangeProjectile = false;
				mageProjectile = true;
			}
		}
	}
}