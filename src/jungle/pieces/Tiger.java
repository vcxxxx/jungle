package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Tiger piece in Jungle, inherited from Piece.
 * Has the unique ability to leap horizontally across water.
 *
 * @author 240027249
 */
public class Tiger extends Piece {

    /**
     * Initializes a Tiger piece with a specified owner and starting square.
     *
     * @param owner  The player who owns the tiger.
     * @param square The square where the tiger is placed.
     */
    public Tiger(Player owner, Square square) {
        super(owner, square, 6); // Tiger has rank 6
    }

    /**
     * Indicates that the tiger can leap horizontally.
     *
     * @return {@code true} as tigers can leap horizontally.
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }
}
