package gg.voided.api.menu.task;

import gg.voided.api.menu.MenuHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutoUpdateTask implements Runnable {
    private final MenuHandler handler;
    @Getter private int ticks = 0;

    @Override
    public void run() {

    }
}
