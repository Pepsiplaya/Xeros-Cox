package me.pepsi.xeros.zulrah.methods;

import java.awt.Toolkit;
import java.util.Date;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimplePlayer;

public class Staff {
	
	public static final WorldArea HOME = new WorldArea(
            new WorldPoint(3049, 3540, 0),
            new WorldPoint(3047, 3405, 0),
            new WorldPoint(3146, 3404, 0),
            new WorldPoint(3145, 3539, 0)
    );
	
	public static final WorldArea ZULRAH = new WorldArea(
            new WorldPoint(2259, 3066, 0),
            new WorldPoint(2279, 3080, 0)
    );
	
	public static SimplePlayer getNearbyStaff(ClientContext ctx) {
		return ctx.antiban.nearbyStaff().nearest().next();
	}

    public static void antiStaff(ClientContext ctx) {
    	long startTime = System.currentTimeMillis();
    	long elapsedTime = 0L;
    	SimplePlayer staff = ctx.antiban.nearbyStaff().nearest().next();
    	if (ctx.dialogue.dialogueOpen()) {
    		Toolkit.getDefaultToolkit().beep();
    		return;
    	}
    	if (getNearbyStaff(ctx) != null) {
    		Zulrah.antibot = "STAFF: " + staff.getName() + " | " + staff.distanceTo(ctx.players.getLocal());
    		ctx.log(staff.getName() + " is nearby | " + staff.distanceTo(ctx.players.getLocal()));
    		if (ZULRAH.containsPoint(ctx.players.getLocal())) {
    			ctx.magic.castHomeTeleport();
    			ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 6);
    			ctx.prayers.disableAll();
    			Banking.loadPreset(ctx);
    			ctx.log("STAFF INSIDE ZULRAH INSTANCE PANIC STOP");
    			ctx.sleep(750);
    			ctx.stopScript();
    		}
    	} else {
    		Zulrah.antibot = "No Staff Nearby";
    	}
    	if (ctx.players.getLocal().getLocation().getRegionID() == 8751) {
    		if (getNearbyStaff(ctx) != null) {
    			SimpleGameObject bank = (SimpleGameObject) ctx.objects.populate().filterContains("Bank").nearest().next();
    			if (bank != null) {
    	    		ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 8); // Waiting until i'm not animating
    				bank.interact(SimpleObjectActions.FIRST);
    				ctx.onCondition(() -> ctx.bank.openBank(), 250, 12);
    			}
    			while (elapsedTime < 1*60*1000) {
    	    	    elapsedTime = (new Date()).getTime() - startTime;
    	    	    Zulrah.antibot = "Staff check every min (" + ctx.paint.formatTime(elapsedTime) + ")";
    	    	}
    		}
    	}
    }
}
