package gg.voided.api.menu.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public abstract ItemStack getItem(Player player);
    public void onClick(ClickType type) { }
}
