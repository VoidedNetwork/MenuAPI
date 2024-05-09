package gg.voided.api.menu.task;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import gg.voided.api.menu.annotation.AutoUpdate;
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
