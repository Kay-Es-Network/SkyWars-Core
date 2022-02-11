package it.aendrix.skywars.items;

import it.aendrix.skywars.items.enums.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public final class Team {

    private ArrayList<Player> players;
    private Color color;


    public Team(Color color) {
        this.color = color;
        this.players = new ArrayList<>();
    }

    public Team(Color color, ArrayList<Player> players) {
        this.color = color;
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
