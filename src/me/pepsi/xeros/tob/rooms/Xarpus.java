package me.pepsi.xeros.tob.rooms;

import java.util.ArrayList;
import java.util.List;
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

public class Xarpus {
	static ClientContext ctx = ClientContext.instance();
	public static WorldPoint xarpusMeleeStand1 = new WorldPoint(3167, 4385, 1);
	public static WorldPoint xarpusMeleeStand2 = new WorldPoint(3168, 4385, 1);
	public static WorldPoint xarpusMeleeStand3 = new WorldPoint(3168, 4386, 1);
	public static WorldPoint xarpusMeleeStand4 = new WorldPoint(3168, 4387, 1);
	public static WorldPoint xarpusMeleeStand5 = new WorldPoint(3167, 4387, 1);
	public static WorldPoint xarpusMeleeStand6 = new WorldPoint(3167, 4386, 1);
	
	public static WorldPoint xarpusMeleeStand7 = new WorldPoint(3167, 4388, 1);
	public static WorldPoint xarpusMeleeStand8 = new WorldPoint(3168, 4388, 1);
	public static WorldPoint xarpusMeleeStand9 = new WorldPoint(3168, 4389, 1);
	public static WorldPoint xarpusMeleeStand10 = new WorldPoint(3168, 4390, 1);
	public static WorldPoint xarpusMeleeStand11 = new WorldPoint(3167, 4390, 1);
	public static WorldPoint xarpusMeleeStand12 = new WorldPoint(3167, 4389, 1);
	
	public static WorldPoint xarpusMeleeStand13 = new WorldPoint(3169, 4390, 1);
	public static WorldPoint xarpusMeleeStand14 = new WorldPoint(3169, 4389, 1);
	public static WorldPoint xarpusMeleeStand15 = new WorldPoint(3170, 4389, 1);
	public static WorldPoint xarpusMeleeStand16 = new WorldPoint(3171, 4389, 1);
	public static WorldPoint xarpusMeleeStand17 = new WorldPoint(3171, 4390, 1);
	public static WorldPoint xarpusMeleeStand18 = new WorldPoint(3170, 4390, 1);
	
	public static WorldPoint xarpusMeleeStand19 = new WorldPoint(3172, 4390, 1);
	public static WorldPoint xarpusMeleeStand20 = new WorldPoint(3172, 4389, 1);
	public static WorldPoint xarpusMeleeStand21 = new WorldPoint(3172, 4388, 1);
	public static WorldPoint xarpusMeleeStand22 = new WorldPoint(3173, 4388, 1);
	public static WorldPoint xarpusMeleeStand23 = new WorldPoint(3173, 4389, 1);
	public static WorldPoint xarpusMeleeStand24 = new WorldPoint(3173, 4390, 1);
	
	public static WorldPoint xarpusMeleeStand25 = new WorldPoint(3172, 4387, 1);
	public static WorldPoint xarpusMeleeStand26 = new WorldPoint(3172, 4386, 1);
	public static WorldPoint xarpusMeleeStand27 = new WorldPoint(3172, 4385, 1);
	public static WorldPoint xarpusMeleeStand28 = new WorldPoint(3173, 4385, 1);
	public static WorldPoint xarpusMeleeStand29 = new WorldPoint(3173, 4386, 1);
	public static WorldPoint xarpusMeleeStand30 = new WorldPoint(3173, 4387, 1);
	
	public static WorldPoint xarpusMeleeStand31 = new WorldPoint(3171, 4384, 1);
	public static WorldPoint xarpusMeleeStand32 = new WorldPoint(3171, 4385, 1);
	public static WorldPoint xarpusMeleeStand33 = new WorldPoint(3170, 4385, 1);
	public static WorldPoint xarpusMeleeStand34 = new WorldPoint(3169, 4385, 1);
	public static WorldPoint xarpusMeleeStand35 = new WorldPoint(3169, 4384, 1);
	public static WorldPoint xarpusMeleeStand36 = new WorldPoint(3170, 4384, 1);
	
	public static WorldPoint xarpusRangeStand1 = new WorldPoint(3167, 4382, 1);
	public static WorldPoint xarpusRangeStand2 = new WorldPoint(3168, 4382, 1);
	public static WorldPoint xarpusRangeStand3 = new WorldPoint(3169, 4382, 1);
	public static WorldPoint xarpusRangeStand4 = new WorldPoint(3169, 4383, 1);
	public static WorldPoint xarpusRangeStand5 = new WorldPoint(3168, 4383, 1);
	public static WorldPoint xarpusRangeStand6 = new WorldPoint(3167, 4383, 1);
	
	public static WorldPoint xarpusRangeStand7 = new WorldPoint(3170, 4382, 1);
	public static WorldPoint xarpusRangeStand8 = new WorldPoint(3171, 4382, 1);
	public static WorldPoint xarpusRangeStand9 = new WorldPoint(3172, 4382, 1);
	public static WorldPoint xarpusRangeStand10 = new WorldPoint(3172, 4383, 1);
	public static WorldPoint xarpusRangeStand11 = new WorldPoint(3171, 4383, 1);
	public static WorldPoint xarpusRangeStand12 = new WorldPoint(3170, 4383, 1);
	
	public static WorldPoint xarpusRangeStand13 = new WorldPoint(3174, 4383, 1);
	public static WorldPoint xarpusRangeStand14 = new WorldPoint(3174, 4384, 1);
	public static WorldPoint xarpusRangeStand15 = new WorldPoint(3174, 4385, 1);
	public static WorldPoint xarpusRangeStand16 = new WorldPoint(3175, 4385, 1);
	public static WorldPoint xarpusRangeStand17 = new WorldPoint(3175, 4384, 1);
	public static WorldPoint xarpusRangeStand18 = new WorldPoint(3175, 4383, 1);
	
	public static WorldPoint xarpusRangeStand19 = new WorldPoint(3174, 4388, 1);
	public static WorldPoint xarpusRangeStand20 = new WorldPoint(3174, 4389, 1);
	public static WorldPoint xarpusRangeStand21 = new WorldPoint(3174, 4390, 1);
	public static WorldPoint xarpusRangeStand22 = new WorldPoint(3175, 4390, 1);
	public static WorldPoint xarpusRangeStand23 = new WorldPoint(3175, 4389, 1);
	public static WorldPoint xarpusRangeStand24 = new WorldPoint(3175, 4388, 1);
	
	public static WorldPoint xarpusRangeStand25 = new WorldPoint(3173, 4391, 1);
	public static WorldPoint xarpusRangeStand26 = new WorldPoint(3172, 4391, 1);
	public static WorldPoint xarpusRangeStand27 = new WorldPoint(3171, 4391, 1);
	public static WorldPoint xarpusRangeStand28 = new WorldPoint(3171, 4392, 1);
	public static WorldPoint xarpusRangeStand29 = new WorldPoint(3172, 4392, 1);
	public static WorldPoint xarpusRangeStand30 = new WorldPoint(3173, 4392, 1);
	
	public static WorldPoint xarpusRangeStand31 = new WorldPoint(3166, 4389, 1);
	public static WorldPoint xarpusRangeStand32 = new WorldPoint(3166, 4388, 1);
	public static WorldPoint xarpusRangeStand33 = new WorldPoint(3166, 4387, 1);
	public static WorldPoint xarpusRangeStand34 = new WorldPoint(3165, 4387, 1);
	public static WorldPoint xarpusRangeStand35 = new WorldPoint(3165, 4388, 1);
	public static WorldPoint xarpusRangeStand36 = new WorldPoint(3165, 4389, 1);
	static WorldPoint xarpusExit = new WorldPoint(3171, 4395, 1);
	
	public static void roomFive() {
		SimpleGameObject barrier = (SimpleGameObject) ctx.objects.populate().filter("Barrier").shuffle().next();
		if (Util.ROOM_FIVE.containsPoint(ctx.players.getLocal())) {
			if (barrier != null && Tob.isLeader && ctx.players.getLocal().getLocation().distanceTo(barrier.getLocation()) <= 7) {
				Tob.status = "Pathing to barrier";
				ctx.log("Pathing to barrier");
				barrier.interact(502);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 16);
				if (ctx.widgets.getBackDialogId() == 2459) {
					ctx.menuActions.sendAction(315, -1, -1, 2461);
					ctx.onCondition(() -> Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal()), 250, 3);
				}
			}
		} else if (Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
			Tob.restock = true;
			SimpleNpc mv7 = xarpus().nearest().next();
			SimpleNpc xarpus = mv7 != null ? mv7 : xarpus().nearest().next();
			if (xarpus != null && xarpus.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 8) {
				if ((!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(25867).isEmpty() || !ctx.equipment.populate().filter(25865).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty())) {
					if (Tob.rangeType == -2) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
						}
					} else if (Tob.rangeType == -1) {
						if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
							ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
						}
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				}
				if (!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(25867).isEmpty() || !ctx.equipment.populate().filter(25865).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) {
					if (!ctx.pathing.onTile(xarpusRangeStand1) && !ctx.pathing.onTile(xarpusRangeStand2) && !ctx.pathing.onTile(xarpusRangeStand3) && !ctx.pathing.onTile(xarpusRangeStand4) && !ctx.pathing.onTile(xarpusRangeStand5) && !ctx.pathing.onTile(xarpusRangeStand6) && !ctx.pathing.onTile(xarpusRangeStand7) && !ctx.pathing.onTile(xarpusRangeStand8) && !ctx.pathing.onTile(xarpusRangeStand9) && !ctx.pathing.onTile(xarpusRangeStand10) && !ctx.pathing.onTile(xarpusRangeStand11) && !ctx.pathing.onTile(xarpusRangeStand12) && !ctx.pathing.onTile(xarpusRangeStand13) && !ctx.pathing.onTile(xarpusRangeStand14) && !ctx.pathing.onTile(xarpusRangeStand15) && !ctx.pathing.onTile(xarpusRangeStand16) && !ctx.pathing.onTile(xarpusRangeStand17) && !ctx.pathing.onTile(xarpusRangeStand18) && !ctx.pathing.onTile(xarpusRangeStand19) && !ctx.pathing.onTile(xarpusRangeStand20) && !ctx.pathing.onTile(xarpusRangeStand21) && !ctx.pathing.onTile(xarpusRangeStand22) && !ctx.pathing.onTile(xarpusRangeStand23) && !ctx.pathing.onTile(xarpusRangeStand24) && !ctx.pathing.onTile(xarpusRangeStand25) && !ctx.pathing.onTile(xarpusRangeStand26) && !ctx.pathing.onTile(xarpusRangeStand27) && !ctx.pathing.onTile(xarpusRangeStand28) && !ctx.pathing.onTile(xarpusRangeStand29) && !ctx.pathing.onTile(xarpusRangeStand30) && !ctx.pathing.onTile(xarpusRangeStand31) && !ctx.pathing.onTile(xarpusRangeStand32) && !ctx.pathing.onTile(xarpusRangeStand33) && !ctx.pathing.onTile(xarpusRangeStand34) && !ctx.pathing.onTile(xarpusRangeStand35) && !ctx.pathing.onTile(xarpusRangeStand36)) {
						Random rn = new Random();
						int random = rn.nextInt(6);
						if (random == 0) {
							ctx.pathing.step(xarpusRangeStand1);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand1), 250, 16);
						} else if (random == 1) {
							ctx.pathing.step(xarpusRangeStand7);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand7), 250, 16);
						} else if (random == 2) {
							ctx.pathing.step(xarpusRangeStand13);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand13), 250, 16);
						} else if (random == 3) {
							ctx.pathing.step(xarpusRangeStand19);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand19), 250, 16);
						} else if (random == 4) {
							ctx.pathing.step(xarpusRangeStand25);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand25), 250, 16);
						} else if (random == 5) {
							ctx.pathing.step(xarpusRangeStand31);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusRangeStand31), 250, 16);
						}
					}
				} else {
					if (!ctx.pathing.onTile(xarpusMeleeStand1) && !ctx.pathing.onTile(xarpusMeleeStand2) && !ctx.pathing.onTile(xarpusMeleeStand3) && !ctx.pathing.onTile(xarpusMeleeStand4) && !ctx.pathing.onTile(xarpusMeleeStand5) && !ctx.pathing.onTile(xarpusMeleeStand6) && !ctx.pathing.onTile(xarpusMeleeStand7) && !ctx.pathing.onTile(xarpusMeleeStand8) && !ctx.pathing.onTile(xarpusMeleeStand9) && !ctx.pathing.onTile(xarpusMeleeStand10) && !ctx.pathing.onTile(xarpusMeleeStand11) && !ctx.pathing.onTile(xarpusMeleeStand12) && !ctx.pathing.onTile(xarpusMeleeStand13) && !ctx.pathing.onTile(xarpusMeleeStand14) && !ctx.pathing.onTile(xarpusMeleeStand15) && !ctx.pathing.onTile(xarpusMeleeStand16) && !ctx.pathing.onTile(xarpusMeleeStand17) && !ctx.pathing.onTile(xarpusMeleeStand18) && !ctx.pathing.onTile(xarpusMeleeStand19) && !ctx.pathing.onTile(xarpusMeleeStand20) && !ctx.pathing.onTile(xarpusMeleeStand21) && !ctx.pathing.onTile(xarpusMeleeStand22) && !ctx.pathing.onTile(xarpusMeleeStand23) && !ctx.pathing.onTile(xarpusMeleeStand24) && !ctx.pathing.onTile(xarpusMeleeStand25) && !ctx.pathing.onTile(xarpusMeleeStand26) && !ctx.pathing.onTile(xarpusMeleeStand27) && !ctx.pathing.onTile(xarpusMeleeStand28) && !ctx.pathing.onTile(xarpusMeleeStand29) && !ctx.pathing.onTile(xarpusMeleeStand30) && !ctx.pathing.onTile(xarpusMeleeStand31) && !ctx.pathing.onTile(xarpusMeleeStand32) && !ctx.pathing.onTile(xarpusMeleeStand33) && !ctx.pathing.onTile(xarpusMeleeStand34) && !ctx.pathing.onTile(xarpusMeleeStand35) && !ctx.pathing.onTile(xarpusMeleeStand36)) {
						Random rn = new Random();
						int random = rn.nextInt(6);
						if (random == 0) {
							ctx.pathing.step(xarpusMeleeStand1);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand1), 250, 16);
						} else if (random == 1) {
							ctx.pathing.step(xarpusMeleeStand7);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand7), 250, 16);
						} else if (random == 2) {
							ctx.pathing.step(xarpusMeleeStand13);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand13), 250, 16);
						} else if (random == 3) {
							ctx.pathing.step(xarpusMeleeStand19);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand19), 250, 16);
						} else if (random == 4) {
							ctx.pathing.step(xarpusMeleeStand25);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand25), 250, 16);
						} else if (random == 5) {
							ctx.pathing.step(xarpusMeleeStand31);
							ctx.onCondition(() -> ctx.pathing.onTile(xarpusMeleeStand31), 250, 16);
						}
					}
				}
				/*if (Tob.onAcid || (xarpus.isAnimating() && xarpus.getInteracting().equals(ctx.players.getLocal()))) {
					final WorldPoint point = getAcidSpots();
					if (point != null) {
						ctx.pathing.step(point);
					} else {
						ctx.log("No valid destination found.");
					}
				}*/
				if (Tob.onAcid) {
					if (ctx.pathing.onTile(xarpusRangeStand1)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand2);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand2)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand3);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand3)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand4);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand4)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand5);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand5)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand6);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand6)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand1);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand7)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand8);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand8)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand9);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand9)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand10);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand10)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand11);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand11)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand12);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand12)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand7);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand13)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand14);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand14)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand15);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand15)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand16);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand16)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand17);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand17)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand18);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand18)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand13);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand19)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand20);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand20)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand21);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand21)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand22);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand22)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand23);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand23)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand24);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand24)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand19);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand25)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand26);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand26)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand27);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand27)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand28);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand28)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand29);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand29)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand30);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand30)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand25);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand31)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand32);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand32)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand33);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand33)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand34);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand34)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand35);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand35)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand36);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusRangeStand36)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusRangeStand31);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand1)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand2);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand2)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand3);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand3)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand4);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand4)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand5);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand5)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand6);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand6)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand1);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand7)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand8);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand8)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand9);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand9)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand10);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand10)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand11);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand11)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand12);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand12)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand7);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand13)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand14);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand14)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand15);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand15)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand16);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand16)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand17);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand17)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand18);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand18)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand13);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand19)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand20);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand20)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand21);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand21)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand22);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand22)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand23);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand23)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand24);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand24)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand19);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand25)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand26);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand26)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand27);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand27)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand28);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand28)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand29);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand29)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand30);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand30)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand25);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand31)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand32);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand32)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand33);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand33)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand34);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand34)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand35);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand35)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand36);
						Tob.onAcid = false;
					} else if (ctx.pathing.onTile(xarpusMeleeStand36)) {
						Tob.status = "Dodging Xarpus Acid";
						ctx.log("Dodging Xarpus Acid");
						ctx.pathing.step(xarpusMeleeStand31);
						Tob.onAcid = false;
					}
				}
				if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand1) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand2);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand2) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand3);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand3) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand4);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand4) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand5);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand5) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand6);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand6) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand1);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand7) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand8);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand8) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand9);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand9) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand10);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand10) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand11);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand11) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand12);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand12) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand7);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand13) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand14);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand14) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand15);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand15) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand16);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand16) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand17);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand17) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand18);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand18) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand13);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand19) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand20);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand20) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand21);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand21) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand22);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand22) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand23);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand23) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand24);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand24) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand19);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand25) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand26);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand26) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand27);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand27) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand28);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand28) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand29);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand29) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand30);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand30) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand25);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand31) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand32);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand32) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand33);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand33) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand34);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand34) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand35);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand35) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand36);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusRangeStand36) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusRangeStand31);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand1) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand2);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand2) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand3);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand3) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand4);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand4) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand5);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand5) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand6);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand6) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand1);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand7) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand8);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand8) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand9);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand9) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand10);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand10) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand11);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand11) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand12);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand12) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand7);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand13) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand14);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand14) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand15);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand15) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand16);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand16) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand17);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand17) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand18);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand18) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand13);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand19) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand20);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand20) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand21);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand21) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand22);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand22) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand23);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand23) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand24);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand24) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand19);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand25) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand26);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand26) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand27);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand27) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand28);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand28) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand29);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand29) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand30);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand30) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand25);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand31) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand32);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand32) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand33);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand33) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand34);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand34) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand35);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand35) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand36);
				} else if (xarpus.isAnimating() && ctx.pathing.onTile(xarpusMeleeStand36) && xarpus.getInteracting().equals(ctx.players.getLocal())) {
					Tob.status = "Dodging Xarpus acid";
					ctx.log("Dodging Xarpus acid");
					ctx.pathing.step(xarpusMeleeStand31);
				}
			} else {
				Tob.status = "Pathing to barrier";
				ctx.log("Pathing to barrier");
				ctx.prayers.disableAll();
				ctx.pathing.step(xarpusExit);
				ctx.onCondition(() -> ctx.pathing.distanceTo(xarpusExit) <= 5, 250, 32);
				if (barrier != null && ctx.players.getLocal().getLocation().distanceTo(barrier.getLocation()) <= 5) {
					barrier.interact(502);
					ctx.onCondition(() -> Util.ROOM_FIVE_EXIT.containsPoint(ctx.players.getLocal()), 250, 24);
				}
			}
			if (ctx.players.getLocal().getInteracting() == null && xarpus != null && xarpus.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 8) {
				xarpus.interact(SimpleNpcActions.ATTACK);
				ctx.onCondition(() -> xarpus.isAnimating(), 200, 1);
			}
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.dropVial();
			Util.useSpecialBP();
		} else if (Util.ROOM_FIVE_EXIT.containsPoint(ctx.players.getLocal())) {
			SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
			SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
			SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
			SimpleItem prims = ctx.inventory.populate().filter(13239).next();
			SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
			SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
			SimpleItem torture = ctx.inventory.populate().filter(19553).next();
			SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
			SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
			SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
			SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
			SimpleItem defender = ctx.inventory.populate().filterContains("defender").next();
			SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
			SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
			if ((System.currentTimeMillis() - Tob.lastOverload) >= 240000) {
				Tob.lastOverload = (System.currentTimeMillis() - 605000);
				ctx.sleep(600);
				Util.handleRangePotion();
				Tob.lastOverload = System.currentTimeMillis();
			}
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
				if (dragonboots != null) {
					dragonboots.interact(SimpleItemActions.WEAR);
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
				if (defender != null) {
					defender.interact(SimpleItemActions.WEAR);
				}
			}
			SimpleGameObject exit = (SimpleGameObject) ctx.objects.populate().filter("Door").nearest().next();
			SimpleGameObject chest = (SimpleGameObject) ctx.objects.populate().filter("Chest").nearest().next();
			if (chest != null && Tob.restock && ctx.widgets.getOpenInterfaceId() != 21490 && ctx.inventory.getFreeSlots() > 1) {
				chest.interact(502);
				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 21490, 250, 8);
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
			if (exit != null && (!Tob.restock || ctx.inventory.getFreeSlots() <= 1)) {
				ctx.prayers.disableAll();
				Tob.status = "Afking for restocking";
				ctx.log("Afking for restocking");
				ctx.onCondition(() -> Util.ROOM_SIX.containsPoint(ctx.players.getLocal()), 250, 1);
				Tob.status = "Entering Verzik";
				ctx.log("Entering Verzik");
				exit.interact(502);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 2459 || Util.ROOM_SIX.containsPoint(ctx.players.getLocal()), 250, 8);
			}
			if (ctx.widgets.getBackDialogId() == 2459) {
				ctx.menuActions.sendAction(315, -1, -1, 2461);
				ctx.onCondition(() -> Util.ROOM_SIX.containsPoint(ctx.players.getLocal()), 250, 3);
			}
		}
	}
	
	
	public static WorldPoint getAcidSpots() {
		final int radiusX = 1;
		final int radiusY = 1;
		final WorldPoint myPoint = ctx.players.getLocal().getLocation();
		if (Tob.acidTiles.contains(myPoint)) {
			WorldPoint tempPoint;
			final List<WorldPoint> validPoints = new ArrayList<>();
			for (int x = -radiusX; x <= radiusX; x++) {
				for (int y = -radiusY; y <= radiusY; y++) {
					tempPoint = myPoint.derrive(x, y);
					if (Tob.acidTiles.contains(tempPoint)) {
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
	
	public final static SimpleEntityQuery<SimpleNpc> xarpus() {
		return ctx.npcs.populate().filter(8340).filter(n -> {
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
