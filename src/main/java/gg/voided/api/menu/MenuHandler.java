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

@Getter
public class MenuHandler {
    @Getter private static MenuHandler instance;

    private final JavaPlugin plugin;
    private final Map<Player, Menu> openMenus = new ConcurrentHashMap<>();
    private final AutoUpdateTask autoUpdateTask;

    @Setter private boolean resetCursor = false;
    @Setter private boolean closeOnBack = false;

    public MenuHandler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;

        autoUpdateTask = new AutoUpdateTask(this);
        Bukkit.getScheduler().runTaskTimer(plugin, autoUpdateTask, 0, 1);

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
    }

    public void ifOpen(Player player, Consumer<Menu> callback) {
        Menu menu = openMenus.get(player);
        if (menu != null) callback.accept(menu);
    }

    public void warning(String message) {
        plugin.getLogger().warning("[MenuAPI] " + message);
    }

    protected void runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            runAsync(task, false);
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    protected void runAsync(Runnable task, boolean async) {
        if (async) {
            runTask(task, true);
        } else {
            task.run();
        }
    }

    protected void runTask(Runnable task, boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        } else {
            runSync(task);
        }
    }
}
