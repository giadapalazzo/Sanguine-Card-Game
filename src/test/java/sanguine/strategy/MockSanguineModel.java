package sanguine.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Board;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellContent;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Mock model for testing strategies.
 * Records which methods were called.
 */
public class MockSanguineModel implements ReadOnlySanguineModel {


  private final List<String> transcript;
  private final int rows;
  private final int cols;
  private final List<Card> redHand;
  private final List<Card> blueHand;
  private final PlayerColor currentPlayer;
  private final boolean[][] legalMoves;
  private final int[][] rowScores; //[row][player] where player: 0 = Red 1= Blue

  /**
   * Constructor for mock model.
   *
   * @param rows number of rows
   * @param cols number of columns
   * @param redHand red player's hand
   * @param blueHand blue player's hand
   * @param currentPlayer current player
   */
  public MockSanguineModel(int rows, int cols,
                           List<Card> redHand, List<Card> blueHand,  PlayerColor currentPlayer) {
    this.transcript = new ArrayList<>();
    this.rows = rows;
    this.cols = cols;
    this.redHand = new ArrayList<>(redHand);
    this.blueHand = new ArrayList<>(blueHand);
    this.currentPlayer = currentPlayer;
    this.legalMoves = new boolean[rows * cols][redHand.size()];
    this.rowScores = new int[rows][2];
  }
  /**
   * Sets whether a move is legal.
   *
   * @param cardIndex card index
   * @param row row
   * @param col column
   * @param isLegal whether the move is legal
   */

  public void setLegalMove(int cardIndex, int row, int col, boolean isLegal) {
    int cellIndex = row * cols + col;
    legalMoves[cellIndex][cardIndex] = isLegal;
  }

  /**
   * Sets the row score for a player.
   *
   * @param row row index
   * @param player player
   * @param score score value
   */

  public void setRowScore(int row, PlayerColor player, int score) {
    rowScores[row][player == PlayerColor.RED ? 0 : 1] = score;
  }

  /**
   * Gets the transcript of method calls.
   *
   * @return list of method calls
   */

  public List<String> getTranscript() {
    return new ArrayList<>(transcript);
  }

  @Override
  public boolean isLegalMove(int cardIndex, int row, int col) {
    transcript.add("isLegalMove(" + cardIndex + ", " + row + ", " + col + ")");
    int cellIndex = row * cols + col;
    return legalMoves[cellIndex][cardIndex];
  }

  @Override
  public int getRowScore(int row, PlayerColor player) {
    transcript.add("getRowScore(" + row + ", " + player + ")");
    return rowScores[row][player == PlayerColor.RED ? 0 : 1];
  }

  @Override
  public List<Card> getPlayerHand(PlayerColor player) {
    transcript.add("getPlayerHand(" + player + ")");
    return player == PlayerColor.RED
        ? new ArrayList<>(redHand) : new ArrayList<>(blueHand);
  }

  @Override
  public int getRows() {
    transcript.add("getRows()");
    return rows;
  }

  @Override
  public int getCols() {
    transcript.add("getCols()");
    return cols;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    transcript.add("getCurrentPlayer()");
    return currentPlayer;
  }

  @Override
  public boolean isGameOver() {
    transcript.add("isGameOver()");
    return false;
  }

  // Stub implementations for methods not used in strategies
  @Override
  public PlayerColor getWinner() {
    return null;
  }

  @Override
  public Board getBoard() {
    return null;
  }

  @Override
  public List<Card> getHand(PlayerColor player) {
    return getPlayerHand(player);
  }

  @Override
  public boolean isLegalPlacement(Card card, Cell cell) {
    return false;
  }

  @Override
  public int getTotalScore(PlayerColor player) {
    return 0;
  }

  @Override
  public int getNumRows() {
    return rows;
  }

  @Override
  public int getNumCols() {
    return cols;
  }

  @Override
  public CellContent getCellContent(int row, int col) {
    return CellContent.EMPTY;
  }

  @Override
  public PlayerColor getOwnerOfCell(int row, int col) {
    return null;
  }

  @Override
  public int getPawnCount(int row, int col) {
    return 0;
  }

  @Override
  public Card getCardAt(int row, int col) {
    return null;
  }

  @Override
  public Cell getCell(int row, int col) {
    return null;
  }

  @Override
  public int getCurrentHandSize() {
    return currentPlayer == PlayerColor.RED ? redHand.size() : blueHand.size();
  }


}
