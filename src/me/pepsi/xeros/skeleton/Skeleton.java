package me.pepsi.xeros.skeleton;

import me.pepsi.xeros.cerberus.core.Cerberus;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import javax.swing.SwingUtilities;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Script Name Here", category = Category.OTHER, version = "1.0D", description = "Description Here", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Skeleton extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {


	private static int prayerType;
	public static boolean started;
	public static boolean staff;
	public GUI gui;

	public static final WorldArea HOME = new WorldArea(
			new WorldPoint(3065, 3460, 0),
			new WorldPoint(3125, 3520, 0)
	);

	@Override
	public boolean onExecute() {
		try {
			Skeleton script = this;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gui = new GUI(script, ctx);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onProcess() {
		Prayers();
	}

	private void Prayers() {
		if (prayerType == -2 && !HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.RIGOUR)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.RIGOUR, true);
			}
		} else if (prayerType == -1 && !HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.EAGLE_EYE)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.EAGLE_EYE, true);
			}
		} else if (prayerType == -3 && !HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
				ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
			}
		}
	}

	public void setupPrayer(int prayerType) {
		Skeleton.prayerType = prayerType;
	}

	@Override
	public void onTerminate() {
		if (gui != null) {
			GUI.frame.setVisible(false);
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		
	}

	public void onChatMessage(ChatMessageEvent event) {
	}

	@Override
	public int loopDuration() {
		return 150;
	}
}