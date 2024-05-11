package gg.voided.api.menus.listener;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

@RequiredArgsConstructor
public class InventoryListener implements Listener {
    private final MenuHandler handler;

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Menu menu = handler.getOpenMenus().get((Player) event.getWhoClicked());
        if (menu == null) return;

        event.setCancelled(true);
        menu.click(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Menu menu = handler.getOpenMenus().get((Player) event.getPlayer());
        if (menu == null) return;

        menu.close();
    }
}
