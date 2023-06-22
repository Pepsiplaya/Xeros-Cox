package me.pepsi.xeros.zalcano;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleGraphicsObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleSceneObject;
import simple.api.actions.SimpleItemActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Zalcano", category = Category.OTHER, version = "1.0D", description = "Does Hespori when it's time", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Zalcano extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	public static String status;
	public static boolean started = false;
	public static boolean staff = false;
	private boolean full = false;
	private long startTime;
	private long lastAnimation = -1;
	public static int slot = 1;
	private Image staffImage;
	final static WorldArea ZALCANO_WAITING = new WorldArea(new WorldPoint(3040, 6072), new WorldPoint(3027, 6064));
	final static WorldArea ZALCANO = new WorldArea(new WorldPoint(3020, 6035), new WorldPoint(3047, 6063));
	final static WorldArea HOME = new WorldArea(new WorldPoint(3086, 3468, 0), new WorldPoint(3110, 3505, 0));
	private static WorldPoint dodgePointBoulder = null;
	private static WorldPoint dodgePointRed = null;

	public GUI gui;

	@Override
	public boolean onExecute() {
		staff = false;
		startTime = System.currentTimeMillis();
		full = false;
		started = false;
		try {
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		try {
			Zalcano script = this;
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
		if (!started) {
			return;
		}
		if (ctx.players.getLocal().isAnimating()) {
			lastAnimation = System.currentTimeMillis();
		}
		if (ctx.inventory.getFreeSlots() >= 2 && full) {
			full = false;
		}
		ctx.graphicsObjects.populate().filter(1727).next();
		SimpleNpc zalcano = ctx.npcs.populate().filter(9049).next();
		SimpleNpc zalcano2 = ctx.npcs.populate().filter(9050).next();
		SimpleItem unrefined = ctx.inventory.populate().filter(23905).next();
		SimpleItem refined = ctx.inventory.populate().filter(23906).next();
		SimpleItem imbued = ctx.inventory.populate().filter(23907).next();
		SimpleGameObject furnace = (SimpleGameObject) ctx.objects.populate().filter("Furnace").nearest().next();
		SimpleGameObject altar = (SimpleGameObject) ctx.objects.populate().filter("Altar").nearest().next();
		SimpleGameObject blue = (SimpleGameObject) ctx.objects.populate().filter(3432).nearest().next();
		SimpleGameObject rock = (SimpleGameObject) ctx.objects.populate().filterContains("(Glowing)").nearest().next();
		if (ZALCANO.containsPoint(ctx.players.getLocal())) {
			dodgeBoulder();
			dodgeRed();
			dropVial();
			handleEatingFood();
			if (ctx.pathing.energyLevel() >= 25) {
				ctx.pathing.running(true);
			} // CHANGE THE ID FOR MINING HERE SO IT WON'T DODGE WHILE MINING, also you should probably make a better dodging mechanic i just threw this together in a second.
			if (zalcano != null && zalcano.getAnimation() == 8432 && ctx.players.getLocal().getAnimation() == 1) { // this is dodging the fire ball zalcano shoots at the ore
				if (furnace != null) {
					ctx.pathing.step(furnace);
					ctx.sleep(1200);
				}
			}
			if (!ctx.players.getLocal().isAnimating() && System.currentTimeMillis() > (lastAnimation + 2000) && zalcano2 != null) { // this attacks zalcano after the shield has been taken down.
				zalcano2.interact(20);
				lastAnimation = System.currentTimeMillis();
				return;
			}
			if (imbued != null && zalcano != null) { // uses the blue imbued stone on zalcano, for some reason it won't walk on the blue symbols
				if (blue != null && !ctx.pathing.onTile(blue)) {
					ctx.pathing.step(blue);
				} else {
					zalcano.interact(20);
				}
				return;
			}
			if (!ctx.inventory.inventoryFull() && !full && zalcano2 == null) { // mines the correct glowing ore
				if (rock != null) {
					status = "Mining";
					ctx.log("Mining");
					if (ctx.pathing.distanceTo(rock) >= 8) {
						ctx.pathing.step(rock);
					} else {
						rock.interact("Mine");
						lastAnimation = System.currentTimeMillis();
						ctx.sleep(800);
					}
				}
			} else if ((full || ctx.inventory.inventoryFull()) && unrefined != null) {
				if (furnace != null) {
					status = "Refining";
					ctx.log("Refining");
					if (ctx.pathing.distanceTo(furnace) >= 8) {
						ctx.pathing.step(furnace);
					} else {
						furnace.interact(502);
						ctx.sleep(800);
					}
				}
			} else if (ctx.inventory.populate().filter(23905).isEmpty() && refined != null && (full || ctx.inventory.inventoryFull())) {
				if (altar != null) {
					status = "Imbuing";
					ctx.log("Imbuing");
					if (ctx.pathing.distanceTo(altar) >= 8) {
						ctx.pathing.step(altar);
					} else {
						altar.interact(502);
					}
				}
			}
		}
	}
	
	public void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}

	public void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.healthPercent() <= 75 && ctx.combat.health() != 10) {
			status = "Eating Food";
			ctx.log("Eating Food");
			food.interact(74);
		} else if (brew != null && ctx.combat.healthPercent() <= 80 && ctx.combat.health() != 10) {
			status = "Drinking Brew";
			ctx.log("Drainking Brew");
			brew.interact(74);
		}
	}

	WorldPoint getBoulder() {
		final int radius = 2;// Radius of tiles to find a valid location
		final WorldPoint myPointBoulder = ctx.players.getLocal().getLocation();// Store our current location
		final List<WorldPoint> boulder = ctx.graphicsObjects.populate().filter(1727).toStream()
				.map(SimpleGraphicsObject::getLocation).collect(Collectors.toList());// Build list of locations of boulder
		if (boulder.contains(myPointBoulder)) { // We need to move
			WorldPoint tempPointBoulder;// Temporary object to use for checks below
			final List<WorldPoint> validPointsBoulder = new ArrayList<>();// List of worldpoints that have no boulder on them and are reachable
			for (int x = -radius; x <= radius; x++) {
				for (int y = -radius; y <= radius; y++) {
					tempPointBoulder = myPointBoulder.derrive(x, y);
					if (boulder.contains(tempPointBoulder)) {
						continue;
					}
					if (ctx.pathing.reachable(tempPointBoulder)) {
						validPointsBoulder.add(tempPointBoulder);
					}
				}
			}
			if (validPointsBoulder.isEmpty()) {
				ctx.log("We couldn't find any valid tiles to traverse to.");
				return null;
			}
			// Sorting our valid tiles to ones closest to us
			tempPointBoulder = validPointsBoulder.get(0);
			for (WorldPoint point : validPointsBoulder) {
				if (point.distanceTo(myPointBoulder) < tempPointBoulder.distanceTo(myPointBoulder)) {
					tempPointBoulder = point;
				}
			}
			return tempPointBoulder;
		}
		return null;
	}
	
	WorldPoint getRed() {
		final int radius = 3;// Radius of tiles to find a valid location
		final WorldPoint myPointRed = ctx.players.getLocal().getLocation();// Store our current location
		final List<WorldPoint> red = ctx.objects.populate().filter(3431).toStream().map(SimpleSceneObject::getLocation).collect(Collectors.toList());// Build list of locations of red
		if (red.contains(myPointRed) || red.contains(myPointRed)) { // We need to move
			WorldPoint tempPointRed;// Temporary object to use for checks below
			final List<WorldPoint> validPoints = new ArrayList<>();// List of worldpoints that have no boulder on them
																	// and are reachable
			for (int x = -radius; x <= radius; x++) {
				for (int y = -radius; y <= radius; y++) {
					tempPointRed = myPointRed.derrive(x, y);
					if (red.contains(tempPointRed)) {
						continue;
					}
					if (ctx.pathing.reachable(tempPointRed)) {
						validPoints.add(tempPointRed);
					}
				}
			}
			if (validPoints.isEmpty()) {
				ctx.log("We couldn't find any valid tiles to traverse to.");
				return null;
			}
			// Sorting our valid tiles to ones closest to us
			tempPointRed = validPoints.get(0);
			for (WorldPoint point : validPoints) {
				if (point.distanceTo(myPointRed) < tempPointRed.distanceTo(myPointRed)) {
					tempPointRed = point;
				}
			}
			return tempPointRed;
		}
		return null;
	}

	private void dodgeBoulder() {
		if (dodgePointBoulder == null) {
			final WorldPoint point = getBoulder();
			if (point != null) {
				dodgePointBoulder = point;
			}
		} else {
			if (!ctx.pathing.onTile(dodgePointBoulder) && !ctx.pathing.inMotion()) {
				ctx.pathing.step(dodgePointBoulder);
				ctx.sleep(3200);
				status = "Dodging Boulder";
			} else {
				dodgePointBoulder = null;
			}
		}
	}
	
	private void dodgeRed() {
		if (dodgePointRed == null) {
			final WorldPoint point = getRed();
			if (point != null) {
				dodgePointRed = point;
			}
		} else {
			if (!ctx.pathing.onTile(dodgePointRed) && !ctx.pathing.inMotion()) {
				ctx.pathing.step(dodgePointRed);
				status = "Dodging Boulder";
			} else {
				dodgePointRed = null;
			}
		}
	}
	
	private static int between(final int min, final int max) {
		final Random random = new Random();
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}

	@Override
	public void onTerminate() {
		if (gui != null) {
			GUI.frame.setVisible(false);
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(new Color(29, 3, 3, 94));
		g.fillRect(7, 283, 180, 35);
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1));
		g.drawRect(7, 283, 180, 35);
		g.setFont(new Font("Arial", 1, 10));
		g.setColor(new Color(255, 255, 255));
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
		g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 297);
		g.drawString("Status: " + status, 10, 310);
	}

	public void onChatMessage(ChatMessageEvent event) {
		SimpleGameObject rock = (SimpleGameObject) ctx.objects.populate().filterContains("(Glowing)").nearest().next();
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You have run out of inventory space.")) {
				full = true;
			} else if (event.getMessage().contains("You feel a drain on your memory")) {
			} else if (event.getMessage().contains("You have no more free")) {
				full = true;
			} else if (event.getMessage().contains("You take damage from standing")) {
				if (rock != null) {
					ctx.pathing.step(rock);
				}
			} else if (event.getMessage().contains("The Demonic Symbol hurts") && !ctx.pathing.inMotion()) {
				ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(between(-1, 0), between(-1, 0)));
			}
		}
	}

	@Override
	public int loopDuration() {
		return 150;
	}
}