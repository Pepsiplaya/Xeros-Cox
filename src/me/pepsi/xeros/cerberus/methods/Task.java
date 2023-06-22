package me.pepsi.xeros.cerberus.methods;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleNpc;

public class Task {
	
	final static ClientContext ctx = ClientContext.instance();

	public static void teleport() {
		if (Cerberus.getTask && ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty()) { // teleports out of cerb once task is complete and loot is picked up
			ctx.prayers.disableAll();
			ctx.magic.castHomeTeleport();
    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 36);
		}
	}
	
	public static void getTask() {
		SimpleNpc task = ctx.npcs.populate().filter(5870).nearest().next();
		if (Cerberus.getTask) {
			if (task != null && !ctx.dialogue.dialogueOpen()) {
				task.interact(225);
			} else if (task == null) {
				ctx.prayers.disableAll();
				ctx.teleporter.teleportPrevious();
				ctx.onCondition(() -> task != null, 250, 8);
			}
			if (ctx.widgets.getBackDialogId() == 4882 || ctx.widgets.getBackDialogId() == 4893) {
				Cerberus.getTask = false;
				ctx.sleep(600);
			}
		} else {
			Pathing.door();
		}
	}
}
