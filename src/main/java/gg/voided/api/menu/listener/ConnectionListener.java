package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles connection related actions.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    /**
     * The listener's menu handler.
     */
    private final MenuHandler handler;

    /**
     * Closes a player's menu when they leave if they have one open.
     *
     * @param event The quit event.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Menu menu = handler.getOpenedMenus().get(event.getPlayer());
        if (menu != null) menu.close();
    }
}
