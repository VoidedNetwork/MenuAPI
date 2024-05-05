package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Handles inventory clicks, sending
 * them to the corresponding menu.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@RequiredArgsConstructor
public class ClickListener implements Listener {
    /**
     * The listener's menu handler.
     */
    private final MenuHandler handler;

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
