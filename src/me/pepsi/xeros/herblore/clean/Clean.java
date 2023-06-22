package me.pepsi.xeros.herblore.clean;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;
import java.awt.*;

@ScriptManifest(author = "Pepsiplaya", name = "Cleans Torstols", category = Category.HERBLORE, version = "1.0D", description = "", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Clean extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	@Override
	public boolean onExecute() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onProcess() {
		SimpleItem reverseGrimyTorstol = ctx.inventory.populate().reverse().filter(219).next();
		int torstols = ctx.inventory.populate().filter(219).population(true);
		if (reverseGrimyTorstol != null) {
			if (reverseGrimyTorstol != null) {
				reverseGrimyTorstol.interact(74);
			}
		}
		if (torstols <= 4) {
			ctx.quickGear.latest();
		}
	}

	@Override
	public void onTerminate() {
	}

	@Override
	public void onPaint(Graphics2D g) {
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessage().contains("Preset equipment item not in bank") || event.getMessage().contains("Preset inventory item not in bank")) {
			ctx.log("OUT OF SUPPLIES");
			ctx.stopScript();
		} else if (event.getMessage().contains("You need to be near a bank to use")) {
			ctx.log("TPED/MOVED?");
			ctx.sleep(3000, 5000);
			ctx.keyboard.sendKeys("?");
			ctx.stopScript();
		}
	}

	@Override
	public int loopDuration() {
		return 50;
	}
}