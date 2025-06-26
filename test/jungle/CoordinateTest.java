import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jungle.Coordinate;

public class CoordinateTest {

    @Test
    public void testRow() {
        Coordinate c = new Coordinate(2, 3);
        assertEquals(2, c.row());
    }

    @Test
    public void testCol() {
        Coordinate c = new Coordinate(2, 3);
        assertEquals(3, c.col());
    }
}
