package gg.voided.api.menu.button;

import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public abstract ItemStack getIcon();
    public void onClick(ButtonClick click) { }
}
