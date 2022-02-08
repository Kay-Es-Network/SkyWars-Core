package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.BaseArena;
import it.aendrix.skywars.arena.Border;
import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.exception.PlayerIsNotInGameException;
import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.items.Title;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public final class SkyWarsArena extends Arena implements BaseArena {

    public static HashMap<String, SkyWarsArena> arene;
    private SkyWarsType type;
    private Title title;
    private HashMap<Location , Integer> chests; //Luogo;Livello

    public SkyWarsArena(String name, int maxPlayers, int minPlayers, int timeToStart, SkyWarsType type, Location lobbyLocation, Location specLocation, Location[] spawnLocations, Border border) {
        super(name, maxPlayers, minPlayers, timeToStart, lobbyLocation, specLocation, spawnLocations, border);
        this.type = type;

        if (arene==null) arene = new HashMap<>();
        arene.put(name.toUpperCase(),this);

        this.title = new Title();
        title.setFadeInTime(0);
        title.setFadeOutTime(0);
        title.setStayTime(1);
        title.setTitle("");

        super.setState(State.WAITING);

        if (type != null)
            start();
    }

    @Override
    public void start() {

        new BukkitRunnable() {

            @Override
            public void run() {

                if (getState().equals(State.STOPPED)) {
                    return;
                }
                else if (getState().equals(State.READY)) {

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

                    if (time<=0) {
                        setState(State.RESTARTING);
                        return;
                    }

                    if (players.size()<2) {
                        if (players.size()!=0) {
                            Player winner = players.get(0);
                            //Attenzione alle win senza kill
                        }
                        setState(State.RESTARTING);
                        return;
                    }

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

                    if (getPlayers().size()<getMinPlayers()) {
                        setState(State.WAITING);
                        time = timeToStart;
                    }

                    if (time == 0) {
                        time = type.getTimeMax();

                        title.setTitle(utils.color("&6&lINIZIO"));

                        //Carica chest
                        Chest[] chestlevels = type.getChestsType();

                        HashMap<Location, Integer> chestscopy = new HashMap<>();
                        for (Location loc : chests.keySet())
                            chestscopy.put(loc, chests.get(loc));

                        for (int i = 1; i<6; i++) {
                            if (!chestscopy.containsValue(i))
                                continue;

                            Chest c = chestlevels[Math.abs(-1+i)];

                            ArrayList<ItemStack> items = utils.itemListCreator(c.getItems());
                            Location[] locs = chestscopy.keySet().toArray(new Location[chests.size()]);

                            for (Location loc : locs)
                                if (chestscopy.get(loc)!=null && chestscopy.get(loc)==i) {
                                    loc.getBlock().setType(Material.CHEST);
                                    org.bukkit.block.Chest ch = (org.bukkit.block.Chest) loc.getBlock().getState();
                                    ch.getBlockInventory().clear();
                                    int itemam = (int) (Math.random()*(c.getMaxItems()-c.getMinItems())+c.getMinItems());
                                    for (int n = 0; n<itemam; n++) {
                                        int j = (int) (Math.random()*(ch.getBlockInventory().getSize()));
                                        while (ch.getBlockInventory().getItem(j)!=null)
                                            j = (int) (Math.random()*(ch.getBlockInventory().getSize()));
                                        ch.getBlockInventory().setItem(j, utils.itemChoser(items, c.getItems()));
                                    }
                                    chestscopy.remove(loc);
                                }
                        }
                        //Chest Caricate

                        int i = 0;
                        //Teletrasporta tutti i giocatori in game
                        for (Player p : getPlayers()) {
                            p.teleport(spawnLocations[i]);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setExp(0);
                            title.send(p);
                            i++;
                        }

                        setState(State.INGAME);
                    } else {
                        title.setTitle(utils.color("&b&l"+time));
                        for (Player p : players)
                            title.send(p);
                    }

                }

                if (!getState().equals(State.WAITING))
                    time--;
            }

        }.runTaskTimer(Main.getInstance(),20L,20L);

    }

    @Override
    public void stop() {

        for (Player p : super.getPlayers()) {
            p.teleport(lobbyLocation);
        }

        for (Player p: super.spectators) {
            p.teleport(lobbyLocation);
            p.setGameMode(GameMode.SURVIVAL);
        }

        reset();
    }

    @Override
    public void reset() {
        while(super.placedBlocks.size()>0) {
            super.placedBlocks.get(0).getLocation().getBlock().setType(Material.AIR);
            super.placedBlocks.remove(0);
        }

        for (Location loc : super.brokenBlocks.keySet()) {
            Block b = loc.getBlock();
            BlockState state = super.brokenBlocks.get(loc);

            b.setType(state.getType());
            b.setData(state.getData().getData());
        }

        super.brokenBlocks.clear();

        while(super.players.size()>0)
            try {
                super.quit(super.players.get(0));
            } catch (PlayerIsNotInGameException ignored) {}

        while(super.spectators.size()>0)
            try {
                super.quit(super.players.get(0));
            } catch (PlayerIsNotInGameException ignored) {}

        for (Location loc : this.chests.keySet())
            loc.getBlock().setType(Material.CHEST);

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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public HashMap<Location, Integer> getChests() {
        return chests;
    }

    public void setChests(HashMap<Location, Integer> chests) {
        this.chests = chests;
    }
}
