package me.pepsi.xeros.raids.methods;

import me.pepsi.xeros.raids.Raids;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleGroundItem;

public class Looting {
	static void loot() {
		final ClientContext ctx = ClientContext.instance();
		SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
		if (ctx.groundItems.populate().filterContains(Raids.lootNames) != null && item != null && ctx.inventory.canPickupItem(item)) {
			Raids.status = "Looting " + item.getName();
			ctx.log("Looting " + item.getName());
			item.interact();
			return;
		} else {
			Rooms.nextRoom();
		}
	}
}
