package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles closing the inventory when players quit.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    /**
     * The menu handler that registered the listener.
     */
    private final MenuHandler handler;

    /**
     * Closes a players menu when they leave the game.
     *
     * @param event The quit event.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        handler.ifOpen(event.getPlayer(), Menu::close);
    }
}
