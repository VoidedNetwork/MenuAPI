package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.pagination.PaginationButton;
import gg.voided.api.menu.pagination.button.NextPageButton;
import gg.voided.api.menu.pagination.button.PreviousPageButton;
import gg.voided.api.menu.template.Template;
import gg.voided.api.menu.pagination.PaginatedMenu;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @AllArgsConstructor
public class PaginationTemplate implements Template {
    private final PaginatedMenu menu;
    private Button background;

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {
        new BorderTemplate(menu, this.background).setup(background, foreground);

        foreground.set(0, menu.getRows() - 1, new PreviousPageButton(menu));
        foreground.set(Menu.COLUMNS - 1, menu.getRows() - 1, new NextPageButton(menu));
        foreground.center(new PaginationButton(menu));
    }
}
