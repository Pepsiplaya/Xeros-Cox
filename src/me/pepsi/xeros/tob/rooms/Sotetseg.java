package me.pepsi.xeros.tob.rooms;

import me.pepsi.xeros.tob.Tob;
import me.pepsi.xeros.tob.util.Util;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleWallObject;

public class Sotetseg {
	static ClientContext ctx = ClientContext.instance();
	static WorldPoint sotetsegWalk = new WorldPoint(3279, 4323, 0);
	static boolean sotetsegDead = false;
	static boolean sotetsegSwap = false;
	static WorldPoint sotetsegStart = new WorldPoint(3279, 4308, 0);
	
	public static void roomFour() {
		SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Barrier").shuffle().next();
		SimpleNpc mv = sotetseg().nearest().next();
		SimpleNpc sotetseg = mv != null ? mv : sotetseg().nearest().next();
		if (Util.ROOM_FOUR.containsPoint(ctx.players.getLocal())) {
			SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
			SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
			SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
			SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
			SimpleItem prims = ctx.inventory.populate().filter(13239).next();
			SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
			SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
			SimpleItem torture = ctx.inventory.populate().filter(19553).next();
			SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
			SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
			SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
			SimpleItem twistedbow = ctx.inventory.populate().filter(20997).next();
			SimpleItem assemblermax = ctx.inventory.populate().filter(21898).next();
			SimpleItem archerringi = ctx.inventory.populate().filter(11771).next();
			SimpleItem zarytevambs = ctx.inventory.populate().filter(26235).next();
			SimpleItem anguish = ctx.inventory.populate().filter(19547).next();
			SimpleItem pegs = ctx.inventory.populate().filter(13237).next();
			SimpleItem voidrange = ctx.inventory.populate().filter(11664).next();
			SimpleItem voidrangeor = ctx.inventory.populate().filter(26475).next();
			SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
			SimpleItem assembler = ctx.inventory.populate().filter(22109).next();
			SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
			SimpleItem whip = ctx.inventory.populate().filter(4151).next();
			SimpleItem bos = ctx.inventory.populate().filter(23995).next();
			SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
			SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
			SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
			SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
			if (sotetsegSwap == false && sotetsegDead) {
				if (scythe != null) {
					if (berserkerringi != null) {
						berserkerringi.interact(SimpleItemActions.WEAR);
					}
					if (barrowsgloves != null) {
						barrowsgloves.interact(SimpleItemActions.WEAR);
					}
					if (ferociousgloves != null) {
						ferociousgloves.interact(SimpleItemActions.WEAR);
					}
					if (abyssaltentacle != null) {
						abyssaltentacle.interact(SimpleItemActions.WEAR);
					}
					if (torture != null) {
						torture.interact(SimpleItemActions.WEAR);
					}
					if (firemaxcape != null) {
						firemaxcape.interact(SimpleItemActions.WEAR);
					}
					if (firecape != null) {
						firecape.interact(SimpleItemActions.WEAR);
					}
					if (infernalmaxcape != null) {
						infernalmaxcape.interact(SimpleItemActions.WEAR);
					}
					if (infernalcape != null) {
						infernalcape.interact(SimpleItemActions.WEAR);
					}
					if (prims != null) {
						prims.interact(SimpleItemActions.WEAR);
					}
					if (voidmelee != null) {
						voidmelee.interact(SimpleItemActions.WEAR);
					}
					if (voidmeleeor != null) {
						voidmeleeor.interact(SimpleItemActions.WEAR);
					}
					scythe.interact(SimpleItemActions.WEAR);
				} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
					if (archerringi != null) {
						archerringi.interact(SimpleItemActions.WEAR);
					}
					if (zarytevambs != null) {
						zarytevambs.interact(SimpleItemActions.WEAR);
					}
					if (anguish != null) {
						anguish.interact(SimpleItemActions.WEAR);
					}
					if (pegs != null) {
						pegs.interact(SimpleItemActions.WEAR);
					}
					if (assemblermax != null) {
						assemblermax.interact(SimpleItemActions.WEAR);
					}
					if (assembler != null) {
						assembler.interact(SimpleItemActions.WEAR);
					}
					if (voidrange != null) {
						voidrange.interact(SimpleItemActions.WEAR);
					}
					if (voidrangeor != null) {
						voidrangeor.interact(SimpleItemActions.WEAR);
					}
					if (twistedbow != null) {
						twistedbow.interact(SimpleItemActions.WEAR);
					}
				} else {
					if (bosc != null) {
						bosc.interact(SimpleItemActions.WEAR);
					}
					if (rapier != null) {
						rapier.interact(SimpleItemActions.WEAR);
					}
					if (bos != null) {
						bos.interact(SimpleItemActions.WEAR);
					}
					if (abyssaltentacle != null) {
						abyssaltentacle.interact(SimpleItemActions.WEAR);
					}
					if (avernic != null) {
						avernic.interact(SimpleItemActions.WEAR);
					}
					if (voidmelee != null) {
						voidmelee.interact(SimpleItemActions.WEAR);
					}
					if (voidmeleeor != null) {
						voidmeleeor.interact(SimpleItemActions.WEAR);
					}
					if (dragonboots != null) {
						dragonboots.interact(SimpleItemActions.WEAR);
					}
					if (infernalmaxcape != null) {
						infernalmaxcape.interact(SimpleItemActions.WEAR);
					}
					if (infernalcape != null) {
						infernalcape.interact(SimpleItemActions.WEAR);
					}
					if (firecape != null) {
						firecape.interact(SimpleItemActions.WEAR);
					}
					if (firemaxcape != null) {
						firemaxcape.interact(SimpleItemActions.WEAR);
					}
					if (torture != null) {
						torture.interact(SimpleItemActions.WEAR);
					}
					if (whip != null) {
						whip.interact(SimpleItemActions.WEAR);
					}
					if (prims != null) {
						prims.interact(SimpleItemActions.WEAR);
					}
					if (berserkerringi != null) {
						berserkerringi.interact(SimpleItemActions.WEAR);
					}
					if (ferociousgloves != null) {
						ferociousgloves.interact(SimpleItemActions.WEAR);
					}
					if (barrowsgloves != null) {
						barrowsgloves.interact(SimpleItemActions.WEAR);
					}
				}
				sotetsegSwap = true;
			}
			if (sotetsegDead) {
				SimpleWallObject exit = (SimpleWallObject) ctx.objects.populate().filter("Formidable passage").nearest().next();
				SimpleGameObject chest = (SimpleGameObject) ctx.objects.populate().filter("Chest").nearest().next();
				if ((System.currentTimeMillis() - Tob.lastOverload) <= 280000) {
					Tob.lastOverload = (System.currentTimeMillis() - 605000);
					ctx.sleep(600);
					Util.handleRangePotion();
					Tob.lastOverload = System.currentTimeMillis();
				}
				if (chest != null && Tob.restock && ctx.widgets.getOpenInterfaceId() != 21490 && ctx.inventory.getFreeSlots() > 1) {
					chest.interact(502);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 21490, 250, 8);
				}
				if (ctx.widgets.getOpenInterfaceId() == 21490 && Tob.restock) {
					if (ctx.inventory.getFreeSlots() > 1) {
						ctx.menuActions.sendAction(632, 6685, 2, 21493);
						ctx.sleep(650);
					}
					if (ctx.inventory.getFreeSlots() <= 1) {
						Tob.restock = false;
					}
					return;
				}
				if (exit != null && (!Tob.restock || ctx.inventory.getFreeSlots() <= 1)) {
					ctx.prayers.disableAll();
					Tob.status = "Pathing to next room";
					ctx.log("Pathing to next room");
					if ((System.currentTimeMillis() - Tob.lastOverload) <= 240000) {
						Tob.lastOverload = (System.currentTimeMillis() - 605000);
						ctx.sleep(600);
						Util.handleRangePotion();
						Tob.lastOverload = System.currentTimeMillis();
					}
					exit.interact(502);
					ctx.onCondition(() -> Util.ROOM_FIVE.containsPoint(ctx.players.getLocal()), 250, 3);
				}
			} else if (barrier != null && Tob.isLeader) {
				Tob.status = "Pathing to barrier";
				ctx.log("Pathing to barrier");
				barrier.interact(502);
				SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
				if (dwh != null) {
					if (dwh != null) {
						dwh.interact(SimpleItemActions.WEAR);
					}
					if (infernalmaxcape != null) {
						infernalmaxcape.interact(SimpleItemActions.WEAR);
					}
					if (infernalcape != null) {
						infernalcape.interact(SimpleItemActions.WEAR);
					}
					if (firemaxcape != null) {
						firemaxcape.interact(SimpleItemActions.WEAR);
					}
					if (firecape != null) {
						firecape.interact(SimpleItemActions.WEAR);
					}
					if (torture != null) {
						torture.interact(SimpleItemActions.WEAR);
					}
					if (prims != null) {
						prims.interact(SimpleItemActions.WEAR);
					}
					if (berserkerringi != null) {
						berserkerringi.interact(SimpleItemActions.WEAR);
					}
					if (barrowsgloves != null) {
						barrowsgloves.interact(SimpleItemActions.WEAR);
					}
					if (ferociousgloves != null) {
						ferociousgloves.interact(SimpleItemActions.WEAR);
					}
					if (voidmeleeor != null) {
						voidmeleeor.interact(SimpleItemActions.WEAR);
					}
					if (voidmelee != null) {
						voidmelee.interact(SimpleItemActions.WEAR);
					}
					if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
						ctx.inventory.populate().filterContains("defender").next().interact(454);
					}
				}
				barrier.interact(502);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal()), 250, 24);
				if (ctx.widgets.getBackDialogId() == 2459) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
					ctx.onCondition(() -> Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
				}
			}
		} else if (Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal())) {
			if (Tob.rangeProjectile) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					ctx.sleep(1, 600);
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
					Tob.status = "Switching To Range Prayers";
					ctx.log("Switching To Range Prayers");
					Tob.rangeProjectile = false;
				}
			}
			if (Tob.mageProjectile) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.sleep(1, 600);
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
					Tob.status = "Switching To Mage Prayers";
					ctx.log("Switching To Mage Prayers");
					Tob.mageProjectile = false;
				}
			}
			Tob.restock = true;
			if ((!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(25867).isEmpty() || !ctx.equipment.populate().filter(25865).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) && !sotetsegDead) {
				if (Tob.rangeType == -2) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				} else if (Tob.rangeType == -1) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				}
			} else if ((!ctx.equipment.populate().filter(22325).isEmpty() || !ctx.equipment.populate().filter(23995).isEmpty() || !ctx.equipment.populate().filter(24551).isEmpty() || !ctx.equipment.populate().filter(12006).isEmpty() || !ctx.equipment.populate().filter(4151).isEmpty()) && !sotetsegDead) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
			} //11329
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.handleRangePotion();
			Util.dropVial();
			if (ctx.pathing.distanceTo(sotetsegStart) <= 2 && !sotetsegDead) {
				Tob.status = "Pathing to Sotetseg";
				ctx.log("Pathing to Sotetseg");
				Util.dwhSpecial();
				ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, 8));
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				ctx.onCondition(() -> sotetseg != null, 250, 3);
			}
			if (sotetseg != null) {
				if (sotetseg.isDead()) {
					sotetsegDead = true;
					if ((System.currentTimeMillis() - Tob.lastOverload) <= 280000) {
						Tob.lastOverload = (System.currentTimeMillis() - 605000);
						ctx.sleep(600);
						Util.handleRangePotion();
						ctx.sleep(600);
					}
					if (barrier != null) {
						Tob.status = "Pathing to barrier";
						ctx.log("Pathing to barrier");
						if (ctx.pathing.distanceTo(barrier) <= 10) {
							ctx.prayers.disableAll();
							barrier.interact(502);
							ctx.onCondition(() -> Util.ROOM_FOUR.containsPoint(ctx.players.getLocal()), 250, 24);
						} else {
							ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -8));
						}
					} else if (sotetsegDead) {
						Tob.status = "Pathing to barrier";
						ctx.log("Pathing to barrier");
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -8));
					} else {
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, 8));
					}
				}
				Util.dwhSpecial();
				Util.useSpecialBP();
				if (ctx.players.getLocal().getInteracting() == null) {
					Tob.status = "Attacking Sotetseg";
					ctx.log("Attacking Sotetseg");
					sotetseg.interact(SimpleNpcActions.ATTACK);
				}
			} else {
				if (ctx.players.getLocal().getLocalX() >= 6336) {
					sotetsegDead = true;
				}
				if (barrier != null && sotetsegDead) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
					if (ctx.pathing.distanceTo(barrier) <= 10) {
						ctx.prayers.disableAll();
						barrier.interact(502);
						ctx.onCondition(() -> Util.ROOM_FOUR.containsPoint(ctx.players.getLocal()), 250, 8);
					} else {
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -8));
					}
				} else if (sotetsegDead) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
					ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -8));
				} else {
					ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, 8));
				}
			} 
		}
	}
	
	public final static SimpleEntityQuery<SimpleNpc> sotetseg() {
		return ctx.npcs.populate().filter(8388).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
}
