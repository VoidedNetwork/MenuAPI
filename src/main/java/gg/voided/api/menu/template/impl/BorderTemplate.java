package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.impl.BackButton;
import gg.voided.api.menu.button.impl.PlaceholderButton;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.template.Template;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @AllArgsConstructor
public class BorderTemplate implements Template {
    private final Menu menu;
    private Button background;

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {
        if (this.background != null) background.center(this.background);
        background.border(new PlaceholderButton());
        foreground.set(0, new BackButton(menu));
    }
}
