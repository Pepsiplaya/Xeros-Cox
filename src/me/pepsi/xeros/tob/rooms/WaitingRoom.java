package me.pepsi.xeros.tob.rooms;

import me.pepsi.xeros.tob.Tob;
import me.pepsi.xeros.tob.util.Util;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleWidget;

public class WaitingRoom {
	static ClientContext ctx = ClientContext.instance();
	static WorldPoint waiting = new WorldPoint(3671, 3219, 0);
	static WorldPoint bank = new WorldPoint(3675, 3217, 0);
	
	@SuppressWarnings("deprecation")
	public static void waitingRoom() {
		if (Util.WAITING_ROOM.containsPoint(ctx.players.getLocal())) {
			Tob.hasMoved = false;
			Tob.acidWalk = false;
			Tob.onBlood = false;
			Tob.onAcid = false;
			Maiden.maidenDead = false;
			Nylocas.spiderSwap = false;
			Nylocas.spiderDead = false;
			Sotetseg.sotetsegSwap = false;
			Sotetseg.sotetsegDead = false;
			Tob.rangeWalk = true;
			Tob.restock = true;
			Tob.stage = 0;
			ctx.prayers.disableAll();
			if (!Tob.presetLoaded && ctx.pathing.distanceTo(bank) <= 4) {
				Tob.status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			} else {
				ctx.pathing.step(bank);
				ctx.sleep(1200);
			}
			if (Tob.lastOverload <= System.currentTimeMillis() - 150000) {
				ctx.pathing.step(bank);
				ctx.sleep(1800);
				ctx.quickGear.latest();
				SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
				if (overload != null && ctx.combat.health() >= 51) {
					Tob.status = "Using Overload";
					ctx.log("Using Overload");
					ctx.sleep(600, 2000);
					overload.interact(74);
					Tob.lastOverload = System.currentTimeMillis();
					ctx.sleep(3000);
					ctx.quickGear.latest();
				}
			}
			Util.heal();
			if (ctx.widgets.getBackDialogId() == 306) {
				ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
			}
			SimpleWidget party = ctx.widgets.populate().filter(30372).next();
			if (ctx.pathing.onTile(waiting)) {
				ctx.pathing.walkToTile(bank);
				ctx.onCondition(() -> ctx.pathing.distanceTo(bank) <= 4, 250, 8);
			}
			if (Tob.isLeader) {
				if (!party.getText().contains("/5)")) {
					Tob.status = "Creating Party";
					ctx.log("Creating Party");
					ctx.menuActions.sendAction(169, -1, -1, 30374);
					ctx.onCondition(() -> party.getText().contains("/5)"), 250, 8);
					return;
				}
				for (int i = 0; i <= Tob.leaderDelay + 1; i++) {
					System.out.print("Reaches " + Tob.leaderDelay + ", it will start raid - " + i + "\n");
					ctx.sleep(600);
					if (i >= Tob.leaderDelay) {
						SimpleGameObject wall = (SimpleGameObject) ctx.objects.populate().filter("Theatre of Blood").nearest().next();
						if (wall != null && Tob.presetLoaded) {
							Tob.status = "Entering Tob";
							ctx.log("Entering Tob");
							if (wall != null) {
								wall.interact(SimpleObjectActions.OPEN);
								ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2469, 250, 12);
							}
							if (ctx.widgets.getBackDialogId() == 2469) {
								ctx.menuActions.sendAction(315, -1, -1, 2471);
								ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 363, 250, 3);
							}
							if (ctx.widgets.getBackDialogId() == 363) {
								ctx.menuActions.sendAction(679, -1, -1, 367);
								ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459, 250, 16);
							}
							if (ctx.widgets.getBackDialogId() == 2459) {
								ctx.menuActions.sendAction(315, -1, -1, 2461);
								ctx.onCondition(() -> !Util.WAITING_ROOM.containsPoint(ctx.players.getLocal()), 250, 3);
							}
						}
						i = 0;
						break;
					}
				}
			} else {
				Tob.status = "Waiting On Leader";
				if (ctx.dialogue.dialogueOpen()) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
				}
			}
		}
	}
}
