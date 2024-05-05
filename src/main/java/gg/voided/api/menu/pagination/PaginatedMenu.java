package gg.voided.api.menu.pagination;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.MenuSize;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.buttons.PaginationButton;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class PaginatedMenu extends Menu {
    private final List<Button> entries = new ArrayList<>();
    private int page = 1;
    private int paginationSlots = 0;

    public PaginatedMenu(String title, MenuSize size, Player player) {
        super(title, size, player);
    }

    public void update() {
        paginationSlots = 0;

        for (Button button : getButtons().values()) {
            if (button instanceof PaginationButton) paginationSlots++;
        }

        page = MathUtils.clamp(page, 1, getTotalPages());
        super.update();
    }

    public void nextPage() {
        page++;
        update();
    }

    public void previousPage() {
        page--;
        update();
    }

    public void setPage(int page) {
        this.page = page;
        update();
    }

    public void addEntry(Button button) {
        if (!getHandler().isUniquePaginationEntries() || !entries.contains(button)) entries.add(button);
    }

    public int getTotalPages() {
        return paginationSlots / entries.size();
    }

    public int getPageIndex() {
        return page - 1;
    }
}
