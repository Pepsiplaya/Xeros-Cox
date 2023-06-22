package me.pepsi.xeros.nex.methods;

import java.util.Random;

import me.pepsi.xeros.nex.core.Nex;
import simple.api.ClientContext;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldPoint;
import simple.api.wrappers.SimpleGameObject;

public class Banking {
	final static ClientContext ctx = ClientContext.instance();
    
    @SuppressWarnings("deprecation")
	public static void loadPreset() {
		if (ctx.widgets.getBackDialogId() == 306) {
			ctx.pathing.walkToTile(ctx.players.getLocal().getLocation());
		}
    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
    	SimpleGameObject chest = (SimpleGameObject) ctx.objects.populate().filterContains("Chest").nearest().next();
    	final WorldPoint homeBank = new WorldPoint(3096, 3492, 0);
    	
		if (!Nex.presetLoaded && Nex.RAIDS_WAITING_AREA.containsPoint(ctx.players.getLocal())) {
			Nex.status = "Loading Preset";
			ctx.log("Loading Latest Preset");
			ctx.quickGear.latest();
		} else if (!Nex.presetLoaded && Nex.HOME.containsPoint(ctx.players.getLocal())) {
			if (!ctx.players.getLocal().getLocation().equals(homeBank)) {
				Nex.status = "Walking Closer To Bank";
				ctx.pathing.walkToTile(homeBank);
			} else {
				Nex.status = "Loading Preset";
				ctx.log("Loading Latest Preset");
				ctx.quickGear.latest();
			}
		} else if (!Nex.presetLoaded && Nex.NEX_LOBBY.containsPoint(ctx.players.getLocal())) {
			if (!ctx.inventory.inventoryFull()) {
				Nex.status = "Grabbing Another Brew";
				if (!ctx.bank.bankOpen()) {
					chest.interact(SimpleObjectActions.FIRST);
					ctx.onCondition(() -> ctx.bank.bankOpen(), 250, 24);
					return;
				} else if (ctx.bank.bankOpen()) {
					ctx.bank.withdraw(6685, 1);
				}
			} else {
				Nex.presetLoaded = true;
				ctx.bank.closeBank();
				Random rn = new Random();
				int answer = rn.nextInt(5);
		    	final WorldPoint bankWalk = new WorldPoint(2906 - answer, 5205 - answer);
		    	ctx.pathing.step(bankWalk);
			}
		}
		if (Nex.presetLoaded && ctx.players.getLocal().getLocation().getRegionID() != 11601) {
			Nex.status = "Teleporting To Nex";
			ctx.keyboard.sendKeys("::nex");
			ctx.onCondition(() -> ctx.players.getLocal().getLocation().getRegionID() == 11602, 250, 36);
		}
	}
    
    public static void heal() {
    	SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
	    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				Nex.status = "Healing";
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				return;
			}
		}
    }
}
