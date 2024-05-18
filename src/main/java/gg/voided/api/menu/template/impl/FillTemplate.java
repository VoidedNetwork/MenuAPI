package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.impl.BackButton;
import gg.voided.api.menu.button.impl.PlaceholderButton;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.template.Template;
import lombok.RequiredArgsConstructor;

/**
 * A template for a basic filled
 * menu with a back button.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@RequiredArgsConstructor
public class FillTemplate implements Template {
    /**
     * The menu the template is being applied to.
     */
    private final Menu menu;

    /**
     * Fills the menu with a placeholder button and adds a back button.
     *
     * @param background The menu's background layer.
     * @param foreground The menu's foreground layer.
     */
    @Override
    public void apply(BackgroundLayer background, ForegroundLayer foreground) {
        background.fill(new PlaceholderButton());
        foreground.set(0, new BackButton(menu));
    }
}
