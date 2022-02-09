package it.aendrix.skywars.events;

import it.aendrix.skywars.arena.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOutArenaBordersEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private Arena arena;

    private Location from;

    private Location to;

    public PlayerOutArenaBordersEvent(Player player, Arena arena, Location from, Location to) {
        this.player = player;
        this.arena = arena;
        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return this.from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return this.to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
