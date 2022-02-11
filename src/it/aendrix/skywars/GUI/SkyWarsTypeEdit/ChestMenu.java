package it.aendrix.skywars.GUI.SkyWarsTypeEdit;

import it.aendrix.skywars.GUI.GUI;
import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.main.utils.utils;
import it.aendrix.skywars.skywars.SkyWarsType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ChestMenu extends GUI implements Listener {
    private ItemStack[] items;

    private final SkyWarsType type;

    public ChestMenu(SkyWarsType type) {
        super("&8&l" + type.getName().toUpperCase(), 36);
        this.type = type;
        createItems();
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void createItems() {
        this.items = new ItemStack[54];
        int i;
        for (i = 0; i < 5; i++) {
            if (this.type.getChestsType()[i] != null) {
                this.items[11 + i] = utils.itemcreate(Material.CHEST, (short)0, 1, "&bLivello &l" + i + 1, new String[] { "", "&7Massimo: &f" + this.type
                        .getChestsType()[i].getMaxItems(), "&7Minimo: &f" + this.type.getChestsType()[i].getMinItems(), "&7Oggetti: &f" + this.type.getChestsType()[i].getItems().size() }, false);
            } else {
                this.items[11 + i] = utils.itemcreate(Material.COAL_BLOCK, (short)0, 1, "&bLivello &l" + i + 1, new String[] { "", "&7Non configurato" }, false);
            }
        }
        for (i = 27; i < 36; i++)
            this.items[i] = utils.itemcreate(Material.STAINED_GLASS_PANE, (short)15, 1, "&f", null, false);
        this.items[31] = utils.itemcreate(Material.RECORD_11, (short)0, 1, "&6&lCHIUDI", null, false);
        initialize(this.items);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        if (using(p)) {
            this.user.remove(p);
            this.items = null;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (!using(p))
            return;
        if (!e.getInventory().equals(this.inventory))
            return;
        e.setCancelled(true);
        if (e.getSlot() >= 11 && e.getSlot() <= 15) {
            int i = e.getSlot() - 11;
            Chest[] c = this.type.getChestsType();
            if (c[i] == null) {
                c[i] = new Chest(2, 6, i + 1, new ArrayList<>());
                createItems();
            } else {
                EditMenu.open(p, c[i], this.type);
            }
        }
        if (e.getSlot() == 31)
            p.closeInventory();
    }
}
