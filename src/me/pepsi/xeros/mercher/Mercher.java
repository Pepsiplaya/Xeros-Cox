package me.pepsi.xeros.mercher;

import simple.api.script.Category;
import simple.api.script.LoopingScript;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleWidget;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Script;

import java.awt.*;
import javax.swing.SwingUtilities;

@ScriptManifest(author = "Pepsiplaya", name = "Mercher", category = Category.MONEYMAKING, version = "1.0D", description = "Merches items you select", discord = "Pepsiplaya#6988", servers = {
		"Xeros" })

public class Mercher extends Script implements LoopingScript, SimplePaintable, SimpleMessageListener {

	private String status;
	private long startTime;
	public static int stage = 0;
	public static boolean started = false;
	public static String itemBuy1;
	public static String itemBuy2;
	public static String itemBuy3;
	public static String itemBuy4;
	public static String itemBuy5;
	public static String itemBuy6;
	public static String itemBuy7;
	public static String itemBuy8;
	public static String itemBuy9;
	public static String itemBuy10;
	public static String itemBuy11;
	public static String itemBuy12;
	public static String itemBuy13;
	public static String itemBuy14;
	public static String itemBuy15;
	public static String itemBuy16;
	public static String itemBuy17;
	public static String itemBuy18;
	public static String itemBuy19;
	public static String itemBuy20;
	public static String itemBuy;
	public static int itemPriceBuy;
	public static int itemPriceSell;
	public static int itemPriceBuy1;
	public static int itemPriceSell1;
	public static int itemPriceBuy2;
	public static int itemPriceSell2;
	public static int itemPriceBuy3;
	public static int itemPriceSell3;
	public static int itemPriceBuy4;
	public static int itemPriceSell4;
	public static int itemPriceBuy5;
	public static int itemPriceSell5;
	public static int itemPriceBuy6;
	public static int itemPriceSell6;
	public static int itemPriceBuy7;
	public static int itemPriceSell7;
	public static int itemPriceBuy8;
	public static int itemPriceSell8;
	public static int itemPriceBuy9;
	public static int itemPriceSell9;
	public static int itemPriceBuy10;
	public static int itemPriceSell10;
	public static int itemPriceBuy11;
	public static int itemPriceSell11;
	public static int itemPriceBuy12;
	public static int itemPriceSell12;
	public static int itemPriceBuy13;
	public static int itemPriceSell13;
	public static int itemPriceBuy14;
	public static int itemPriceSell14;
	public static int itemPriceBuy15;
	public static int itemPriceSell15;
	public static int itemPriceBuy16;
	public static int itemPriceSell16;
	public static int itemPriceBuy17;
	public static int itemPriceSell17;
	public static int itemPriceBuy18;
	public static int itemPriceSell18;
	public static int itemPriceBuy19;
	public static int itemPriceSell19;
	public static int itemPriceBuy20;
	public static int itemPriceSell20;
	private final WorldPoint bank = new WorldPoint(3096, 3492, 0);

	public GUI gui;

	@Override
	public boolean onExecute() {
		started = false;
		status = "Configure GUI";
		startTime = System.currentTimeMillis();
		try {
			Mercher script = this;
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
		if (!started) {
			return;
		}
		if (ctx.widgets.getOpenInterfaceId() == 48600 || ctx.widgets.getOpenInterfaceId() == 48000
				|| ctx.widgets.getOpenInterfaceId() == 48599 || ctx.widgets.getOpenInterfaceId() == 48598) {
			SimpleWidget priceWidget1 = ctx.widgets.populate().filter(26024).next();
			String price1 = priceWidget1.getText().replaceAll(",", "").replaceAll(" each", "");
			if (ctx.widgets.getOpenInterfaceId() == 48600 && itemPriceBuy1 != 0) {
				if (stage == 1 && itemPriceBuy1 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy1;
					ctx.log("Searching for " + itemBuy1);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy1);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 2 && itemPriceBuy2 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy2;
					ctx.log("Searching for " + itemBuy2);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy2);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 3 && itemPriceBuy3 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy3;
					ctx.log("Searching for " + itemBuy3);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy3);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 4 && itemPriceBuy4 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy4;
					ctx.log("Searching for " + itemBuy4);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy4);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 5 && itemPriceBuy5 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy5;
					ctx.log("Searching for " + itemBuy5);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy5);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 6 && itemPriceBuy6 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy6;
					ctx.log("Searching for " + itemBuy6);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy6);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 7 && itemPriceBuy7 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy7;
					ctx.log("Searching for " + itemBuy7);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy7);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 8 && itemPriceBuy8 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy8;
					ctx.log("Searching for " + itemBuy8);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy8);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 9 && itemPriceBuy9 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy9;
					ctx.log("Searching for " + itemBuy9);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy9);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 10 && itemPriceBuy10 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy10;
					ctx.log("Searching for " + itemBuy10);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy10);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 11 && itemPriceBuy11 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy11;
					ctx.log("Searching for " + itemBuy11);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy11);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 12 && itemPriceBuy12 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy12;
					ctx.log("Searching for " + itemBuy12);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy12);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 13 && itemPriceBuy13 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy13;
					ctx.log("Searching for " + itemBuy13);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy13);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 14 && itemPriceBuy14 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy14;
					ctx.log("Searching for " + itemBuy14);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy14);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 15 && itemPriceBuy15 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy15;
					ctx.log("Searching for " + itemBuy15);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy15);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 16 && itemPriceBuy16 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy16;
					ctx.log("Searching for " + itemBuy16);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy16);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 17 && itemPriceBuy17 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy17;
					ctx.log("Searching for " + itemBuy17);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy17);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 18 && itemPriceBuy18 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy18;
					ctx.log("Searching for " + itemBuy18);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy18);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 19 && itemPriceBuy19 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy19;
					ctx.log("Searching for " + itemBuy19);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy19);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else if (stage == 20 && itemPriceBuy20 != 0) {
					ctx.menuActions.sendAction(315, -1, -1, 48612); // search for item to buy
					status = "Searching for " + itemBuy20;
					ctx.log("Searching for " + itemBuy20);
					ctx.sleep(2000, 3000);
					ctx.keyboard.sendKeys(itemBuy20);
					ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48000, 250, 24);
				} else {
					ctx.log("Sleeping");
					status = "Sleeping";
					stage = 1;
					ctx.sleep(600000, 1200000);
				}
			} else if ((ctx.widgets.getOpenInterfaceId() == 48000 || ctx.widgets.getOpenInterfaceId() == 48600
					|| ctx.widgets.getOpenInterfaceId() == 48599 || ctx.widgets.getOpenInterfaceId() == 48598)
					&& itemPriceBuy1 != 0) {
				if (priceWidget1 != null && !price1.contains("simple")) {
					if (stage == 1) {
						itemPriceBuy = itemPriceBuy1;
						itemPriceSell = itemPriceSell1;
						itemBuy = itemBuy1;
					} else if (stage == 2) {
						itemPriceBuy = itemPriceBuy2;
						itemPriceSell = itemPriceSell2;
						itemBuy = itemBuy2;
					} else if (stage == 3) {
						itemPriceBuy = itemPriceBuy3;
						itemPriceSell = itemPriceSell3;
						itemBuy = itemBuy3;
					} else if (stage == 4) {
						itemPriceBuy = itemPriceBuy4;
						itemPriceSell = itemPriceSell4;
						itemBuy = itemBuy4;
					} else if (stage == 5) {
						itemPriceBuy = itemPriceBuy5;
						itemPriceSell = itemPriceSell5;
						itemBuy = itemBuy5;
					} else if (stage == 6) {
						itemPriceBuy = itemPriceBuy6;
						itemPriceSell = itemPriceSell6;
						itemBuy = itemBuy6;
					} else if (stage == 7) {
						itemPriceBuy = itemPriceBuy7;
						itemPriceSell = itemPriceSell7;
						itemBuy = itemBuy7;
					} else if (stage == 8) {
						itemPriceBuy = itemPriceBuy8;
						itemPriceSell = itemPriceSell8;
						itemBuy = itemBuy8;
					} else if (stage == 9) {
						itemPriceBuy = itemPriceBuy9;
						itemPriceSell = itemPriceSell9;
						itemBuy = itemBuy9;
					} else if (stage == 10) {
						itemPriceBuy = itemPriceBuy10;
						itemPriceSell = itemPriceSell10;
						itemBuy = itemBuy10;
					} else if (stage == 11) {
						itemPriceBuy = itemPriceBuy11;
						itemPriceSell = itemPriceSell11;
						itemBuy = itemBuy11;
					} else if (stage == 12) {
						itemPriceBuy = itemPriceBuy12;
						itemPriceSell = itemPriceSell12;
						itemBuy = itemBuy12;
					} else if (stage == 13) {
						itemPriceBuy = itemPriceBuy13;
						itemPriceSell = itemPriceSell13;
						itemBuy = itemBuy13;
					} else if (stage == 14) {
						itemPriceBuy = itemPriceBuy14;
						itemPriceSell = itemPriceSell14;
						itemBuy = itemBuy14;
					} else if (stage == 15) {
						itemPriceBuy = itemPriceBuy15;
						itemPriceSell = itemPriceSell15;
						itemBuy = itemBuy15;
					} else if (stage == 16) {
						itemPriceBuy = itemPriceBuy16;
						itemPriceSell = itemPriceSell16;
						itemBuy = itemBuy16;
					} else if (stage == 17) {
						itemPriceBuy = itemPriceBuy17;
						itemPriceSell = itemPriceSell17;
						itemBuy = itemBuy17;
					} else if (stage == 18) {
						itemPriceBuy = itemPriceBuy18;
						itemPriceSell = itemPriceSell18;
						itemBuy = itemBuy18;
					} else if (stage == 19) {
						itemPriceBuy = itemPriceBuy19;
						itemPriceSell = itemPriceSell19;
						itemBuy = itemBuy19;
					} else if (stage == 20) {
						itemPriceBuy = itemPriceBuy20;
						itemPriceSell = itemPriceSell20;
						itemBuy = itemBuy20;
					}
					buySell();
				}
			} else {
				if (itemPriceBuy1 == 0) {
					ctx.log("Please set items price");
					ctx.stopScript();
				} else if (!ctx.pathing.onTile(bank)) {
					ctx.log("Not on the correct tile, stopping script");
					ctx.stopScript();
				}
			}
		} else {
			ctx.log("the thing happened");
			ctx.keyboard.sendKeys("wtf");
			ctx.sleep(4000, 8000);
			ctx.sendLogout();
			ctx.stopScript();
		}
	}

	@Override
	public void onTerminate() {
		if (gui != null) {
			GUI.frame.setVisible(false);
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(new Color(29, 3, 3, 94));
		g.fillRect(7, 273, 180, 35);
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1));
		g.drawRect(7, 273, 180, 35);
		g.setFont(new Font("Arial", 1, 10));
		g.setColor(new Color(255, 255, 255));
		g.drawString("Status: " + status, 10, 300);
		g.drawString("Time Running: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 10, 287);
	}

	public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You need at least")) {
				ctx.log("Out Of GP");
				if (gui != null) {
					gui.onCloseGUI();
				}
				ctx.stopScript();
			} else if (event.getMessage().contains("You must wait before doing this again")) {
				return;
			}
		}
	}

	private void buySell() {
		SimpleWidget nameWidget1 = ctx.widgets.populate().filter(26023).next();
		SimpleWidget priceWidget1 = ctx.widgets.populate().filter(26024).next();
		String price1 = priceWidget1.getText().replaceAll(",", "").replaceAll(" each", "");
		if (!nameWidget1.getText().contains(" ")) {
			stage = stage + 1;
			ctx.menuActions.sendAction(315, 0, 0, 48005);
			price1 = "0";
		}
		int intPrice = Integer.parseInt(price1);
		if (itemPriceBuy == 0) {
			stage = 1;
		}
		if (intPrice <= itemPriceBuy && nameWidget1.getText().toLowerCase().contains(itemBuy.toLowerCase())) {
			ctx.sleep(2400);
			status = "Buying " + itemBuy + " Price: " + price1;
			ctx.log("Buying " + itemBuy + " Price: " + price1);
			ctx.menuActions.sendAction(431, 23951, 0, 26022);
			return;
		} else if (ctx.widgets.getOpenInterfaceId() == 48000) {
			ctx.log("Price Too High for " + itemBuy + ". Current price: " + intPrice
					+ ", what we're trying to buy it for: " + itemPriceBuy);
			status = "Price Too High: " + intPrice;
			ctx.sleep(600);
			ctx.menuActions.sendAction(315, 0, 0, 48005);
			ctx.onCondition(() -> ctx.widgets.getOpenInterfaceId() == 48600, 250, 24);
			stage = stage + 1;
		}
		/*
		 if ((ctx.widgets.getOpenInterfaceId() == 48600 ||
		 ctx.widgets.getOpenInterfaceId() == 48599 || ctx.widgets.getOpenInterfaceId()
		 == 48598) && ctx.inventory.populate().filterContains(itemBuy).next() != null
		 && itemPriceSell != 0) { if (ctx.widgets.getOpenInterfaceId() == 48600) {
		 ctx.log("Clicking list item"); ctx.menuActions.sendAction(315, 0, 0, 48621);
		 } else if (ctx.widgets.getOpenInterfaceId() == 48599) {
		 ctx.log("Adding item to TP"); if
		 (ctx.inventory.populate().filterContains(itemBuy).next() != null) {
		 ctx.menuActions.sendAction(431, 990, -1, 48501); }
		 //ctx.inventory.populate().filterContains(itemBuy4).next().interact(431)
		 ; } else if (ctx.widgets.getOpenInterfaceId() == 48598) {
		 ctx.log("Clicking set price"); ctx.menuActions.sendAction(315, 0, 0, 48968);
		 ctx.sleep(1200); ctx.log("Typing in price: " + itemPriceSell);
		 ctx.keyboard.sendKeys("" + itemPriceSell); ctx.sleep(600);
		 ctx.log("Clicking confirm"); ctx.menuActions.sendAction(315, 0, 0, 48974);
		 ctx.sleep(1200); ctx.log("Clicking confirm"); ctx.menuActions.sendAction(315,
		 0, 0, 2461); stage = stage + 1; } } else { stage = stage + 1; ctx.sleep(600);
		 }
		 */
	}

	@Override
	public int loopDuration() {
		return 1000;
	}
}