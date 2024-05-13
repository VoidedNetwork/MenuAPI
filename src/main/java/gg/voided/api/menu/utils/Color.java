package gg.voided.api.menu.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for bukkit colors.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@UtilityClass
public class Color {
    /**
     * The reset color code (&r).
     */
    public static final String RESET = ChatColor.RESET.toString();

    /**
     * Translates the colors in text.
     *
     * @param content The text to translate.
     * @return The translated text.
     */
    public String translate(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    /**
     * Translates each line in a string list.
     *
     * @param lines The string list to translate.
     * @return The translated string list.
     */
    public List<String> translate(List<String> lines) {
        List<String> translated = new ArrayList<>();

        for (String line : lines) {
            translated.add(translate(line));
        }

        return translated;
    }
}
