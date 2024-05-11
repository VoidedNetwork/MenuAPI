package gg.voided.api.menu.button.impl;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceholderButton extends Button {

    @Override
    public ItemStack getIcon() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        item.setDurability((short) 7);
        meta.setDisplayName(Color.RESET);

        item.setItemMeta(meta);
        return item;
    }
}
