package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Rat piece in Jungle, inherited from Piece class.
 * Has the unique abilities to swim and defeat elephants.
 *
 * @author 240027249
 */
public class Rat extends Piece {

    /**
     * Initializes a Rat piece with a specified owner and starting square.
     *
     * @param owner  The player who owns the rat.
     * @param square The square where the rat is placed.
     */
    public Rat(Player owner, Square square) {
        super(owner, square, 1);
    }

    /**
     * Indicates that the rat can swim.
     *
     * @return {@code true} as rats can swim.
     */
    @Override
    public boolean canSwim() {
        return true;
    }

    /**
     * Determines if the rat can defeat a target piece.
     * Rats can defeat elephants (rank 8),
     * even though their rank is lower.
     *
     * @param target The piece the rat is attempting to defeat.
     * @return {@code true} if the target is an elephant
     * or if the rat's rank is equal or higher.
     */
    @Override
    public boolean canDefeat(Piece target) {
        if (target.getStrength() == 8) {
            return true;
        }
        return super.canDefeat(target);
    }
}
