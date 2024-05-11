package gg.voided.api.menu.layer;

import gg.voided.api.menu.Menu;
import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.pagination.PaginationButton;
import gg.voided.api.menu.utils.Pair;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class Layer {
    private final Menu menu;
    private final Map<Integer, Button> buttons = new HashMap<>();

    public abstract String getName();

    public void set(int index, Button button) {
        if (isInvalid(button, index)) return;
        buttons.put(index, button);
    }

    public void set(int x, int y, Button button) {
        set(menu.getIndex(x, y), button);
    }

    public void add(Button button) {
        for (int i = 0; i < menu.getTotalSlots(); i++) {
            if (buttons.get(i) != null) continue;

            set(i, button);
            return;
        }
    }

    public void fill(Button button) {
        for (int i = 0; i < menu.getTotalSlots(); i++) {
            set(i, button);
        }
    }

    public void fill(int ax, int ay, int bx, int by, Button button) {
        Pair<Integer, Integer> a = new Pair<>(Math.min(ax, bx), Math.min(ay, by));
        Pair<Integer, Integer> b = new Pair<>(Math.max(ax, bx), Math.max(ay, by));

        for (int x = a.getX(); x <= b.getX(); x++) {
            for (int y = a.getY(); y <= b.getY(); y++) {
                set(x, y, button);
            }
        }
    }

    public void fill(int first, int second, Button button) {
        Pair<Integer, Integer> a = menu.getPosition(first);
        Pair<Integer, Integer> b = menu.getPosition(second);
        fill(a.getX(), a.getY(), b.getX(), b.getY(), button);
    }

    public void row(int row, Button button) {
        for (int i = 0; i < Menu.COLUMNS; i++) {
            set(i, row, button);
        }
    }

    public void column(int column, Button button) {
        for (int i = 0; i < menu.getRows(); i++) {
            set(column, i, button);
        }
    }

    public void border(Button button) {
        row(0, button);
        if (menu.getRows() < 2) return;

        row(menu.getRows() - 1, button);
        if (menu.getRows() < 3) return;

        column(0, button);
        column(Menu.COLUMNS - 1, button);
    }

    public void center(Button button, int inset) {
        if (menu.getRows() < inset * 2 + 1) return;

        for (int x = inset; x < Menu.COLUMNS - inset; x++) {
            for (int y = inset; y < menu.getRows() - inset; y++) {
                set(x, y, button);
            }
        }
    }

    public void center(Button button) {
        center(button, 1);
    }

    public void remove(int index) {
        buttons.remove(index);
    }

    public void remove(int x, int y) {
        remove(menu.getIndex(x, y));
    }

    public void clear() {
        buttons.clear();
    }

    protected boolean isInvalid(Button button, int index) {
        if (button == null) {
            warning("null", null, index);
            return true;
        }

        if (index < 0 || index >= menu.getTotalSlots()) {
            warning("out of bounds", button, index);
            return true;
        }

        return false;
    }

    protected void warning(String reason, Button button, int slot) {
        menu.getHandler().warning(
            "Menu '" + menu.getClass().getSimpleName() +
            "' tried to add " + reason + " button '" +
            (button != null ? button.getClass().getSimpleName() : "unknown") +
            "' to layer '" + getName() + "' at index '" + slot +
            "' out of '" + menu.getTotalSlots() + "'."
        );
    }

    public Map<Integer, Button> getButtons(Map<Integer, Button> placed) {
        Map<Integer, Button> buttons = new HashMap<>();
        int paginationIndex = 0;

        for (Map.Entry<Integer, Button> entry : this.buttons.entrySet()) {
            int slot = entry.getKey();
            if (placed != null && placed.get(slot) != null) continue;

            Button button = entry.getValue();

            if (button instanceof PaginationButton) {
                ((PaginationButton) button).setIconIndex(++paginationIndex);
            }

            ItemStack icon = button.getIcon();
            if (icon != null) buttons.put(slot, button);
        }

        return buttons;
    }

    // TODO: maybe remove
    public static Map<Integer, Button> merge(Layer ...layers) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Layer layer : layers) {
            buttons.putAll(layer.getButtons(buttons));
        }

        return buttons;
    }
}