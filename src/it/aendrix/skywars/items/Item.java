package it.aendrix.skywars.items;

import it.aendrix.skywars.main.utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private ItemStack item;
    private int minInGame, maxInGame;

    public Item(ItemStack item, int maxInGame, int minInGame) {
        this.item = item;
        this.maxInGame = maxInGame;
        this.minInGame = minInGame;
    }

    public Item(Item item) {
        this.item = item.item;
        this.maxInGame = item.maxInGame;
        this.minInGame = item.minInGame;
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

    public ItemStack toItemStack() {
        ItemStack n = utils.copyItem(item);
        ItemMeta meta = n.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore())
            lore = meta.getLore();

        lore.add("Max:"+maxInGame);
        lore.add("Min:"+minInGame);
        meta.setLore(lore);
        n.setItemMeta(meta);

        return n;
    }
}
