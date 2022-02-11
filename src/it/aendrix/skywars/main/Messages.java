package it.aendrix.skywars.main;

import it.aendrix.skywars.main.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Messages {
    private static final File file = new File("plugins/SkyWars-Core" + File.separator + "messages.yml");
    private static final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private static HashMap<String, String> messages = new HashMap<>();
    private static final String N = "/!newline";

    public Messages() {
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getMessage(String msg) {
        return msg.split("/!newline");
    }

    public static void sendMessage(Player p, String msg, String[] ph) {
        msg = messages.get(msg);
        if (ph != null)
            for (String x : ph) {
                String[] n = x.split(";");
                msg = msg.replaceAll(n[0].toUpperCase(), n[1]);
            }
        String[] s = msg.split("/!newline");
        for (String x : s)
            utils.sendMsg(p, x);
    }

    public static void sendMessage(CommandSender p, String msg, String[] ph) {
        msg = messages.get(msg);
        if (ph != null)
            for (String x : ph) {
                String[] n = x.split(";");
                msg = msg.replaceAll(n[0].toUpperCase(), n[1]);
            }
        String[] s = msg.split("/!newline");
        for (String x : s)
            utils.sendMsg(p, x);
    }

    public static void broadcastMessage(String msg, String[] ph) {
        for (Player x : Bukkit.getOnlinePlayers())
            sendMessage(x, msg, ph);
    }

    public static void broadcastMessage(String msg, String[] ph, ArrayList<Player> players) {
        for (Player x : players)
            sendMessage(x, msg, ph);
    }

    public static void broadcastMessage(String msg, String[] ph, Player[] players) {
        for (Player x : players)
            sendMessage(x, msg, ph);
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, String> messages) {
        Messages.messages = messages;
    }

    private void load() throws IOException {
        loadString("general.no-permission", "&f&lSKYWARS &b&fNon possiedi abbastanza permessi");
        loadString("general.no-arguments", "&f&lSKYWARS &b&fNon hai inserito tutti i dati richiesti");
        loadString("general.error-arguments", "&f&lSKYWARS &b&fI dati inseriti non corrispondono");
        loadString("general.player-sender", "&f&lSKYWARS &b&fSolo un giocatore pueseguire questa azione");
        loadString("general.error", "&f&lSKYWARS &b&fErrore");
        loadString("skywars.commands.help", " /!newline&f/&bsw create <&ename&b> &7&fCrea un Arena/!newline&f/&bsw remove <&ename&b> &7&fRimuovi un Arena/!newline&f/&bsw setminplayers <&ename&b> <&eamount&b> &7&fImposta il minimo di giocatori di un Arena/!newline&f/&bsw setmaxplayers <&ename&b> <&eamount&b> &7&fImposta il massimo di giocatori di un Arena/!newline&f/&bsw settimestart <&ename&b> <&eamount&b> &7&fImposta il tempo di un Arena/!newline&f/&bsw setlobby <&ename&b> &7&fImposta la lobby di un Arena/!newline&f/&bsw setspec <&ename&b> &7&fImposta il punto spettatori di un Arena/!newline&f/&bsw addspawn <&ename&b> <&eid&b> &7&fAggiungi uno spawn ad un Arena/!newline&f/&bsw removespawn <&ename&b> <&eid&b> &7&fRimuovi uno spawn ad un Arena/!newline&f/&bsw list &7&fOttieni una lista di tutte le Arene/!newline&f/&bsw info <&ename&b> &7&fOttieni le informazioni di un Arena/!newline&f/&bsw join <&ename&b> &7&fEntra in una Arena/!newline&f/&bsw createtype <&ename&b> &7&fCrea un tipo/!newline&f/&bsw removetype <&ename&b> &7&fRimuovi un tipo/!newline&f/&bsw settype <&earena&b> <&etype&b> &7&fImposta il tipo di un Arena/!newline&f/&bsw edittypes &7&fModifica i vari Tipi disponibili/!newline&f/&bsw chestadmin <&earena&b>&7&fAbilita o disabilita modalit&bChestAdmin/!newline&f/&bsw corner1 <&earena&b>&7&fImposta il primo angolo dell'Arena/!newline&f/&bsw corner2 <&earena&b>&7&fImposta il secondo angolo dell'Arena/!newline ");
        loadString("skywars.commands.arena-exists", "&f&lSKYWARS &b&fQuesta arena esiste già");
                loadString("skywars.commands.arena-not-exists", "&f&lSKYWARS &b&fQuesta arena non esiste");
        loadString("skywars.commands.arena-created", "&f&lSKYWARS &b&fArena creata");
        loadString("skywars.commands.arena-removed", "&f&lSKYWARS &b&fArena rimossa");
        loadString("skywars.commands.arena-list", "&f&lLISTA ARENE");
        loadString("skywars.commands.arena-info", "&f&lINFORMAZIONI ARENA &b&l%NAME%");
        loadString("skywars.commands.arena-ingame", "&f&lSKYWARS &b&fQuesta arena non disponibile, aspetta che termini la partita in corso");
        loadString("skywars.commands.arena-min-set", "&f&lSKYWARS &b&fMinimo di giocatori settato");
        loadString("skywars.commands.arena-max-set", "&f&lSKYWARS &b&fMassimo di giocatori settato");
        loadString("skywars.commands.arena-time-set", "&f&lSKYWARS &b&fTempo per iniziare una partita settato");
        loadString("skywars.commands.arena-type-set", "&f&lSKYWARS &b&fTipo di arena settato");
        loadString("skywars.commands.arena-lobby-set", "&f&lSKYWARS &b&fLobby dell'arena impostata");
        loadString("skywars.commands.arena-spec-set", "&f&lSKYWARS &b&fPunto degli spettatori dell'arena impostato");
        loadString("skywars.commands.arena-spawn-add", "&f&lSKYWARS &b&fSpawn di gioco aggiunto");
        loadString("skywars.commands.arena-spawn-remove", "&f&lSKYWARS &b&fSpawn di gioco rimosso");
        loadString("skywars.commands.arena-spawn-exists", "&f&lSKYWARS &b&fQuesto spawn esiste già");
                loadString("skywars.commands.arena-spawn-not-exists", "&f&lSKYWARS &b&fQuesto spawn non esiste");
        loadString("skywars.game.arena-join", "&f&lSKYWARS &b&fSei entrato in partita!");
        loadString("skywars.game.arena-join-all", "&b%PLAYER% &fentrato in partita!");
        loadString("skywars.game.arena-left", "&f&lSKYWARS &b&fSei uscito dalla partita!");
        loadString("skywars.game.arena-left-all", "&b%PLAYER% &fuscito dalla partita!");
        loadString("skywars.commands.arena-alredy-join", "&f&lSKYWARS &b&fStai gigiocando in un'altra partita!");
        loadString("skywars.commands.arena-not-join", "&f&lSKYWARS &b&fNon stai gigiocando a nessuna partita!");
        loadString("skywars.commands.arena-full", "&f&lSKYWARS &b&fQuesta arena piena");
        loadString("skywars.commands.arena-started", "&f&lSKYWARS &b&fArena avviata");
        loadString("skywars.commands.arena-stopped", "&f&lSKYWARS &b&fArena stoppata");
        loadString("skywars.commands.arena-restarted", "&f&lSKYWARS &b&fArena riavviata");
        loadString("skywars.commands.type-exist", "&f&lSKYWARS &b&fQuesto tipo esiste già");
                loadString("skywars.commands.type-not-exist", "&f&lSKYWARS &b&fQuesto tipo non esiste");
        loadString("skywars.commands.type-created", "&f&lSKYWARS &b&fTipo creato");
        loadString("skywars.commands.type-removed", "&f&lSKYWARS &b&fTipo rimosso");
        loadString("skywars.commands.type-edited", "&f&lSKYWARS &b&fTipo modificato");
        loadString("skywars.commands.chestadmin-enabled", "&f&lSKYWARS &b&fModalitChestAdmin &babilitata");
        loadString("skywars.commands.chestadmin-disabled", "&f&lSKYWARS &b&fModalitChestAdmin &bdisabilitata");
        loadString("skywars.commands.chest-added", "&f&lSKYWARS &b&fChest aggiunta");
        loadString("skywars.commands.chest-removed", "&f&lSKYWARS &b&fChest rimossa");
        loadString("skywars.commands.chest-exist", "&f&lSKYWARS &b&fChest esistente");
        loadString("skywars.commands.chest-not-exist", "&f&lSKYWARS &b&fChest non esistente");
        loadString("skywars.commands.corner-set", "&f&lSKYWARS &b&fAngolo impostato");
        loadString("skywars.game.arena-border", "&f&lSKYWARS &b&fHai raggiunto il bordo dell'arena");
        loadString("skywars.game.player-kill", "&c[!] &fSei stato ucciso da &b%KILLER%");
        loadString("skywars.game.player-kill-all", "&7[!] &b%VICTIM%&f stato ucciso da &b%KILLER%");
        loadString("skywars.game.player-void-kill", "&c[!] &b%KILLER% &fti ha spinto nel vuoto");
        loadString("skywars.game.player-void-kill-all", "&7[!] &b%KILLER%&f ha spinto nel vuoto &b%VICTIM%");
        loadString("skywars.game.player-self-void-kill", "&c[!] &fSei caduto nel vuoto");
        loadString("skywars.game.player-self-void-kill-all", "&7[!] &b%VICTIM%&f caduto nel vuoto");
        loadString("skywars.game.player-self-kill", "&c[!] &fSei morto");
        loadString("skywars.game.player-self-kill-all", "&7[!] &b%VICTIM%&f morto");
        loadString("skywars.game.arena-no-winner", "&c[!] &fQuesta partita terminata senza vincitori");
        loadString("skywars.game.player-win", "&b[!] &fHai vinto!");
        loadString("skywars.game.player-win-all", "&7[!] &b%PLAYER%&f ha vinto!");
        loadString("skywars.chat.lobby", "&b%PLAYER% &8&f%MESSAGE%");
        loadString("skywars.chat.ingame", "&b%PLAYER% &8&f%MESSAGE%");
        loadString("skywars.chat.ingame-team", "&c&lTEAM &b%PLAYER% &8&f%MESSAGE%");
        loadString("skywars.chat.spector", "&b%PLAYER% &8&7%MESSAGE%");
        cfg.save(file);
    }

    private void loadString(String str, String msg) {
        if (cfg.getString(str) != null) {
            messages.put(str, cfg.getString(str));
        } else {
            cfg.set(str, msg);
            messages.put(str, msg);
        }
    }
}
