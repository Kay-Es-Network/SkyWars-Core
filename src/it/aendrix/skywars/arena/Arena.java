package it.aendrix.skywars.arena;

import it.aendrix.skywars.events.*;
import it.aendrix.skywars.exception.ArenaInGameException;
import it.aendrix.skywars.exception.ArenaIsFullException;
import it.aendrix.skywars.exception.PlayerAlredyInGameException;
import it.aendrix.skywars.exception.PlayerIsNotInGameException;
import it.aendrix.skywars.items.Team;
import it.aendrix.skywars.items.enums.KillType;
import it.aendrix.skywars.items.enums.State;
import it.aendrix.skywars.main.utils.SortedList;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.Messages;
import it.aendrix.skywars.main.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Arena implements Listener, Serializable {
    private static HashMap<String, Arena> arenaPlayers;
    public static HashMap<String, Arena> arene;
    protected String name;
    protected ArrayList<Player> players;
    protected ArrayList<Player> spectators;
    protected int maxPlayers;
    protected int minPlayers;
    protected int time;
    protected int timeToStart;
    protected ArrayList<Block> placedBlocks;
    protected HashMap<Location, BlockState> brokenBlocks;
    protected State state;
    protected Location corner1;
    protected Location corner2;
    protected Border border;
    protected SortedList playerKills;
    protected Location lobbyLocation;
    protected Location specLocation;
    protected Location[] spawnLocations;

    public Arena() {}

    public Arena(String name, int maxPlayers, int minPlayers, int timeToStart, Location lobbyLocation, Location specLocation, Location[] spawnLocations, Border border) {
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.time = timeToStart;
        this.timeToStart = timeToStart;
        this.placedBlocks = new ArrayList<>();
        this.brokenBlocks = new HashMap<>();
        this.state = State.STOPPED;
        this.lobbyLocation = lobbyLocation;
        this.specLocation = specLocation;
        this.spawnLocations = spawnLocations;
        this.border = border;
        this.spectators = new ArrayList<>();
        this.playerKills = new SortedList();
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    public static HashMap<String, Arena> getArenaPlayers() {
        return arenaPlayers;
    }

    public static void setArenaPlayers(HashMap<String, Arena> arenaPlayers) {
        Arena.arenaPlayers = arenaPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public ArrayList<Block> getPlacedBlocks() {
        return this.placedBlocks;
    }

    public void setPlacedBlocks(ArrayList<Block> placedBlocks) {
        this.placedBlocks = placedBlocks;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTimeToStart() {
        return this.timeToStart;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
    }

    public Location getLobbyLocation() {
        return this.lobbyLocation;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public Location getSpecLocation() {
        return this.specLocation;
    }

    public void setSpecLocation(Location specLocation) {
        this.specLocation = specLocation;
    }

    public Location[] getSpawnLocations() {
        return this.spawnLocations;
    }

    public void setSpawnLocations(Location[] spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    public HashMap<Location, BlockState> getBrokenBlocks() {
        return this.brokenBlocks;
    }

    public void setBrokenBlocks(HashMap<Location, BlockState> brokenBlocks) {
        this.brokenBlocks = brokenBlocks;
    }

    public Location getCorner1() {
        return this.corner1;
    }

    public void setCorner1(Location corner1) {
        this.corner1 = corner1;
    }

    public Location getCorner2() {
        return this.corner2;
    }

    public void setCorner2(Location corner2) {
        this.corner2 = corner2;
    }

    public Border getBorder() {
        return this.border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public ArrayList<Player> getSpectators() {
        return this.spectators;
    }

    public void setSpectators(ArrayList<Player> spectators) {
        this.spectators = spectators;
    }

    public SortedList getPlayerKills() {
        return this.playerKills;
    }

    public void setPlayerKills(SortedList playerKills) {
        this.playerKills = playerKills;
    }

    public boolean containsPlayer(String name) {
        for (Player p : this.players) {
            if (p != null && p.getName().equalsIgnoreCase(name))
                return true;
        }
        for (Player p : this.spectators) {
            if (p != null && p.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public void toSpectator(Player player) {
        this.spectators.add(player);
        player.teleport(this.specLocation);
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void join(Player player) throws PlayerAlredyInGameException, ArenaInGameException, ArenaIsFullException {
        if (getArenaPlayers().containsKey(player.getName()))
            throw new PlayerAlredyInGameException();
        if (!getState().equals(State.WAITING) && !getState().equals(State.READY))
            throw new ArenaInGameException();
        if (getPlayers().size() >= getMaxPlayers())
            throw new ArenaIsFullException();
        this.players.add(player);
        getArenaPlayers().put(player.getName(), this);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setExp(0.0F);
        player.teleport(this.lobbyLocation);
    }

    public void quit(Player player) throws PlayerIsNotInGameException {
        if (!getArenaPlayers().containsKey(player.getName()))
            throw new PlayerIsNotInGameException();
        player.teleport(this.lobbyLocation);
        this.players.remove(player);
        this.spectators.remove(player);
        player.getInventory().clear();
        player.setExp(0.0F);
        getArenaPlayers().remove(player.getName());
    }

    public void kill(Player player, Player killer, KillType type) {
        this.players.remove(player);
        if (type.equals(KillType.KILLED)) {
            Messages.sendMessage(player, "skywars.game.player-kill", new String[] { "%KILLER%;" + killer.getName() });
            Messages.broadcastMessage("skywars.game.player-kill-all", new String[] { "%VICTIM%;" + player.getName(), "%KILLER%;" + killer.getName() }, this.players);
            Messages.broadcastMessage("skywars.game.player-kill-all", new String[] { "%VICTIM%;" + player.getName(), "%KILLER%;" + killer.getName() }, this.spectators);
        }
        if (type.equals(KillType.VOID_KILLED)) {
            Messages.sendMessage(player, "skywars.game.player-void-kill", new String[] { "%KILLER%;" + killer.getName() });
            Messages.broadcastMessage("skywars.game.player-void-kill-all", new String[] { "%VICTIM%;" + player.getName(), "%KILLER%;" + killer.getName() }, this.players);
            Messages.broadcastMessage("skywars.game.player-void-kill-all", new String[] { "%VICTIM%;" + player.getName(), "%KILLER%;" + killer.getName() }, this.spectators);
        } else if (type.equals(KillType.VOID)) {
            Messages.sendMessage(player, "skywars.game.player-self-void-kill", null);
            Messages.broadcastMessage("skywars.game.player-self-void-kill-all", new String[] { "%VICTIM%;" + player.getName() }, this.players);
            Messages.broadcastMessage("skywars.game.player-self-void-kill-all", new String[] { "%VICTIM%;" + player.getName() }, this.spectators);
        } else if (type.equals(KillType.SELF)) {
            Messages.sendMessage(player, "skywars.game.player-self-kill", null);
            Messages.broadcastMessage("skywars.game.player-self-kill-all", new String[] { "%VICTIM%;" + player.getName() }, this.players);
            Messages.broadcastMessage("skywars.game.player-self-kill-all", new String[] { "%VICTIM%;" + player.getName() }, this.spectators);
        }
        toSpectator(player);
        if (killer != null)
            this.playerKills.insert(killer, this.playerKills.getValue(killer) + 1);
    }

    public boolean equals(Arena arena) {
        return arena.getName().equalsIgnoreCase(getName());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!this.state.equals(State.INGAME))
            return;
        Player p = e.getPlayer();
        if (containsPlayer(p.getName())) {
            Block block = e.getBlock();
            if (!utils.blockRemoveList(block, getPlacedBlocks()))
                getBrokenBlocks().put(block.getLocation(), block.getState());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!this.state.equals(State.INGAME))
            return;
        Player p = e.getPlayer();
        if (containsPlayer(p.getName()))
            getPlacedBlocks().add(e.getBlock());
    }

    @EventHandler
    public void onJoin(PlayerJoinArenaEvent e) {
        Player p = e.getPlayer();
        if (equals(e.getArena())) {
            for (Player x : Bukkit.getOnlinePlayers()) {
                if (!this.players.contains(x) && !x.equals(p)) {
                    x.hidePlayer(Main.getInstance(), p);
                    p.hidePlayer(Main.getInstance(), x);
                    continue;
                }
                if (!x.equals(p)) {
                    p.showPlayer(Main.getInstance(), x);
                    x.showPlayer(Main.getInstance(), p);
                }
            }
            Messages.broadcastMessage("skywars.game.arena-join-all", new String[] { "%PLAYER%;" + p.getName() }, getPlayers());
        }
    }

    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent e) {
        Player p = e.getPlayer();
        if (equals(e.getArena()))
            try {
                quit(p);
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                p.setExp(0.0F);
                Messages.broadcastMessage("skywars.game.arena-left-all", new String[] { "%PLAYER%;" + p.getName() }, this.spectators);
                Messages.broadcastMessage("skywars.game.arena-left-all", new String[] { "%PLAYER%;" + p.getName() }, this.players);
                for (Player x : Bukkit.getOnlinePlayers()) {
                    if (arenaPlayers.containsKey(x.getName()) && !x.equals(p)) {
                        p.hidePlayer(Main.getInstance(), x);
                        x.hidePlayer(Main.getInstance(), p);
                        continue;
                    }
                    if (!x.equals(p)) {
                        p.showPlayer(Main.getInstance(), x);
                        x.showPlayer(Main.getInstance(), p);
                    }
                }
            } catch (PlayerIsNotInGameException playerIsNotInGameException) {}
    }

    @EventHandler
    public void onGoOutBorders(PlayerOutArenaBordersEvent e) {
        Player p = e.getPlayer();
        if (equals(e.getArena())) {
            Messages.sendMessage(p, "skywars.game.arena-border", null);
            e.getTo().setX(e.getFrom().getX());
            e.getTo().setY(e.getFrom().getY());
            e.getTo().setZ(e.getFrom().getZ());
        }
    }

    @EventHandler
    public void onChat(PlayerChatArenaEvent e) {
        Player p = e.getPlayer();
        if (equals(e.getArena()))
            if (this instanceof TeamArena && e.getMessage().charAt(0) != '!' && this.state.equals(State.INGAME)) {
                for (Team t : ((TeamArena) this).teams)
                    if (t.getPlayers().contains(p)) {
                        Messages.broadcastMessage("skywars.chat.ingame", new String[]{"%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage()}, t.getPlayers());
                        break;
                    }
            } else if (this.players.contains(p) && this.state.equals(State.INGAME)) {
                Messages.broadcastMessage("skywars.chat.ingame", new String[] { "%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage() }, this.players);
                Messages.broadcastMessage("skywars.chat.ingame", new String[] { "%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage() }, this.spectators);
            } else if (this.spectators.contains(p) && this.state.equals(State.INGAME)) {
                Messages.broadcastMessage("skywars.chat.spector", new String[] { "%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage() }, this.spectators);
            } else if (containsPlayer(p.getName())) {
                Messages.broadcastMessage("skywars.chat.lobby", new String[] { "%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage() }, this.players);
                Messages.broadcastMessage("skywars.chat.lobby", new String[] { "%PLAYER%;" + p.getName(), "%MESSAGE%;" + e.getMessage() }, this.spectators);
            }
    }

    @EventHandler
    public void onKill(PlayerArenaKillEvent e) {
        Player p = e.getVictim();
        if (equals(e.getArena()) && this.players.contains(e.getVictim())) {
            kill(p, e.getKiller(), e.getKillType());
            for (Player x : Bukkit.getOnlinePlayers()) {
                if (!this.players.contains(x) && !this.spectators.contains(x) && !x.equals(p)) {
                    x.hidePlayer(Main.getInstance(), p);
                    p.hidePlayer(Main.getInstance(), x);
                    continue;
                }
                if (this.players.contains(x)) {
                    x.hidePlayer(Main.getInstance(), p);
                    continue;
                }
                if (!x.equals(p))
                    p.showPlayer(Main.getInstance(), x);
            }
        }
    }

    @EventHandler
    public void onWin(PlayerWinArenaEvent e) {
        Player p = e.getPlayer();
        if (equals(e.getArena())) {
            if (this.playerKills.size() == 0) {
                Messages.broadcastMessage("skywars.game.arena-no-winner", null, this.players);
                Messages.broadcastMessage("skywars.game.arena-no-winner", null, this.spectators);
                setState(State.RESTARTING);
                return;
            }
            Messages.broadcastMessage("skywars.game.player-win-all", new String[] { "%PLAYER%;" + p.getName() }, this.spectators);
            Messages.broadcastMessage("skywars.game.player-win-all", new String[] { "%PLAYER%;" + p.getName() }, this.players);
            setState(State.RESTARTING);
        }
    }
}
