package me.pepsi.xeros.herblore.divine;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.actions.SimpleItemActions;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;
import java.awt.*;

@ScriptManifest(author = "Pepsiplaya", name = "Makes Super Combats", category = Category.HERBLORE, version = "1.0D", description = "", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Super extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	public static boolean preset = false;
	public static boolean started;
	
	@Override
	public boolean onExecute() {
		started = true;
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onProcess() {
		Staff.antiStaff();
		if (started) {
			SimpleItem torstolPotion = ctx.inventory.populate().filter(111).next();
			SimpleItem strPotion = ctx.inventory.populate().filter(2440).next();
			if (torstolPotion != null && strPotion != null) {
				torstolPotion.interact(SimpleItemActions.USE);
				strPotion.interact(SimpleItemActions.USE_WITH);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 27510, 250, 3);
				if (ctx.widgets.getBackDialogId() == 27510) {
					ctx.menuActions.sendAction(646, 0, 0, 27523);
					ctx.onCondition(() -> preset == true, 250, 100);
				}
			}
			if (preset) {
				ctx.quickGear.latest();
				preset = false;
			}
		}
	}

	@Override
	public void onTerminate() {
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
				ctx.log("OUT OF SUPPLIES");
				ctx.stopScript();
			} else if (event.getMessage().contains("You need to be near a bank to use")) {
				ctx.log("TPED/MOVED?");
				ctx.sleep(3000, 5000);
				ctx.keyboard.sendKeys("?");
				ctx.stopScript();
			} else if (event.getMessage().contains("You have run out of")) {
				preset = true;
			}
		}
		if (event.getMessageType() == 7) {
			Toolkit.getDefaultToolkit().beep();
			ctx.log(event.getSender() + ": " + event.getMessage());
			ctx.stopScript();
		}
		if (event.getMessageType() == 2 || event.getMessageType() == 1) {
			ctx.log(event.getSender() + ": " + event.getMessage());
		}
	}

	@Override
	public int loopDuration() {
		return 300;
	}
}