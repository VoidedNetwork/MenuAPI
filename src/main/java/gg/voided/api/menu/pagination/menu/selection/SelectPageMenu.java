package gg.voided.api.menu.pagination.menu.selection;

import gg.voided.api.menu.MenuSize;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.menu.selection.button.SelectPageButton;
import gg.voided.api.menu.template.impl.PaginationTemplate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A menu to select a page to switch to.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@Getter
public class SelectPageMenu extends PaginatedMenu {
    /**
     * The pagination menu to switch pages on.
     */
    private final PaginatedMenu paginatedMenu;

    /**
     * Creates a new select page menu.
     *
     * @param menu The pagination menu to switch pages on.
     */
    public SelectPageMenu(PaginatedMenu menu) {
        super("Select Page", MenuSize.FOUR, menu.getPlayer());

        this.paginatedMenu = menu;
        setPreviousMenu(menu);
    }

    /**
     * Applies the pagination template.
     *
     * @param background The background layer.
     * @param foreground The foreground layer.
     */
    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {
        apply(new PaginationTemplate(this));
    }

    /**
     * Loops through the total pages and adds
     * a select page button for each one.
     *
     * @return The select page buttons.
     */
    @Override
    public List<Button> getEntries() {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < paginatedMenu.getTotalPages(); i++) {
            buttons.add(new SelectPageButton(i + 1, this));
        }

        return buttons;
    }
}
