package gg.voided.api.menu.pagination.menu.selection.button;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
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
public class SelectPageButton extends Button {
    private final int page;
    private final SelectPageMenu menu;

    @Override
    public ItemStack getIcon() {
        boolean current = page == menu.getPaginatedMenu().getPage();

        ItemStack item = new ItemStack(current ? Material.ENCHANTED_BOOK : Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        item.setAmount(page);
        meta.setDisplayName(Color.translate("&d&lPage " + page + (current ? " &7(Selected)" : "")));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&dSelect Page " + page + " &7(Left Click)");

        meta.setLore(Color.translate(lore));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void onClick(ButtonClick click) {
        if (!click.getType().equals(ClickType.LEFT)) return;

        menu.getPaginatedMenu().page(page);
        menu.back();
    }
}
