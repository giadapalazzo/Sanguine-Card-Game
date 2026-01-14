package sanguine.view;

import sanguine.controller.CardGameListener;

/**
 * Interface for Sanguine game views.
 * Defines methods that view implementations need.
 */
public interface SanguineView {
  /**
   * Makes the view visible or invisible.
   *
   * @param visible true to show, false to hide
   */
  void setVisible(boolean visible);

  /**
   * Refreshes the view to reflect current model state.
   */
  void refresh();

  /**
   * Adds a listener to handle user interactions.
   *
   * @param listener the listener to add
   */
  void addClickListener(CardGameListener listener);

  /**
   * Displays a message dialog to the user.
   * Used for showing errors, game over messages, etc.
   *
   * @param message the message to display
   */
  void showMessage(String message);

  /**
   * Sets the title of the view window.
   * Used to show whose turn it is (e.g., "RED - Your Turn" or "BLUE - Waiting...")
   *
   * @param title the title to display
   */
  void setTitle(String title);

  /**
   * Highlights the selected card in the hand.
   *
   * @param cardIndex the index of the card to highlight
   */
  void setSelectedCard(int cardIndex);

  /**
   * Highlights the selected cell on the board.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   */
  void setSelectedCell(int row, int col);

  /**
   * Clears all visual selections (cards and cells).
   */
  void clearSelections();

}
