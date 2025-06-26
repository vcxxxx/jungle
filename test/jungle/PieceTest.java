import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jungle.Player;
import jungle.pieces.Lion;
import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Square;
import jungle.squares.Trap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceTest {

  private Player michael, oz;
  private Square land, michaelsDen, ozsTrap;
  private Piece michaelsWolf, michaelsLion, michaelsElephant, ozsRat;

  @BeforeEach
  public void setup() {
    // Players
    michael = new Player("Michael", 0);
    oz = new Player("Ozgur", 1);

    // Squares
    land = new PlainSquare();
    michaelsDen = new Den(michael);
    ozsTrap = new Trap(oz);

    // Pieces
    michaelsWolf = new Piece(michael, land, 4);
    michaelsLion = new Lion(michael, ozsTrap);
    ozsRat = new Rat(oz, land);
    michaelsElephant = new Piece(michael, land, 8);
  }

  @Test
  public void testOwner() {
    assertTrue(michaelsWolf.isOwnedBy(michael));
  }

  @Test
  public void testNonOwner() {
    assertFalse(michaelsWolf.isOwnedBy(oz));
  }

  @Test
  public void testStrength() {
    assertEquals(4, michaelsWolf.getStrength());
  }

  @Test
  public void testTrappedStrength() {
    assertEquals(0, michaelsLion.getStrength());
  }

  @Test
  public void testCanSwimFalse() {
    assertFalse(michaelsWolf.canSwim());
  }

  @Test
  public void testCanSwimTrue() {
    assertTrue(ozsRat.canSwim());
  }

  @Test
  public void testCanLeap() {
    assertTrue(michaelsLion.canLeapVertically());
  }

  @Test
  public void testCanDefeat() {
    assertTrue(michaelsWolf.canDefeat(ozsRat));
  }

  @Test
  public void testCannotDefeat() {
    assertFalse(ozsRat.canDefeat(michaelsWolf));
  }

  @Test
  public void testDefeatTrapped() {
    assertTrue(ozsRat.canDefeat(michaelsLion));
  }

  @Test
  public void testRatDefeatElephant() {
    assertTrue(ozsRat.canDefeat(michaelsElephant));
  }

  @Test
  public void testMove() {
    michaelsLion.move(ozsTrap);
    assertEquals(0, michaelsLion.getStrength());
  }

  @Test
  public void testMoveToDen() {
    ozsRat.move(michaelsDen);
    assertTrue(oz.hasCapturedDen());
  }

  @Test
  public void testMoveToOwnDen() {
    michaelsWolf.move(michaelsDen);
    assertFalse(michael.hasCapturedDen());
  }

  @Test
  public void testHasPieces() {
    assertTrue(oz.hasPieces());
  }

  @Test
  public void testLoseAllPieces() {
    ozsRat.beCaptured();
    assertFalse(oz.hasPieces());
  }
}
