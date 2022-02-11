package it.aendrix.skywars.main;

import it.aendrix.skywars.GUI.SkyWarsTypeEdit.EditMenu;
import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.ArenaEvents;
import it.aendrix.skywars.arena.Loader;
import it.aendrix.skywars.commands.SkyWarsArenaCommands;
import it.aendrix.skywars.main.utils.MessageRequire;
import it.aendrix.skywars.skywars.ChestAdmin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin {
    private static Main instance;
    private static Loader loader;
    private static Messages messages;
    private static HashMap<String, Arena> players;

    public void onEnable() {
        instance = this;
        (new File("plugins/SkyWars-Core")).mkdir();
        players = new HashMap<>();
        loader = new Loader();
        loader.loadAll();
        messages = new Messages();
        loadCommands();
        loadEvents();
    }

    public void onDisable() {
        if (loader != null)
            loader.unloadAll();
    }

    private void loadCommands() {
        getCommand("skywars").setExecutor(new SkyWarsArenaCommands());
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new MessageRequire(), this);
        getServer().getPluginManager().registerEvents(new EditMenu(), this);
        getServer().getPluginManager().registerEvents(new ChestAdmin(), this);
        getServer().getPluginManager().registerEvents(new ArenaEvents(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static Loader getLoader() {
        return loader;
    }

    public static Messages getMessages() {
        return messages;
    }

    public static HashMap<String, Arena> getPlayers() {
        return players;
    }
}
