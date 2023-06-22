package me.pepsi.xeros.cerberus.methods;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import me.pepsi.xeros.cerberus.core.Cerberus;

public class Combat {
	public static WorldPoint poison = null;
	final static WorldPoint start = new WorldPoint(1240, 1241);
	
	final static ClientContext ctx = ClientContext.instance();
	
    public static void doCombat() {
		SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
		SimpleGameObject flames = (SimpleGameObject) ctx.objects.populate().filter(23105).nearest().next();
		if (ctx.pathing.onTile(start) && flames != null) {
			flames.interact(502);
			ctx.onCondition(() -> !ctx.pathing.onTile(start), 250, 3);
		}
		if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
        	Cerberus.status = "Opening Coin Bag";
        	ctx.log("Opening Coin Bag");
        	ctx.inventory.populate().filterContains("coin bag").next().interact(539);
        	ctx.onCondition(() -> ctx.equipment.populate().filterContains("coin bag").isEmpty(), 250, 3);
        }
		if (cerberus != null) {
			Cerberus.presetLoaded = false;
			Potions.rangePotion();
			SimpleItem bos = ctx.inventory.populate().filter(21183).next();
			SimpleItem row = ctx.inventory.populate().filter(2572).next();
			SimpleItem rowi = ctx.inventory.populate().filter(12785).next();
			if (cerberus.getHealth() != 0 && cerberus.getHealth() <= 100 && ctx.players.getLocal().inCombat()) {
				if (bos != null) {
					bos.interact(SimpleItemActions.WEAR);
					Cerberus.status = "Equipping Bos";
		            ctx.log("Equipping Bos");
		            ctx.onCondition(() -> ctx.equipment.populate().filter(21183).next() != null, 250, 3);
				}
				if (row != null) {
					row.interact(SimpleItemActions.WEAR);
					Cerberus.status = "Equipping Row";
		            ctx.log("Equipping Row");
		            ctx.onCondition(() -> ctx.equipment.populate().filter(2572).next() != null, 250, 3);
				} else if (rowi != null) {
					rowi.interact(SimpleItemActions.WEAR);
					Cerberus.status = "Equipping Rowi";
		            ctx.log("Equipping Rowi");
		            ctx.onCondition(() -> ctx.equipment.populate().filter(12785).next() != null, 250, 3);
				}
			}
			if (ctx.players.getLocal().getInteracting() == null) {
				cerberus.interact(SimpleNpcActions.ATTACK);
				ctx.log("Attacking Cerberus");
				Cerberus.status = "Attacking Cerberus";
			}
			//useSpecialSGS();
			//useSpecialDWH();
			//useSpecialClaws();
			useSpecialDDSS();
		} else {
			ctx.prayers.disableAll();
			SimpleItem hydraGloves = ctx.inventory.populate().filter(22981).next();
			SimpleItem berserkerRingi = ctx.inventory.populate().filter(11773).next();
	        SimpleItem berserkerRing = ctx.inventory.populate().filter(6737).next();
	        SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
	        SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
	        if (hydraGloves != null) {
	    		hydraGloves.interact(SimpleItemActions.FIRST);
	    		Cerberus.status = "Equipping Gloves";
	            ctx.log("Equipping Gloves");
	            ctx.onCondition(() -> ctx.equipment.populate().filter(22981).next() != null, 250, 1);
	    	}
	    	if (berserkerRingi != null) {
	    		berserkerRingi.interact(SimpleItemActions.FIRST);
	    		Cerberus.status = "Equipping Bringi";
	            ctx.log("Equipping Bringi");
	            ctx.onCondition(() -> ctx.equipment.populate().filter(11773).next() != null, 250, 1);
	    	} else if (berserkerRing != null) {
	    		berserkerRing.interact(SimpleItemActions.FIRST);
	    		Cerberus.status = "Equipping Bring";
	            ctx.log("Equipping Bring");
	            ctx.onCondition(() -> ctx.equipment.populate().filter(6737).next() != null, 250, 1);
	    	} else if (archerRing != null) {
	    		archerRing.interact(SimpleItemActions.FIRST);
	    		Cerberus.status = "Equipping Aring";
	            ctx.log("Equipping Aring");
	            ctx.onCondition(() -> ctx.equipment.populate().filter(6733).next() != null, 250, 1);
	    	} else if (archerRingi != null) {
	    		archerRingi.interact(SimpleItemActions.FIRST);
	    		Cerberus.status = "Equipping Aringi";
	            ctx.log("Equipping Aringi");
	            ctx.onCondition(() -> ctx.equipment.populate().filter(11771).next() != null, 250, 1);
	        }
		}
		int food = ctx.inventory.populate().filterHasAction("Eat").population(true);
		int brews = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
		SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
		SimpleItem prayer = ctx.inventory.populate().filterContains("Prayer").next();
		if (ctx.groundItems.populate().filterContains(Looting.lootNames).isEmpty() && cerberus == null && ((food <= 3 && brews <= 2) || (restore == null && prayer == null))) { // restocks once supplies are low
			ctx.magic.castHomeTeleport();
			ctx.sleep(600);
			Cerberus.status = "Restocking";
			ctx.log("Restocking, out of supplies");
			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			return;
		}
    }
    
	public static void useSpecialBP() {
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50
				&& ctx.players.getLocal().inCombat()
				&& ctx.players.getLocal().getHealth() <= ctx.players.getLocal().getMaxHealth() - 25) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleepCondition(() -> ctx.combat.specialAttack(), 1500);
		}
	}
    
    public static void useSpecialDWH() {
    	SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
    	SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
    	if (dwh != null || ctx.equipment.populate().filter(13576).next() != null) {
    		if (ctx.players.getLocal().getAnimation() == 1062 || ctx.players.getLocal().getAnimation() == 1378 || ctx.players.getLocal().getAnimation() == 401 || ctx.players.getLocal().getAnimation() == 4177) {
    			ctx.sleep(900);
    			Cerberus.special = false;
    		}
			if (ctx.combat.getSpecialAttackPercentage() >= 50 && (cerberus != null && cerberus.getHealth() >= 300)) {
				if (!ctx.inventory.populate().filter(13576).isEmpty()) {
					ctx.inventory.populate().filter(13576).next().interact(454);
				}
				if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
					ctx.inventory.populate().filterContains("defender").next().interact(454);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if (!ctx.equipment.populate().filter(13576).isEmpty() && Cerberus.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					Cerberus.special = true;
					Cerberus.status = "Using Special";
					ctx.log("Using Special");
					if (cerberus != null) {
						cerberus.interact(SimpleNpcActions.ATTACK);
					}
				}
			} else {
				Cerberus.special = false;
				if (scythe != null) {
					if (scythe != null) {
						scythe.interact(SimpleItemActions.WEAR);
					}
				} else if (ctx.equipment.populate().filter(22325).isEmpty() && scythe == null && ctx.equipment.populate().filter(13576).isEmpty()) {
					if (avernic != null) {
						avernic.interact(SimpleItemActions.WEAR);
					}
					if (dragondefender != null) {
						dragondefender.interact(SimpleItemActions.WEAR);
					}
					if (whip != null) {
						whip.interact(SimpleItemActions.WEAR);
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
    	}
    }
	public static void useSpecialDDSS() {
		SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
		SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
		SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
		SimpleItem prims = ctx.inventory.populate().filter(13239).next();
		SimpleItem torture = ctx.inventory.populate().filter(19553).next();
		SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
		SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
		SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
		SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
		SimpleItem defender = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem dds = ctx.inventory.populate().filterContains("Dragon dagger").next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
		if (dds != null || ctx.equipment.populate().filterContains("Dragon dagger").next() != null) {
    		if (ctx.players.getLocal().getAnimation() == 1062) {
    			Cerberus.special = false;
    		}
    		if (ctx.players.getLocal().getAnimation() == 376) {
    			Cerberus.special = false;
    		}
		if (ctx.combat.getSpecialAttackPercentage() >= 25) {
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
			if (dragonboots != null) {
				dragonboots.interact(SimpleItemActions.WEAR);
			}
			if (barrowsgloves != null) {
				barrowsgloves.interact(SimpleItemActions.WEAR);
			}
			if (voidmeleeor != null) {
				voidmeleeor.interact(SimpleItemActions.WEAR);
			}
			if (voidmelee != null) {
				voidmelee.interact(SimpleItemActions.WEAR);
			}
			if (dds != null) {
				dds.interact(454);
			}
			if (defender != null) {
				defender.interact(SimpleItemActions.WEAR);
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
			}
			if (!ctx.equipment.populate().filterContains("Dragon dagger").isEmpty() && Cerberus.special == false) {
				ctx.combat.toggleSpecialAttack(true);
				Cerberus.special = true;
				Cerberus.status = "Using Special";
				ctx.log("Using Special");
				ctx.sleep(600);
			}
		} else {
			Cerberus.special = false;
			if (scythe != null) {
				scythe.interact(SimpleItemActions.WEAR);
			}
			if (barrowsgloves != null) {
				barrowsgloves.interact(SimpleItemActions.WEAR);
			}
			if (torture != null) {
				torture.interact(SimpleItemActions.WEAR);
			}
			if (firecape != null) {
				firecape.interact(SimpleItemActions.WEAR);
			}
			if (firemaxcape != null) {
				firemaxcape.interact(SimpleItemActions.WEAR);
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
			if (dragonboots != null) {
				dragonboots.interact(SimpleItemActions.WEAR);
			}
			if (voidmelee != null) {
				voidmelee.interact(SimpleItemActions.WEAR);
			}
			if (voidmeleeor != null) {
				voidmeleeor.interact(SimpleItemActions.WEAR);
			}
			if (whip != null) {
				whip.interact(SimpleItemActions.WEAR);
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
			if (defender != null) {
				defender.interact(SimpleItemActions.WEAR);
			}
		}
	}
	}
    
    public static void useSpecialDDS() {
    	SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem dds = ctx.inventory.populate().filterContains("Dragon dagger").next();
    	SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
    	if (dds != null || ctx.equipment.populate().filterContains("Dragon dagger").next() != null) {
    		if (ctx.players.getLocal().getAnimation() == 1062 || ctx.players.getLocal().getAnimation() == 376 || ctx.players.getLocal().getAnimation() == 4177) {
    			Cerberus.special = false;
    			ctx.sleep(1000);
    		}
			if (ctx.combat.getSpecialAttackPercentage() >= 25) {
				if (!ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) {
					ctx.inventory.populate().filterContains("Dragon dagger").next().interact(454);
				}
				if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
					ctx.inventory.populate().filterContains("defender").next().interact(454);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if (!ctx.equipment.populate().filterContains("Dragon dagger").isEmpty() && Cerberus.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					Cerberus.special = true;
					Cerberus.status = "Using Special";
					ctx.log("Using Special");
					if (cerberus != null) {
						cerberus.interact(SimpleNpcActions.ATTACK);
					}
				}
			} else {
				Cerberus.special = true;
				if (scythe != null) {
					if (ctx.inventory.inventoryFull()) {
						SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
				    	SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
				    	if (food != null) {
				    		food.interact(SimpleItemActions.DROP);
				    	} else if (brew != null) {
				    		brew.interact(SimpleItemActions.DROP);
				    	}
					}
					if (scythe != null) {
						scythe.interact(SimpleItemActions.WEAR);
					}
				} else if (ctx.equipment.populate().filter(22325).isEmpty() && scythe == null && ctx.equipment.populate().filter(13576).isEmpty()) {
					if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
						ctx.inventory.populate().filterContains("defender").next().interact(454);
					}
					if (whip != null) {
						whip.interact(SimpleItemActions.WEAR);
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
    	}
    }
    
    public static void useSpecialClaws() {
    	SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem dclaws = ctx.inventory.populate().filter(20784).next();
    	SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
    	if (dclaws != null || !ctx.equipment.populate().filter(20784).isEmpty() && cerberus.getHealth() >= 200 && cerberus.getHealth() != 0) {
    		if (ctx.players.getLocal().getAnimation() == 7514 || ctx.players.getLocal().getAnimation() == 390) {
    			ctx.sleep(1000);
    			Cerberus.special = false;
    		}
			if (ctx.combat.getSpecialAttackPercentage() >= 50 && (cerberus != null && cerberus.getHealth() >= 200 && cerberus.getHealth() != 0)) {
				if (!ctx.inventory.populate().filter(20784).isEmpty()) {
					ctx.inventory.populate().filter(20784).next().interact(454);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if (!ctx.equipment.populate().filter(20784).isEmpty() && Cerberus.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					Cerberus.special = true;
					Cerberus.status = "Using Special";
					ctx.log("Using Special");
					if (cerberus != null) {
						cerberus.interact(SimpleNpcActions.ATTACK);
					}
				}
			} else {
				Cerberus.special = false;
				if (scythe != null) {
					if (scythe != null) {
						scythe.interact(SimpleItemActions.WEAR);
					}
				} else if (ctx.equipment.populate().filter(22325).isEmpty() && scythe == null && ctx.equipment.populate().filter(13576).isEmpty()) {
					if (avernic != null) {
						avernic.interact(SimpleItemActions.WEAR);
					}
					if (dragondefender != null) {
						dragondefender.interact(SimpleItemActions.WEAR);
					}
					if (whip != null) {
						whip.interact(SimpleItemActions.WEAR);
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
    	}
    }
    
    public static void useSpecialSGS() {
    	SimpleNpc souls = ctx.npcs.populate().filter(5867, 5868, 5769).nearest().next();
    	SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
        if (souls == null && cerberus != null && cerberus.getHealth() >= 70) {
        	if (ctx.combat.getSpecialAttackPercentage() >= 50 && !ctx.inventory.inventoryFull() && ctx.combat.health() <= (ctx.combat.maxHealth() - 20)) {
            	if (ctx.inventory.populate().filter(11806).next() != null) {
                    ctx.inventory.populate().filter(11806).next().interact(454);
                    Cerberus.status = "Switching To SGS";
    				ctx.log("Switching To SGS");
                    ctx.sleepCondition(() -> ctx.equipment.populate().filter(11806).next() != null, 2000);
                }
                if (ctx.equipment.populate().filter(11806).next() != null) {
                    ctx.combat.toggleSpecialAttack(true);
                    Cerberus.status = "Using Special";
                    ctx.log("Using Special");
                    if (cerberus != null) {
                    	cerberus.interact(412);
                    }
                    ctx.onCondition(() -> ctx.combat.health() >= (ctx.combat.maxHealth() - 19) || ctx.combat.getSpecialAttackPercentage() < 50, 250, 14);
                }
            } else {
                if (ctx.inventory.populate().filter(24551).next() != null) {
                	ctx.inventory.populate().filter(24551).next().interact(454);
                	Cerberus.status = "Switching Back";
    				ctx.log("Switching Back");
                	ctx.sleepCondition(() -> ctx.equipment.populate().filter(24417).next() != null, 2000);
                }
                if (ctx.inventory.populate().filterContains("defender").next() != null) {
                	ctx.inventory.populate().filterContains("defender").next().interact(454);
                	ctx.sleepCondition(() -> ctx.equipment.populate().filter(22322).next() != null, 2000);
                }
            }
        }
    }
    
    public static void handleEatingFood() {
    	SimpleNpc ghost = ctx.npcs.populate().filter(5868).nearest().next();
    	SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
    	SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (ghost != null && ctx.combat.health() <= 99) {
			Cerberus.status = "Drinking " + brew.getName().toString();
			ctx.log("Drinking " + brew.getName().toString());
			brew.interact(74);
			return;
		}
    	if (food != null && ctx.combat.health() <= Cerberus.eatAt && ctx.combat.health() != 10) {
			Cerberus.status = "Eating " + food.getName().toString();
			ctx.log("Eating " + food.getName().toString());
			food.interact(74);
			return;
		} else if (brew != null && ctx.combat.health() <= Cerberus.eatAt && ctx.combat.health() != 10) {
			Cerberus.status = "Drinking " + brew.getName().toString();
			ctx.log("Drinking " + brew.getName().toString());
			brew.interact(74);
			return;
		}
	}
	
	public static void handlePrayers() {
		SimpleNpc cerberus = ctx.npcs.populate().filter(5862).nearest().next();
		if (cerberus != null) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
			}
		}
		if (Cerberus.prayerType == -2 && !Cerberus.HOME.containsPoint(ctx.players.getLocal()) && cerberus != null) {
	        if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
	        	ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
	        }
	    } else if (Cerberus.prayerType == -1 && !Cerberus.HOME.containsPoint(ctx.players.getLocal()) && cerberus != null) {
	        if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
	            ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
	        }
	    } else if (Cerberus.prayerType == -3 && !Cerberus.HOME.containsPoint(ctx.players.getLocal()) && cerberus != null) {
	        if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
	            ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
	        }
	    }
	}
}
