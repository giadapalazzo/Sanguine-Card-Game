package sanguine.model;

import sanguine.controller.ModelStatusListener;

/**
 * Sanguine model that is mutable and represents changes to hands.
 */
public interface MutableSanguineModel extends ReadOnlySanguineModel {
  /**
   * Attempts placing card from current player hand to a cell.
   *
   * @param cardIndex index of the card in hand.
   * @param row of the specified cell.
   * @param col of the specified cell.
   *
   * @return boolean based on if card was successfully placed.
   */
  boolean placeCard(int cardIndex, int row, int col);

  /**
   * Passes current player's turn.
   */
  void pass();

  /**
   * Adds a listener to be notified of model status changes.
   *
   * @param listener the listener to add
   */
  void addModelStatusListener(ModelStatusListener listener);

  /**
   * Starts the game and notifies the first player that it's their turn.
   * Must be called after all listeners are registered.
   */
  void startGame();
}
