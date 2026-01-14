package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ModelStatusListener;


/**
 * Mock model for testing.
 * Provides minimal implementation needed for tests.
 */
public class MockSanguineModel implements MutableSanguineModel {

  private PlayerColor currentPlayer;
  private boolean gameOver;
  private List<Card> redHand;
  private List<Card> blueHand;
  private List<ModelStatusListener> listeners;
  private int rows;
  private int cols;
  private Board board;
  /**
   * instantiating for the mock model.
   */

  public MockSanguineModel() {
    this(false);
  }

  /**
   * instantiating for the mock model.
   */
  public MockSanguineModel(boolean emptyHands) {
    this.currentPlayer = PlayerColor.RED;
    this.gameOver = false;
    this.listeners = new ArrayList<>();
    this.rows = 3;
    this.cols = 3;
    this.board = new Board(rows, cols);

    if (!emptyHands) {
      this.redHand = createMockHand();
      this.blueHand = createMockHand();
    } else {
      this.redHand = new ArrayList<>();
      this.blueHand = new ArrayList<>();
    }
  }

  private List<Card> createMockHand() {
    List<Card> hand = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      hand.add(createMockCard("Card" + i, 1, i + 1));
    }
    return hand;
  }

  private Card createMockCard(String name, int cost, int value) {
    boolean[][] influence = new boolean[5][5];
    influence[2][2] = false; // Center
    influence[2][1] = true;  // Left
    influence[2][3] = true;  // Right
    influence[1][2] = true;  // Up
    influence[3][2] = true;  // Down
    return new Card(name, cost, value, influence);
  }


  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    listeners.add(listener);
  }

  @Override
  public void startGame() {
    notifyTurnStart();
  }

  @Override
  public boolean placeCard(int cardIndex, int row, int col) {
    if (!isLegalMove(cardIndex, row, col)) {
      return false;
    }
    getCurrentHand().remove(cardIndex);
    switchPlayer();
    return true;
  }

  @Override
  public void pass() {
    switchPlayer();
  }

  // ========== ReadOnlySanguineModel methods ==========

  @Override
  public PlayerColor getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public PlayerColor getWinner() {
    if (!gameOver) {
      return null;
    }
    return PlayerColor.RED;
  }

  @Override
  public Board getBoard() {
    return board;
  }

  @Override
  public int getRowScore(int row, PlayerColor player) {
    return 0;
  }

  @Override
  public List<Card> getHand(PlayerColor player) {
    return getPlayerHand(player);
  }

  @Override
  public boolean isLegalPlacement(Card card, Cell cell) {
    if (cell.getContent() != CellContent.PAWNS) {
      return false;
    }
    if (cell.getPlayer() != currentPlayer) {
      return false;
    }
    return cell.getNumPawns() >= card.getCost();
  }

  @Override
  public int getTotalScore(PlayerColor player) {
    return 0;
  }

  @Override
  public boolean isLegalMove(int cardIndex, int row, int col) {
    if (cardIndex < 0 || cardIndex >= getCurrentHand().size()) {
      return false;
    }
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      return false;
    }
    return true;
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
    return board.getCell(row, col).getContent();
  }

  @Override
  public PlayerColor getOwnerOfCell(int row, int col) {
    Cell cell = board.getCell(row, col);
    return cell.getPlayer();
  }

  @Override
  public int getPawnCount(int row, int col) {
    return board.getCell(row, col).getNumPawns();
  }

  @Override
  public Card getCardAt(int row, int col) {
    return board.getCell(row, col).getCard();
  }

  @Override
  public Cell getCell(int row, int col) {
    return board.getCell(row, col);
  }

  @Override
  public List<Card> getPlayerHand(PlayerColor player) {
    return player == PlayerColor.RED ? new ArrayList<>(redHand) : new ArrayList<>(blueHand);
  }

  @Override
  public int getCurrentHandSize() {
    return getCurrentHand().size();
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  // ========== Helper methods ==========

  private void switchPlayer() {
    currentPlayer = currentPlayer.opposite();
    notifyTurnStart();
  }

  private void notifyTurnStart() {
    for (ModelStatusListener listener : listeners) {
      listener.onTurnStart(currentPlayer);
    }
  }

  private List<Card> getCurrentHand() {
    return currentPlayer == PlayerColor.RED ? redHand : blueHand;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public void setCurrentPlayer(PlayerColor player) {
    this.currentPlayer = player;
  }
}