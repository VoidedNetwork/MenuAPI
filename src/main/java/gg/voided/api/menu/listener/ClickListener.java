package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor
public class ClickListener implements Listener {
    private final MenuHandler handler;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Menu menu = handler.getOpenedMenus().get((Player) event.getWhoClicked());
        if (menu == null) return;

        if (!event.getClickedInventory().equals(menu.getInventory())) return;

        event.setCancelled(true);
        menu.handleClick(event);
    }
}
