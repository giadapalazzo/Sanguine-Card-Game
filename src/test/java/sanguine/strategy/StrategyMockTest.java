package sanguine.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.model.Card;
import sanguine.model.PlayerColor;


/**
 * Mock-based tests for Sanguine strategies.
 */
public class StrategyMockTest {

  /**
   * Helper to create a simple test deck.
   */
  private List<Card> createSimpleDeck(int size) {
    List<Card> deck = new ArrayList<>();
    boolean[][] influence = new boolean[5][5];
    influence[2][2] = true;

    for (int i = 0; i < size; i++) {
      deck.add(new Card("Card" + i, 1, i + 1, influence));
    }
    return deck;
  }

  @Test
  public void testFillFirstChecksCardsLeftToRight() {
    List<Card> hand = createSimpleDeck(3);
    MockSanguineModel mock = new MockSanguineModel(
        3, 5, hand, hand, PlayerColor.RED);

    mock.setLegalMove(2, 1, 2, true);

    FillFirstStrategy strategy = new FillFirstStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNotNull("Should find the legal move", move);
    assertEquals("Should choose card 2", 2, move.getCardIndex());
    assertEquals("Should choose row 1", 1, move.getRow());
    assertEquals("Should choose col 2", 2, move.getCol());

    // Verify it checked cards in order (0, 1, then 2)
    List<String> transcript = mock.getTranscript();
    assertTrue("Should check card 0 before card 2",
        transcript.indexOf("isLegalMove(0, 0, 0)")
            < transcript.indexOf("isLegalMove(2, 1, 2)"));
  }

  @Test
  public void testFillFirstChecksCellsTopToBottomLeftToRight() {
    List<Card> hand = createSimpleDeck(1);
    MockSanguineModel mock = new MockSanguineModel(
        3, 3, hand, hand, PlayerColor.RED);

    mock.setLegalMove(0, 2, 2, true);

    FillFirstStrategy strategy = new FillFirstStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNotNull("Should find the legal move", move);
    assertEquals("Should choose row 2", 2, move.getRow());
    assertEquals("Should choose col 2", 2, move.getCol());

    // Verify it checked (0,0) before (2,2)
    List<String> transcript = mock.getTranscript();
    int index00 = transcript.indexOf("isLegalMove(0, 0, 0)");
    int index22 = transcript.indexOf("isLegalMove(0, 2, 2)");
    assertTrue("Should check (0,0) before (2,2)", index00 < index22);
  }

  @Test
  public void testFillFirstStopsAtFirstLegalMove() {
    List<Card> hand = createSimpleDeck(2);
    MockSanguineModel mock = new MockSanguineModel(
        2, 2, hand, hand, PlayerColor.RED);

    mock.setLegalMove(0, 0, 1, true);
    mock.setLegalMove(1, 1, 1, true);

    FillFirstStrategy strategy = new FillFirstStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertEquals("Should stop at first legal move", 0, move.getCardIndex());
    assertEquals("Should choose row 0", 0, move.getRow());
    assertEquals("Should choose col 1", 1, move.getCol());

    List<String> transcript = mock.getTranscript();
    assertTrue("Should have checked card 0 at (0,1)",
        transcript.contains("isLegalMove(0, 0, 1)"));
  }

  @Test
  public void testMaximizeRowScoreChecksRowsTopToBottom() {
    List<Card> hand = createSimpleDeck(2);
    MockSanguineModel mock = new MockSanguineModel(
        3, 3, hand, hand, PlayerColor.RED);

    mock.setRowScore(0, PlayerColor.RED, 1);
    mock.setRowScore(0, PlayerColor.BLUE, 5);

    mock.setRowScore(2, PlayerColor.RED, 2);
    mock.setRowScore(2, PlayerColor.BLUE, 4);

    mock.setLegalMove(1, 2, 0, true);



    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    List<String> transcript = mock.getTranscript();
    int row0Check = transcript.indexOf("getRowScore(0, RED)");
    int row2Check = transcript.indexOf("getRowScore(2, RED)");
    assertTrue("Should check row 0 before row 2", row0Check < row2Check);
  }

  @Test
  public void testMaximizeRowScoreOnlyTriesToWinLosingRows() {
    List<Card> hand = createSimpleDeck(3);
    MockSanguineModel mock = new MockSanguineModel(
        3, 3, hand, hand, PlayerColor.RED);

    mock.setRowScore(0, PlayerColor.RED, 10);
    mock.setRowScore(0, PlayerColor.BLUE, 5);

    mock.setRowScore(1, PlayerColor.RED, 3);
    mock.setRowScore(1, PlayerColor.BLUE, 8);


    mock.setRowScore(1, PlayerColor.BLUE, 5);

    mock.setLegalMove(2, 1, 0, true);

    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNotNull("Should find a move for losing row", move);
    assertEquals("Should target row 1", 1, move.getRow());
    assertEquals("Should choose card 2", 2, move.getCardIndex());
  }

  @Test
  public void testMaximizeRowScoreCalculatesCorrectly() {
    List<Card> hand = createSimpleDeck(3);
    MockSanguineModel mock = new MockSanguineModel(
        2, 3, hand, hand, PlayerColor.BLUE);

    mock.setRowScore(0, PlayerColor.BLUE, 5);
    mock.setRowScore(0, PlayerColor.RED, 10);


    mock.setRowScore(1, PlayerColor.BLUE, 2);
    mock.setRowScore(1, PlayerColor.RED, 3);

    mock.setLegalMove(2, 1, 0, true);

    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.BLUE);

    assertNotNull("Should find a winning move", move);
    assertEquals("Should choose card 2", 2, move.getCardIndex());
    assertEquals("Should target row 1", 1, move.getRow());
  }

  @Test
  public void testMaximizeRowScoreReturnsNullWhenNoWinningMove() {
    List<Card> hand = createSimpleDeck(2);
    MockSanguineModel mock = new MockSanguineModel(
        2, 2, hand, hand, PlayerColor.RED);

    mock.setRowScore(0, PlayerColor.RED, 1);
    mock.setRowScore(0, PlayerColor.BLUE, 20);
    mock.setRowScore(1, PlayerColor.RED, 2);
    mock.setRowScore(1, PlayerColor.BLUE, 25);

    for (int card = 0; card < 2; card++) {
      for (int row = 0; row < 2; row++) {
        for (int col = 0; col < 2; col++) {
          mock.setLegalMove(card, row, col, true);
        }
      }
    }

    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNull("Should return null when no move can win a row", move);
  }

  @Test
  public void testFillFirstReturnsNullWhenNoLegalMoves() {
    List<Card> hand = createSimpleDeck(2);
    MockSanguineModel mock = new MockSanguineModel(
        2, 2, hand, hand, PlayerColor.RED);

    FillFirstStrategy strategy = new FillFirstStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNull("Should return null when no legal moves", move);
  }

  @Test
  public void testMaximizeRowScoreFindsFirstCardThatWins() {
    List<Card> hand = createSimpleDeck(3);
    MockSanguineModel mock = new MockSanguineModel(
        1, 3, hand, hand, PlayerColor.RED);

    mock.setRowScore(0, PlayerColor.RED, 5);
    mock.setRowScore(0, PlayerColor.BLUE, 8);


    mock.setRowScore(0, PlayerColor.BLUE, 7);

    mock.setLegalMove(2, 0, 0, true);

    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(mock, PlayerColor.RED);

    assertNotNull("Should find the winning card", move);
    assertEquals("Should choose card 2", 2, move.getCardIndex());
  }
}