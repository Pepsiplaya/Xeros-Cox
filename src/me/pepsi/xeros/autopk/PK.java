package me.pepsi.xeros.autopk;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimplePlayer;
import simple.api.wrappers.definitions.ItemDefinition;
import simple.api.HeadIcon;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimplePlayerActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import java.util.function.BooleanSupplier;
import simple.api.listeners.SimpleMessageListener;
import simple.api.queries.SimplePlayerQuery;
import simple.api.script.Script;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@ScriptManifest(author = "Pepsiplaya", name = "[P] PK Assist", category = Category.OTHER, version = "1.0D", description = "Auto NH's", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class PK extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener, KeyListener {
	
	public long antiPkLastAnimation;
	public long startTime;
	public boolean range;
	public static String status;
	static public long teleBlocked;
	SimplePlayer target;
	
	private GearSwitches gearSwitches = null;
	
	@Override
	public boolean onExecute() {
		antiPkLastAnimation = 0;
		startTime = System.currentTimeMillis();
		teleBlocked = 0;
		target = null;
		range = true;
		status = "Starting";
		if (ctx.combat.autoRetaliate()) {
			ctx.combat.toggleAutoRetaliate(false);
		}
		try {
			gearSwitches = new GearSwitches(ctx, new BooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return true;
				}
			});
			gearSwitches.start();
		} catch (Exception e) {
			ctx.log(e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	@Override
	public void onProcess() {
		if (ctx.client.isLoggedIn()) {
			int gear[] = ctx.players.getLocal().getEquipment();
			ItemDefinition def = ctx.definitions.getItemDefinition(gear[3] - 512);
			if (def.getName().contains("staff")) {
				ctx.log("Staff Detected");
			}
			SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
			SimpleItem combat = ctx.inventory.populate().filterContains("Super combat ").next();
			SimpleItem ranging = ctx.inventory.populate().filterContains("Ranging ").next();
			final int realRange = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
			final int boostedRange = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
			final int realStrength = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
			final int boostedStrength = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
			final int realMagic = ctx.skills.getRealLevel(SimpleSkills.Skill.MAGIC);
			final int boostedMagic = ctx.skills.getLevel(SimpleSkills.Skill.MAGIC);
			if (HOME.containsPoint(ctx.players.getLocal())) {
				target = null;
				ctx.prayers.disableAll();
				if (ctx.combat.health() >= 65) {
					if (ctx.prayers.points() <= 30) {
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
					} else if (boostedRange - realRange <= 6) {
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
					} else if (boostedStrength - realStrength <= 10) {
						if (combat != null) {
							combat.interact(SimpleItemActions.CONSUME);
							ctx.sleep(600);
						}
					}
				}
			} else {
				if (ctx.combat.isPoisoned() || ctx.combat.isVenomed()) {
					SimpleItem sanfew = ctx.inventory.populate().filterContains("Sanfew serum").next();
					if (sanfew != null) {
						sanfew.interact(74);
					}
				}
				if (ctx.prayers.points() > 0) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_ITEM)) {
		    			ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_ITEM, true);
		    		}
				}
				prayerSwitches();
				if (ctx.combat.health() >= 65) {
					if (ctx.prayers.points() <= 30) {
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
					} else if (boostedRange - realRange <= 6) {
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
					} else if (boostedStrength - realStrength <= 10) {
						if (combat != null) {
							combat.interact(SimpleItemActions.CONSUME);
							ctx.sleep(600);
						}
					}
				}
			}	
		}
		GearSwitches.run(ctx);
	}
	
	public void prayerSwitches() {
		SimpleItem voidMelee = ctx.inventory.populate().filter(26477).next();
		SimpleItem voidRange = ctx.inventory.populate().filter(26475).next();
		SimpleItem voidMage = ctx.inventory.populate().filter(26473).next();
		SimpleItem ancientStaff = ctx.inventory.populate().filter(4675).next();
		SimpleItem occultNecklace = ctx.inventory.populate().filter(12002).next();
		SimpleItem spiritShield = ctx.inventory.populate().filter(12831).next();
		SimpleItem imbuedGuthixMaxCape = ctx.inventory.populate().filter(21784).next();
		SimpleItem amuletOfTorture = ctx.inventory.populate().filter(19553).next();
		SimpleItem infernalMaxCape = ctx.inventory.populate().filter(21285).next();
		SimpleItem ags = ctx.inventory.populate().filter(11802).next();
		SimpleItem heavyBallista = ctx.inventory.populate().filter(19481).next();
		SimpleItem necklaceOfAnguish = ctx.inventory.populate().filter(19547).next();
		SimpleItem assemblerMaxCape = ctx.inventory.populate().filter(21898).next();
        SimplePlayerQuery<SimplePlayer> players = ctx.players.populate().filter(p -> !p.getName().equals(ctx.players.getLocal().getName()));
        for (SimplePlayer p : players) {
        	if (ctx.players.getLocal().getInteracting() != null && ctx.players.getLocal().getInteracting().equals(p)) {
        		target = p;
            }
        	if ((target != null && target.getHealth() <= 0) || (target != null && (target.getAnimation() == 714 || target.getAnimation() == 1979))) {
    			target = null;
    			ctx.log("Target null");
    		}
            if (p != null) {
                if (target != null) {
                	if (target.getAnimation() == 2068 || target.getAnimation() == 806 || target.getAnimation() == 7222 || target.getAnimation() == 12152 || target.getAnimation() == 884 || target.getAnimation() == 426 || target.getAnimation() == 4230 || target.getAnimation() == 2075 || target.getAnimation() == 7218) {
                        antiPkLastAnimation = System.currentTimeMillis() + 2000;
                    }
                    if (target.distanceTo(ctx.players.getLocal().getLocation()) <= 1 && (System.currentTimeMillis() >= (antiPkLastAnimation - 1000))) {
                    	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
                    		ctx.log("Target Close, Switching To Melee Prayer");
                    		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE);
                    	}
                    } else if (System.currentTimeMillis() <= antiPkLastAnimation) {
                    	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
                    		ctx.log("Range Attack Detected, Switching To Range Prayer");
                    		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES);
                    	}
                    } else if (range) {
                    	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
                    		ctx.log("Switching To Mage Prayers, No Other Attacked Registered");
                    		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC);
                    	}
                    }
                    if ((target.getOverheadIcon() != null && !target.getOverheadIcon().equals(HeadIcon.MELEE)) || target.getOverheadIcon() == null) {
            			if ((target.getHealth() <= 45 && target.getHealth() != 0) && (ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.combat.getSpecialAttackPercentage() != 50)) {
            				if (ags != null) {
            					ctx.log("Target Hp Low, Attempting To Spec");
            					ags.interact(SimpleItemActions.WEAR);

            				}
            				if (voidMelee != null) {
            					voidMelee.interact(SimpleItemActions.WEAR);

            				}
            				if (amuletOfTorture != null) {
            					amuletOfTorture.interact(SimpleItemActions.WEAR);

            				}
            				if (infernalMaxCape != null) {
            					infernalMaxCape.interact(SimpleItemActions.WEAR);

            				}
            				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
            		    		ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
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
                    	if (ancientStaff != null) {
            				ctx.log("Target Moving, Attempting To Snare");
            				ancientStaff.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (voidMage != null) {
            				voidMage.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (occultNecklace != null) {
            				occultNecklace.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (imbuedGuthixMaxCape != null) {
                    		imbuedGuthixMaxCape.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (spiritShield != null) {
            				spiritShield.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
            	    		ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
            	    	}
            			ctx.sleep(250, 600);
            			ctx.magic.castSpellOnPlayer(12891, target);
            			return;
                    }
                    if ((target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.RANGED) || target.getOverheadIcon() == null)) {
                    	if (ancientStaff != null) {
            				ctx.log("Range/No Prayer Detected");
            				ancientStaff.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (voidMage != null) {
            				voidMage.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (occultNecklace != null) {
            				occultNecklace.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (imbuedGuthixMaxCape != null) {
                    		imbuedGuthixMaxCape.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (spiritShield != null) {
            				spiritShield.interact(SimpleItemActions.WEAR);
            				
            			}
                		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
                    		ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
                    	}
                		ctx.sleep(250, 600);
                		ctx.magic.castSpellOnPlayer(12891, target);
                	}
            		if (target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.MAGIC)) {
            			if (heavyBallista != null) {
            				ctx.log("Mage Prayer Detected, Switching To Range");
            				heavyBallista.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (voidRange != null) {
            				voidRange.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (necklaceOfAnguish != null) {
            				necklaceOfAnguish.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (assemblerMaxCape != null) {
            				assemblerMaxCape.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
            	    		ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
            	    	}
            			
            			target.interact(SimplePlayerActions.ATTACK);
            		}
            		if (target.getOverheadIcon() != null && target.getOverheadIcon().equals(HeadIcon.MELEE)) {
            			if (ancientStaff != null) {
            				ctx.log("Melee Prayer Detected, Switching To Range");
            				ancientStaff.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (voidMage != null) {
            				voidMage.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (occultNecklace != null) {
            				occultNecklace.interact(SimpleItemActions.WEAR);
            				
            			}
                    	if (spiritShield != null) {
            				spiritShield.interact(SimpleItemActions.WEAR);
            				
            			}
            			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
            	    		ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
            	    	}
            			ctx.sleep(250, 600);
                		ctx.magic.castSpellOnPlayer(12891, target);
            		}
                }
            }
        }	
	}
	
	@Override
	public void onTerminate() {

	}

	private final Color color1 = new Color(255, 255, 255);
    private final Color color2 = new Color(29, 3, 3, 94);
    private final Color color3 = new Color(0, 0, 0);
    private final BasicStroke stroke1 = new BasicStroke(1);
    
	@Override
	public void onPaint(Graphics2D g) {
		int brews = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
		int restores = ctx.inventory.populate().filterContains("restore").population(true);
		g.setColor(color2);
	    g.fillRect(7, 19, 235, 20);
	    g.fillRect(7, 39, 150, 12);
	    g.fillRect(7, 51, 60, 10);
	    g.fillRect(7, 61, 67, 14);
	    g.fillRect(7, 75, 100, 15);
	    g.fillRect(7, 90, 100, 13);
	    g.fillRect(7, 103, 109, 12);
	    g.setColor(color3);
	    g.setStroke(stroke1);
	    g.setColor(color1);
	    Font currentFont = g.getFont();
	    Font newFont = currentFont.deriveFont(currentFont.getSize() * 1F);
	    g.setFont(newFont);
	    g.drawString("Made By Pepsiplaya - Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 35);
	    g.drawString("Current Task: " + status, 10, 48);
	    g.drawString("Brews: " + brews, 10, 61);
	    g.drawString("Restores: " + restores, 10, 74);
	    if (target != null) {
	    	g.drawString("Target: " + target, 10, 87);
	    	g.drawString("Enemy Health: " + target.getHealth(), 10, 99);
	    } else {
	    	g.drawString("Target: Searching", 10, 87);
	    	g.drawString("Enemy Health: N/A", 10, 100);
	    }
	    if (teleBlocked >= System.currentTimeMillis()) {
	    	g.drawString("Is Enemy TBed: True", 10, 113);
	    } else {
	    	g.drawString("Is Enemy TBed: False", 10, 113);
	    }
    }

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0) {
            if (event.getMessage().contains("You need at least level")) {
            	SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
            	if (restore != null) {
					restore.interact(SimpleItemActions.CONSUME);
				}
            }
        }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		SimpleItem voidMelee = ctx.inventory.populate().filter(26477).next();
		SimpleItem voidRange = ctx.inventory.populate().filter(26475).next();
		SimpleItem voidMage = ctx.inventory.populate().filter(26473).next();
		SimpleItem ancientStaff = ctx.inventory.populate().filter(4675).next();
		SimpleItem occultNecklace = ctx.inventory.populate().filter(12002).next();
		SimpleItem spiritShield = ctx.inventory.populate().filter(12831).next();
		SimpleItem imbuedGuthixMaxCape = ctx.inventory.populate().filter(21784).next();
		SimpleItem amuletOfTorture = ctx.inventory.populate().filter(19553).next();
		SimpleItem infernalMaxCape = ctx.inventory.populate().filter(21285).next();
		SimpleItem ags = ctx.inventory.populate().filter(11802).next();
		SimpleItem heavyBallista = ctx.inventory.populate().filter(19481).next();
		SimpleItem necklaceOfAnguish = ctx.inventory.populate().filter(19547).next();
		SimpleItem assemblerMaxCape = ctx.inventory.populate().filter(21898).next();
		if (e.getKeyCode() == KeyEvent.VK_1) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			if (heavyBallista != null) {
				heavyBallista.interact(SimpleItemActions.WEAR);
				
			}
			if (voidRange != null) {
				voidRange.interact(SimpleItemActions.WEAR);
				
			}
			if (necklaceOfAnguish != null) {
				necklaceOfAnguish.interact(SimpleItemActions.WEAR);
				
			}
			if (assemblerMaxCape != null) {
				assemblerMaxCape.interact(SimpleItemActions.WEAR);
				
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
	    		ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
	    	}
			if (target != null) {
				target.interact(SimplePlayerActions.ATTACK);
			}
	    }
		if (e.getKeyCode() == KeyEvent.VK_2) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			if (ancientStaff != null) {
				ancientStaff.interact(SimpleItemActions.WEAR);
				
			}
        	if (voidMage != null) {
				voidMage.interact(SimpleItemActions.WEAR);
				
			}
        	if (occultNecklace != null) {
				occultNecklace.interact(SimpleItemActions.WEAR);
				
			}
        	if (imbuedGuthixMaxCape != null) {
        		imbuedGuthixMaxCape.interact(SimpleItemActions.WEAR);
				
			}
        	if (spiritShield != null) {
				spiritShield.interact(SimpleItemActions.WEAR);
				
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
	    		ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
	    	}
			if (target != null) {
				ctx.magic.castSpellOnPlayer(12891, target);
			} else {
				ctx.magic.selectSpell(12891);
			}
	    }
		if (e.getKeyCode() == KeyEvent.VK_3) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			if (ags != null) {
				ags.interact(SimpleItemActions.WEAR);
				
			}
			if (voidMelee != null) {
				voidMelee.interact(SimpleItemActions.WEAR);
				
			}
			if (amuletOfTorture != null) {
				amuletOfTorture.interact(SimpleItemActions.WEAR);
				
			}
			if (infernalMaxCape != null) {
				infernalMaxCape.interact(SimpleItemActions.WEAR);
				
			}
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
	    		ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
	    	}
			ctx.combat.toggleSpecialAttack(true);
			if (target != null) {
				target.interact(SimplePlayerActions.ATTACK);
				ctx.sleep(1800);
			}
	    }
		if (e.getKeyCode() == KeyEvent.VK_E) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
			SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
			SimpleItem restore = ctx.inventory.populate().filterContains("restore").next();
			if (ctx.combat.health() < 99 && ctx.combat.health() > 50) {
				if (food != null) {
					food.interact(SimpleItemActions.CONSUME);
				} else {
					if (brew != null) {
						brew.interact(SimpleItemActions.CONSUME);
					}
				}
				if (restore != null && ctx.prayers.points() <= 50) {
					restore.interact(SimpleItemActions.CONSUME);
				}
			} else if (ctx.combat.health() <= 50) {
				if (food != null) {
					food.interact(SimpleItemActions.CONSUME);
				} else {
					if (brew != null) {
						brew.interact(SimpleItemActions.CONSUME);
					}
				}
				if (restore != null && ctx.prayers.points() <= 50) {
					restore.interact(SimpleItemActions.CONSUME);
				}
			}
	    }
		if (e.getKeyCode() == KeyEvent.VK_T) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			SimpleItem royalSeedPod = ctx.inventory.populate().filter(19564).next();
			if (royalSeedPod != null) {
				royalSeedPod.interact(74);
			}
        }
		if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
			ctx.keyboard.clickKey(KeyEvent.VK_BACK_SPACE);
			if (range) {
				ctx.log("Will use ONLY range prayer");
				range = false;
			} else if (!range) {
				ctx.log("Will use ALL prayers");
				range = true;
			}
        }
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public int loopDuration() {
		return 250;
	}
}