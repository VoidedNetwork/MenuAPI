package gg.voided.api.menu.pagination.button;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.pagination.PaginatedMenu;
import gg.voided.api.menu.pagination.menu.selection.SelectPageMenu;
import gg.voided.api.menu.utils.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A styled button that switches pages when clicked.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@Getter
@RequiredArgsConstructor
public abstract class SwitchPageButton extends Button {
    /**
     * The menu to switch pages on.
     */
    private final PaginatedMenu menu;

    /**
     * Returns the switch page icon using the
     * name, returns null if show is false.
     *
     * @param name The switch name.
     * @param show If the button should be shown.
     * @return The switch page icon.
     */
    public ItemStack getItem(String name, boolean show) {
        if (!show) return null;

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Color.translate("&d&l" + name + " Page"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Page: &f" + menu.getPage() + "&7/&f" + menu.getTotalPages());
        lore.add("");
        if (!(menu instanceof SelectPageMenu)) lore.add("&dSelect Page &7(Middle Click)");
        lore.add("&d" + name + " Page &7(Left Click)");

        meta.setLore(Color.translate(lore));
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Handles a switch page click, executing the
     * task if the button was left-clicked opening,
     * the select page menu if it was middle-clicked.
     *
     * @param click The button click.
     * @param task The task to run when left-clicked.
     */
    public void onClick(ButtonClick click, Runnable task) {
        if (click.getType().equals(ClickType.MIDDLE) && !(menu instanceof SelectPageMenu)) {
            new SelectPageMenu(menu).open();
            return;
        }

        if (click.getType().equals(ClickType.LEFT)) task.run();
    }
}
