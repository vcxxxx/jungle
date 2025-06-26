package jungle;

import jungle.squares.Square;
import jungle.squares.WaterSquare;
import jungle.squares.PlainSquare;
import jungle.squares.Den;
import jungle.squares.Trap;

import java.util.logging.Logger;

import static jungle.Game.DEN_COL;
import static jungle.Game.HEIGHT;
import static jungle.Game.WIDTH;
import static jungle.Game.WATER_COLS;
import static jungle.Game.WATER_ROWS;

/**
 * Utility class for initializing game board and boundary checks for Jungle.
 *
 * @author 240027249
 */

public class GameHelper {

    private static final Logger LOGGER = LoggerSetup.getLogger();

    /**
     * Initializes the game board with traps, dens, water squares, and land squares.
     *
     * @param p0    Player 0 for initializing their designated areas.
     * @param p1    Player 1 for initializing their designated areas.
     * @param board The game board matrix to be set up.
     */
    public static void initializeBoard(Player p0, Player p1, Square[][] board) {
        initializeTraps(p0, p1, board);
        initializeDens(p0, p1, board);
        initializeWaterSquares(board);
        initializeLandSquares(board);
        LOGGER.info("Board has been initialized.");

    }

    /**
     * Sets trap squares on the board for each player.
     *
     * @param p0    Player 0 for setting their traps.
     * @param p1    Player 1 for setting their traps.
     * @param board The game board matrix to add traps.
     */
    private static void initializeTraps(Player p0, Player p1, Square[][] board) {
        board[0][2] = new Trap(p0);
        board[0][4] = new Trap(p0);
        board[1][3] = new Trap(p0);

        board[8][2] = new Trap(p1);
        board[8][4] = new Trap(p1);
        board[7][3] = new Trap(p1);
    }

    /**
     * Sets den squares on the board for each player.
     *
     * @param p0    Player 0 for setting their den.
     * @param p1    Player 1 for setting their den.
     * @param board The game board matrix to add dens.
     */
    private static void initializeDens(Player p0, Player p1, Square[][] board) {
        board[0][DEN_COL] = new Den(p0);
        board[8][DEN_COL] = new Den(p1);
    }

    /**
     * Sets water squares on the board based on predefined rows and columns.
     *
     * @param board The game board matrix to add water squares.
     */
    private static void initializeWaterSquares(Square[][] board) {
        for (int row : WATER_ROWS) {
            for (int col : WATER_COLS) {
                board[row][col] = new WaterSquare();
            }
        }
    }

    /**
     * Fills remaining squares on the board with land squares.
     *
     * @param board The game board matrix to add land squares.
     */
    private static void initializeLandSquares(Square[][] board) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (board[row][col] == null) {
                    board[row][col] = new PlainSquare();
                }
            }
        }
    }

    /**
     * Checks if the specified row and column are within board bounds.
     *
     * @param row The row index.
     * @param col The column index.
     * @return {@code true} if within bounds;
     * {@code false} otherwise.
     */
    public static boolean isWithinBounds(int row, int col) {
        return row >= 0
                && row < HEIGHT
                && col >= 0
                && col < WIDTH;
    }

    /**
     * Finds the minimum value in an integer array.
     *
     * @param arr The array to search.
     * @return The minimum integer value in the array.
     */
    public static int minimum(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int arrElement : arr) {
            if (arrElement < min) {
                min = arrElement;
            }
        }
        return min;
    }

    /**
     * Finds the maximum value in an integer array.
     *
     * @param arr The array to search.
     * @return The maximum integer value in the array.
     */
    public static int maximum(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int arrElement : arr) {
            if (arrElement > max) {
                max = arrElement;
            }
        }
        return max;
    }

    /**
     * Checks if an integer is present in an array.
     *
     * @param num The number to check.
     * @param arr The array to search.
     * @return {@code true} if the number is in the array;
     * {@code false} otherwise.
     */
    public static boolean contains(int num, int[] arr) {
        for (int arrElement : arr) {
            if (arrElement == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a position is within the horizontal leap boundary.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return {@code true} if within
     * the horizontal leap boundary;
     * {@code false} otherwise.
     */
    public static boolean isInLeapBoundaryHorizontally(int row, int col) {
        return row >= minimum(WATER_ROWS)
                && row <= maximum(WATER_ROWS)
                && !contains(col, WATER_COLS);
    }

    /**
     * Checks if a position is within the vertical leap boundary.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return {@code true} if within the vertical leap boundary;
     * {@code false} otherwise.
     */
    public static boolean isInLeapBoundaryVertically(int row, int col) {
        return (row == maximum(WATER_ROWS) + 1
                || row == minimum(WATER_ROWS) - 1)
                && contains(col, WATER_COLS);
    }


}


