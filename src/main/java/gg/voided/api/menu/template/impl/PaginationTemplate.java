package gg.voided.api.menu.template.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.impl.BackButton;
import gg.voided.api.menu.button.impl.PlaceholderButton;
import gg.voided.api.menu.layer.Layer;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.PaginationButton;
import gg.voided.api.menu.pagination.button.NextPageButton;
import gg.voided.api.menu.pagination.button.PreviousPageButton;
import gg.voided.api.menu.template.Template;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaginationTemplate implements Template {
    private final PaginatedMenu menu;

    @Override
    public void setup(Layer background, Layer foreground) {
        background.setBorder(new PlaceholderButton());
        foreground.fill(new PaginationButton(menu), 1);

        foreground.set(0, new BackButton(menu));
        foreground.set(0, menu.getRows() - 1, new PreviousPageButton(menu));
        foreground.set(Menu.COLUMNS - 1, menu.getRows() - 1, new NextPageButton(menu));
    }
}
