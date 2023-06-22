package me.pepsi.xeros.hespori;

import simple.api.filters.SimpleBank;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;
import simple.api.wrappers.SimpleWidget;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Hespori", category = Category.OTHER, version = "1.3D", description = "Does Hespori when it's time", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Hespori extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	public static String status;
	private boolean chiselStone = false;
	public static boolean started = false;
	public static boolean teleported = false;
	public static boolean staff = false;
	public static boolean walked = false;
	private boolean spawned = false;
	private boolean full = false;
	private long startTime;
	private long lastAnimation = -1;
	public static int slot = 1;
	private int phase = 0;
	private Image staffImage;
	final static WorldArea HESPORI_WAITING = new WorldArea(new WorldPoint(3069, 3490),
			new WorldPoint(3085, 3520));
	final static WorldArea HOME = new WorldArea(new WorldPoint(3086, 3468, 0), new WorldPoint(3110, 3505, 0));
	//private final WorldPoint finish = new WorldPoint(3073, 3506, 0);
	private final WorldPoint start = new WorldPoint(3067, 3499, 0);
	private final WorldPoint walkWait = new WorldPoint(3074, 3501, 0);
	private final WorldPoint vineWalk = new WorldPoint(3063, 3486, 0);
	private final WorldPoint phase5Bug = new WorldPoint(3050, 3495, 0);

	@Override
	public boolean onExecute() {
		staff = false;
		startTime = System.currentTimeMillis();
		phase = 0;
		full = false;
		started = false;
		teleported = false;
		walked = false;
		spawned = false;
		status = "Waiting On Hespori";
		chiselStone = false;
		try {
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		return true;
	}

	@Override
	public void onProcess() {
		Staff.antiStaff();
    	if (ctx.players.getLocal().isAnimating() && !ctx.pathing.inMotion()) { // need to make a check so it does't do animation count when converting runes.
    		lastAnimation = System.currentTimeMillis();
    	}
		if (ctx.inventory.getFreeSlots() >= 2 && full) {
			full = false;
		}
		SimpleWidget hesporiSpawn1 = ctx.widgets.populate().filter(1000004).next();
		SimpleWidget hesporiSpawn2 = ctx.widgets.populate().filter(1000005).next();
		handleEatingFood();
		if (((hesporiSpawn1 != null && hesporiSpawn1.getText().contains("Hespori spawns in 2m")) || (hesporiSpawn2 != null && hesporiSpawn2.getText().contains("Hespori spawns in 2m"))) && walked == false) {
			if (!HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
				ctx.log("0-1 minute sleep");
				ctx.sleep(1, 60000);
				if (ctx.bank.bankOpen()) {
					ctx.bank.closeBank();
				}
				ctx.keyboard.sendKeys("::slayer");
				teleported = true;
				ctx.onCondition(() -> HESPORI_WAITING.containsPoint(ctx.players.getLocal()), 250, 36);
			} else {
				if (!ctx.bank.bankOpen()) {
					ctx.bank.openBank();
					walked = true;
					ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 36);

				}
			}
		} else {
			SimpleItem hesporiKey = ctx.inventory.populate().filter("Hespori key").next();
			if (HESPORI_WAITING.containsPoint(ctx.players.getLocal()) && !teleported && hesporiKey == null) {
				ctx.magic.castHomeTeleport();
			} else if (HOME.containsPoint(ctx.players.getLocal()) && !ctx.bank.bankOpen()) {
				ctx.bank.openBank();
			}
		}
		if (HESPORI_WAITING.containsPoint(ctx.players.getLocal()) && !ctx.pathing.onTile(start)) {
			SimpleItem hesporiKey = ctx.inventory.populate().filter("Hespori key").next();
			phase = 0;
			if (!ctx.bank.bankOpen() && hesporiKey != null) {
				ctx.bank.openBank();
				ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 36);
			} else if (ctx.bank.bankOpen() && !started) {
				if (hesporiKey != null) {
					ctx.bank.deposit(22374, SimpleBank.Amount.ALL);
					ctx.onCondition(() -> hesporiKey == null, 250, 16);
				} else {
					ctx.bank.closeBank();
				}
			}
		}
		if (phase == 0 && !HOME.containsPoint(ctx.players.getLocal())) {
			if (ctx.players.getLocal().getLocation().equals(start)) {
				spawned = false;
				chiselStone = false;
				if (ctx.pathing.distanceTo(vineWalk) >= 2) {
					ctx.pathing.walkToTile(vineWalk);
					ctx.onCondition(() -> ctx.pathing.onTile(vineWalk), 250, 24);
					ctx.sleep(600);
				}
				if (!spawned) {
					phase = 1;
				}
			}
			if (HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
				SimpleGameObject gate = (SimpleGameObject) ctx.objects.populate().filter("Gate").nearest().next();
				if (gate != null && spawned) {
					status = "Opening Gate";
					ctx.log("Opening Gate");
					ctx.sleep(1200, 2400);
					gate.interact(SimpleObjectActions.OPEN);
					ctx.sleep(2000);
					ctx.onCondition(() -> ctx.pathing.onTile(start), 250, 20);
					if (!ctx.pathing.onTile(start)) {
						ctx.keyboard.sendKeys("ffs");
						ctx.stopScript();
					}
					return;
				}
			}
		} else if (phase == 1) {
			SimpleGameObject hesporiVines = (SimpleGameObject) ctx.objects.populate().filter("Hespori Vines").nearest()
					.next();
			if (!ctx.inventory.inventoryFull() && !full) {
				if (hesporiVines != null) {
					if (ctx.pathing.distanceTo(vineWalk) <= 2) {
						hesporiVines.interact(502);
						if (!ctx.pathing.inMotion()) {
							ctx.log("Collecting Vines");
						}
						status = "Collecting Vines";
					} else {
						ctx.pathing.walkToTile(vineWalk.derrive(between(-1, 0), between(-1, 0)));
						if (!ctx.pathing.inMotion()) {
							ctx.log("Walking To Vines");
						}
						status = "Walking To Vines";
					}
				} else {
					phase = 5;
				}
			} else {
				SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
				SimpleGameObject fire = (SimpleGameObject) ctx.objects.populate().filterHasAction("Burn-essence").nearest()
						.next();
				if (fire != null) {
					fire.interact(502);
					status = "Burning Essence";
					if (!ctx.pathing.inMotion()) {
						ctx.log("Burning Essence");
					}
				} else {
					ctx.pathing.step(burningOre);
				}
			}
		} else if (phase == 2) {
			SimpleItem toxicGem = ctx.inventory.populate().filter("Toxic gem").next();
			SimpleItem chisel = ctx.inventory.populate().filter(1755).next();
			SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
			SimpleGameObject enchantedStone = (SimpleGameObject) ctx.objects.populate().filter("Enchanted stone").nearest()
					.next();
			SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
			SimpleNpc hespori = ctx.npcs.populate().filter(8583).next();
			SimpleItem hesporiEssence = ctx.inventory.populate().filter("Hespori essence").reverse().next();
			int uncutGemCount = ctx.inventory.populate().filterContains("Uncut toxic").population(true);
			if (uncutGemCount >= 8) {
				chiselStone = true;
			}
			if (chiselStone) {
				if (ctx.inventory.populate().filter("Uncut toxic gem").isEmpty()) {
					chiselStone = false;
				} else if (uncutToxicGem != null && chisel != null) {
					status = "Chiseling";
					ctx.log("Chiseling");
					uncutToxicGem.interact(SimpleItemActions.USE);
					ctx.sleep(600);
					chisel.interact(SimpleItemActions.USE_WITH);
					ctx.onCondition(() -> ctx.inventory.populate().filter("Uncut toxic gem").isEmpty(), 250, 16);
				}
			} else if (toxicGem != null && ctx.inventory.populate().filterContains("Uncut toxic gem").isEmpty()) {
				status = "Attacking";
				ctx.log("Attacking - Toxic");
				hespori.interact(SimpleNpcActions.ATTACK);
				ctx.onCondition(() -> ctx.inventory.populate().filter("Toxic gem").isEmpty(), 250, 3);
			}
			if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full
					&& System.currentTimeMillis() > (lastAnimation + 2000) && !chiselStone && hesporiEssence == null) {
				status = "Mining";
				ctx.log("Mining");
				if (enchantedStone != null) {
					enchantedStone.interact("Mine");
				} else {
					if (burningOre != null && !ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full && System.currentTimeMillis() > (lastAnimation + 2000)) {
						burningOre.interact("Mine");
						lastAnimation = System.currentTimeMillis();
					}
				}
				ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
			}
			if (hesporiEssence != null) {
				hesporiEssence.interact(SimpleItemActions.DROP);
			}
		} else if (phase == 3) {
			SimpleItem hesporiEssence = ctx.inventory.populate().filter("Hespori essence").reverse().next();
			SimpleItem toxicGem = ctx.inventory.populate().filter("Toxic gem").next();
			SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
			SimpleItem burningRune = ctx.inventory.populate().filter("Burning rune").next();
			SimpleGameObject burningOre = (SimpleGameObject) ctx.objects.populate().filter("Burning Ore").nearest().next();
			SimpleNpc hespori = ctx.npcs.populate().filter(8583).next();
			SimpleGameObject fire = (SimpleGameObject) ctx.objects.populate().filterHasAction("Burn-essence").nearest()
					.next();
	    	if (hespori != null && hespori.getHealth() <= 500) {
	    		phase = 4;
	    	}
			if (chiselStone) {
				chiselStone = false;
			}
	    	if (hesporiEssence != null) {
				hesporiEssence.interact(SimpleItemActions.DROP);
			}
			if (toxicGem != null) {
				status = "Attacking";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Attacking - Toxic - Phase 3");
				}
				hespori.interact(SimpleNpcActions.ATTACK);
				ctx.onCondition(() -> ctx.inventory.populate().filter("Toxic gem").isEmpty(), 250, 3);
			} else if (uncutToxicGem != null) {
				uncutToxicGem.interact(SimpleItemActions.DROP);
				return;
			}
			if (burningRune != null) {
				hespori.interact(SimpleNpcActions.ATTACK);
				status = "Attacking";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Attacking - Burning");
				}
				return;
			}
			if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full && System.currentTimeMillis() > (lastAnimation + 2000)) {
				if (burningOre != null) {
					status = "Mining";
					if (!ctx.pathing.inMotion()) {
						ctx.log("Mining");
					}
					if (ctx.pathing.distanceTo(burningOre) >= 8) {
						ctx.pathing.step(burningOre);
					} else {
						burningOre.interact("Mine");
						lastAnimation = System.currentTimeMillis();
						ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
					}
				}
			} else if ((ctx.inventory.inventoryFull() || full) && fire != null && ctx.pathing.distanceTo(fire) >= 3) {
				status = "Converting Runes";
				ctx.log("Converting Runes");
				fire.interact(900);
				ctx.onCondition(() -> ctx.inventory.populate().filter(9698).isEmpty(), 250, 8);
				return;
			}
		} else if (phase == 4) {
			SimpleGameObject deadHespori = (SimpleGameObject) ctx.objects.populate().filter("Hespori").nearest().next();
			walked = false;
			if (deadHespori != null) {
				deadHespori.interact(502);
				status = "Attacking";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Attacking - Dead");
				}
				ctx.onCondition(() -> HESPORI_WAITING.containsPoint(ctx.players.getLocal()), 250, 36);
				if (HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
					phase = 0;
					ctx.sleep(1200, 4600);
				}
			}
		} else if (phase == 5) {
			int uncutGemCount = ctx.inventory.populate().filterContains("Uncut toxic").population(true);
			SimpleItem chisel = ctx.inventory.populate().filter(1755).next();
			SimpleItem uncutToxicGem = ctx.inventory.populate().filter("Uncut toxic gem").reverse().next();
			SimpleGameObject enchantedStone = (SimpleGameObject) ctx.objects.populate().filter("Enchanted stone").nearest()
					.next();
			teleported = false;
			if (uncutGemCount >= 20) {
				chiselStone = true;
			}
			if (chiselStone) {
				if (ctx.inventory.populate().filter("Uncut toxic gem").isEmpty()) {
					chiselStone = false;
				} else if (uncutToxicGem != null && chisel != null) {
					status = "Chiseling";
					ctx.log("Chiseling");
					uncutToxicGem.interact(SimpleItemActions.USE);
					ctx.sleep(600);
					chisel.interact(SimpleItemActions.USE_WITH);
					ctx.onCondition(() -> ctx.inventory.populate().filter("Uncut toxic gem").isEmpty(), 250, 16);
				}
			}
			if (!ctx.players.getLocal().isAnimating() && !ctx.inventory.inventoryFull() && !full
					&& System.currentTimeMillis() > (lastAnimation + 2000)) {
				status = "Mining";
				ctx.log("Mining");
				if (enchantedStone != null) {
					enchantedStone.interact("Mine");
					lastAnimation = System.currentTimeMillis();
				} else {
					ctx.pathing.walkToTile(phase5Bug);
				}
				ctx.onCondition(() -> ctx.players.getLocal().isAnimating(), 250, 3);
			}
		}
	}

	public void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.healthPercent() <= 65 && ctx.combat.health() != 10) {
			status = "Eating Food";
			ctx.log("Eating Food");
			food.interact(74);
		} else if (brew != null && ctx.combat.healthPercent() <= 70 && ctx.combat.health() != 10) {
			status = "Drinking Brew";
			ctx.log("Drainking Brew");
			brew.interact(74);
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
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You have run out of inventory space.")) {
				full = true;
			} else if (event.getMessage().contains("Hespori world boss has spawned")) {
				spawned = true;
			} else if (event.getMessage().contains("Hespori's defence has been lowered")
					|| event.getMessage().contains("enough essence")) {
				phase = 2;
			} else if (event.getMessage().contains("Hespori is now extremely weak")) {
				phase = 3;
			} else if (event.getMessage().contains("Enough essence has already")) {
				phase = 2;
			} else if (event.getMessage().contains("Enough essence has burned")) {
				phase = 2;
			} else if (event.getMessage().contains("You have no more free")) {
				full = true;
			} else if (event.getMessage().contains("You cannot do this right now")) {
				phase = 4;
			} else if (event.getMessage().contains("essence are still required to be burned")) {
				phase = 2;
			} else if (event.getMessage().contains("You must have at least one free inventory")) {
				if (ctx.inventory.populate().filter("Unfired burning rune").next() != null) {
					ctx.inventory.populate().filter("Unfired burning rune").next().interact(SimpleItemActions.DROP);
				}
			} else if (event.getMessage().contains("Hespori's magic prevents you from")) {
				phase = 0;
			}
		}
	}

	@Override
	public int loopDuration() {
		return 150;
	}
}