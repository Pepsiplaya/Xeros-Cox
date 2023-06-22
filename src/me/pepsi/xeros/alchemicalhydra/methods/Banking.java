package me.pepsi.xeros.alchemicalhydra.methods;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleWallObject;

public class Banking {
	final static ClientContext ctx = ClientContext.instance();
    
	@SuppressWarnings("deprecation")
	public static void loadPreset() {
		if (ctx.widgets.getBackDialogId() == 306) {
			ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
		}
    	SimpleWallObject tp = (SimpleWallObject) ctx.objects.populate().filterContains("Trading Post").nearest().next();
    	final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
		if (!AlchemicalHydra.presetLoaded && AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.players.getLocal().getLocation().equals(homeBank)) {
				AlchemicalHydra.status = "Walking Closer To Bank";
				ctx.pathing.walkToTile(homeBank);
				ctx.onCondition(() -> ctx.pathing.walkToTile(homeBank), 250, 16);
			} else {
				AlchemicalHydra.status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			}
		}
		if (AlchemicalHydra.getTask && ctx.inventory.populate().filter(995).population(true) < 15000000 && AlchemicalHydra.presetLoaded) {
    		if (!ctx.players.getLocal().getLocation().equals(homeBank)) {
    			AlchemicalHydra.status = "Walking Closer To Bank";
				ctx.pathing.walkToTile(homeBank);
				ctx.onCondition(() -> ctx.pathing.walkToTile(homeBank), 250, 16);
    		} else {
    			AlchemicalHydra.status = "Grabbing Cash";
				ctx.log("Grabbing Cash For Task");
				if (!ctx.bank.bankOpen() && ctx.widgets.getOpenInterfaceId() != 21553) {
					tp.interact(SimpleObjectActions.FIRST);
					ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 24);
				}
				if (ctx.bank.bankOpen()) {
					ctx.bank.withdraw(995, 15000000);
					ctx.bank.closeBank();
					ctx.onCondition(() -> ctx.bank.closeBank(), 250, 24);
				}
			}
    		if (ctx.inventory.populate().filter(995).population(true) >= 15000000 && AlchemicalHydra.presetLoaded && AlchemicalHydra.teleport && (!AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal()) && !AlchemicalHydra.HYDRA.containsPoint(ctx.players.getLocal()))) {
    			AlchemicalHydra.status = "Teleporting To Alchemical Hydra";
    			ctx.log("Teleporting To Alchemical Hydra");
    			ctx.teleporter.teleportPrevious();
    			ctx.onCondition(() -> AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal()), 250, 36);
    		} else if (ctx.inventory.populate().filter(995).population(true) >= 15000000 && AlchemicalHydra.presetLoaded && !AlchemicalHydra.teleport) {
    			AlchemicalHydra.status = "Teleporting To Alchemical Hydra";
    			ctx.log("Teleporting To Alchemical Hydra");
    			ctx.teleporter.open();
    			ctx.teleporter.teleportStringPath("Bosses", "Alchemical hydra");
    			ctx.onCondition(() -> AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal()), 250, 36);
    			AlchemicalHydra.teleport = true;
    		}
    	} else {
    		if (AlchemicalHydra.presetLoaded && AlchemicalHydra.teleport) {
    			AlchemicalHydra.status = "Teleporting To Alchemical Hydra";
    			ctx.log("Teleporting To Alchemical Hydra");
    			ctx.teleporter.teleportPrevious();
    			ctx.onCondition(() -> AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal()), 250, 36);
    		} else if (AlchemicalHydra.presetLoaded && !AlchemicalHydra.teleport) {
    			AlchemicalHydra.status = "Teleporting To Alchemical Hydra";
    			ctx.log("Teleporting To Alchemical Hydra");
    			ctx.teleporter.open();
    			ctx.teleporter.teleportStringPath("Bosses", "Alchemical hydra");
    			ctx.onCondition(() -> AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal()), 250, 36);
    			AlchemicalHydra.teleport = true;
    		}
    	}
	}
    
    public static void heal() {
    	SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
	    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				AlchemicalHydra.status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}
		}
    }
}
