package me.pepsi.xeros.thieving;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleNpc;
import simple.api.actions.SimpleNpcActions;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Master Farmer", category = Category.THIEVING, version = "1.0D",
        description = "Empty inventory, start near farmer.", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class MasterFarmer extends Script implements SimplePaintable, SimpleMessageListener {
    
    @Override
    public boolean onExecute() {
        return true;
    }
    
    @Override
    public void onProcess() {
    	SimpleNpc farmer = ctx.npcs.populate().filter(5730).next();
    	if (farmer != null) {
    		farmer.interact(SimpleNpcActions.INTERACT);
    		ctx.sleep(1, 1800); 
    	}
    }

    @Override
    public void onTerminate() {
    	
    }
    
	@Override
    public void onPaint(Graphics2D g) {
		
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	if ((event.getMessageType() == 2 || event.getMessageType() == 1)) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
}