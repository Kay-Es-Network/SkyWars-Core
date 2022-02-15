package it.aendrix.skywars.commands;

import it.aendrix.skywars.GUI.SkyWarsTypeEdit.MainMenu;
import it.aendrix.skywars.arena.Arena;
import it.aendrix.skywars.arena.Border;
import it.aendrix.skywars.events.PlayerJoinArenaEvent;
import it.aendrix.skywars.events.PlayerLeaveArenaEvent;
import it.aendrix.skywars.exception.ArenaInGameException;
import it.aendrix.skywars.exception.ArenaIsFullException;
import it.aendrix.skywars.exception.PlayerAlredyInGameException;
import it.aendrix.skywars.files.ArenaYML;
import it.aendrix.skywars.items.enums.State;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.Messages;
import it.aendrix.skywars.main.utils.utils;
import it.aendrix.skywars.skywars.ChestAdmin;
import it.aendrix.skywars.skywars.SkyWarsArena;
import it.aendrix.skywars.skywars.SkyWarsTeamArena;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SkyWarsArenaCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("skywars") || cmd.getName().equalsIgnoreCase("sw")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                Messages.sendMessage(sender, "skywars.commands.help", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (!sender.hasPermission("skywars.create") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 2) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-exists", null);
                    return true;
                }

                if (args[2].equalsIgnoreCase("Single")) {
                    SkyWarsArena arena = new SkyWarsArena(args[1], 0, 0, 0, null, null, null, null, null);
                    SkyWarsArena.getArene().put(arena.getName().toUpperCase(), arena);
                    arena.setState(State.STOPPED);
                } else if (args[2].equalsIgnoreCase("Team")) {
                    SkyWarsTeamArena arena = new SkyWarsTeamArena(args[1], 0, 0, 0, null, null, null,null, null, new ArrayList<>(), 1);
                    SkyWarsArena.getArene().put(arena.getName().toUpperCase(), arena);
                    arena.setState(State.STOPPED);
                } else {
                    Messages.sendMessage(sender, "skywars.commands.arena-creating-mode", null);
                    return true;
                }

                Messages.sendMessage(sender, "skywars.commands.arena-created", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("skywars.remove") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                Messages.sendMessage(sender, "skywars.commands.arena-removed", null);
                Main.getLoader().unload(arena.getName());
                ArenaYML.remove(arena.getName());
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("skywars.list") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                Messages.sendMessage(sender, "skywars.commands.arena-list", null);
                for (Arena arena : SkyWarsArena.arene.values())
                    utils.sendMsg(sender, "&b  " + arena.getName());
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("skywars.info") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                Messages.sendMessage(sender, "skywars.commands.arena-info", new String[] { "%NAME%;" + arena.getName().toUpperCase() });
                utils.sendMsg(sender, "&b Minimo giocatori: &f" + arena.getMinPlayers());
                utils.sendMsg(sender, "&b Massimo giocatori: &f" + arena.getMaxPlayers());
                utils.sendMsg(sender, "&b Tempo di inizio: &f" + arena.getTimeToStart());
                utils.sendMsg(sender, "&b Stato: &f" + arena.getState());
                utils.sendMsg(sender, "&b Giocatori attuali: &f" + arena.getPlayers().size());
                utils.sendMsg(sender, "&b Secondi attuali: &f" + arena.getTime());
                return true;
            }
            if (args[0].equalsIgnoreCase("setminplayers")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setMinPlayers(value);
                Messages.sendMessage(sender, "skywars.commands.arena-min-set", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("setmaxplayers")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setMaxPlayers(value);
                Messages.sendMessage(sender, "skywars.commands.arena-max-set", null);
                if (arena instanceof SkyWarsArena) {
                    Location[] spawns = new Location[value];
                    utils.mergeArray(spawns, arena.getSpawnLocations());
                    arena.setSpawnLocations(spawns);
                    ArenaYML.save((SkyWarsArena) arena);
                } else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("setteammaxplayers")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                if (!(SkyWarsArena.getArene().get(args[1].toUpperCase()) instanceof SkyWarsTeamArena)) {
                    Messages.sendMessage(sender, "general.error", null);
                    return true;
                }

                SkyWarsTeamArena arena = (SkyWarsTeamArena)SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setTeamPlayers(value);
                Messages.sendMessage(sender, "skywars.commands.team-maxplayers-set", null);
                ArenaYML.save(arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("settimestart")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setTimeToStart(value);
                Messages.sendMessage(sender, "skywars.commands.arena-time-set", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("setlobby")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 2) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setLobbyLocation(((Player)sender).getLocation());
                Messages.sendMessage(sender, "skywars.commands.arena-lobby-set", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("setspec")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 2) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                arena.setSpecLocation(((Player)sender).getLocation());
                Messages.sendMessage(sender, "skywars.commands.arena-spec-set", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("addspawn")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                if (arena.getSpawnLocations() == null || arena.getMaxPlayers() <= value) {
                    Messages.sendMessage(sender, "general.error", null);
                    return true;
                }
                if (arena.getSpawnLocations()[value] != null) {
                    Messages.sendMessage(sender, "skywars.commands.arena-spawn-exists", null);
                    return true;
                }
                arena.getSpawnLocations()[value] = ((Player)sender).getLocation();
                Messages.sendMessage(sender, "skywars.commands.arena-spawn-add", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("removespawn")) {
                int value;
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }
                if (arena.getMaxPlayers() <= value) {
                    Messages.sendMessage(sender, "general.error", null);
                    return true;
                }
                if (arena.getSpawnLocations()[value] == null) {
                    Messages.sendMessage(sender, "skywars.commands.arena-spawn-not-exists", null);
                    return true;
                }
                arena.getSpawnLocations()[value] = null;
                Messages.sendMessage(sender, "skywars.commands.arena-spawn-remove", null);
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                try {
                    arena.join((Player)sender);
                    Messages.sendMessage(sender, "skywars.game.arena-join", null);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinArenaEvent((Player)sender, arena));
                } catch (PlayerAlredyInGameException e) {
                    Messages.sendMessage(sender, "skywars.commands.arena-alredy-join", null);
                } catch (ArenaInGameException e) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                } catch (ArenaIsFullException e) {
                    Messages.sendMessage(sender, "skywars.commands.arena-full", null);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = Arena.getArenaPlayers().get(sender.getName());
                if (arena == null) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-join", null);
                    return true;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent((Player)sender, arena));
                Messages.sendMessage(sender, "skywars.game.arena-left", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("stop")) {
                if (!sender.hasPermission("skywars.stop") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                arena.setState(State.STOPPED);
                Messages.sendMessage(sender, "skywars.commands.arena-stopped", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("start")) {
                if (!sender.hasPermission("skywars.start") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                arena.setState(State.WAITING);
                Messages.sendMessage(sender, "skywars.commands.arena-started", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("restart")) {
                if (!sender.hasPermission("skywars.start") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                arena.setState(State.RESTARTING);
                Messages.sendMessage(sender, "skywars.commands.arena-restarted", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("createtype")) {
                if (!sender.hasPermission("skywars.types") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (SkyWarsType.types.containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.type-exist", null);
                    return true;
                }
                SkyWarsType type = new SkyWarsType(args[1], 600, 0, 10, 50, null);
                SkyWarsType.types.put(args[1].toUpperCase(), type);
                Messages.sendMessage(sender, "skywars.commands.type-created", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("removetype")) {
                if (!sender.hasPermission("skywars.types") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsType.types.containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.type-not-exist", null);
                    return true;
                }
                SkyWarsType.types.remove(args[1].toUpperCase());
                Messages.sendMessage(sender, "skywars.commands.type-removed", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("settype")) {
                if (!sender.hasPermission("skywars.types") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (args.length < 3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!SkyWarsType.types.containsKey(args[2].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.type-not-exists", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                SkyWarsType type = SkyWarsType.types.get(args[2].toUpperCase());

                if (arena instanceof SkyWarsArena)
                    ((SkyWarsArena)arena).setType(type);
                else if (arena instanceof SkyWarsTeamArena)
                    ((SkyWarsTeamArena)arena).setType(type);

                Messages.sendMessage(sender, "skywars.commands.arena-type-set", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("edittypes")) {
                if (!sender.hasPermission("skywars.types") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                (new MainMenu()).open((Player)sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("chestadmin")) {
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                ChestAdmin.toggleAdmin((Player)sender, arena);
                return true;
            }
            if (args[0].equalsIgnoreCase("pos1") || args[0].equalsIgnoreCase("corner1")) {
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                arena.setCorner1(((Player)sender).getLocation().getBlock().getLocation());
                if (arena.getCorner2() != null)
                    arena.setBorder(new Border(arena.getCorner1(), arena.getCorner2()));
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                Messages.sendMessage(sender, "skywars.commands.corner-set", null);
                return true;
            }
            if (args[0].equalsIgnoreCase("pos2") || args[0].equalsIgnoreCase("corner2")) {
                if (args.length == 1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }
                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Messages.sendMessage(sender, "general.player-sender", null);
                    return true;
                }
                Arena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                arena.setCorner2(((Player)sender).getLocation().getBlock().getLocation());
                if (arena.getCorner1() != null)
                    arena.setBorder(new Border(arena.getCorner1(), arena.getCorner2()));
                if (arena instanceof SkyWarsArena)
                    ArenaYML.save((SkyWarsArena) arena);
                else if (arena instanceof SkyWarsTeamArena)
                    ArenaYML.save((SkyWarsTeamArena) arena);
                Messages.sendMessage(sender, "skywars.commands.corner-set", null);
                return true;
            }
        }
        return true;
    }
}
