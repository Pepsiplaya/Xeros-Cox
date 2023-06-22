package me.pepsi.xeros.nex.methods;

import me.pepsi.xeros.nex.core.Nex;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleGameObject;

public class Pathing {
	final static ClientContext ctx = ClientContext.instance();

	public static void door1() {
		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
		SimpleGameObject door = (SimpleGameObject) ctx.objects.populate().filter("Frozen Door").nearest().next();
		Nex.status = "Opening Door";
		if (door != null) {
			door.interact(502);
			ctx.onCondition(() -> ctx.pathing.onTile(Nex.nex2), 250, 24);
		}
	}

	public static void door2() {
		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
		SimpleGameObject door = (SimpleGameObject) ctx.objects.populate().filter("Door").nearest().next();
		Nex.status = "Opening Door";
		door.interact(502);
		ctx.onCondition(() -> ctx.pathing.onTile(Nex.nex3), 250, 8);
	}
}
