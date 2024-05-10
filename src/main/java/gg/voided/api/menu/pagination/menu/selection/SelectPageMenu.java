package gg.voided.api.menu.pagination.menu.selection;

import gg.voided.api.menu.MenuSize;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.menu.selection.button.SelectPageButton;
import gg.voided.api.menu.template.impl.PaginationTemplate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SelectPageMenu extends PaginatedMenu {
    private final PaginatedMenu paginatedMenu;

    public SelectPageMenu(PaginatedMenu menu) {
        super("Select Page", MenuSize.FOUR, menu.getPlayer());

        this.paginatedMenu = menu;
        setPreviousMenu(menu);
    }

    @Override
    public void setup() {
        apply(new PaginationTemplate(this));
    }

    @Override
    public List<Button> getPaginatedButtons() {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < paginatedMenu.getTotalPages(); i++) {
            buttons.add(new SelectPageButton(i + 1, this));
        }

        return buttons;
    }
}
