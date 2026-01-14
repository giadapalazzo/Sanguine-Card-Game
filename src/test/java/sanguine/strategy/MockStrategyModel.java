package sanguine.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Board;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellContent;
import sanguine.model.MockBoard;
import sanguine.model.MockCell;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Mock class for SanguineModelImpl.
 */
public class MockStrategyModel implements ReadOnlySanguineModel {

  private final StringBuilder log;
  private final MockBoard board;
  private final int rows;
  private final int cols;

  private final List<Card> hand;
  private final boolean[][] legalMoves;

  private final int[][] redRowScores;
  private final int[][] blueRowScores;
  /**
   * Creates a mock read-only model used for strategy testing.
   *
   * @param log           a StringBuilder used to record method calls
   * @param rows          number of rows in the mock board
   * @param cols          number of columns in the mock board
   * @param hand          the mock hand returned for any player
   * @param legalMoves    a 2D boolean grid indicating whether a move at (r,c) is legal
   * @param redRowScores  mock row scores for the RED player
   * @param blueRowScores mock row scores for the BLUE player
   */

  public MockStrategyModel(StringBuilder log,
                           int rows,
                           int cols,
                           List<Card> hand,
                           boolean[][] legalMoves,
                           int[][] redRowScores,
                           int[][] blueRowScores) {

    this.log = log;
    this.rows = rows;
    this.cols = cols;
    this.hand = hand;
    this.legalMoves = legalMoves;
    this.redRowScores = redRowScores;
    this.blueRowScores = blueRowScores;
    this.board = new MockBoard(rows, cols);
  }

  @Override
  public List<Card> getHand(PlayerColor player) {
    log.append("getHand ").append(player).append("\n");
    return hand;
  }

  @Override
  public int getNumRows() {
    log.append("getNumRows\n");
    return rows;
  }

  @Override
  public int getNumCols() {
    log.append("getNumCols\n");
    return cols;
  }

  @Override
  public Board getBoard() {
    log.append("getBoard\n");
    return board;
  }

  @Override
  public boolean isLegalPlacement(Card c, Cell cell) {
    log.append("isLegalPlacement ")
        .append(cell.getRow()).append(" ")
        .append(cell.getCol()).append("\n");

    return legalMoves[cell.getRow()][cell.getCol()];
  }

  @Override
  public int getRowScore(int row, PlayerColor player) {
    log.append("getRowScore ").append(row).append(" ").append(player).append("\n");

    if (player == PlayerColor.RED) {
      return redRowScores[row][0];
    } else {
      return blueRowScores[row][0];
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public PlayerColor getWinner() {
    return null;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return PlayerColor.RED;
  }

  @Override
  public CellContent getCellContent(int r, int c) {
    return null;
  }

  @Override
  public PlayerColor getOwnerOfCell(int r, int c) {
    return null;
  }

  @Override
  public int getPawnCount(int r, int c) {
    return 0;
  }

  @Override
  public Card getCardAt(int r, int c) {
    return null;
  }

  @Override
  public int getTotalScore(PlayerColor player) {
    return 0;
  }


  @Override
  public boolean isLegalMove(int cardIndex, int row, int col) {
    log.append("isLegalMove ").append(cardIndex)
        .append(" ").append(row).append(" ").append(col).append("\n");

    return legalMoves[row][col];
  }

  @Override
  public Cell getCell(int row, int col) {
    log.append("getCell ").append(row).append(" ").append(col).append("\n");
    return new MockCell(row, col);
  }


  @Override
  public List<Card> getPlayerHand(PlayerColor player) {
    log.append("getPlayerHand ").append(player).append("\n");
    return new ArrayList<>(hand);
  }

  @Override
  public int getCurrentHandSize() {
    log.append("getCurrentHandSize\n");
    return hand.size();
  }

  @Override
  public int getRows() {
    log.append("getRows\n");
    return rows;
  }

  @Override
  public int getCols() {
    log.append("getCols\n");
    return cols;
  }
}
