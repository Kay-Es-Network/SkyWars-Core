package it.aendrix.skywars.GUI;

import it.aendrix.skywars.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GUI {

    protected Inventory inventory;
    protected ArrayList<Player> user;

    public GUI(String name, int pos) {
        if (pos%9!=0)
            pos = 9;
        this.inventory = Bukkit.createInventory(null, pos, ChatColor.translateAlternateColorCodes('&', name));

        this.user = new ArrayList<>();
    }

    public void initialize(ItemStack[] items) {
        for (int i = 0; i<items.length && i<inventory.getSize(); i++)
            this.inventory.setItem(i, items[i]);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Player> getUser() {
        return user;
    }

    public void setUser(ArrayList<Player> user) {
        this.user = user;
    }

    public boolean using(Player p) {
        for (Player u : user)
            if (u.getName().equalsIgnoreCase(p.getName()))
                return true;
        return false;
    }

    public void open(Player p) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            p.closeInventory();
            p.openInventory(inventory);
            user.add(p);

        });
    }
}
