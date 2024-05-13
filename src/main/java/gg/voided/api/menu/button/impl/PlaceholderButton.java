package gg.voided.api.menu.button.impl;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A basic placeholder button with a glass pane as an
 * icon, an empty display name, and no click handler.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
public class PlaceholderButton extends Button {

    /**
     * Returns a placeholder icon
     * with an empty name and a gray
     * stained-glass pane item.
     *
     * @return A placeholder icon.
     */
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
