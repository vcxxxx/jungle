package jungle.pieces;

import jungle.LoggerSetup;
import jungle.Player;
import jungle.squares.Square;

import java.util.logging.Logger;

/**
 * Represents a game piece in Jungle, with attributes for owner, rank, current square,
 * and captured status. Provides default behaviors
 * for movement and interaction with other pieces.
 *
 * @author 240027249
 */

public class Piece {
    protected Player owner;
    protected Square square;
    protected int rank;
    protected boolean captured;
    private static final Logger LOGGER = LoggerSetup.getLogger();

    /**
     * Initializes a piece with a given owner, position, and rank.
     *
     * @param owner  The player who owns the piece.
     * @param square The square where the piece starts.
     * @param rank   The rank or strength of the piece.
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        if (square.isTrap()) {
            this.rank = 0;
        } else {
            this.rank = rank;
        }
        this.captured = false;
        this.owner.gainOnePiece();
    }

    /**
     * Checks if the piece is owned by a specific player.
     *
     * @param player The player to check.
     * @return {@code true} if the piece is owned by the player; {@code false} otherwise.
     */
    public boolean isOwnedBy(Player player) {
        return owner.equals(player);
    }

    /**
     * Gets the rank i.e. strength of the piece.
     *
     * @return The piece's rank.
     */
    public int getStrength() {
        return rank;
    }

    /**
     * Checks if the piece can swim. Default is {@code false}.
     *
     * @return {@code true} if the piece can swim; {@code false} otherwise.
     */
    public boolean canSwim() {
        return false;
    }

    /**
     * Checks if the piece can leap horizontally. Default is {@code false}.
     *
     * @return {@code true} if the piece can leap horizontally; {@code false} otherwise.
     */
    public boolean canLeapHorizontally() {
        return false;
    }

    /**
     * Checks if the piece can leap vertically. Default is {@code false}.
     *
     * @return {@code true} if the piece can leap vertically; {@code false} otherwise.
     */
    public boolean canLeapVertically() {
        return false;
    }

    /**
     * Moves the piece to a new square, handling interactions with the target square
     * (such as capturing an opponent’s piece, entering a trap, or capturing the den).
     *
     * @param toSquare The square to move to.
     */
    public void move(Square toSquare) {

        if (toSquare.isOwnedBy(this.owner)) {
            if (toSquare.isDen()) {
                LOGGER.warning("Cannot enter your own den!");
                return;
            }


        } else { //Square belongs to the enemy player
            if (toSquare.getPiece() != null && this.canDefeat(toSquare.getPiece())) {
                toSquare.getPiece().beCaptured();
                this.owner.gainOnePiece();
            } else if (toSquare.isDen()) {
                this.owner.captureDen();
            } else if (toSquare.isTrap()) {
                this.rank = 0;
            }


        }
        this.square.clearSquare(); //Clear the previous square.
        this.square = toSquare;
        this.square.setPiece(this);
        //Code structure allows the player to enter their own trap without affecting them.
    }

    /**
     * Checks if this piece can defeat another piece based on rank.
     *
     * @param target The piece to compare against.
     * @return {@code true} if this piece can defeat the target; {@code false} otherwise.
     */
    public boolean canDefeat(Piece target) {
        // By default, a piece can defeat another piece if it has an equal or higher rank
        return this.rank >= target.getStrength();
    }

    /**
     * Handles the capture of this piece by an opponent.
     */
    public void beCaptured() {
        this.captured = true;
        this.owner.loseOnePiece();
        this.square = null;
    }

    /**
     * Gets the player who owns this piece.
     *
     * @return The piece's owner.
     */
    public Player getOwner() {
        return this.owner;
    }
}
