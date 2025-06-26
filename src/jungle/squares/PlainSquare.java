package jungle.squares;

import jungle.Player;

/**
 * Represents a plain (regular) square on the Jungle game board.
 *
 * @author 240027249
 */
public class PlainSquare extends Square {

    /**
     * Creates a plain square owned by the specified player.
     *
     * @param owner The player who owns this square.
     */
    public PlainSquare(Player owner) {
        super(owner);
    }

    /**
     * Creates an unowned plain square.
     */
    public PlainSquare() {
        super();
    }

    /**
     * Indicates that this square is not water.
     *
     * @return {@code false} as plain squares are not water.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Indicates that this square is not a den.
     *
     * @return {@code false} as plain squares are not dens.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Indicates that this square is not a trap.
     *
     * @return {@code false} as plain squares are not traps.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}

