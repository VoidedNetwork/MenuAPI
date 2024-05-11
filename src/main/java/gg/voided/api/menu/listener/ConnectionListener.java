package gg.voided.api.menu.listener;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    private final MenuHandler handler;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        handler.ifOpen(event.getPlayer(), Menu::close);
    }
}
