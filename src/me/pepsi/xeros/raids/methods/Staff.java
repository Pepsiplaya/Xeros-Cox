package me.pepsi.xeros.raids.methods;

import java.awt.Toolkit;
import java.util.Date;

import me.pepsi.xeros.raids.Raids;
import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null) {
    		long startTime = System.currentTimeMillis();
        	long elapsedTime = 0L;
    		if (Rooms.WAITING_AREA.containsPoint(ctx.players.getLocal())) {
    			while (elapsedTime < 1*60*1000) {
    	    	    elapsedTime = (new Date()).getTime() - startTime;
    	    	    Raids.antibot = "Staff check every min (" + ctx.paint.formatTime(elapsedTime) + ")";
    	    	    return;
    			}
    		}
    		Raids.staff = true;
    		Raids.antibot = "STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal());
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!Rooms.HOME_AREA.containsPoint(ctx.players.getLocal()) && !Rooms.WAITING_AREA.containsPoint(ctx.players.getLocal())) {
    			if (Raids.staffBeep) {
        		    Toolkit.getDefaultToolkit().beep();
    			}
    		}
    	} else {
    		Raids.antibot = "No Staff Nearby";
    		Raids.staff = false;
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
