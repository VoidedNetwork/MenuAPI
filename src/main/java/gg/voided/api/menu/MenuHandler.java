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

    protected void runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
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
