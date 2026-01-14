package sanguine.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.MockSanguineModel;
import sanguine.model.PlayerColor;



/**
 * Tests for MinimizeOpponentScoreStrategy.
 */
public class MinimizeOpponentScoreStrategyTest {

  private MinimizeOpponentScoreStrategy strategy;
  private MockSanguineModel model;

  /**
   * set values up before testing.
   */
  @Before
  public void setUp() {
    strategy = new MinimizeOpponentScoreStrategy();
    model = new MockSanguineModel();
  }

  @Test
  public void testChooseMoveReturnsValidMove() {
    Move move = strategy.chooseMove(model, PlayerColor.RED);

    assertNotNull(move);
    assertTrue(move.getCardIndex() >= 0);
    assertTrue(move.getRow() >= 0);
    assertTrue(move.getCol() >= 0);
  }

  @Test
  public void testChooseMoveWithEmptyHand() {
    MockSanguineModel emptyModel = new MockSanguineModel(true);

    Move move = strategy.chooseMove(emptyModel, PlayerColor.RED);

    assertNull(move);
  }

  @Test
  public void testChooseMoveSelectsLegalMove() {
    Move move = strategy.chooseMove(model, PlayerColor.RED);

    if (move != null) {
      assertTrue(model.isLegalMove(move.getCardIndex(), move.getRow(), move.getCol()));
    }
  }

  @Test
  public void testChooseMoveForBluePlayer() {
    model.setCurrentPlayer(PlayerColor.BLUE);
    Move move = strategy.chooseMove(model, PlayerColor.BLUE);

    assertNotNull(move);
    assertTrue(move.getCardIndex() >= 0);
  }

  @Test
  public void testChooseMoveConsistency() {
    // Same board state should produce same move
    Move move1 = strategy.chooseMove(model, PlayerColor.RED);
    Move move2 = strategy.chooseMove(model, PlayerColor.RED);

    assertNotNull(move1);
    assertNotNull(move2);
    assertEquals(move1.getCardIndex(), move2.getCardIndex());
    assertEquals(move1.getRow(), move2.getRow());
    assertEquals(move1.getCol(), move2.getCol());
  }
}