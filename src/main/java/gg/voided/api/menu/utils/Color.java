package gg.voided.api.menu.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Color {
    public static final String RESET = ChatColor.RESET.toString();

    public String translate(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public List<String> translate(List<String> lines) {
        List<String> translated = new ArrayList<>();

        for (String line : lines) {
            translated.add(translate(line));
        }

        return translated;
    }
}
