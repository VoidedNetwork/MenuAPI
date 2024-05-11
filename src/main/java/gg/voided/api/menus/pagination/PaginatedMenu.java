package gg.voided.api.menus.pagination;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.MenuSize;
import gg.voided.api.menus.button.Button;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class PaginatedMenu extends Menu {
    public final static int MINIMUM_PAGE = 1;

    private final List<Button> paginatedButtons = new ArrayList<>();
    private final Map<Integer, Integer> indexCache = new HashMap<>();

    private int page = MINIMUM_PAGE;
    private int totalPages = MINIMUM_PAGE;

    public PaginatedMenu(String title, MenuSize size, Player player) {
        super(title, size, player);
    }

    public abstract List<Button> getEntries();

    @Override
    protected void update(boolean async) {
        getHandler().runTask(() -> {
            paginatedButtons.clear();
            paginatedButtons.addAll(getEntries());

            super.update(false);

            int index = 0;
            indexCache.clear();

            for (Map.Entry<Integer, Button> entry : getButtons().entrySet()) {
                if (entry.getValue() instanceof PaginationButton) {
                    indexCache.put(entry.getKey(), ++index);
                }
            }

            if (getPaginationSlots() < 1) {
                totalPages = MINIMUM_PAGE;
                page = MINIMUM_PAGE;
                return;
            }

            totalPages = Math.max(
                (int) Math.ceil((double) paginatedButtons.size() / getPaginationSlots()),
                MINIMUM_PAGE
            );

            page = Math.min(Math.max(page, MINIMUM_PAGE), totalPages);
            super.update(false);
        }, isAsync());
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

    public int getPageIndex() {
        return page - 1;
    }

    public int getPaginationSlots() {
        return indexCache.size();
    }

    public boolean hasNextPage() {
        return page < getTotalPages();
    }

    public boolean hasPreviousPage() {
        return page > 1;
    }
}
