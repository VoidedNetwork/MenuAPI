package gg.voided.api.menu.pagination;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuSize;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.buttons.PaginationButton;
import gg.voided.api.menu.utils.MathUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A menu with pagination functionality.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@Getter
public abstract class PaginatedMenu extends Menu {
    /**
     * The buttons to be paginated.
     */
    private final List<Button> entries = new ArrayList<>();

    /**
     * The current page.
     */
    private int page = 1;

    /**
     * The number of pagination slots.
     */
    private int paginationSlots = 0;

    /**
     * Creates a new paginated menu, initializing the underlying bukkit inventory.
     *
     * @param title The title, auto translated.
     * @param size The menu size, amount of rows.
     * @param player The player to open the menu with.
     */
    public PaginatedMenu(String title, MenuSize size, Player player) {
        super(title, size, player);
    }

    /**
     * Updates the pagination slot count, corrects the
     * pagination page and then calls {@link Menu#update()}.
     */
    public void update() {
        paginationSlots = 0;

        for (Button button : getButtons().values()) {
            if (button instanceof PaginationButton) paginationSlots++;
        }

        page = MathUtils.clamp(page, 1, getTotalPages());
        super.update();
    }

    /**
     * Switches to the next page and updates the menu.
     */
    public void nextPage() {
        page++;
        update();
    }

    /**
     * Switches to the previous page and updates the menu.
     */
    public void previousPage() {
        page--;
        update();
    }

    /**
     * Sets the page and updates the menu.
     *
     * @param page The page to switch to.
     */
    public void setPage(int page) {
        this.page = page;
        update();
    }

    /**
     * Adds a button to the pagination entries.
     *
     * @param button The button to add.
     */
    public void addEntry(Button button) {
        if (!getHandler().isUniquePaginationEntries() || !entries.contains(button)) entries.add(button);
    }

    /**
     * Calculates the total page count by dividing
     * the slot count by the entry count.
     *
     * @return The total page count.
     */
    public int getTotalPages() {
        return paginationSlots / entries.size();
    }

    /**
     * Calculates the page index by
     * subtracting one from the page number.
     *
     * @return The page index.
     */
    public int getPageIndex() {
        return page - 1;
    }

    /**
     * Checks if there is a next page.
     *
     * @return If there is a next page.
     */
    public boolean hasNextPage() {
        return page < getTotalPages();
    }

    /**
     * Checks if there is a previous page.
     *
     * @return If there is a previous page.
     */
    public boolean hasPreviousPage() {
        return page > 1;
    }
}
