package gg.voided.api.menus.task;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.MenuHandler;
import gg.voided.api.menus.annotation.AutoUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutoUpdateTask implements Runnable {
    private final MenuHandler handler;
    @Getter private long ticks = 0;

    @Override
    public void run() {
        ticks++;

        for (Menu menu : handler.getOpenMenus().values()) {
            AutoUpdate annotation = menu.getClass().getAnnotation(AutoUpdate.class);
            if (annotation == null || ticks - menu.getLastUpdate() < annotation.value()) continue;

            menu.update();
        }
    }
}
