package sanguine.strategy;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import sanguine.model.Card;
import sanguine.model.PlayerColor;

/**
 * Testing the maximize row score strategy.
 */
public class MaximizeRowScoreStrategyTest {

  @Test
  public void testFindsMoveThatWinsRow() {

    StringBuilder log = new StringBuilder();

    boolean[][] grid = new boolean[5][5];
    Card c = new Card("A", 1, 2, grid);
    List<Card> hand = List.of(c);

    boolean[][] legal = {
        { false, true, false },
        { false, false, false },
        { false, false, false }
    };

    int[][] redScores = {
        {0}, {0}, {0}
    };
    int[][] blueScores = {
        {1}, {0}, {0}
    };

    MockStrategyModel mock =
        new MockStrategyModel(log, 3, 3, hand, legal, redScores, blueScores);

    SanguineStrategy strat = new MaximizeRowScoreStrategy();

    Move move = strat.chooseMove(mock, PlayerColor.RED);

    assertEquals(0, move.getCardIndex());
    assertEquals(0, move.getRow());
    assertEquals(1, move.getCol());
  }
}
