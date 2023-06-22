package me.pepsi.xeros.zulrah.methods;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;

public class Pathing {
	
    public static void boat(ClientContext ctx) {
    	final WorldPoint start = new WorldPoint(2268, 3069, 0);
    	Zulrah.dead = false;
    	SimpleGameObject boat = (SimpleGameObject) ctx.objects.populate().filter(10068).nearest().next();
    	if (boat != null) {
    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 36);
    		Zulrah.status = "Walking To Boat";
    		ctx.log("Walking To Boat");
    		boat.interact(SimpleObjectActions.USE);
    		ctx.onCondition(() -> ctx.players.getLocal().getLocation().equals(start), 250, 24);
    	}
    }
}
