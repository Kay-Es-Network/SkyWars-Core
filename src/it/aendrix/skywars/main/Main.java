package it.aendrix.skywars.main;

import it.aendrix.skywars.arena.Loader;
import it.aendrix.skywars.commands.SkyWarsArenaCommands;
import it.aendrix.skywars.skywars.SkyWarsArena;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Loader loader;
    private static Messages messages;

    public void onEnable() {
        instance=this;
        new File("plugins/SkyWars-Core").mkdir();

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
        //getServer().getPluginManager().registerEvents((Listener)new SkyWarsArena(),this);
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
}
