package it.aendrix.skywars.files;

import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.items.Item;
import it.aendrix.skywars.skywars.SkyWarsArena;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SkyWarsTypeYML implements FileYML{

    private final static File file = new File("plugins/SkyWars-Core" + File.separator + "chests.yml");
    private final static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public SkyWarsTypeYML() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getObject(String name) {
        if (name == null)
            return null;

        if (cfg.getString("types."+name) == null) {
            return null;
        }

        ArrayList<Chest> c = new ArrayList<>();
        ArrayList<Item> it = new ArrayList<Item>();

        if (cfg.getString("types." + name + ".chests")!=null) {

            for (String key : cfg.getConfigurationSection("types." + name + ".chests").getKeys(false)) {
                it.clear();
                if (cfg.getString("types." + name + ".chests." + key + ".items")!=null) {
                    for (String itkey : cfg.getConfigurationSection("types." + name + ".chests." + key + ".items").getKeys(false)) {
                        it.add(
                                new Item(cfg.getItemStack("types." + name + ".chests." + key + ".items." + itkey + ".item"),
                                        cfg.getInt("types." + name + ".chests." + key + ".items." + itkey + ".maxInGame"),
                                        cfg.getInt("types." + name + ".chests." + key + ".items." + itkey + ".minInGame")
                                ));
                    }
                }

                c.add(new Chest(
                        cfg.getInt("types." + name + ".chests." + key + ".minItems"),
                        cfg.getInt("types." + name + ".chests." + key + ".maxItems"),
                        cfg.getInt("types." + name + ".chests." + key + ".level"),
                        it
                ));
            }

        }

        SkyWarsType type = new SkyWarsType(
            cfg.getString("types."+name+".name"),
            cfg.getInt("types."+name+".timeMax"),
            cfg.getInt("types."+name+".pointOnJoin"),
            cfg.getInt("types."+name+".pointOnKill"),
            cfg.getInt("types."+name+".pointOnWin"),
            c.toArray(new Chest[c.size()])
        );

        return type;
    }

    @Override
    public String[] listObjects() {
        if (cfg.getString("types")==null)
            return new String[0];

        ArrayList<String> a = new ArrayList<>();

        for(String key : cfg.getConfigurationSection("types").getKeys(false))
            a.add(key);

        return a.toArray(new String[a.size()]);
    }
}
