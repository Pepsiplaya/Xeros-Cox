package me.remie.xeros.combat;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.events.ChatMessageEvent;
import simple.api.filters.SimpleSkills;
import simple.api.queries.SimpleEntityQuery;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleGroundItem;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;
import javax.swing.*;

import me.remie.xeros.utils.ExperienceTracker;

import java.awt.*;

/**
 * Created by Seth on April 10/11/2021, 2021 at 9:50 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707
 */
@ScriptManifest(author = "Reminisce", name = "SAIO Combat - Xeros", category = Category.COMBAT, version = "0.1",
        description = "Kills monsters", discord = "Reminisce#1707", servers = { "Xeros" })
public class AutoFighter extends Script implements SimplePaintable {

    public int eatHealth;
    public int[] lootNames;
    public int[] npcIds;
    public int[] foodId;
    public boolean eatForSpace;
    public boolean quickPrayers;
    public boolean teleOnTask;
    public boolean started;
    public boolean fillCannon;
    public static long lastHeart = -1;

    public long startTime;
    public static String status;
    public static final WorldArea HOME_AREA = new WorldArea(
            new WorldPoint(3072, 3521, 0), new WorldPoint(3072, 3464, 0),
            new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));

    public AutoFighterUI gui;

    public ExperienceTracker experienceTracker;

    @Override
    public boolean onExecute() {
    	//quickPrayers = true;
        experienceTracker = new ExperienceTracker(ctx);
        experienceTracker.start(SimpleSkills.Skill.HITPOINTS, SimpleSkills.Skill.ATTACK, SimpleSkills.Skill.STRENGTH,
                SimpleSkills.Skill.DEFENCE, SimpleSkills.Skill.RANGED, SimpleSkills.Skill.MAGIC);
        startTime = System.currentTimeMillis();
        status = "Waiting to start...";
        ctx.log("Thanks for using %s!", getName());
        try {
            AutoFighter script = this;
            SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
                gui = new AutoFighterUI(script);
            }});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public void useSpecial() {
        if (ctx.combat.getSpecialAttackPercentage() >= 25) {
            if (ctx.inventory.populate().filter(5698).size() > 0) {
                ctx.inventory.populate().filter(5698).next().interact(454);
                ctx.sleepCondition(() -> ctx.equipment.populate().filter(5698).size() > 0, 2000);
            }
            if (ctx.equipment.populate().filter(5698).size() > 0) {
                ctx.combat.toggleSpecialAttack(true);
                ctx.sleepCondition(() -> ctx.combat.specialAttack(), 2000);
            }

        }
        if (ctx.combat.getSpecialAttackPercentage() < 25) {
            if (ctx.inventory.populate().filter(12006).size() > 0 ) {
                ctx.inventory.populate().filter(12006).next().interact("wield");
                ctx.sleepCondition(() -> ctx.equipment.populate().filter(12006).size() > 0, 2000);
            }
        }
    }

    @Override
    public void onProcess() {
        try {
            if (!started) { // Checks whether the script has been started or not.
                return;
            }
            if (HOME_AREA.containsPoint(ctx.players.getLocal())) {
            }
            if (ctx.inventory.inventoryFull() && !HOME_AREA.containsPoint(ctx.players.getLocal())) {
            	ctx.magic.castHomeTeleport();
            	ctx.prayers.disableAll();
            	ctx.sleep(1200);
            }

            if (foodId != null && (ctx.combat.health() < eatHealth)) { // If we selected food and our hp is below threshhold, let's eat
                if (eatFood()) {
                    return;
                }
            }
        	SimpleGameObject cannon = (SimpleGameObject) ctx.objects.populate().filter(6).nearest().next();
            if (cannon != null && fillCannon == true) {
            	cannon.interact(502);
            }

            if (lootNames.length > 0) {//looting has been setup

                if (eatForSpace && foodId != null &&  ctx.inventory.getFreeSlots() <= 0 && !ctx.inventory.populate().filter(foodId).isEmpty()) {
                    eatFood();
                }

                if (ctx.inventory.getFreeSlots() > 0 && !ground().isEmpty()) {
                    SimpleGroundItem item = ctx.groundItems.nearest().next();
                    if (item != null) {
                        status("Looting " + item.getName());
                        item.interact();
                    }
                    return;
                }
            }
            if (foodId != null && ctx.inventory.populate().filter(foodId).isEmpty() && ctx.combat.health() <= 20) {
                ctx.magic.castHomeTeleport();
                ctx.onCondition(() -> HOME_AREA.containsPoint(ctx.players.getLocal().getLocation()), 150, 12);
                ctx.stopScript();
            }
            //useSpecial();
            specialAttack(ctx);
    		SimpleItem corruptHeart = ctx.inventory.populate().filter(20731).next();
    	    if (corruptHeart != null && System.currentTimeMillis() > (lastHeart + 430000)) {
    	    	status = "Using Corrupt Heart";
    	    	ctx.log("Using Corrupt Heart");
    	    	corruptHeart.interact(74);
    	    	ctx.sleep(600);
    	    	lastHeart = System.currentTimeMillis();
    	    }
            if (!ctx.players.getLocal().inCombat() || ctx.players.getLocal().getInteracting() == null) {
                SimpleNpc fm = npcs().filter((n) -> n.getInteracting() != null && n.getInteracting().equals(ctx.players.getLocal()) && n.inCombat()).nearest().next();
                SimpleNpc npc = fm != null ? fm : npcs().nearest().next();
                if (npc == null) {
                    return;
                }
                if (npc.getHealth() <= 0) {
                	ctx.sleep(1200, 2400);
                }
                //ctx.sleep(250, 2000);
                if (ctx.pathing.reachable(npc) && ground().isEmpty()) {
                	status("Attacking " + npc.getName());
                    npc.interact("attack");
                    ctx.onCondition(() -> ctx.players.getLocal().inCombat(), 250, 3);
                }
            }
            handleDrinkingPrayer();
            if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
    			ctx.inventory.next().interact(SimpleItemActions.DROP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        if (gui != null) {
            gui.onCloseGUI();
        }
    }

    /**
     * Drinks prayer potions when your prayer points drop below a certain amount
     */
    private void handleDrinkingPrayer() {
        if (ctx.prayers.points() > 5) {
            //ctx.prayers.quickPrayers(true);
        } else {
        	if (ctx.prayers.quickPrayers()) {
        		ctx.prayers.quickPrayers(false);
        	}
        	ctx.magic.castHomeTeleport();
        	ctx.sleep(2400);
        }
        if (ctx.prayers.points() <= 20) {
            final SimpleItem restore = ctx.inventory.populate().filterContains("Super restore").next();
            final SimpleItem prayer = ctx.inventory.populate().filterContains("Prayer potion").next();
            final int cached = ctx.prayers.points();
            status("Drinking prayer");
            if (restore != null && restore.interact("drink")) {
                ctx.onCondition(() -> ctx.prayers.points() > cached, 250, 12);
            } else if (prayer != null && prayer.interact("drink")) {
                ctx.onCondition(() -> ctx.prayers.points() > cached, 250, 12);
            }
        }
    }

    private boolean eatFood() {
        if (foodId != null) {
            final SimpleItem food = ctx.inventory.populate().filter(foodId).next();
            if (food != null) {
                final int cached = ctx.inventory.getFreeSlots();
                status("Eating " + food.getName());
                food.interact("eat");
                return ctx.onCondition(() -> ctx.inventory.getFreeSlots() > cached, 250, 9);
            }
        }
        return false;
    }
    
    public static void specialAttack(ClientContext ctx) {
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.health() <= ctx.combat.maxHealth() - 15 && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().getInteracting() != null) {
			ctx.combat.toggleSpecialAttack(true);
			status = "Using Special Attack";
			ctx.log("Using Special Attack");
			ctx.onCondition(() -> ctx.combat.specialAttack(), 250, 6);
		}
    }

    public final SimpleEntityQuery<SimpleNpc> npcs() {
        return ctx.npcs.populate().filter(npcIds).filter(n -> {
            if (n == null) {
                return false;
            }
            if (n.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 18) {
                return false;
            }
            if (n.inCombat() && n.getInteracting() != null && !n.getInteracting().equals(ctx.players.getLocal())) {
                return false;
            }
            return true;
        });
    }

    public final SimpleEntityQuery<SimpleGroundItem> ground() {
        return ctx.groundItems.populate().filter(lootNames).filter(t -> {
            if (t == null) {
                return false;
            }
            if (!ctx.pathing.reachable(t.getLocation())) {
                return false;
            }
            if (t.getLocation().distanceTo(ctx.players.getLocal().getLocation()) > 15) {
                return false;
            }
            return true;
        });
    }
    
    public void onChatMessage(ChatMessageEvent event) {
		if (event.getMessageType() == 0 && event.getSender().equals("")) {
			if (event.getMessage().contains("You have completed")) {
				ctx.magic.castHomeTeleport();
				ctx.stopScript();
			} else if (event.getMessage().contains("Your cannon has run out of ammo")) {
				fillCannon = true;
			} else if (event.getMessage().contains("cannonballs into your cannon")) {
				fillCannon = false;;
			} else if (event.getMessage().contains("You activate the twisted heart") || event.getMessage().contains("You must wait 5 minutes")) {
		    	lastHeart = System.currentTimeMillis();
			}
		}
    }

    @Override
    public void onPaint(Graphics2D g) {
       
    }

    private void status(String status) { // Set's our script's status
        AutoFighter.status = status;
    }

    public void setupEating(int[] foodId, int eatAt) {
        this.foodId = foodId;
        this.eatHealth = eatAt;
    }

    public void setupLooting(int[] lootNames) {
        this.lootNames = lootNames;
    }

    public void setupAttacking(int[] npcIds) {
        this.npcIds = npcIds;
    }

}