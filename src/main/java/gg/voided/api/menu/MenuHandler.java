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

@Getter @Setter
public class MenuHandler {
    @Getter private static MenuHandler instance;
    private final Map<Player, Menu> openedMenus = new ConcurrentHashMap<>();
    private final JavaPlugin plugin;

    private boolean closeOnBack = false;
    private boolean uniquePaginationEntries = false;

    private long ticks = 0;

    public MenuHandler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;

        registerListeners();
        startTasks();
    }

    protected void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

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

    protected void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }
}
