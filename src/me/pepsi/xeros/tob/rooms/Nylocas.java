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

public class Nylocas {
	static ClientContext ctx = ClientContext.instance();
	static boolean spiderSwap = false;
	static boolean spiderDead = false;
	static WorldPoint roomThreeStart = new WorldPoint(3296, 4283, 0);
	static WorldPoint roomThreeWalk = new WorldPoint(3295, 4265, 0);
	static WorldPoint roomThreeBarrier = new WorldPoint(3297, 4256, 0);
	
	
	public static void roomThree() {
		SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Barrier").shuffle().next();
		SimpleNpc mv3 = melee().nearest().next();
		SimpleNpc melee = mv3 != null ? mv3 : melee().nearest().next();
		SimpleNpc mv4 = mage().nearest().next();
		SimpleNpc mage = mv4 != null ? mv4 : mage().nearest().next();
		SimpleNpc mv5 = range().nearest().next();
		SimpleNpc range = mv5 != null ? mv5 : range().nearest().next();
		SimpleItem voidmage = ctx.inventory.populate().filter(11663).next();
		SimpleItem voidmageor = ctx.inventory.populate().filter(26473).next();
		SimpleItem voidrange = ctx.inventory.populate().filter(11664).next();
		SimpleItem voidrangeor = ctx.inventory.populate().filter(26475).next();
		SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
		SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
		SimpleItem blowpipe = ctx.inventory.populate().filter(12926).next();
		SimpleItem pegs = ctx.inventory.populate().filter(13237).next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
		SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
		SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem prims = ctx.inventory.populate().filter(13239).next();
		SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem torture = ctx.inventory.populate().filter(19553).next();
		SimpleItem magesbook = ctx.inventory.populate().filter(6889).next();
		SimpleItem totseas = ctx.inventory.populate().filter(11907).next();
		SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
		SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
		SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
		SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
		SimpleItem guthixcape = ctx.inventory.populate().filter(2413).next();
		SimpleItem saracape = ctx.inventory.populate().filter(2412).next();
		SimpleItem zammycape = ctx.inventory.populate().filter(2414).next();
		SimpleItem zammymaxcape = ctx.inventory.populate().filter(13333).next();
		SimpleItem imbuedguthixcape = ctx.inventory.populate().filter(21793).next();
		SimpleItem imbuedsaracape = ctx.inventory.populate().filter(21791).next();
		SimpleItem imbuedzammycape = ctx.inventory.populate().filter(21795).next();
		SimpleItem imbuedguthixmaxcape = ctx.inventory.populate().filter(21784).next();
		SimpleItem imbuedsaramaxcape = ctx.inventory.populate().filter(21776).next();
		SimpleItem imbuedzammymaxcape = ctx.inventory.populate().filter(21780).next();
		SimpleItem tormentedbracelet = ctx.inventory.populate().filter(19544).next();
		SimpleItem assemblermax = ctx.inventory.populate().filter(21898).next();
		SimpleItem assembler = ctx.inventory.populate().filter(22109).next();
		SimpleItem archerringi = ctx.inventory.populate().filter(11771).next();
		SimpleItem seersringi = ctx.inventory.populate().filter(11770).next();
		SimpleItem bookofdarkness = ctx.inventory.populate().filter(12612).next();
		SimpleItem totswamp = ctx.inventory.populate().filter(12899).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem eternals = ctx.inventory.populate().filter(13235).next();
		SimpleItem zarytevambs = ctx.inventory.populate().filter(26235).next();
		SimpleItem anguish = ctx.inventory.populate().filter(19547).next();
		SimpleItem occult = ctx.inventory.populate().filter(12002).next();
		SimpleItem twistedbow = ctx.inventory.populate().filter(20997).next();
		SimpleItem sangstaff = ctx.inventory.populate().filter(22323).next();
		SimpleItem avernic = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
		SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
		SimpleItem cbowfa = ctx.inventory.populate().filter(25867).next();
		SimpleItem bowfa = ctx.inventory.populate().filter(25865).next();
		if (Util.ROOM_THREE.containsPoint(ctx.players.getLocal())) {
			Util.handleRangePotion();
			if (spiderDead && spiderSwap == false) {
				if (scythe != null || (!ctx.equipment.populate().filter(22325).isEmpty())) {
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
					if (voidmelee != null) {
						voidmelee.interact(SimpleItemActions.WEAR);
					}
					if (voidmeleeor != null) {
						voidmeleeor.interact(SimpleItemActions.WEAR);
					}
					if (scythe != null) {
						scythe.interact(SimpleItemActions.WEAR);
					}
				}
				spiderSwap = true;
			}
			if (barrier != null && spiderDead == false) {
				if (ctx.players.getLocal().getLocation().distanceTo(barrier) >= 10) {
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(0, -8));
					ctx.sleep(600);
					return;
				} else {
					if (ctx.widgets.getBackDialogId() != 2459) {
						barrier.interact(502);
					}
					if (!ctx.inventory.populate().filter(13576).isEmpty()) {
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
					} else if (!ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) {
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
						if (!ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) {
							ctx.inventory.populate().filterContains("Dragon dagger").next().interact(454);
						}
						if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
							ctx.inventory.populate().filterContains("defender").next().interact(454);
						}
					}
					ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_THREE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
					if (ctx.widgets.getBackDialogId() == 2459) {
						ctx.menuActions.sendAction(315, -1, -1, 2461);
						ctx.onCondition(() -> Util.ROOM_THREE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
					}
				}
			} else if (!spiderDead) {
				ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(0, -8));
				ctx.sleep(600);
				return;
			} else if (spiderDead) {
				SimpleWallObject exit = (SimpleWallObject) ctx.objects.populate().filter("Formidable passage").nearest().next();
				SimpleGameObject chest = (SimpleGameObject) ctx.objects.populate().filter("Chest").nearest().next();
				ctx.prayers.disableAll();
				if (chest != null && Tob.restock && ctx.widgets.getOpenInterfaceId() != 21490 && ctx.inventory.getFreeSlots() > 1) {
					chest.interact(502);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 21490, 250, 8);
				}
				SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
				if (dwh != null || ctx.equipment.populate().filter(13576).next() != null) {
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
				} else {
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
					if (torture != null && ctx.equipment.populate().filter(12018).isEmpty() && ctx.equipment.populate().filter(12017).isEmpty() && ctx.equipment.populate().filter(10588).isEmpty()) {
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
					if (scythe != null) {
						scythe.interact(SimpleItemActions.WEAR);
					}
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
				if (exit != null && exit.getLocation().y <= 4280 &&  (!Tob.restock || ctx.inventory.getFreeSlots() <= 1)) {
					ctx.prayers.disableAll();
					Tob.status = "Pathing to next room";
					ctx.log("Pathing to next room");
					if ((System.currentTimeMillis() - Tob.lastOverload) >= 240000) {
						Tob.lastOverload = (System.currentTimeMillis() - 605000);
						ctx.sleep(600);
						Util.handleRangePotion();
						Tob.lastOverload = System.currentTimeMillis();
					}
					exit.interact(502);
				} else {
					ctx.pathing.step(ctx.players.getLocal().getLocation().derrive(0, 8));
				}
			}
		} else if (Util.ROOM_THREE_FIGHT.containsPoint(ctx.players.getLocal())) {
			Tob.restock = true;
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.handleRangePotion();
			Util.dropVial();
			if (melee != null) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if ((!ctx.equipment.populate().filterContains("Dragon dagger").isEmpty() || !ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) && ctx.combat.getSpecialAttackPercentage() >= 40) {
					Util.useSpecialDDS();
				} else {
					if ((scythe != null || (!ctx.equipment.populate().filter(22325).isEmpty()) && ctx.equipment.populate().filter(13576).isEmpty() && ctx.equipment.populate().filterContains("Dragon dagger").isEmpty())) {
						if (scythe != null) {
							ctx.pathing.step(ctx.players.getLocal().getLocation());
							scythe.interact(SimpleItemActions.WEAR);
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
					} else if (ctx.equipment.populate().filter(13576).isEmpty() && ctx.equipment.populate().filterContains("Dragon dagger").isEmpty()) {
						if (bosc != null) {
							ctx.pathing.step(ctx.players.getLocal().getLocation());
							bosc.interact(SimpleItemActions.WEAR);
						}
						if (rapier != null) {
							ctx.pathing.step(ctx.players.getLocal().getLocation());
							rapier.interact(SimpleItemActions.WEAR);
						}
						if (bos != null) {
							ctx.pathing.step(ctx.players.getLocal().getLocation());
							bos.interact(SimpleItemActions.WEAR);
						}
						if (abyssaltentacle != null) {
							ctx.pathing.step(ctx.players.getLocal().getLocation());
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
				}
				if (ctx.players.getLocal().getInteracting() == null && (ctx.equipment.populate().filterContains("Dagger").next() != null || ctx.equipment.populate().filter(4151).next() != null || ctx.equipment.populate().filter(24551).next() != null || ctx.equipment.populate().filter(23995).next() != null || ctx.equipment.populate().filter(22325).next() != null || ctx.equipment.populate().filter(22324).next() != null || ctx.equipment.populate().filter(12006).next() != null)) {
					Tob.status = "Attacking melee Nylocas";
					ctx.log("Attacking melee Nylocas");
					melee.interact(SimpleNpcActions.ATTACK);
				}
			} else if (mage != null) {
				if (totseas != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					totseas.interact(SimpleItemActions.WEAR);
				}
				if (sangstaff != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					sangstaff.interact(SimpleItemActions.WEAR);
				}
				if (totswamp != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					totswamp.interact(SimpleItemActions.WEAR);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if (Tob.mageType == -2) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.AUGURY)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.AUGURY, true);
					}
				} else if (Tob.mageType == -1) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.MYSTIC_MIGHT)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.MYSTIC_MIGHT, true);
					}
				}
				if (eternals != null) {
					eternals.interact(SimpleItemActions.WEAR);
				}
				if (occult != null) {
					occult.interact(SimpleItemActions.WEAR);
				}
				if (zammycape != null) {
					zammycape.interact(SimpleItemActions.WEAR);
				}
				if (zammymaxcape != null) {
					zammymaxcape.interact(SimpleItemActions.WEAR);
				}
				if (tormentedbracelet != null) {
					tormentedbracelet.interact(SimpleItemActions.WEAR);
				}
				if (saracape != null) {
					saracape.interact(SimpleItemActions.WEAR);
				}
				if (guthixcape != null) {
					guthixcape.interact(SimpleItemActions.WEAR);
				}
				if (imbuedguthixcape != null) {
					imbuedguthixcape.interact(SimpleItemActions.WEAR);
				}
				if (imbuedsaracape != null) {
					imbuedsaracape.interact(SimpleItemActions.WEAR);
				}
				if (imbuedzammycape != null) {
					imbuedzammycape.interact(SimpleItemActions.WEAR);
				}
				if (imbuedzammymaxcape != null) {
					imbuedzammymaxcape.interact(SimpleItemActions.WEAR);
				}
				if (bookofdarkness != null) {
					bookofdarkness.interact(SimpleItemActions.WEAR);
				}
				if (imbuedsaramaxcape != null) {
					imbuedsaramaxcape.interact(SimpleItemActions.WEAR);
				}
				if (magesbook != null) {
					magesbook.interact(SimpleItemActions.WEAR);
				}
				if (imbuedguthixmaxcape != null) {
					imbuedguthixmaxcape.interact(SimpleItemActions.WEAR);
				}
				if (seersringi != null) {
					seersringi.interact(SimpleItemActions.WEAR);
				}
				if (voidmage != null) {
					voidmage.interact(SimpleItemActions.WEAR);
				}
				if (voidmageor != null) {
					voidmageor.interact(SimpleItemActions.WEAR);
				}
				if (ctx.players.getLocal().getInteracting() == null && (ctx.equipment.populate().filter(22323).next() != null || ctx.equipment.populate().filter(12899).next() != null || ctx.equipment.populate().filter(11907).next() != null)) {
					Tob.status = "Attacking mage Nylocas";
					ctx.log("Attacking mage Nylocas");
					mage.interact(SimpleNpcActions.ATTACK);
				}
			} else if (range != null) {
				if (bowfa != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					bowfa.interact(SimpleItemActions.WEAR);
				}
				if (cbowfa != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					cbowfa.interact(SimpleItemActions.WEAR);
				}
				if (blowpipe != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					blowpipe.interact(SimpleItemActions.WEAR);
				}
				if (twistedbow != null) {
					ctx.pathing.step(ctx.players.getLocal().getLocation());
					twistedbow.interact(SimpleItemActions.WEAR);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
				}
				if (Tob.rangeType == -2) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				} else if (Tob.rangeType == -1) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				}
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
				Util.useSpecialBP();
				Util.handleEatingFood();
				if (ctx.players.getLocal().getInteracting() == null && (ctx.equipment.populate().filter(12926).next() != null || ctx.equipment.populate().filter(25865).next() != null || ctx.equipment.populate().filter(25867).next() != null || ctx.equipment.populate().filter(20997).next() != null)) {
					Tob.status = "Attacking Range Nylocas";
					ctx.log("Attacking Range Nylocas");
					range.interact(SimpleNpcActions.ATTACK);
				}
			} else {
				spiderDead = true;
				if (barrier != null) {
					barrier.interact(502);
					ctx.onCondition(() -> Util.ROOM_THREE.containsPoint(ctx.players.getLocal()), 250, 8);
				}
			}
		}
	}
	
	public final static SimpleEntityQuery<SimpleNpc> melee() {
		return ctx.npcs.populate().filter(8355).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> mage() {
		return ctx.npcs.populate().filter(8356).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}

	public final static SimpleEntityQuery<SimpleNpc> range() {
		return ctx.npcs.populate().filter(8357).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
}
