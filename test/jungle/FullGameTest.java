package jungle;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jungle.Game;
import jungle.IllegalMoveException;
import jungle.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Tests the Game class with all pieces added. */
public class FullGameTest {

  private Player michael, oz;
  private Game game;

  @BeforeEach
  public void setup() {
    michael = new Player("Michael", 0);
    oz = new Player("Ozgur", 1);
    game = new Game(michael, oz);
    game.addStartingPieces();
  }

  @Test
  public void testPiecePlacedStrength() {
    assertEquals(4, game.getPiece(2, 4).getStrength());
  }

  @Test
  public void testPiecePlacedOwner() {
    assertTrue(game.getPiece(2, 4).isOwnedBy(michael));
  }

  @Test
  public void testEmptySquare() {
    assertNull(game.getPiece(2, 3));
  }

  @Test
  public void testPlacedRat() {
    assertTrue(game.getPiece(2, 0).canSwim());
  }

  @Test
  public void testPlacedLion() {
    assertTrue(game.getPiece(8, 6).canLeapVertically());
  }

  @Test
  public void testPlacedDog() {
    assertEquals(3, game.getPiece(1, 1).getStrength());
  }

  @Test
  public void testNoWinnerAtStart() {
    assertNull(game.getWinner());
  }

  @ParameterizedTest
  @CsvSource({
    "2, 4, 3, 4", // wolf tries to enter water
    "2, 6, 2, 7", // elephant tries to go off board
    "2, 0, 4, 0", // rat tries to move two spaces at once
    "2, 2, 3, 3", // leopard tries to move diagonally
    "6, 4, 6, 5", // P1 tries to move on P0's turn
  })
  public void testIllegalMoveException(int fromRow, int fromCol, int toRow, int toCol) {
    assertThrows(IllegalMoveException.class, () -> game.move(fromRow, fromCol, toRow, toCol));
  }

  @Test
  public void testLegalMove() {
    game.move(1, 1, 1, 2);
    assertNull(game.getPiece(1, 1));
    assertNotNull(game.getPiece(1, 2));
  }

  /** How many legal moves can a piece make from the starting position? */
  @ParameterizedTest
  @CsvSource({
    "0, 0, 2", // P0's lion can reach two spaces (it's in the corner)
    "1, 1, 4", // P0's dog is clear in all 4 directions
    "2, 6, 3", // P0's elephant is at the edge: can reach 3 spaces
    "2, 4, 3", // P0's wolf cannot enter the water
    "7, 1, 0", // P1's cat cannot move on P0's turn
    "2, 1, 0", // Square (2, 1) has no piece on it
  })
  public void testGetLegalMovesStart(int row, int col, int expectedNumber) {
    assertEquals(expectedNumber, game.getLegalMoves(row, col).size());
  }

  /**
   * One complete game, testing various things along the way.
   *
   * <p>Normally one assert per test is better, but at least one test of this form seems sensible.
   */
  @Test
  public void testFullGame() {
    game.move(2, 0, 3, 0); // michael advances his rat
    game.move(6, 0, 5, 0); // oz advances his elephant

    game.move(3, 0, 3, 1); // michael's rat enters the water
    game.move(5, 0, 4, 0); // oz advances his elephant further

    game.move(3, 1, 4, 1); // michael's rat swims further down the board
    assertEquals(2, game.getLegalMoves(4, 0).size()); // elephant can't capture rat in water
    game.move(8, 6, 7, 6); // oz's lion advances

    game.move(4, 1, 4, 0); // michael's rat captures oz's elephant!
    assertEquals(1, game.getLegalMoves(7, 6).size()); // lion can't capture friendly pieces
    game.move(6, 6, 5, 6); // oz's rat moves forward

    game.move(4, 0, 4, 1); // michael's rat re-enters the water
    game.move(7, 6, 6, 6); // oz's lion advances
    assertNull(game.getPiece(4, 0));
    assertEquals(1, game.getPiece(4, 1).getStrength());
    assertEquals(7, game.getPiece(6, 6).getStrength());

    game.move(0, 0, 0, 1); // michael's lion moves sideways
    game.move(5, 6, 5, 5); // oz's rat enters the water

    game.move(4, 1, 4, 2); // michael moves his rat sideways (still in the water)
    game.move(6, 6, 6, 5); // oz's lion approaches the water
    // assertEquals(2, game.getLegalMoves(6, 1).size()); // tiger can now leap across the water

    game.move(0, 1, 0, 2); // michael moves his lion into his own trap
    assertEquals(7, game.getPiece(0, 2).getStrength()); // michael's piece not affected by own trap
    assertEquals(
        1, game.getLegalMoves(6, 5).size()); // oz's lion cannot leap across the water due to rat
    game.move(5, 5, 5, 4); // oz moves his rat out of the way

    game.move(2, 4, 2, 5); // michael moves his wolf into danger
    game.move(6, 5, 2, 5); // oz's lion does the leap and takes michael's wolf!
    assertTrue(game.getPiece(2, 5).isOwnedBy(oz));

    assertEquals(3, game.getLegalMoves(1, 5).size()); // michael's cat cannot take oz's tiger
    game.move(0, 6, 0, 5); // michael moves his tiger across
    assertEquals(
        3,
        game.getLegalMoves(2, 5)
            .size()); // oz's lion can jump backwards, but cannot take michael's elephant
    game.move(2, 5, 1, 5); // oz's lion takes michael's cat (7 > 2)

    game.move(4, 2, 5, 2); // michael advances his rat
    game.move(1, 5, 1, 4); // oz moves his lion sideways

    assertEquals(3, game.getLegalMoves(5, 2).size()); // michael's rat cannot capture wolf
    game.move(5, 2, 5, 3); // michael's rat leaves the water
    game.move(1, 4, 0, 4); // oz's lion enters michael's trap

    assertEquals(
        3, game.getLegalMoves(0, 5).size()); // michael's tiger could take oz's trapped lion
    game.move(5, 3, 5, 4); // michael's rat takes oz's rat instead
    assertFalse(game.isGameOver());
    assertNull(game.getWinner());
    game.move(0, 4, 0, 3); // oz moves onto michael's den and wins!

    assertTrue(game.isGameOver());
    assertEquals(oz, game.getWinner());
    assertTrue(game.getLegalMoves(1, 1).isEmpty()); // no legal moves after game end
  }
}
