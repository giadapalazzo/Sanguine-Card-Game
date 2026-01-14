package sanguine.controller;

import sanguine.model.PlayerColor;

/**
 * Listener for model status.
 */
public interface ModelStatusListener {

  /**
   * Called when it becomes a player's turn.
   *
   * @param color the color of the player whose turn it is
   */
  void onTurnStart(PlayerColor color);

  /**
   * Called when the game has ended.
   *
   * @param winner the color of the winning player
   * @param winningScore the winner's score
   */
  void onGameOver(PlayerColor winner, int winningScore);
}
