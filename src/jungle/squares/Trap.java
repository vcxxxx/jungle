package jungle.squares;

import jungle.Player;

/**
 * Represents a trap square on the Jungle game board,
 * owned by a specific player.
 *
 * @author 240027249
 */
public class Trap extends Square {

    /**
     * Creates a trap square owned by the specified player.
     *
     * @param owner The player who owns this trap.
     */
    public Trap(Player owner) {
        super(owner);
    }

    /**
     * Indicates that this square is not water.
     *
     * @return {@code false} as traps are not water.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Indicates that this square is not a den.
     *
     * @return {@code false} as traps are not dens.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Indicates that this square is a trap.
     *
     * @return {@code true} as this is a trap square.
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
