package sanguine.model;

import static org.junit.Assert.assertFalse;

import java.util.List;
import org.junit.Test;

/**
 * Tests for legal move validation.
 */
public class LegalMoveTests {

  @Test
  public void testCannotPlaceOnEmptyCell() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    boolean result = model.placeCard(0, 1, 2);

    assertFalse("Cannot place on empty cell", result);
  }

  @Test
  public void testCannotPlaceOnOpponentPawns() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    boolean result = model.placeCard(0, 0, 4);

    assertFalse("Cannot place on opponent's pawns", result);
  }

  @Test
  public void testCannotPlaceOnCardCell() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    model.placeCard(0, 0, 0);

    boolean result = model.placeCard(0, 0, 0);

    assertFalse("Cannot place on cell with card", result);
  }

  @Test
  public void testCostRequirement() {
    List<Card> deck = MockCard.createVariedDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    int cost3Index = -1;
    for (int i = 0; i < model.getPlayerHand(PlayerColor.RED).size(); i++) {
      if (model.getPlayerHand(PlayerColor.RED).get(i).getCost() == 3) {
        cost3Index = i;
        break;
      }
    }

    if (cost3Index != -1) {
      boolean result = model.placeCard(cost3Index, 0, 0);
      assertFalse("Cannot place card costing 3 on cell with 1 pawn", result);
    }
  }

  @Test
  public void testIsLegalMoveBoundaryCheck() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    assertFalse("Should reject negative row",
        model.isLegalMove(0, -1, 0));
    assertFalse("Should reject negative col",
        model.isLegalMove(0, 0, -1));
    assertFalse("Should reject row >= rows",
        model.isLegalMove(0, 3, 0));
    assertFalse("Should reject col >= cols",
        model.isLegalMove(0, 0, 5));
  }

  @Test
  public void testIsLegalMoveInvalidCardIndex() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);

    assertFalse("Should reject negative card index",
        model.isLegalMove(-1, 0, 0));
    assertFalse("Should reject card index >= hand size",
        model.isLegalMove(10, 0, 0));
  }
}