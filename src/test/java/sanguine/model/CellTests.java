package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for Cell class.
 */
public class CellTests {

  @Test
  public void testEmptyCellInitialization() {
    Cell cell = new Cell();
    assertTrue("New cell should be empty", cell.isEmpty());
    assertEquals("Empty cell should have EMPTY content",
        CellContent.EMPTY, cell.getContent());
  }

  @Test
  public void testInitPawn() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 2);

    assertTrue("Cell should have pawns", cell.hasPawns());
    assertEquals("Should have 2 pawns", 2, cell.getNumPawns());
    assertEquals("Should be owned by RED", PlayerColor.RED, cell.getPlayer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitPawnTooMany() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 4); // Max is 3
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitPawnTooFew() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 0);
  }

  @Test
  public void testPlaceCard() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 2);

    Card card = MockCard.createCard("Test", 1, 5);
    cell.placeCard(PlayerColor.RED, card);

    assertTrue("Cell should have card", cell.hasCard());
    assertEquals("Card value should be 5", 5, cell.getCardValue());
    assertEquals("Pawns should be cleared", 0, cell.getNumPawns());
  }

  @Test
  public void testAddPawn() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.BLUE, 1);

    cell.addPawn();
    assertEquals("Should have 2 pawns", 2, cell.getNumPawns());

    cell.addPawn();
    assertEquals("Should have 3 pawns", 3, cell.getNumPawns());

    cell.addPawn(); // Try to add 4th
    assertEquals("Should still have 3 pawns (max)", 3, cell.getNumPawns());
  }

  @Test
  public void testConvertPawns() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 2);

    cell.convertPawns(PlayerColor.BLUE);

    assertEquals("Should be owned by BLUE", PlayerColor.BLUE, cell.getPlayer());
    assertEquals("Should still have 2 pawns", 2, cell.getNumPawns());
  }

  @Test
  public void testConvertPawnsDoesNotAffectCards() {
    Cell cell = new Cell();
    Card card = MockCard.createCard("Test", 1, 5);
    cell.placeCard(PlayerColor.RED, card);

    cell.convertPawns(PlayerColor.BLUE);

    assertEquals("Card owner should not change",
        PlayerColor.RED, cell.getPlayer());
  }

  @Test
  public void testCellCopy() {
    Cell original = new Cell();
    original.initPawn(PlayerColor.RED, 3);

    Cell copy = original.copy();

    assertEquals("Copy should have same content",
        original.getContent(), copy.getContent());
    assertEquals("Copy should have same player",
        original.getPlayer(), copy.getPlayer());
    assertEquals("Copy should have same pawns",
        original.getNumPawns(), copy.getNumPawns());

    copy.convertPawns(PlayerColor.BLUE);

    assertEquals("Original should still be RED",
        PlayerColor.RED, original.getPlayer());
  }

  @Test
  public void testIsOwnedBy() {
    Cell cell = new Cell();
    cell.initPawn(PlayerColor.RED, 1);

    assertTrue("Should be owned by RED", cell.isOwnedBy(PlayerColor.RED));
    assertFalse("Should not be owned by BLUE", cell.isOwnedBy(PlayerColor.BLUE));
  }
}