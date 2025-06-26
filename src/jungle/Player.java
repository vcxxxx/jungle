package jungle;

import java.util.logging.Logger;

/**
 * Represents a player in the Jungle game with attributes for name, player number,
 * captured den status, and piece count.
 *
 * @author 240027249
 */
public class Player {
    private static final Logger LOGGER = LoggerSetup.getLogger();
    private final String name;
    private final int playerNumber;
    private boolean hasCapturedDen;
    private int piecesCount;

    /**
     * Initializes a player with a name and player number.
     *
     * @param name         The player's name.
     * @param playerNumber The player's identifier number i.e. 0 or 1
     */
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.hasCapturedDen = false;
        this.piecesCount = 0;
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's number.
     *
     * @return The player's number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Marks the opponent's den as captured by this player.
     */
    public void captureDen() {
        LOGGER.info(name + " has captured the opponent's den!");
        this.hasCapturedDen = true;
    }

    /**
     * Checks if the player has captured the opponent's den.
     *
     * @return {@code true} if the den is captured; {@code false} otherwise.
     */
    public boolean hasCapturedDen() {
        return hasCapturedDen;
    }

    /**
     * Checks if the player has any pieces left.
     *
     * @return {@code true} if the player has pieces; {@code false} otherwise.
     */
    public boolean hasPieces() {
        return piecesCount > 0;
    }

    /**
     * Increases the player's number of pieces by one.
     */
    public void gainOnePiece() {
        piecesCount++;
    }

    /**
     * Decreases the player's piece count by one if they have pieces remaining.
     */
    public void loseOnePiece() {
        if (piecesCount > 0) {
            LOGGER.info(name + " has lost a piece!");
            piecesCount--;
        }
    }
}
