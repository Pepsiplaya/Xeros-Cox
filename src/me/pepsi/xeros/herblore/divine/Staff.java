package me.pepsi.xeros.herblore.divine;

import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		Super.started = false;
    	} else {
    		Super.started = true;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
