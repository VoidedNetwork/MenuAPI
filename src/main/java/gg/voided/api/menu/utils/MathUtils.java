package gg.voided.api.menu.utils;

import lombok.experimental.UtilityClass;

/**
 * Math utilities.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@UtilityClass
public class MathUtils {

    /**
     * Clamps a value between a min and a max.
     *
     * @param value The value.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }
}
