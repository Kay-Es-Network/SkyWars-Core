package it.aendrix.skywars.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public void onEnable() {
        instance=this;
    }

    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }


}
