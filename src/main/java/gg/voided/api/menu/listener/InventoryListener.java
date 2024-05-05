package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Handles inventory related actions.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@RequiredArgsConstructor
public class InventoryListener implements Listener {
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

    /**
     * Calls the close handler of the menu when it is closed.
     *
     * @param event The close event.
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Menu menu = handler.getOpenedMenus().get((Player) event.getPlayer());
        if (menu == null) return;

        menu.close();
    }

    /**
     * Sends a click event to a player's menu if they have one
     * open, cancelling the click, so they can't pick up the item.
     *
     * @param event The click event.
     */
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
