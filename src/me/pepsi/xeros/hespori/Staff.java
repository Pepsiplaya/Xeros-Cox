package me.pepsi.xeros.hespori;

import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
    		Hespori.staff = true;
    		//ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!Hespori.HOME.containsPoint(ctx.players.getLocal()) && !Hespori.HESPORI_WAITING.containsPoint(ctx.players.getLocal())) {
    			//Toolkit.getDefaultToolkit().beep();
    		}
    	} else {
    		Hespori.staff = false;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
