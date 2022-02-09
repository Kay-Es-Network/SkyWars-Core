package it.aendrix.skywars.items;

import it.aendrix.skywars.GUI.SkyWarsTypeEdit.ChestMenu;
import it.aendrix.skywars.main.Messages;
import it.aendrix.skywars.main.utils;
import it.aendrix.skywars.skywars.SkyWarsType;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageRequire implements Listener {
    private static HashMap<String, String> mess = new HashMap<>();

    public static HashMap<String, String> getMess() {
        return mess;
    }

    public static void setMess(HashMap<String, String> mess) {
        MessageRequire.mess = mess;
    }

    public static void addMess(Player p, String m) {
        mess.put(p.getName(), m);
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {
        if (!mess.containsKey(e.getPlayer().getName()))
            return;
        Player p = e.getPlayer();
        String type = mess.get(p.getName());
        if (type == null) {
            mess.remove(p.getName());
            return;
        }
        String msg = e.getMessage();
        e.setCancelled(true);
        if (type.equalsIgnoreCase("SkyWarsTypeEdit.MainMenu;0")) {
            if (msg.equalsIgnoreCase("exit")) {
                utils.sendMsg(p, "&fUscito");
            } else if (SkyWarsType.getTypes().containsKey(msg.toUpperCase())) {
                (new ChestMenu(SkyWarsType.getTypes().get(msg.toUpperCase()))).open(p);
            } else {
                Messages.sendMessage(p, "skywars.commands.type-not-exist", null);
                return;
            }
        } else if (type.equalsIgnoreCase("SkyWarsTypeEdit.MainMenu;1")) {
            if (SkyWarsType.types.containsKey(msg.toUpperCase())) {
                Messages.sendMessage(p, "skywars.commands.type-exist", null);
                return;
            }
            SkyWarsType x = new SkyWarsType(msg, 600, 0, 10, 50, null);
            SkyWarsType.types.put(msg.toUpperCase(), x);
            Messages.sendMessage(p, "skywars.commands.type-created", null);
        }
        mess.remove(p.getName());
    }
}
