package it.aendrix.skywars.arena;

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
    protected int timeMax;
    protected ArrayList<Block> placedBlocks;
    protected State state;

    public Arena() {
    }

    public Arena(String name, int maxPlayers, int minPlayers, int timeToStart, int timeMax) {
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.time = timeToStart;
        this.timeToStart = timeToStart;
        this.timeMax = timeMax;
        this.placedBlocks = new ArrayList<>();
        this.state = State.STOPPED;
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

    public int getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public int getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
    }
}
