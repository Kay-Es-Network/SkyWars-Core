package it.aendrix.skywars.items;

import org.bukkit.inventory.ItemStack;

public class Item {

    private ItemStack item;
    private int minInGame, maxInGame;

    public Item(ItemStack item, int maxInGame, int minInGame) {
        this.item = item;
        this.maxInGame = maxInGame;
        this.minInGame = minInGame;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getMaxInGame() {
        return maxInGame;
    }

    public void setMaxInGame(int maxInGame) {
        this.maxInGame = maxInGame;
    }

    public int getMinInGame() {
        return minInGame;
    }

    public void setMinInGame(int minInGame) {
        this.minInGame = minInGame;
    }
}
