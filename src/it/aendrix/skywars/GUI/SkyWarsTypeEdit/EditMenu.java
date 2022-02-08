package it.aendrix.skywars.GUI.SkyWarsTypeEdit;

import it.aendrix.skywars.files.SkyWarsTypeYML;
import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.items.Item;
import it.aendrix.skywars.main.utils;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class EditMenu implements Listener {

    protected static HashMap<String, Chest> users = new HashMap<>();
    protected static HashMap<String, SkyWarsType> exinv = new HashMap<>();

    public static HashMap<String, Chest> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<String, Chest> users) {
        EditMenu.users = users;
    }

    public static HashMap<String, SkyWarsType> getExinv() {
        return exinv;
    }

    public static void setExinv(HashMap<String, SkyWarsType> exinv) {
        EditMenu.exinv = exinv;
    }

    public static void open(Player p, Chest c, SkyWarsType t) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&8&lMODIFICA"));
        for (int i = 0; i<c.getItems().size() && i<54; i++)
            inv.setItem(i, c.getItems().get(i).toItemStack());
        p.openInventory(inv);
        users.put(p.getName(), c);
        exinv.put(p.getName(), t);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (users.containsKey(p.getName())) {
            Chest chest = users.get(p.getName());
            chest.getItems().clear();
            for (int i = 0; i<e.getInventory().getSize(); i++)
                if (e.getInventory().getItem(i)!=null) {
                    chest.getItems().add(utils.toItem(e.getInventory().getItem(i)));
                }
            SkyWarsTypeYML.save(exinv.get(p.getName()));
            new ChestMenu(exinv.get(p.getName())).open(p);
            users.remove(p.getName());
            exinv.remove(p.getName());
        }
    }

}
