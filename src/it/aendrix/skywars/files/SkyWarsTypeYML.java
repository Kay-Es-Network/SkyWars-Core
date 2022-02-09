package it.aendrix.skywars.files;

import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.items.Item;
import it.aendrix.skywars.skywars.SkyWarsType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SkyWarsTypeYML implements FileYML {
    private static final File file = new File(directory + File.separator + "chests.yml");

    private static final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public SkyWarsTypeYML() {
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public Object getObject(String name) {
        if (name == null)
            return null;
        if (cfg.getString("types." + name) == null)
            return null;
        ArrayList<Chest> c = new ArrayList<>();
        if (cfg.getString("types." + name + ".chests") != null)
            for (String key : cfg.getConfigurationSection("types." + name + ".chests").getKeys(false)) {
                ArrayList<Item> it = new ArrayList<>();
                if (cfg.getString("types." + name + ".chests." + key + ".items") != null)
                    for (String itkey : cfg.getConfigurationSection("types." + name + ".chests." + key + ".items").getKeys(false))
                        it.add(new Item(cfg
                                .getItemStack("types." + name + ".chests." + key + ".items." + itkey + ".item"), cfg
                                .getInt("types." + name + ".chests." + key + ".items." + itkey + ".maxInGame"), cfg
                                .getInt("types." + name + ".chests." + key + ".items." + itkey + ".minInGame")));
                c.add(new Chest(cfg
                        .getInt("types." + name + ".chests." + key + ".minItems"), cfg
                        .getInt("types." + name + ".chests." + key + ".maxItems"), cfg
                        .getInt("types." + name + ".chests." + key + ".level"), it));
            }
        return new SkyWarsType(cfg.getString("types." + name + ".name"), cfg.getInt("types." + name + ".timeMax"), cfg.getInt("types." + name + ".pointOnJoin"), cfg.getInt("types." + name + ".pointOnKill"), cfg.getInt("types." + name + ".pointOnKill"), c.toArray(new Chest[c.size()]));
    }

    public String[] listObjects() {
        if (cfg.getString("types") == null)
            return new String[0];
        ArrayList<String> a = new ArrayList<>(cfg.getConfigurationSection("types").getKeys(false));
        return a.toArray(new String[a.size()]);
    }

    public static void save(SkyWarsType type) {
        cfg.set("types." + type.getName() + ".name", type.getName());
        cfg.set("types." + type.getName() + ".timeMax", type.getTimeMax());
        cfg.set("types." + type.getName() + ".pointOnJoin", type.getPointOnJoin());
        cfg.set("types." + type.getName() + ".pointOnKill", type.getPointOnKill());
        cfg.set("types." + type.getName() + ".pointOnWin", type.getPointOnWin());
        cfg.set("types." + type.getName() + ".chests", null);
        for (int i = 0; i < (type.getChestsType()).length; i++) {
            Chest c = type.getChestsType()[i];
            cfg.set("types." + type.getName() + ".chests." + i + ".minItems", c.getMinItems());
            cfg.set("types." + type.getName() + ".chests." + i + ".maxItems", c.getMaxItems());
            cfg.set("types." + type.getName() + ".chests." + i + ".level", c.getLevel());
            for (int j = 0; j < c.getItems().size(); j++) {
                Item x = c.getItems().get(j);
                cfg.set("types." + type.getName() + ".chests." + i + ".items." + j + ".item", x.getItem());
                cfg.set("types." + type.getName() + ".chests." + i + ".items." + j + ".minInGame", x.getMinInGame());
                cfg.set("types." + type.getName() + ".chests." + i + ".items." + j + ".maxInGame", x.getMaxInGame());
            }
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
