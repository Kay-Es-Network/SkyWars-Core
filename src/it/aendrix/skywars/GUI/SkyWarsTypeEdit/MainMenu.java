package it.aendrix.skywars.GUI.SkyWarsTypeEdit;

import it.aendrix.skywars.GUI.GUI;
import it.aendrix.skywars.items.MessageRequire;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.utils;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends GUI implements Listener {

    private static ItemStack[] items;

    public MainMenu() {
        super("&8&lTIPOLOGIE", 36);
        createItems();

        Main.getInstance().getServer().getPluginManager().registerEvents(this,Main.getInstance());
    }

    public void createItems() {
        items = new ItemStack[54];

        items[11] = utils.itemcreate(
                Material.BOW,(short) 0,1,"&6&lMODIFICA UN TIPO",
                new String[]{
                        "","&fModifica un &bTipo","&fa tua scelta"
                },
                true
        );

        items[15] = utils.itemcreate(
                Material.ARROW,(short) 0,1,"&6&lCREA UN TIPO",
                new String[]{
                        "","&fCrea un nuovo &bTipo"
                },
                false
        );

        for (int i = 27; i<36; i++)
            items[i] = utils.itemcreate(Material.STAINED_GLASS_PANE,(short) 15,1,"&f", null,false);

        items[31] = utils.itemcreate(Material.RECORD_11,(short) 0,1,"&6&lCHIUDI", null,false);

        super.initialize(items);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (super.using(p))
            super.user.remove(p);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!super.using(p))
            return;
        ItemStack item = e.getCurrentItem();

        if (!e.getInventory().equals(this.inventory))
            return;

        e.setCancelled(true);

        if (e.getSlot()==11) {
            //Scegli chi modificare
            if (SkyWarsType.getTypes().size()==0) {
                utils.sendMsg(p, "&fNessun tipo &bdisponibile");
                return;
            }
            String s = "&fScegli chi modificare tra: &b";
            for (SkyWarsType ty : SkyWarsType.getTypes().values())
                s+=ty.getName()+" ";
            utils.sendMsg(p,s);

            MessageRequire.addMess(p,"SkyWarsTypeEdit.MainMenu;0");
            p.closeInventory();
        } else if (e.getSlot()==15) {
            //Crea nuovo
            utils.sendMsg(p,"&fDigita il nome del nuovo &btipo");
            MessageRequire.addMess(p,"SkyWarsTypeEdit.MainMenu;1");
            p.closeInventory();
        } else if (e.getSlot()==31) {
            //Chiudi
            p.closeInventory();
        }
    }
}
