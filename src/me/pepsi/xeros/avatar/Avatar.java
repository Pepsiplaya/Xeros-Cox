package me.pepsi.xeros.avatar;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Script;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Avatar", category = Category.OTHER, version = "1.0D", description = "Does Avatar event when it's time", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Avatar extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	private long lastOverload = -1;
	public static String status;
	public static boolean started = false;
	public static boolean staff = false;
	private static boolean presetLoaded = false;
	private boolean spawned = false;
	public static int prayer = 20;
	private long startTime;
	public static int prayerType;
	public static int slot = 1;
	private Image staffImage;
	final static WorldArea HOME = new WorldArea(new WorldPoint(3080, 3485, 0), new WorldPoint(3102, 3508, 0));

	public GUI gui;

	@Override
	public boolean onExecute() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		prayer = 20 + random;
		staff = false;
		startTime = System.currentTimeMillis();
		started = false;
		presetLoaded = false;
		spawned = false;
		try {
			staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
		} catch (IOException e) {
			ctx.log("ERROR LOADING PAINT");
		}
		try {
			Avatar script = this;
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

	@SuppressWarnings("deprecation")
	@Override
	public void onProcess() {
		if (!started) {
			return;
		}
		if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		SimpleNpc mv1 = avatar1(ctx).nearest().next();
		SimpleNpc avatar1 = mv1 != null ? mv1 : avatar1(ctx).nearest().next();
		SimpleNpc mv2 = avatar1(ctx).nearest().next();
		SimpleNpc avatar2 = mv2 != null ? mv2 : avatar2(ctx).nearest().next();
		SimpleItem key = ctx.inventory.populate().filter(23502).next();
		final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
		//ctx.log(chest.getName() + "");
		if (HOME.containsPoint(ctx.players.getLocal())) {
			ctx.prayers.disableAll();
			if (key != null) {
				SimpleGameObject chest = (SimpleGameObject) ctx.objects.populate().filterContains("Avatar").nearest().next();
				if (chest != null) {
					chest.interact(502);
					ctx.sleep(1200, 3000);
					return;	
				}
			} else {
				if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
					heal(ctx);	
				}
				if (!ctx.players.getLocal().getLocation().equals(homeBank) && !presetLoaded && key == null) {
					status = "Walking Closer To Bank";
					ctx.pathing.walkToTile(homeBank);
					ctx.onCondition(() -> ctx.pathing.walkToTile(homeBank), 250, 16);
				} else if (!presetLoaded) {
					status = "Loading Preset";
					ctx.log("Loading Preset");
					ctx.quickGear.latest();
					status = "Waiting for Avatar";
				}
			}
			if (spawned) {
				Toolkit.getDefaultToolkit().beep();
				ctx.sleep(2000, 7000);
				Toolkit.getDefaultToolkit().beep();
				ctx.keyboard.sendKeys("::crashevent");
				ctx.onCondition(() -> avatar1 != null || avatar2 != null, 250, 20);
				spawned = false;
			}
		} else {
			Staff.antiStaff();
			if (avatar1 != null) {
				presetLoaded = false;
				spawned = false;
				handlePrayer();
				handleDrinkingRange();
				handleEatingFood();
				handleDrinkingPrayer();
				if (ctx.players.getLocal().getInteracting() == null) {
					avatar1.interact(SimpleNpcActions.ATTACK);
				}
			} else if (avatar2 != null) {
				presetLoaded = false;
				spawned = false;
				handlePrayer();
				handleDrinkingRange();
				handleEatingFood();
				handleDrinkingPrayer();
				if (ctx.players.getLocal().getInteracting() == null) {
					avatar2.interact(SimpleNpcActions.ATTACK);
				}
			} else {
				if (!HOME.containsPoint(ctx.players.getLocal()) && ctx.pathing.distanceTo(homeBank) > 100) {
					ctx.prayers.disableAll();
					ctx.magic.castHomeTeleport();
					ctx.onCondition(() -> HOME.containsPoint(ctx.players.getLocal()), 250, 24);
				}
			}
		}
	}
	
	public final static SimpleEntityQuery<SimpleNpc> avatar1(ClientContext ctx) {
		return ctx.npcs.populate().filter(10531).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> avatar2(ClientContext ctx) {
		return ctx.npcs.populate().filter(10532).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
	
	public static void heal(ClientContext ctx) {
		final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
		if (ctx.pathing.distanceTo(homeBank) <= 10) {
			SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filterContains("Box of Health").nearest().next();
	    	SimpleItem key = ctx.inventory.populate().filter(23502).next();
	    	if (heal != null && key == null) {
				status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}	
		}
    }
	
	private void handleDrinkingRange() {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			ctx.log("Using Overload");
			overload.interact(74);
			ctx.sleep(600);
			lastOverload = System.currentTimeMillis();
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9) {
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedLevel - realLevel <= 2) {
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
			ctx.sleep(600);
		}
	}
	
	private void handlePrayer() {
		if (ctx.prayers.points() >= 1) {
			if (Avatar.prayerType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
	    			ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
	    		}
	    		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
	    			ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
	    		}
			} else if (Avatar.prayerType == -1) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
			}	
		}
	}
	
	public void handleDrinkingPrayer() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super r").isEmpty() && ctx.prayers.points() <= 20) {
			status = "Drinking Restore Potion";
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			prayer = 20 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= 20) {
			status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			prayer = 20 + random;
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

	@Override
	public void onTerminate() {
		if (gui != null) {
			GUI.frame.setVisible(false);
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(new Color(29, 3, 3, 94));
		g.fillRect(7, 283, 180, 45);
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1));
		g.drawRect(7, 283, 180, 45);
		g.setFont(new Font("Arial", 1, 10));
		g.setColor(new Color(255, 255, 255));
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
		g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 297);
		g.drawString("Status: " + status, 10, 310);
		g.drawString("Spawned: " + spawned, 10, 323);
	}
	
	public void setupPrayer(int prayerType) {
		Avatar.prayerType = prayerType;
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0) {
			if (event.getMessage().contains("is causing havoc")) {
				spawned = true;
			}
			if (event.getMessage().contains("You feel a drain on your memory")) {
				presetLoaded = true;
			}
		}
	}

	@Override
	public int loopDuration() {
		return 150;
	}
}