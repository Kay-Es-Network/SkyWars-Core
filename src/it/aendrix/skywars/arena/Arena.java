package it.aendrix.skywars.arena;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Arena{

    protected String name;
    protected ArrayList<Player> players;
    protected int maxPlayers;
    protected int minPlayers;
    protected int time;
    protected int timeToStart;
    protected ArrayList<Block> placedBlocks;
    protected ArrayList<Block> brokenBlocks;
    protected State state;

    protected Location lobbyLocation, specLocation;
    protected Location[] spawnLocations;

    public Arena() {
    }

    public Arena(String name, int maxPlayers, int minPlayers, int timeToStart, Location lobbyLocation, Location specLocation, Location[] spawnLocations) {
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.time = timeToStart;
        this.timeToStart = timeToStart;
        this.placedBlocks = new ArrayList<>();
        this.brokenBlocks = new ArrayList<>();
        this.state = State.STOPPED;
        this.lobbyLocation = lobbyLocation;
        this.specLocation = specLocation;
        this.spawnLocations = spawnLocations;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public ArrayList<Block> getPlacedBlocks() {
        return placedBlocks;
    }

    public void setPlacedBlocks(ArrayList<Block> placedBlocks) {
        this.placedBlocks = placedBlocks;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public Location getSpecLocation() {
        return specLocation;
    }

    public void setSpecLocation(Location specLocation) {
        this.specLocation = specLocation;
    }

    public Location[] getSpawnLocations() {
        return spawnLocations;
    }

    public void setSpawnLocations(Location[] spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    public ArrayList<Block> getBrokenBlocks() {
        return brokenBlocks;
    }

    public void setBrokenBlocks(ArrayList<Block> brokenBlocks) {
        this.brokenBlocks = brokenBlocks;
    }

    public boolean containsPlayer(String name) {
        for (Player p : players)
            if (p!=null && p.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }

}
