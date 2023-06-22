package me.pepsi.xeros.tob.util;

import java.util.Random;

import me.pepsi.xeros.tob.Tob;
import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleObjectActions;
import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;
import simple.api.filters.SimpleSkills;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;

public class Util {
	static ClientContext ctx = ClientContext.instance();
	
	public static Random random = new Random();
	public static WorldArea WAITING_ROOM = new WorldArea(new WorldPoint(3650, 3200), new WorldPoint(3700, 3250));
	public static WorldArea ROOM_ONE = new WorldArea(new WorldPoint(3185, 4425), new WorldPoint(3230, 4465));
	public static WorldArea ROOM_ONE_FIGHT = new WorldArea(new WorldPoint(3160, 4436), new WorldPoint(3185, 4458));
	public static WorldArea ROOM_ONE_BOTTOM = new WorldArea(new WorldPoint(3173, 4422), new WorldPoint(3193, 4434));
	public static WorldArea ROOM_TWO = new WorldArea(new WorldPoint(3305, 4445), new WorldPoint(3323, 4500));
	public static WorldArea ROOM_TWO_FIGHT = new WorldArea(new WorldPoint(3287, 4439), new WorldPoint(3304, 4456));
	public static WorldArea ROOM_TWO_EXIT = new WorldArea(new WorldPoint(3269, 4445), new WorldPoint(3287, 4450));
	public static WorldArea ROOM_THREE = new WorldArea(new WorldPoint(3293, 4256), new WorldPoint(3310, 4284));
	public static WorldArea ROOM_THREE_FIGHT = new WorldArea(new WorldPoint(3285, 4243), new WorldPoint(3305, 4255));
	public static WorldArea ROOM_FOUR = new WorldArea(new WorldPoint(3277, 4292), new WorldPoint(3282, 4307));
	public static WorldArea ROOM_FOUR_FIGHT = new WorldArea(new WorldPoint(3272, 4307), new WorldPoint(3287, 4350));
	public static WorldArea ROOM_FIVE = new WorldArea(new WorldPoint(3168, 4373, 1), new WorldPoint(4172, 4379, 1));
	public static WorldArea ROOM_FIVE_FIGHT = new WorldArea(new WorldPoint(3162, 4379, 1), new WorldPoint(3178, 4395, 1));
	public static WorldArea ROOM_FIVE_EXIT = new WorldArea(new WorldPoint(3168, 4395, 1), new WorldPoint(3172, 4401, 1));
	public static WorldArea ROOM_SIX = new WorldArea(new WorldPoint(3153, 4302), new WorldPoint(3183, 4323));
	public static WorldArea TREASURE_ROOM = new WorldArea(new WorldPoint(3225, 4305), new WorldPoint(3250, 4335));
	public static WorldArea HOME_AREA = new WorldArea(new WorldPoint(3072, 3521, 0),
			new WorldPoint(3072, 3464, 0), new WorldPoint(3137, 3474, 0), new WorldPoint(3137, 3521, 0));
	
	public static void useSpecialBP() {
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().inCombat() && ctx.combat.health() <= 80) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleep(600);
		}
	}
	public static void useSpecialDDS() {
		SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
		SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
		SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
		SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
		SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
		SimpleItem prims = ctx.inventory.populate().filter(13239).next();
		SimpleItem torture = ctx.inventory.populate().filter(19553).next();
		SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
		SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
		SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
		SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
		SimpleItem defender = ctx.inventory.populate().filterContains("defender").next();
		SimpleItem dds = ctx.inventory.populate().filterContains("Dragon dagger").next();
		SimpleItem voidrange = ctx.inventory.populate().filter(11664).next();
		SimpleItem voidrangeor = ctx.inventory.populate().filter(26475).next();
		SimpleItem pegs = ctx.inventory.populate().filter(13237).next();
		SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
		SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
		SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
		SimpleItem assemblermax = ctx.inventory.populate().filter(21898).next();
		SimpleItem assembler = ctx.inventory.populate().filter(22109).next();
		SimpleItem archerringi = ctx.inventory.populate().filter(11771).next();
		SimpleItem bos = ctx.inventory.populate().filter(23995).next();
		SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
		SimpleItem zarytevambs = ctx.inventory.populate().filter(26235).next();
		SimpleItem anguish = ctx.inventory.populate().filter(19547).next();
		SimpleItem twistedbow = ctx.inventory.populate().filter(20997).next();
		SimpleItem whip = ctx.inventory.populate().filter(4151).next();
		SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
		SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
    	if (dds != null || ctx.equipment.populate().filterContains("Dragon dagger").next() != null) {
    		if (ctx.players.getLocal().getAnimation() == 1062) {
    			ctx.sleep(900);
    			Tob.special = false;
    		}
    		if (ctx.players.getLocal().getAnimation() == 376) {
    			Tob.special = false;
    		}
    		if (Util.ROOM_THREE_FIGHT.containsPoint(ctx.players.getLocal())) {
    			if (ctx.combat.getSpecialAttackPercentage() >= 40) {
    				if (infernalmaxcape != null) {
    					infernalmaxcape.interact(SimpleItemActions.WEAR);
    				}
    				if (infernalcape != null) {
    					infernalcape.interact(SimpleItemActions.WEAR);
    				}
    				if (firemaxcape != null) {
    					firemaxcape.interact(SimpleItemActions.WEAR);
    				}
    				if (firecape != null) {
    					firecape.interact(SimpleItemActions.WEAR);
    				}
    				if (torture != null) {
    					torture.interact(SimpleItemActions.WEAR);
    				}
    				if (prims != null) {
    					prims.interact(SimpleItemActions.WEAR);
    				}
    				if (berserkerringi != null) {
    					berserkerringi.interact(SimpleItemActions.WEAR);
    				}
    				if (barrowsgloves != null) {
    					barrowsgloves.interact(SimpleItemActions.WEAR);
    				}
    				if (ferociousgloves != null) {
    					ferociousgloves.interact(SimpleItemActions.WEAR);
    				}
    				if (voidmeleeor != null) {
    					voidmeleeor.interact(SimpleItemActions.WEAR);
    				}
    				if (voidmelee != null) {
    					voidmelee.interact(SimpleItemActions.WEAR);
    				}
    				if (!ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) {
    					ctx.inventory.populate().filterContains("Dragon dagger").next().interact(454);
    				}
    				if (defender != null) {
    					defender.interact(SimpleItemActions.WEAR);
    				}
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
    					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
    				}
    				if (!ctx.equipment.populate().filterContains("Dragon dagger").isEmpty() && Tob.special == false) {
    					Tob.special = true;
    					ctx.combat.toggleSpecialAttack(true);
    					Tob.status = "Using Special";
    					ctx.log("Using Special");
    				}
    			} else {
    				if (scythe != null || !ctx.equipment.populate().filter(22325).isEmpty()) {
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						scythe.interact(SimpleItemActions.WEAR);
					} else {
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (whip != null) {
							whip.interact(SimpleItemActions.WEAR);
						}
						if (bosc != null) {
							bosc.interact(SimpleItemActions.WEAR);
						}
						if (rapier != null) {
							rapier.interact(SimpleItemActions.WEAR);
						}
						if (bos != null) {
							bos.interact(SimpleItemActions.WEAR);
						}
						if (abyssaltentacle != null) {
							abyssaltentacle.interact(SimpleItemActions.WEAR);
						}
						if (defender != null) {
							defender.interact(SimpleItemActions.WEAR);
						}
						if (dragondefender != null) {
							dragondefender.interact(SimpleItemActions.WEAR);
						}
					}
    			}
    		} else {
    			if (ctx.combat.getSpecialAttackPercentage() >= 25) {
    				if (infernalmaxcape != null) {
    					infernalmaxcape.interact(SimpleItemActions.WEAR);
    				}
    				if (infernalcape != null) {
    					infernalcape.interact(SimpleItemActions.WEAR);
    				}
    				if (firemaxcape != null) {
    					firemaxcape.interact(SimpleItemActions.WEAR);
    				}
    				if (firecape != null) {
    					firecape.interact(SimpleItemActions.WEAR);
    				}
    				if (torture != null) {
    					torture.interact(SimpleItemActions.WEAR);
    				}
    				if (prims != null) {
    					prims.interact(SimpleItemActions.WEAR);
    				}
    				if (berserkerringi != null) {
    					berserkerringi.interact(SimpleItemActions.WEAR);
    				}
    				if (barrowsgloves != null) {
    					barrowsgloves.interact(SimpleItemActions.WEAR);
    				}
    				if (ferociousgloves != null) {
    					ferociousgloves.interact(SimpleItemActions.WEAR);
    				}
    				if (voidmeleeor != null) {
    					voidmeleeor.interact(SimpleItemActions.WEAR);
    				}
    				if (voidmelee != null) {
    					voidmelee.interact(SimpleItemActions.WEAR);
    				}
    				if (!ctx.inventory.populate().filterContains("Dragon dagger").isEmpty()) {
    					ctx.inventory.populate().filterContains("Dragon dagger").next().interact(454);
    				}
    				if (defender != null) {
    					defender.interact(SimpleItemActions.WEAR);
    				}
    				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
    					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
    				}
    				if (!ctx.equipment.populate().filterContains("Dragon dagger").isEmpty() && Tob.special == false) {
    					ctx.combat.toggleSpecialAttack(true);
    					Tob.special = true;
    					Tob.status = "Using Special";
    					ctx.log("Using Special");
    				}
    			} else {
    				Tob.special = false;
    				if (Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal())) {
    					if (twistedbow != null || !ctx.equipment.populate().filter(20997).isEmpty()) {
    						if (voidrange != null) {
    							voidrange.interact(SimpleItemActions.WEAR);
    						}
    						if (voidrangeor != null) {
    							voidrangeor.interact(SimpleItemActions.WEAR);
    						}
    						if (archerringi != null) {
    							archerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (zarytevambs != null) {
    							zarytevambs.interact(SimpleItemActions.WEAR);
    						}
    						if (anguish != null) {
    							anguish.interact(SimpleItemActions.WEAR);
    						}
    						if (pegs != null) {
    							pegs.interact(SimpleItemActions.WEAR);
    						}
    						if (assemblermax != null) {
    							assemblermax.interact(SimpleItemActions.WEAR);
    						}
    						if (assembler != null) {
    							assembler.interact(SimpleItemActions.WEAR);
    						}
    						twistedbow.interact(SimpleItemActions.WEAR);
    					} else if (scythe != null && ctx.equipment.populate().filter(20997).isEmpty()) {
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (dragonboots != null) {
    							dragonboots.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						scythe.interact(SimpleItemActions.WEAR);
    					} else {
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (whip != null) {
    							whip.interact(SimpleItemActions.WEAR);
    						}
    						if (bosc != null) {
    							bosc.interact(SimpleItemActions.WEAR);
    						}
    						if (rapier != null) {
    							rapier.interact(SimpleItemActions.WEAR);
    						}
    						if (bos != null) {
    							bos.interact(SimpleItemActions.WEAR);
    						}
    						if (abyssaltentacle != null) {
    							abyssaltentacle.interact(SimpleItemActions.WEAR);
    						}
    						if (defender != null) {
    							defender.interact(SimpleItemActions.WEAR);
    						}
    						if (dragondefender != null) {
    							dragondefender.interact(SimpleItemActions.WEAR);
    						}
    					}
    				} else if (Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal())) {
    					if (scythe != null || !ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (dragonboots != null) {
    							dragonboots.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						scythe.interact(SimpleItemActions.WEAR);
    					} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidrange != null) {
    							voidrange.interact(SimpleItemActions.WEAR);
    						}
    						if (voidrangeor != null) {
    							voidrangeor.interact(SimpleItemActions.WEAR);
    						}
    						if (archerringi != null) {
    							archerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (zarytevambs != null) {
    							zarytevambs.interact(SimpleItemActions.WEAR);
    						}
    						if (anguish != null) {
    							anguish.interact(SimpleItemActions.WEAR);
    						}
    						if (pegs != null) {
    							pegs.interact(SimpleItemActions.WEAR);
    						}
    						if (assemblermax != null) {
    							assemblermax.interact(SimpleItemActions.WEAR);
    						}
    						if (assembler != null) {
    							assembler.interact(SimpleItemActions.WEAR);
    						}
    						twistedbow.interact(SimpleItemActions.WEAR);
    						if (Tob.rangeWalk) {
    							ctx.pathing.walkToTile(ctx.players.getLocal().getLocation().derrive(0, -8));
    							ctx.sleep(1200);
    							Tob.rangeWalk = false;
    						}
    					} else {
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (whip != null) {
    							whip.interact(SimpleItemActions.WEAR);
    						}
    						if (bosc != null) {
    							bosc.interact(SimpleItemActions.WEAR);
    						}
    						if (rapier != null) {
    							rapier.interact(SimpleItemActions.WEAR);
    						}
    						if (bos != null) {
    							bos.interact(SimpleItemActions.WEAR);
    						}
    						if (abyssaltentacle != null) {
    							abyssaltentacle.interact(SimpleItemActions.WEAR);
    						}
    						if (defender != null) {
    							defender.interact(SimpleItemActions.WEAR);
    						}
    						if (dragondefender != null) {
    							dragondefender.interact(SimpleItemActions.WEAR);
    						}
    					}
    				} else if (Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
    					if (scythe != null || !ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (dragonboots != null) {
    							dragonboots.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						scythe.interact(SimpleItemActions.WEAR);
    					} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidrange != null) {
    							voidrange.interact(SimpleItemActions.WEAR);
    						}
    						if (voidrangeor != null) {
    							voidrangeor.interact(SimpleItemActions.WEAR);
    						}
    						if (archerringi != null) {
    							archerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (zarytevambs != null) {
    							zarytevambs.interact(SimpleItemActions.WEAR);
    						}
    						if (anguish != null) {
    							anguish.interact(SimpleItemActions.WEAR);
    						}
    						if (pegs != null) {
    							pegs.interact(SimpleItemActions.WEAR);
    						}
    						if (assemblermax != null) {
    							assemblermax.interact(SimpleItemActions.WEAR);
    						}
    						if (assembler != null) {
    							assembler.interact(SimpleItemActions.WEAR);
    						}
    						twistedbow.interact(SimpleItemActions.WEAR);
    					} else {
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (whip != null) {
    							whip.interact(SimpleItemActions.WEAR);
    						}
    						if (bosc != null) {
    							bosc.interact(SimpleItemActions.WEAR);
    						}
    						if (rapier != null) {
    							rapier.interact(SimpleItemActions.WEAR);
    						}
    						if (bos != null) {
    							bos.interact(SimpleItemActions.WEAR);
    						}
    						if (abyssaltentacle != null) {
    							abyssaltentacle.interact(SimpleItemActions.WEAR);
    						}
    						if (defender != null) {
    							defender.interact(SimpleItemActions.WEAR);
    						}
    						if (dragondefender != null) {
    							dragondefender.interact(SimpleItemActions.WEAR);
    						}
    					}
    				} else if (Util.ROOM_SIX.containsPoint(ctx.players.getLocal())) {
    					if (scythe != null || !ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (dragonboots != null) {
    							dragonboots.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						scythe.interact(SimpleItemActions.WEAR);
    					} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
    						if (voidrange != null) {
    							voidrange.interact(SimpleItemActions.WEAR);
    						}
    						if (voidrangeor != null) {
    							voidrangeor.interact(SimpleItemActions.WEAR);
    						}
    						if (archerringi != null) {
    							archerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (zarytevambs != null) {
    							zarytevambs.interact(SimpleItemActions.WEAR);
    						}
    						if (anguish != null) {
    							anguish.interact(SimpleItemActions.WEAR);
    						}
    						if (pegs != null) {
    							pegs.interact(SimpleItemActions.WEAR);
    						}
    						if (assemblermax != null) {
    							assemblermax.interact(SimpleItemActions.WEAR);
    						}
    						if (assembler != null) {
    							assembler.interact(SimpleItemActions.WEAR);
    						}
    						twistedbow.interact(SimpleItemActions.WEAR);
    					} else {
    						if (berserkerringi != null) {
    							berserkerringi.interact(SimpleItemActions.WEAR);
    						}
    						if (barrowsgloves != null) {
    							barrowsgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (ferociousgloves != null) {
    							ferociousgloves.interact(SimpleItemActions.WEAR);
    						}
    						if (torture != null) {
    							torture.interact(SimpleItemActions.WEAR);
    						}
    						if (firecape != null) {
    							firecape.interact(SimpleItemActions.WEAR);
    						}
    						if (firemaxcape != null) {
    							firemaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalmaxcape != null) {
    							infernalmaxcape.interact(SimpleItemActions.WEAR);
    						}
    						if (infernalcape != null) {
    							infernalcape.interact(SimpleItemActions.WEAR);
    						}
    						if (prims != null) {
    							prims.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmelee != null) {
    							voidmelee.interact(SimpleItemActions.WEAR);
    						}
    						if (voidmeleeor != null) {
    							voidmeleeor.interact(SimpleItemActions.WEAR);
    						}
    						if (whip != null) {
    							whip.interact(SimpleItemActions.WEAR);
    						}
    						if (bosc != null) {
    							bosc.interact(SimpleItemActions.WEAR);
    						}
    						if (rapier != null) {
    							rapier.interact(SimpleItemActions.WEAR);
    						}
    						if (bos != null) {
    							bos.interact(SimpleItemActions.WEAR);
    						}
    						if (abyssaltentacle != null) {
    							abyssaltentacle.interact(SimpleItemActions.WEAR);
    						}
    						if (defender != null) {
    							defender.interact(SimpleItemActions.WEAR);
    						}
    						if (dragondefender != null) {
    							dragondefender.interact(SimpleItemActions.WEAR);
    						}
    					}
    				}
    			}
    		}
		}
	}
	
	public static void dwhSpecial() {
		if (ctx.players.getLocal().getAnimation() == 1378 || ctx.players.getLocal().getAnimation() == 401 || ctx.players.getLocal().getAnimation() == 4177) {
			Tob.special = false;
		}
		SimpleItem dwh = ctx.inventory.populate().filter(13576).next();
		if (dwh != null || ctx.equipment.populate().filter(13576).next() != null) {
			if (ctx.combat.getSpecialAttackPercentage() >= 50) {
				if (!ctx.inventory.populate().filter(13576).isEmpty()) {
					ctx.inventory.populate().filter(13576).next().interact(454);
				}
				if (!ctx.inventory.populate().filterContains("defender").isEmpty()) {
					ctx.inventory.populate().filterContains("defender").next().interact(454);
				}
				if (!ctx.prayers.prayerActive(SimplePrayers.Prayers.PIETY)) {
					ctx.prayers.prayer(SimplePrayers.Prayers.PIETY, true);
				}
				if (!ctx.equipment.populate().filter(13576).isEmpty() && Tob.special == false) {
					ctx.combat.toggleSpecialAttack(true);
					Tob.special = true;
					Tob.status = "Using Special";
					ctx.log("Using Special");
					ctx.sleep(1000);
				}
			} else {
				Tob.special = false;
				SimpleItem voidrange = ctx.inventory.populate().filter(11664).next();
				SimpleItem voidrangeor = ctx.inventory.populate().filter(26475).next();
				SimpleItem voidmelee = ctx.inventory.populate().filter(11665).next();
				SimpleItem voidmeleeor = ctx.inventory.populate().filter(26477).next();
				SimpleItem blowpipe = ctx.inventory.populate().filter(12926).next();
				SimpleItem pegs = ctx.inventory.populate().filter(13237).next();
				SimpleItem abyssaltentacle = ctx.inventory.populate().filter(12006).next();
				SimpleItem ferociousgloves = ctx.inventory.populate().filter(22981).next();
				SimpleItem barrowsgloves = ctx.inventory.populate().filter(7462).next();
				SimpleItem berserkerringi = ctx.inventory.populate().filter(11773).next();
				SimpleItem rapier = ctx.inventory.populate().filter(22324).next();
				SimpleItem prims = ctx.inventory.populate().filter(13239).next();
				SimpleItem scythe = ctx.inventory.populate().filter(22325).next();
				SimpleItem torture = ctx.inventory.populate().filter(19553).next();
				SimpleItem firecape = ctx.inventory.populate().filter(6570).next();
				SimpleItem firemaxcape = ctx.inventory.populate().filter(13329).next();
				SimpleItem infernalcape = ctx.inventory.populate().filter(21295).next();
				SimpleItem infernalmaxcape = ctx.inventory.populate().filter(21285).next();
				SimpleItem assemblermax = ctx.inventory.populate().filter(21898).next();
				SimpleItem assembler = ctx.inventory.populate().filter(22109).next();
				SimpleItem archerringi = ctx.inventory.populate().filter(11771).next();
				SimpleItem bos = ctx.inventory.populate().filter(23995).next();
				SimpleItem bosc = ctx.inventory.populate().filter(24551).next();
				SimpleItem zarytevambs = ctx.inventory.populate().filter(26235).next();
				SimpleItem anguish = ctx.inventory.populate().filter(19547).next();
				SimpleItem twistedbow = ctx.inventory.populate().filter(20997).next();
				SimpleItem defender = ctx.inventory.populate().filterContains("defender").next();
				SimpleItem whip = ctx.inventory.populate().filter(4151).next();
				SimpleItem dragondefender = ctx.inventory.populate().filter(12954).next();
				SimpleItem dragonboots = ctx.inventory.populate().filter(11840).next();
				if (Util.ROOM_ONE_FIGHT.containsPoint(ctx.players.getLocal())) {
					if (twistedbow != null) {
						if (voidrange != null) {
							voidrange.interact(SimpleItemActions.WEAR);
						}
						if (voidrangeor != null) {
							voidrangeor.interact(SimpleItemActions.WEAR);
						}
						if (archerringi != null) {
							archerringi.interact(SimpleItemActions.WEAR);
						}
						if (zarytevambs != null) {
							zarytevambs.interact(SimpleItemActions.WEAR);
						}
						if (anguish != null) {
							anguish.interact(SimpleItemActions.WEAR);
						}
						if (pegs != null) {
							pegs.interact(SimpleItemActions.WEAR);
						}
						if (assemblermax != null) {
							assemblermax.interact(SimpleItemActions.WEAR);
						}
						if (assembler != null) {
							assembler.interact(SimpleItemActions.WEAR);
						}
						twistedbow.interact(SimpleItemActions.WEAR);
					} else if (scythe != null && ctx.equipment.populate().filter(20997).isEmpty()) {
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (dragonboots != null) {
							dragonboots.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						scythe.interact(SimpleItemActions.WEAR);
					} else if (blowpipe != null && twistedbow == null && ctx.equipment.populate().filter(20997).isEmpty() && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
						if (voidrange != null) {
							voidrange.interact(SimpleItemActions.WEAR);
						}
						if (voidrangeor != null) {
							voidrangeor.interact(SimpleItemActions.WEAR);
						}
						if (archerringi != null) {
							archerringi.interact(SimpleItemActions.WEAR);
						}
						if (zarytevambs != null) {
							zarytevambs.interact(SimpleItemActions.WEAR);
						}
						if (anguish != null) {
							anguish.interact(SimpleItemActions.WEAR);
						}
						if (pegs != null) {
							pegs.interact(SimpleItemActions.WEAR);
						}
						if (assemblermax != null) {
							assemblermax.interact(SimpleItemActions.WEAR);
						}
						if (assembler != null) {
							assembler.interact(SimpleItemActions.WEAR);
						}
						blowpipe.interact(SimpleItemActions.WEAR);
					}
				} else if (Util.ROOM_FOUR_FIGHT.containsPoint(ctx.players.getLocal())) {
					if (scythe != null) {
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (dragonboots != null) {
							dragonboots.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (scythe != null) {
							scythe.interact(SimpleItemActions.WEAR);
						}
					} else {
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (whip != null) {
							whip.interact(SimpleItemActions.WEAR);
						}
						if (bosc != null) {
							bosc.interact(SimpleItemActions.WEAR);
						}
						if (rapier != null) {
							rapier.interact(SimpleItemActions.WEAR);
						}
						if (bos != null) {
							bos.interact(SimpleItemActions.WEAR);
						}
						if (abyssaltentacle != null) {
							abyssaltentacle.interact(SimpleItemActions.WEAR);
						}
						if (defender != null) {
							defender.interact(SimpleItemActions.WEAR);
						}
						if (dragondefender != null) {
							dragondefender.interact(SimpleItemActions.WEAR);
						}
					}
				} else if (Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
					if (scythe != null) {
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (dragonboots != null) {
							dragonboots.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						scythe.interact(SimpleItemActions.WEAR);
					} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
						if (voidrange != null) {
							voidrange.interact(SimpleItemActions.WEAR);
						}
						if (archerringi != null) {
							archerringi.interact(SimpleItemActions.WEAR);
						}
						if (zarytevambs != null) {
							zarytevambs.interact(SimpleItemActions.WEAR);
						}
						if (anguish != null) {
							anguish.interact(SimpleItemActions.WEAR);
						}
						if (pegs != null) {
							pegs.interact(SimpleItemActions.WEAR);
						}
						if (assemblermax != null) {
							assemblermax.interact(SimpleItemActions.WEAR);
						}
						if (assembler != null) {
							assembler.interact(SimpleItemActions.WEAR);
						}
						twistedbow.interact(SimpleItemActions.WEAR);
					} else if (blowpipe != null && twistedbow == null && ctx.equipment.populate().filter(20997).isEmpty() && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (whip != null) {
							whip.interact(SimpleItemActions.WEAR);
						}
						if (bosc != null) {
							bosc.interact(SimpleItemActions.WEAR);
						}
						if (rapier != null) {
							rapier.interact(SimpleItemActions.WEAR);
						}
						if (bos != null) {
							bos.interact(SimpleItemActions.WEAR);
						}
						if (abyssaltentacle != null) {
							abyssaltentacle.interact(SimpleItemActions.WEAR);
						}
						if (defender != null) {
							defender.interact(SimpleItemActions.WEAR);
						}
						if (dragondefender != null) {
							dragondefender.interact(SimpleItemActions.WEAR);
						}
					}
				} else if (Util.ROOM_SIX.containsPoint(ctx.players.getLocal())) {
					if (scythe != null) {
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (dragonboots != null) {
							dragonboots.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						scythe.interact(SimpleItemActions.WEAR);
					} else if (twistedbow != null && scythe == null && ctx.equipment.populate().filter(22325).isEmpty()) {
						if (voidrange != null) {
							voidrange.interact(SimpleItemActions.WEAR);
						}
						if (archerringi != null) {
							archerringi.interact(SimpleItemActions.WEAR);
						}
						if (zarytevambs != null) {
							zarytevambs.interact(SimpleItemActions.WEAR);
						}
						if (anguish != null) {
							anguish.interact(SimpleItemActions.WEAR);
						}
						if (pegs != null) {
							pegs.interact(SimpleItemActions.WEAR);
						}
						if (assemblermax != null) {
							assemblermax.interact(SimpleItemActions.WEAR);
						}
						if (assembler != null) {
							assembler.interact(SimpleItemActions.WEAR);
						}
						twistedbow.interact(SimpleItemActions.WEAR);
					} else {
						if (berserkerringi != null) {
							berserkerringi.interact(SimpleItemActions.WEAR);
						}
						if (barrowsgloves != null) {
							barrowsgloves.interact(SimpleItemActions.WEAR);
						}
						if (ferociousgloves != null) {
							ferociousgloves.interact(SimpleItemActions.WEAR);
						}
						if (torture != null) {
							torture.interact(SimpleItemActions.WEAR);
						}
						if (firecape != null) {
							firecape.interact(SimpleItemActions.WEAR);
						}
						if (firemaxcape != null) {
							firemaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalmaxcape != null) {
							infernalmaxcape.interact(SimpleItemActions.WEAR);
						}
						if (infernalcape != null) {
							infernalcape.interact(SimpleItemActions.WEAR);
						}
						if (prims != null) {
							prims.interact(SimpleItemActions.WEAR);
						}
						if (voidmelee != null) {
							voidmelee.interact(SimpleItemActions.WEAR);
						}
						if (voidmeleeor != null) {
							voidmeleeor.interact(SimpleItemActions.WEAR);
						}
						if (whip != null) {
							whip.interact(SimpleItemActions.WEAR);
						}
						if (bosc != null) {
							bosc.interact(SimpleItemActions.WEAR);
						}
						if (rapier != null) {
							rapier.interact(SimpleItemActions.WEAR);
						}
						if (bos != null) {
							bos.interact(SimpleItemActions.WEAR);
						}
						if (abyssaltentacle != null) {
							abyssaltentacle.interact(SimpleItemActions.WEAR);
						}
						if (defender != null) {
							defender.interact(SimpleItemActions.WEAR);
						}
						if (dragondefender != null) {
							dragondefender.interact(SimpleItemActions.WEAR);
						}
					}
				}
			}
		}
	}
	
	public static void handleRangePotion() {
		final int realLevel = ctx.skills.getRealLevel(SimpleSkills.Skill.RANGED);
		final int boostedLevel = ctx.skills.getLevel(SimpleSkills.Skill.RANGED);
		SimpleItem focusHeart = ctx.inventory.populate().filter(281).next();
		SimpleItem overload = ctx.inventory.populate().filterContains("Overload").next();
		if (overload != null && System.currentTimeMillis() > (Tob.lastOverload + 301000) && ctx.combat.health() >= 75) {
			Tob.status = "Using Overload";
			ctx.log("Using Overload");
			if (!Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
				ctx.sleep(750);
			}
			overload.interact(74);
			Tob.lastOverload = System.currentTimeMillis();
		} else if (!ctx.inventory.populate().filterContains("Ranging").isEmpty() && boostedLevel - realLevel <= 9) {
			Tob.status = "Drinking Ranging Potion";
			ctx.log("Drinking Ranging Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
		} else if (focusHeart != null && boostedLevel - realLevel <= 2) {
			Tob.status = "Using Focused Heart";
			ctx.log("Using Focused Heart");
			focusHeart.interact(74);
		}
	}

	public final static SimpleEntityQuery<SimpleNpc> xarpus() {
		return ctx.npcs.populate().filter(8340).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			return true;
		});
	}
	
	public static void handleEatingFood() {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		int foodThreshold = ctx.equipment.populate().filterContains("Torva").isEmpty() ? 90 : 100;
		if (Util.ROOM_FIVE_FIGHT.containsPoint(ctx.players.getLocal())) {
			if (brew != null && ctx.combat.health() <= foodThreshold) {
				Tob.status = "Drinking brew";
				ctx.log("Drinking brew");
				brew.interact(74);
			} else if (food != null && ctx.combat.health() <= foodThreshold) {
				Tob.status = "Eating Food";
				ctx.log("Eating Food");
				food.interact(SimpleItemActions.CONSUME);
			}
		} else if (brew != null && ctx.combat.health() <= 80) {
			Tob.status = "Drinking brew";
			ctx.log("Drinking brew");
			brew.interact(74);
		} else if (food != null && ctx.combat.health() <= 70) {
			Tob.status = "Eating Food";
			ctx.log("Eating Food");
			food.interact(SimpleItemActions.CONSUME);
		}
	}

	public static void handleDrinkingPrayer() {
		Random rn = new Random();
		int random = rn.nextInt(25);
		if (!ctx.inventory.populate().filterContains("Super r").isEmpty() && ctx.prayers.points() <= Tob.prayer) {
			Tob.status = "Drinking Restore Potion";
			ctx.log("Drinking Restore Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Tob.prayer = 20 + random;
		} else if (!ctx.inventory.populate().filterContains("Prayer p").isEmpty() && ctx.prayers.points() <= Tob.prayer) {
			Tob.status = "Drinking Prayer Potion";
			ctx.log("Drinking Prayer Potion");
			ctx.inventory.next().interact(SimpleItemActions.DRINK);
			Tob.prayer = 20 + random;
		}
	}

	public static void dropVial() {
		if (!ctx.inventory.populate().filter("Vial").isEmpty()) {
			Tob.status = "Dropping Vial";
			ctx.log("Dropping Vial");
			ctx.inventory.next().interact(SimpleItemActions.DROP);
		}
	}

	public static void Spec() {
		if (!ctx.equipment.populate().filter(12788).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50
				&& ctx.players.getLocal().inCombat()) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleepCondition(() -> ctx.combat.specialAttack(), 1500);
		}
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50
				&& ctx.players.getLocal().inCombat()
				&& ctx.players.getLocal().getHealth() <= ctx.players.getLocal().getMaxHealth() - 25) {
			ctx.combat.toggleSpecialAttack(true);
			ctx.sleepCondition(() -> ctx.combat.specialAttack(), 1500);
		}
	}

	public static void roomOnePath() {
		WorldPoint[] PATH1 = { new WorldPoint(3218, 4453, 0), new WorldPoint(3213, 4448, 0),
				new WorldPoint(3205, 4447, 0), new WorldPoint(3195, 4447, 0) };
		ctx.pathing.walkPath(PATH1);
	}
	
	public static int between(int min, int max) {
		try {
			return min + (max == min ? 0 : random.nextInt(max - min));
		} catch (Exception e) {
			return min + (max - min);
		}
	}
	
	public static void heal() {
    	SimpleGameObject heal = (SimpleGameObject) ctx.objects.populate().filter(23709).nearest().next();
		if (heal != null) {
	    	ctx.onCondition(() -> !ctx.players.getLocal().isAnimating(), 250, 24);
			if (ctx.combat.health() < ctx.combat.maxHealth() || ctx.prayers.points() < ctx.prayers.maxPoints()) {
				Tob.status = "Healing";
				ctx.log("Healing");
				heal.interact(SimpleObjectActions.OPEN);
				ctx.onCondition(() -> ctx.widgets.getBackDialogId() == 306, 250, 16);
				if (ctx.widgets.getBackDialogId() == 306) {
					ctx.sleep(600);
					ctx.pathing.step(ctx.players.getLocal().getLocation());
				}
				return;
			}
		}
    }
}
