package me.pepsi.xeros.shop;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleNpc;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Shop", category = Category.OTHER, version = "1.0D",
        description = "Helps with quest", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class Quest extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

    @Override
    public boolean onExecute() {
	        return true;
    }

    @Override
    public void onProcess() {
    	SimpleNpc shop = ctx.npcs.populate().filter(5809).nearest().next();
    	if (!ctx.inventory.inventoryFull()) {
    		if (shop != null && ctx.widgets.getOpenInterfaceId() != 35426) {
    			ctx.log("Opening Shop");
        		shop.interact(225);
        		ctx.sleep(600);
        	} else {
        		ctx.log("Buying Gems");
        		ctx.menuActions.sendAction(53, 1615, 14, 35468);
        		ctx.sleep(200);
        	}
    	} else {
    		if (ctx.widgets.getOpenInterfaceId() == 35426) {
    			ctx.menuActions.sendAction(200, 1619, 0, 37302);
    		} else {
    			ctx.log("Depositing Inventory");
    			ctx.sleep(250);
    			ctx.quickGear.latest();
    		}
    	}
    }

    @Override
    public void onTerminate() {
    	
    }
    
    @Override
	public int loopDuration() {
		return 150;
	}
    
	@Override
    public void onPaint(Graphics2D g) {
		
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	
    }
}