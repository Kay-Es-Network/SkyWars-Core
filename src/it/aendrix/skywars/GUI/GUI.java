package it.aendrix.skywars.GUI;

import it.aendrix.skywars.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GUI {
    protected Inventory inventory;

    protected ArrayList<Player> user;

    public GUI(String name, int pos) {
        if (pos % 9 != 0)
            pos = 9;
        this.inventory = Bukkit.createInventory(null, pos, ChatColor.translateAlternateColorCodes('&', name));
        this.user = new ArrayList<>();
    }

    public void initialize(ItemStack[] items) {
        for (int i = 0; i < items.length && i < this.inventory.getSize(); i++)
            this.inventory.setItem(i, items[i]);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Player> getUser() {
        return this.user;
    }

    public void setUser(ArrayList<Player> user) {
        this.user = user;
    }

    public boolean using(Player p) {
        for (Player u : this.user) {
            if (u.getName().equalsIgnoreCase(p.getName()))
                return true;
        }
        return false;
    }

    public void open(Player p) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            p.closeInventory();
            p.openInventory(this.inventory);
            this.user.add(p);
        });
    }
}
