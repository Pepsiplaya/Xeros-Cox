package me.pepsi.xeros.cerberus.methods;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleWallObject;

public class Banking {
	final static ClientContext ctx = ClientContext.instance();
    
    @SuppressWarnings("deprecation")
	public static void loadPreset() {
    	Cerberus.mageProjectile = false;
    	Cerberus.rangeProjectile = false;
		if (ctx.widgets.getBackDialogId() == 306) {
			ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
		}
    	SimpleWallObject tp = (SimpleWallObject) ctx.objects.populate().filterContains("Trading Post").nearest().next();
    	final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
    	if (!Cerberus.presetLoaded && Cerberus.RAIDS.containsPoint(ctx.players.getLocal())) {
    		Cerberus.status = "Loading Preset";
			ctx.log("Loading Preset");
			ctx.quickGear.latest();
		} else if (!Cerberus.presetLoaded && Cerberus.HOME.containsPoint(ctx.players.getLocal())) {
			if (ctx.players.getLocal().getLocation().distanceTo(homeBank) > 4) {
				Cerberus.status = "Walking Closer To Bank";
				ctx.pathing.walkToTile(homeBank);
				ctx.onCondition(() -> ctx.players.getLocal().getLocation().distanceTo(homeBank) <= 4, 250, 2);
			} else {
				Cerberus.status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			}
		}
		if (Cerberus.getTask && ctx.inventory.populate().filter(995).population(true) < 6000000 && Cerberus.presetLoaded) {
    		if (ctx.pathing.distanceTo(homeBank) > 4) {
				Cerberus.status = "Walking Closer To Bank";
				ctx.pathing.walkToTile(homeBank);
				ctx.onCondition(() -> ctx.pathing.walkToTile(homeBank), 250, 4);
			} else {
				Cerberus.status = "Grabbing Cash";
				ctx.log("Grabbing Cash For Task");
				if (!ctx.bank.bankOpen() && ctx.widgets.getOpenInterfaceId() != 21553) {
					tp.interact(SimpleObjectActions.FIRST);
					ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 24);
				}
				if (ctx.bank.bankOpen()) {
					ctx.bank.withdraw(995, 6000000);
					ctx.bank.closeBank();
					ctx.onCondition(() -> ctx.bank.closeBank(), 250, 24);
				}
			}
			if (ctx.inventory.populate().filter(995).population(true) >= 6000000 && Cerberus.presetLoaded && (!Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()) && !Cerberus.CERBERUS.containsPoint(ctx.players.getLocal()))) {
				if (Cerberus.teleport) {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.teleportPrevious();
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				} else {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.open();
					ctx.teleporter.teleportStringPath("Bosses", "Cerberus");
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				}
			}
		} else {
			SimpleItem teleport = ctx.inventory.populate().filter(13249).next();
    		if (!Cerberus.getTask && teleport != null && Cerberus.presetLoaded && (!Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()) && !Cerberus.CERBERUS.containsPoint(ctx.players.getLocal()))) {
	    		Cerberus.status = "Teleporting To Cerberus";
				ctx.log("Teleporting To Cerberus");
				ctx.prayers.disableAll();
				teleport.interact(74);
				Cerberus.dead = false;
			} else if (!Cerberus.getTask && Cerberus.presetLoaded && (!Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()) && !Cerberus.CERBERUS.containsPoint(ctx.players.getLocal()))) {
				if (Cerberus.teleport) {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.teleportPrevious();
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				} else {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.open();
					ctx.teleporter.teleportStringPath("Bosses", "Cerberus");
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				}
			}
    		if (Cerberus.getTask && ctx.inventory.populate().filter(995).population(true) >= 6000000 && Cerberus.presetLoaded && (!Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()) && !Cerberus.CERBERUS.containsPoint(ctx.players.getLocal()))) {
				if (Cerberus.teleport) {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.teleportPrevious();
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				} else {
					Cerberus.status = "Teleporting To Cerberus";
					ctx.log("Teleporting To Cerberus");
					ctx.teleporter.open();
					ctx.teleporter.teleportStringPath("Bosses", "Cerberus");
					ctx.onCondition(() -> Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal()), 250, 36);
					Cerberus.dead = false;
				}
			}
    	}
	}
    
    public static void heal() {
    	SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
	    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				Cerberus.status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}
		}
    }
}
