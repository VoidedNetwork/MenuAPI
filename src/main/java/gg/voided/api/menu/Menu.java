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

@Getter
public abstract class Menu {
    private static final int WIDTH = 9;

    private final String title;
    private final int rows;

    private final Player player;
    private final Inventory inventory;
    @Setter private Menu previousMenu;

    private final Map<Integer, Button> buttons = new HashMap<>();
    private final Map<Integer, Button> buttonCache = new HashMap<>();
    private final MenuHandler handler = MenuHandler.getInstance();

    private long lastUpdate = 0;

    public Menu(String title, MenuSize size, Player player) {
        this.title = title;
        this.rows = size.getRows();
        this.player = player;

        this.inventory = Bukkit.createInventory(
            player,
            getMaxSlots(),
            Color.translate(title)
        );
    }

    public void onOpen() { }
    public void onClose() { }

    public void open() {
        handler.getOpenedMenus().put(player, this);
        update();

        handler.runSync(() -> {
            player.openInventory(inventory);
            onOpen();
        });
    }

    public void update() {
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
    }

    public void back() {
        if (previousMenu == null) {
            if (handler.isCloseOnBack()) close();
            return;
        }

        previousMenu.open();
    }

    public void close() {
        onClose();
    }

    public void add(Button button) {
        for (int i = 0; i < getMaxSlots(); i++) {
            if (buttons.get(i) != null) continue;

            set(i, button);
            return;
        }
    }

    public void set(int index, Button button) {
        buttonCache.put(index, button);
    }

    public void set(int x, int y, Button button) {
        set(getIndex(x, y), button);
    }

    public void remove(int index) {
        buttons.remove(index);
    }

    public void remove(int x, int y) {
        remove(getIndex(x, y));
    }

    public int getMaxSlots() {
        return rows * WIDTH;
    }

    private int getIndex(int x, int y) {
        return y * WIDTH + x;
    }

    public void handleClick(InventoryClickEvent event) {
        Button button = buttons.get(event.getSlot());
        if (button == null) return;

        handler.runSync(() -> button.onClick(event.getClick()));
    }

    public void clear() {
        buttonCache.clear();
    }

    public void fill(Button button) {
        for (int i = 0; i < getMaxSlots(); i++) {
            set(i, button);
        }
    }

    public void fillRow(int row, Button button) {
        for (int i = 0; i < WIDTH; i++) {
            set(i, row, button);
        }
    }

    public void fillColumn(int column, Button button) {
        for (int i = 0; i < rows; i++) {
            set(column, i, button);
        }
    }

    public void fillBorder(Button button) {
        fillRow(0, button);
        fillColumn(0, button);
        fillRow(getRows() - 1, button);
        fillColumn(WIDTH - 1, button);
    }

    public void fillCenter(Button button) {
        if (rows < 3) return;

        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {
                set(x, y, button);
            }
        }
    }
}
