package me.pepsi.xeros.skeleton;

import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
			Skeleton.staff = true;
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    	} else {
			Skeleton.staff = false;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
