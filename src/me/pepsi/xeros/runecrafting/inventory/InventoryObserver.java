package me.pepsi.xeros.runecrafting.inventory;

import simple.api.ClientContext;
import simple.api.wrappers.SimpleItem;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryObserver extends Thread {
    private final ClientContext ctx;
    private final ArrayList<InventoryChangedListener> listeners;

    public InventoryObserver(ClientContext ctx) {
        this.ctx = ctx;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        HashMap<Integer, Integer> map = inventoryHashMap();

        while (!Thread.currentThread().isInterrupted()) {
            HashMap<Integer, Integer> updatedMap = inventoryHashMap();
            for (Integer i : updatedMap.keySet()) {
                int countInitial = map.getOrDefault(i, 0), countFinal = updatedMap.get(i);
                if (countFinal > countInitial) {
                    addTrigger(i, countFinal - countInitial);
                } else if (countFinal < countInitial) {
                    subtractedTrigger(i, countInitial - countFinal);
                }
                map.remove(i);
            }
            for (Integer i : map.keySet()) {
                if (!updatedMap.containsKey(i)) {
                    subtractedTrigger(i, map.get(i));
                }
            }

            map = updatedMap;
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public HashMap<Integer, Integer> inventoryHashMap() {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (SimpleItem item : ctx.inventory.populate()) {
            map.put(item.getId(), ctx.inventory.populate().filter(item.getId()).population(true));
        }

        return map;
    }

    public void addListener(InventoryChangedListener inventoryListener) {
        listeners.add(inventoryListener);
    }

    public void addTrigger(int id, int count) {
        for (InventoryChangedListener l : listeners) {
            l.inventoryItemGained(id, count);
        }
    }

    public void subtractedTrigger(int id, int count) {
        for (InventoryChangedListener l : listeners) {
            l.inventoryItemLost(id, count);
        }
    }
}
