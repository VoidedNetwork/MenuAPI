package gg.voided.api.menus.layer;

import gg.voided.api.menus.Menu;
import gg.voided.api.menus.button.Button;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public final class Layer {
    private final Menu menu;
    @Getter private final Map<Integer, Button> buttons = new HashMap<>();

    public void set(int index, Button button) {
        if (button == null)  {
            String layer = menu.getForeground().equals(this) ? "FOREGROUND" : "BACKGROUND";

            menu.getHandler().getPlugin().getLogger().warning(
                "[MenuAPI] Menu '" + menu.getClass().getSimpleName() + "' " +
                    "has null button in layer '" + layer + "' at index '" + index + "'."
            );

            return;
        }

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

    public void remove(int index) {
        buttons.remove(index);
    }

    public void remove(int x, int y) {
        remove(menu.getIndex(x, y));
    }

    public void clear() {
        buttons.clear();
    }

    public void setRow(int row, Button button) {
        for (int i = 0; i < Menu.COLUMNS; i++) {
            set(i, row, button);
        }
    }

    public void setColumn(int column, Button button) {
        for (int i = 0; i < menu.getRows(); i++) {
            set(column, i, button);
        }
    }

    public void setBorder(Button button) {
        setRow(0, button);
        if (menu.getRows() < 2) return;

        setRow(menu.getRows() - 1, button);
        if (menu.getRows() < 3) return;

        setColumn(0, button);
        setColumn(Menu.COLUMNS - 1, button);
    }

    public void fill(Button button, int inset) {
        if (menu.getRows() < inset * 2 + 1) return;

        System.out.println("fat");

        for (int x = inset; x < Menu.COLUMNS - inset; x++) {
            for (int y = inset; y < menu.getRows() - inset; y++) {
                set(x, y, button);
            }
        }
    }

    public void fill(Button button) {
        fill(button, 0);
    }
}
