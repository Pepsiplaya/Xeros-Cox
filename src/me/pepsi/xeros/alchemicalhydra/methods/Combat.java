package me.pepsi.xeros.alchemicalhydra.methods;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.wrappers.SimpleGraphicsObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleProjectile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;

public class Combat {
	public static WorldPoint start = new WorldPoint(1356, 10258);
	public static WorldPoint poison = null;
	
	final static ClientContext ctx = ClientContext.instance();
	
    public static void doCombat() {
		final WorldPoint hydraStart = new WorldPoint(1356, 10258);
	    final WorldPoint greenWalk = new WorldPoint(1374, 10266);
	    final WorldPoint blueWalk = new WorldPoint(1373, 10275);
	    final WorldPoint redWalk = new WorldPoint(1359, 10272);
	    final WorldPoint walk = new WorldPoint(1365, 10269);
	    final WorldPoint walkGreen = new WorldPoint(1374, 10268);
	    SimpleNpc hydra1 = ctx.npcs.populate().filter(8616).nearest().next();
		SimpleNpc hydra2 = ctx.npcs.populate().filter(8617).nearest().next();
		SimpleNpc hydra3 = ctx.npcs.populate().filter(8618).nearest().next();
		SimpleNpc greenHydra = ctx.npcs.populate().filter(8615).nearest().next();
		SimpleNpc blueHydra = ctx.npcs.populate().filter(8619).nearest().next();
		SimpleNpc redHydra = ctx.npcs.populate().filter(8620).nearest().next();
		SimpleNpc blackHydra = ctx.npcs.populate().filter(8621).nearest().next();
		int food = ctx.inventory.populate().filterHasAction("Eat").population(true);
		int brew = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
		SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
		SimpleItem prayer = ctx.inventory.populate().filterContains("Prayer").next();
		if (ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty() && AlchemicalHydra.dead == false && (hydra1 == null && hydra2 == null && hydra3 == null && greenHydra == null && blueHydra == null && redHydra == null && blackHydra == null) && ((brew >= 2 || food >= 2) && (!ctx.inventory.populate().filterContains("restore").isEmpty() || !ctx.inventory.populate().filterContains("Prayer").isEmpty()))) {
			Pathing.door();
		} else if (ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty() && (hydra1 == null && hydra2 == null && hydra3 == null && greenHydra == null && blueHydra == null && redHydra == null && blackHydra == null) && (brew <= 1 && food <= 1 || (restore == null && prayer == null))) {
			ctx.prayers.disableAll();
			ctx.magic.castHomeTeleport();
			AlchemicalHydra.status = "Restocking";
			ctx.log("Restocking, out of supplies");
			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
		}
		if (AlchemicalHydra.presetLoaded) {
			AlchemicalHydra.presetLoaded = false;
		}
		if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
        	AlchemicalHydra.status = "Opening Coin Bag";
        	ctx.log("Opening Coin Bag");
        	ctx.inventory.populate().filterContains("coin bag").next().interact(539);
        }
		if (poison == null) {
			final WorldPoint point = getPoisonSpots();
        	if (point != null) {
        		poison = point;
        	}
		} else {
    		if (!ctx.pathing.onTile(poison) && !ctx.pathing.inMotion()) {
    			ctx.pathing.step(poison.derrive(0, -1));
        		AlchemicalHydra.status = "Dodging Poison";
        		ctx.log("Dodging Poison");
        		ctx.sleep(600);
        		poison = null;
        	} else {
        		poison = null;
        	}
    	}
		handlePrayers();
		if (ctx.combat.isPoisoned()) {
			SimpleItem sanfew = ctx.inventory.populate().filterContains("Sanfew serum").next();
			if (sanfew != null) {
				sanfew.interact(74);
			}
		}
		if (AlchemicalHydra.walkBoolean) {
			ctx.pathing.step(walk);
			ctx.sleep(1200);
			AlchemicalHydra.onFire = false;
			AlchemicalHydra.walkBoolean = false;
		}
		if (greenHydra != null) {
			if (ctx.pathing.distanceTo(hydraStart) <= 2) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true, false);
			}
			if (greenHydra.getLocalY() == 7040 && !ctx.pathing.inMotion()) {
				ctx.pathing.step(walkGreen);
				ctx.sleep(1000);
				return;
			}
			if (ctx.pathing.distanceTo(greenWalk) >= 2) {
				AlchemicalHydra.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing To Green Kiting Spot");
				}
				ctx.pathing.walkToTile(greenWalk);
				ctx.sleep(600);
			} else if (ctx.players.getLocal().getInteracting() != null && !ctx.inventory.inventoryFull()) {
				useSpecialSGS();
			} else if (ctx.players.getLocal().getInteracting() == null && greenHydra.getLocalX() >= 8400) {
				greenHydra.interact(SimpleNpcActions.ATTACK);
				AlchemicalHydra.status = "Attacking Green Hydra";
				ctx.log("Attacking Green Hydra");
				ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
			}
		} else if (hydra1 != null) {
			if (!ctx.pathing.onTile(blueWalk)) {
				AlchemicalHydra.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing To Blue Kiting Spot");
				}
				ctx.pathing.walkToTile(blueWalk);
				ctx.sleep(600);
			}
		} else if (blueHydra != null) {
			if (ctx.pathing.distanceTo(blueWalk) >= 2) {
				AlchemicalHydra.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing To Blue Kiting Spot");
				}
				ctx.pathing.walkToTile(blueWalk);
				ctx.sleep(600);
			} else if (ctx.players.getLocal().getInteracting() != null && !ctx.inventory.inventoryFull()) {
				useSpecialSGS();
			} else if (ctx.players.getLocal().getInteracting() == null && blueHydra.getLocalY() >= 8000) {
				blueHydra.interact(SimpleNpcActions.ATTACK);
				AlchemicalHydra.status = "Attacking Blue Hydra";
				ctx.log("Attacking Blue Hydra");
				ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
			}
		} else if (hydra2 != null) {
			if (!ctx.pathing.onTile(redWalk)) {
				AlchemicalHydra.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing To Red Kiting Spot");
				}
				ctx.pathing.walkToTile(redWalk);
				ctx.sleep(600);
				return;
			}
		} else if (redHydra != null) {
			if (redHydra.getLocalX() > 8500 && redHydra.getLocalY() > 8100 && redHydra.getHealth() > 480) {
				if (!ctx.pathing.onTile(redWalk)) {
					ctx.pathing.walkToTile(redWalk);
				}
			}
			if (ctx.players.getLocal().getInteracting() == null && redHydra.getLocalX() < 7700 && redHydra.getLocalY() == 8320) {
				redHydra.interact(SimpleNpcActions.ATTACK);
				AlchemicalHydra.status = "Attacking Red Hydra";
				ctx.log("Attacking Red Hydra");
				ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
				return;
			} else if (ctx.pathing.distanceTo(redWalk) >= 2 && redHydra.getLocalX() < 7552) {
				AlchemicalHydra.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing To Red Kiting Spot");
				}
				ctx.pathing.walkToTile(redWalk);
				ctx.sleep(600);
				return;
			} else if (ctx.players.getLocal().getInteracting() == null && redHydra.getLocalY() < 8192) {
				redHydra.interact(SimpleNpcActions.ATTACK);
				AlchemicalHydra.status = "Attacking Red Hydra";
				ctx.log("Attacking Red Hydra");
				ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
			}
			if (AlchemicalHydra.onFire && !ctx.pathing.inMotion()) {
				ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(between(5, 6), between(-4, -2)));
		        AlchemicalHydra.status = "Dodging Fire";
		        ctx.log("Dodging Fire");
		        AlchemicalHydra.onFire = false;
		        ctx.sleep(600);
			}
		} else if (blackHydra != null) {
			SimpleItem row = ctx.inventory.populate().filter(2572).next();
			SimpleItem rowi = ctx.inventory.populate().filter(12785).next();
			if (row != null) {
				if (blackHydra != null && blackHydra.getHealth() <= 100) {
					row.interact(SimpleItemActions.WEAR);
				}
			} else if (rowi != null) {
				if (blackHydra != null && blackHydra.getHealth() <= 100) {
					rowi.interact(SimpleItemActions.WEAR);
				}
			}
			if (ctx.players.getLocal().getInteracting() == null) {
				blackHydra.interact(SimpleNpcActions.ATTACK);
				AlchemicalHydra.status = "Attacking Black Hydra"; 
				ctx.log("Attacking Black Hydra");
				ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
			}
			if (AlchemicalHydra.onFire && !ctx.pathing.inMotion()) {
				ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(between(5, 6), between(-4, -2)));
		        AlchemicalHydra.status = "Dodging Fire";
		        ctx.log("Dodging Fire");
		        AlchemicalHydra.onFire = false;
		        ctx.sleep(600);
			}
			if (blackHydra.isDead() || (hydra1 == null && hydra2 == null && hydra3 == null && greenHydra == null && blueHydra == null && redHydra == null && blackHydra == null)) {
				AlchemicalHydra.dead = true;
				ctx.log("test");
				ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 4);
			}
		}
    }
    
    public static void useSpecialSGS() {
    	SimpleNpc greenHydra = ctx.npcs.populate().filter(8615).nearest().next();
		SimpleNpc blueHydra = ctx.npcs.populate().filter(8619).nearest().next();
		SimpleItem sgs = ctx.inventory.populate().filter(11806).next();
        if (greenHydra != null  || blueHydra != null && !ctx.inventory.inventoryFull()) {
        	if (sgs != null || ctx.equipment.populate().filter(11806).next() != null) {
        		if (ctx.players.getLocal().getAnimation() == 7640) {
        			ctx.sleep(600);
        			AlchemicalHydra.special = false;
        		}
        	}
        	if (ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.combat.health() <= (ctx.combat.maxHealth() - 20)) {
            	if (sgs != null) {
                    sgs.interact(454);
                    AlchemicalHydra.status = "Switching To SGS";
    				ctx.log("Switching To SGS");
                    ctx.sleepCondition(() -> ctx.equipment.populate().filter(11806).size() > 0, 2000);
                }
            	if (!ctx.equipment.populate().filter(11806).isEmpty() && AlchemicalHydra.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					AlchemicalHydra.special = true;
					AlchemicalHydra.status = "Using Special";
					ctx.log("Using Special");
				}
            } else {
                if (ctx.inventory.populate().filter(22978).next() != null) {
                	ctx.inventory.populate().filter(22978).next().interact(454);
                	AlchemicalHydra.status = "Switching Back";
    				ctx.log("Switching Back");
                	ctx.sleepCondition(() -> ctx.equipment.populate().filter(22978).size() > 0, 2000);
                }
                if (ctx.inventory.populate().filterContains("defender").next() != null) {
                	ctx.inventory.populate().filterContains("defender").next().interact(454);
                	ctx.sleepCondition(() -> ctx.inventory.populate().filterContains("defender").size() > 0, 2000);
                }
            }
        }
    }
    
    public static void handleEatingFood() {
    	SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
    	SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.health() <= AlchemicalHydra.eatAt && ctx.combat.health() != 10) {
			AlchemicalHydra.status = "Eating " + food.getName().toString();
			ctx.log("Eating " + food.getName().toString());
			food.interact(74);
		} else if (brew != null && ctx.combat.health() <= AlchemicalHydra.eatAt && ctx.combat.health() != 10) {
			AlchemicalHydra.status = "Drinking " + brew.getName().toString();
			ctx.log("Drinking " + brew.getName().toString());
			brew.interact(74);
		}
	}
	
	public static void handlePrayers() {
		SimpleNpc hydra1 = ctx.npcs.populate().filter(8616).nearest().next();
		SimpleNpc hydra2 = ctx.npcs.populate().filter(8617).nearest().next();
		SimpleNpc hydra3 = ctx.npcs.populate().filter(8618).nearest().next();
		SimpleNpc greenHydra = ctx.npcs.populate().filter(8615).nearest().next();
		SimpleNpc blueHydra = ctx.npcs.populate().filter(8619).nearest().next();
		SimpleNpc redHydra = ctx.npcs.populate().filter(8620).nearest().next();
		SimpleNpc blackHydra = ctx.npcs.populate().filter(8621).nearest().next();
		if (Staff.getNearbyStaff() != null) {
			if (greenHydra != null && blueHydra != null && redHydra != null && blackHydra != null) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true, false);
				if (AlchemicalHydra.prayerType == -2 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				} else if (AlchemicalHydra.prayerType == -1 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				} else if (AlchemicalHydra.prayerType == -3 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				}
			}
        } else {
        	SimpleProjectile projectile = ctx.projectiles.populate().filter(1663, 1662).next();
    	    if (projectile != null) {
    	    	if (projectile.getId() == 1663) {
    	    		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true, false);
    	        } else {
    	        	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true, false);
    	        }
    	    }
			if (AlchemicalHydra.prayerType == -2 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
				}
			} else if (AlchemicalHydra.prayerType == -1 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
				}
			} else if (AlchemicalHydra.prayerType == -3 && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal()) && (hydra1 != null || hydra2 != null || hydra3 != null || greenHydra != null || blueHydra != null || redHydra != null || blackHydra != null)) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
			}
        }
	}
    
    public static WorldPoint getFireSpots() {
        final int radius = 2;
        final WorldPoint myPoint = ctx.players.getLocal().getLocation();
        final List<WorldPoint> fires = ctx.graphicsObjects.populate().filter(1668).toStream().map(SimpleGraphicsObject::getLocation).collect(Collectors.toList());
        if (fires.contains(myPoint)) {
            WorldPoint tempPoint;
            final List<WorldPoint> validPoints = new ArrayList<>();
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    tempPoint = myPoint.derrive(x, y);
                    if (fires.contains(tempPoint)) {
                        continue;
                    }
                    if (ctx.pathing.reachable(tempPoint)) {
                        validPoints.add(tempPoint);
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
    
    public static WorldPoint getPoisonSpots() {
        final int radius = 2;
        final WorldPoint myPoint = ctx.players.getLocal().getLocation();
        final List<WorldPoint> poison = ctx.graphicsObjects.populate().filter(1645, 1654, 1655, 1656, 1657, 1658, 1659, 1660, 1661).toStream().map(SimpleGraphicsObject::getLocation).collect(Collectors.toList());
        if (poison.contains(myPoint)) { 
            WorldPoint tempPoint;
            final List<WorldPoint> validPoints = new ArrayList<>();
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    tempPoint = myPoint.derrive(x, y);
                    if (poison.contains(tempPoint)) {
                        continue;
                    }
                    if (ctx.pathing.reachable(tempPoint)) {
                        validPoints.add(tempPoint);
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
}
