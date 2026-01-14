package sanguine.controller;

/**
 * Listener which reacts to specific actions by a player.
 */
public interface PlayerActionListener {

  /**
   * Action for if a card is selected.
   *
   * @param cardIndex is the index of the card.
   */
  void cardSelected(int cardIndex);

  /**
   * Action for if a cell is selected.
   *
   * @param row of the cell in integer form.
   * @param col of the cell in integer form.
   */
  public void cellSelected(int row, int col);

  /**
   * Confirms move.
   */
  public void confirmMove();

  /**
   * Skips turn.
   */
  public void passTurn();
}
