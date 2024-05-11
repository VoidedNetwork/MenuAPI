package gg.voided.api.menu.template;

import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;

public interface Template {
    void setup(BackgroundLayer background, ForegroundLayer foreground);
}
