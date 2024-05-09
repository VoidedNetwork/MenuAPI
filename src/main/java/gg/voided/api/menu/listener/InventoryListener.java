package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public class InventoryListener implements Listener {
    private final MenuHandler handler;

    @EventHandler
    public void onInteract(InventoryInteractEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Menu menu = handler.getOpenMenus().get((Player) event.getWhoClicked());
        if (menu == null) return;

        Inventory inventory = event.getInventory();
        if (inventory != null && !menu.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        if (!(event instanceof InventoryClickEvent)) return;
        menu.click((InventoryClickEvent) event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Menu menu = handler.getOpenMenus().get((Player) event.getPlayer());
        if (menu == null) return;

        menu.close();
    }
}
