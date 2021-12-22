package it.aendrix.skywars.commands;

import it.aendrix.skywars.arena.State;
import it.aendrix.skywars.files.ArenaYML;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.Messages;
import it.aendrix.skywars.main.utils;
import it.aendrix.skywars.skywars.SkyWarsArena;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyWarsArenaCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("skywars") || cmd.getName().equalsIgnoreCase("sw")) {

            if (args.length==0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                Messages.sendMessage(sender, "skywars.commands.help", null);
                return true;
            }

            if (args[0].equalsIgnoreCase("create")) {
                if (!sender.hasPermission("skywars.create") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length==1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-exists", null);
                    return true;
                }

                SkyWarsArena arena = new SkyWarsArena(args[1],0,0,0,null,null,null,null);
                SkyWarsArena.getArene().put(arena.getName().toUpperCase(), arena);

                Messages.sendMessage(sender, "skywars.commands.arena-created", null);
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("skywars.remove") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length==1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

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

                for (SkyWarsArena arena : SkyWarsArena.arene.values())
                    utils.sendMsg(sender,"&b  "+arena.getName());
                return true;
            }

            if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("skywars.info") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length==1) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());
                Messages.sendMessage(sender, "skywars.commands.arena-info", new String[]{"%NAME%;"+arena.getName().toUpperCase()});

                utils.sendMsg(sender, "&b Minimo giocatori: &f"+arena.getMinPlayers());
                utils.sendMsg(sender, "&b Massimo giocatori: &f"+arena.getMaxPlayers());
                if (arena.getType()!=null)
                    utils.sendMsg(sender, "&b Tipo: &f"+arena.getType().getName());
                utils.sendMsg(sender, "&b Tempo di inizio: &f"+arena.getTimeToStart());
                utils.sendMsg(sender, "&b Stato: &f"+arena.getState());
                utils.sendMsg(sender, "&b Giocatori attuali: &f"+arena.getPlayers().size());

                return true;
            }

            if (args[0].equalsIgnoreCase("setminplayers")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                int value;
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                arena.setMinPlayers(value);

                Messages.sendMessage(sender, "skywars.commands.arena-min-set", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("setmaxplayers")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                int value;
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                arena.setMaxPlayers(value);
                Location[] spawns = new Location[value];
                utils.mergeArray(spawns,arena.getSpawnLocations());
                arena.setSpawnLocations(spawns);

                Messages.sendMessage(sender, "skywars.commands.arena-max-set", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("settimestart")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                int value;
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                arena.setTimeToStart(value);

                Messages.sendMessage(sender, "skywars.commands.arena-time-set", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("setlobby")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<2) {
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

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                arena.setLobbyLocation(((Player)sender).getLocation());

                Messages.sendMessage(sender, "skywars.commands.arena-lobby-set", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("setspec")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<2) {
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

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                arena.setSpecLocation(((Player)sender).getLocation());

                Messages.sendMessage(sender, "skywars.commands.arena-spec-set", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("addspawn")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<3) {
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

                int value;
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                if (arena.getSpawnLocations()==null || arena.getMaxPlayers()<=value) {
                    Messages.sendMessage(sender, "general.error", null);
                    return true;
                }

                if (arena.getSpawnLocations()[value]!=null) {
                    Messages.sendMessage(sender, "skywars.commands.arena-spawn-exists", null);
                    return true;
                }

                arena.getSpawnLocations()[value] = ((Player)sender).getLocation();

                Messages.sendMessage(sender, "skywars.commands.arena-spawn-add", null);

                ArenaYML.save(arena);
                return true;
            }

            if (args[0].equalsIgnoreCase("removespawn")) {
                if (!sender.hasPermission("skywars.edit") || !sender.hasPermission("skywars.*")) {
                    Messages.sendMessage(sender, "general.no-permission", null);
                    return true;
                }

                if (args.length<3) {
                    Messages.sendMessage(sender, "general.no-arguments", null);
                    return true;
                }

                if (!SkyWarsArena.getArene().containsKey(args[1].toUpperCase())) {
                    Messages.sendMessage(sender, "skywars.commands.arena-not-exists", null);
                    return true;
                }

                int value;
                try {
                    value = Math.abs(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    Messages.sendMessage(sender, "general.error-arguments", null);
                    return true;
                }

                SkyWarsArena arena = SkyWarsArena.getArene().get(args[1].toUpperCase());

                if (!arena.getState().equals(State.STOPPED)) {
                    Messages.sendMessage(sender, "skywars.commands.arena-ingame", null);
                    return true;
                }

                if (arena.getMaxPlayers()<=value) {
                    Messages.sendMessage(sender, "general.error", null);
                    return true;
                }

                if (arena.getSpawnLocations()[value]==null) {
                    Messages.sendMessage(sender, "skywars.commands.arena-spawn-not-exists", null);
                    return true;
                }

                arena.getSpawnLocations()[value] = null;

                Messages.sendMessage(sender, "skywars.commands.arena-spawn-remove", null);

                ArenaYML.save(arena);
                return true;
            }

        }

        return true;
    }


}
