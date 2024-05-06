package gg.voided.api.menu;

import gg.voided.api.menu.listener.InventoryListener;
import gg.voided.api.menu.listener.ConnectionListener;
import gg.voided.api.menu.task.MenuAutoUpdateTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores open menus, menu options, registers listeners
 * and starts tasks for the menus to function.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@Getter @Setter
public class MenuHandler {
    @Getter private static MenuHandler instance;

    /**
     * The plugin to register listeners with.
     */
    private final JavaPlugin plugin;

    /**
     * Stores players that have open menus.
     */
    private final Map<Player, Menu> openedMenus = new ConcurrentHashMap<>();


    /**
     * Weather the menu should close when the back method
     * is called if there is no previous menu.
     */
    private boolean closeOnBack = false;

    /**
     * A tick timer used internally for auto updating,
     * this should not be modified.
     */
    private long ticks = 0;

    /**
     * Creates a new menu handler,
     * registering listeners and tasks.
     *
     * @param plugin The plugin to register listeners with.
     */
    public MenuHandler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;

        registerListeners();
        startTasks();
    }

    /**
     * Runs a task on the main thread for bukkit.
     *
     * @param task The task to run.
     */
    protected void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
    }

    private void startTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new MenuAutoUpdateTask(this), 0, 1);
    }
}
