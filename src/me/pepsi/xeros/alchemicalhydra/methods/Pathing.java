package me.pepsi.xeros.alchemicalhydra.methods;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleWallObject;

public class Pathing {
	final static ClientContext ctx = ClientContext.instance();

	public static void door() {
		AlchemicalHydra.walkBoolean = false;
		AlchemicalHydra.presetLoaded = false;
		SimpleWallObject door = (SimpleWallObject) ctx.objects.populate().filter("Alchemical door").nearest().next();
		if (AlchemicalHydra.HYDRA.containsPoint(ctx.players.getLocal())) {
			AlchemicalHydra.status = "Restarting Fight";
			door.interact(502);
			ctx.sleep(600);
			if (!ctx.pathing.inMotion()) {
				ctx.log("Restarting Fight");
			}
		} else if (AlchemicalHydra.OUTSIDE_HYDRA.containsPoint(ctx.players.getLocal())) {
			if (ctx.inventory.populate().filter(22978).size() > 0 ) {
				ctx.inventory.populate().filter(22978).next().interact(454);
	            ctx.sleepCondition(() -> ctx.equipment.populate().filter(22978).size() > 0, 2000);
	        }
	        if (ctx.inventory.populate().filter(22322).size() > 0 ) {
	        	ctx.inventory.populate().filter(22322).next().interact(454);
	            ctx.sleepCondition(() -> ctx.equipment.populate().filter(22322).size() > 0, 2000);
	        }
			AlchemicalHydra.status = "Starting Fight";
			door.interact(502);
			ctx.sleep(600);
			if (!ctx.pathing.inMotion()) {
				ctx.log("Starting Fight");
			}
		}
	}
}
