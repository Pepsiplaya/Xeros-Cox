package me.pepsi.xeros.autopk;

import java.util.function.BooleanSupplier;

import simple.api.ClientContext;
import simple.api.HeadIcon;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimplePlayerActions;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.queries.SimplePlayerQuery;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.wrappers.definitions.ItemDefinition;

public class GearSwitches extends Thread {
	
	private ClientContext ctx;
	private static BooleanSupplier condition;
	private static long currTick = 0;
	public GearSwitches(ClientContext ctx, BooleanSupplier condition) {
		this.ctx = ctx;
		GearSwitches.condition = condition;
		this.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
	}
	
	public static void run(ClientContext ctx) {
		while (!ctx.client.isLoggedIn()) {
			ctx.log("Not logged in");
			ctx.sleep(500);
		}
		while (true) {
			if (ctx.magic.isSpellSelected()) {
				ctx.log("Test2");
				ctx.sleep(10);
				continue;
			}
			ctx.sleep(10);
		}
	}
	static SimplePlayer target;
	public static void prayerSwitches(ClientContext ctx) {
		SimpleItem brcBow = ctx.inventory.populate().filter(25001).next();
		SimpleItem zaryteBow = ctx.inventory.populate().filter(14990).next();
		SimpleItem thirdAgeBow = ctx.inventory.populate().filter(12424).next();
		SimpleItem dragonKnifes = ctx.inventory.populate().filter(22829).next();
		SimpleItem morrigansTop = ctx.inventory.populate().filter(13870).next();
		SimpleItem morrigansBottom = ctx.inventory.populate().filter(13873).next();
		SimpleItem karilsTop = ctx.inventory.populate().filter(4736).next();
		SimpleItem karilsSkirt = ctx.inventory.populate().filter(4738).next();
		SimpleItem avas = ctx.inventory.populate().filter(10499).next();
		SimpleItem ancientStaff = ctx.inventory.populate().filter(4675).next();
		SimpleItem staffOfLight = ctx.inventory.populate().filter(21032).next();
		SimpleItem staffOfLight2 = ctx.inventory.populate().filter(21034).next();
		SimpleItem staffOfTheDead = ctx.inventory.populate().filter(11791).next();
		SimpleItem bAncientStaff = ctx.inventory.populate().filter(25003).next();
		SimpleItem toxicStaffOfTheDead = ctx.inventory.populate().filter(12904).next();
		SimpleItem infinityBoots = ctx.inventory.populate().filter(6920).next();
		SimpleItem zurielsTop = ctx.inventory.populate().filter(13858).next();
		SimpleItem zurielsBottom = ctx.inventory.populate().filter(13861).next();
		SimpleItem ahrimsTop = ctx.inventory.populate().filter(4712).next();
		SimpleItem ahrimsBottom = ctx.inventory.populate().filter(4714).next();
		SimpleItem bookOfDarkness = ctx.inventory.populate().filter(20481).next();
		SimpleItem tomeOfFire = ctx.inventory.populate().filter(20449).next();
		SimpleItem zammyCape = ctx.inventory.populate().filter(2414).next();
		SimpleItem dclaws = ctx.inventory.populate().filter(14484).next();
		SimpleItem ags = ctx.inventory.populate().filter(11694).next();
		SimpleItem fireCape = ctx.inventory.populate().filter(6570).next();
		SimpleItem dBoots = ctx.inventory.populate().filter(11732).next();
		
		SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
		SimpleItem combat = ctx.inventory.populate().filterContains("Super combat ").next();
		SimpleItem ranging = ctx.inventory.populate().filterContains("Ranging ").next();
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		final int realRange = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedRange = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		final int realStrength = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
		final int boostedStrength = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
		final int realMagic = ctx.skills.getRealLevel(SimpleSkills.Skill.MAGIC);
		final int boostedMagic = ctx.skills.getLevel(SimpleSkills.Skill.MAGIC);
		if (ctx.combat.health() < 70 && ctx.combat.health() > 50) {
			if (brew != null) {
				brew.interact(SimpleItemActions.CONSUME);
			}
		} else if (ctx.combat.health() <= 50) {
			if (food != null) {
				food.interact(SimpleItemActions.CONSUME);
			}
			if (brew != null) {
				brew.interact(SimpleItemActions.CONSUME);
			}
		}
		if (ctx.combat.health() >= 65) {
			if (ctx.prayers.points() <= 50) {
				if (restore != null) {
					restore.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
					return;
				}
			}
			if (boostedMagic - realMagic <= -1) {
				if (restore != null) {
					restore.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
					return;
				}
			}
			if (boostedRange - realRange <= -1) {
				if (restore != null) {
					restore.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
					return;
				}
			} else if (boostedRange - realRange <= 0) {
				if (ranging != null) {
					ranging.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
				}
			}
			if (boostedStrength - realStrength <= -1) {
				if (restore != null) {
					restore.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
					return;
				}
			} else if (boostedStrength - realStrength <= 0) {
				if (combat != null) {
					combat.interact(SimpleItemActions.CONSUME);
					ctx.sleep(600);
				}
			}
		}
        SimplePlayerQuery<SimplePlayer> players = ctx.players.populate().filter(p -> !p.getName().equals(ctx.players.getLocal().getName()));
        for (SimplePlayer p : players) {
        	if (ctx.players.getLocal().getInteracting() != null && ctx.players.getLocal().getInteracting().equals(p)) {
        		target = p;
        		PK.status = "Target Found";
            }
        	if (target != null && (target.getGraphic() == 1576 || target.getAnimation() == 9599 || target.getAnimation() == 8939)) {
    			target = null;
    			PK.teleBlocked = System.currentTimeMillis();
    			PK.status = "Target Teleported";
    		}
        	if (target != null && target.getHealth() <= 0) {
    			target = null;
    			PK.teleBlocked = System.currentTimeMillis();
    			PK.status = "Target Died";
    		}
            if (p != null) {
                if (target != null) {
                	int gear[] = target.getEquipment();
                	ItemDefinition def = ctx.definitions.getItemDefinition(gear[3] - 512);
                	if (def.getName().contains("bow") || def.getName().contains("cannon") || def.getName().contains("knife")) {
                		PK.status = "Switching To Range Prayer";
               		    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
               		    	ctx.log("Range Weapon Detected, Switching To Range Prayers");
               		    	ctx.sleep(0, 600);
                        	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES);
                        }
                  	} else if (def.getName().contains("korasi") || def.getName().contains("staff") || def.getName().contains("Staff") || def.getName().contains("wand") || def.getName().contains("sceptre")) {
                  		PK.status = "Switching To Magic Prayer";
                  		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
               		    	ctx.log("Magic Weapon Detected, Switching To Mage Prayers");
               		    	ctx.sleep(0, 600);
                            ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC);
                        }
               	    } else if (def.getName().contains("sword") || def.getName().contains("axe") || def.getName().contains("spear") || def.getName().contains("maul") || def.getName().contains("mace") || def.getName().contains("rapier") || def.getName().contains("dagger") || def.getName().contains("bludgeon") || def.getName().contains("whip") || def.getName().contains("Blade") || def.getName().contains("scythe") || def.getName().contains("Scythe") || def.getName().contains("claws") || def.getName().contains("scimitar") || def.getName().contains("hammer")) {
               	    	PK.status = "Switching To Melee Prayer";
               	    	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
               	        	ctx.log("Melee Weapon Detected, Switching To Melee Prayers");
               	        	ctx.sleep(0, 600);
                       	    ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE);
                        }
                	}
                    if ((target.getOverheadIcon() != null && !target.getOverheadIcon().equals(HeadIcon.MELEE)) || (target.getHeadIcon() != 13 && target.getOverheadIcon() == null)) {
            			if ((target.getHealth() <= 45 && target.getHealth() != 0) && (ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.combat.getSpecialAttackPercentage() != 50)) {
            				PK.status = "Using Special";
            				if (ags != null) {
            					ctx.log("Target Hp Low, Attempting To Spec");
            					ags.interact(SimpleItemActions.WEAR);
            					ctx.sleep(100, 150);
            				} else if (dclaws != null) {
            					ctx.log("Target Hp Low, Attempting To Spec");
            					dclaws.interact(SimpleItemActions.WEAR);
            					ctx.sleep(100, 150);
            				}
            				if (fireCape != null) {
            					fireCape.interact(SimpleItemActions.WEAR);
            					ctx.sleep(100, 150);
            				}
            				if (dBoots != null) {
            					dBoots.interact(SimpleItemActions.WEAR);
            					ctx.sleep(100, 150);
            				}
            				final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
            				if (realPrayer >= 70) {
            					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
            		    			ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
            		    		}
            				} else {
            					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.ULTIMATE_STRENGTH)) {
            		    			ctx.prayers.prayer(SimplePrayers.Prayers.ULTIMATE_STRENGTH, true);
            		    		}
            					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.INCREDIBLE_REFLEXES)) {
            		    			ctx.prayers.prayer(SimplePrayers.Prayers.INCREDIBLE_REFLEXES, true);
            		    		}
            				}
            				ctx.combat.toggleSpecialAttack(true);
            				if (target != null) {
            					target.interact(SimplePlayerActions.ATTACK);
            					ctx.sleep(1800);
            				}
            				return;
            			}
            		}
                    if (target.getRemainingPath() >= 1) {
                    	PK.status = "Snaring";
                    	if (staffOfLight2 != null) {
             				ctx.log("Target Moving, Attempting To Snare");
             				staffOfLight2.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (staffOfLight != null) {
             				ctx.log("Target Moving, Attempting To Snare");
             				staffOfLight.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (toxicStaffOfTheDead != null) {
                    		ctx.log("Target Moving, Attempting To Snare");
            				toxicStaffOfTheDead.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (staffOfTheDead != null) {
            				ctx.log("Target Moving, Attempting To Snare");
            				staffOfTheDead.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (bAncientStaff != null) {
            				ctx.log("Target Moving, Attempting To Snare");
            				bAncientStaff.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (ancientStaff != null) {
            				ctx.log("Target Moving, Attempting To Snare");
            				ancientStaff.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (tomeOfFire != null) {
            				tomeOfFire.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (bookOfDarkness != null) {
            				bookOfDarkness.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (infinityBoots != null) {
            				infinityBoots.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zurielsTop != null) {
            				zurielsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zurielsBottom != null) {
            				zurielsBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (ahrimsTop != null) {
            				ahrimsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (ahrimsBottom != null) {
            				ahrimsBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zammyCape != null) {
            				zammyCape.interact(SimpleItemActions.WEAR);
            			}
            			final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
            			if (realPrayer >= 77) {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
            	    		}
            			} else {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.MYSTIC_MIGHT)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.MYSTIC_MIGHT, true);
            	    		}
            			}
            			ctx.sleep(250, 600);
            			ctx.magic.castSpellOnPlayer(1592, target);
            			return;
                    }
                    if (PK.teleBlocked <= System.currentTimeMillis()) {
                    	PK.status = "Teleblocking";
                    	if (staffOfLight2 != null) {
                    		ctx.log("Attempting To Teleblock");
             				staffOfLight2.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (staffOfLight != null) {
                    		ctx.log("Attempting To Teleblock");
             				staffOfLight.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (toxicStaffOfTheDead != null) {
                    		ctx.log("Attempting To Teleblock");
            				toxicStaffOfTheDead.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (staffOfTheDead != null) {
            				ctx.log("Attempting To Teleblock");
            				staffOfTheDead.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (bAncientStaff != null) {
            				ctx.log("Attempting To Teleblock");
            				bAncientStaff.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (ancientStaff != null) {
            				ctx.log("Attempting To Teleblock");
            				ancientStaff.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (tomeOfFire != null) {
            				tomeOfFire.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			} else if (bookOfDarkness != null) {
            				bookOfDarkness.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (infinityBoots != null) {
            				infinityBoots.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zurielsTop != null) {
            				zurielsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zurielsBottom != null) {
            				zurielsBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (ahrimsTop != null) {
            				ahrimsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (ahrimsBottom != null) {
            				ahrimsBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (zammyCape != null) {
            				zammyCape.interact(SimpleItemActions.WEAR);
            			}
            			final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
            			if (realPrayer >= 77) {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
            	    		}
            			} else {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.MYSTIC_MIGHT)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.MYSTIC_MIGHT, true);
            	    		}
            			}
            			ctx.sleep(250, 600);
            			ctx.magic.castSpellOnPlayer(12445, target);
            			return;
                    } else if ((target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.RANGED)) || target.getHeadIcon() == 15) {
                    	if (staffOfLight2 != null) {
                    		PK.status = "Switching To Mage";
                    		ctx.log("Range Prayer Detected, Switching To Mage");
             				staffOfLight2.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (staffOfLight != null) {
             				PK.status = "Switching To Mage";
                    		ctx.log("Range Prayer Detected, Switching To Mage");
             				staffOfLight.interact(SimpleItemActions.WEAR);
             				ctx.sleep(100, 150);
             			} else if (toxicStaffOfTheDead != null) {
             				PK.status = "Switching To Mage";
                    		ctx.log("Range Prayer Detected, Switching To Mage");
                			toxicStaffOfTheDead.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		} else if (staffOfTheDead != null) {
                			PK.status = "Switching To Mage";
                			ctx.log("Range Prayer Detected, Switching To Mage");
                			staffOfTheDead.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		} else if (bAncientStaff != null) {
                			PK.status = "Switching To Mage";
                			ctx.log("Range Prayer Detected, Switching To Mage");
                			bAncientStaff.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		} else if (ancientStaff != null) {
                			PK.status = "Switching To Mage";
                			ctx.log("Range Prayer Detected, Switching To Mage");
                			ancientStaff.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (tomeOfFire != null) {
                			tomeOfFire.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		} else if (bookOfDarkness != null) {
                			bookOfDarkness.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (infinityBoots != null) {
                			infinityBoots.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (zurielsTop != null) {
                			zurielsTop.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (zurielsBottom != null) {
                			zurielsBottom.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (ahrimsTop != null) {
                			ahrimsTop.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (ahrimsBottom != null) {
                			ahrimsBottom.interact(SimpleItemActions.WEAR);
                			ctx.sleep(100, 150);
                		}
                		if (zammyCape != null) {
                			zammyCape.interact(SimpleItemActions.WEAR);
                		}
                		final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
                		if (realPrayer >= 77) {
                			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
                    			ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
                    		}
                		} else {
                			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.MYSTIC_MIGHT)) {
                    			ctx.prayers.prayer(SimplePrayers.Prayers.MYSTIC_MIGHT, true);
                    		}
                		}
                		ctx.sleep(250, 600);
                		ctx.magic.castSpellOnPlayer(19130, target);
                		return;
                	}
            		if ((target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.MAGIC)) || (target.getHeadIcon() == 14 || (target.getOverheadIcon() == null && target.getHeadIcon() != 14 && target.getHeadIcon() != 13 && target.getHeadIcon() != 15))) {
            			if (zaryteBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Mage Prayer Detected, Switching To Range");
            				zaryteBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (thirdAgeBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Mage Prayer Detected, Switching To Range");
            				thirdAgeBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (brcBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Mage Prayer Detected, Switching To Range");
            				brcBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (dragonKnifes != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Mage Prayer Detected, Switching To Range");
            				dragonKnifes.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (morrigansTop != null) {
            				morrigansTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (morrigansBottom != null) {
            				morrigansBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (karilsTop != null) {
            				karilsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (karilsSkirt != null) {
            				karilsSkirt.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (avas != null) {
            				avas.interact(SimpleItemActions.WEAR);
            			}
            			final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
            			if (realPrayer >= 74) {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
            	    		}
            			} else {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
            	    		}
            			}
            			ctx.sleep(100, 150);
            			target.interact(SimplePlayerActions.ATTACK);
            			return;
            		}
            		if ((target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.MELEE)) || target.getHeadIcon() == 13) {
            			if (zaryteBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Melee Prayer Detected, Switching To Range");
            				zaryteBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (thirdAgeBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Melee Prayer Detected, Switching To Range");
            				thirdAgeBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (brcBow != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Melee Prayer Detected, Switching To Range");
            				brcBow.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (dragonKnifes != null) {
            				PK.status = "Switching To Range";
            				ctx.log("Melee Prayer Detected, Switching To Range");
            				dragonKnifes.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (morrigansTop != null) {
            				morrigansTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (morrigansBottom != null) {
            				morrigansBottom.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (karilsTop != null) {
            				karilsTop.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (karilsSkirt != null) {
            				karilsSkirt.interact(SimpleItemActions.WEAR);
            				ctx.sleep(100, 150);
            			}
            			if (avas != null) {
            				avas.interact(SimpleItemActions.WEAR);
            			}
            			final int realPrayer = ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER);
            			if (realPrayer >= 74) {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
            	    		}
            			} else {
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
            	    			ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
            	    		}
            			}
            			ctx.sleep(100, 150);
            			target.interact(SimplePlayerActions.ATTACK);
            			return;
            		}
                }
            }
        }	
	}
}
