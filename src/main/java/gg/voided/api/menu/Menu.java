package gg.voided.api.menu;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.template.Template;
import gg.voided.api.menu.utils.Color;
import gg.voided.api.menu.utils.Position;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.TreeMap;

/**
 * A wrapper around a bukkit inventory with
 * buttons, layers, and async functionality.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@Getter
public abstract class Menu {
    /**
     * The in game chest inventory width.
     */
    public static final int COLUMNS = 9;

    /**
     * The menu handler the menu should use.
     */
    private final MenuHandler handler = MenuHandler.getInstance();

    /**
     * The buttons shown inside the inventory.
     */
    private final Map<Integer, Button> buttons = new TreeMap<>();

    /**
     * The player associated with the menu.
     */
    private final Player player;

    /**
     * The underlying bukkit inventory the player sees.
     */
    private final Inventory inventory;

    /**
     * The title shown at the top of the inventory.
     */
    private final String title;

    /**
     * The menu size in rows.
     */
    private final int rows;

    /**
     * The menu's foreground layer.
     */
    private final ForegroundLayer foreground;

    /**
     * The menu's background layer.
     */
    private final BackgroundLayer background;

    /**
     * The previous menu, if present.
     */
    @Setter private Menu previousMenu;

    /**
     * If the menu should be asynchronous.
     */
    @Setter private boolean async = false;

    /**
     * The last tick that the menu was updated.
     */
    private long lastUpdate = 0;

    /**
     * If the menu's buttons have been set up.
     */
    private boolean setup = false;

    /**
     * Creates a new menu, initializing the underlying bukkit inventory.
     *
     * @param title The title, auto translated.
     * @param size The menu size, in rows.
     * @param player The player to open the menu with.
     */
    public Menu(String title, MenuSize size, Player player) {
        if (this.handler == null) {
            throw new IllegalStateException("No menu handler instance found.");
        }

        this.player = player;
        this.rows = size.getRows();

        this.foreground = new ForegroundLayer(this);
        this.background = new BackgroundLayer(this);

        this.title = Color.translate(title);
        this.inventory = Bukkit.createInventory(player, getTotalSlots(), this.title);
    }

    /**
     * Used for setting up the menu layout,
     * called when the inventory is first opened.
     *
     * @param background The background layer.
     * @param foreground The foreground layer.
     */
    public abstract void setup(BackgroundLayer background, ForegroundLayer foreground);

    /**
     * Called when the menu opens.
     */
    public void onOpen() { }

    /**
     * Called when the menu closes.
     */
    public void onClose() { }

    /**
     * Called when any button is clicked,
     * or when the outside of the inventory is
     * clicked, in which case the button is null.
     *
     * @param click The button click.
     */
    public void onClick(ButtonClick click) { }

    /**
     * Opens the menu, closing any existing menus if they are open.
     * The setup method is called on the first invocation of this method.
     */
    public void open() {
        handler.runTask(() -> {
            if (!setup) {
                setup(background, foreground);
                setup = true;
            }

            refresh();

            handler.runSync(() -> {
                Menu existing = handler.getOpenMenus().get(player);

                if (existing == null) {
                    player.closeInventory();
                } else {
                    existing.close(handler.isResetCursor());
                }

                player.openInventory(inventory);
                handler.getOpenMenus().put(player, this);
                handler.runAsync(this::onOpen, async);
            });
        }, async);
    }

    /**
     * Removes the player from the open menus map.
     *
     * @param exit If the player's inventory should be closed.
     */
    public void close(boolean exit) {
        if (exit) player.closeInventory();
        handler.getOpenMenus().remove(player, this);
        handler.runAsync(this::onClose, async);
    }

    /**
     * Closes the menu and removes the player from the open menus map.
     */
    public void close() {
        close(true);
    }

    /**
     * Merges layers and updates the inventory contents.
     */
    protected void refresh() {
        lastUpdate = handler.getAutoUpdateTask().getTicks();
        buttons.clear();

        ItemStack[] icons = new ItemStack[getTotalSlots()];
        buttons.putAll(foreground.getButtons(icons));
        buttons.putAll(background.getButtons(icons));

        handler.runSync(() -> {
            inventory.setContents(icons);
            player.updateInventory();
        });
    }

    /**
     * Updates the inventory buttons shown in the menu.
     */
    public void update() {
        handler.runTask(this::refresh, async);
    }

    /**
     * Returns to the previous menu, if the handler
     * has closeOnBack set and there is no previous
     * menu, then the menu will close.
     */
    public void back() {
        if (previousMenu == null) {
            if (handler.isCloseOnBack()) close();
        } else {
            previousMenu.open();
        }
    }

    /**
     * Applies a template to the background and foreground layers.
     *
     * @param template The template to apply.
     */
    public void apply(Template template) {
        template.apply(background, foreground);
    }

    /**
     * Handles a menu click, sending the click to the slot's button.
     *
     * @param event The inventory click event.
     */
    public void click(InventoryClickEvent event) {
        handler.runTask(() -> {
            Button button = buttons.get(event.getSlot());
            ButtonClick click = new ButtonClick(event, button, this);

            onClick(click);
            if (button == null || click.isIgnored()) return;

            button.onClick(click);
        }, async);
    }

    /**
     * Checks if there is a previous menu set.
     *
     * @return If there is a previous menu.
     */
    public boolean hasPreviousMenu() {
        return previousMenu != null;
    }

    /**
     * Gets the maximum slots in the menu.
     *
     * @return The total slots.
     */
    public int getTotalSlots() {
        return rows * COLUMNS;
    }

    /**
     * Converts x and y coordinates from the
     * top left of the menu into an index.
     *
     * @param x The x coordinate.
     * @param y The y coordinate
     * @return The index of the coordinates.
     */
    public int getIndex(int x, int y) {
        return y * COLUMNS + x;
    }

    /**
     * Converts an index into x and y
     * coordinates from the top left of the menu.
     *
     * @param index The index of the slot.
     * @return The x and y coordinates.
     */
    public Position getPosition(int index) {
        return new Position(index % COLUMNS, index / COLUMNS);
    }
}
