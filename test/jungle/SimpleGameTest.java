package jungle;

import jungle.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests the Game class using simple setups with only a few pieces. */
public class SimpleGameTest {

  private Player michael, oz;
  private Game game;

  @BeforeEach
  public void setup() {
    michael = new Player("Michael", 0);
    oz = new Player("Ozgur", 1);
    game = new Game(michael, oz);
  }

  @Test
  public void testHeight() {
    assertEquals(9, Game.HEIGHT);
  }

  @Test
  public void testWidth() {
    assertEquals(7, Game.WIDTH);
  }

  @Test
  public void testExists() {
    assertNotNull(game);
  }

  @Test
  public void testNoPiece() {
    assertNull(game.getPiece(1, 0));
  }

  @Test
  public void testAddPiece() {
    game.addPiece(1, 0, 3, 0); // Player 0's dog at (1,0)
    assertNotNull(game.getPiece(1, 0));
  }

  @Test
  public void testAddPieceOccupied() {
    game.addPiece(1, 0, 3, 0); // Player 0's dog at (1,0)
    game.addPiece(1, 0, 2, 0); // Player 0's cat replaces it
    assertEquals(2, game.getPiece(1, 0).getStrength());
  }

  @Test
  public void testGetBadSquare() {
    // Calling game.getSquare(0, 9) throws an IndexOutOfBoundsException
    assertThrows(IndexOutOfBoundsException.class, () -> game.getSquare(0, 9));
  }

  @Test
  public void testGetBadSquareNegative() {
    assertThrows(IndexOutOfBoundsException.class, () -> game.getSquare(-1, 2));
  }

  @Test
  public void testWater() {
    assertTrue(game.getSquare(4, 5).isWater());
  }

  @Test
  public void testNotWater() {
    assertFalse(game.getSquare(4, 6).isWater());
  }

  @Test
  public void testDen() {
    assertTrue(game.getSquare(8, 3).isDen());
  }

  @Test
  public void testDenOwner() {
    assertTrue(game.getSquare(8, 3).isOwnedBy(oz));
  }

  @Test
  public void testTrap() {
    assertTrue(game.getSquare(0, 2).isTrap());
  }

  @Test
  public void testGetPlayer() {
    assertEquals(oz, game.getPlayer(1));
  }

  @Test
  public void testGetPlayerBad() {
    assertThrows(IllegalArgumentException.class, () -> game.getPlayer(3));
  }

  @Test
  public void testNoLegalMoves() {
    assertTrue(game.getLegalMoves(0, 0).isEmpty());
  }

  @Test
  public void testLegalMovesBadSquare() {
    assertThrows(IndexOutOfBoundsException.class, () -> game.getLegalMoves(-2, 7));
  }

  @Test
  public void testMoveFromBadSquare() {
    assertThrows(IndexOutOfBoundsException.class, () -> game.move(-1, 7, 0, 7));
  }

  @Test
  public void testMoveLegal() {
    game.addPiece(2, 4, 4, 0); // place michael's wolf
    Piece wolf = game.getPiece(2, 4);
    game.addPiece(6, 2, 5, 1); // place oz's leopard
    game.move(2, 4, 2, 3); // michael's wolf moves right
    assertEquals(wolf, game.getPiece(2, 3));
    assertNull(game.getPiece(2, 4));
  }

  @Test
  public void testEnterDenWinGame() {
    game.addPiece(8, 2, 2, 0); // michael's cat near oz's den
    game.addPiece(6, 2, 4, 1); // oz's wolf

    game.move(8, 2, 8, 3); // michael's cat enters oz's den
    assertEquals(michael, game.getWinner());
    assertTrue(game.isGameOver());
  }

  @Test
  public void testNoPiecesLoseGame() {
    game.addPiece(2, 4, 4, 0); // place michael's wolf, oz has no pieces
    assertEquals(michael, game.getWinner());
    assertTrue(game.isGameOver());
  }

  @Test
  public void testTigerHorizontalJump() {
    game.addPiece(3, 0, 6, 0); // place michael's tiger next to water
    game.addPiece(7, 1, 2, 1); // oz has a piece too
    assertEquals(3, game.getLegalMoves(3, 0).size()); // tiger can leap water horizontally
    game.move(3, 0, 3, 3); // leap and don't throw exception
  }

  @Test
  public void testTigerVerticalJump() {
    game.addPiece(2, 1, 6, 0); // place michael's tiger behind water
    game.addPiece(7, 1, 2, 1); // oz has a piece too
    assertEquals(3, game.getLegalMoves(2, 1).size()); // tiger cannot leap water vertically
    assertThrows(IllegalMoveException.class, () -> game.move(2, 1, 6, 1));
  }

  @Test
  public void testLionVerticalJump() {
    game.addPiece(2, 1, 7, 0); // place michael's lion behind water
    game.addPiece(7, 1, 2, 1); // oz has a piece too
    assertEquals(4, game.getLegalMoves(2, 1).size()); // tiger can leap water vertically
    game.move(2, 1, 6, 1);
  }
}
