package gg.voided.api.menus.button;

import gg.voided.api.menus.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

@Getter
public class ButtonClick {
    @Getter(AccessLevel.NONE) private final InventoryClickEvent event;

    private final Menu menu;
    private final Button button;
    private final Player player;
    private final ClickType type;
    private final int slot;

    @Setter private boolean ignored = false;

    public ButtonClick(InventoryClickEvent event, Button button, Menu menu) {
        this.event = event;
        this.menu = menu;
        this.button = button;
        this.player = menu.getPlayer();
        this.type = event.getClick();
        this.slot = event.getSlot();
    }
}
