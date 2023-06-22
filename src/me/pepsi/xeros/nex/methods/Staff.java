package me.pepsi.xeros.nex.methods;

import me.pepsi.xeros.nex.core.Nex;
import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff(ctx) != null) {
    		Nex.status = "STAFF NEARBY: " + getNearbyStaff(ctx).getName().toString();
    		//Toolkit.getDefaultToolkit().beep();
    	}
    }
    public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
}
