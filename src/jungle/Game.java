package jungle;

import jungle.pieces.Lion;
import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.pieces.Tiger;
import jungle.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import static jungle.GameHelper.initializeBoard;
import static jungle.GameHelper.isInLeapBoundaryVertically;
import static jungle.GameHelper.isInLeapBoundaryHorizontally;
import static jungle.GameHelper.minimum;
import static jungle.GameHelper.maximum;
import static jungle.GameHelper.isWithinBounds;


/**
 * Game class sets up and runs the board game Jungle.
 * It handles the higher level game logic.
 * It interfaces with various aspects of the game like
 * squares and pieces and uses GameHelper class for lower level logic
 * verification.
 *
 * @author 240027249
 */

public class Game {
    private static final Logger LOGGER = LoggerSetup.getLogger();
    /**
     * Height of the board.
     */
    public static final int HEIGHT = 9;
    /**
     * Width of the board.
     */
    public static final int WIDTH = 7;
    /**
     * Rows where WaterSquares are present.
     */
    public static final int[] WATER_ROWS = {3, 4, 5};
    /**
     * Columns where WaterSquares are present.
     */
    public static final int[] WATER_COLS = {1, 2, 4, 5};
    /**
     * The middle column 3 has the den
     * At rows 0 for p0 and 8 for p1.
     */
    public static final int DEN_COL = 3;
    /**
     * Possible horizontal leaps:
     * 3 columns to the right or left.
     */

    public static final int[] HOR_LEAP_OFFSETS = {3, -3};


    private final Square[][] board;
    private final Player[] players;
    private Player winner;
    private int numMoves;

    /**
     * Constructor for Game class. Initializes the board and players.
     *
     * @param p0 Player 0 with the first turn in Jungle.
     * @param p1 Player 1 with alternate turn in Jungle.
     */
    public Game(Player p0, Player p1) {
        this.players = new Player[]{p0, p1};
        this.board = new Square[HEIGHT][WIDTH];
        initializeBoard(p0, p1, board);
    }

    /**
     * Initializes the game board by placing the starting pieces for both
     * players. Each piece is added to its designated position based on the
     * game's initial configuration. This setup includes placing pieces for
     * both players (Player 0 and Player 1) with specific animals at their
     * respective board positions. The pieces are represented by integers
     * indicating their type:
     * - 8 - Elephant
     * - 7 - Lion
     * - 6 - Tiger
     * - 5 - Leopard
     * - 4 - Wolf
     * - 3 - Dog
     * - 2 - Cat
     * - 1 - Rat
     * All of these pieces are initialized using the {@code addPiece} method.
     */
    public void addStartingPieces() {
        addPiece(0, 0, 7, 0);  //Lion for Player 0
        addPiece(0, 6, 6, 0); // Tiger for Player 0
        addPiece(1, 1, 3, 0); // Dog for Player 0
        addPiece(1, 5, 2, 0); // Cat for Player 0
        addPiece(2, 0, 1, 0); // Rat for Player 0
        addPiece(2, 2, 5, 0); // Leopard for Player 0
        addPiece(2, 4, 4, 0); // Wolf for Player 0
        addPiece(2, 6, 8, 0); // Elephant for Player 0

        addPiece(8, 0, 6, 1); // Tiger for Player 1
        addPiece(8, 6, 7, 1); // Lion for Player 1
        addPiece(7, 1, 2, 1); // Cat for Player 1
        addPiece(7, 5, 3, 1); // Dog for Player 1
        addPiece(6, 0, 8, 1); // Elephant for Player 1
        addPiece(6, 2, 4, 1); // Wolf for Player 1
        addPiece(6, 4, 5, 1); // Leopard for Player 1
        addPiece(6, 6, 1, 1); // Rat for Player 1
        LOGGER.info("Pieces have been added to the board.");
    }

    /**
     * Adds a piece to the board at the specified position for a given player.
     * This method creates a piece based on its rank and assigns it to the
     * designated square on the board. Special ranks, such as Rat, Tiger, and
     * Lion, create specific subclasses, while other ranks create a generic
     * piece with the specified rank.
     *
     * @param row          The row position on the board where the piece will be
     *                     placed.
     * @param col          The column position on the board where the piece will
     *                     be placed.
     * @param rank         The rank of the piece to be added. Specific ranks
     *                     correspond to special pieces:
     *                     - 1: Rat
     *                     - 6: Tiger
     *                     - 7: Lion
     *                     All other ranks create a generic Piece.
     * @param playerNumber The player number to whom the piece belongs (e.g., 0
     *                     or 1). This method logs the creation of each piece
     *                     with its rank and player number and assigns it to the
     *                     target square on the board.
     */
    public void addPiece(int row, int col, int rank, int playerNumber) {
        Player player = getPlayer(playerNumber);
        Square targetSquare = board[row][col];
        Piece piece = switch (rank) {
            case 1 -> new Rat(player, targetSquare);
            case 6 -> new Tiger(player, targetSquare);
            case 7 -> new Lion(player, targetSquare);
            default -> new Piece(player, targetSquare, rank);
        };

        LOGGER.info("Created a piece with rank " + rank
                + " " + "for player " + playerNumber);


        targetSquare.setPiece(piece);
    }

    /**
     * getPlayer returns the player object corresponding to the integer player number.
     * It validates the player number first.
     *
     * @param playerNumber 0 or 1
     * @return {@code Player Object}
     */
    public Player getPlayer(int playerNumber) {
        if (playerNumber < 0 || playerNumber >= players.length) {
            LOGGER.severe("Invalid player number " + playerNumber);
            throw new IllegalArgumentException("Invalid player number " + playerNumber);
        }
        return players[playerNumber];
    }

    /**
     * Simply tells us whether the game is over or not.
     * Does so by checking whether the winner has been set or not.
     *
     * @return {@code true or false}
     */
    public boolean isGameOver() {
        return winner != null;
    }

    /**
     * Gets the winner attribute defined in the Game class. Has a special check
     * in case the winner isn't set yet and one of the players has no more
     * pieces left.
     *
     * @return {@code Player Object}
     */

    public Player getWinner() {
        if (winner == null) {
            if (players[0].hasPieces() ^ players[1].hasPieces()) {
                //Enters if block when only one of these statements is true
                winner = players[0].hasPieces() ? players[0] : players[1];
                //sets winner to whoever still has pieces on the board
            }
        }
        return winner;
    }

    /**
     * Takes board coordinates as input and returns the square object associated to it.
     *
     * @param row Row of the square on the board
     * @param col Column of the square on the board
     * @return {@code Square Object} associated with board[row][column]
     */
    public Square getSquare(int row, int col) {
        if (!isWithinBounds(row, col)) {
            LOGGER.severe("Index " + row + " " + col + "is out of bounds.");
            throw new IndexOutOfBoundsException("Invalid square position");

        }
        return board[row][col];
    }

    /**
     * Calculates and returns a list of legal moves for the piece located at the
     * specified board coordinates. This method checks if there is a piece at
     * the given position, whether it belongs to the player whose turn it is,
     * and if the game is still active before calculating legal moves.
     * <p>
     * The method generates legal moves in four main directions (up, down, left,
     * right) based on the piece's position. Additionally, it considers any
     * special movement abilities the piece might have, such as leaping over
     * certain obstacles on the board.
     *
     * @param row The row of the square containing the piece whose moves are to
     *            be calculated.
     * @param col The column of the square containing the piece whose moves are
     *            to be calculated.
     * @return A list of coordinates representing all legal moves for the piece
     * at the specified location. If there is no piece, if it is not the
     * player's turn, or if the game is over, an empty list is
     * returned.
     * <p>
     * This method also logs a warning if no moves are available and logs
     * information about the piece and its initial position when generating
     * legal moves.
     */
    public List<Coordinate> getLegalMoves(int row, int col) {
        List<Coordinate> legalMoves = new ArrayList<>();

        Square currentSquare = getSquare(row, col);

        // Exit early if no piece on square, not correct turn, or game is over
        if (currentSquare.getPiece() == null
                || !currentSquare.isOwnedBy(whoseTurn())
                || isGameOver()) {
            LOGGER.warning(
                    "0 legal moves to make. "
                            + "Could be because no piece is available at square, "
                            + "not the right turn, "
                            + "or the game is over.");
            return legalMoves;
        }
        Piece pieceOnCurrentSquare = currentSquare.getPiece();


        LOGGER.info("Creating list of legal moves for " + pieceOnCurrentSquare
                + " with coordinates [" + row + ", " + col + "].");

        // Define possible move directions (up, down, left, right)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            addLegalMoveToList(
                    row + direction[0], col + direction[1], currentSquare,
                    pieceOnCurrentSquare, legalMoves
            );
        }

        // Handle leaps based on piece abilities
        addVerticalLeaps(row, col, legalMoves, pieceOnCurrentSquare);
        addHorizontalLeaps(row, col, legalMoves, pieceOnCurrentSquare);

        return legalMoves;
    }

    /**
     * Checks if a move to the specified coordinates is legal for the given
     * piece and, if so, adds the coordinates to the list of legal moves. This
     * method verifies that the target square is within board boundaries and
     * meets the game's criteria for a legal move.
     *
     * @param newRow               The row index of the prospective move.
     * @param newCol               The column index of the prospective move.
     * @param currentSquare        The current square where the piece is located.
     * @param pieceOnCurrentSquare The piece for which legal moves are being
     *                             calculated.
     * @param moves                The list of legal move coordinates to which a
     *                             new move will be added if valid.
     *                             <p>
     *                             The method logs information when a legal move
     *                             is found and adds the move's coordinates to
     *                             the list. If the move is out of bounds or not
     *                             legal, no action is taken.
     */
    private void addLegalMoveToList(
            int newRow, int newCol, Square currentSquare, Piece pieceOnCurrentSquare,
            List<Coordinate> moves) {
        if (isWithinBounds(newRow, newCol)) {
            Square newSquare = getSquare(newRow, newCol);
            if (isLegalMove(newSquare, currentSquare.getOwner(), pieceOnCurrentSquare)) {
                LOGGER.info("Legal move found at: " + newRow + ", " + newCol);
                moves.add(new Coordinate(newRow, newCol));
            }
        }
    }


    /**
     * Determines whether a move to the specified square is legal for a given
     * piece, based on game rules. This method checks several conditions to
     * validate the move, such as whether the target square is a den, whether it
     * is occupied by another piece owned by the same player, and whether the
     * square is a water square.
     *
     * @param square The target square being evaluated for a legal move.
     * @param owner  The player who owns the piece attempting to move.
     * @param piece  The piece attempting to move to the target square.
     * @return {@code true} if the move to the specified square is legal;
     * {@code false} otherwise.
     * <p>
     * The move is considered illegal if:
     * - The square is the player's own den.
     * - The square is occupied by another piece owned by the same player.
     * - The square is a water square, unless the piece is a rat (strength == 1).
     */
    private boolean isLegalMove(Square square, Player owner, Piece piece) {

        // Cannot move into own den
        if (square.isDen() && square.isOwnedBy(owner)) {
            return false;
        }

        // Cannot move into a square occupied by own piece
        if (square.getPiece() != null && square.getPiece().isOwnedBy(owner)) {
            return false;
        }

        // Handle movement into water squares
        if (square.isWater()) {
            // Only a rat (strength == 1) can move into water
            return piece.getStrength() == 1;
        }

        // If the square has an opponent's piece, check if our piece can defeat it
        if (square.getPiece() != null) {
            LOGGER.info(
                    "Current Piece Strength: " + piece.getStrength()
                            + " " + "Opponent piece strength: "
                            + square.getPiece().getStrength());
            LOGGER.info(
                    "Can the current piece defeat the opponent's piece?: "
                            + piece.canDefeat(square.getPiece()));
            return piece.canDefeat(square.getPiece());
        }

        // For empty land squares, the move is legal
        return true;
    }

    /**
     * Adds a legal vertical leap move for a lion (strength == 7) if it is
     * within the leap boundary. Checks if a rat blocks the leap path and if
     * the target square is a legal move.
     *
     * @param row        The piece's current row.
     * @param col        The piece's current column.
     * @param legalMoves List of legal moves to add to if a valid leap is found.
     * @param piece      The piece attempting to leap.
     */
    private void addVerticalLeaps(
            int row, int col,
            List<Coordinate> legalMoves, Piece piece) {
        if (piece.getStrength() != 7 || !isInLeapBoundaryVertically(row, col)) {
            LOGGER.info(
                    "Piece is not a lion OR isn't in leap boundary."
                            + " No vertical leaps to add.");
            return;
        }

        Coordinate verticalJump = verticalCoordinate(row, col);
        int newRow = verticalJump.row();
        int newCol = verticalJump.col();

        if (!ratInMiddleVertically(col)
                && isLegalMove(board[newRow][newCol], piece.getOwner(), piece)) {
            LOGGER.info(
                    "Legal move found at: " + verticalJump.row()
                            + ", " + verticalJump.col());
            legalMoves.add(verticalJump);
        }
    }

    /**
     * Adds legal horizontal leaps for a tiger or lion if within the leap
     * boundary. Checks if the path is clear of blocking rats and if the target
     * squares are legal moves.
     *
     * @param row        The piece's current row.
     * @param col        The piece's current column.
     * @param legalMoves List of legal moves to add to if valid leaps are found.
     * @param piece      The piece attempting to leap.
     */
    private void addHorizontalLeaps(
            int row, int col, List<Coordinate> legalMoves, Piece piece) {
        // Ensure the piece can leap and is within the row and column boundaries
        if (!isInLeapBoundaryHorizontally(row, col) || !piece.canLeapHorizontally()) {
            LOGGER.info(
                    "Piece is not a tiger or a lion OR isn't in leap boundary."
                            + " No horizontal leaps to add.");
            return;
        }

        LOGGER.info("Adding horizontal leaps for the Tiger or Lion (if available)");

        for (int offset : HOR_LEAP_OFFSETS) {
            int leapCol = col + offset;
            if (isWithinBounds(row, leapCol)
                    && !ratInMiddleHorizontally(row, leapCol, col)
                    && isLegalMove(board[row][leapCol], piece.getOwner(), piece)) {
                LOGGER.info("Legal move found at: " + row + ", " + leapCol);
                legalMoves.add(new Coordinate(row, leapCol));

            }
        }
    }

    /**
     * Checks if there is a rat in the vertical water squares of the column.
     *
     * @param col The column to check for blocking rats in water squares.
     * @return {@code true} if a rat is found in the vertical path;
     * {@code false} otherwise.
     */
    private boolean ratInMiddleVertically(int col) {
        for (int i = minimum(WATER_ROWS); i <= maximum(WATER_ROWS); i++) {
            //Checks the water cells in the vertical column only for rats.
            if (board[i][col].getPiece() != null
                    && board[i][col].getPiece().getStrength() == 1) {
                LOGGER.info(
                        "Rat found in the middle of the vertical path at "
                                + i + " " + col);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a rat (strength == 1) in the horizontal path between
     * two columns in the specified row.
     *
     * @param leapRow The row to check for blocking rats.
     * @param leapCol The target column of the leap.
     * @param col     The starting column of the leap.
     * @return {@code true} if a rat is found in the horizontal path;
     * {@code false} otherwise.
     */
    private boolean ratInMiddleHorizontally(int leapRow, int leapCol, int col) {
        for (int i = Math.min(leapCol, col); i < Math.max(leapCol, col); i++) {
            if (board[leapRow][i].getPiece() != null
                    && board[leapRow][i].getPiece().getStrength() == 1) {
                LOGGER.info(
                        "Rat found in the middle"
                                + " of the horizontal path at "
                                + leapRow + " " + i);
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the target coordinate for a vertical leap based on the starting row.
     *
     * @param row The starting row of the piece.
     * @param col The column of the piece.
     * @return The target coordinate for the vertical leap.
     */
    private Coordinate verticalCoordinate(int row, int col) {
        Coordinate verticalTarget;
        if (row == (minimum(WATER_ROWS) - 1)) {
            verticalTarget = new Coordinate(maximum(WATER_ROWS) + 1, col);
            return verticalTarget;
        }
        verticalTarget = new Coordinate(minimum(WATER_ROWS) - 1, col);
        return verticalTarget;
    }

    /**
     * Determines which player's turn it is based on the move count.
     *
     * @return The player whose turn it is.
     */
    private Player whoseTurn() {
        if (numMoves % 2 == 0) {
            return players[0];
        }
        return players[1];
    }

    /**
     * Attempts to move a piece from one square to another. Checks for legal
     * moves and updates the board if the move is valid. Declares a winner if
     * the opponent's den is captured.
     *
     * @param fromRow The starting row of the piece.
     * @param fromCol The starting column of the piece.
     * @param toRow   The target row for the move.
     * @param toCol   The target column for the move.
     * @throws IllegalMoveException if the move is not legal.
     */
    public void move(int fromRow, int fromCol, int toRow, int toCol) {

        LOGGER.info(
                "Attempting move from " + fromRow + " " + fromCol
                        + "to " + toRow + " " + toCol);

        List<Coordinate> legalMoves = getLegalMoves(fromRow, fromCol);

        Coordinate moveToMake = new Coordinate(toRow, toCol);

        if (legalMoves.isEmpty() || !legalMoves.contains(moveToMake)) {
            LOGGER.warning("No legal moves to make.");
            throw new IllegalMoveException("No legal moves to make.");
        }

        LOGGER.info("This is move #" + numMoves + 1);
        Square currentSquare = getSquare(fromRow, fromCol);
        Square newSquare = getSquare(toRow, toCol);
        Piece pieceOne = currentSquare.getPiece();

        pieceOne.move(newSquare);
        if (pieceOne.getOwner().hasCapturedDen()) {
            LOGGER.info("The winner is here!");
            winner = pieceOne.getOwner();
        }
        LOGGER.info("Move #" + numMoves + 1 + " is over.");
        numMoves++;
    }

    /**
     * Retrieves the piece located at the specified board coordinates.
     *
     * @param row The row of the square.
     * @param col The column of the square.
     * @return The piece at the specified position, or {@code null} if none exists.
     */
    public Piece getPiece(int row, int col) {

        return board[row][col].getPiece();
    }


}
