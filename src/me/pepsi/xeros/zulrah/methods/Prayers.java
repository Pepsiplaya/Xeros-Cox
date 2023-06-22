package me.pepsi.xeros.zulrah.methods;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleNpc;

public class Prayers {
	
	final static WorldPoint zulrahNorth = new WorldPoint(2268, 3074, 0);
	
	public final static SimpleEntityQuery<SimpleNpc> range(ClientContext ctx) {
		return ctx.npcs.populate().filter(2042).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> melee(ClientContext ctx) {
		return ctx.npcs.populate().filter(2043).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> mage(ClientContext ctx) {
		return ctx.npcs.populate().filter(2044).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
    public static void pray(ClientContext ctx) {
    	SimpleNpc mv = range(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc range = mv != null ? mv : range(ctx).nearest().next();
		SimpleNpc mv2 = melee(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc melee = mv2 != null ? mv2 : melee(ctx).nearest().next();
		SimpleNpc mv3 = mage(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc mage = mv3 != null ? mv3 : mage(ctx).nearest().next();
    	if (range != null) {
    		if (Zulrah.prayerType == -2) {
    			if (!range.getLocation().equals(zulrahNorth)) {
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
        			}
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
        			}	
    			} else {
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
        			}	
    			}
    		} else {
    			if (!range.getLocation().equals(zulrahNorth)) {
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
    					ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
    				}
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
    					ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
    				}
    			} else {
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
        			}	
    			}
    		}
    	} else if (melee != null) {
    		if (Zulrah.prayerType == -2) {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
    			}
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
    			}
    		} else {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MELEE)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MELEE, true);
    			}
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
    			}
    		}
    	} else if (mage != null) {
    		//SimpleProjectile rangeProjectile = ctx.projectiles.populate().filter(1044).next();
    		//SimpleProjectile mageProjectile = ctx.projectiles.populate().filter(1046).next();
    		if (Zulrah.prayerType == -2) {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
    			}
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
    			}
    		} else {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
    			}
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
    			}
    			/*if (mageProjectile != null) {
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
        			}
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
        			}
    			} else if (rangeProjectile != null) {
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
        			}
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
        			}
    			}
    		} else {
    			if (mageProjectile != null) {
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
        			}
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
        			}
    			} else if (rangeProjectile != null) {
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MISSILES)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MISSILES, true);
        			}
        			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
        				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
        			}
    			}*/
    		}
    		/*if (Zulrah.prayerType == -2) {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
        			ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
        		}
        		if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
        			ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
        		}
    		} else {
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PROTECT_FROM_MAGIC)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.PROTECT_FROM_MAGIC, true);
    			}
    			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
    				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
    			}
    		}*/
    	}
    }
}
