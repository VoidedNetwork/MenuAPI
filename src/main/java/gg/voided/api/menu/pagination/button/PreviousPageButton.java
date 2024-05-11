package gg.voided.api.menu.pagination.button;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.menu.selection.SelectPageMenu;
import gg.voided.api.menu.utils.Color;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PreviousPageButton extends Button {
    private final PaginatedMenu menu;

    @Override
    public ItemStack getIcon() {
        if (!menu.hasPreviousPage()) return null;

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Color.translate("&d&lPrevious Page"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Page: &f" + menu.getPage() + "&7/&f" + menu.getTotalPages());
        lore.add("");
        lore.add("&dSelect Page &7(Middle Click)");
        lore.add("&dPrevious Page &7(Left Click)");

        meta.setLore(Color.translate(lore));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void onClick(ButtonClick click) {
        if (click.getType().equals(ClickType.MIDDLE)) {
            new SelectPageMenu(menu).open();
            return;
        }

        if (click.getType().equals(ClickType.LEFT)) menu.previous();
    }
}
