package gg.voided.api.menu.button.impl;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.utils.Color;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BackButton extends Button {
    private final Menu menu;

    @Override
    public ItemStack getIcon() {
        if (!menu.hasPreviousMenu()) return null;

        ItemStack item = new ItemStack(Material.BED);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Color.translate("&d&lBack"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&dGo Back &7(Left Click)");

        meta.setLore(Color.translate(lore));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void onClick(ButtonClick click) {
        if (click.getType().equals(ClickType.LEFT)) menu.back();
    }
}
