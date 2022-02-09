package it.aendrix.skywars.arena;

import it.aendrix.skywars.events.PlayerArenaKillEvent;
import it.aendrix.skywars.events.PlayerChatArenaEvent;
import it.aendrix.skywars.events.PlayerLeaveArenaEvent;
import it.aendrix.skywars.events.PlayerOutArenaBordersEvent;
import it.aendrix.skywars.items.KillType;
import it.aendrix.skywars.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ArenaEvents implements Listener {

    public static class DamagerTimer {
        protected Player damager;

        protected int seconds;

        public DamagerTimer(Player damager, int seconds) {
            this.damager = damager;
            this.seconds = seconds;
        }

        public Player getDamager() {
            return this.damager;
        }

        public void setDamager(Player damager) {
            this.damager = damager;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }
    }

    private static final HashMap<String, DamagerTimer> lastDamageTimePlayer = new HashMap<>();
    private static final HashMap<String, Arena> arenaPlayers = Arena.getArenaPlayers();

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player) ||
                !arenaPlayers.containsKey(e.getDamager().getName()) ||
                !arenaPlayers.containsKey(e.getEntity().getName()))
            return;
        final String name = e.getEntity().getName();
        if (lastDamageTimePlayer.containsKey(name)) {
            lastDamageTimePlayer.get(name).seconds = 10;
        } else {
            lastDamageTimePlayer.put(name, new DamagerTimer((Player)e.getDamager(), 10));
            (new BukkitRunnable() {

                public void run() {
                    ArenaEvents.lastDamageTimePlayer.get(name).seconds--;
                    if (ArenaEvents.lastDamageTimePlayer.get(name).seconds <= 0) {
                        cancel();
                        ArenaEvents.lastDamageTimePlayer.remove(name);
                    }
                }
            }).runTaskTimerAsynchronously(Main.getInstance(), 20L, 20L);
        }
    }

    @EventHandler
    public void onDamaging(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p) || !arenaPlayers.containsKey(e.getEntity().getName()))
            return;
        Arena arena = arenaPlayers.get(p.getName());
        if (p.getHealth() - e.getDamage() <= 0.0D) {
            if (arena.containsPlayer(p.getName()) && arena.getState().equals(State.INGAME))
                if (lastDamageTimePlayer.containsKey(p.getName()) && arena.containsPlayer(p.getLastDamageCause().getEntity().getName())) {
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerArenaKillEvent(p, lastDamageTimePlayer.get(p.getName()).getDamager(), p.getLocation(), arena, KillType.KILLED));
                } else {
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerArenaKillEvent(p, p.getLocation(), arena, KillType.SELF));
                }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!arenaPlayers.containsKey(p.getName()))
            return;
        Arena arena = arenaPlayers.get(p.getName());
        Border border = arena.getBorder();
        if (border != null && !border.isInRegion(p.getLocation()) && arena
                .getState().equals(State.INGAME) && arena.getPlayers().contains(p)) {
            if (p.getLocation().getY() < border.getLesserCorner().getY()) {
                if (lastDamageTimePlayer.containsKey(p.getName())) {
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerArenaKillEvent(p, lastDamageTimePlayer.get(p.getName()).getDamager(), p.getLocation(), arena, KillType.VOID_KILLED));
                } else {
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerArenaKillEvent(p, p.getLocation(), arena, KillType.VOID));
                }
                return;
            }
            Bukkit.getServer().getPluginManager().callEvent(new PlayerOutArenaBordersEvent(p, arena, e.getFrom(), e.getTo()));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player x : Bukkit.getOnlinePlayers()) {
            if (Arena.getArenaPlayers().containsKey(x.getName())) {
                x.hidePlayer(Main.getInstance(), p);
                p.hidePlayer(Main.getInstance(), x);
                continue;
            }
            if (!x.equals(p))
                p.showPlayer(Main.getInstance(), x);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!arenaPlayers.containsKey(p.getName()))
            return;
        Arena arena = arenaPlayers.get(p.getName());
        if (arena.containsPlayer(p.getName()))
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(p, arena));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!arenaPlayers.containsKey(e.getPlayer().getName()))
            return;
        Player p = e.getPlayer();
        Arena arena = arenaPlayers.get(p.getName());
        if (arena.containsPlayer(p.getName())) {
            e.setCancelled(true);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerChatArenaEvent(p, arena, e.getMessage()));
        }
    }

    public static HashMap<String, DamagerTimer> getLastDamageTimePlayer() {
        return lastDamageTimePlayer;
    }

    public static HashMap<String, Arena> getArenaPlayers() {
        return arenaPlayers;
    }
}
