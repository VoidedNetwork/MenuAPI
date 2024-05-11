package gg.voided.api.menu.layer.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.layer.Layer;
import gg.voided.api.menu.pagination.PaginationButton;

public class BackgroundLayer extends Layer {

    public BackgroundLayer(Menu menu) {
        super(menu);
    }

    @Override
    public String getName() {
        return "background";
    }

    @Override
    protected boolean isInvalid(Button button, int index) {
        if (super.isInvalid(button, index)) return true;

        if (button instanceof PaginationButton) {
            warning("pagination", button, index);
            return true;
        }

        return false;
    }
}
