package me.pepsi.xeros.pkassist;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimplePlayer;
import simple.api.ClientContext;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;

@ScriptManifest(author = "Pepsiplaya", name = "[P] Xeros Pk Helper", category = Category.OTHER, version = "1.0D", description = "Kills Vorkath for GP", discord = "Pepsiplaya#6988", servers = {
		"Runewild" })

public class PK extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {
	final static ClientContext ctx = ClientContext.instance();
	
	public PK() {
		
	}
	
	@Override
	public boolean onExecute() {
		return true;
	}

	@Override
	public void onProcess() {

	}

	@Override
	public void onTerminate() {
	}
	
	public static SimplePlayer getNearbyStaff() {
		return ctx.antiban.nearbyStaff().nearest().next();
	}

	@Override
	public void onPaint(Graphics2D g) {
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			
		}
	}

	@Override
	public int loopDuration() {
		return 100;
	}
}