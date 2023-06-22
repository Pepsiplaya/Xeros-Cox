package me.pepsi.xeros.zulrah.methods;

import simple.api.ClientContext;
import simple.api.actions.SimpleItemActions;
import simple.api.actions.SimpleNpcActions;
import simple.api.coords.WorldPoint;
import simple.api.queries.SimpleEntityQuery;
import simple.api.wrappers.SimpleGameObject;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;

import java.util.Random;

import me.pepsi.xeros.zulrah.core.Zulrah;

public class Combat {
	
	final static WorldPoint zulrahEast = new WorldPoint(2277, 3074, 0);
	final static WorldPoint zulrahWest = new WorldPoint(2259, 3073, 0);
	final static WorldPoint zulrahNorth = new WorldPoint(2268, 3074, 0);
	final static WorldPoint zulrahSouth = new WorldPoint(2268, 3064, 0);
	final static WorldPoint east = new WorldPoint(2272, 3073, 0);
	final static WorldPoint west = new WorldPoint(2265, 3070, 0);
	final static WorldPoint west2 = new WorldPoint(2264, 3070, 0);
	static final WorldPoint northSouth = new WorldPoint(2267, 3070, 0);
	static final WorldPoint melee1 = new WorldPoint(2266, 3070, 0);
	static final WorldPoint melee2 = new WorldPoint(2267, 3070, 0);
	static final WorldPoint melee3 = new WorldPoint(2268, 3070, 0);
	static final WorldPoint melee4 = new WorldPoint(2269, 3070, 0);
	static final WorldPoint melee5 = new WorldPoint(2270, 3070, 0);
	static final WorldPoint start = new WorldPoint(2268, 3069, 0);

	public final static SimpleEntityQuery<SimpleNpc> range(ClientContext ctx) {
		return ctx.npcs.populate().filter(2042).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			if (n.getAnimation() == 5072) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> melee(ClientContext ctx) {
		return ctx.npcs.populate().filter(2043).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			if (n.getAnimation() == 5072) {
				return false;
			}
			return true;
		});
	}
	
	public final static SimpleEntityQuery<SimpleNpc> mage(ClientContext ctx) {
		return ctx.npcs.populate().filter(2044).filter(n -> {
			if (n == null) {
				return false;
			}
			if (n.isDead()) {
				return false;
			}
			if (n.getAnimation() == 5072) {
				return false;
			}
			return true;
		});
	}
	
    public static void doCombat(ClientContext ctx) {
    	SimpleGameObject cloud = (SimpleGameObject) ctx.objects.populate().filter(11700).nearest().next();
     	SimpleNpc mv = range(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc range = mv != null ? mv : range(ctx).nearest().next();
		SimpleNpc mv2 = melee(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc melee = mv2 != null ? mv2 : melee(ctx).nearest().next();
		SimpleNpc mv3 = mage(ctx).filter((n) -> n.inCombat()).next();
		SimpleNpc mage = mv3 != null ? mv3 : mage(ctx).nearest().next();
		SimpleItem row = ctx.inventory.populate().filter(2572).next();
		SimpleItem rowi = ctx.inventory.populate().filter(12785).next();
		SimpleNpc snakeling = ctx.npcs.populate().filter(2045).next();
		SimpleItem suffering = ctx.inventory.populate().filter(19550).next();
		SimpleItem sufferingi = ctx.inventory.populate().filter(19710).next();
		SimpleItem recoil = ctx.inventory.populate().filter(2550).next();
		SimpleItem archerRingi = ctx.inventory.populate().filter(11771).next();
        SimpleItem archerRing = ctx.inventory.populate().filter(6733).next();
		int naturerune = ctx.inventory.populate().filter(561).population(true);
		int firerune = ctx.inventory.populate().filter(554).population(true);
		if (row != null) {
			if (mage != null && (mage.getHealth() <= 80 || mage.isDead()) && mage.inCombat()) {
				row.interact(SimpleItemActions.WEAR);
			} else if (range != null && (range.getHealth() <= 80 || range.isDead()) && range.inCombat()) {
				row.interact(SimpleItemActions.WEAR);
			} else if (melee != null && (melee.getHealth() <= 80 || melee.isDead()) && melee.inCombat()) {
				row.interact(SimpleItemActions.WEAR);
			}
		} else if (rowi != null) {
			if (mage != null && (mage.getHealth() <= 80 || mage.isDead()) && mage.inCombat()) {
				rowi.interact(SimpleItemActions.WEAR);
			} else if (range != null && (range.getHealth() <= 80 || range.isDead()) && range.inCombat()) {
				rowi.interact(SimpleItemActions.WEAR);
			} else if (melee != null && (melee.getHealth() <= 80 || melee.isDead()) && melee.inCombat()) {
				rowi.interact(SimpleItemActions.WEAR);
			}
		}
		if (snakeling != null && ctx.equipment.populate().filterContains("wealth").isEmpty() && (ctx.equipment.populate().filterContains("recoil").isEmpty() && ctx.equipment.populate().filterContains("suffering").isEmpty())) {
			if (recoil != null) {
				recoil.interact(SimpleItemActions.WEAR);
			} else if (suffering != null) {
				suffering.interact(SimpleItemActions.WEAR);
			} else if (sufferingi != null) {
				sufferingi.interact(SimpleItemActions.WEAR);
			}
		}
		if (snakeling == null && ctx.equipment.populate().filterContains("wealth").next() == null) {
			if (archerRing != null) {
				archerRing.interact(SimpleItemActions.WEAR);
			} else if (archerRingi != null) {
				archerRingi.interact(SimpleItemActions.WEAR);
			}
		}
        if (!ctx.inventory.populate().filterContains("coin bag").isEmpty() && !ctx.inventory.inventoryFull()) {
        	Zulrah.status = "Opening Coin Bag";
            ctx.inventory.next().interact(SimpleItemActions.CONSUME);
        }
        if (!ctx.inventory.populate().filterContains("Dragon med helm").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	Zulrah.status = "Alching Dragon med helm";
        	SimpleItem alch = ctx.inventory.populate().filter(1149).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
        if (!ctx.inventory.populate().filterContains("Dragon halberd").isEmpty() && (firerune >= 5 && naturerune >= 1)) {
        	Zulrah.status = "Alching Dragon halberd";
        	SimpleItem alch = ctx.inventory.populate().filter(3204).next();
            ctx.magic.castSpellOnItem(1178, alch);
        }
		specialAttack(ctx);
		if (range != null && ctx.players.getLocal().getInteracting() == null) {
        	Zulrah.status = "Attacking Zulrah";
        	ctx.log("Attacking Zulrah");
        	if (range.getLocation().equals(zulrahNorth) || range.getLocation().equals(zulrahSouth)) {
        		range.interact(SimpleNpcActions.ATTACK);
        		specialAttack(ctx);
            } else if (range.getLocation().equals(zulrahEast)) {
            	if (cloud != null && !ctx.players.getLocal().getLocation().equals(east)) {
            		ctx.pathing.walkToTile(east);
            	} else {
                	range.interact(SimpleNpcActions.ATTACK);
                	specialAttack(ctx);
            	}
            } else if (range.getLocation().equals(zulrahWest)) {
    			range.interact(SimpleNpcActions.ATTACK);
    			specialAttack(ctx);
            }
        }
        if (mage != null && ctx.players.getLocal().getInteracting() == null) {
        	Zulrah.status = "Attacking Zulrah";
        	ctx.log("Attacking Zulrah");
        	 if (mage.getLocation().equals(zulrahNorth)) {
        		mage.interact(SimpleNpcActions.ATTACK);
  	        	specialAttack(ctx);
             } else if (mage.getLocation().equals(zulrahSouth)) {
            	Random rn = new Random();
         		int answer = rn.nextInt(3);
             	if (!ctx.players.getLocal().getLocation().equals(west) && !ctx.players.getLocal().getLocation().equals(west2) && !ctx.players.getLocal().getLocation().equals(melee3) && !ctx.players.getLocal().getLocation().equals(melee4) && !ctx.players.getLocal().getLocation().equals(melee5)) {
             		if (answer == 0) {
             			ctx.pathing.walkToTile(melee3);
             			ctx.sleep(1000);
             		} else if (answer == 1) {
             			ctx.pathing.walkToTile(melee4);
             			ctx.sleep(1000);
             		} else if (answer == 2) {
             			ctx.pathing.walkToTile(melee5);
             			ctx.sleep(1000);
             		}
             	} else {
             		mage.interact(SimpleNpcActions.ATTACK);
      	        	specialAttack(ctx);
             	}
             } else if (mage.getLocation().equals(zulrahEast)) {
            	mage.interact(SimpleNpcActions.ATTACK);
  	        	specialAttack(ctx);
             } else if (mage.getLocation().equals(zulrahWest) && cloud != null) {
     			if (!ctx.players.getLocal().getLocation().equals(west)) {
                 	ctx.pathing.walkToTile(west);
     			} else {
     	        	mage.interact(SimpleNpcActions.ATTACK);
     	        	specialAttack(ctx);
     			}
             }
        }
        if (melee != null && ctx.players.getLocal().getInteracting() == null) {
        	Zulrah.status = "Attacking Zulrah";
        	ctx.log("Attacking Zulrah");
        	Random rn = new Random();
    		int answer = rn.nextInt(4);
        	if (!ctx.players.getLocal().getLocation().equals(melee1) && !ctx.players.getLocal().getLocation().equals(melee2) && !ctx.players.getLocal().getLocation().equals(melee3) && !ctx.players.getLocal().getLocation().equals(melee4) && !ctx.players.getLocal().getLocation().equals(melee5)) {
        		if (answer == 0) {
        			ctx.pathing.walkToTile(melee1);
        			ctx.sleep(1000);
        		} else if (answer == 1) {
        			ctx.pathing.walkToTile(melee2);
        			ctx.sleep(1000);
        		} else if (answer == 2) {
        			ctx.pathing.walkToTile(melee3);
        			ctx.sleep(1000);
        		} else if (answer == 3) {
        			ctx.pathing.walkToTile(melee4);
        			ctx.sleep(1000);
        		}
        	} else {
        		melee.interact(SimpleNpcActions.ATTACK);
        		specialAttack(ctx);
        	}
        }
		if (ctx.players.getLocal().getLocation().equals(start) && !Zulrah.dead) {
			Random rn = new Random();
    		int answer = rn.nextInt(3);
    		if (answer == 0) {
    			ctx.pathing.walkToTile(melee1);
    			ctx.sleep(1000);
    		} else if (answer == 1) {
    			ctx.pathing.walkToTile(melee2);
    			ctx.sleep(1000);
    		} else if (answer == 2) {
    			ctx.pathing.walkToTile(melee3);
    			ctx.sleep(1000);
    		}
		}
    }
    
    public static void specialAttack(ClientContext ctx) {
		if (!ctx.equipment.populate().filter(12926).isEmpty() && ctx.combat.health() <= ctx.combat.maxHealth() - 25 && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().getInteracting() != null) {
			ctx.combat.toggleSpecialAttack(true);
			Zulrah.status = "Using Special Attack";
			ctx.log("Using Special Attack");
			ctx.onCondition(() -> ctx.combat.specialAttack(), 250, 6);
		}
		if (!ctx.equipment.populate().filter(12788).isEmpty() && ctx.combat.getSpecialAttackPercentage() >= 50 && ctx.players.getLocal().getInteracting() != null) {
			ctx.combat.toggleSpecialAttack(true);
			Zulrah.status = "Using Special Attack";
			ctx.log("Using Special Attack");
			ctx.onCondition(() -> ctx.combat.specialAttack(), 250, 6);
		}
    }
    
	public static void handleEatingFood(ClientContext ctx) {
		SimpleItem food = ctx.inventory.populate().filterHasAction("Eat").next();
		SimpleItem brew = ctx.inventory.populate().filterContains("Saradomin brew").next();
		if (food != null && ctx.combat.health() <= Zulrah.eatAt && ctx.combat.health() != 10) {
			Zulrah.status = "Eating " + food.getName();
			ctx.log("Eating " + food.getName());
			food.interact(SimpleItemActions.CONSUME);
		} else if (brew != null && ctx.combat.health() <= Zulrah.eatAt && ctx.combat.health() != 10) {
			Zulrah.status = "Eating " + brew.getName();
			ctx.log("Drinking " + brew.getName());
			brew.interact(SimpleItemActions.DRINK);
		}
	}
	
	public static void recoil(ClientContext ctx) {
		SimpleItem equipRecoil = ctx.inventory.populate().filter(2550).next();
		if (Zulrah.recoil && equipRecoil != null && ctx.equipment.populate().filter(2550).isEmpty()) {
			equipRecoil.interact(SimpleItemActions.WEAR);
			Zulrah.status = "Equipping Recoil";
			ctx.log("Equipping Recoil");
			Zulrah.recoil = false;
		}
	}
}
