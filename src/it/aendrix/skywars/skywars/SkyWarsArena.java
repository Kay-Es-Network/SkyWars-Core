package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.BaseArena;
import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.main.Main;
import org.bukkit.Location;
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

    public SkyWarsArena(String name, int maxPlayers, int minPlayers, int timeToStart, SkyWarsType type, Location lobbyLocation, Location specLocation, Location[] spawnLocations) {
        super(name, maxPlayers, minPlayers, timeToStart, lobbyLocation, specLocation, spawnLocations);
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

                    /*
                        IL GIOCO è PRONTO PER INIZIARE IN QUANTO è STATO RAGGIUNTO IL
                        MINIMO DI GIOCATORI PER GIOCARE
                     */

                    if (time <= 0)
                        setState(State.STARTING);

                    time--;

                    if (getPlayers().size()>=getMaxPlayers()) {
                        setState(State.FULL);
                        time = 10;
                    } else if (getPlayers().size()<getMinPlayers()) {
                        setState(State.WAITING);
                        time = timeToStart;
                    }

                }
                else if (getState().equals(State.WAITING)) {

                    /*
                        IL GIOCO NON POSSIEDE ANCORA ABBASTANZA GIOCATORI PER INIZIARE
                     */

                    if (getPlayers().size()>=getMinPlayers())
                        setState(State.READY);
                }
                else if (getState().equals(State.INGAME)) {

                    /*
                        IL GIOCO è INIZIATO
                     */

                    time--;

                }
                else if (getState().equals(State.FULL)) {

                    /*
                        IL GIOCO HA RAGGIUNTO IL MASSIMO DI GIOCATORI PER GIOCARE.
                        INIZIERà PIù VELOCEMENTE
                     */

                    if (time <= 0)
                        setState(State.STARTING);

                    time--;

                    if (getPlayers().size()<getMaxPlayers()) {
                        setState(State.READY);
                        time = timeToStart;
                    }

                }
                else if (getState().equals(State.STOPPED)) {

                    /*
                        IL GIOCO NON PUò INIZIARE E DOVRà ESSERE AVVIATO DA UN AMMINISTRATORE
                     */

                } else if (getState().equals(State.RESTARTING)) {

                    /*
                        IL GIOCO SI STA RIAVVIANDO
                     */

                } else if (getState().equals(State.STARTING)) {

                    /*
                        IL GIOCO SI STA AVVIANDO
                     */

                    time = type.getTimeMax();
                    //Carica chest
                    int i = 0;
                    //Teletrasporta tutti i giocatori in game
                    for (Player p : getPlayers()) {
                        p.teleport(spawnLocations[i]);
                        i++;
                    }

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
