package it.aendrix.skywars.main;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class Messages {

    private final static File file = new File("plugins/SkyWars-Core" + File.separator + "messages.yml");
    private final static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private static HashMap<String, String> messages = new HashMap<>();

    private static final String N = "/!newline";

    public Messages() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getMessage(String msg) {
        return msg.split(N);
    }

    public static void sendMessage(Player p, String msg, String[] ph) {
        msg = messages.get(msg);

        if (ph!=null)
            for(String x : ph) {
                String[] n = x.split(";");
                    msg = msg.replaceAll(n[0].toUpperCase(),n[1]);
            }


        String[] s = msg.split("/!newline");
        for (String x : s)
            utils.sendMsg(p,x);
    }

    public static void sendMessage(CommandSender p, String msg, String[] ph) {
        msg = messages.get(msg);

        if (ph!=null)
            for(String x : ph) {
                String[] n = x.split(";");
                msg = msg.replaceAll(n[0].toUpperCase(),n[1]);
            }


        String[] s = msg.split("/!newline");
        for (String x : s)
            utils.sendMsg(p,x);
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, String> messages) {
        Messages.messages = messages;
    }

    private void load() throws IOException {
        loadString("general.no-permission","&f&lSKYWARS &b➤ &fNon possiedi abbastanza permessi");
        loadString("general.no-arguments","&f&lSKYWARS &b➤ &fNon hai inserito tutti i dati richiesti");
        loadString("general.error-arguments","&f&lSKYWARS &b➤ &fI dati inseriti non corrispondono");
        loadString("general.player-sender","&f&lSKYWARS &b➤ &fSolo un giocatore può eseguire questa azione");
        loadString("general.error","&f&lSKYWARS &b➤ &fErrore");

        loadString("skywars.commands.help"," "+N+
                "&f/&bsw create <&ename&b> &7➤ &fCrea un Arena"+N+
                "&f/&bsw remove <&ename&b> &7➤ &fRimuovi un Arena"+N+
                "&f/&bsw setminplayers <&ename&b> <&eamount&b> &7➤ &fImposta il minimo di giocatori di un Arena"+N+
                "&f/&bsw setmaxplayers <&ename&b> <&eamount&b> &7➤ &fImposta il massimo di giocatori di un Arena"+N+
                "&f/&bsw settimestart <&ename&b> <&eamount&b> &7➤ &fImposta il tempo di un Arena"+N+
                "&f/&bsw setlobby <&ename&b> &7➤ &fImposta la lobby di un Arena"+N+
                "&f/&bsw setspec <&ename&b> &7➤ &fImposta il punto spettatori di un Arena"+N+
                "&f/&bsw addspawn <&ename&b> <&eid&b> &7➤ &fAggiungi uno spawn ad un Arena"+N+
                "&f/&bsw removespawn <&ename&b> <&eid&b> &7➤ &fRimuovi uno spawn ad un Arena"+N+
                "&f/&bsw list &7➤ &fOttieni una lista di tutte le Arene"+N+
                "&f/&bsw info <&ename&b> &7➤ &fOttieni le informazioni di un Arena"+N+
                " ");
        loadString("skywars.commands.arena-exists","&f&lSKYWARS &b➤ &fQuesta arena esiste già");
        loadString("skywars.commands.arena-not-exists","&f&lSKYWARS &b➤ &fQuesta arena non esiste");
        loadString("skywars.commands.arena-created","&f&lSKYWARS &b➤ &fArena creata");
        loadString("skywars.commands.arena-removed","&f&lSKYWARS &b➤ &fArena rimossa");
        loadString("skywars.commands.arena-list","&f&lLISTA ARENE");
        loadString("skywars.commands.arena-info","&f&lINFORMAZIONI ARENA &b&l%NAME%");
        loadString("skywars.commands.arena-ingame","&f&lSKYWARS &b➤ &fPer proseguire, l'Arena deve essere in stato Stoppato");
        loadString("skywars.commands.arena-min-set","&f&lSKYWARS &b➤ &fMinimo di giocatori settato");
        loadString("skywars.commands.arena-max-set","&f&lSKYWARS &b➤ &fMassimo di giocatori settato");
        loadString("skywars.commands.arena-time-set","&f&lSKYWARS &b➤ &fTempo per iniziare una partita settato");
        loadString("skywars.commands.arena-lobby-set","&f&lSKYWARS &b➤ &fLobby dell'arena impostata");
        loadString("skywars.commands.arena-spec-set","&f&lSKYWARS &b➤ &fPunto degli spettatori dell'arena impostato");
        loadString("skywars.commands.arena-spawn-add","&f&lSKYWARS &b➤ &fSpawn di gioco aggiunto");
        loadString("skywars.commands.arena-spawn-remove","&f&lSKYWARS &b➤ &fSpawn di gioco rimosso");
        loadString("skywars.commands.arena-spawn-exists","&f&lSKYWARS &b➤ &fQuesto spawn esiste già");
        loadString("skywars.commands.arena-spawn-not-exists","&f&lSKYWARS &b➤ &fQuesto spawn non esiste");



        cfg.save(file);
    }

    private void loadString(String str, String msg) {
        if (cfg.getString(str)!=null)
            messages.put(str,cfg.getString(str));
        else {
            cfg.set(str,msg);
            messages.put(str,msg);
        }
    }
}
