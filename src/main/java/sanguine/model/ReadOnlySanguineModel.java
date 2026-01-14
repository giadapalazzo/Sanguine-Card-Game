package sanguine.model;

import java.util.List;

/**
 * Interface to represent the observer methods of the Sanguine Model.
 */
public interface ReadOnlySanguineModel {

  /**
   * Returns the current player by their color.
   *
   * @return the PlayerColor of the current player.
   */
  PlayerColor getCurrentPlayer();

  /**
   * Returns whether the game is over or not.
   *
   * @return a boolean representing if the game is over or not.
   */
  boolean isGameOver();

  /**
   * Returns the winner of the game.
   *
   * @return PlayerColor based on winner of the game if there is one.
   */
  PlayerColor getWinner();

  /**
   * Returns current game board with all the modifications.
   *
   * @return Board representing current state of game board.
   */
  Board getBoard();

  /**
   * Returns score for specific row for a specified player.
   *
   * @param row that is specified.
   * @param player that is specified.
   *
   * @return score in integer format.
   */
  int getRowScore(int row, PlayerColor player);

  /**
   * Returns copy of specific player's hand.
   *
   * @param player that is specified.
   *
   * @return the list of the player's card.
   */
  List<Card> getHand(PlayerColor player);

  /**
   * Figures whether card can legally be placed on cell.
   *
   * @param card that is specified.
   * @param cell that is specified.
   *
   * @return boolean value representing if card can be legally placed on cell.
   */
  boolean isLegalPlacement(Card card, Cell cell);

  /**
   * Returns player's total score.
   *
   * @param player that is specified.
   *
   * @return the player's total score.
   */
  int getTotalScore(PlayerColor player);

  /**
   * Checks if card is at a legal position for the current player.
   *
   * @param cardIndex index of card in current player's hand
   * @param row target row position
   * @param col target column position
   * @return true if legal, or false otherwise
   */
  boolean isLegalMove(int cardIndex, int row, int col);

  /**
   * Returns number of rows in board.
   *
   * @return integer form of number of rows in the board.
   */
  int getNumRows();

  /**
   * Returns number of columns in board.
   *
   * @return integer form of number of columns in the board.
   */
  int getNumCols();

  /**
   * Returns copy of content in the cell.
   *
   * @param row that is specified.
   * @param col that is specified.
   *
   * @return CellContent of the specified cell.
   */
  CellContent getCellContent(int row, int col);

  /**
   * Returns owner of cell.
   *
   * @param row that is specified.
   * @param col that is specified.
   *
   * @return the owner of the cell by PlayerColor.
   */
  PlayerColor getOwnerOfCell(int row, int col);

  /**
   * Get count of pawns at the cell.
   *
   * @param row of the cell specified.
   * @param col of the cell specified.
   *
   * @return integer form of pawns in cell.
   */
  int getPawnCount(int row, int col);

  /**
   * Returns card at the current cell.
   *
   * @param row of the specified cell.
   * @param col of the specified cell.
   *
   * @return card at the specified cell.
   */
  Card getCardAt(int row, int col);

  /**
   * Gets the cell at the specified position.
   *
   * @param row the row index
   * @param col the column index
   * @return the cell at that position
   */

  Cell getCell(int row, int col);

  /**
   * gets a defensive copy of the specified player's hand.
   *
   * @param player the player whose hand to retrieve
   * @return a copy of the player's hand
   */
  List<Card> getPlayerHand(PlayerColor player);

  /**
   * Gets the size of the current player's hand.
   *
   * @return number of cards in current player's hand
   */
  int getCurrentHandSize();

  /**
   * gets the number of rows.
   *
   * @return returns the number of rows in a board
   */
  int getRows();

  /**
   * gets the number of columns.
   *
   * @return returns the number of columns in a board
   */
  int getCols();
}

