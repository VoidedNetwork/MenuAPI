package gg.voided.api.menu.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * A menu button, used to display an item and handle a click.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
public abstract class Button {
    /**
     * Gets the button's display item stack.
     *
     * @param player The player associated with the menu.
     * @return The item stack to display.
     */
    public abstract ItemStack getItem(Player player);

    /**
     * A click handler, called when the button gets clicked.
     *
     * @param type The click type.
     */
    public void onClick(ClickType type) { }
}
