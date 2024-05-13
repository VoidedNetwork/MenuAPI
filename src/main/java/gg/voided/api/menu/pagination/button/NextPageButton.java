package gg.voided.api.menu.pagination.button;

import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.pagination.PaginatedMenu;
import org.bukkit.inventory.ItemStack;

/**
 * A styled button that switches
 * to the next page when clicked.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
public class NextPageButton extends SwitchPageButton {
    /**
     * Creates a new next page button.
     *
     * @param menu The menu to switch to the next page on.
     */
    public NextPageButton(PaginatedMenu menu) {
        super(menu);
    }

    /**
     * Returns a styled next page icon.
     *
     * @return The next page icon.
     */
    @Override
    public ItemStack getIcon() {
        return getItem("Next", getMenu().hasNextPage());
    }

    /**
     * Handles a next page click, incrementing the
     * page if the button was left-clicked opening,
     * the select page menu if it was middle-clicked.
     *
     * @param click The button click.
     */
    @Override
    public void onClick(ButtonClick click) {
        onClick(click, getMenu()::next);
    }
}
