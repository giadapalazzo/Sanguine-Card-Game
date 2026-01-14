package sanguine.controller;

import sanguine.model.PlayerColor;
/**
 * Interface for listening to game events from the view.
 * Controllers implement this to respond to user interactions.
 */

public interface CardGameListener {

  /**
   * Called when a card is selected from a player's hand.
   *
   * @param cardIndex the index of the selected card
   */

  void onCardSelected(int cardIndex);

  /**

   * Called when a cell on the board is selected.
   *
   * @param row the row of the selected cell
   * @param col the column of the selected cell
   */
  void onCellSelected(int row, int col);

  /**
   * Called when the user confirms their move(presses  enter).
   */
  void onConfirmMove();

  /**
   * Called when the user chooses to pass their turn.
   */
  void onPass();
}
