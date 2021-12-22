package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.BaseArena;
import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.items.Title;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class SkyWarsArena extends Arena implements BaseArena, Listener {

    public static HashMap<String, SkyWarsArena> arene;
    private SkyWarsType type;
    private Title title;
    //Shops e altro

    public SkyWarsArena(String name, int maxPlayers, int minPlayers, int timeToStart, SkyWarsType type, Location lobbyLocation, Location specLocation, Location[] spawnLocations) {
        super(name, maxPlayers, minPlayers, timeToStart, lobbyLocation, specLocation, spawnLocations);
        this.type = type;

        if (arene==null) arene = new HashMap<>();
        arene.put(name.toUpperCase(),this);

        this.title = new Title();
        title.setFadeInTime(0);
        title.setFadeOutTime(0);
        title.setStayTime(1);
        title.setTitle("");

        super.setState(State.STOPPED);

        Main.getInstance().getServer().getPluginManager().registerEvents(this,Main.getInstance());
    }

    public SkyWarsArena() {
        super();
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

                    if (time <= 11)
                        setState(State.STARTING);

                    if (getPlayers().size()>=getMaxPlayers()) {
                        setState(State.STARTING);
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

                    if (time<=0)
                        setState(State.RESTARTING);

                }
                else if (getState().equals(State.RESTARTING)) {

                    /*
                        IL GIOCO SI STA RIAVVIANDO
                     */

                    stop();
                    setState(State.WAITING);
                }
                else if (getState().equals(State.STARTING)) {

                    /*
                        IL GIOCO SI STA AVVIANDO
                     */

                    if (getPlayers().size()<getMaxPlayers()) {
                        setState(State.READY);
                        time = timeToStart;
                    }

                    if (time == 0) {
                        time = type.getTimeMax();

                        title.setTitle(utils.color("&6&lINIZIO"));

                        //Carica chest
                        int i = 0;
                        //Teletrasporta tutti i giocatori in game
                        for (Player p : getPlayers()) {
                            p.teleport(spawnLocations[i]);
                            title.send(p);
                            i++;
                        }
                    } else {
                        title.setTitle(utils.color("&b&l"+time));
                        for (Player p : players)
                            title.send(p);
                    }

                }

                if (!getState().equals(State.STOPPED))
                    time--;
            }

        }.runTaskTimer(Main.getInstance(),20L,20L);

    }

    @Override
    public void stop() {

        for (Player p : super.getPlayers()) {
            p.teleport(lobbyLocation);
        }

        reset();
    }

    @Override
    public void reset() {
        for (Block block : super.placedBlocks)
            block.getLocation().getBlock().setType(Material.AIR);

        for (Block block : super.brokenBlocks)
            block.getLocation().getBlock().setType(block.getType());

        super.brokenBlocks.clear();
        super.placedBlocks.clear();
        super.players.clear();

        super.time = super.timeToStart;
        super.state = State.STARTING;
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

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (super.containsPlayer(p.getName())) {
            Block block = e.getBlock();
            if (!utils.blockRemoveList(block, super.getPlacedBlocks()))
                super.getBrokenBlocks().add(block);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (super.containsPlayer(p.getName()))
            super.getPlacedBlocks().add(e.getBlock());
    }


}
