package gg.voided.api.menu;

import gg.voided.api.menu.listener.ClickListener;
import gg.voided.api.menu.listener.OpenListener;
import gg.voided.api.menu.listener.QuitListener;
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
     * Weather the pagination ignore duplicate button entries.
     */
    private boolean uniquePaginationEntries = false;

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

    /**
     * Runs a task asynchronously with bukkit.
     *
     * @param task The task to run.
     */
    protected void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new OpenListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new QuitListener(this), plugin);
    }

    private void startTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new MenuAutoUpdateTask(this), 0, 1);
    }
}
