package net.j4c0b3y.api.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The possible sizes for a bukkit inventory.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 12/05/2024
 */
@Getter
@RequiredArgsConstructor
public enum MenuSize {
    /**
     * A menu with three rows.
     */
    THREE(3),

    /**
     * A menu with four rows.
     */
    FOUR(4),

    /**
     * A menu with five rows.
     */
    FIVE(5),

    /**
     * A menu with six rows.
     */
    SIX(6);

    /**
     * How many rows the menu has.
     */
    private final int rows;
}
