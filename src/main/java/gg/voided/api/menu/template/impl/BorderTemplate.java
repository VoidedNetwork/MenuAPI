package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.impl.BackButton;
import gg.voided.api.menu.button.impl.PlaceholderButton;
import gg.voided.api.menu.layer.Layer;
import gg.voided.api.menu.template.Template;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @AllArgsConstructor
public class BorderTemplate extends Template {
    private final Menu menu;
    private Button background;

    @Override
    public void setup(Layer background, Layer foreground) {
        background.fill(this.background);
        background.setBorder(new PlaceholderButton());
        foreground.set(0, new BackButton(menu));
    }
}
