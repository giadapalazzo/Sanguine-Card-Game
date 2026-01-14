package sanguine.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sanguine.controller.PlayerActionListener;
import sanguine.model.MockSanguineModel;
import sanguine.model.PlayerColor;
import sanguine.strategy.FillFirstStrategy;
import sanguine.strategy.SanguineStrategy;


/**
 * Tests for MachinePlayer class.
 */
public class MachinePlayerTest {

  private MachinePlayer player;
  private MockSanguineModel model;
  private MockPlayerActionListener listener;

  /**
   * sets up values before testing.
   */
  @Before
  public void setUp() {
    model = new MockSanguineModel();
    SanguineStrategy strategy = new FillFirstStrategy();
    player = new MachinePlayer(PlayerColor.RED, strategy, model);
    listener = new MockPlayerActionListener();
    player.addPlayerActionListener(listener);
  }

  @Test
  public void testGetColor() {
    assertEquals(PlayerColor.RED, player.getColor());
  }

  @Test
  public void testGetColorBlue() {
    SanguineStrategy strategy = new FillFirstStrategy();
    MachinePlayer bluePlayer = new MachinePlayer(PlayerColor.BLUE, strategy, model);
    assertEquals(PlayerColor.BLUE, bluePlayer.getColor());
  }

  @Test
  public void testOnTurnStartTriggersStrategy() {
    player.onTurnStart(PlayerColor.RED);

    assertTrue(listener.wasCardSelectedCalled());
    assertTrue(listener.wasCellSelectedCalled());
    assertTrue(listener.wasConfirmMoveCalled());
  }

  @Test
  public void testOnTurnStartNotMyTurn() {
    player.onTurnStart(PlayerColor.BLUE);

    assertFalse(listener.wasCardSelectedCalled());
    assertFalse(listener.wasCellSelectedCalled());
    assertFalse(listener.wasConfirmMoveCalled());
  }

  @Test
  public void testOnTurnStartWithEmptyHand() {
    MockSanguineModel emptyModel = new MockSanguineModel(true);
    SanguineStrategy strategy = new FillFirstStrategy();
    MachinePlayer emptyPlayer = new MachinePlayer(PlayerColor.RED, strategy, emptyModel);
    MockPlayerActionListener emptyListener = new MockPlayerActionListener();
    emptyPlayer.addPlayerActionListener(emptyListener);

    emptyPlayer.onTurnStart(PlayerColor.RED);

    assertFalse(emptyListener.wasCardSelectedCalled());
  }

  @Test
  public void testOnGameOver() {
    // Should not throw exception
    player.onGameOver(PlayerColor.RED, 100);
    player.onGameOver(PlayerColor.BLUE, 50);
    player.onGameOver(null, 0);
  }

  @Test
  public void testMultipleListeners() {
    MockPlayerActionListener listener2 = new MockPlayerActionListener();
    player.addPlayerActionListener(listener2);

    player.onTurnStart(PlayerColor.RED);

    assertTrue(listener.wasCardSelectedCalled());
    assertTrue(listener2.wasCardSelectedCalled());
  }



  // Mock listener
  private static class MockPlayerActionListener implements PlayerActionListener {
    private boolean cardSelectedCalled = false;
    private boolean cellSelectedCalled = false;
    private boolean confirmMoveCalled = false;
    private boolean passTurnCalled = false;

    @Override
    public void cardSelected(int cardIndex) {
      cardSelectedCalled = true;
    }

    @Override
    public void cellSelected(int row, int col) {
      cellSelectedCalled = true;
    }

    @Override
    public void confirmMove() {
      confirmMoveCalled = true;
    }

    @Override
    public void passTurn() {
      passTurnCalled = true;
    }

    public boolean wasCardSelectedCalled() {
      return cardSelectedCalled;
    }

    public boolean wasCellSelectedCalled() {
      return cellSelectedCalled;
    }

    public boolean wasConfirmMoveCalled() {
      return confirmMoveCalled;
    }

    public boolean wasPassTurnCalled() {
      return passTurnCalled;
    }
  }
}