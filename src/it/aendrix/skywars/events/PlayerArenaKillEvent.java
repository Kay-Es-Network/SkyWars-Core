package it.aendrix.skywars.events;

import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.items.enums.KillType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArenaKillEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player victim;

    private Player killer;

    private Location location;

    private Arena arena;

    private KillType killType;

    public PlayerArenaKillEvent(Player victim, Player killer, Location location, Arena arena, KillType killType) {
        this.victim = victim;
        this.killer = killer;
        this.location = location;
        this.arena = arena;
        this.killType = killType;
    }

    public PlayerArenaKillEvent(Player victim, Location location, Arena arena, KillType killType) {
        this.victim = victim;
        this.location = location;
        this.arena = arena;
        this.killType = killType;
    }

    public Player getVictim() {
        return this.victim;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }

    public Player getKiller() {
        return this.killer;
    }

    public void setKiller(Player killer) {
        this.killer = killer;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public KillType getKillType() {
        return this.killType;
    }

    public void setKillType(KillType killType) {
        this.killType = killType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
