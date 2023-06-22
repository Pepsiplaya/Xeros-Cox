package me.pepsi.xeros.tob.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import me.pepsi.xeros.tob.Tob;
import me.pepsi.xeros.tob.util.Util;
import simple.api.ClientContext;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleGraphicsObject;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleWallObject;

public class Maiden {
	static ClientContext ctx = ClientContext.instance();
	//static WorldPoint blood = null;
	//static WorldPoint acid = null;
	static WorldPoint roomOneFightStart = new WorldPoint(3184, 4446, 0);
	public static WorldPoint roomOneDodge1 = new WorldPoint(3168, 4444, 0);
	public static WorldPoint roomOneDodge2 = new WorldPoint(3168, 4449, 0);
	public static WorldPoint roomOneDodge3 = new WorldPoint(3171, 4449, 0);
	public static WorldPoint roomOneDodge4 = new WorldPoint(3171, 4444, 0);
	public static WorldPoint barrierPath = new WorldPoint(3210, 4448, 0);
	static boolean maidenDead = false;
	static WorldPoint roomOneBottomWalk = new WorldPoint(3180, 4432, 0);
	
	public static void roomOne() {
		SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Barrier").shuffle().next();
		if (Util.ROOM_ONE.containsPoint(ctx.players.getLocal())) {
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.handleRangePotion();
			Util.dropVial();
			Tob.presetLoaded = false;
			if (barrier != null && maidenDead == false) {
				barrier.interact(502);
				Tob.status = "Entering Maiden";
				ctx.log("Entering Maiden");
				ctx.sleep(600, 3600);
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 16);
				if (ctx.widgets.getBackDialogId() == 2459) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
					ctx.onCondition(() -> Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
				}
				return;
			} else if (maidenDead == false) {
				if (!ctx.pathing.inMotion()) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
				}
				ctx.pathing.step(barrierPath.derrive(between(-2, 2), between(-2, 2)));
			} else {
				ctx.prayers.disableAll();
				if (!ctx.pathing.inMotion()) {
					Tob.status = "Pathing to next room";
					ctx.log("Pathing to next room");
				}
				ctx.pathing.step(roomOneBottomWalk.derrive(between(-2, 2), between(-2, 2)));
			}
		} else if (Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal())) {
			SimpleNpc mv1 = maiden().nearest().next();
			SimpleNpc maiden = mv1 != null ? mv1 : maiden().nearest().next();
			if (maiden != null) {
				Util.handleEatingFood();
				maidenDead = false;
				if (maiden.isDead() && maiden.isAnimating()) {
					maidenDead = true;
					if (barrier != null) {
						Tob.status = "Pathing to barrier";
						ctx.log("Pathing to barrier");
						if (ctx.pathing.distanceTo(barrier) <= 10) {
							ctx.prayers.disableAll();
							barrier.interact(502);
							ctx.onCondition(() -> Util.ROOM_ONE.containsPoint(ctx.players.getLocal()), 250, 8);
						} else {
							ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(8, 0));
						}
					} else {
						Tob.status = "Pathing to barrier";
						ctx.log("Pathing to barrier");
						ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(8, 0));
					}
					return;
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && maidenDead == false) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if ((!ctx.equipment.populate().filter(20997).isEmpty()|| !ctx.equipment.populate().filter(12926).isEmpty()) && maidenDead == false) {
					if (Tob.rangeType == -2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
						}
					} else if (Tob.rangeType == -1) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
						}
					}
				} else if ((!ctx.equipment.populate().filter(22325).isEmpty() || !ctx.equipment.populate().filter(4151).isEmpty() || !ctx.equipment.populate().filter(23995).isEmpty() || !ctx.equipment.populate().filter(24551).isEmpty() || !ctx.equipment.populate().filter(12006).isEmpty()) && maidenDead == false) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				}
				Util.useSpecialDDS();
				Util.useSpecialBP();
		        final WorldPoint point = getBloodSpots();
				if (point != null) {
			        Tob.status = "Dodging Blood";
			        ctx.log("Dodging Blood");
					ctx.pathing.step(point);
				} else {
					ctx.log("No valid destination found.");
				}
				/*if (Tob.onBlood) {
			        WorldPoint dodgeTile;
			        if (!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) {
			            dodgeTile = ctx.pathing.distanceTo(roomOneDodge3) <= 2 ? roomOneDodge4 : roomOneDodge3;
			        } else {
			            dodgeTile = ctx.pathing.distanceTo(roomOneDodge1) <= 2 ? roomOneDodge2 : roomOneDodge1;
			        }
			        ctx.pathing.step(dodgeTile.derrive(between(-1, 1), between(-1, 1)));
			        ctx.onCondition(() -> ctx.pathing.onTile(dodgeTile), 250, 6);
			        Tob.onBlood = false;
			    }*/
				Util.handleEatingFood();
				Util.handleDrinkingPrayer();
				Util.handleRangePotion();
				Util.dropVial();
				if (!maiden.isDead() && ctx.players.getLocal().getInteracting() == null) {
					Tob.status = "Attacking Maiden";
					ctx.log("Attacking Maiden");
					maiden.interact(SimpleNpcActions.ATTACK);
				}
			} else {
				if (ctx.pathing.distanceTo(roomOneFightStart) <= 2 && maidenDead == false /*&& Tob.maidenStart*/) {
					Tob.status = "Pathing to Maiden";
					ctx.log("Pathing to Maiden");
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(-10, 0));
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && maidenDead == false) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
					}
					ctx.sleep(1800);
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(-10, 0));
					ctx.sleep(1600);
					if ((!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) && maidenDead == false) {
						if (Tob.rangeType == -2) {
							if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
								ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
							}
						} else if (Tob.rangeType == -1) {
							if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
								ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
								return;
							}
						}
					} else if (maidenDead == false) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
							return;
						}
					}
				}
				if (barrier != null && maidenDead) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
					if (ctx.pathing.distanceTo(barrier) <= 10) {
						barrier.interact(502);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> Util.ROOM_ONE.containsPoint(ctx.players.getLocal()), 250, 16);
					} else {
						ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(8, 0));
					}
				} else if (maidenDead) {
					Tob.status = "Pathing to barrier";
					ctx.log("Pathing to barrier");
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(8, 0));
				} else {
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(-8, 0));
				}
			}
			if (ctx.players.getLocal().getLocation().x <= 3171 && maiden == null && !maidenDead) {
				maidenDead = true;
			}
		} else if (Util.ROOM_ONE_BOTTOM.containsPoint(ctx.players.getLocal())) {
			SimpleWallObject exit = (SimpleWallObject) ctx.objects.populate().filter("Formidable passage").nearest().next();
			if ((System.currentTimeMillis() - Tob.lastOverload) >= 240000) {
				Tob.lastOverload = (System.currentTimeMillis() - 605000);
				ctx.sleep(600);
				Util.handleRangePotion();
				Tob.lastOverload = System.currentTimeMillis();
			}
			if (exit != null) {
				Tob.maidenStart = false;
				exit.interact(502);
				ctx.onCondition(() -> Util.ROOM_TWO.containsPoint(ctx.players.getLocal()), 250, 3);
			}
		}
	}
	
	public static WorldPoint getBloodSpots() {
	    final int radius = 4;
	    final WorldPoint myPoint = ctx.players.getLocal().getLocation();
	    if (Tob.bloodTiles.contains(myPoint)) {
	        WorldPoint tempPoint;
	        final List<WorldPoint> validPoints = new ArrayList<>();
	        if (ctx.players.getLocal().getLocation().y >= 4447) {
	            for (int x = -radius; x <= radius; x++) {
	                for (int y = -radius; y <= 0; y++) {
	                    tempPoint = myPoint.derrive(x, y);
	                    if (Tob.bloodTiles.contains(tempPoint)) {
	                        continue;
	                    }
	                    if (ctx.pathing.reachable(tempPoint)) {
	                        validPoints.add(tempPoint);
	                    }
	                }
	            }
	        } else if (ctx.players.getLocal().getLocation().y <= 4446) {
	            for (int x = -radius; x <= radius; x++) {
	                for (int y = 0; y <= radius; y++) {
	                    tempPoint = myPoint.derrive(x, y);
	                    if (Tob.bloodTiles.contains(tempPoint)) {
	                        continue;
	                    }
	                    if (ctx.pathing.reachable(tempPoint)) {
	                        validPoints.add(tempPoint);
	                    }
	                }
	            }
	        }
	        if (validPoints.isEmpty()) {
	            ctx.log("We couldn't find any valid tiles to traverse to.");
	            return null;
	        }
	        tempPoint = validPoints.get(0);
	        for (WorldPoint point : validPoints) {
	            if (point.distanceTo(myPoint) < tempPoint.distanceTo(myPoint)) {
	                tempPoint = point;
	            }
	        }
	        return tempPoint;
	    }
	    return null;
	}
	
	private static int between(final int min, final int max) {
		final Random random = new Random();
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}
	
	public final static SimpleEntityQuery<SimpleNpc> maiden() {
		return ctx.npcs.populate().filter(8360).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
}
