package jungle.squares;

/**
 * Represents a water square on the Jungle game board.
 *
 * @author 240027249
 */
public class WaterSquare extends Square {

    /**
     * Creates an unowned water square.
     */
    public WaterSquare() {
        super();
    }

    /**
     * Indicates that this square is water.
     *
     * @return {@code true} as this is a water square.
     */
    @Override
    public boolean isWater() {
        return true;
    }

    /**
     * Indicates that this square is not a den.
     *
     * @return {@code false} as water squares are not dens.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Indicates that this square is not a trap.
     *
     * @return {@code false} as water squares are not traps.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}
