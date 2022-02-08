package it.aendrix.skywars.main;

import it.aendrix.skywars.GUI.SkyWarsTypeEdit.EditMenu;
import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.Loader;
import it.aendrix.skywars.commands.SkyWarsArenaCommands;
import it.aendrix.skywars.items.MessageRequire;
import it.aendrix.skywars.skywars.ChestAdmin;
import it.aendrix.skywars.skywars.SkyWarsArena;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Loader loader;
    private static Messages messages;
    private static HashMap<String, Arena> players;

    public void onEnable() {
        instance=this;
        new File("plugins/SkyWars-Core").mkdir();
        players = new HashMap<>();

        loader = new Loader();
        loader.loadAll();

        messages = new Messages();

        loadCommands();
        loadEvents();

    }

    public void onDisable() {
        loader.unloadAll();
    }

    private void loadCommands() {
        this.getCommand("skywars").setExecutor(new SkyWarsArenaCommands());
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents((Listener)new MessageRequire(),this);
        getServer().getPluginManager().registerEvents((Listener)new EditMenu(),this);
        getServer().getPluginManager().registerEvents((Listener)new ChestAdmin(),this);
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
