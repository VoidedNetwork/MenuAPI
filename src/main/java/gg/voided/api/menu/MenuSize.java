package gg.voided.api.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The possible menu sizes for a chest inventory.
 *
 * @author J4C0B3Y
 * @version MenuAPI
 * @since 5/05/2024
 */
@Getter
@RequiredArgsConstructor
public enum MenuSize {
    /**
     * A menu with one row.
     */
    ONE(1),

    /**
     * A menu with two rows.
     */
    TWO(2),

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

    private final int rows;
}
