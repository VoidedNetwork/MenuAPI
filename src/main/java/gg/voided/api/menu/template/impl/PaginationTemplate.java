package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.pagination.PaginationSlot;
import gg.voided.api.menu.pagination.button.NextPageButton;
import gg.voided.api.menu.pagination.button.PreviousPageButton;
import gg.voided.api.menu.template.Template;
import gg.voided.api.menu.pagination.PaginatedMenu;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * A template for a bordered pagination menu with
 * a back button and a previous and next page button.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@RequiredArgsConstructor @AllArgsConstructor
public class PaginationTemplate implements Template {
    /**
     * The paginated menu the template is being applied to.
     */
    private final PaginatedMenu menu;

    /**
     * The button to add to the background.
     */
    private Button background;

    /**
     * Applies the border template, adds a previous
     * and next page button and fills the center
     * of the menu with pagination slots.
     *
     * @param background The menu's background layer.
     * @param foreground The menu's foreground layer.
     */
    @Override
    public void apply(BackgroundLayer background, ForegroundLayer foreground) {
        new BorderTemplate(menu, this.background).apply(background, foreground);

        foreground.set(0, menu.getRows() - 1, new PreviousPageButton(menu));
        foreground.set(Menu.COLUMNS - 1, menu.getRows() - 1, new NextPageButton(menu));
        foreground.center(new PaginationSlot(menu));
    }
}
