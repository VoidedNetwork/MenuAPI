package gg.voided.api.menus;

import gg.voided.api.menus.button.Button;
import gg.voided.api.menus.button.ButtonClick;
import gg.voided.api.menus.layer.Layer;
import gg.voided.api.menus.pagination.PaginationButton;
import gg.voided.api.menus.template.Template;
import gg.voided.api.menus.utils.Color;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Menu {
    public static final int COLUMNS = 9;

    private final MenuHandler handler = MenuHandler.getInstance();

    private final Player player;
    private final Inventory inventory;
    private final String title;
    private final int rows;

    private final Layer foreground;
    private final Layer background;

    @Setter private Menu previousMenu;
    @Setter private boolean async = false;

    private long lastUpdate = 0;
    private boolean setup = false;

    private final Map<Integer, Button> buttons = new HashMap<>();

    public Menu(String title, MenuSize size, Player player) {
        this.player = player;
        this.rows = size.getRows();

        this.foreground = new Layer(this);
        this.background = new Layer(this);

        this.title = Color.translate(title);
        this.inventory = Bukkit.createInventory(player, getTotalSlots(), this.title);
    }

    public abstract void setup(Layer background, Layer foreground);
    public void onOpen() { }
    public void onClose() { }
    public void onClick(ButtonClick click) { }

    public void open() {
        handler.runTask(() -> {
            if (!setup) {
                setup(background, foreground);
                setup = true;
            }

            update(false);

            handler.runSync(() -> {
                if (handler.isResetCursor()) player.closeInventory();
                player.openInventory(inventory);

                handler.runTask(() -> {
                    onOpen();
                    handler.getOpenMenus().put(player, this);
                }, async);
            });
        }, async);
    }

    public void close() {
        handler.getOpenMenus().remove(player, this);
        handler.runTask(this::onClose, async);
    }

    public void update() {
        update(async);
    }

    protected void update(boolean async) {
        handler.runCurrent(() -> {
            lastUpdate = handler.getAutoUpdateTask().getTicks();
            buttons.clear();

            ItemStack[] icons = new ItemStack[getTotalSlots()];
            update(foreground, icons);
            update(background, icons);

            handler.runSync(() -> {
                inventory.setContents(icons);
                player.updateInventory();
            });
        }, !async);
    }

    protected void update(Layer layer, ItemStack[] icons) {
        layer.getButtons().forEach((slot, button) -> {
            if (slot >= getTotalSlots()) {
                handler.getPlugin().getLogger().warning(
                    "[MenuAPI] Menu '" + getClass().getSimpleName() +
                        "' has button '" + button.getClass().getSimpleName() +
                        "' at index '" + slot +
                        "' out of bounds '" + getTotalSlots() + "'."
                );

                return;
            }

            if (buttons.get(slot) != null) return;

            if (button instanceof PaginationButton) {
                ((PaginationButton) button).setIconSlot(slot);
            }

            ItemStack icon = button.getIcon();
            icons[slot] = icon;

            if (icon != null) buttons.put(slot, button);
        });
    }

    public void back() {
        if (previousMenu == null) {
            if (handler.isCloseOnBack()) close();
            return;
        }

        previousMenu.open();
    }

    public void apply(Template template) {
        template.setup(background, foreground);
    }

    public void click(InventoryClickEvent event) {
        Button button = buttons.get(event.getSlot());
        ButtonClick click = new ButtonClick(event, button, this);

        onClick(click);
        if (button == null || click.isIgnored()) return;

        button.onClick(click);
    }

    public int getTotalSlots() {
        return rows * COLUMNS;
    }

    public int getIndex(int x, int y) {
        return y * COLUMNS + x;
    }

    public boolean hasPreviousMenu() {
        return previousMenu != null;
    }
}
