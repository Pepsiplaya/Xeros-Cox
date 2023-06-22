package me.pepsi.xeros.alchemicalhydra.methods;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleNpc;

public class Task {
	
	final static ClientContext ctx = ClientContext.instance();

	public static void teleport() {
		if (AlchemicalHydra.getTask && ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty()) {
			ctx.prayers.disableAll();
			ctx.magic.castHomeTeleport();
    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 36);
		}
	}
	
	public static void getTask() {
		SimpleNpc task = ctx.npcs.populate().filter(8605).nearest().next();
		if (AlchemicalHydra.getTask) {
			if (task != null && !ctx.dialogue.dialogueOpen()) {
				task.interact(20);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 4882, 250, 24);
			}
			if (ctx.widgets.getBackDialogId() == 4882) {
				ctx.dialogue.clickContinue();
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459, 250, 24);
			}
			if (ctx.widgets.getBackDialogId() == 2459) {
				if (ctx.inventory.populate().filter(995).population(true) >= 15000000) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
					ctx.onCondition(() -> ctx.inventory.populate().filter(995).population(true) < 15000000, 250, 24);
					if (ctx.inventory.populate().filter(995).population(true) < 15000000) {
						AlchemicalHydra.getTask = false;
					}
				} else {
					ctx.log("OUT OF CASH");
					ctx.stopScript();
				}
			}
		} else {
			Pathing.door();
		}
	}
}
