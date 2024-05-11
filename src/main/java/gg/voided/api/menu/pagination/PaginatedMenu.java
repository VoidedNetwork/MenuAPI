package gg.voided.api.menu.pagination;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuSize;
import gg.voided.api.menu.button.Button;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class PaginatedMenu extends Menu {
    public final static int MINIMUM = 1;

    private final List<Button> paginationEntries = new ArrayList<>();
    private final Map<Integer, Integer> indexCache = new HashMap<>();

    private int page = MINIMUM;
    private int totalPages = MINIMUM;

    public PaginatedMenu(String title, MenuSize size, Player player) {
        super(title, size, player);
    }

    public abstract List<Button> getEntries();

    @Override
    protected void refresh() {
        paginationEntries.clear();
        paginationEntries.addAll(getEntries());

        indexCache.clear();
        int index = 0;

        for (Map.Entry<Integer, Button> entry : getForeground().getButtons().entrySet()) {
            if (entry.getValue() instanceof PaginationButton) {
                indexCache.put(entry.getKey(), ++index);
            }
        }

        totalPages = indexCache.isEmpty() ? MINIMUM : Math.max(
            (int) Math.ceil((double) paginationEntries.size() / getEntrySlots()),
            MINIMUM
        );

        page = Math.min(Math.max(page, MINIMUM), totalPages);
        super.refresh();
    }

    public void next() {
        page++;
        update();
    }

    public void previous() {
        page--;
        update();
    }

    public void page(int number) {
        page = number;
        update();
    }

    public boolean hasNextPage() {
        return page < getTotalPages();
    }

    public boolean hasPreviousPage() {
        return page > 1;
    }

    public int getEntrySlots() {
        return indexCache.size();
    }
}
