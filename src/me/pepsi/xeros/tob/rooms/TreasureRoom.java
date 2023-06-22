package me.pepsi.xeros.tob.rooms;

import me.pepsi.xeros.tob.Tob;
import me.pepsi.xeros.tob.util.Util;
import simple.api.ClientContext;

public class TreasureRoom {
	static ClientContext ctx = ClientContext.instance();
	
	public static void treasureRoom() {
		if (Util.TREASURE_ROOM.containsPoint(ctx.players.getLocal())) {
			Tob.status = "Teleporting to ToB";
			ctx.log("Teleporting to ToB");
			ctx.keyboard.sendKeys("");
			ctx.keyboard.sendKeys("::tob");
			ctx.onCondition(() -> Util.WAITING_ROOM.containsPoint(ctx.players.getLocal()), 250, 16);
		}
	}
}
