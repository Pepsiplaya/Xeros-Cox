package me.pepsi.xeros.runecrafting.inventory;

import java.util.EventListener;

public interface InventoryChangedListener extends EventListener {
    public void inventoryItemGained(int id, int count);
    public void inventoryItemLost(int id, int count);
}
