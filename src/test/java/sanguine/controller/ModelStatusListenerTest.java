package sanguine.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import sanguine.model.PlayerColor;



/**
 * Tests for ModelStatusListener interface.
 */
public class ModelStatusListenerTest {

  @Test
  public void testListenerImplementation() {
    MockModelStatusListener listener = new MockModelStatusListener();

    listener.onTurnStart(PlayerColor.RED);
    assertTrue(listener.wasTurnStartCalled());
    assertEquals(PlayerColor.RED, listener.getLastPlayer());

    listener.onGameOver(PlayerColor.BLUE, 100);
    assertTrue(listener.wasGameOverCalled());
    assertEquals(PlayerColor.BLUE, listener.getWinner());
    assertEquals(100, listener.getWinningScore());
  }

  private static class MockModelStatusListener implements ModelStatusListener {
    private boolean turnStartCalled = false;
    private boolean gameOverCalled = false;
    private PlayerColor lastPlayer;
    private PlayerColor winner;
    private int winningScore;

    @Override
    public void onTurnStart(PlayerColor color) {
      turnStartCalled = true;
      lastPlayer = color;
    }

    @Override
    public void onGameOver(PlayerColor winner, int winningScore) {
      gameOverCalled = true;
      this.winner = winner;
      this.winningScore = winningScore;
    }

    public boolean wasTurnStartCalled() {
      return turnStartCalled;
    }

    public boolean wasGameOverCalled() {
      return gameOverCalled;
    }

    public PlayerColor getLastPlayer() {
      return lastPlayer;
    }

    public PlayerColor getWinner() {
      return winner;
    }

    public int getWinningScore() {
      return winningScore;
    }
  }
}