package it.aendrix.skywars.events;

import it.aendrix.skywars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChatArenaEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private Arena arena;

    private String message;

    public PlayerChatArenaEvent(Player player, Arena arena, String message) {
        super(true);
        this.player = player;
        this.arena = arena;
        this.message = message;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
