package gg.voided.api.menu;

import gg.voided.api.menu.button.Button;
import gg.voided.api.menu.button.ButtonClick;
import gg.voided.api.menu.layer.impl.BackgroundLayer;
import gg.voided.api.menu.layer.impl.ForegroundLayer;
import gg.voided.api.menu.template.Template;
import gg.voided.api.menu.utils.Color;
import gg.voided.api.menu.utils.Pair;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Menu {
    public static final int COLUMNS = 9;

    private final MenuHandler handler = MenuHandler.getInstance();
    private final Map<Integer, Button> buttons = new HashMap<>();

    private final Player player;
    private final Inventory inventory;
    private final String title;
    private final int rows;

    private final ForegroundLayer foreground;
    private final BackgroundLayer background;

    @Setter private Menu previousMenu;
    @Setter private boolean async = false;

    private long lastUpdate = 0;
    private boolean setup = false;

    public Menu(String title, MenuSize size, Player player) {
        this.player = player;
        this.rows = size.getRows();

        this.foreground = new ForegroundLayer(this);
        this.background = new BackgroundLayer(this);

        this.title = Color.translate(title);
        this.inventory = Bukkit.createInventory(player, getTotalSlots(), this.title);
    }

    public abstract void setup(BackgroundLayer background, ForegroundLayer foreground);
    public void onOpen() { }
    public void onClose() { }
    public void onClick(ButtonClick click) { }

    //TODO: Sync menu long duration warning (open, update, etc)
    public void open() {

    }

    public void close() {

    }

    protected void update(boolean async) {
        handler.runAsync(() -> {

        });
    }

    public void update() {
        update(is);
    }

    public void back() {
        if (previousMenu == null) {
            if (handler.isCloseOnBack()) close();
        } else {
            previousMenu.open();
        }
    }

    public void apply(Template template) {
        template.setup(background, foreground);
    }

    public void click(InventoryClickEvent event) {
        handler.runTask(() -> {
            Button button = buttons.get(event.getSlot());
            ButtonClick click = new ButtonClick(event, button, this);

            onClick(click);
            if (button == null || click.isIgnored()) return;

            button.onClick(click);
        }, async);
    }

    public int getTotalSlots() {
        return rows * COLUMNS;
    }

    public int getIndex(int x, int y) {
        return y * COLUMNS + x;
    }

    public Pair<Integer, Integer> getPosition(int index) {
        return new Pair<>(index % COLUMNS, index / COLUMNS);
    }
}
