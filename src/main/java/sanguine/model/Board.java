package sanguine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game board for the game Sanguine.
 */
public class Board {
  private final int rows;
  private final int cols;
  private final List<List<Cell>> grid;

  /**
   * A constructor for a game board the Sanguine board game.
   *
   * @param rows in the board.
   * @param cols in the board.
   */
  public Board(int rows, int cols) {
    if (rows <= 0 || cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Invalid board dimensions");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new ArrayList<>();

    for (int row = 0; row < rows; row++) {
      ArrayList<Cell> listOfRows  = new ArrayList<>();
      for (int col = 0; col < cols; col++) {
        Cell cell = new Cell();
        if (col == 0) {
          cell.initPawn(PlayerColor.RED, 1);
        } else if (col == cols - 1) {
          cell.initPawn(PlayerColor.BLUE, 1);
        }
        cell.setPosition(row, col);
        listOfRows.add(cell);
      }
      grid.add(listOfRows);
    }
  }

  /**
   * Private constructor for creating a board with a pre-existing grid.
   * Used internally for copying boards.
   *
   * @param rows number of rows
   * @param cols number of columns
   * @param grid pre populated grid
   */
  public Board(int rows, int cols, List<List<Cell>> grid) {
    if (rows <= 0 || cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Invalid board dimensions");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = grid;
  }

  /**
   * Creates a deep copy of this board.
   *
   * @return a new board with the same state
   */
  public Board copy() {
    List<List<Cell>> newGrid = new ArrayList<>();
    for (int row = 0; row < rows; row++) {
      List<Cell> newRow = new ArrayList<>();
      for (int col = 0; col < cols; col++) {
        newRow.add(grid.get(row).get(col).copy());
      }
      newGrid.add(newRow);
    }
    Board boardCopy = new Board(this.rows, this.cols, newGrid);
    return boardCopy;
  }

  /**
   * Returns the number of rows in this game board.
   *
   * @return number of rows in this game board.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in this game board.
   *
   * @return number of columns in this game board.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Returns the specific cell specified by cell coordinates.
   *
   * @param row number of cell.
   * @param col number of cell.
   * @return the specified cell.
   */
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Cell position does not exist");
    }
    return grid.get(row).get(col);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<Cell> row : grid) {
      for (Cell cell : row) {
        sb.append(cell.cellString());
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
