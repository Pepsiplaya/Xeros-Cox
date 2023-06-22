package me.pepsi.xeros.fletching;

import simple.api.script.Category;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.actions.SimpleItemActions;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;


@ScriptManifest(author = "Pepsiplaya", name = "Headless Arrows", category = Category.FLETCHING, version = "1.0D",
        description = "Have both arrow shafts and feathers in inventory.", discord = "Pepsiplaya#6988", servers = { "Xeros" })

public class HeadlessArrows extends Script implements SimplePaintable, SimpleMessageListener {
    
    @Override
    public boolean onExecute() {
        return true;
    }
    
    @Override
    public void onProcess() {
    	SimpleItem arrowShaft = ctx.inventory.populate().filter(52).next();
    	SimpleItem feather = ctx.inventory.populate().filter(314).next();
    	if (arrowShaft != null && feather != null) {
        	arrowShaft.interact(SimpleItemActions.USE_WITH);
        	feather.interact(SimpleItemActions.USE);	
    	} else {
    		ctx.stopScript();
    	}
    }

    @Override
    public void onTerminate() {
    	
    }
    
	@Override
    public void onPaint(Graphics2D g) {
		
    }
	
    public void onChatMessage(ChatMessageEvent event) {
    	
    }
}