package sanguine.player;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import sanguine.controller.PlayerActionListener;
import sanguine.model.PlayerColor;


/**
 * Tests for HumanPlayer class.
 */
public class HumanPlayerTest {

  private HumanPlayer player;

  /**
   * sets up values before testing.
   */
  @Before
  public void setUp() {
    player = new HumanPlayer(PlayerColor.RED);
  }

  @Test
  public void testGetColorRed() {
    assertEquals(PlayerColor.RED, player.getColor());
  }

  @Test
  public void testGetColorBlue() {
    HumanPlayer bluePlayer = new HumanPlayer(PlayerColor.BLUE);
    assertEquals(PlayerColor.BLUE, bluePlayer.getColor());
  }

  @Test
  public void testAddListener() {
    MockPlayerActionListener listener = new MockPlayerActionListener();
    player.addPlayerActionListener(listener);
    // Should not throw exception
  }

  @Test
  public void testAddMultipleListeners() {
    MockPlayerActionListener listener1 = new MockPlayerActionListener();
    MockPlayerActionListener listener2 = new MockPlayerActionListener();

    player.addPlayerActionListener(listener1);
    player.addPlayerActionListener(listener2);
  }


  private static class MockPlayerActionListener implements PlayerActionListener {
    @Override
    public void cardSelected(int cardIndex) {}

    @Override
    public void cellSelected(int row, int col) {}

    @Override
    public void confirmMove() {}

    @Override
    public void passTurn() {}
  }
}