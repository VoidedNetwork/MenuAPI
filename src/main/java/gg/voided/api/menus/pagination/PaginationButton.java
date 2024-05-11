package gg.voided.api.menus.pagination;

import gg.voided.api.menus.button.Button;
import gg.voided.api.menus.button.ButtonClick;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class PaginationButton extends Button {
    private final PaginatedMenu menu;
    @Setter private int iconSlot = -1;

    @Override
    public ItemStack getIcon() {
        if (iconSlot == -1) return new ItemStack(Material.AIR);
        Button button = getButton(iconSlot);
        return button != null ? button.getIcon() : null;
    }

    @Override
    public void onClick(ButtonClick click) {
        Button button = getButton(click.getSlot());
        if (button != null) button.onClick(click);
    }

    private Button getButton(int slot) {
        Integer relative = menu.getIndexCache().get(slot);
        if (relative == null) return null;

        int index = menu.getPageIndex() * menu.getPaginationSlots() + relative;
        if (index >= menu.getPaginatedButtons().size()) return null;

        return menu.getPaginatedButtons().get(index);
    }
}
