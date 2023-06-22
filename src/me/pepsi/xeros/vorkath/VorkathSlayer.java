package me.pepsi.xeros.vorkath;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;


@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Vorkath", category = Category.MONEYMAKING, version = "1.6D",
        description = "Kills Vorkath for GP", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class VorkathSlayer extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {
	final static ClientContext ctx = ClientContext.instance();
    private static String status;
    private long startTime;
    private long vorkathKills;
    boolean started;
    private static boolean staff = false;
    private Image staffImage;
    private int prayerType;
    private long lastPotion = 0;
    static int slot = 1;
    private long lastHeart = -1;
    private boolean presetLoaded = false;
    private static long lastOverload = -1;
	
	public static boolean teleport = false;
	
	public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
    
    public static final WorldArea VORKATH_AREA = new WorldArea(
            new WorldPoint(2262, 4054, 0),
            new WorldPoint(2282, 4076, 0));
    
    public static final WorldArea OUTSIDE_VORKATH_AREA = new WorldArea(
            new WorldPoint(2268, 4037, 0),
            new WorldPoint(2276, 4053, 0));
    
    
    public final WorldPoint vork = new WorldPoint(2272, 4061, 0);
    
    public static final String[] lootNames = { "Magic seed", "ragon bones", "Rune long", "Rune kite", "Rune dart tip", "Dragon dart tip", "necklace", "Jar", "Draconic", "Skeletal", "Vork", "head", "Dragon a", "coin", "Resource box", "Magic logs", "ore", "Red dragonhide", "Green dragonhide", "Wrath r", "Chaos r", "Clue scroll", "Manta ray", "Death rune", "Crystal", "Dragon p", "Dragon battleaxe", "Dragon l", "Dragon m"};
   
    public GUI gui;

    @Override
    public boolean onExecute() {
    	lastPotion = System.currentTimeMillis() - 177000;
        startTime = System.currentTimeMillis();
        vorkathKills = 0;
        presetLoaded = false;
        teleport = false;
        status = "Waiting to start...";
        try {
        	staffImage = ImageIO.read(new URL("https://i.imgur.com/E8ZkG80.png"));
        } catch (IOException e) {
        	ctx.log("ERROR LOADING PAINT");
        }
        try {
            VorkathSlayer script = this;
            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
                gui = new GUI(script);
            }});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onProcess() {
    	if (getNearbyStaff() != null) {
    		if (!HOME_AREA.containsPoint(ctx.players.getLocal())) {
    			Toolkit.getDefaultToolkit().beep();
    		}  
    		status = "STAFF NEARBY: " + getNearbyStaff().getName() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal());
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		staff = true;
    	} else {
    		staff = false;
    	}
    	if (ctx.dialogue.dialogueOpen() && ctx.widgets.getBackDialogId() != 306 && ctx.widgets.getBackDialogId() != 4882 && ctx.widgets.getBackDialogId() != 2459 && ctx.widgets.getBackDialogId() != 23753 && ctx.widgets.getBackDialogId() != 356 && ctx.widgets.getBackDialogId() != 4893 && ctx.widgets.getBackDialogId() != 363 && ctx.widgets.getBackDialogId() != 2469 && ctx.widgets.getBackDialogId() != 968) {
    		ctx.log("Unknown dialogue open react quickly!");
    		ctx.sleep(1000);
			Toolkit.getDefaultToolkit().beep();
			return;
		}
    	if (!started) {
    		return;
    	}
    	if (HOME_AREA.containsPoint(ctx.players.getLocal())) {
    		ctx.prayers.disableAll();
    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 3);
    		heal();
    		if (!presetLoaded) {
				loadPreset();
            }
    		if (presetLoaded && teleport && (!OUTSIDE_VORKATH_AREA.containsPoint(ctx.players.getLocal()) && !VORKATH_AREA.containsPoint(ctx.players.getLocal()))) {
				status = "Teleporting To Vorkath";
				ctx.log("Teleporting To Vorkath");
				ctx.teleporter.teleportPrevious();
				ctx.onCondition(() -> OUTSIDE_VORKATH_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
			} else if (presetLoaded && !teleport) {
				status = "Teleporting To Vorkath";
				ctx.log("Teleporting To Vorkath");
				ctx.teleporter.open();
				ctx.teleporter.teleportStringPath("Bosses", "Vorkath");
				ctx.onCondition(() -> OUTSIDE_VORKATH_AREA.containsPoint(ctx.players.getLocal()), 250, 36);
				teleport = true;
			}
        }
    	
    	if (OUTSIDE_VORKATH_AREA.containsPoint(ctx.players.getLocal())) {
            if (presetLoaded) {
                presetLoaded = false;
            }
            SimpleGameObject wall = (SimpleGameObject) ctx.objects.populate().filter(31990).nearest().next();
        	if (wall != null) {
                status = "Climbing Wall";
                ctx.log("Climbing Wall");
            	wall.interact(SimpleObjectActions.FIRST);
            	ctx.sleep(1000, 2000);
            	ctx.onCondition(() -> VORKATH_AREA.containsPoint(ctx.players.getLocal()), 250, 5);
        	}
    	}
    	if (VORKATH_AREA.containsPoint(ctx.players.getLocal())) {
    		if (!presetLoaded) {
    			presetLoaded = false;
    		}
            alch();
            SimpleNpc vorkie = ctx.npcs.populate().filter(8028).next();
            if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty() || !ctx.groundItems.populate().filter(1752).isEmpty()) {
            	SimpleGroundItem item = ctx.groundItems.nearest().peekNext();
                if (item != null && !ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
		            if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
		            	status = "Opening Coin Bag";
		            	ctx.log("Opening Coin Bag");
		                ctx.inventory.next().interact(539);
		            }
                	if (ctx.inventory.canPickupItem(item)) {
                        status = "Looting " + item.getName();
                        ctx.log("Looting " + item.getName());
                        item.interact();
                        return;	
                	} else if (!ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
                  		 if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
                           	status = "Dropping Vial";
                           	ctx.log("Dropping Vial");
                               ctx.inventory.next().interact(SimpleItemActions.DROP);
                           }
                		if (!ctx.inventory.populate().filterHasAction("Bury").isEmpty()) {
    		                status = "Clearing Space";
    		                ctx.log("Clearing Space, Burying Bone");
    		                ctx.inventory.next().interact(74);
    		                return;
    		    		} else if (!ctx.inventory.populate().filter(385).isEmpty()) {
                        	status = "Clearing Space";
                        	ctx.log("Clearing Space");
                            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
                            return;
                    	} else if (!ctx.inventory.populate().filter(391).isEmpty()) {
                        	status = "Clearing Space";
                        	ctx.log("Clearing Space");
                            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
                            return;
                    	} else if (!ctx.inventory.populate().filterContains("Saradomin brew").isEmpty()) {
                    		status = "Clearing Space";
                        	ctx.log("Clearing Space");
                            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
                            ctx.sleep(600);
                            return;
                    	}
                	}
                	return;
                }
            }
            SimpleItem corruptHeart = ctx.inventory.populate().filter(280).next();
            SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
            SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
            if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
    			status = "Using Overload";
    			ctx.log("Using Overload");
    			overload.interact(74);
    			lastOverload = System.currentTimeMillis();
    			ctx.sleep(600);
    		} else if (focusHeart != null && System.currentTimeMillis() > (lastHeart + 430000)) {
    	    	status = "Using Focused Heart";
    	    	ctx.log("Using Focused Heart");
    	    	focusHeart.interact(74);
    	    	ctx.sleep(600);
    	    } else if (corruptHeart != null && System.currentTimeMillis() > (lastHeart + 430000)) {
    		    	status = "Using Corrupt Heart";
    		    	ctx.log("Using Corrupt Heart");
    		    	corruptHeart.interact(74);
    		    	ctx.sleep(600);
    		}
            final int realLevelRange = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
            final int boostedLevelRange = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
            if (realLevelRange > 90) {
            	if (boostedLevelRange - realLevelRange <= 7) {
            		if (!ctx.inventory.populate().filterContains("Ranging").isEmpty()) {
            			status = "Drinking Ranging Pot";
            			ctx.log("Drinking Ranging Pot");
            			ctx.inventory.next().interact(SimpleItemActions.DRINK);
            			ctx.sleep(650);
            		}
            	}
            } else {
            	if (boostedLevelRange - realLevelRange <= 7) {
            		if (!ctx.inventory.populate().filterContains("Ranging").isEmpty()) {
            			status = "Drinking Ranging Pot";
            			ctx.log("Drinking Ranging Pot");
            			ctx.inventory.next().interact(SimpleItemActions.DRINK);
            			ctx.sleep(650);
            		}
            	}
            }
            SimpleItem strengthPot = ctx.inventory.populate().filterContains("strength").next();
            SimpleItem attackPot = ctx.inventory.populate().filterContains("attack").next();
            final int realLevelStrength = ctx.skills.getRealLevel(SimpleSkills.Skill.STRENGTH);
            final int boostedLevelStrength = ctx.skills.getLevel(SimpleSkills.Skill.STRENGTH);
            final int realLevelAttack = ctx.skills.getRealLevel(SimpleSkills.Skill.ATTACK);
            final int boostedLevelAttack = ctx.skills.getLevel(SimpleSkills.Skill.ATTACK);
            if (boostedLevelStrength - realLevelStrength <= 7) {
            	if (!ctx.inventory.populate().filterContains("strength").isEmpty()) {
            		status = "Drinking Strength Pot";
            		ctx.log("Drinking Strength Pot");
            		strengthPot.interact(SimpleItemActions.DRINK);
            		ctx.sleep(650);
            	}
            }
            if (boostedLevelAttack - realLevelAttack <= 7) {
            	if (!ctx.inventory.populate().filterContains("attack").isEmpty()) {
            		status = "Drinking Attack Pot";
            		ctx.log("Drinking Attack Pot");
            		attackPot.interact(SimpleItemActions.DRINK);
            		ctx.sleep(650);
            	}
            }
            if (!ctx.inventory.populate().filterContains("antifire potion").isEmpty() && System.currentTimeMillis() > (lastPotion + 177000)) {
            	ctx.inventory.next().interact(SimpleItemActions.DRINK);
            	ctx.sleep(600);
            }
            if (ctx.inventory.populate().filterContains("Prayer potion").isEmpty() && ctx.inventory.populate().filterContains("restore").isEmpty() && ctx.prayers.points() < 2 && ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
                ctx.prayers.disableAll();
                ctx.sleep(750);
                ctx.magic.castHomeTeleport();
                status = "Restocking";
                ctx.log("Restocking");
                ctx.sleep(1500);
                return;
            }
            int food = ctx.inventory.populate().filterHasAction("Eat").population(true);
            int brew = ctx.inventory.populate().filterContains("Saradomin brew").population(true);
            if (food <= 0 && brew <= 0 && ctx.groundItems.populate().filterContains(lootNames).isEmpty()) {
            	ctx.prayers.disableAll();
                ctx.sleep(750);
                ctx.magic.castHomeTeleport();
                status = "Restocking";
                ctx.log("Restocking");
                ctx.sleep(1500);
                heal();
                return;
            }
        	if (ctx.combat.health() != 10) {
                handleEatingFood();
            }
        	if (ctx.prayers.prayerPercent() <= 20) {
        		handleDrinkingPrayer();
            }
            SimpleItem diamondBolts = ctx.inventory.populate().filter(9243).next();
            SimpleItem rubyBolts = ctx.inventory.populate().filter(9242).next();
            SimpleItem berserkerRingi = ctx.inventory.populate().filter(11773).next();
            SimpleItem berserkerRing = ctx.inventory.populate().filter(6737).next();
            SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
            SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
            if (vorkie != null) {
            	useSpecialDWH();
            	if (prayerType == -2 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR) && vorkie != null && !vorkie.isDead()) {
                		status = "Turning On Prayers";
                		ctx.log("Turning On Prayers");
                		ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
                	}
                	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && !vorkie.isDead() && vorkie.getAnimation() != 80) {
                		status = "Turning On Prayers";
                		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                	}
                } else if (prayerType == -1 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE) && vorkie != null && !vorkie.isDead()) {
                    	status = "Turning On Prayers";
                    	ctx.log("Turning On Prayers");
                    	ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
                    }
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && !vorkie.isDead() && vorkie.getAnimation() != 80) {
                    	status = "Turning On Prayers";
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                    }
                } else if (prayerType == -3 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY) && vorkie != null && !vorkie.isDead()) {
                    	status = "Turning On Prayers";
                    	ctx.log("Turning On Prayers");
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
                    }
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && !vorkie.isDead() && vorkie.getAnimation() != 80) {
                    	status = "Turning On Prayers";
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                    }
                }
            	if (vorkie.getHealth() <= 165) {
            		if (diamondBolts != null) {
            			status = "Switching Bolts";
            			ctx.log("Switching Bolts");
            			diamondBolts.interact(SimpleItemActions.FIRST);
            		}
            	} else {
            		if (rubyBolts != null) {
            			status = "Switching Bolts";
            			ctx.log("Switching Bolts");
            			rubyBolts.interact(SimpleItemActions.FIRST);
            		}
            	}
    			if (vorkie.isDead()) {
    				ctx.prayers.disableAll();
    			}
        		SimpleItem row = ctx.inventory.populate().filter(2572).next();
        		SimpleItem rowi = ctx.inventory.populate().filter(12785).next();
        		if (row != null) {
        			if (vorkie != null && (vorkie.getHealth() <= 100 || vorkie.isDead())) {
        				row.interact(SimpleItemActions.WEAR);
        			}
        		} else if (rowi != null) {
        			if (vorkie != null && (vorkie.getHealth() <= 100 || vorkie.isDead())) {
        				rowi.interact(SimpleItemActions.WEAR);
        			}
        		}
            }
            if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
            	status = "Dropping Vial";
            	ctx.log("Dropping Vial");
                ctx.inventory.next().interact(SimpleItemActions.DROP);
            }
            if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
            	status = "Opening Coin Bag";
            	ctx.log("Opening Coin Bag");
                ctx.inventory.next().interact(539);
            }
            SimpleNpc sleepingVork = ctx.npcs.populate().filter(8026).nearest().next();
            if (sleepingVork != null && !ctx.players.getLocal().isAnimating()) {
            	if (food <= 0 && brew <= 0) {
            		ctx.prayers.disableAll();
                    ctx.sleep(750);
                    ctx.magic.castHomeTeleport();
                	status = "Restocking";
                	ctx.log("Restocking");
                	return;
                }
            	if (prayerType == -2 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
                		status = "Turning On Prayers";
                		ctx.log("Turning On Prayers");
                		ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
                	}
                	if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && vorkie.getAnimation() != 80) {
                		status = "Turning On Prayers";
                		ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                	}
                } else if (prayerType == -1 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
                    	status = "Turning On Prayers";
                    	ctx.log("Turning On Prayers");
                    	ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
                    }
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && vorkie.getAnimation() != 80) {
                    	status = "Turning On Prayers";
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                    }
                } else if (prayerType == -3 && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
                    	status = "Turning On Prayers";
                    	ctx.log("Turning On Prayers");
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
                    }
                    if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC) && vorkie != null && vorkie.getAnimation() != 80) {
                    	status = "Turning On Prayers";
                    	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
                    }
                }
            	ctx.sleep(500);
        		if (rubyBolts != null) {
        			status = "Switching Bolts";
        			ctx.log("Switching Bolts");
        			rubyBolts.interact(SimpleItemActions.FIRST);
        		}
        		if (berserkerRingi != null) {
        			status = "Switching Ring";
        			ctx.log("Switching Ring");
        			berserkerRingi.interact(SimpleItemActions.FIRST);
        		} else if (berserkerRing != null) {
        			status = "Switching Ring";
        			ctx.log("Switching Ring");
        			berserkerRing.interact(SimpleItemActions.FIRST);
        		} else if (archerRing != null) {
        			status = "Switching Ring";
        			ctx.log("Switching Ring");
        			archerRing.interact(SimpleItemActions.FIRST);
        		} else if (archerRingi != null) {
        			status = "Switching Ring";
        			ctx.log("Switching Ring");
        			archerRingi.interact(SimpleItemActions.FIRST);
        		}
            	sleepingVork.interact(SimpleNpcActions.INTERACT);
            	status = "Waking Up Vorkath";
            	if (!ctx.pathing.inMotion()) {
            		ctx.log("Waking Up Vorkath");
            	}
            	ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
            }
            specialAttack();
            if (!ctx.npcs.populate().filter(8027).isEmpty() || !ctx.npcs.populate().filter(8028).isEmpty()) {
                if (ctx.players.getLocal().getInteracting() == null) {
                    SimpleNpc vork = ctx.npcs.nearest().next();
                    if (vork != null && ctx.players.getLocal().getInteracting() == null) {
                        status = "Attacking Vorkath";
                        ctx.log("Attacking Vorkath");
                        vork.interact("Attack");
                        ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 5);
                    }
                }
            }
        }
    }

    @Override
    public void onTerminate() {
        if (gui != null) {
            gui.onCloseGUI();
        }
    }
    
	private void heal() {
		SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}
		}
	}
	
    public static void specialAttack() {
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.health() <= ctx.combat.maxHealth() - 15 && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().getInteracting() != null) {
			ctx.combat.toggleSpecialAttack(true);
			status = "Using Special Attack";
			ctx.log("Using Special Attack");
			ctx.onCondition(() -> ctx.combat.specialAttack(), 250, 6);
		}
		if (!ctx.equipment.populate().filter(12788).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().getInteracting() != null) {
			ctx.combat.toggleSpecialAttack(true);
			status = "Using Special Attack";
			ctx.log("Using Special Attack");
			ctx.onCondition(() -> ctx.combat.specialAttack(), 250, 6);
		}
    }
    
    private void alch() {
    	int naturerune = ctx.inventory.populate().filter(561).population(true);
		int firerune = ctx.inventory.populate().filter(554).population(true);
    	if (!ctx.inventory.populate().filterContains("Rune kiteshield").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Rune kiteshield";
        	ctx.log("Alching Rune kiteshield");
        	SimpleItem alch = ctx.inventory.populate().filter(1201).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Rune longsword").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Rune longsword";
        	ctx.log("Alching Rune longsword");
        	SimpleItem alch = ctx.inventory.populate().filter(1303).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Dragon plateskirt").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Dragon plateskirt";
        	ctx.log("Alching Dragon plateskirt");
        	SimpleItem alch = ctx.inventory.populate().filter(4585).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Dragon longsword").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Dragon longsword";
        	ctx.log("Alching Dragon longsword");
        	SimpleItem alch = ctx.inventory.populate().filter(1305).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Dragon battleaxe").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Dragon battleaxe";
        	ctx.log("Alching Dragon battleaxe");
        	SimpleItem alch = ctx.inventory.populate().filter(1377).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
    	if (!ctx.inventory.populate().filterContains("Dragon platelegs").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	status = "Alching Dragon platelegs";
        	ctx.log("Alching Dragon platelegs");
        	SimpleItem alch = ctx.inventory.populate().filter(4087).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }	
    }
    
    public static void useSpecialDWH() {
    	SimpleNpc vorkie = ctx.npcs.populate().filter(8028).nearest().next();
        if (vorkie != null && vorkie.getHealth() >= 250) {
        	if (ctx.combat.getSpecialAttackPercentage() >= 50) {
            	if (ctx.inventory.populate().filter(13576).size() > 0) {
                    ctx.inventory.populate().filter(13576).next().interact(454);
                    status = "Switching To DWH";
    				ctx.log("Switching To DWH");
                    ctx.onCondition(() -> ctx.equipment.populate().filter(13576).size() > 0, 250, 8);
                }
                if (ctx.equipment.populate().filter(13576).size() > 0) {
                    ctx.combat.toggleSpecialAttack(true);
                    status = "Using Special";
                    ctx.log("Using Special");
                    vorkie.interact("Attack");
                    ctx.onCondition(() -> ctx.combat.getSpecialAttackPercentage() < 50, 250, 10);
                }
            }
            
            if (ctx.combat.getSpecialAttackPercentage() < 50) {
                if (ctx.inventory.populate().filter(22978).size() > 0 ) {
                	ctx.inventory.populate().filter(22978).next().interact(454);
                	status = "Switching Back";
    				ctx.log("Switching Back");
                	ctx.onCondition(() -> ctx.equipment.populate().filter(22978).size() > 0, 250, 8);
                }
                if (ctx.inventory.populate().filter(22322).size() > 0 ) {
                	ctx.inventory.populate().filter(22322).next().interact(454);
                	ctx.onCondition(() -> ctx.equipment.populate().filter(22322).size() > 0, 250, 8);
                	vorkie.interact("Attack");
                }
            }
        }
    }
    
    private void handleEatingFood() {
    	SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
    	SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
    	if (food != null && ctx.combat.health() <= (ctx.combat.maxHealth() - 35) && ctx.combat.health() != 10) {
			status = "Eating " + food.getName().toString();
			ctx.log("Eating " + food.getName().toString());
			food.interact(74);
		} else if (brew != null && ctx.combat.health() <= (ctx.combat.maxHealth() - 35) && ctx.combat.health() != 10) {
			status = "Drinking " + brew.getName().toString();
			ctx.log("Drinking " + brew.getName().toString());
			brew.interact(74);
		}
    }
    
    private void handleDrinkingPrayer() {
    	if (!ctx.inventory.populate().filterContains("Super restore").isEmpty()) {
			status = "Drinking Super Restore";
			ctx.log("Drinking Super Restore");
			ctx.inventory.next().interact(74);
			ctx.sleep(600);
		} else if (!ctx.inventory.populate().filterContains("Prayer potion").isEmpty()) {
			status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(74);
			ctx.sleep(600);
		}
    }

    @SuppressWarnings("deprecation")
	private void loadPreset() {
    	if (ctx.widgets.getBackDialogId() == 306) {
			ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
		}
    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
    	final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
    	
		if (!presetLoaded && HOME_AREA.containsPoint(ctx.players.getLocal())) {
			if (!ctx.players.getLocal().getLocation().equals(homeBank)) {
				status = "Walking Closer To Bank";
				ctx.pathing.step(homeBank);
				return;
			} else {
				status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			}
			if (ctx.inventory.populate().filter(22978).size() > 0 ) {
				ctx.inventory.populate().filter(22978).next().interact(454);
                ctx.sleepCondition(() -> ctx.equipment.populate().filter(22978).size() > 0, 2000);
            }
		}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
	
	@Override
    public void onPaint(Graphics2D g) {
		if (staff) {
			g.drawImage(staffImage, 4, 4, null);
		}
	    g.setFont(new Font("Arial", 1, 10));
	    Font currentFont = g.getFont();
	    Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.2F);
	    g.setFont(newFont);
	    g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 286);
	    g.drawString("Status: " + status, 10, 302);
	    g.drawString("Kills / Per Hour: " + vorkathKills + " / " + ctx.paint.valuePerHour((int) vorkathKills, startTime), 10, 318);
    }
	
    public void onChatMessage(ChatMessageEvent event) {
        if (event.getMessageType() == 0 && event.getSender().equals("")) {
            if (event.getMessage().contains("You feel a drain on your memory")) {
                presetLoaded = true;
            } else if (event.getMessage().contains("You need to have used a preset recently")) {
				presetLoaded = false;
			} else if (event.getMessage().contains("has been temporarily raised")) {
		    	lastHeart = System.currentTimeMillis();
			} else if (event.getMessage().contains("You must wait 7 minutes")) {
		    	lastHeart = System.currentTimeMillis() - 30000;
			} else if (event.getMessage().contains("You now have complete resistance against dragon fire")) {
				lastPotion = System.currentTimeMillis();
			} else if (event.getMessage().contains("You haven't teleported anywhere recently")) {
				teleport = false;
			} else if (event.getMessage().contains("You teleport to your most recent location")) {
				teleport = true;
			} else if (event.getMessage().contains("Your Vorkath kill count is:")) {
            	vorkathKills = vorkathKills + 1;
            }
        }
        
        if (event.getMessageType() == 7) {
        	Toolkit.getDefaultToolkit().beep();
        }
    }
    
    public void setupInfo(int prayerType) {
        this.prayerType = prayerType;
    }
    
	@Override
	public int loopDuration() {
		return 250;
	}
}