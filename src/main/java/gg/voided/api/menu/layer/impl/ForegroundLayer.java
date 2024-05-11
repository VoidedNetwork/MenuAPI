package gg.voided.api.menu.layer.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.layer.Layer;

public class ForegroundLayer extends Layer {

    public ForegroundLayer(Menu menu) {
        super(menu);
    }

    @Override
    public String getName() {
        return "foreground";
    }
}
