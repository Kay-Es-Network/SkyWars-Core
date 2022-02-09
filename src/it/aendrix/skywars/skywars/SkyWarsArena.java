package it.aendrix.skywars.skywars;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.BaseArena;
import it.aendrix.skywars.arena.Border;
import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.events.PlayerWinArenaEvent;
import it.aendrix.skywars.exception.PlayerIsNotInGameException;
import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.items.Title;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public final class SkyWarsArena extends Arena implements BaseArena, Serializable {
    private SkyWarsType type;

    private Title title;

    private HashMap<Location, Integer> chests;

    public SkyWarsArena(String name, int maxPlayers, int minPlayers, int timeToStart, SkyWarsType type, Location lobbyLocation, Location specLocation, Location[] spawnLocations, Border border) {
        super(name, maxPlayers, minPlayers, timeToStart, lobbyLocation, specLocation, spawnLocations, border);
        this.type = type;
        if (arene == null)
            arene = new HashMap<>();
        arene.put(name.toUpperCase(), this);
        this.title = new Title();
        this.title.setFadeInTime(0);
        this.title.setFadeOutTime(0);
        this.title.setStayTime(1);
        this.title.setTitle("");
        setState(State.WAITING);
        if (type != null)
            start();
    }

    public void start() {
        (new BukkitRunnable() {
            public void run() {
                if (SkyWarsArena.this.getState().equals(State.STOPPED))
                    return;
                if (SkyWarsArena.this.getState().equals(State.READY)) {
                    if (SkyWarsArena.this.time <= 11)
                        SkyWarsArena.this.setState(State.STARTING);
                    if (SkyWarsArena.this.getPlayers().size() >= SkyWarsArena.this.getMaxPlayers()) {
                        SkyWarsArena.this.setState(State.STARTING);
                        SkyWarsArena.this.time = 10;
                    } else if (SkyWarsArena.this.getPlayers().size() < SkyWarsArena.this.getMinPlayers()) {
                        SkyWarsArena.this.setState(State.WAITING);
                        SkyWarsArena.this.time = SkyWarsArena.this.timeToStart;
                    }
                } else if (SkyWarsArena.this.getState().equals(State.WAITING)) {
                    if (SkyWarsArena.this.getPlayers().size() >= SkyWarsArena.this.getMinPlayers())
                        SkyWarsArena.this.setState(State.READY);
                } else if (SkyWarsArena.this.getState().equals(State.INGAME)) {
                    if (SkyWarsArena.this.time <= 0) {
                        SkyWarsArena.this.setState(State.RESTARTING);
                        return;
                    }
                    if (SkyWarsArena.this.players.size() < 2) {
                        if (SkyWarsArena.this.players.size() != 0) {
                            Player winner = SkyWarsArena.this.players.get(0);
                            Bukkit.getServer().getPluginManager().callEvent(new PlayerWinArenaEvent(winner, Arena.getArenaPlayers().get(winner.getName())));
                        } else {
                            SkyWarsArena.this.setState(State.RESTARTING);
                        }
                        return;
                    }
                } else if (SkyWarsArena.this.getState().equals(State.RESTARTING)) {
                    SkyWarsArena.this.stop();
                    SkyWarsArena.this.setState(State.WAITING);
                } else if (SkyWarsArena.this.getState().equals(State.STARTING)) {
                    if (SkyWarsArena.this.getPlayers().size() < SkyWarsArena.this.getMinPlayers()) {
                        SkyWarsArena.this.setState(State.WAITING);
                        SkyWarsArena.this.time = SkyWarsArena.this.timeToStart;
                    }
                    if (SkyWarsArena.this.time == 0) {
                        SkyWarsArena.this.time = SkyWarsArena.this.type.getTimeMax();
                        SkyWarsArena.this.title.setTitle(utils.color("&6&lINIZIO"));
                        Chest[] chestlevels = SkyWarsArena.this.type.getChestsType();
                        HashMap<Location, Integer> chestscopy = new HashMap<>();
                        for (Location loc : SkyWarsArena.this.chests.keySet())
                            chestscopy.put(loc, SkyWarsArena.this.chests.get(loc));
                        int i;
                        for (i = 1; i < 6; i++) {
                            if (chestscopy.containsValue(i)) {
                                Chest c = chestlevels[Math.abs(-1 + i)];
                                ArrayList<ItemStack> items = utils.itemListCreator(c.getItems());
                                Location[] locs = (Location[])chestscopy.keySet().toArray((Object[])new Location[SkyWarsArena.this.chests.size()]);
                                for (Location loc : locs) {
                                    if (chestscopy.get(loc) != null && chestscopy.get(loc) == i) {
                                        loc.getBlock().setType(Material.CHEST);
                                        org.bukkit.block.Chest ch = (org.bukkit.block.Chest) loc.getBlock().getState();
                                        ch.getBlockInventory().clear();
                                        int itemam = (int)(Math.random() * (c.getMaxItems() - c.getMinItems()) + c.getMinItems());
                                        for (int n = 0; n < itemam; n++) {
                                            int j = (int)(Math.random() * ch.getBlockInventory().getSize());
                                            while (ch.getBlockInventory().getItem(j) != null)
                                                j = (int)(Math.random() * ch.getBlockInventory().getSize());
                                            ch.getBlockInventory().setItem(j, utils.itemChoser(items, c.getItems()));
                                        }
                                        chestscopy.remove(loc);
                                    }
                                }
                            }
                        }
                        i = 0;
                        for (Player p : SkyWarsArena.this.getPlayers()) {
                            p.teleport(SkyWarsArena.this.spawnLocations[i]);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setExp(0.0F);
                            SkyWarsArena.this.title.send(p);
                            i++;
                        }
                        SkyWarsArena.this.setState(State.INGAME);
                    } else {
                        SkyWarsArena.this.title.setTitle(utils.color("&b&l" + SkyWarsArena.this.time));
                        for (Player p : SkyWarsArena.this.players)
                            SkyWarsArena.this.title.send(p);
                    }
                }
                if (!SkyWarsArena.this.getState().equals(State.WAITING))
                    SkyWarsArena.this.time--;
            }
        }).runTaskTimer(Main.getInstance(), 20L, 20L);
    }

    public void stop() {
        for (Player p : getPlayers())
            p.teleport(this.lobbyLocation);
        for (Player p : this.spectators) {
            p.teleport(this.lobbyLocation);
            p.setGameMode(GameMode.SURVIVAL);
        }
        reset();
    }

    public void reset() {
        while (this.placedBlocks.size() > 0) {
            this.placedBlocks.get(0).getLocation().getBlock().setType(Material.AIR);
            this.placedBlocks.remove(0);
        }
        for (Location loc : this.brokenBlocks.keySet()) {
            Block b = loc.getBlock();
            BlockState state = this.brokenBlocks.get(loc);
            b.setType(state.getType());
            b.setData(state.getData().getData());
        }
        this.brokenBlocks.clear();
        while (this.players.size() > 0) {
            try {
                quit(this.players.get(0));
            } catch (PlayerIsNotInGameException ignored) {}
        }
        while (this.spectators.size() > 0) {
            try {
                quit(this.spectators.get(0));
            } catch (PlayerIsNotInGameException ignored) {}
        }
        for (Location loc : this.chests.keySet())
            loc.getBlock().setType(Material.CHEST);
        this.time = this.timeToStart;
        this.state = State.STARTING;
    }

    public static HashMap<String, Arena> getArene() {
        return arene;
    }

    public static void setArene(HashMap<String, Arena> arene) {
        SkyWarsArena.arene = arene;
    }

    public SkyWarsType getType() {
        return this.type;
    }

    public void setType(SkyWarsType type) {
        this.type = type;
    }

    public Title getTitle() {
        return this.title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public HashMap<Location, Integer> getChests() {
        return this.chests;
    }

    public void setChests(HashMap<Location, Integer> chests) {
        this.chests = chests;
    }
}
