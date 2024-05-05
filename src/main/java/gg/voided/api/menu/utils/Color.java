package gg.voided.api.menu.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Color {
    public String translate(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }
}
