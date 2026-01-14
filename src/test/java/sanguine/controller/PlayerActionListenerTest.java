package sanguine.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * Tests for PlayerActionListener interface.
 */
public class PlayerActionListenerTest {

  @Test
  public void testListenerImplementation() {
    MockPlayerActionListener listener = new MockPlayerActionListener();

    listener.cardSelected(3);
    assertTrue(listener.wasCardSelectedCalled());
    assertEquals(3, listener.getCardIndex());

    listener.cellSelected(2, 5);
    assertTrue(listener.wasCellSelectedCalled());
    assertEquals(2, listener.getRow());
    assertEquals(5, listener.getCol());

    listener.confirmMove();
    assertTrue(listener.wasConfirmMoveCalled());

    listener.passTurn();
    assertTrue(listener.wasPassTurnCalled());
  }

  private static class MockPlayerActionListener implements PlayerActionListener {
    private boolean cardSelectedCalled = false;
    private boolean cellSelectedCalled = false;
    private boolean confirmMoveCalled = false;
    private boolean passTurnCalled = false;
    private int cardIndex = -1;
    private int row = -1;
    private int col = -1;

    @Override
    public void cardSelected(int cardIndex) {
      cardSelectedCalled = true;
      this.cardIndex = cardIndex;
    }

    @Override
    public void cellSelected(int row, int col) {
      cellSelectedCalled = true;
      this.row = row;
      this.col = col;
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

    public int getCardIndex() {
      return cardIndex;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }
  }
}