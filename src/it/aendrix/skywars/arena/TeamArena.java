package it.aendrix.skywars.arena;

import it.aendrix.skywars.exception.PlayerAlredyInTeamException;
import it.aendrix.skywars.exception.TeamFullException;
import it.aendrix.skywars.items.Team;
import it.aendrix.skywars.items.enums.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class TeamArena extends Arena implements Serializable {

    protected ArrayList<Team> teams;
    protected int teamPlayers;

    public TeamArena(String name, int maxPlayers, int minPlayers, int timeToStart, Location lobbyLocation, Location specLocation,
                     Location[] spawnLocations, Border border, ArrayList<Team> teams, int teamPlayers) {
        super(name, maxPlayers, minPlayers, timeToStart, lobbyLocation, specLocation, spawnLocations, border);
        this.teams = teams;
        this.teamPlayers = teamPlayers;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public int getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(int teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public boolean inTeam(Player victim, Player killer) {
        for (Team t : teams)
            if (t.getPlayers().contains(victim)&&t.getPlayers().contains(killer))
                return true;
        return false;
    }

    public void changeTeam(Player player, Color color) throws PlayerAlredyInTeamException, TeamFullException {
        for (Team t : teams)
            if (t.getColor().equals(color)) {
                if (t.getPlayers().contains(player))
                    throw new PlayerAlredyInTeamException();
                if (t.getPlayers().size()>this.teamPlayers)
                    throw new TeamFullException();
                for (Team tx : teams)
                    tx.getPlayers().remove(player);
                t.getPlayers().add(player);
            }
    }
}
