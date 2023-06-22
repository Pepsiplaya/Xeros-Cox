package me.pepsi.xeros.tob.util;

import me.pepsi.xeros.tob.Tob;
import simple.api.ClientContext;
import simple.api.filters.SimplePrayers;

public class Prayers {
	final static ClientContext ctx = ClientContext.instance();
	
	static void protectMelee() {
		if (ctx.prayers.points() > 0) {
			if (Tob.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					Tob.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
				}
			}
		}
	}

	static void protectRange() {
		if (ctx.prayers.points() > 0) {
			if (Tob.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					Tob.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
				}
			}
		}
	}

	static void protectMagic() {
		if (ctx.prayers.points() > 0) {
			if (Tob.rangeType == -2) {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					Tob.status = "Turning On Prayers";
					ctx.log("Turning On Prayers");
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
				}
			} else {
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
					Tob.status = "Turning On Prayers";
					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
				}
			}
		}
	}
}
