package com.ClassicRockFan.ShockWave.engine.entities.characters;


import com.ClassicRockFan.ShockWave.engine.entities.items.InventoryItem;

import java.util.HashMap;

public class Inventory {

    private int slots;
    private HashMap<InventoryItem, Integer> items;

    public Inventory() {
        this(100);
    }

    public Inventory(int slots) {
        this.slots = slots;
        this.items = new HashMap<InventoryItem, Integer>(slots);
    }

    public int getSlots() {
        return slots;
    }

    public HashMap<InventoryItem, Integer> getItems() {
        return items;
    }

    public boolean addItem(InventoryItem item) {
        if (items.containsKey(item)) {
            int num = items.get(item);
            items.remove(item);
            items.put(item, num + 1);
            return true;
        } else {
            items.put(item, 1);
            return false;
        }
    }

    public boolean removeItem(InventoryItem item) {
        if (items.containsKey(item)) {
            int num = items.get(item);
            items.remove(item);
            if (num != 1)
                items.put(item, num - 1);
            return true;
        } else {
            return false;
        }
    }
}
