package gg.voided.api.menu.pagination.button;

import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.pagination.PaginatedMenu;
import org.bukkit.inventory.ItemStack;

/**
 * A styled button that switches
 * to the previous page when clicked.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
public class PreviousPageButton extends SwitchPageButton {
    /**
     * Creates a new previous page button.
     *
     * @param menu The menu to switch to the previous page on.
     */
    public PreviousPageButton(PaginatedMenu menu) {
        super(menu);
    }

    /**
     * Returns a styled previous page icon.
     *
     * @return The previous page icon.
     */
    @Override
    public ItemStack getIcon() {
        return getItem("Previous", getMenu().hasPreviousPage());
    }

    /**
     * Handles a previous page click, decrementing the
     * page if the button was left-clicked opening,
     * the select page menu if it was middle-clicked.
     *
     * @param click The button click.
     */
    @Override
    public void onClick(ButtonClick click) {
        onClick(click, getMenu()::previous);
    }
}
