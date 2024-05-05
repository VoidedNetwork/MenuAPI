package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Used to close a menu when a new one opens.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@RequiredArgsConstructor
public class OpenListener implements Listener {
    /**
     * The listener's menu handler.
     */
    private final MenuHandler handler;

    /**
     * Closes a menu when a new one gets opened.
     *
     * @param event The open event.
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Menu menu = handler.getOpenedMenus().get((Player) event.getPlayer());
        if (menu == null) return;

        if (event.getInventory().equals(menu.getInventory())) return;
        menu.close();
    }
}
