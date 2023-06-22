package me.pepsi.xeros.zulrah.methods;

import me.pepsi.xeros.zulrah.core.Zulrah;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleWallObject;
import simple.api.wrappers.SimpleWidget;

public class Sell {
	
    public static void tradingPost(ClientContext ctx) {
    	SimpleWallObject tp = (SimpleWallObject) ctx.objects.populate().filterContains("Trading Post").nearest().next();
    	SimpleItem magicFang = ctx.inventory.populate().filter(12932).next();
    	SimpleItem tanzanite = ctx.inventory.populate().filter(12922).next();
    	SimpleItem serp = ctx.inventory.populate().filter(12927).next();
    	SimpleItem onyx = ctx.inventory.populate().filter(6571).next();
    	SimpleWidget priceWidget = ctx.widgets.populate().filter(48964).next();
    	
    	if (tp != null) {
    		if (ctx.widgets.getOpenInterfaceId() != 48600 && ctx.widgets.getOpenInterfaceId() != 48599 && ctx.widgets.getOpenInterfaceId() != 48598) {
    			tp.interact(SimpleObjectActions.SECOND);
    			ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48600, 250, 24);
    		} else if (ctx.widgets.getOpenInterfaceId() == 48600) {
				ctx.menuActions.sendAction(315, -1, -1, 48621);
				ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48599, 250, 8);
			} else if (ctx.widgets.getOpenInterfaceId() == 48599) {
				if (magicFang != null) {
					ctx.menuActions.sendAction(431, 12932, -1, 48501);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48598, 250, 8);
				} else if (tanzanite != null) {
					if (tanzanite != null) {
						ctx.menuActions.sendAction(431, 12922, -1, 48501);
						ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48598, 250, 8);
					}
				} else if (serp != null) {
					if (serp != null) {
						ctx.menuActions.sendAction(431, 12927, -1, 48501);
						ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48598, 250, 8);
					}
				} else if (onyx != null) {
					if (onyx != null) {
						ctx.menuActions.sendAction(431, 6571, -1, 48501);
						ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48598, 250, 8);
					}
				}
			} else if (ctx.widgets.getOpenInterfaceId() == 48598) {
				ctx.menuActions.sendAction(315, -1, -1, 48968);
				ctx.sleep(1500);
				if (magicFang != null) {
					ctx.keyboard.sendKeys(Zulrah.magicFang);
					ctx.onCondition(() -> priceWidget.getText().equals("Price (each): " + Zulrah.magicFang.replace("m", ",000,000")), 250, 8);
					ctx.log("Price (each): " + Zulrah.magicFang.replace("m", ",000,000"));
	    			if (priceWidget.getText().equals("Price (each): " + Zulrah.magicFang.replace("m", ",000,000"))) {
	    				ctx.menuActions.sendAction(315, -1, -1, 48974);
	    				ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    				ctx.menuActions.sendAction(315, -1, -1, 2461);
	    			}
				} else if (tanzanite != null) {
					ctx.keyboard.sendKeys(Zulrah.tanz);
					ctx.onCondition(() -> priceWidget.getText().equals("Price (each): " + Zulrah.tanz.replace("m", ",000,000")), 250, 8);
	    			if (priceWidget.getText().equals("Price (each): " + Zulrah.tanz.replace("m", ",000,000"))) {
	    				ctx.menuActions.sendAction(315, -1, -1, 48974);
	    				ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    				ctx.menuActions.sendAction(315, -1, -1, 2461);
	    			}
				} else if (serp != null) {
					ctx.keyboard.sendKeys(Zulrah.serp);
					ctx.onCondition(() -> priceWidget.getText().equals("Price (each): " + Zulrah.serp.replace("m", ",000,000")), 250, 8);
	    			if (priceWidget.getText().equals("Price (each): " + Zulrah.serp.replace("m", ",000,000"))) {
	    				ctx.menuActions.sendAction(315, -1, -1, 48974);
	    				ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    				ctx.menuActions.sendAction(315, -1, -1, 2461);
	    			}
				} else if (onyx != null) {
					ctx.keyboard.sendKeys(Zulrah.onyx);
					ctx.onCondition(() -> priceWidget.getText().equals("Price (each): " + Zulrah.onyx.replace("m", ",000,000")), 250, 8);
	    			if (priceWidget.getText().equals("Price (each): " + Zulrah.onyx.replace("m", ",000,000"))) {
	    				ctx.menuActions.sendAction(315, -1, -1, 48974);
	    				ctx.onCondition(() -> ctx.dialogue.dialogueOpen(), 250, 8);
	    				ctx.menuActions.sendAction(315, -1, -1, 2461);
	    			}
				}
    		}
    	}
    }
}
