package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.BaseArena;
import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.main.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class SkyWarsArena extends Arena implements BaseArena {

    public static HashMap<String, SkyWarsArena> arene;
    private SkyWarsType type;
    //Shops e altro

    public SkyWarsArena(String name) {
        super();

        if (arene==null) arene = new HashMap<>();
        arene.put(name,this);
    }

    public SkyWarsArena(String name, int maxPlayers, int minPlayers, int timeToStart, int timeMax, SkyWarsType type) {
        super(name, maxPlayers, minPlayers, timeToStart, timeMax);
        this.type = type;

        if (arene==null) arene = new HashMap<>();
        arene.put(name,this);
    }

    @Override
    public void start() {

        new BukkitRunnable() {

            @Override
            public void run() {

                if (getState().equals(State.READY)) {

                    if (getPlayers().size()>=getMaxPlayers()) {
                        setState(State.FULL);
                        time = 10;
                    } else if (getPlayers().size()<getMinPlayers()) {
                        setState(State.WAITING);
                        time = timeToStart;
                    }

                } else if (getState().equals(State.WAITING)) {

                    if (getPlayers().size()>=getMinPlayers())
                        setState(State.READY);

                } else if (getState().equals(State.INGAME)) {

                } else if (getState().equals(State.FULL)) {

                    if (getPlayers().size()<getMaxPlayers()) {
                        setState(State.READY);
                        time = timeToStart;
                    }

                } else if (getState().equals(State.STOPPED)) {

                } else if (getState().equals(State.RESTARTING)) {



                }

                setTime(getTime()-1);
            }

        }.runTaskTimer(Main.getInstance(),20L,20L);

    }

    @Override
    public void stop() {

    }

    public static HashMap<String, SkyWarsArena> getArene() {
        return arene;
    }

    public static void setArene(HashMap<String, SkyWarsArena> arene) {
        SkyWarsArena.arene = arene;
    }


    public SkyWarsType getType() {
        return type;
    }

    public void setType(SkyWarsType type) {
        this.type = type;
    }
}
