package sanguine.view;

import sanguine.model.Board;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineModelImpl;

/**
 * Class to represent the textual view of sanguine game.
 */
public class SanguineTextView {
  private final Board board;
  private final SanguineModelImpl model;

  /**
   * Constructor to initialize SanguineTextView object.
   *
   * @param model game.
   */
  public SanguineTextView(SanguineModelImpl model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    this.board = model.getBoard();
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    for (int row = 0; row < board.getRows(); row++) {
      int redRowScore = model.getRowScore(row, PlayerColor.RED);
      int blueRowScore = model.getRowScore(row, PlayerColor.BLUE);

      sb.append(redRowScore).append(" ");

      for (int col = 0; col < board.getCols(); col++) {
        sb.append(board.getCell(row, col).toSymbol());
      }
      sb.append(" ").append(blueRowScore);
      sb.append("\n");
    }
    return sb.toString();

  }

  /**
   * Method to render the view.
   */
  public void render() {
    System.out.print(this.toString());
  }
}
