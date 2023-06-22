package me.pepsi.xeros.raids;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleWidget;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import me.pepsi.xeros.raids.methods.Rooms;
import me.pepsi.xeros.raids.methods.Staff;
import me.pepsi.xeros.raids.util.GUI;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros CoX Raids", category = Category.MONEYMAKING, version = "2.2D", description = "With Ultimate Instinct Raids, fly through both solo and team raids with style!", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Raids extends Script implements MouseListener, LoopingScript, SimplePaintable, SimpleMessageListener {

	public static String status;
	public static String antibot;
	public long startTime;
	public long raidKills;
	public long rareKeys;
	public long commonKeys;
	public static int rangeType;
	public static int mageType;
	private Image x;
	public Image staffImage;
	private Image image;
	private Image guiWrench;
	boolean hide = false;
	public static boolean preset = false;
	public boolean exit = false;
	public static boolean staff = false;
	public static boolean started = false;
	public static boolean dead = false;
	public static boolean presetLoaded = false;
	public static boolean tektonWalk = false;
	public static boolean loot = false;
	public static long lastPotion = -1;
	public static int slot = 1;
	public static boolean isLeader = false;
	public static boolean staffBeep = false;
	public static boolean pickupFood = false;
	public static String username;
	public static Integer leaderDelay;

	public static final String[] lootNames = { "Skele", "mage", "Vanguard", "Muttadile", "Tekton", "Vasa", "Elder",
			"coin", "Shark", "Super restore", "Overload" };

	public GUI gui;

	@Override
	public boolean onExecute() {
		lastPotion = System.currentTimeMillis() - 177000;
		startTime = System.currentTimeMillis();
		raidKills = 0;
		staff = false;
		presetLoaded = false;
		preset = false;
		exit = false;
		started = false;
		tektonWalk = false;
		status = "Waiting to start...";
		antibot = "No Staff Nearby";
		try {
			image = ImageIO.read(new URL("https://i.imgur.com/OJDcmko.png"));
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
			guiWrench = ImageIO.read(new URL("https://i.imgur.com/aHfblqV.png"));
			x = ImageIO.read(new URL("https://i.imgur.com/vWgA578.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		try {
			Raids script = this;
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
		Staff.antiStaff();
		if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		Raids.antibot = "Unknown dialogue open";
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if (!started) {
			return;
		}
		Rooms.WaitingArea();
		Rooms.Spec();
		Rooms.Muttatile();
		Rooms.Vasa();
		Rooms.Vanguard();
		Rooms.Tetkon();
		Rooms.Shaman();
		Rooms.Others();
		Rooms.Olm();
		Rooms.OutsideOlm();
		Rooms.Mages();
		Rooms.IceDemon();
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
			g.drawString("Runs: " + raidKills, 40, 384);
			g.drawString("Runs/hr: " + ctx.paint.valuePerHour((int) raidKills, startTime), 40, 420);
			if (!isLeader) {
				if (leader != null) {
					g.drawString(leader.getText().replaceAll("(\\<.*?\\>)", "").replaceAll("(\\@.*?\\@)", "")
							.replaceAll("Extreme ", "").replaceAll("Donator ", "").replaceAll("Giant Slayer ", "")
							.replaceAll("#DOuEvenLift ", "").replaceAll("Completionist ", "")
							.replaceAll("Diamond Club ", "").replaceAll("Onyx Club ", "").replaceAll("Zenyte Club ", "")
							.replaceAll("Divine ", "").replaceAll("Helper ", "").replaceAll("Moderator ", "")
							.replaceAll("Fisherman ", "").replaceAll("Lumberjack ", "").replaceAll("Master Slayer ", "")
							.replaceAll("Grave Digger ", "").replaceAll("Ironman ", "").replaceAll("HC Ironman ", "")
							.replaceAll("PVM Fanatic ", "").replaceAll("MR Collector ", ""), 240, 456);
				}
			} else {
				g.drawString("You Are The Leader", 240, 456);
			}
			g.drawString("Anti-Bot: " + antibot, 240, 384);
			g.drawString("Status: " + status, 240, 420);
			g.drawString("Common/Rare: " + commonKeys + "/" + rareKeys, 40, 456);
			Font currentFont1 = g.getFont();
			Font newFont1 = currentFont1.deriveFont(currentFont1.getSize() * 1.4F);
			g.setFont(newFont1);
			g.drawString(ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 335);
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
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You feel a drain on your memory") || event.getMessage().contains("An ancient wisdomin fills your mind")) {
				presetLoaded = true;
				preset = true;
			} else if (event.getMessage().contains("You need to have used")) {
				presetLoaded = false;
				preset = false;
			} else if (event.getMessage().contains("You are now the party leader")) {
				isLeader = true;
			} else if (event.getMessage().contains("territory") && Rooms.TEKTON.containsPoint(ctx.players.getLocal())) {
				tektonWalk = true;
			} else if (event.getMessage().contains("You left the party")) {
				isLeader = false;
			} else if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (event.getMessage().contains("Oh dear, you")) {
				dead = true;
				lastPotion = System.currentTimeMillis() - 177000;
			} else if (event.getMessage().contains("You need to defeat the current")) {
				dead = false;
			} else if (event.getMessage().contains("Common Key") && !event.getMessage().contains("bank")
					&& !event.getMessage().contains("Finished")) {
				commonKeys = commonKeys + 1;
			} else if (event.getMessage().contains("Rare Key") && !event.getMessage().contains("bank")
					&& !event.getMessage().contains("Finished") && !event.getMessage().contains("Raids")) {
				rareKeys = rareKeys + 1;
				Toolkit.getDefaultToolkit().beep();
			} else if (event.getMessage().contains("You have completed")) {
				raidKills = raidKills + 1;
			} else if (event.getMessage().contains("You now have complete resistance against dragon fire")) {
				lastPotion = System.currentTimeMillis();
			}
		}
		if (username != null && !username.isEmpty()) {
			if (event.getMessageType() == 2 && event.getMessage().toLowerCase().contains(username)
					&& username.toLowerCase() != ctx.players.getLocal().getName().toLowerCase()) {
				Toolkit.getDefaultToolkit().beep();
			}
		}
		if (event.getMessageType() == 2 && (event.getMessage().contains("inv") || event.getMessage().contains("Inv"))) {
			Rooms.invite = event.getSender().toString().replaceAll("(\\<.*?\\>)", "").replaceAll("(\\@.*?\\@)", "")
					.replaceAll("Extreme ", "").replaceAll("Donator ", "").replaceAll("Giant Slayer ", "")
					.replaceAll("#DOuEvenLift ", "").replaceAll("Completionist ", "").replaceAll("Diamond Club ", "")
					.replaceAll("Onyx Club ", "").replaceAll("Zenyte Club ", "").replaceAll("Divine ", "")
					.replaceAll("Helper ", "").replaceAll("Moderator ", "").replaceAll("Fisherman ", "")
					.replaceAll("Lumberjack ", "").replaceAll("Master Slayer ", "").replaceAll("Grave Digger ", "")
					.replaceAll("Ironman ", "").replaceAll("HC Ironman ", "").replaceAll("PVM Fanatic ", "")
					.replaceAll("MR Collector ", "").replaceAll(ctx.players.getLocal().getName(), "");
		}
	}

	@Override
	public int loopDuration() {
		return 300;
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
		Raids.rangeType = rangeType;
	}

	public void setupMage(int mageType) {
		Raids.mageType = mageType;
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
}