package jungle.squares;

import jungle.Player;

/**
 * Represents a den square on the Jungle game board,
 * owned by a specific player.
 *
 * @author 240027249
 */

public class Den extends Square {

    /**
     * Creates a den square owned by the specified player.
     *
     * @param owner The player who owns this den.
     */
    public Den(Player owner) {
        super(owner);
    }

    /**
     * Indicates that this square is not a water square.
     *
     * @return {@code false} as dens are not water.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Indicates that this square is a den.
     *
     * @return {@code true} as this is a den square.
     */
    @Override
    public boolean isDen() {
        return true;
    }

    /**
     * Indicates that this square is not a trap.
     *
     * @return {@code false} as dens are not traps.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}
