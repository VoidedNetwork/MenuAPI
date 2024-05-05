package gg.voided.api.menu.pagination.buttons;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * A pagination button which when added to a menu,
 * mimics the respective entry if present.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@RequiredArgsConstructor
public final class PaginationButton extends Button {
    /**
     * The pagination menu associated with the button.
     */
    private final PaginatedMenu menu;

    /**
     * The pagination entry index.
     */
    @Setter private int index;

    /**
     * Displays the pagination entry button's item stack if present.
     *
     * @param player The player associated with the menu.
     * @return The pagination entry's item stack.
     */
    @Override
    public ItemStack getItem(Player player) {
        Button button = getButton();
        if (button == null) return null;

        return button.getItem(player);
    }

    /**
     * Uses the pagination entry button's click handler if present.
     *
     * @param type The inventory click type.
     */
    @Override
    public void onClick(ClickType type) {
        Button button = getButton();
        if (button == null) return;

        button.onClick(type);
    }

    /**
     * Gets the pagination entry button if present.
     *
     * @return The pagination entry button.
     */
    public Button getButton() {
        int index = menu.getPageIndex() * menu.getPaginationSlots() + this.index;
        if (index > menu.getEntries().size()) return null;

        return menu.getEntries().get(index);
    }
}
