package gg.voided.api.menus.template.impl;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.button.Button;
import gg.voided.api.menus.button.impl.BackButton;
import gg.voided.api.menus.button.impl.PlaceholderButton;
import gg.voided.api.menus.layer.Layer;
import gg.voided.api.menus.template.Template;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @AllArgsConstructor
public class BorderTemplate extends Template {
    private final Menu menu;
    private Button background;

    @Override
    public void setup(Layer background, Layer foreground) {
        if (this.background != null) background.fill(this.background);
        background.setBorder(new PlaceholderButton());
        foreground.set(0, new BackButton(menu));
    }
}
