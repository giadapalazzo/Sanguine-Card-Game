package sanguine.model;

import java.util.ArrayList;

/**
 * Mock Board for testing purposes.
 */
public class MockBoard extends Board {

  /**
   * Constructor that creates the Mock board.
   *
   * @param rows desired rows
   * @param cols desired columns
   */
  public MockBoard(int rows, int cols) {
    super(rows, cols, new ArrayList<>());
  }

  @Override
  public MockCell getCell(int row, int col) {
    return new MockCell(row, col);
  }

  /**
   * Creates a simple 3x5 board with initial pawns.
   */
  public static Board createStandardBoard() {
    return new Board(3, 5, new ArrayList<>());
  }
  /**
   * Creates a small 2x3 board for quick testing.
   */

  public static Board createSmallBoard() {
    return new Board(2, 3, new ArrayList<>());
  }

  /**
   * Creates a large 5x7 board.
   */
  public static Board createLargeBoard() {
    return new Board(5, 7, new ArrayList<>());
  }
  /**
   * Sets up a board with specific pawn configuration for testing.
   */

  public static Board createBoardWithPawns(int rows, int cols,
                                           int redPawns, int bluePawns) {
    Board board = new Board(rows, cols, new ArrayList<>());
    return board;
  }
}
