package me.pepsi.xeros.cerberus.methods;

import java.awt.Toolkit;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (ctx.dialogue.dialogueOpen() && Cerberus.CERBERUS.containsPoint(ctx.players.getLocal())) {
    		Cerberus.started = false;
    		Toolkit.getDefaultToolkit().beep();
    		ctx.stopScript();
    	}
    	if (getNearbyStaff() != null) {
    		Cerberus.staff = true;
    		Cerberus.antibot = "STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal());
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!Cerberus.HOME.containsPoint(ctx.players.getLocal())) {
    			Toolkit.getDefaultToolkit().beep();
    		}
    	} else {
    		Cerberus.staff = false;
    		Cerberus.antibot = "No Staff Nearby";
    	}
    }
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
