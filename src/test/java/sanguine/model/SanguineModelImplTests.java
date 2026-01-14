package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * tests for the Sanguine model.
 */
public class SanguineModelImplTests {

  private SanguineModelImpl model;

  /**
   * sets up values before testing.
   */

  @Before
  public void setUp() {
    model = MockModel.createStandardGame();
  }

  @Test
  public void testInitialSetup() {
    assertNotNull("Model should not be null", model);
    assertFalse("Game should not be over initially", model.isGameOver());
    assertEquals("Red should go first", PlayerColor.RED, model.getCurrentPlayer());
  }

  @Test
  public void testRedGoesFirst() {
    assertEquals("Red player should start", PlayerColor.RED, model.getCurrentPlayer());
  }

  @Test
  public void testBoardNotNull() {
    assertNotNull("Board should not be null", model.getBoard());
  }

  @Test
  public void testBoardDimensions() {
    Board board = model.getBoard();
    assertEquals("Board should have 3 rows", 3, board.getRows());
    assertEquals("Board should have 5 columns", 5, board.getCols());
  }

  @Test
  public void testPlaceValidCard() {
    boolean result = model.placeCard(0, 0, 0);
    assertTrue("Should successfully place card", result);
  }


  @Test
  public void testPass() {
    model.pass();
    assertEquals("Should switch player after pass", PlayerColor.BLUE, model.getCurrentPlayer());
  }

  @Test
  public void testGameEndsAfterBothPass() {
    model.pass(); // Red passes
    model.pass(); // Blue passes
    assertTrue("Game should be over after both pass", model.isGameOver());
  }

  @Test
  public void testPassDoesNotEndGameIfOnlyOne() {
    model.pass(); // Red passes
    assertFalse("Game should not be over after one pass", model.isGameOver());
  }

  @Test
  public void testPlaceCardResetsPass() {
    model.pass();
    assertFalse("Game continues", model.isGameOver());
    model.placeCard(0, 0, 4);
    model.pass();
    assertFalse("Game should not end - Blue's pass was reset", model.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullRedDeck() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    new SanguineModelImpl(3, 5, null, deck, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBlueDeck() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    new SanguineModelImpl(3, 5, deck, null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandSizeTooLarge() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    new SanguineModelImpl(3, 5, deck, deck, 10); // 10 > 15/3
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughCards() {
    List<Card> smallDeck = MockCard.createSimpleDeck(10);
    new SanguineModelImpl(3, 5, smallDeck, smallDeck, 3); // Need 15 cards for 3x5
  }

  @Test
  public void testRowScoreCalculation() {
    model.placeCard(0, 0, 0); // Red places
    int redScore = model.getRowScore(0, PlayerColor.RED);
    assertTrue("Red should have score > 0 in row 0", redScore > 0);
  }

  @Test
  public void testWinnerIsNullWhileGameActive() {
    assertNull("Winner should be null while game active", model.getWinner());
  }

  @Test
  public void testWinnerAfterGameEnds() {
    model.pass();
    model.pass();

    PlayerColor winner = model.getWinner();
  }

  @Test
  public void testCannotPlaceAfterGameOver() {
    model.pass();
    model.pass();
    assertTrue("Game should be over", model.isGameOver());
    boolean result = model.placeCard(0, 0, 0);
    assertFalse("Should not place card after game over", result);
  }

  @Test
  public void testCannotPassAfterGameOver() {
    model.pass();
    model.pass();
    assertTrue("Game should be over", model.isGameOver());
    model.pass();
    assertTrue("Game should still be over", model.isGameOver());
  }
}
