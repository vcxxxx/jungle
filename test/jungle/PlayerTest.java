import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jungle.Player;

public class PlayerTest {

    private Player michael;
    private Player oz;

    @BeforeEach
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
    }

    @Test
    public void testExists() {
        assertNotNull(michael);
    }

    @Test
    public void testGetName() {
        assertEquals("Michael", michael.getName());
    }

    @Test
    public void testGetPlayerNumber() {
        assertEquals(0, michael.getPlayerNumber());
        assertEquals(1, oz.getPlayerNumber());
    }

    @Test
    public void testHasCapturedDenFalse() {
        assertFalse(michael.hasCapturedDen());
    }

    @Test
    public void testHasCapturedDenTrue() {
        michael.captureDen();
        assertTrue(michael.hasCapturedDen());
    }

    @Test
    public void testHasPiecesFalse() {
        assertFalse(michael.hasPieces());
    }

    @Test
    public void testGainLosePieces() {
        michael.gainOnePiece();
        assertTrue(michael.hasPieces());
        michael.loseOnePiece();
        assertFalse(michael.hasPieces());
    }
}
