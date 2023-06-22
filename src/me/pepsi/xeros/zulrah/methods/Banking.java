package me.pepsi.xeros.zulrah.methods;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;

public class Banking {
    
    @SuppressWarnings("deprecation")
	public static void loadPreset(ClientContext ctx) {
		if (ctx.widgets.getBackDialogId() == 306) {
			ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
		}
    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
    	final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
    	
		if (!Zulrah.presetLoaded && Zulrah.WAITING_AREA.containsPoint(ctx.players.getLocal())) {
			Zulrah.status = "Loading Preset";
			ctx.log("Loading Preset");
			ctx.quickGear.latest();
		} else if (!Zulrah.presetLoaded && Zulrah.HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.players.getLocal().getLocation().equals(homeBank)) {
				Zulrah.status = "Walking Closer To Bank";
				ctx.log("Walking Closer To Bank");
				ctx.pathing.walkToTile(homeBank);
			} else {
				Zulrah.status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			}
		}
		if (Zulrah.presetLoaded) {
			SimpleItem teleport = ctx.inventory.populate().filter(12938).next();
			Zulrah.status = "Teleporting To Zulrah";
			ctx.log("Teleporting To Zulrah");
			if (teleport != null) {
				teleport.interact(74);
				ctx.sleep(4500, 6000);
			} else {
				if (Zulrah.teleport) {
					ctx.teleporter.teleportPrevious();
					Zulrah.dead = false;
					ctx.onCondition(() -> ctx.players.getLocal().getLocation().getRegionID() == 8751, 250, 36);
				} else {
					ctx.teleporter.open();
					ctx.teleporter.teleportStringPath("Bosses", "Zulrah");
					Zulrah.dead = false;
					Zulrah.teleport = true;
					ctx.onCondition(() -> ctx.players.getLocal().getLocation().getRegionID() == 8751, 250, 36);
				}
			}
		}
	}
    
    public static void heal(ClientContext ctx) {
    	SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
	    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				Zulrah.status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}
		}
    }
}
