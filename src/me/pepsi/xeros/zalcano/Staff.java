package me.pepsi.xeros.zalcano;

import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
    		Zalcano.staff = true;
    		//ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!Zalcano.HOME.containsPoint(ctx.players.getLocal()) && !Zalcano.ZALCANO_WAITING.containsPoint(ctx.players.getLocal())) {
    			//Toolkit.getDefaultToolkit().beep();
    		}
    	} else {
    		Zalcano.staff = false;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
