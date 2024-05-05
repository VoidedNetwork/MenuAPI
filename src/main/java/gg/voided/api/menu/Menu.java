package gg.voided.api.menu;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.buttons.PaginationButton;
import gg.voided.api.menu.utils.Color;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


/**
 * A wrapper around a bukkit inventory with extra functionality
 * which stores items as buttons and keeps track of previous menus.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@Getter
public abstract class Menu {
    /**
     * The in-game chest menu width,
     * which should not be changed.
     */
    public static final int WIDTH = 9;

    /**
     * The menus title, this will show
     * up inside the inventory.
     */
    private final String title;

    /**
     * The menu size in rows.
     */
    private final int rows;

    /**
     * The player associated with the menu.
     */
    private final Player player;

    /**
     * The underlying bukkit inventory that the player sees.
     */
    private final Inventory inventory;

    /**
     * The previous menu, if present.
     */
    @Setter private Menu previousMenu;

    /**
     * The menu's buttons shown inside the inventory.
     */
    private final Map<Integer, Button> buttons = new HashMap<>();

    /**
     * Acts as a queue for buttons which get
     * pushed to the menu when update is called.
     */
    private final Map<Integer, Button> buttonCache = new HashMap<>();

    /**
     * The menu's handler.
     */
    private final MenuHandler handler;

    /**
     * The last tick time the menu was updated.
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
     * @param size The menu size, amount of rows.
     * @param player The player to open the menu with.
     */
    public Menu(String title, MenuSize size, Player player) {
        MenuHandler handler = MenuHandler.getInstance();
        if (handler == null) throw new IllegalStateException("Please initialize the MenuHandler before creating a menu.");
        this.handler = handler;

        this.title = title;
        this.rows = size.getRows();
        this.player = player;

        this.inventory = Bukkit.createInventory(
            player,
            getMaxSlots(),
            Color.translate(title)
        );
    }

    /**
     * Called when the menu is created,
     * this always runs on the server thread.
     */
    public void setupButtons() { }

    /**
     * Called when the inventory opens,
     * this always runs on the server thread.
     */
    public void onOpen() { }

    /**
     * Called when the inventory closes,
     * this always runs on the server thread.
     */
    public void onClose() { }

    /**
     * Opens the menu, setting up the buttons if they haven't been set up.
     */
    public void open() {
        if (hasPreviousMenu()) previousMenu.close();

        if (!setup) {
            setupButtons();
            setup = true;
        }

        update(() -> {
            handler.runSync(() -> {
                player.openInventory(inventory);
                onOpen();
            });

            handler.getOpenedMenus().put(player, this);
        });
    }

    /**
     * Updates the underlying inventory with the button's item stacks,
     * also updating the indexes of pagination buttons if present.
     */
    public void update(Runnable callback) {
        handler.runAsync(() -> {
            lastUpdate = handler.getTicks();

            buttons.clear();
            buttons.putAll(buttonCache);

            ItemStack[] items = new ItemStack[getMaxSlots()];

            int paginationButtons = 0;

            for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
                Button button = entry.getValue();

                if (button instanceof PaginationButton) {
                    ((PaginationButton) button).setIndex(++paginationButtons);
                }

                items[entry.getKey()] = button.getItem(player);
            }

            inventory.setContents(items);
            handler.runSync(player::updateInventory);

            if (callback != null) callback.run();
        });
    }

    /**
     * Updates the underlying inventory with the button's item stacks,
     * also updating the indexes of pagination buttons if present.
     */
    public void update() {
        update(null);
    }

    /**
     * Returns to the previous menu. If enabled in the handler,
     * the menu will just close if there is no previous menu.
     */
    public void back() {
        if (isClosed()) return;

        if (previousMenu == null) {
            if (handler.isCloseOnBack()) close();
            return;
        }

        previousMenu.open();
    }

    /**
     * Closes the inventory for the player.
     */
    public void close() {
        if (isClosed()) return;

        handler.getOpenedMenus().remove(player);

        handler.runSync(() -> {
            player.closeInventory();
            handler.runSync(this::onClose);
        });
    }

    /**
     * Checks if the players open inventory
     * does not match the current menu.
     *
     * @return If the menu is closed.
     */
    public boolean isClosed() {
         return !handler.getOpenedMenus().get(player).equals(this);
    }

    /**
     * Adds a button to a menu's next available slot.
     *
     * @param button The button to add.
     */
    public void add(Button button) {
        for (int i = 0; i < getMaxSlots(); i++) {
            if (buttonCache.get(i) != null) continue;

            set(i, button);
            return;
        }
    }

    /**
     * Sets a button using a slot's index.
     *
     * @param index The slot's index.
     * @param button The button to set.
     */
    public void set(int index, Button button) {
        buttonCache.put(index, button);
    }

    /**
     * Sets a button using a slot's coordinates.
     *
     * @param x The slot's x coordinate.
     * @param y The slot's y coordinate.
     * @param button The button to set.
     */
    public void set(int x, int y, Button button) {
        set(getIndex(x, y), button);
    }

    /**
     * Removes a button using a slot's index.
     *
     * @param index The slot's index.
     */
    public void remove(int index) {
        buttons.remove(index);
    }

    /**
     * Removes a button using a slot's coordinates.
     *
     * @param x The slot's x coordinate.
     * @param y The slot's y coordinate.
     */
    public void remove(int x, int y) {
        remove(getIndex(x, y));
    }

    /**
     * Calculates the max button slots by
     * multiplying the menu width by the row count.
     *
     * @return The max buttons slots in the menu.
     */
    public int getMaxSlots() {
        return rows * WIDTH;
    }

    /**
     * Converts a slot's coordinates to its index.
     *
     * @param x The slot's x coordinate.
     * @param y The slot's y coordinate.
     * @return The slot's index.
     */
    private int getIndex(int x, int y) {
        return y * WIDTH + x;
    }

    /**
     * Handles a click event for the menu,
     * directing it to the respective button.
     *
     * @param event The click event.
     */
    public void handleClick(InventoryClickEvent event) {
        Button button = buttons.get(event.getSlot());
        if (button == null) return;

        handler.runSync(() -> button.onClick(event.getClick()));
    }

    /**
     * Clears all buttons from the menu.
     */
    public void clear() {
        buttonCache.clear();
    }

    /**
     * Fills the menu with a button.
     *
     * @param button The button.
     */
    public void fill(Button button) {
        for (int i = 0; i < getMaxSlots(); i++) {
            set(i, button);
        }
    }

    /**
     * Fills a row with a button.
     *
     * @param row The row to fill.
     * @param button The button.
     */
    public void fillRow(int row, Button button) {
        for (int i = 0; i < WIDTH; i++) {
            set(i, row, button);
        }
    }

    /**
     * Fills a column with a button.
     *
     * @param column The column to fill.
     * @param button The button.
     */
    public void fillColumn(int column, Button button) {
        for (int i = 0; i < rows; i++) {
            set(column, i, button);
        }
    }

    /**
     * Fills the border of a menu with a button.
     *
     * @param button The button.
     */
    public void fillBorder(Button button) {
        fillRow(0, button);
        if (rows < 2) return;

        fillRow(getRows() - 1, button);
        if (rows < 3) return;

        fillColumn(0, button);
        fillColumn(WIDTH - 1, button);
    }

    /**
     * Fills the center of a menu with a button,
     * this is similar to a fill but inset by one.
     *
     * @param button The button.
     */
    public void fillCenter(Button button) {
        if (rows < 3) return;

        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {
                set(x, y, button);
            }
        }
    }

    /**
     * Checks if the menu has a previous menu.
     *
     * @return If there is a previous menu.
     */
    public boolean hasPreviousMenu() {
        return previousMenu != null;
    }
}
