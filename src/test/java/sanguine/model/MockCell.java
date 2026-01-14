package sanguine.model;

/**
 * creates a mock instatiation of the Cell class.
 */
public class MockCell extends Cell {

  private final int row;
  private final int col;

  /**
   * creates the cell.
   *
   * @param row desired row
   * @param col desired column
   */
  public MockCell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }
}