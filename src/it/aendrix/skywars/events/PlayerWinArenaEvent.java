package it.aendrix.skywars.events;

import it.aendrix.skywars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinArenaEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private Arena arena;

    public PlayerWinArenaEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
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
