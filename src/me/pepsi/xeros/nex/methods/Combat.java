package me.pepsi.xeros.nex.methods;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimpleSceneObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import me.pepsi.xeros.nex.core.Nex;

public class Combat {
    private static WorldPoint clearPoint = null;
	
	final static ClientContext ctx = ClientContext.instance();
	
    public static void doCombat() {
    	boolean lootWalk = false;
		final WorldPoint start = new WorldPoint(2925, 5207);
		final WorldPoint fumusWalk = new WorldPoint(2921, 5213);
		final WorldPoint umbraWalk = new WorldPoint(2936, 5206);
		final WorldPoint cruorWalk = new WorldPoint(2929, 5192);
		final WorldPoint glaciesWalk = new WorldPoint(2915, 5198);
		final WorldPoint glaciesSouth = new WorldPoint(2924, 5191);
		final WorldPoint glaciesNorth = new WorldPoint(2915, 5201);
		final WorldPoint loot = new WorldPoint(2913, 5200);
		SimpleNpc nexTalk = ctx.npcs.populate().filter(11279).nearest().next();
		SimpleNpc nex = ctx.npcs.populate().filter(11278).nearest().next();
		SimpleNpc nex1 = ctx.npcs.populate().filter(11280).nearest().next();
		SimpleNpc nex2 = ctx.npcs.populate().filter(11281).nearest().next();
		SimpleNpc nex3 = ctx.npcs.populate().filter(11282).nearest().next();
		SimpleNpc fumus = ctx.npcs.populate().filter(11283).nearest().next();
		SimpleNpc umbra = ctx.npcs.populate().filter(11284).nearest().next();
		SimpleNpc cruor = ctx.npcs.populate().filter(11285).nearest().next();
		SimpleNpc glacies = ctx.npcs.populate().filter(11286).nearest().next();
		if (ctx.combat.isPoisoned()) {
			SimpleItem sanfew = ctx.inventory.populate().filterContains("Sanfew serum").next();
			if (sanfew != null) {
				sanfew.interact(74);
			}
		}
		if (Nex.phase == 0) {
			SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
			if (!ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty() && item != null && ctx.inventory.canPickupItem(item)) {
				Looting.Loot();
			} else {
				ctx.sleep(600);
				ctx.pathing.walkToTile(start.derrive(between(-1, 1), between(-1, 1)));
				Nex.status = "Pathing";
				ctx.prayers.disableAll();
				if (ctx.pathing.distanceTo(start) <= 5) {
					Nex.phase = 1;
				}
			}
			if (nex != null) {
				if (!nex.isDead() || nex.getHealth() != 0) {
					Nex.phase = 1;
				}
			}
		}
		if (Nex.phase == 1) {
			SimpleItem berserkerRingi = ctx.inventory.populate().filter(11773).next();
	        SimpleItem berserkerRing = ctx.inventory.populate().filter(6737).next();
	        SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
	        SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
			if (berserkerRingi != null) {
    			Nex.status = "Switching Ring";
    			berserkerRingi.interact(SimpleItemActions.FIRST);
    		} else if (berserkerRing != null) {
    			Nex.status = "Switching Ring";
    			berserkerRing.interact(SimpleItemActions.FIRST);
    		} else if (archerRing != null) {
    			Nex.status = "Switching Ring";
    			archerRing.interact(SimpleItemActions.FIRST);
    		} else if (archerRingi != null) {
    			Nex.status = "Switching Ring";
    			archerRingi.interact(SimpleItemActions.FIRST);
    		}
			if (nexTalk != null && nexTalk.overheadText().contains("Cruor!")) {
				ctx.sleep(0, 4000);
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
			}
			useSpecialDWH();
            if (nex != null) {
            	 if (ctx.equipment.populate().filter(13576).size() > 0 && !ctx.combat.specialAttack()) {
	                ctx.combat.toggleSpecialAttack(true);
	                if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
	        			ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
	        			ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
	                }
	                Nex.status = "Using Special";
	                ctx.log("Using Special");
	                ctx.sleep(600);
	                ctx.sleepCondition(() -> !ctx.combat.specialAttack(), 2500);
	            }
    			Nex.status = "Attacking Nex";
    			nex.interact(412);
    			if (ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
    				ctx.sleep(600);
    			}
    			if (ctx.combat.getSpecialAttackPercentage() <= 49) {
    				handleMagePrayers();
    			}
            }
		} else if (Nex.phase == 2) {
			handleMagePrayers();
			if (ctx.inventory.populate().filter(20997).size() > 0 || ctx.inventory.populate().filter(25865).size() > 0 || ctx.inventory.populate().filter(25867).size() > 0) {
            	if (ctx.inventory.populate().filter(20997).next() != null) {
            		ctx.inventory.populate().filter(20997).next().interact(454);
            		ctx.sleepCondition(() -> ctx.equipment.populate().filter(20997).size() > 0, 2000);
            	} else if (ctx.inventory.populate().filter(25865).next() != null) {
            		ctx.inventory.populate().filter(25865).next().interact(454);
            		ctx.sleepCondition(() -> ctx.equipment.populate().filter(25865).size() > 0, 2000);
            	} else if (ctx.inventory.populate().filter(25867).next() != null) {
            		ctx.inventory.populate().filter(25867).next().interact(454);
            		ctx.sleepCondition(() -> ctx.equipment.populate().filter(25867).size() > 0, 2000);
            	}
        	}
			if (ctx.pathing.distanceTo(fumusWalk) >= 5) {
				ctx.pathing.walkToTile(fumusWalk.derrive(between(-2, 2), between(-2, 2)));
			} else if (fumus != null && ctx.pathing.distanceTo(fumusWalk) <= 8) {
				fumus.interact(412);
				Nex.status = "Attacking Fumus";
				if (fumus.isDead()) {
					Nex.phase = 3;
				}
			}
		} else if (Nex.phase == 3) {
			if (clearPoint == null) {
				final WorldPoint point = getClearPoint();
            	if (point != null) {
            		clearPoint = point;
            	}
			} else {
        		if (!ctx.pathing.onTile(clearPoint)) {
            		ctx.pathing.step(clearPoint);
            		 Nex.status = "Dodging Shadow";
            		 ctx.sleep(1500);
    				if (umbra.isDead()) {
    					Nex.phase = 5;
    				}
            	} else {
            		clearPoint = null;
            	}
        	}
			if (ctx.pathing.distanceTo(umbraWalk) >= 5 && ctx.pathing.distanceTo(nex) <= 10) {
				Nex.status = "Kiting Nex";
				ctx.pathing.walkToTile(umbraWalk.derrive(between(-2, 2), between(-2, 2)));
				ctx.sleep(1000);
			}
			handleRangePrayers();
			if (nex != null) {
				nex.interact(412);
				Nex.status = "Attacking Nex";
			} else {
				if (ctx.pathing.distanceTo(umbraWalk) >= 5) {
					ctx.pathing.walkToTile(umbraWalk.derrive(between(-2, 2), between(-2, 2)));
				}
			}
			if (clearPoint == null) {
				final WorldPoint point = getClearPoint();
            	if (point != null) {
            		clearPoint = point;
            	}
			} else {
        		if (!ctx.pathing.onTile(clearPoint)) {
            		ctx.pathing.step(clearPoint);
            		 Nex.status = "Dodging Shadow";
            		 ctx.sleep(1500);
    				if (umbra.isDead()) {
    					Nex.phase = 5;
    				}
            	} else {
            		clearPoint = null;
            	}
        	}
		} else if (Nex.phase == 4) {
			if (clearPoint == null) {
				final WorldPoint point = getClearPoint();
            	if (point != null) {
            		clearPoint = point;
            	}
			} else {
        		if (!ctx.pathing.onTile(clearPoint)) {
            		ctx.pathing.step(clearPoint);
            		 Nex.status = "Dodging Shadow";
            		 ctx.sleep(1500);
    				if (umbra.isDead()) {
    					Nex.phase = 5;
    				}
            	} else {
            		clearPoint = null;
            	}
        	}
			if (ctx.pathing.distanceTo(umbraWalk) >= 5) {
				ctx.pathing.walkToTile(umbraWalk.derrive(between(-2, 2), between(-2, 2)));
			}
			handleRangePrayers();
			if (umbra != null && ctx.pathing.distanceTo(umbraWalk) <= 8) {
				umbra.interact(412);
				Nex.status = "Attacking Umbra";
				if (umbra.isDead()) {
					Nex.phase = 5;
				}
			}
			if (clearPoint == null) {
				final WorldPoint point = getClearPoint();
            	if (point != null) {
            		clearPoint = point;
            	}
			} else {
        		if (!ctx.pathing.onTile(clearPoint)) {
            		ctx.pathing.step(clearPoint);
            		 Nex.status = "Dodging Shadow";
            		 ctx.sleep(1500);
    				if (umbra.isDead()) {
    					Nex.phase = 5;
    				}
            	} else {
            		clearPoint = null;
            	}
        	}
		} else if (Nex.phase == 5) {
			handleMagePrayers();
			if (ctx.pathing.distanceTo(cruorWalk) >= 5 && ctx.pathing.distanceTo(nex) <= 10) {
				Nex.status = "Kiting Nex";
				ctx.pathing.walkToTile(cruorWalk.derrive(between(-2, 2), between(-2, 2)));
				ctx.sleep(1550);
			}
			if (nex != null) {
				if (nex.getAnimation() == 9183) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					return;
				}
				nex.interact(412);
				Nex.status = "Attacking Nex";
			} else {
				if (ctx.pathing.distanceTo(cruorWalk) >= 5) {
					ctx.pathing.step(cruorWalk.derrive(between(-2, 2), between(-2, 2)));
				}
			}
		} else if (Nex.phase == 6) {
			if (ctx.pathing.distanceTo(cruorWalk) >= 5) {
				ctx.pathing.step(cruorWalk.derrive(between(-2, 2), between(-2, 2)));
			}
			handleMagePrayers();
			if (cruor != null) {
				cruor.interact(412);
				Nex.status = "Attacking Cruor";
				if (cruor.isDead()) {
					Nex.phase = 7;
				}
			}
		} else if (Nex.phase == 7) {
			if (ctx.pathing.distanceTo(glaciesWalk) >= 7 && ctx.pathing.distanceTo(nex) <= 10) {
				Nex.status = "Kiting Nex";
				ctx.pathing.walkToTile(glaciesWalk.derrive(between(-2, 2), between(-2, 2)));
				ctx.sleep(1550);
			}
			if (ctx.pathing.distanceTo(glaciesWalk) <= 10) {
				if (ctx.pathing.distanceTo(glaciesNorth) > 4 && nex != null && ctx.pathing.distanceTo(nex) <= 4 && ctx.pathing.distanceTo(glaciesSouth) > 4) {
					ctx.pathing.walkToTile(glaciesNorth.derrive(between(-2, 2), between(-2, 2)));
					Nex.status = "Kiting Nex";
					ctx.sleep(1550);
				} else if (ctx.pathing.distanceTo(glaciesSouth) > 4 && nex != null && ctx.pathing.distanceTo(nex) <= 4) {
					ctx.pathing.walkToTile(glaciesSouth.derrive(between(-2, 2), between(-2, 2)));
					Nex.status = "Kiting Nex";
					ctx.sleep(2000);
				}
			}
			handleMagePrayers();
			if (nex != null) {
				nex.interact(412);
				Nex.status = "Attacking Nex";
			} else {
				if (ctx.pathing.distanceTo(glaciesWalk) >= 7) {
					ctx.pathing.walkToTile(glaciesWalk.derrive(between(-2, 2), between(-2, 2)));
				}
			}
		} else if (Nex.phase == 8) {
			attackIce();
			if (nex != null) {
				if (ctx.pathing.distanceTo(glaciesNorth) > 4 && nex != null && ctx.pathing.distanceTo(nex) <= 4 && ctx.pathing.distanceTo(glaciesSouth) > 4) {
					ctx.pathing.walkToTile(glaciesNorth.derrive(between(-2, 2), between(-2, 2)));
					Nex.status = "Kiting Nex";
					ctx.sleep(1550);
				} else if (ctx.pathing.distanceTo(glaciesSouth) > 4 && nex != null && ctx.pathing.distanceTo(nex) <= 4) {
					ctx.pathing.walkToTile(glaciesSouth.derrive(between(-2, 2), between(-2, 2)));
					Nex.status = "Kiting Nex";
					ctx.sleep(2000);
				}
			}
			if (ctx.pathing.distanceTo(glaciesWalk) >= 7) {
				ctx.pathing.walkToTile(glaciesWalk.derrive(between(-2, 2), between(-2, 2)));
			}
			handleMagePrayers();
			if (glacies != null) {
				glacies.interact(412);
				Nex.status = "Attacking Glacies";
				if (glacies.isDead()) {
					Nex.phase = 9;
				}
			}
		} else if (Nex.phase == 9) {
			attackIce();
			SimpleItem row = ctx.inventory.populate().filter(2572).next();
    		SimpleItem rowi = ctx.inventory.populate().filter(12785).next();
			if (nex != null) {
	    		if (row != null) {
	    			if (nex.getHealth() <= 250 || nex.isDead()) {
	    				row.interact(SimpleItemActions.WEAR);
	    				Nex.status = "Switching Ring";
	    			}
	    		} else if (rowi != null) {
	    			if (nex.getHealth() <= 250 || nex.isDead()) {
	    				rowi.interact(SimpleItemActions.WEAR);
	    				Nex.status = "Switching Ring";
	    			}
	    		}
				nex.interact(412);
				Nex.status = "Attacking Nex";
				if (nex.isDead()) {
					if (!lootWalk && nex.distanceTo(ctx.players.getLocal()) <= 4) {
						lootWalk = true;
						ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
					}
					ctx.sleep(1100, 2200);
					ctx.prayers.disableAll();
					ctx.sleep(1100, 2200);
					lootWalk = false;
			    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 12);
			    	Nex.phase = 0;
				}
			} else if (nex1 != null) {
				if (row != null) {
	    			if (nex1.getHealth() <= 250 || nex1.isDead()) {
	    				row.interact(SimpleItemActions.WEAR);
	    			}
	    		} else if (rowi != null) {
	    			if (nex1.getHealth() <= 250 || nex1.isDead()) {
	    				rowi.interact(SimpleItemActions.WEAR);
	    			}
	    		}
				nex1.interact(412);
				Nex.status = "Attacking Nex";
				if (nex1.isDead()) {
					if (!lootWalk && nex1.distanceTo(ctx.players.getLocal()) <= 5) {
						lootWalk = true;
						ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
					}
					ctx.sleep(1100, 2200);
					ctx.prayers.disableAll();
					ctx.sleep(1100, 2200);
					lootWalk = false;
			    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
					Nex.phase = 0;
				}
			} else if (nex2 != null) {
				if (row != null) {
	    			if (nex2.getHealth() <= 250 || nex2.isDead()) {
	    				row.interact(SimpleItemActions.WEAR);
	    			}
	    		} else if (rowi != null) {
	    			if (nex2.getHealth() <= 250 || nex2.isDead()) {
	    				rowi.interact(SimpleItemActions.WEAR);
	    			}
	    		}
				nex2.interact(412);
				Nex.status = "Attacking Nex";
				if (nex2.isDead()) {
					if (!lootWalk && nex2.distanceTo(ctx.players.getLocal()) <= 5) {
						lootWalk = true;
						ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
					}
					ctx.sleep(1100, 2200);
					ctx.prayers.disableAll();
					ctx.sleep(1100, 2200);
					lootWalk = false;
			    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
					Nex.phase = 0;
				}
			} else if (nex3 != null) {
				if (row != null) {
	    			if (nex3.getHealth() <= 250 || nex3.isDead()) {
	    				row.interact(SimpleItemActions.WEAR);
	    			}
	    		} else if (rowi != null) {
	    			if (nex3.getHealth() <= 250 || nex3.isDead()) {
	    				rowi.interact(SimpleItemActions.WEAR);
	    			}
	    		}
				nex3.interact(412);
				Nex.status = "Attacking Nex";
				if (nex3.isDead()) {
					if (!lootWalk && nex3.distanceTo(ctx.players.getLocal()) <= 5) {
						lootWalk = true;
						ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
					}
					ctx.sleep(1100, 2200);
					ctx.prayers.disableAll();
					ctx.sleep(1100, 2200);
					lootWalk = false;
			    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
					Nex.phase = 0;
				}
			}
			handleMagePrayers();
		}
		if (nex != null) {
			if (nex.isDead()) {
				if (!lootWalk && nex.distanceTo(ctx.players.getLocal()) <= 5) {
					lootWalk = true;
					ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
				}
				ctx.sleep(1100, 2200);
				ctx.prayers.disableAll();
				ctx.sleep(1100, 2200);
				lootWalk = false;
		    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
				Nex.phase = 0;
			}
		} else if (nex1 != null) {
			if (nex1.isDead()) {
				if (!lootWalk && nex1.distanceTo(ctx.players.getLocal()) <= 5) {
					lootWalk = true;
					ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
				}
				ctx.sleep(1100, 2200);
				ctx.prayers.disableAll();
				ctx.sleep(1100, 2200);
				lootWalk = false;
		    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
				Nex.phase = 0;
			}
		} else if (nex2 != null) {
			if (nex2.isDead()) {
				if (!lootWalk && nex2.distanceTo(ctx.players.getLocal()) <= 5) {
					lootWalk = true;
					ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
				}
				ctx.sleep(1100, 2200);
				ctx.prayers.disableAll();
				ctx.sleep(1100, 2200);
				lootWalk = false;
		    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
				Nex.phase = 0;
			}
		} else if (nex3 != null) {
			if (nex3.isDead()) {
				if (!lootWalk && nex3.distanceTo(ctx.players.getLocal()) <= 5) {
					lootWalk = true;
					ctx.pathing.walkToTile(loot.derrive(between(-2, 2), between(-2, 2)));
				}
				ctx.sleep(1100, 2200);
				ctx.prayers.disableAll();
				ctx.sleep(1100, 2200);
				lootWalk = false;
		    	ctx.onCondition(() -> !ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty(), 250, 24);
				Nex.phase = 0;
			}
		}
    }
    
    public static WorldPoint getClearPoint() {
        final int radius = 2;// Radius of tiles to find a valid location
        final WorldPoint myPoint = ctx.players.getLocal().getLocation();// Store our current location
        final List<WorldPoint> shadows = ctx.objects.populate().filter(42942).toStream().map(SimpleSceneObject::getLocation).collect(Collectors.toList());// Build list of locations of shadows
        if (shadows.contains(myPoint)) { //We need to move
            WorldPoint tempPoint;// Temporary object to use for checks below
            final List<WorldPoint> validPoints = new ArrayList<>();// List of worldpoints that have no shadows on them and are reachable
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    tempPoint = myPoint.derrive(x, y);
                    if (shadows.contains(tempPoint)) {
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
            // Sorting our valid tiles to ones closest to us
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
    
	public static void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.health() <= Nex.eatAt && ctx.combat.health() != 10) {
			Nex.status = "Eating Food";
			food.interact(74);
		}
	}
	
	public static void handleMagePrayers() {
		if (Nex.prayerType == -2) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
				ctx.sleep(750, 1000);
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR) && ctx.equipment.populate().filter(13576).isEmpty()) {
				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
			}
		} else {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
				ctx.sleep(750, 1000);
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE) && ctx.equipment.populate().filter(13576).isEmpty()) {
				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
			}
		}
	}
	
	public static void handleRangePrayers() {
		if (Nex.prayerType == -2) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
				ctx.sleep(750, 1000);
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
			}
		} else {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
				ctx.sleep(750, 1000);
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
			}
		}
	}
	
	public static void useSpecialDWH() {
		final WorldPoint fumusWalk = new WorldPoint(2921, 5213);
		SimpleItem tbow = ctx.inventory.populate().filter(20997).next();
		SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
    	SimpleNpc nex = ctx.npcs.populate().filter(5862).nearest().next();
    	if (dwh != null || ctx.equipment.populate().filter(13576).next() != null) {
    		if (ctx.players.getLocal().getAnimation() == 1378 || ctx.players.getLocal().getAnimation() == 401 || ctx.players.getLocal().getAnimation() == 4177) {
    			ctx.sleep(1000);
    			Nex.special = false;
    		}
			if (ctx.combat.getSpecialAttackPercentage() >= 50) {
				if (!ctx.inventory.populate().filter(13576).isEmpty()) {
					ctx.inventory.populate().filter(13576).next().interact(454);
				}
				if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
					ctx.inventory.populate().filterContains("defender").next().interact(454);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if (!ctx.equipment.populate().filter(13576).isEmpty() && Nex.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					Nex.special = true;
					Nex.status = "Using Special";
					ctx.log("Using Special");
					if (nex != null) {
						nex.interact(SimpleNpcActions.ATTACK);
					}
				}
			} else {
				Nex.special = false;
				if (tbow != null) {
					if (tbow != null) {
						tbow.interact(SimpleItemActions.WEAR);
					}
				}
			}
    	} else {
			if (ctx.pathing.distanceTo(fumusWalk) >= 4) {
				ctx.pathing.walkToTile(fumusWalk.derrive(between(-2, 2), between(-2, 2)));
			}
		}
    }
	
	public static void attackIce() {
		SimpleGameObject ice = (SimpleGameObject) ctx.objects.populate().filter(10176).nearest().next();
		if (ice != null) {
			ice.interact(502);
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
}
