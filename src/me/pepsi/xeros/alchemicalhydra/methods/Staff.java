package me.pepsi.xeros.alchemicalhydra.methods;

import java.awt.Toolkit;

import me.pepsi.xeros.alchemicalhydra.core.AlchemicalHydra;
import simple.api.ClientContext;
import simple.api.wrappers.SimplePlayer;

public class Staff {

	final static ClientContext ctx = ClientContext.instance();
	
    public static void antiStaff() {
    	if (getNearbyStaff() != null && !AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal())) {
    		AlchemicalHydra.staff = true;
    		AlchemicalHydra.antibot = "STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal());
    		ctx.log("STAFF NEARBY: " + getNearbyStaff().getName().toString() + " | " + getNearbyStaff().distanceTo(ctx.players.getLocal()));
    		if (!AlchemicalHydra.HOME.containsPoint(ctx.players.getLocal())) {
    		    Toolkit.getDefaultToolkit().beep();
    		}
    	} else {
    		AlchemicalHydra.staff = false;
    		AlchemicalHydra.antibot = "No Staff Nearby";
    	}
    }
    
    public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}
    
    public static void getNearbyPlayers() {
    	if (getnearbyPlayers() != null) {
    		AlchemicalHydra.staff = true;
    		AlchemicalHydra.antibot = "STAFF NEARBY: " + getnearbyPlayers().getName().toString() + " | " + getnearbyPlayers().distanceTo(ctx.players.getLocal());
    	}
    }
    
    static SimplePlayer getnearbyPlayers() {
    	if (ctx.players.nearest().next() != null) {
    		if (ctx.players.nearest().next().getName() == ctx.players.getLocal().getName()) {
    			return null;
    		} else {
    			return ctx.players.nearest().next();	
    		}
    	} else {
    		return null;
    	}
	}
}
