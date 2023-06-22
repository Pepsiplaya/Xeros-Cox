package me.pepsi.xeros.tob.util;

import java.awt.Toolkit;

import me.pepsi.xeros.tob.Tob;
import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
    		Tob.staff = true;
    		Tob.antibot = "STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal());
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!Util.HOME_AREA.containsPoint(ctx.players.getLocal()) && !Util.WAITING_ROOM.containsPoint(ctx.players.getLocal())) {
    			if (Tob.staffBeep) {
        		    Toolkit.getDefaultToolkit().beep();
    			}
    		}
    	} else {
    		Tob.antibot = "No Staff Nearby";
    		Tob.staff = false;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
