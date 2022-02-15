package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.main.Messages;
import it.aendrix.skywars.main.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ChestAdmin implements Listener {
    private static HashMap<String, Arena> admins = new HashMap<>();

    private static HashMap<String, Inventory> adminsexinv = new HashMap<>();

    public static void toggleAdmin(Player player, Arena arena) {
        if (admins.containsKey(player.getName())) {
            player.getInventory().clear();
            Inventory inv = adminsexinv.get(player.getName());
            for (int i = 0; i < inv.getSize(); i++)
                player.getInventory().setItem(i, inv.getItem(i));
            admins.remove(player.getName());
            adminsexinv.remove(player.getName());
            if (player.isOnline())
                Messages.sendMessage(player, "skywars.commands.chestadmin-disabled", null);
        } else {
            Inventory inv = Bukkit.createInventory(null, 36);
            for (int i = 0; i < 36; i++) {
                inv.setItem(i, player.getInventory().getItem(i));
                player.getInventory().setItem(i, null);
            }
            player.getInventory().setItem(0, utils.itemcreate(Material.COAL_BLOCK, (short)0, 64, "&fPiazza &bChest lvl &l1", null, false));
            player.getInventory().setItem(1, utils.itemcreate(Material.IRON_BLOCK, (short)0, 64, "&fPiazza &bChest lvl &l2", null, false));
            player.getInventory().setItem(2, utils.itemcreate(Material.GOLD_BLOCK, (short)0, 64, "&fPiazza &bChest lvl &l3", null, false));
            player.getInventory().setItem(3, utils.itemcreate(Material.DIAMOND_BLOCK, (short)0, 64, "&fPiazza &bChest lvl &l4", null, false));
            player.getInventory().setItem(4, utils.itemcreate(Material.EMERALD_BLOCK, (short)0, 64, "&fPiazza &bChest lvl &l5", null, false));
            admins.put(player.getName(), arena);
            adminsexinv.put(player.getName(), inv);
            if (player.isOnline())
                Messages.sendMessage(player, "skywars.commands.chestadmin-enabled", null);
        }
    }

    public static HashMap<String, Arena> getAdmins() {
        return admins;
    }

    public static void setAdmins(HashMap<String, Arena> admins) {
        ChestAdmin.admins = admins;
    }

    public static HashMap<String, Inventory> getAdminsexinv() {
        return adminsexinv;
    }

    public static void setAdminsexinv(HashMap<String, Inventory> adminsexinv) {
        ChestAdmin.adminsexinv = adminsexinv;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (admins.containsKey(p.getName()))
            toggleAdmin(p, null);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location loc = b.getLocation();
        if (!admins.containsKey(p.getName()))
            return;
        Arena arena = admins.get(p.getName());
        if (b.getType().equals(Material.COAL_BLOCK)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().put(b.getLocation(), 1);
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().put(b.getLocation(), 1);
        } else if (b.getType().equals(Material.IRON_BLOCK)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().put(b.getLocation(), 2);
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().put(b.getLocation(), 2);
        } else if (b.getType().equals(Material.GOLD_BLOCK)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().put(b.getLocation(), 3);
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().put(b.getLocation(), 3);
        } else if (b.getType().equals(Material.DIAMOND_BLOCK)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().put(b.getLocation(), 4);
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().put(b.getLocation(), 4);
        } else if (b.getType().equals(Material.EMERALD_BLOCK)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().put(b.getLocation(), 5);
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().put(b.getLocation(), 5);
        } else {
            return;
        }
        Messages.sendMessage(p, "skywars.commands.chest-added", null);
        loc.getBlock().setType(Material.CHEST);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (!admins.containsKey(p.getName()))
            return;
        Arena arena = admins.get(p.getName());
        if (b.getType().equals(Material.CHEST)) {
            if (arena instanceof SkyWarsArena)
                ((SkyWarsArena)arena).getChests().remove(b.getLocation());
            else if (arena instanceof SkyWarsTeamArena)
                ((SkyWarsTeamArena)arena).getChests().remove(b.getLocation());
        } else {
            return;
        }
        Messages.sendMessage(p, "skywars.commands.chest-removed", null);
    }
}
