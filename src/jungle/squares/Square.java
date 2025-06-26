package jungle.squares;

import jungle.Player;
import jungle.pieces.Piece;

/**
 * Represents an abstract square on the Jungle game board,
 * which may be owned by a player and occupied by a piece.
 * Subclasses define specific square types such as water, den, or trap.
 *
 * @author 240027249
 */

public abstract class Square {
    protected Player owner;
    protected Piece piece; //Piece is added to square's attributes for game movements.

    /**
     * Creates an unowned square.
     */
    public Square() {
        this.owner = null;
    }

    /**
     * Creates a square owned by the specified player.
     *
     * @param owner The player who owns this square.
     */
    public Square(Player owner) {
        this.owner = owner;
    }

    /**
     * Checks if the square is owned by a specific player.
     *
     * @param player The player to check.
     * @return {@code true} if the square is owned by the player; {@code false} otherwise.
     */
    public boolean isOwnedBy(Player player) {
        return owner != null && owner.equals(player);
    }

    /**
     * Places a piece on this square and assigns ownership to the piece's owner.
     *
     * @param piece The piece to place on the square.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        this.owner = piece.getOwner(); //VERIFY
    }

    /**
     * Checks if the square is a water square.
     *
     * @return {@code true} if it is a water square; {@code false} otherwise.
     */
    public abstract boolean isWater();

    /**
     * Checks if the square is a den square.
     *
     * @return {@code true} if it is a den square; {@code false} otherwise.
     */
    public abstract boolean isDen();

    /**
     * Checks if the square is a trap square.
     *
     * @return {@code true} if it is a trap square; {@code false} otherwise.
     */
    public abstract boolean isTrap();

    /**
     * Gets the piece currently occupying this square.
     *
     * @return The piece on this square, or {@code null} if empty.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Gets the owner of this square.
     *
     * @return The player who owns this square, or {@code null} if unowned.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Clears the square of any piece and resets ownership.
     */
    public void clearSquare() {
        this.piece = null;
        this.owner = null;
    }
}
