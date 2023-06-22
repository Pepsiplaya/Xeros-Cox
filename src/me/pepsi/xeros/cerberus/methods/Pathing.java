package me.pepsi.xeros.cerberus.methods;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;

public class Pathing {
	final static ClientContext ctx = ClientContext.instance();
	
	final WorldPoint start = new WorldPoint(1310, 1248);
	final WorldPoint walk = new WorldPoint(1310, 1265);

	public static void door() {
		Cerberus.presetLoaded = false;
		Cerberus.dead = false;
		SimpleGameObject winch = (SimpleGameObject) ctx.objects.populate().filter("Iron Winch").nearest().next();
		if (Cerberus.OUTSIDE_CERBERUS.containsPoint(ctx.players.getLocal())) {
			int food = ctx.inventory.populate().filterHasAction("Eat").population(true);
			int brews = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
			if (food >= 4 || brews >= 4) {
				SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
				if (dwh != null) {
					dwh.interact(SimpleItemActions.WEAR);
				}
				if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
					ctx.inventory.populate().filterContains("defender").next().interact(454);
				}
				if (winch != null) {
					winch.interact(502);
					Potions.rangePotion();
					Combat.handleEatingFood();
					Cerberus.status = "Starting Fight";
					ctx.sleep(600);
					if (!ctx.pathing.inMotion()) {
						ctx.log("Starting Fight");
					}
				}
			} else {
				ctx.prayers.disableAll();
				ctx.magic.castHomeTeleport();
    			ctx.sleep(600);
    			Cerberus.status = "Restocking";
    			ctx.log("Restocking, out of supplies");
    			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			}
		}
	}
}
