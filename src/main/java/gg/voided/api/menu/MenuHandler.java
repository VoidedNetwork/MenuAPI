package gg.voided.api.menu;


import gg.voided.api.menu.listener.ConnectionListener;
import gg.voided.api.menu.listener.InventoryListener;
import gg.voided.api.menu.task.AutoUpdateTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Stores open menus, menu options, starts tasks
 * and registers listeners for menus to function.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@Getter
public class MenuHandler {
    /**
     * The menu handler instance.
     */
    @Getter private static MenuHandler instance;

    /**
     * The plugin to register listeners and start tasks with.
     */
    private final JavaPlugin plugin;

    /**
     * Stores menus that players have open.
     */
    private final Map<Player, Menu> openMenus = new ConcurrentHashMap<>();

    /**
     * Handles auto updating menus.
     */
    private final AutoUpdateTask autoUpdateTask;

    /**
     * If the cursor position should
     * reset when switching between menus.
     */
    @Setter private boolean resetCursor = false;

    /**
     * If the menu should close when the back
     * method is called and there is no previous menu.
     */
    @Setter private boolean closeOnBack = false;

    /**
     * Creates a new menu handler, starting the autoupdating
     * task and registering inventory and connection listeners.
     *
     * @param plugin The plugin to register listeners and start tasks with.
     */
    public MenuHandler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;

        autoUpdateTask = new AutoUpdateTask(this);
        Bukkit.getScheduler().runTaskTimer(plugin, autoUpdateTask, 0, 1);

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
    }

    /**
     * Executes a callback if the
     * specified player has an open menu.
     *
     * @param player The player to check.
     * @param callback The callback to execute.
     */
    public void ifOpen(Player player, Consumer<Menu> callback) {
        Menu menu = openMenus.get(player);
        if (menu != null) callback.accept(menu);
    }

    /**
     * Displays a warning message that comes
     * from the plugin associated with the handler.
     *
     * @param message The warning message.
     */
    public void warning(String message) {
        plugin.getLogger().warning("[MenuAPI] " + message);
    }

    /**
     * Runs a task on the server thread.
     *
     * @param task The task to run.
     */
    protected void runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            runAsync(task, false);
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    /**
     * Runs a task asynchronously.
     *
     * @param task The task to run.
     * @param async If the task should be run asynchronously.
     */
    protected void runAsync(Runnable task, boolean async) {
        if (async) {
            runTask(task, true);
        } else {
            task.run();
        }
    }

    /**
     * Runs a task using the bukkit scheduler.
     *
     * @param task The task to run.
     * @param async If the task should be run asynchronously.
     */
    protected void runTask(Runnable task, boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        } else {
            runSync(task);
        }
    }
}
