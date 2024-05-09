package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.layer.Layer;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.PaginationButton;
import gg.voided.api.menu.pagination.button.NextPageButton;
import gg.voided.api.menu.pagination.button.PreviousPageButton;
import gg.voided.api.menu.template.Template;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @AllArgsConstructor
public class PaginationTemplate extends Template {
    private final PaginatedMenu menu;
    private Button background;

    @Override
    public void setup(Layer background, Layer foreground) {
        new BorderTemplate(menu, this.background).setup(background, foreground);

        foreground.set(0, menu.getRows() - 1, new PreviousPageButton(menu));
        foreground.set(Menu.COLUMNS - 1, menu.getRows() - 1, new NextPageButton(menu));
        foreground.fill(new PaginationButton(menu), 1);
    }
}
