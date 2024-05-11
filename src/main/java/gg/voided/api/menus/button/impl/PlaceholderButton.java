package gg.voided.api.menus.button.impl;

import gg.voided.api.menus.button.Button;
import gg.voided.api.menus.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceholderButton extends Button {

    @Override
    public ItemStack getIcon() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 7);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.translate("&r"));

        item.setItemMeta(meta);
        return item;
    }
}
