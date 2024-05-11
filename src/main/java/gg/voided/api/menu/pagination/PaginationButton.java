package gg.voided.api.menu.pagination;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PaginationButton extends Button {
    private final PaginatedMenu menu;
    @Setter private int iconSlot;

    @Override
    public ItemStack getIcon() {
        Button button = getButton(iconSlot);
        return button != null ? button.getIcon() : null;
    }

    @Override
    public void onClick(ButtonClick click) {
        Button button = getButton(click.getSlot());
        if (button != null) button.onClick(click);
    }

    private Button getButton(int slot) {
        int index = (menu.getPage() - 1) * menu.getEntrySlots() + menu.getIndexCache().get(slot);
        if (index >= menu.getPaginationEntries().size()) return null;

        return menu.getPaginationEntries().get(index);
    }
}
