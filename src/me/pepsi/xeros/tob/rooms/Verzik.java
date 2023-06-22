package me.pepsi.xeros.tob.rooms;

import me.pepsi.xeros.tob.Tob;
import me.pepsi.xeros.tob.util.Util;
import simple.api.ClientContext;
import simple.api.actions.SimpleNpcActions;
import simple.api.filters.SimplePrayers;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleNpc;

public class Verzik {
	static ClientContext ctx = ClientContext.instance();
	public static boolean verzikRangeMove = false;
	
	public static void roomSix() {
		SimpleNpc mv8 = verzik().nearest().next();
		SimpleNpc verzik = mv8 != null ? mv8 : verzik().nearest().next();
		SimpleNpc mv9 = verzik2().nearest().next();
		SimpleNpc verzik2 = mv9 != null ? mv9 : verzik2().nearest().next();
		if (Util.ROOM_SIX.containsPoint(ctx.players.getLocal())) {
			Util.handleEatingFood();
			Util.handleDrinkingPrayer();
			Util.handleRangePotion();
			Util.dropVial();
			if (verzik != null) {
				if (verzik.isAnimating() && !ctx.pathing.inMotion()) {
					int move = (int) (Math.random() * 20);
					Tob.status = "Dodging Verzik attack";
					ctx.log("Dodging Verzik attack");
					if (move >= 0 && move < 14) {
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -1));
					} else if (move >= 14 && move <= 16) {
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(1, 0));
					} else {
						ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(-1, 0));
					}
					ctx.onCondition(() -> !verzik.isAnimating(), 250, 2);
				}
				Util.dwhSpecial();
				Util.useSpecialBP();
				if (ctx.players.getLocal().getInteracting() == null) {
					Tob.status = "Attacking Verzik";
					ctx.log("Attacking Verzik");
					verzik.interact(SimpleNpcActions.ATTACK);
				}
				if (!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) {
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
			} else if (verzik2 != null) {
				if (!ctx.equipment.populate().filter(20997).isEmpty() || !ctx.equipment.populate().filter(12926).isEmpty()) {
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
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				/*if (verzik2.getAnimation() == 8126) {
					Tob.status = "Dodge flower attack";
					ctx.log("Dodge flower attack");
					Toolkit.getDefaultToolkit().beep();
					ctx.sleep(1000);
					SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
					if (brew != null && ctx.combat.health() <= 95) {
						Tob.status = "Overhealing";
						ctx.log("Overhealing");
						brew.interact(74);
					}
				}*/
				Util.useSpecialDDS();
				Util.useSpecialBP();
				if (Tob.yellowTile.size() <= 0 && ctx.players.getLocal().getInteracting() == null && (!ctx.pathing.inMotion() && (!verzik2.isAnimating() || verzik2.getLocation().distanceTo(ctx.players.getLocal().getLocation()) <= 3))) {
					verzik2.interact(SimpleNpcActions.ATTACK);
					ctx.log("Attacking Verzik");
					Tob.status = "Attacking Verzik";
				}
			} else {
				SimpleGameObject exit = (SimpleGameObject) ctx.objects.populate().filter("Treasure room").nearest().next();
				if (exit != null) {
					Tob.status = "Pathing to exit";
					ctx.log("Pathing to exit");
					if (exit != null) {
						exit.interact(502);
						ctx.prayers.disableAll();
						ctx.onCondition(() -> Util.TREASURE_ROOM.containsPoint(ctx.players.getLocal()), 250, 16);
					}
				}
			}
		}
	}

	public final static SimpleEntityQuery<SimpleNpc> verzik() {
		return ctx.npcs.populate().filter(8372).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> verzik2() {
		return ctx.npcs.populate().filter(8374).filter(n -> {
			if (n == null) {
				return false;
			}
			return true;
		});
	}
}
