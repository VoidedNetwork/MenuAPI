package gg.voided.api.menus.listener;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    private final MenuHandler handler;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Menu menu = handler.getOpenMenus().get(event.getPlayer());
        if (menu != null) menu.close();
    }
}
