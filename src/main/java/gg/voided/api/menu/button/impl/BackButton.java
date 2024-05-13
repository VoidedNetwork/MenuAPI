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

/**
 * A styled back button that returns to the previous menu,
 * if there is no previous menu, the icon will not show.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@RequiredArgsConstructor
public class BackButton extends Button {
    /**
     * The menu to go back from.
     */
    private final Menu menu;

    /**
     * A styles back icon that says
     * return to the previous menu.
     *
     * @return The back button icon.
     */
    @Override
    public ItemStack getIcon() {
        if (!menu.hasPreviousMenu()) return null;

        ItemStack item = new ItemStack(Material.BED);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Color.translate("&d&lBack"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Return to the previous menu");
        lore.add("");
        lore.add("&dGo Back &7(Left Click)");

        meta.setLore(Color.translate(lore));
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Returns to the previous menu when left-clicked.
     *
     * @param click The button click.
     */
    @Override
    public void onClick(ButtonClick click) {
        if (click.getType().equals(ClickType.LEFT)) menu.back();
    }
}
