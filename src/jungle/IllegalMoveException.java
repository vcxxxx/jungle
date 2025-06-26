package jungle;

/**
 * Exception thrown when an illegal move is attempted in the Jungle game.
 *
 * @author 240027249
 */
public class IllegalMoveException extends RuntimeException {
    /**
     * Constructs an IllegalMoveException with a specified detail message.
     *
     * @param message The detailed error message explaining the reason for the exception.
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
