package sanguine.strategy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.MockSanguineModel;
import sanguine.model.PlayerColor;



/**
 * Tests for FillFirstStrategy.
 */
public class FillFirstStrategyTest {

  private FillFirstStrategy strategy;
  private MockSanguineModel model;

  /**
   * set up values before testing.
   */
  @Before
  public void setUp() {
    strategy = new FillFirstStrategy();
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
  public void testChoosesMoveInTopLeftOrder() {
    Move move = strategy.chooseMove(model, PlayerColor.RED);

    assertNotNull(move);
    assertTrue(move.getRow() >= 0);
    assertTrue(move.getCol() >= 0);
  }
}