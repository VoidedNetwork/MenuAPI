package gg.voided.api.menu.pagination;

import gg.voided.api.menu.button.Button;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PaginationButton extends Button {
    private final PaginatedMenu menu;
    @Setter private int iconIndex;

    @Override
    public ItemStack getIcon() {
        return null;
    }

    @Override
    public void onClick() {

    }
}
