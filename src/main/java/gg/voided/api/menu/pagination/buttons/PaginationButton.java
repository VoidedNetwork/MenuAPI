package gg.voided.api.menu.pagination.buttons;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PaginationButton extends Button {
    private final PaginatedMenu menu;
    @Setter private int index;

    @Override
    public ItemStack getItem(Player player) {
        Button button = getButton();
        if (button == null) return null;

        return button.getItem(player);
    }

    @Override
    public void onClick(ClickType type) {
        Button button = getButton();
        if (button == null) return;

        button.onClick(type);
    }

    public Button getButton() {
        int index = menu.getPageIndex() * menu.getPaginationSlots() + this.index;
        if (index > menu.getEntries().size()) return null;

        return menu.getEntries().get(index);
    }
}
