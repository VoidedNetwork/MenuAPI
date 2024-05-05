package gg.voided.api.menu.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

/**
 * Color utilities.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@UtilityClass
public class Color {
    /**
     * Translates bukkit color codes for a string.
     *
     * @param content The content to translate.
     * @return The translated content.
     */
    public String translate(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }
}
