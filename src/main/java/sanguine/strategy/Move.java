package sanguine.strategy;

import java.util.Objects;

/**
 * Represents a move in Sanguine consisting of a card index and board position.
 */
public class Move {

  private final int cardIndex;
  private final int row;
  private final int col;

  /**
   * Constructs a move.
   *
   * @param cardIndex the index of the card in the player's hand
   * @param row the target row on the board
   * @param col the target column on the board
   */

  public Move(int cardIndex, int row, int col) {
    this.cardIndex = cardIndex;
    this.row = row;
    this.col = col;
  }
  /**
   * Gets the card index.
   *
   * @return the card index
   */

  public int getCardIndex() {
    return cardIndex;
  }

  /**
   * Gets the row.
   *
   * @return the row
   */

  public int getRow() {
    return row;
  }

  /**
   * Gets the column.
   *
   * @return the column
   */

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Move)) {
      return false;
    }
    Move move = (Move) o;
    return getCardIndex() == move.getCardIndex() && getCol() == move.getCol();
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardIndex, row, col);
  }

  @Override
  public String toString() {
    return "Move {card=" + cardIndex + ", row=" + row + ", col=" + col + "}";
  }
}
