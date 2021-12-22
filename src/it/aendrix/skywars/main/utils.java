package it.aendrix.skywars.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class utils {

    public static boolean blockRemoveList(Block block, ArrayList<Block> blocks) {
        for (int i = 0; i<blocks.size(); i++)
            if (blocks.get(i).getLocation().equals(block.getLocation())) {
                blocks.remove(i);
                return true;
            }
        return false;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static Location getLocationString(FileConfiguration cfg, String path) {
        if (cfg.getString(path)==null)
            return null;
        String[] x = cfg.getString(path).split(";");

        return new Location(
                Bukkit.getWorld(x[0]),
                Double.parseDouble(x[1]),
                Double.parseDouble(x[2]),
                Double.parseDouble(x[3]),
                Float.parseFloat(x[4]),
                Float.parseFloat(x[5])
        );
    }

    public static Location[] getLocationList(FileConfiguration cfg, String path) {
        ArrayList<Location> l = new ArrayList<>();

        if (cfg.getString(path)==null)
            return null;

        for (String key : cfg.getConfigurationSection(path).getKeys(false)) {
            l.add(getLocationString(cfg, path+"."+key));
        }

        return l.toArray(new Location[l.size()]);
    }

    public static String locationString(Location location) {
        if (location == null)
            return null;
        return location.getWorld().getName()+";"+
                location.getX()+";"+
                location.getY()+";"+
                location.getZ()+";"+
                location.getYaw()+";"+
                location.getPitch();
    }

    public static void sendMsg(Player p, String s) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void sendMsg(CommandSender p, String s) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void mergeArray(Object[] son, Object[] father) {
        if (son==null||father==null)
            return;

        if (father.length>son.length)
            for (int i = 0; i < son.length; i++)
                son[i] = father[i];
        else
            for (int i = 0; i < father.length; i++)
                son[i] = father[i];
    }

}
