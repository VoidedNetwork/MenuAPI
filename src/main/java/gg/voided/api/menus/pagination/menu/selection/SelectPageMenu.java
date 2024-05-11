package gg.voided.api.menus.pagination.menu.selection;

import gg.voided.api.menus.MenuSize;
import gg.voided.api.menus.button.Button;
import gg.voided.api.menus.layer.Layer;
import gg.voided.api.menus.pagination.PaginatedMenu;
import gg.voided.api.menus.pagination.menu.selection.button.SelectPageButton;
import gg.voided.api.menus.template.impl.PaginationTemplate;
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
    public void setup(Layer background, Layer foreground) {
        apply(new PaginationTemplate(this));
    }

    @Override
    public List<Button> getEntries() {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < paginatedMenu.getTotalPages(); i++) {
            buttons.add(new SelectPageButton(i + 1, this));
        }

        return buttons;
    }
}
