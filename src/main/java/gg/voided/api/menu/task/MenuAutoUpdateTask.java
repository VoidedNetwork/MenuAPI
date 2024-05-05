package gg.voided.api.menu.task;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuHandler;
import gg.voided.api.menu.annotation.AutoUpdate;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MenuAutoUpdateTask implements Runnable {
    private final MenuHandler handler;

    @Override
    public void run() {
        handler.setTicks(handler.getTicks() + 1);

        for (Menu menu : handler.getOpenedMenus().values()) {
            AutoUpdate annotation = menu.getClass().getAnnotation(AutoUpdate.class);
            if (annotation == null) continue;

            if (handler.getTicks() - menu.getLastUpdate() >= annotation.value()) menu.update();
        }
    }
}
