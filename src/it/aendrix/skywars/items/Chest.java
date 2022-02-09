package it.aendrix.skywars.items;

import java.util.ArrayList;

public class Chest {
    private int minItems;
    private int maxItems;
    private int level;
    private ArrayList<Item> items;

    public Chest(int minItems, int maxItems, int level, ArrayList<Item> items) {
        this.minItems = minItems;
        this.maxItems = maxItems;
        this.level = level;
        this.items = items;
    }

    public int getMinItems() {
        return this.minItems;
    }

    public void setMinItems(int minItems) {
        this.minItems = minItems;
    }

    public int getMaxItems() {
        return this.maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
