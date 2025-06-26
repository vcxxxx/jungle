package jungle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jungle.Player;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Square;
import jungle.squares.Trap;
import jungle.squares.WaterSquare;

public class SquareTest {

  private static Square land, water, michaelsDen, ozsTrap;
  private static Player michael, oz;

  @BeforeAll
  public static void setup() {
    michael = new Player("Michael", 0);
    oz = new Player("Ozgur", 1);
    land = new PlainSquare();
    water = new WaterSquare();
    michaelsDen = new Den(michael);
    ozsTrap = new Trap(oz);
  }

  @Test
  public void testExists() {
    assertNotNull(land);
  }

  @Test
  public void testNoOwner() {
    assertFalse(land.isOwnedBy(michael));
  }

  @Test
  public void testOwner() {
    assertTrue(michaelsDen.isOwnedBy(michael));
  }

  @Test
  public void testWrongOwner() {
    assertFalse(michaelsDen.isOwnedBy(oz));
  }

  @Test
  public void testWater() {
    assertTrue(water.isWater());
  }

  @Test
  public void testNotWater() {
    assertFalse(ozsTrap.isWater());
  }

  @Test
  public void testDen() {
    assertTrue(michaelsDen.isDen());
  }

  @Test
  public void testTrap() {
    assertTrue(ozsTrap.isTrap());
  }
}
