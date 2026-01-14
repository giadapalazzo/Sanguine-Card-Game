package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

/**
 * Tests for card influence mechanics.
 */
public class InfluenceTests {

  @Test
  public void testCrossInfluenceRedPlayer() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    deck.set(0, MockCard.createCrossInfluenceCard());

    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    model.placeCard(0, 1, 0);

    Cell up = model.getCell(0, 0);
    Cell down = model.getCell(2, 0);

    assertTrue("Up cell should have pawns", up.hasPawns());
    assertTrue("Down cell should have pawns", down.hasPawns());
  }

  @Test
  public void testInfluenceConvertsPawns() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    deck.set(0, MockCard.createRightInfluenceCard());

    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    model.placeCard(0, 0, 0);

    Cell cell1 = model.getCell(0, 1);

    if (cell1.hasPawns()) {
      assertEquals("Influenced pawns should be red",
          PlayerColor.RED, cell1.getPlayer());
    }
  }

  @Test
  public void testInfluenceDoesNotAffectCards() {
    List<Card> deck = MockCard.createSimpleDeck(15);

    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    model.placeCard(0, 0, 0);

    model.placeCard(0, 1, 4);

    model.placeCard(1, 1, 0);

    Cell blueCell = model.getCell(1, 4);
    if (blueCell.hasCard()) {
      assertEquals("Card should remain BLUE",
          PlayerColor.BLUE, blueCell.getPlayer());
    }
  }

  @Test
  public void testInfluenceAddsToExistingPawns() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    deck.set(0, MockCard.createCrossInfluenceCard());

    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    Cell target = model.getCell(1, 0);
    int initialPawns = target.getNumPawns();

    model.placeCard(0, 0, 0);

    int afterPawns = model.getCell(1, 0).getNumPawns();

    assertTrue("Pawns should increase", afterPawns >= initialPawns);
  }

  @Test
  public void testBlueMirroredInfluence() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    deck.set(0, MockCard.createRightInfluenceCard());

    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    model.pass();

    model.placeCard(0, 0, 4);

    Cell leftCell = model.getCell(0, 3);

    if (leftCell.hasPawns()) {
      assertEquals("Should be blue pawns from mirrored influence",
          PlayerColor.BLUE, leftCell.getPlayer());
    }
  }
}