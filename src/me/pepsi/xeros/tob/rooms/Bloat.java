package me.pepsi.xeros.tob.rooms;

import java.util.Random;

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

public class Bloat {
	static ClientContext ctx = ClientContext.instance();
	static WorldPoint bloatStart = new WorldPoint(3322, 4448, 0);
	static WorldPoint bloatWalk = new WorldPoint(3310, 4448, 0);
	static WorldPoint bloatFightStart = new WorldPoint(3303, 4447, 0);
	static WorldPoint bloatStand1 = new WorldPoint(3293, 4451, 0);
	static WorldPoint bloatWalk1 = new WorldPoint(3292, 4446, 0);
	static WorldPoint bloatStand2 = new WorldPoint(3298, 4444, 0);
	static WorldPoint bloatWalk2 = new WorldPoint(3299, 4445, 0);
	static WorldPoint bloatStand3 = new WorldPoint(3299, 4450, 0);
	static WorldPoint bloatWalk3 = new WorldPoint(3296, 4451, 0);
	static WorldPoint bloatStand4 = new WorldPoint(3293, 4451, 0);
	static WorldPoint bloatStand4temp = new WorldPoint(3292, 4450, 0);
	static WorldPoint bloatWalk4 = new WorldPoint(3292, 4444, 0);
	static WorldPoint bloatStand5 = new WorldPoint(3298, 4444, 0);
	static WorldPoint bloatWalk5 = new WorldPoint(3299, 4445, 0);
	static WorldPoint bloatStand6 = new WorldPoint(3299, 4450, 0);
	static WorldPoint bloatWalk6 = new WorldPoint(3294, 4451, 0);
	static WorldPoint bloatStand7 = new WorldPoint(3293, 4451, 0);
	static WorldPoint bloatStand7temp = new WorldPoint(3292, 4450, 0);
	static WorldPoint bloatWalk7 = new WorldPoint(3293, 4444, 0);
	static WorldPoint bloatStand8 = new WorldPoint(3298, 4444, 0);
	static WorldPoint bloatWalk8 = new WorldPoint(3299, 4446, 0);
	static WorldPoint bloatStand9 = new WorldPoint(3299, 4450, 0);
	static WorldPoint bloatStand9temp = new WorldPoint(3298, 4451, 0);
	static WorldPoint bloatWalk9 = new WorldPoint(3299, 4446, 0);
	static WorldPoint bloatLocation = new WorldPoint(3301, 4446, 1);
	static WorldPoint bloatExitBarrier = new WorldPoint(3288, 4447, 0);
	static WorldPoint roomThreeStart = new WorldPoint(3296, 4283, 0);
	static WorldPoint roomThreeWalk = new WorldPoint(3295, 4265, 0);
	static WorldPoint roomThreeBarrier = new WorldPoint(3297, 4256, 0);
	
	public static void roomTwo() {
		SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Barrier").filterWithin(15).shuffle().next();
		SimpleNpc mv2 = bloat().nearest().next();
		SimpleNpc bloat = mv2 != null ? mv2 : bloat().nearest().next();
		
		if (Util.ROOM_TWO.containsPoint(ctx.players.getLocal())) {
			if (ctx.pathing.distanceTo(bloatStart) <= 2) {
				Tob.status = "Pathing to barrier";
				ctx.log("Pathing to barrier");
				ctx.pathing.walkToTile(bloatWalk);
			} else {
				if (barrier != null) {
					barrier.interact(502);
					ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_TWO_FIGHT.containsPoint(ctx.players.getLocal()), 250, 16);
					if (ctx.widgets.getBackDialogId() == 2459) {
						ctx.menuActions.sendAction(315, -1, -1, 2461);
						ctx.onCondition(() -> Util.ROOM_TWO_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
					}
				}
			}
		} else if (Util.ROOM_TWO_FIGHT.containsPoint(ctx.players.getLocal())) {
			SimpleItem blowpipe = ctx.inventory.populate().filter(12926).next();
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.handleRangePotion();
			Util.dropVial();
			if (ctx.pathing.distanceTo(bloatFightStart) <= 2 && Tob.stage == 0) {
				if (blowpipe != null && ctx.equipment.populate().filterContains("Serpentine helm").isEmpty()) {
					blowpipe.interact(SimpleItemActions.WEAR);
					ctx.onCondition(() -> !ctx.equipment.populate().filter(12926).isEmpty(), 250, 3);
				}
				if (bloat != null && !ctx.equipment.populate().filter(12926).isEmpty() && ctx.equipment.populate().filterContains("Serpentine helm").isEmpty()) {
					if (ctx.players.getLocal().getInteracting() == null) {
						bloat.interact(SimpleNpcActions.ATTACK);
						Tob.status = "Poking with blowpipe";
						ctx.log("Poking with blowpipe");
						ctx.sleep(1000);
					}
				}
				Tob.status = "Pathing to safespot";
				ctx.log("Pathing to safespot");
				ctx.pathing.step(bloatStand1.derrive(between(-1, 1), between(-1, 1)));
				ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand1) <= 1, 250, 16);
				ctx.sleep(0, 1200);
				ctx.pathing.step(bloatStand1);
				ctx.onCondition(() -> ctx.pathing.onTile(bloatStand1), 250, 12);
			}
			if (bloat != null) {
				if (bloat.getHealth() <= 0) {
					if (ctx.pathing.distanceTo(bloatExitBarrier) >= 2) {
						Tob.status = "Pathing to barrier";
						ctx.log("Pathing to barrier");
						ctx.pathing.walkToTile(bloatExitBarrier);
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatExitBarrier) <= 2, 250, 16);
					} else {
						barrier.interact(502);
						ctx.onCondition(() -> Util.ROOM_TWO_EXIT.containsPoint(ctx.players.getLocal()), 250, 3);
					}
					return;
				}
				if (Tob.stage == 0) {
					if (ctx.pathing.distanceTo(bloatStand1) >= 2) {
						ctx.pathing.walkToTile(bloatStand1);
						equipGear();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand1), 250, 12);
					} else {
						Tob.stage = 1;
					}
				} else if (Tob.stage == 1) {
					equipGear();
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand1)) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk1) >= 2) {
							ctx.pathing.step(bloatWalk1.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk1) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk1) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatStand2.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand2) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatStand2);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand2) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatStand2)) {
						Tob.stage = 2;
					}
				} else if (Tob.stage == 2) {
					if (bloat.getLocalY() >= 5401 && bloat.getLocalY() <= 5770) {
						ctx.pathing.walkToTile(bloatWalk2);
						ctx.sleep(600);
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						bloat.interact(SimpleNpcActions.ATTACK);
					}
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand2) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (bloat.isAnimating() && !ctx.pathing.onTile(bloatWalk2)) {
							ctx.pathing.walkToTile(bloatWalk2);
						}
					} else if (bloat.isAnimating() && ctx.pathing.onTile(bloatWalk2)) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 54);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatStand3.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand3) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatStand3);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand3) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 16);
					 }
					if (ctx.pathing.onTile(bloatStand3)) {
						Tob.stage = 3;
					}
				} else if (Tob.stage == 3) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand3) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk3) >= 2) {
							ctx.pathing.step(bloatWalk3.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk3) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk3) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.walkToTile(bloatStand7temp);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand7temp) || bloat.getHealth() <= 0, 250, 18);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatStand7temp)) {
						ctx.sleep(2000, 3000);
						ctx.pathing.walkToTile(bloatStand4);
						Tob.stage = 4;
					}
					if (ctx.pathing.onTile(bloatStand4)) {
						Tob.stage = 4;
					}
				} else if (Tob.stage == 4) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand4) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk4) >= 2) {
							ctx.pathing.step(bloatWalk4.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk4) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk4) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatStand5.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand5) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatStand5);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand5) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatStand5)) {
						Tob.stage = 5;
					}
				} else if (Tob.stage == 5) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand5) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk5) >= 2) {
							ctx.pathing.step(bloatWalk5.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk5) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk5) <= 2) {
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatStand6.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand6) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatStand6);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand6) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatStand6)) {
						Tob.stage = 6;
					}
				} else if (Tob.stage == 6) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand6) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk6) >= 2) {
							ctx.pathing.step(bloatWalk6.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk6) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk6) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.walkToTile(bloatStand7temp);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand7temp) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatStand7temp)) {
						ctx.sleep(3000, 4000);
						ctx.pathing.walkToTile(bloatStand7);
						Tob.stage = 7;
					}
				} else if (Tob.stage == 7) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand7) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk7) >= 2) {
							ctx.pathing.step(bloatWalk7.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk7) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk7) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatWalk1.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk1) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatWalk1);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatWalk1) || bloat.getHealth() <= 0, 250, 416);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 20);
					}
					if (ctx.pathing.onTile(bloatWalk1)) {
						Tob.stage = 8;
					}
				} else if (Tob.stage == 8) {
					if (bloat.isAnimating() && ctx.pathing.onTile(bloatStand2) && bloat.getHealth() > 0) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (ctx.players.getLocal().getInteracting() == null) {
							bloat.interact(SimpleNpcActions.ATTACK);
						}
						if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk2) >= 2) {
							ctx.pathing.step(bloatWalk2.derrive(between(-1, 1), between(-1, 1)));
							ctx.onCondition(() -> ctx.pathing.distanceTo(bloatWalk2) <= 2, 250, 16);
						}
					} else if (bloat.isAnimating() && ctx.pathing.distanceTo(bloatWalk2) <= 2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						Tob.status = "Attacking Bloat";
						ctx.log("Attacking Bloat");
						bloat.interact(SimpleNpcActions.ATTACK);
						ctx.onCondition(() -> bloat.getHealth() <= 0, 250, 50);
						if (bloat.getHealth() <= 0) {
							return;
						}
						Tob.status = "Pathing to safespot";
						ctx.log("Pathing to safespot");
						ctx.pathing.step(bloatStand3.derrive(between(-1, 1), between(-1, 1)));
						ctx.onCondition(() -> ctx.pathing.distanceTo(bloatStand3) <= 1, 250, 16);
						ctx.sleep(0, 1200);
						ctx.pathing.step(bloatStand3);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> ctx.pathing.onTile(bloatStand3) || bloat.getHealth() <= 0, 250, 16);
						ctx.onCondition(() -> !bloat.isAnimating() || bloat.getHealth() <= 0, 250, 16);
					 }
					if (ctx.pathing.onTile(bloatStand3)) {
						Tob.stage = 3;
					}
				}
			} else {
				if (ctx.pathing.distanceTo(bloatExitBarrier) >= 2) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
					ctx.pathing.walkToTile(bloatExitBarrier);
					ctx.onCondition(() -> ctx.pathing.distanceTo(bloatExitBarrier) <= 2, 250, 16);
				} else {
					barrier.interact(502);
					ctx.onCondition(() -> Util.ROOM_TWO_EXIT.containsPoint(ctx.players.getLocal()), 250, 2);
				}
			}
		} else if (Util.ROOM_TWO_EXIT.containsPoint(ctx.players.getLocal())) {
			SimpleWallObject exit = (SimpleWallObject) ctx.objects.populate().filter("Formidable passage").nearest().next();
			SimpleItem torture = ctx.inventory.populate().filter(19553).next();
			SimpleItem fury = ctx.inventory.populate().filter(6585).next();
			SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
			SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
			SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
			SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
			SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
			SimpleItem prims = ctx.inventory.populate().filter(13239).next();
			SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
			SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
			SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
			SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
			SimpleItem defender = ctx.inventory.populate().filterContains("defender").next();
			SimpleItem dds = ctx.inventory.populate().filterContains("Dragon dagger").next();
			SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
			if ((System.currentTimeMillis() - Tob.lastOverload) >= 240000) {
				Tob.lastOverload = (System.currentTimeMillis() - 605000);
				ctx.sleep(600);
				Util.handleRangePotion();
				Tob.lastOverload = System.currentTimeMillis();
			}
			if (exit != null) {
				ctx.prayers.disableAll();
				Tob.status = "Pathing to next room";
				ctx.log("Pathing to next room");
				exit.interact(502);
				if (fury != null) {
					fury.interact(SimpleItemActions.WEAR);
				}
				if (dragonboots != null) {
					dragonboots.interact(SimpleItemActions.WEAR);
				}
				if (defender != null) {
					defender.interact(SimpleItemActions.WEAR);
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
				if (dds != null) {
					dds.interact(SimpleItemActions.WEAR);
				}
				ctx.onCondition(() -> Util.ROOM_THREE.containsPoint(ctx.players.getLocal()), 250, 3);
			}
		}
	}
	
	public static void equipGear() {
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
		SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
		SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
		SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
		SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
		SimpleItem prims = ctx.inventory.populate().filter(13239).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem torture = ctx.inventory.populate().filter(19553).next();
		SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
		SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
		SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
		SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
		SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
		SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
		SimpleItem salveei = ctx.inventory.populate().filter(12018).next();
		SimpleItem salvei = ctx.inventory.populate().filter(12017).next();
		SimpleItem salvee = ctx.inventory.populate().filter(10588).next();
		if (scythe != null || !ctx.equipment.populate().filter(22325).isEmpty()) {
			if (salveei != null) {
				salveei.interact(SimpleItemActions.WEAR);
			}
			if (salvei != null) {
				salvei.interact(SimpleItemActions.WEAR);
			}
			if (salvee != null) {
				salvee.interact(SimpleItemActions.WEAR);
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
			if (torture != null && ctx.equipment.populate().filter(12018).isEmpty()) {
				torture.interact(SimpleItemActions.WEAR);
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
			if (abyssaltentacle != null) {
				abyssaltentacle.interact(SimpleItemActions.WEAR);
			}
			if (scythe != null) {
				scythe.interact(SimpleItemActions.WEAR);
			}
		} else {
			if (salveei != null) {
				salveei.interact(SimpleItemActions.WEAR);
			}
			if (salvei != null) {
				salvei.interact(SimpleItemActions.WEAR);
			}
			if (salvee != null) {
				salvee.interact(SimpleItemActions.WEAR);
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
			if (dragondefender != null) {
				dragondefender.interact(SimpleItemActions.WEAR);
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
			if (torture != null && ctx.equipment.populate().filter(12018).isEmpty() && ctx.equipment.populate().filter(12017).isEmpty() &&ctx.equipment.populate().filter(10588).isEmpty()) {
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
		}
	}
	
	private static int between(final int min, final int max) {
		final Random random = new Random();
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}
	
	public final static SimpleEntityQuery<SimpleNpc> bloat() {
		return ctx.npcs.populate().filter(8359).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
}
