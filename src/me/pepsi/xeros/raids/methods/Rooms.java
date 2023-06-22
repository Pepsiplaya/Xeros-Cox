package me.pepsi.xeros.raids.methods;

import java.util.Random;

import me.pepsi.xeros.raids.Raids;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import simple.api.wrappers.SimplePlayer;
import simple.api.wrappers.SimpleWidget;

public class Rooms {
	final static ClientContext ctx = ClientContext.instance();
	private final static Random random = new Random();
	public static int npcId;
	public static boolean exit = false;
	public static String invite;
	public final static WorldPoint muttadileStart = new WorldPoint(3311, 5309, 1);
	public final static WorldPoint muttadileWalk = new WorldPoint(3310, 5333, 1);
	public final static WorldPoint vanguardStart = new WorldPoint(3312, 5311, 0);
	public final static WorldPoint iceDemonStart = new WorldPoint(3313, 5346, 0);
	public final static WorldPoint iceDemonStart2 = new WorldPoint(3312, 5344, 0);
	public final static WorldPoint tektonStart = new WorldPoint(3310, 5277, 1);
	public final static WorldPoint shamanStart = new WorldPoint(3308, 5208, 0);
	public final static WorldPoint magesStart = new WorldPoint(3312, 5217, 1);
	public final static WorldPoint vasaStart = new WorldPoint(3312, 5279, 0);
	public final static WorldPoint magesWalk = new WorldPoint(3306, 5265, 1);
	public final static WorldPoint magesWalk3 = new WorldPoint(3315, 5261, 1);
	public final static WorldPoint magesWalk4 = new WorldPoint(3307, 5257, 1);
	public final static WorldPoint magesWalk2 = new WorldPoint(3313, 5259, 1);
	public final static WorldPoint olmStart = new WorldPoint(3232, 5730, 0);
	public final static WorldPoint roomOneWalk = new WorldPoint(3307, 5202, 0);
	public static final WorldArea WAITING_AREA = new WorldArea(new WorldPoint(1220, 3552), new WorldPoint(1241, 3575));
	public static final WorldArea ROOM_ONE = new WorldArea(new WorldPoint(3298, 5189), new WorldPoint(3315, 5205));
	public static final WorldArea MUTTADILE = new WorldArea(new WorldPoint(3300, 5308, 1),
			new WorldPoint(3315, 5337, 1));
	public static final WorldArea VASA = new WorldArea(new WorldPoint(3299, 5279, 0), new WorldPoint(3323, 5308, 0));
	public static final WorldArea VANGUARDS = new WorldArea(new WorldPoint(3306, 5310), new WorldPoint(3320, 5342));
	public static final WorldArea ICE_DEMON = new WorldArea(new WorldPoint(3303, 5342, 0),
			new WorldPoint(3319, 5371, 0));
	public static final WorldArea ICE_DEMONSTART = new WorldArea(new WorldPoint(3311, 5343, 0),
			new WorldPoint(3320, 5360, 0));
	public static final WorldArea NOTHING = new WorldArea(new WorldPoint(3300, 5373), new WorldPoint(3325, 5399));
	public static final WorldArea MAGES = new WorldArea(new WorldPoint(3297, 5215, 1), new WorldPoint(3321, 5274, 1));
	public static final WorldArea MAGESSTART = new WorldArea(new WorldPoint(3297, 5200, 1),
			new WorldPoint(3340, 5248, 1));
	public static final WorldArea MAGESBUG = new WorldArea(new WorldPoint(3314, 5254, 1),
			new WorldPoint(3319, 5264, 1));
	public static final WorldArea TEKTON = new WorldArea(new WorldPoint(3307, 5277, 1), new WorldPoint(3320, 5306, 1));
	public static final WorldArea SHAMAN = new WorldArea(new WorldPoint(3301, 5208), new WorldPoint(3322, 5276));
	public static final WorldArea SHAMANSTART = new WorldArea(new WorldPoint(3302, 5208, 0),
			new WorldPoint(3315, 5259, 0));
	public static final WorldArea ABOVE_OLM = new WorldArea(new WorldPoint(3274, 5159), new WorldPoint(3286, 5174));
	public static final WorldArea OUTSIDE_OLM = new WorldArea(new WorldPoint(3228, 5718), new WorldPoint(3238, 5729));
	public static final WorldArea OLM = new WorldArea(new WorldPoint(3226, 5730), new WorldPoint(3241, 5755));
	public static final WorldArea HOME_AREA = new WorldArea(new WorldPoint(3072, 3521, 0),
			new WorldPoint(3072, 3464, 0), new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));

	private static long lastOverload = -1;
	
	public static void Others() {
		if (ROOM_ONE.containsPoint(ctx.players.getLocal())) {
			if (Raids.presetLoaded) {
				Raids.presetLoaded = false;
			}
			ctx.sleep(300, 800);
			nextRoom();
			ctx.sleep(300, 800);
			rangePotion();
			ctx.sleep(300, 800);
			Prayers.protectMelee();
			handleEatingFood();
			nextRoom();
		} else if (NOTHING.containsPoint(ctx.players.getLocal())) {
			nextRoom();
		} else if (ABOVE_OLM.containsPoint(ctx.players.getLocal())) {
			Prayers.protectRange();
			nextRoom();
		}
	}

	public static void Muttatile() {
		if (MUTTADILE.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			}
			npcId = 7563;
			SimpleNpc fm = npcs().nearest().next();
			SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
			if (ctx.players.getLocal().getLocation().equals(muttadileStart)) {
				Raids.loot = false;
				if (!Raids.dead) {
					Prayers.protectMelee();
				}
				ctx.pathing.walkToTile(muttadileWalk.derrive(between(0, 4), between(-2, 2)));
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
				ctx.onCondition(() -> npc != null, 600, 4);
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			Prayers.protectMelee();
			if (npc != null) {
				if (ctx.players.getLocal().distanceTo(muttadileWalk) >= 6 && ctx.equipment.populate().filter(22325).next() == null) {
					npc.interact(SimpleNpcActions.ATTACK);
					if (!ctx.equipment.populate().filter(20997).isEmpty()) {
						ctx.sleep(450, 750);
						ctx.pathing.step(muttadileWalk.derrive(between(-2, 2), between(0, 1)));
						ctx.sleep(1600, 2050);
					} else {
						ctx.sleep(350, 600);
						ctx.pathing.step(muttadileWalk.derrive(between(-2, 2), between(0, 1)));
						ctx.sleep(650, 900);
					}
				}
				npc.interact(SimpleNpcActions.ATTACK);
				Raids.status = "Fighting " + npc.getName();
				if (ctx.players.getLocal().getInteracting() == null) {
					ctx.log("Fighting " + npc.getName());
				}
			} else {
				if (Raids.pickupFood && !ctx.inventory.inventoryFull()) {
					if (!Raids.loot) {
						ctx.sleep(2000);
						Raids.loot = true;
						return;
					}
				}
				if (Raids.pickupFood) {
					Looting.loot();
					return;
				}
				if (!ctx.pathing.inMotion()) {
					nextRoom();
					ctx.onCondition(() -> !MUTTADILE.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			}
		}
	}

	public static void Vanguard() {
		if (VANGUARDS.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			}
			final WorldPoint vanguardsWalk = new WorldPoint(3311, 5322, 0);
			if (ctx.players.getLocal().getLocation().equals(vanguardStart)) {
				Raids.loot = false;
				ctx.pathing.step(vanguardsWalk.derrive(between(0, 6), between(-1, 7)));
				ctx.sleep(1000);
				Prayers.protectMelee();
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			SimpleNpc mv = mageVanguard().filter((n) -> n.getInteracting() != null && n.inCombat() && n.getHealth() > 0).next();
			SimpleNpc mvnpc = mv != null ? mv : mageVanguard().nearest().next();
			SimpleNpc mv2 = meleeVanguard().filter((n) -> n.getInteracting() != null && n.inCombat() && n.getHealth() > 0).next();
			SimpleNpc mv2npc = mv2 != null ? mv2 : meleeVanguard().nearest().next();
			SimpleNpc mv3 = meleeVanguard2().filter((n) -> n.getInteracting() != null && n.inCombat() && n.getHealth() > 0).next();
			SimpleNpc mv3npc = mv3 != null ? mv3 : meleeVanguard2().nearest().next();
			if (!ctx.npcs.populate().filter(7527).isEmpty() || !ctx.npcs.populate().filter(7528).isEmpty()
					|| !ctx.npcs.populate().filter(7529).isEmpty()) {
				Prayers.protectMelee();
				if (mvnpc != null) {
					Raids.status = "Fighting " + mvnpc.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mvnpc.getName());
					}
					mvnpc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() == null, 250, 8);
				} else if (mv2npc != null) {
					Raids.status = "Fighting " + mv2npc.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mv2npc.getName());
					}
					mv2npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() == null, 250, 8);
				} else if (mv3npc != null) {
					Raids.status = "Fighting " + mv3npc.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mv3npc.getName());
					}
					mv3npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() == null, 250, 8);
				} else {
					if (Raids.pickupFood) {
						Looting.loot();
						return;
					}
					nextRoom();
					ctx.onCondition(() -> !VANGUARDS.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			} else {
				if (Raids.pickupFood) {
					Looting.loot();
					return;
				}
				nextRoom();
				ctx.onCondition(() -> !VANGUARDS.containsPoint(ctx.players.getLocal()), 250, 5);
			}
		}
	}

	public static void IceDemon() {
		if (ICE_DEMON.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			}
			npcId = 7585;
			final WorldPoint iceDemonWalk = new WorldPoint(3311, 5367, 0);
			if (ICE_DEMONSTART.containsPoint(ctx.players.getLocal())) {
				Raids.loot = false;
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
				ctx.pathing.walkToTile(iceDemonWalk.derrive(between(0, 3), between(-3, 3)));
				return;
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			if (ctx.players.getLocal().getInteracting() == null) {
				Prayers.protectMelee();
				SimpleNpc fm = npcs().nearest().next();
				SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
				if (npc != null) {
					Raids.status = "Fighting " + npc.getName();
					ctx.log("Fighting " + npc.getName());
					npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
				} else {
					nextRoom();
					ctx.onCondition(() -> !ICE_DEMON.containsPoint(ctx.players.getLocal()), 250, 2);
				}
			}
		}
	}

	public static void Tetkon() {
		if (TEKTON.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			}
			npcId = 7544;
			final WorldPoint tektonWalk = new WorldPoint(3311, 5287, 1);
			if (Raids.tektonWalk == true && ctx.players.getLocal().getInteracting() == null && !Raids.dead) {
				ctx.pathing.walkToTile(tektonWalk.derrive(between(-1, 3), between(0, 2)));
				ctx.sleep(2000);
				Raids.tektonWalk = false;
			}
			if (ctx.players.getLocal().getLocation().equals(tektonStart)) {
				Raids.loot = false;
				if (!Raids.dead) {
					Prayers.protectMelee();
					ctx.pathing.walkToTile(tektonWalk.derrive(between(0, 3), between(0, 2)));
					Raids.status = "Pathing";
					if (!ctx.pathing.inMotion()) {
						ctx.log("Pathing");
					}
					ctx.sleep(3000, 3600);
				}
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			if (ctx.players.getLocal().getInteracting() == null) {
				Prayers.protectMelee();
				SimpleNpc fm = npcs().nearest().next();
				SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
				if (npc != null) {
					Raids.status = "Fighting " + npc.getName();
					ctx.log("Fighting " + npc.getName());
					npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 4);
				} else {
					nextRoom();
					ctx.onCondition(() -> !TEKTON.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			}
		}
	}

	public static void Shaman() {
		if (SHAMAN.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			} else {
				Prayers.protectMelee();
			}
			npcId = 7573;
			WorldPoint[] PATH_TO_SHAMANS = { new WorldPoint(3311, 5213, 0), new WorldPoint(3312, 5222, 0),
					new WorldPoint(3309, 5227, 0), new WorldPoint(3304, 5232, 0), new WorldPoint(3308, 5238, 0),
					new WorldPoint(3311, 5243, 0), new WorldPoint(3313, 5251, 0), new WorldPoint(3312, 5260, 0),
					new WorldPoint(3315, 5267, 0).derrive(between(-2, 2), between(0, 5)) };
			if (SHAMANSTART.containsPoint(ctx.players.getLocal())) {
				Raids.loot = false;
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
				ctx.pathing.walkPath(PATH_TO_SHAMANS);
				return;
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			if (ctx.players.getLocal().getInteracting() == null) {
				Prayers.protectMelee();
				SimpleNpc fm = npcs().nearest().next();
				SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
				if (npc != null) {
					Raids.status = "Fighting " + npc.getName();
					ctx.log("Fighting " + npc.getName());
					npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 3);
				} else {
					if (Raids.pickupFood && ctx.players.getLocal().getInteracting() == null) {
						Looting.loot();
						return;
					}
					nextRoom();
					ctx.onCondition(() -> !SHAMAN.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			}
		}
	}

	public static void Vasa() {
		if (VASA.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				nextRoom();
				return;
			}
			npcId = 7566;
			final WorldPoint vasaWalk = new WorldPoint(3313, 5288, 0);
			if (ctx.players.getLocal().getLocation().equals(vasaStart)) {
				Raids.loot = false;
				if (!Raids.dead) {
					Prayers.protectMagic();
				}
				ctx.pathing.walkToTile(vasaWalk.derrive(between(0, 0), between(-3, 3)));
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
				ctx.sleep(1500);
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			if (ctx.players.getLocal().getInteracting() == null) {
				Prayers.protectMagic();
				SimpleNpc fm = npcs().nearest().next();
				SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
				if (npc != null) {
					Raids.status = "Fighting " + npc.getName();
					ctx.log("Fighting " + npc.getName());
					npc.interact(SimpleNpcActions.ATTACK);
					ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 12);
				} else {
					if (Raids.pickupFood && !ctx.inventory.inventoryFull()) {
						if (!Raids.loot) {
							ctx.sleep(2000);
							Raids.loot = true;
							return;
						}
					}
					if (Raids.pickupFood) {
						Looting.loot();
						return;
					}
					nextRoom();
					ctx.onCondition(() -> !VASA.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			}
		}
	}

	public static void Mages() {
		if (MAGES.containsPoint(ctx.players.getLocal())) {
			WorldPoint[] PATH_TO_MAGES = { new WorldPoint(3313, 5225, 1), new WorldPoint(3315, 5233, 1),
					new WorldPoint(3313, 5240, 1), new WorldPoint(3312, 5249, 1), new WorldPoint(3315, 5253, 1) };
			if (MAGESSTART.containsPoint(ctx.players.getLocal())) {
				Raids.loot = false;
				if (!Raids.dead) {
					Prayers.protectMagic();
				}
				Raids.status = "Pathing";
				if (!ctx.pathing.inMotion()) {
					ctx.log("Pathing");
				}
				ctx.pathing.walkPath(PATH_TO_MAGES);
				npcId = 7606;
				return;
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			SimpleNpc mv = mage1().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mage1 = mv != null ? mv : mage1().nearest().next();
			SimpleNpc mv2 = mage2().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mage2 = mv2 != null ? mv2 : mage2().nearest().next();
			SimpleNpc mv3 = mage3().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mage3 = mv3 != null ? mv3 : mage3().nearest().next();
			if (!ctx.npcs.populate().filter(7605).isEmpty() || !ctx.npcs.populate().filter(7604).isEmpty()
					|| !ctx.npcs.populate().filter(7606).isEmpty()) {
				Prayers.protectMagic();
				if (mage2 != null) {
					if (ctx.players.getLocal().distanceTo(magesWalk3) >= 2 && ctx.equipment.populate().filter(22325).next() == null) {
						mage2.interact(SimpleNpcActions.ATTACK);
						if (!ctx.equipment.populate().filter(20997).isEmpty()) {
							ctx.sleep(450, 750);
							ctx.pathing.step(magesWalk3.derrive(between(-1, 1), between(0, 1)));
							ctx.sleep(1600, 2050);
						} else {
							ctx.sleep(350, 600);
							ctx.pathing.step(magesWalk3.derrive(between(-1, 1), between(0, 1)));
							ctx.sleep(650, 900);
						}
					}
					mage2.interact(SimpleNpcActions.ATTACK);
					Raids.status = "Fighting " + mage2.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mage2.getName());
					}
				} else if (mage1 != null) {
					if (Raids.pickupFood) {
						mage1.interact(SimpleNpcActions.ATTACK);
					} else {
						if (ctx.players.getLocal().distanceTo(magesWalk4) >= 3 && ctx.equipment.populate().filter(22325).next() == null) {
							mage1.interact(SimpleNpcActions.ATTACK);
							if (!ctx.equipment.populate().filter(20997).isEmpty()) {
								ctx.sleep(450, 750);
								ctx.pathing.step(magesWalk4.derrive(between(-1, 2), between(1, -2)));
								ctx.sleep(1600, 2050);
							} else {
								ctx.sleep(350, 600);
								ctx.pathing.step(magesWalk4.derrive(between(-1, 2), between(1, -2)));
								ctx.sleep(650, 900);
							}
						}
					}
					mage1.interact(SimpleNpcActions.ATTACK);
					Raids.status = "Fighting " + mage1.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mage1.getName());
					}

					if (ctx.players.getLocal().getInteracting() == null) {
						mage1.interact(SimpleNpcActions.ATTACK);
						ctx.log("Fighting " + mage1.getName());
						Raids.status = "Fighting " + mage1.getName();
					}
				} else if (mage3 != null) {
					if (ctx.players.getLocal().distanceTo(magesWalk) >= 6 && ctx.equipment.populate().filter(22325).next() == null) {
						mage3.interact(SimpleNpcActions.ATTACK);
						if (!ctx.equipment.populate().filter(20997).isEmpty()) {
							ctx.sleep(450, 750);
							ctx.pathing.step(magesWalk.derrive(between(-2, 2), between(-2, 1)));
							ctx.sleep(1600, 2050);
						} else {
							ctx.sleep(350, 600);
							ctx.pathing.step(magesWalk.derrive(between(-2, 2), between(-2, 1)));
							ctx.sleep(650, 900);
						}
					}
					mage3.interact(SimpleNpcActions.ATTACK);
					Raids.status = "Fighting " + mage3.getName();
					if (ctx.players.getLocal().getInteracting() == null) {
						ctx.log("Fighting " + mage3.getName());
					}
				} else {
					nextRoom();
					ctx.onCondition(() -> !MAGES.containsPoint(ctx.players.getLocal()), 250, 5);
				}
				if (MAGESBUG.containsPoint(ctx.players.getLocal()) && mage2 == null && mage1 == null) {
					ctx.pathing.walkToTile(magesWalk);
				}
				if (mage3 != null && mage3.isDead()) {
					nextRoom();
					ctx.onCondition(() -> !MAGES.containsPoint(ctx.players.getLocal()), 250, 5);
				}
			} else {
				nextRoom();
				ctx.onCondition(() -> !MAGES.containsPoint(ctx.players.getLocal()), 250, 16);
			}
		}
	}

	public static void OutsideOlm() {
		if (OUTSIDE_OLM.containsPoint(ctx.players.getLocal())) {
			SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Mystical barrier").nearest()
					.next();
			if (barrier != null) {
				if (!ctx.inventory.populate().filterContains("antifire potion").isEmpty()
						&& System.currentTimeMillis() > (Raids.lastPotion + 177000)) {
					ctx.inventory.next().interact(SimpleItemActions.DRINK);
					ctx.sleep(600);
				}
				barrier.interact(SimpleObjectActions.OPEN);
				handleEatingFood();
				handleDrinkingPrayer();
				rangePotion();
				dropVial();
				Prayers.protectRange();
				if (barrier != null) {
					barrier.interact(SimpleObjectActions.OPEN);
				}
			}
		}
	}

	public static void Olm() {
		if (OLM.containsPoint(ctx.players.getLocal())) {
			if (Raids.dead) {
				Raids.dead = false;
			}
			SimpleItem twistedbow = ctx.inventory.populate().filter(20997).next();
			SimpleItem sangstaff = ctx.inventory.populate().filter(22323).next();
			SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
			SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
			SimpleItem dhcb = ctx.inventory.populate().filter(21012).next();
			SimpleItem antidragonward = ctx.inventory.populate().filter(22002).next();
			SimpleItem antidragonfire = ctx.inventory.populate().filter(1540).next();
			SimpleItem bolts = ctx.inventory.populate().filter(9242).next();
			SimpleItem mystichat = ctx.inventory.populate().filter(4089).next();
			SimpleItem mysticbody = ctx.inventory.populate().filter(4091).next();
			SimpleItem mysticlegs = ctx.inventory.populate().filter(4093).next();
			SimpleItem lmystichat = ctx.inventory.populate().filter(4109).next();
			SimpleItem lmysticbody = ctx.inventory.populate().filter(4111).next();
			SimpleItem lmysticlegs = ctx.inventory.populate().filter(4113).next();
			SimpleItem dmystichat = ctx.inventory.populate().filter(4099).next();
			SimpleItem dmysticbody = ctx.inventory.populate().filter(4101).next();
			SimpleItem dmysticlegs = ctx.inventory.populate().filter(4103).next();
			SimpleItem dhidebody = ctx.inventory.populate().filter(2503).next();
			SimpleItem dhidelegs = ctx.inventory.populate().filter(2497).next();
			SimpleItem dhidevamb = ctx.inventory.populate().filter(2491).next();
			SimpleItem avas = ctx.inventory.populate().filter(10499).next();
			SimpleItem dragoncbow = ctx.inventory.populate().filter(21012).next();
			SimpleItem armadylcbow = ctx.inventory.populate().filter(11785).next();
			SimpleItem zarytecbow = ctx.inventory.populate().filter(26405).next();
			SimpleItem rcbow = ctx.inventory.populate().filter(9185).next();
			SimpleItem voidmage = ctx.inventory.populate().filter(11663).next();
			SimpleItem voidrange = ctx.inventory.populate().filter(11664).next();
			SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
			SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
			SimpleItem voidrangeor = ctx.inventory.populate().filter(26475).next();
			SimpleItem voidmageor = ctx.inventory.populate().filter(26473).next();
			SimpleItem blowpipe = ctx.inventory.populate().filter(12926).next();
			SimpleItem magebook = ctx.inventory.populate().filter(6889).next();
			SimpleItem msbi = ctx.inventory.populate().filter(12788).next();
			SimpleItem msb = ctx.inventory.populate().filter(861).next();
			SimpleItem dragonscimitar = ctx.inventory.populate().filter(4587).next();
			SimpleItem runeboots = ctx.inventory.populate().filter(4131).next();
			SimpleItem torture = ctx.inventory.populate().filter(19553).next();
			SimpleItem anguish = ctx.inventory.populate().filter(19547).next();
			SimpleItem anguishor = ctx.inventory.populate().filter(22249).next();
			SimpleItem tortureor = ctx.inventory.populate().filter(20366 ).next();
			SimpleItem occult = ctx.inventory.populate().filter(12002).next();
			SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
			SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
			SimpleItem zarytevambs = ctx.inventory.populate().filter(26235).next();
			SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
			SimpleItem primordials = ctx.inventory.populate().filter(13239).next();
			SimpleItem pegasian = ctx.inventory.populate().filter(13237).next();
			SimpleItem eternals = ctx.inventory.populate().filter(13235).next();
			SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
			SimpleItem whip = ctx.inventory.populate().filter(4151).next();
			SimpleItem assemblermax = ctx.inventory.populate().filter(21898).next();
			SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
			SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
			SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
			SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
			SimpleItem guthixcape = ctx.inventory.populate().filter(2413).next();
			SimpleItem saracape = ctx.inventory.populate().filter(2412).next();
			SimpleItem zammycape = ctx.inventory.populate().filter(2414).next();
			SimpleItem imbuedguthixcape = ctx.inventory.populate().filter(21793).next();
			SimpleItem imbuedsaracape = ctx.inventory.populate().filter(21791).next();
			SimpleItem imbuedzammycape = ctx.inventory.populate().filter(21795).next();
			SimpleItem imbuedguthixmaxcape = ctx.inventory.populate().filter(21784).next();
			SimpleItem imbuedsaramaxcape = ctx.inventory.populate().filter(21776).next();
			SimpleItem imbuedzammymaxcape = ctx.inventory.populate().filter(21780).next();
			SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
			SimpleItem dragonfireshield = ctx.inventory.populate().filter(11283).next();
			SimpleItem dhl = ctx.inventory.populate().filter(22978).next();
			SimpleItem bos = ctx.inventory.populate().filter(23995).next();
			SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
			SimpleItem bookofdarkness = ctx.inventory.populate().filter(12612).next();
			SimpleItem totswamp = ctx.inventory.populate().filter(12899).next();
			SimpleItem totseas = ctx.inventory.populate().filter(11907).next();
			SimpleItem assembler = ctx.inventory.populate().filter(22109).next();
			SimpleItem snakeskin = ctx.inventory.populate().filter(6328).next();
			SimpleItem archerringi = ctx.inventory.populate().filter(11771).next();
			SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
			SimpleItem seersringi = ctx.inventory.populate().filter(11770).next();
			SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
			SimpleItem cbowfa = ctx.inventory.populate().filter(25867).next();
			SimpleItem bowfa = ctx.inventory.populate().filter(25865).next();
			SimpleGameObject olmExit = (SimpleGameObject) ctx.objects.populate().filter(29778).nearest().next();
			final WorldPoint olmWalk = new WorldPoint(3228, 5743, 0);
			final WorldPoint olmWalk2 = new WorldPoint(3231, 5745, 0);
			if (ctx.players.getLocal().getLocation().equals(olmStart)) {
				ctx.pathing.walkToTile(olmWalk);
				return;
			}
			handleEatingFood();
			handleDrinkingPrayer();
			rangePotion();
			dropVial();
			SimpleNpc mv = olmRightHand().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mvnpc = mv != null ? mv : olmRightHand().nearest().next();
			SimpleNpc mv2 = olmLeftHand().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mv2npc = mv2 != null ? mv2 : olmLeftHand().nearest().next();
			SimpleNpc mv3 = olm().filter((n) -> n.getInteracting() != null && n.inCombat()).next();
			SimpleNpc mv3npc = mv3 != null ? mv3 : olm().nearest().next();
			if (!ctx.npcs.populate().filter(7554).isEmpty() || !ctx.npcs.populate().filter(7553).isEmpty()
					|| !ctx.npcs.populate().filter(7555).isEmpty()) {
				if (ctx.combat.isPoisoned()) {
					SimpleItem antiPoison = ctx.inventory.populate().filterContains("Antipoison").next();
					if (antiPoison != null) {
						antiPoison.interact(74);
					}
				}
				if (ctx.players.getLocal().getInteracting() == null) {
					if (mvnpc != null) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
							Raids.status = "Turning On Prayers";
							ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
						}
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
							Raids.status = "Turning On Prayers";
							ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
						}
						if (dhl != null) {
							dhl.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (scythe != null) {
							scythe.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (bos != null) {
							bos.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (bosc != null) {
							bosc.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (rapier != null) {
							rapier.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (avernic != null) {
							avernic.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dragonfireshield != null) {
							dragonfireshield.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (antidragonfire != null) {
							antidragonfire.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (primordials != null) {
							primordials.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dragonboots != null) {
							dragonboots.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (runeboots != null) {
							runeboots.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (tortureor != null) {
							tortureor.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dragondefender != null) {
							dragondefender.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (abyssaltentacle != null) {
							abyssaltentacle.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (whip != null) {
							whip.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dragonscimitar != null) {
							dragonscimitar.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						Raids.status = "Fighting " + mvnpc.getName();
						ctx.log("Fighting " + mvnpc.getName());
						mvnpc.interact(SimpleNpcActions.ATTACK);
					} else if (mv2npc != null) {
						if (ctx.equipment.populate().filter(22325).isEmpty()) {
							if (Raids.mageType == -2) {
								if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
									Raids.status = "Turning On Prayers";
									ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
								}
							} else if (Raids.mageType == -1) {
								if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.MYSTIC_MIGHT)) {
									Raids.status = "Turning On Prayers";
									ctx.prayers.prayer(SimplePrayers.Prayers.MYSTIC_MIGHT, true);
								}
							}
						} else {
							if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
								Raids.status = "Turning On Prayers";
								ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
							}
						}
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
							Raids.status = "Turning On Prayers";
							ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
						}
						if (totswamp != null) {
							totswamp.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (sangstaff != null) {
							sangstaff.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (totseas != null) {
							totseas.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (mystichat != null) {
							mystichat.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (mysticbody != null) {
							mysticbody.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (mysticlegs != null) {
							mysticlegs.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (lmystichat != null) {
							lmystichat.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (lmysticbody != null) {
							lmysticbody.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (lmysticlegs != null) {
							lmysticlegs.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}if (dmystichat != null) {
							dmystichat.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dmysticbody != null) {
							dmysticbody.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dmysticlegs != null) {
							dmysticlegs.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (dragonfireshield != null) {
							dragonfireshield.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedguthixmaxcape != null) {
							imbuedguthixmaxcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedsaramaxcape != null) {
							imbuedsaramaxcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedzammymaxcape != null) {
							imbuedzammymaxcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedguthixcape != null) {
							imbuedguthixcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedsaracape != null) {
							imbuedsaracape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (imbuedzammycape != null) {
							imbuedzammycape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (guthixcape != null) {
							guthixcape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (saracape != null) {
							saracape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (zammycape != null) {
							zammycape.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (eternals != null) {
							eternals.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (magebook != null) {
							magebook.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (occult != null) {
							occult.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (bookofdarkness != null) {
							bookofdarkness.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (voidmage != null) {
							voidmage.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (voidmageor != null) {
							voidmageor.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						if (seersringi != null) {
							seersringi.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						}
						Raids.status = "Fighting " + mv2npc.getName();
						ctx.log("Fighting " + mv2npc.getName());
						mv2npc.interact(SimpleNpcActions.ATTACK);
					} else if (mv3npc != null) {
						if (ctx.equipment.populate().filter(22325).isEmpty()) {
							if (Raids.rangeType == -2) {
								if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
									Raids.status = "Turning On Prayers";
									ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
								}
							} else if (Raids.rangeType == -1) {
								if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
									Raids.status = "Turning On Prayers";
									ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
								}
							}
						} else {
							if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
								Raids.status = "Turning On Prayers";
								ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
							}
						}
						if (scythe != null) {
							if (ferociousgloves != null) {
								ferociousgloves.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (firemaxcape != null) {
								firemaxcape.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (infernalmaxcape != null) {
								infernalmaxcape.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (infernalcape != null) {
								infernalcape.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (firecape != null) {
								firecape.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (primordials != null) {
								primordials.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dragonboots != null) {
								dragonboots.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (runeboots != null) {
								runeboots.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (torture != null) {
								torture.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (voidmelee != null) {
								voidmelee.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (voidmeleeor != null) {
								voidmeleeor.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (berserkerringi != null) {
								berserkerringi.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							scythe.interact(SimpleItemActions.WEAR);
							ctx.sleep(100, 200);
						} else if (ctx.equipment.populate().filter(22325).next() != null) {
							mv3npc.interact(SimpleNpcActions.ATTACK);
						} else {
							if (voidrange != null) {
								voidrange.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (voidrangeor != null) {
								voidrangeor.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dhidebody != null) {
								dhidebody.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dhidelegs != null) {
								dhidelegs.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dhidevamb != null) {
								dhidevamb.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (avas != null) {
								avas.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (blowpipe != null) {
								blowpipe.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (twistedbow != null) {
								twistedbow.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (cbowfa != null) {
								cbowfa.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (bowfa != null) {
								bowfa.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (msbi != null) {
								msbi.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (msb != null) {
								msb.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (assemblermax != null) {
								assemblermax.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (archerringi != null) {
								archerringi.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (assembler != null) {
								assembler.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (zarytevambs != null) {
								zarytevambs.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (pegasian != null) {
								pegasian.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (anguish != null) {
								anguish.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (anguishor != null) {
								anguishor.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (snakeskin != null) {
								snakeskin.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dhcb != null) {
								dhcb.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (antidragonward != null) {
								antidragonward.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (bolts != null) {
								bolts.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (rcbow != null) {
								rcbow.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (dragoncbow != null) {
								dragoncbow.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (armadylcbow != null) {
								armadylcbow.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (zarytecbow != null) {
								zarytecbow.interact(SimpleItemActions.WEAR);
								ctx.sleep(100, 200);
							}
							if (!ctx.equipment.populate().filter(22325).isEmpty()) {
								if (infernalmaxcape != null) {
									infernalmaxcape.interact(SimpleItemActions.WEAR);
									ctx.sleep(100, 200);
								}
							}
						}
						Raids.status = "Fighting " + mv3npc.getName();
						ctx.log("Fighting " + mv3npc.getName());
						mv3npc.interact(SimpleNpcActions.ATTACK);
					} else {
						if (olmExit != null) {
							if (ctx.pathing.distanceTo(olmExit) <= 8) {
								ctx.sleep(250, 1500);
								olmExit.interact(SimpleObjectActions.OPEN);
								ctx.sleep(250, 1500);
								ctx.prayers.disableAll();
							} else {
								ctx.sleep(250, 1500);
								ctx.pathing.step(olmExit);
								ctx.sleep(250, 1500);
								ctx.prayers.disableAll();
							}
						} else {
							ctx.pathing.walkToTile(olmWalk2.derrive(between(-2, 1), between(-2, 2)));
						}
					}
				}
			} else {
				if (olmExit != null) {
					if (ctx.pathing.distanceTo(olmExit) <= 8) {
						olmExit.interact(SimpleObjectActions.OPEN);
						ctx.prayers.disableAll();
					} else {
						ctx.pathing.step(olmExit);
					}
				} else {
					ctx.pathing.walkToTile(olmWalk2.derrive(between(-2, 1), between(-2, 2)));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void WaitingArea() {
		if (WAITING_AREA.containsPoint(ctx.players.getLocal())) {
			SimplePlayer player = ctx.players.populate().filter(invite).nearest().next();
			ctx.prayers.disableAll();
			Raids.dead = false;
			SimpleWidget party = ctx.widgets.populate().filter(30372).next();
			if (!Raids.presetLoaded) {
				Raids.status = "Loading Preset";
				ctx.log("Loading Preset");
				ctx.quickGear.latest();
			}
			if (Raids.isLeader) {
				if (!party.getText().contains("/100)")) {
					Raids.status = "Creating Party";
					ctx.log("Creating Party");
					ctx.menuActions.sendAction(169, -1, -1, 30374);
					ctx.onCondition(() -> party.getText().contains("/100)"), 250, 8);
					return;
				}
				if (player != null) {
					ctx.menuActions.sendAction(2027, player.getId(), -1, 0);
					ctx.sleep(2000);
					Raids.status = "Inviting " + invite;
					ctx.log("Inviting " + invite);
					ctx.sleep(600);
					player = null;
					invite = null;
				}
				for (int i = 0; i <= Raids.leaderDelay + 1; i++) {
					System.out.print("Reaches " + Raids.leaderDelay + ", it will start raid - " + i + "\n");
					ctx.sleep(600);
					if (i >= Raids.leaderDelay) {
						SimpleGameObject wall = (SimpleGameObject) ctx.objects.populate().filter("Chambers of Xeric").nearest().next();
						SimpleGameObject wall2 = (SimpleGameObject) ctx.objects.populate().filter("Barrier").nearest().next();
						if (wall != null || wall2 != null) {
							Raids.status = "Entering Raids";
							ctx.log("Entering Raids");
							if (wall != null) {
								wall.interact(SimpleObjectActions.OPEN);
								ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 363, 250, 12);
							} else if (wall2 != null) {
								wall2.interact(SimpleObjectActions.OPEN);
								ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 363, 250, 12);
							}
							if (ctx.widgets.getBackDialogId() == 363) {
								ctx.menuActions.sendAction(679, -1, -1, 367);
							}
							ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459, 250, 12);
							if (ctx.widgets.getBackDialogId() == 2459) {
								ctx.menuActions.sendAction(315, -1, -1, 2461);
							}
							ctx.onCondition(() -> !WAITING_AREA.containsPoint(ctx.players.getLocal()), 250, 3);
						}
						i = 0;
						break;
					}
				}
			} else {
				Raids.status = "Waiting On Leader";
				if (ctx.dialogue.dialogueOpen()) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
				}
			}
			/*if (player != null) {
				ctx.log("Debug: " + invite);
				invite = null;
				player = null;
			}*/
		}
	}

	private static void rangePotion() {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (lastOverload + 301000) && ctx.combat.health() >= 65) {
			Raids.status = "Using Overload";
			ctx.log("Using Overload");
			ctx.sleep(1200);
			overload.interact(74);
			ctx.sleep(600);
			lastOverload = System.currentTimeMillis();
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9) {
			Raids.status = "Drinking Ranging Potion";
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedLevel - realLevel <= 2) {
			Raids.status = "Using Focused Heart";
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
			ctx.sleep(600);
		}
	}

	public static void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.health() <= 65 && ctx.combat.health() != 10) {
			Raids.status = "Eating Food";
			ctx.log("Eating Food");
			food.interact(74);
		} else if (brew != null && ctx.combat.health() <= 70 && ctx.combat.health() != 10) {
			Raids.status = "Drinking brew";
			ctx.log("Drinking brew");
			brew.interact(74);
		}
	}

	public static void handleDrinkingPrayer() {
		if (!ctx.inventory.populate().filterContains("Super r").isEmpty() && ctx.prayers.points() <= 20) {
			Raids.status = "Drinking Restore Potion";
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= 20) {
			Raids.status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		}
	}

	public static void nextRoom() {
		SimpleGameObject exit = (SimpleGameObject) ctx.objects.populate().filter(29789).nearest().next();
		Raids.status = "Entering Next Room";
		if (!ctx.pathing.inMotion()) {
			ctx.log("Entering Next Room");
		}
		if (exit != null) {
			if (ctx.pathing.distanceTo(exit) <= 9) {
				exit.interact(SimpleObjectActions.OPEN);
			} else {
				ctx.pathing.step(exit);
			}
		}
	}

	public static void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			Raids.status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}

	public static void Spec() {
		if (!ctx.equipment.populate().filter(12788).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50
				&& ctx.players.getLocal().inCombat()) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleepCondition(() -> ctx.combat.specialAttack(), 1500);
		}
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50
				&& ctx.players.getLocal().inCombat()
				&& ctx.players.getLocal().getHealth() <= ctx.players.getLocal().getMaxHealth() - 25) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleepCondition(() -> ctx.combat.specialAttack(), 1500);
		}
	}

	public final static SimpleEntityQuery<SimpleNpc> npcs() {
		return ctx.npcs.populate().filter(npcId).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> meleeVanguard() {
		return ctx.npcs.populate().filter(7527).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> meleeVanguard2() {
		return ctx.npcs.populate().filter(7528).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> mageVanguard() {
		return ctx.npcs.populate().filter(7529).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> mage1() {
		return ctx.npcs.populate().filter(7605).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> mage2() {
		return ctx.npcs.populate().filter(7604).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> mage3() {
		return ctx.npcs.populate().filter(7606).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> olmRightHand() {
		return ctx.npcs.populate().filter(7555).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> olmLeftHand() {
		return ctx.npcs.populate().filter(7553).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> olm() {
		return ctx.npcs.populate().filter(7554).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 40) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	private static int between(final int min, final int max) {
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}
}