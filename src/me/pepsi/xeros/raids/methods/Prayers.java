package me.pepsi.xeros.raids.methods;

import me.pepsi.xeros.raids.Raids;
import simple.api.ClientContext;
import simple.api.filters.SimplePrayers;

public class Prayers {
	final static ClientContext ctx = ClientContext.instance();
	
	static void protectMelee() {
		if (ctx.prayers.points() > 0) {
			if (Raids.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					Raids.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					Raids.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				}
			}
		}
	}

	static void protectRange() {
		if (ctx.prayers.points() > 0) {
			if (Raids.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					Raids.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					Raids.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				}
			}
		}
	}

	static void protectMagic() {
		if (ctx.prayers.points() > 0) {
			if (Raids.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					Raids.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
					}
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					Raids.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if (ctx.equipment.populate().filter(22325).next()  != null) {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
					}
				} else {
					if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
						Raids.status = "Turning On Prayers";
						ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
					}
				}
			}
		}
	}
}
