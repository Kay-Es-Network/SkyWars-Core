package it.aendrix.skywars.main.utils;

import it.aendrix.skywars.items.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class utils {
    public static boolean blockRemoveList(Block block, ArrayList<Block> blocks) {
        return blocks.remove(block);
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static Location getLocationString(FileConfiguration cfg, String path) {
        if (cfg.getString(path) == null)
            return null;
        String[] x = cfg.getString(path).split(";");
        return new Location(
                Bukkit.getWorld(x[0]),
                Double.parseDouble(x[1]),
                Double.parseDouble(x[2]),
                Double.parseDouble(x[3]),
                Float.parseFloat(x[4]),
                Float.parseFloat(x[5]));
    }

    public static Location getLocationString(String str) {
        if (str == null)
            return null;
        String[] x = str.split(";");
        return new Location(
                Bukkit.getWorld(x[0]),
                Double.parseDouble(x[1]),
                Double.parseDouble(x[2]),
                Double.parseDouble(x[3]),
                Float.parseFloat(x[4]),
                Float.parseFloat(x[5]));
    }

    public static Location[] getLocationList(FileConfiguration cfg, String path) {
        ArrayList<Location> l = new ArrayList<>();
        if (cfg.getString(path) == null)
            return null;
        for (String key : cfg.getConfigurationSection(path).getKeys(false))
            l.add(getLocationString(cfg, path + "." + path));
        return l.toArray(new Location[l.size()]);
    }

    public static String locationString(Location location) {
        if (location == null)
            return null;
        return location.getWorld().getName() + ";" + location
                .getX() + ";" + location
                .getY() + ";" + location
                .getZ() + ";" + location
                .getYaw() + ";" + location
                .getPitch();
    }

    public static void sendMsg(Player p, String s) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void sendMsg(CommandSender p, String s) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void mergeArray(Object[] son, Object[] father) {
        if (son == null || father == null)
            return;
        if (father.length > son.length) {
            for (int i = 0; i < son.length; i++)
                son[i] = father[i];
        } else {
            for (int i = 0; i < father.length; i++)
                son[i] = father[i];
        }
    }

    public static void removeFromArray(Object[] a, Object b) {
        if (a == null)
            return;
        ArrayList<Object> c = new ArrayList();
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b))
                c.add(a[i]);
        }
        a = c.toArray(new Object[c.size()]);
    }

    public static ItemStack copyItem(ItemStack x) {
        ItemStack it = new ItemStack(x.getType(), x.getAmount(), x.getData().getData());
        ItemMeta itm = it.getItemMeta();
        if (itm instanceof PotionMeta) {
            PotionMeta pm = (PotionMeta)itm;
            pm.setColor(((PotionMeta)x.getItemMeta()).getColor());
            pm.setBasePotionData(((PotionMeta)x.getItemMeta()).getBasePotionData());
            pm.setLore(x.getItemMeta().getLore());
            pm.setDisplayName(x.getItemMeta().getDisplayName());
            for (Enchantment e : x.getEnchantments().keySet())
                pm.addEnchant(e, x.getItemMeta().getEnchantLevel(e), true);
            for (ItemFlag v : x.getItemMeta().getItemFlags()) {
                pm.addItemFlags(v);
            }
            it.setItemMeta(pm);
        } else if (itm instanceof SkullMeta) {
            SkullMeta sm = (SkullMeta)itm;
            sm.setOwningPlayer(((SkullMeta)x.getItemMeta()).getOwningPlayer());
            sm.setLore(x.getItemMeta().getLore());
            sm.setDisplayName(x.getItemMeta().getDisplayName());
            for (Enchantment e : x.getEnchantments().keySet())
                sm.addEnchant(e, x.getItemMeta().getEnchantLevel(e), true);
            for (ItemFlag v : x.getItemMeta().getItemFlags()) {
                sm.addItemFlags(v);
            }
            it.setItemMeta(sm);
        } else {
            itm.setLore(x.getItemMeta().getLore());
            itm.setDisplayName(x.getItemMeta().getDisplayName());
            for (Enchantment e : x.getEnchantments().keySet())
                itm.addEnchant(e, x.getItemMeta().getEnchantLevel(e), true);
            for (ItemFlag v : x.getItemMeta().getItemFlags()) {
                itm.addItemFlags(v);
            }
            it.setItemMeta(itm);
        }
        return it;
    }

    public static Item toItem(ItemStack x) {
        ItemMeta meta = x.getItemMeta();
        if (!meta.hasLore() || meta.getLore().size() < 2)
            return new Item(x, 0, 10);
        String[] lore = (String[])meta.getLore().toArray((Object[])new String[meta.getLore().size()]);
        if (!lore[lore.length - 1].contains("Min:") || !lore[lore.length - 2].contains("Max:"))
            return new Item(x, 0, 10);
        int min = 1, max = 5;
        try {
            min = Integer.parseInt(lore[lore.length - 1].split(":")[1]);
            max = Integer.parseInt(lore[lore.length - 2].split(":")[1]);
        } catch (NumberFormatException numberFormatException) {}
        if (min > max) {
            int v = min;
            min = max;
            max = v;
        }
        List<String> ll = new ArrayList<>();
        for (int i = 0; i < lore.length - 2; i++)
            ll.add(ChatColor.translateAlternateColorCodes('&', lore[i]));
        meta.setLore(ll);
        ItemStack f = copyItem(x);
        f.setItemMeta(meta);
        return new Item(f, max, min);
    }

    public static ArrayList<ItemStack> itemListCreator(ArrayList<Item> s) {
        ArrayList<ItemStack> x = new ArrayList<>();
        for (Item it : s) {
            int n = (int)(Math.random() * (it.getMaxInGame() - it.getMinInGame()) + it.getMinInGame());
            while (n > 0) {
                x.add(it.getItem());
                n--;
            }
        }
        return x;
    }

    public static ItemStack itemChoser(ArrayList<ItemStack> s, ArrayList<Item> v) {
        if (s.size() == 0)
            s = itemListCreator(v);
        int x = (int)(Math.random() * s.size());
        ItemStack item = s.get(x);
        s.remove(x);
        return item;
    }

    public static ItemStack itemcreate(Material material, short data, int amount, String name, String[] lore, boolean glow) {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if (lore != null) {
            ArrayList<String> l = new ArrayList<>();
            for (String s : lore)
                l.add(ChatColor.translateAlternateColorCodes('&', s));
            meta.setLore(l);
        }
        if (glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        }
        item.setItemMeta(meta);
        return item;
    }
}
