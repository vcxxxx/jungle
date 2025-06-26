package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Lion piece in Jungle, inherited from Piece.
 * Has the unique ability to leap horizontally and vertically.
 *
 * @author 240027249
 */
public class Lion extends Piece {

    /**
     * Initializes a Lion piece with a specified owner and starting square.
     *
     * @param owner  The player who owns the lion.
     * @param square The square where the lion is placed.
     */
    public Lion(Player owner, Square square) {
        super(owner, square, 7);
    }


    /**
     * Indicates that the lion can leap vertically.
     *
     * @return {@code true} as lions can leap vertically.
     */
    @Override
    public boolean canLeapVertically() {
        return true;
    }

    /**
     * Indicates that the lion can leap horizontally.
     *
     * @return {@code true} as lions can leap horizontally.
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }
}
