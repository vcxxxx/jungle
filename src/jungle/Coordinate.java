package jungle;

import java.util.Objects;

/**
 * Represents a coordinate on the game board with a row and column.
 * Overrides the equals method to compare by attributes instead of reference.
 * This is specifically needed for the game.
 *
 * @author 240027249
 */


public class Coordinate {
    private final int row;
    private final int col;

    /**
     * Constructs a Coordinate with the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Checks if this coordinate is equal to another object.
     * This is done so that List.contains() method compares by object attributes
     * and not reference, which is the default behaviour.
     *
     * @param obj The object to compare.
     * @return {@code true} if the coordinates are the same; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinate other = (Coordinate) obj;
        return row == other.row && col == other.col;
    }

    /**
     * Overriding hashCode() is necessary because it ensures consistency with equals().
     *
     * @return The hash code based on row and column values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Returns the row of this coordinate.
     *
     * @return The row index.
     */
    public int row() {
        return row;
    }

    /**
     * Returns the column of this coordinate.
     *
     * @return The column index.
     */
    public int col() {
        return col;
    }
}


// Constructor, getters, setters




