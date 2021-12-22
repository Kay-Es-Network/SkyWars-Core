package it.aendrix.skywars.files;

import it.aendrix.skywars.main.utils;
import it.aendrix.skywars.skywars.SkyWarsArena;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ArenaYML implements FileYML{

    private final static File file = new File("plugins/SkyWars-Core" + File.separator + "arena.yml");
    private final static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public ArenaYML() {
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
        if (cfg.getString("arenas."+name) == null) {
            return null;
        }

        if (cfg.getString("arenas."+name+".arena").equalsIgnoreCase("SKYWARS")) {
            Location[] spawns = new Location[cfg.getInt("arenas."+name+".maxPlayers")];
            utils.mergeArray(spawns,utils.getLocationList(cfg, "arenas."+name+".spawn"));

            SkyWarsArena arena = new SkyWarsArena(
                    cfg.getString("arenas."+name+".name"),
                    cfg.getInt("arenas."+name+".maxPlayers"),
                    cfg.getInt("arenas."+name+".minPlayers"),
                    cfg.getInt("arenas."+name+".timeToStart"),
                    (SkyWarsType) new SkyWarsTypeYML().getObject(cfg.getString("arenas."+name+".type")),
                    utils.getLocationString(cfg, "arenas."+name+".lobby"),
                    utils.getLocationString(cfg, "arenas."+name+".spec"),
                    spawns
            );
        }

        return null;
    }

    @Override
    public String[] listObjects() {
        if (cfg.getString("arenas")==null)
            return new String[0];

        ArrayList<String> a = new ArrayList<>();

        for(String key : cfg.getConfigurationSection("arenas").getKeys(false))
            a.add(key);

        return a.toArray(new String[a.size()]);
    }

    public static void save(SkyWarsArena arena) {
        cfg.set("arenas."+arena.getName()+".arena", "SKYWARS");
        cfg.set("arenas."+arena.getName()+".name", arena.getName());
        cfg.set("arenas."+arena.getName()+".maxPlayers", arena.getMaxPlayers());
        cfg.set("arenas."+arena.getName()+".minPlayers", arena.getMinPlayers());
        cfg.set("arenas."+arena.getName()+".timeToStart", arena.getTimeToStart());
        cfg.set("arenas."+arena.getName()+".type", arena.getType());
        cfg.set("arenas."+arena.getName()+".lobby", utils.locationString(arena.getLobbyLocation()));
        cfg.set("arenas."+arena.getName()+".spec", utils.locationString(arena.getSpecLocation()));
        if(arena.getSpawnLocations()!=null)
            for (int i = 0; i<arena.getSpawnLocations().length; i++)
                if (arena.getSpawnLocations()[i]!=null)
                    cfg.set("arenas."+arena.getName()+".spawn."+i , utils.locationString(arena.getSpawnLocations()[i]));

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void remove(String name) {
        cfg.set("arenas."+name,null);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
