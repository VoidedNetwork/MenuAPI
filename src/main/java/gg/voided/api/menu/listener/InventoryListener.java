package gg.voided.api.menu.listener;

import gg.voided.api.menu.MenuHandler;
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
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        handler.ifOpen((Player) event.getWhoClicked(), (menu) -> {
            event.setCancelled(true);
            menu.click(event);
        });
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        handler.ifOpen((Player) event.getPlayer(), (menu -> menu.close(false)));
    }
}
