package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ModelStatusListener;

/**
 * Represents the model for the Sanguine game.
 * This class manages the game state, enforces rules, and controls the game flow.
 * It maintains the board, player hands, decks, and notifies listeners of game events.
 */
public class SanguineModelImpl implements MutableSanguineModel {

  private final Board board;
  private final List<Card> redDeck;
  // INVARIANT: redDeck size and redHand size never exceeds initial deck size
  private final List<Card> blueDeck;
  // INVARIANT: blueDeck contains only valid Card objects (non-null)
  private final List<Card> redHand;
  // INVARIANT: Cards in redHand came from redDeck only
  private final List<Card> blueHand;
  // INVARIANT: Cards in blueHand came from blueDeck only
  private PlayerColor currentPlayer;
  // INVARIANT: currentPlayer is never null during active game
  private boolean redPassed;
  private boolean bluePassed;
  private boolean gameOver;
  private final List<ModelStatusListener> listeners;
  // INVARIANT: listeners list never contains null elements


  /**
   * Constructor to initialize the Sanguine game model and deal initial hands.
   *
   * @param rows number of rows on the board
   * @param cols number of columns on the board
   * @param redDeck deck for red player
   * @param blueDeck deck for blue player
   * @param handSize starting hand size for each player
   * @throws IllegalArgumentException if decks are null, hand size is too large,
   *                                  or decks don't have enough cards
   */
  public SanguineModelImpl(int rows, int cols, List<Card> redDeck,
                       List<Card> blueDeck, int handSize) {
    if (redDeck == null || blueDeck == null) {
      throw new IllegalArgumentException("Decks cannot be null");
    }
    if (handSize > redDeck.size() / 3 || handSize > blueDeck.size() / 3) {
      throw new IllegalArgumentException("Hand size too large for deck");
    }
    if (redDeck.size() < rows * cols || blueDeck.size() < rows * cols) {
      throw new IllegalArgumentException("Not enough cards in the deck");
    }
    this.board = new Board(rows, cols);
    this.redDeck = new ArrayList<>(redDeck);
    this.blueDeck = new ArrayList<>(blueDeck);
    this.redHand = new ArrayList<>();
    this.blueHand = new ArrayList<>();
    this.currentPlayer = PlayerColor.RED; // Enforces invariant: currentPlayer is never null
    this.redPassed = false;
    this.bluePassed = false;
    this.gameOver = false;
    this.listeners = new ArrayList<>();

    // Deal initial hands to both players
    for (int i = 0; i < handSize; i++) {
      redHand.add(this.redDeck.remove(0));
      blueHand.add(this.blueDeck.remove(0));
    }
  }

  /**
   * Starts the game by notifying listeners that the first player's turn has begun.
   * This should be called after all controllers/listeners are registered.
   */
  public void startGame() {
    notifyTurnStart(currentPlayer);
  }

  /**
   * Adds a listener to be notified of model status changes (turn changes, game over).
   *
   * @param listener the listener to add
   */
  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    if (listener != null) {
      listeners.add(listener);
    }
  }

  /**
   * Notifies all registered listeners that a player's turn has started.
   *
   * @param color the color of the player whose turn it is
   */
  private void notifyTurnStart(PlayerColor color) {
    for (ModelStatusListener listener : listeners) {
      listener.onTurnStart(color);
    }
  }

  /**
   * Notifies all registered listeners that the game has ended.
   * Calculates the winner and their score before notifying.
   */
  private void notifyGameOver() {
    PlayerColor winner = getWinner();
    int winningScore = winner != null ? getTotalScore(winner) : 0;
    for (ModelStatusListener listener : listeners) {
      listener.onGameOver(winner, winningScore);
    }
  }

  /**
   * Returns the current player.
   *
   * @return current player color
   */
  @Override
  public PlayerColor getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Checks whether the game is over or not.
   *
   * @return true if game is over, otherwise false
   */
  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Returns the board.
   *
   * @return the game board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns the size of the current player's hand.
   *
   * @return the number of cards in the current player's hand
   */
  public int getCurrentHandSize() {
    return getCurrentHand().size();
  }

  /**
   * Attempts to place a card at a specified position.
   * If successful, applies influence, draws a new card, and switches to the next player.
   * If the move causes the game to end, notifies listeners.
   *
   * @param cardIndex index of card in current player's hand
   * @param row row position on board
   * @param col column position on board
   * @return true if placement is successful, false otherwise
   */
  public boolean placeCard(int cardIndex, int row, int col) {
    if (gameOver) {
      return false;
    }
    List<Card> currentHand = getCurrentHand();
    if (cardIndex < 0 || cardIndex >= currentHand.size()) {
      return false;
    }
    Card card = currentHand.get(cardIndex);
    Cell cell = board.getCell(row, col);

    if (!isLegalPlacement(card, cell)) {
      return false;
    }

    currentHand.remove(cardIndex);
    cell.placeCard(currentPlayer, card);
    applyInfluence(card, row, col);

    // Reset pass status for the player who just moved
    if (currentPlayer == PlayerColor.RED) {
      redPassed = false;
    } else {
      bluePassed = false;
    }

    drawCard();

    if (shouldGameEnd()) {
      gameOver = true;
      notifyGameOver();
    } else {
      switchPlayer();
    }

    return true;
  }

  /**
   * Current player passes their turn.
   * If both players pass consecutively, the game ends.
   * Otherwise, draws a card and switches to the other player.
   */
  public void pass() {
    if (gameOver) {
      return;
    }

    if (currentPlayer == PlayerColor.RED) {
      redPassed = true;
    } else {
      bluePassed = true;
    }

    // If both players passed, game is over
    if (redPassed && bluePassed) {
      gameOver = true;
      notifyGameOver();
      return;
    }

    drawCard();
    switchPlayer();
  }

  /**
   * Checks if a card can be legally placed on a cell.
   * A card can be placed if:
   * - The cell contains pawns (not empty or already has a card)
   * - The pawns belong to the current player
   * - There are enough pawns to pay the card's cost
   *
   * @param card the card to place
   * @param cell the cell to place on
   * @return true if legal, false otherwise
   */
  public boolean isLegalPlacement(Card card, Cell cell) {
    if (cell.getContent() != CellContent.PAWNS) {
      return false;
    }
    if (cell.getPlayer() != currentPlayer) {
      return false;
    }
    return cell.getNumPawns() >= card.getCost();
  }

  /**
   * Applies card influence to the board based on the card's influence grid.
   * For red player, uses the card's normal influence grid.
   * For blue player, uses the mirrored influence grid.
   * Influence affects cells in a 5x5 area centered on the placed card.
   *
   * @param card the card being placed
   * @param cardRow row where card is placed
   * @param cardCol column where card is placed
   */
  public void applyInfluence(Card card, int cardRow, int cardCol) {
    boolean[][] influence = currentPlayer == PlayerColor.RED
        ? card.getInfluenceGrid()
        : card.getMirroredInfluence();

    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        if (influence[r][c] && !(r == 2 && c == 2)) {
          int targetRow = cardRow + (r - 2);
          int targetCol = cardCol + (c - 2);

          if (isValidPosition(targetRow, targetCol)) {
            applyInfluenceToCell(targetRow, targetCol);
          }
        }
      }
    }
  }

  /**
   * Applies influence to a specific cell on the board.
   * - Empty cells: Create a new pawn for the current player
   * - Friendly pawns: Add one more pawn
   * - Enemy pawns: Convert all pawns to the current player's color
   * - Cells with cards: No effect
   *
   * @param row row of target cell
   * @param col column of target cell
   */
  private void applyInfluenceToCell(int row, int col) {
    Cell cell = board.getCell(row, col);

    if (cell.getContent() == CellContent.CARD) {
      return;
    } else if (cell.getContent() == CellContent.EMPTY) {
      cell.initPawn(currentPlayer, 1);
    } else if (cell.getContent() == CellContent.PAWNS) {
      if (cell.getPlayer() == currentPlayer) {
        cell.addPawn();
      } else {
        cell.convertPawns(currentPlayer);
      }
    }
  }

  /**
   * Checks if a position is valid on the board.
   *
   * @param row row position
   * @param col column position
   * @return true if position is within board bounds, false otherwise
   */
  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols();
  }

  /**
   * Draws a card from the current player's deck and adds it to their hand.
   * Does nothing if the deck is empty.
   */
  private void drawCard() {
    List<Card> currentDeck = currentPlayer == PlayerColor.RED ? redDeck : blueDeck;
    List<Card> currentHand = getCurrentHand();
    if (!currentDeck.isEmpty()) {
      currentHand.add(currentDeck.remove(0));
    }
  }

  /**
   * Switches to the other player and notifies listeners of the turn change.
   */
  private void switchPlayer() {
    currentPlayer = currentPlayer.opposite();
    notifyTurnStart(currentPlayer);
  }

  /**
   * Gets the current player's hand.
   *
   * @return current player's hand
   */
  private List<Card> getCurrentHand() {
    return currentPlayer == PlayerColor.RED ? redHand : blueHand;
  }

  /**
   * Determines if the game should end due to players running out of cards.
   * Game ends when either player has no cards in hand and no cards in deck.
   *
   * @return true if game should end, false otherwise
   */
  private boolean shouldGameEnd() {
    boolean redEmpty = redHand.isEmpty() && redDeck.isEmpty();
    boolean blueEmpty = blueHand.isEmpty() && blueDeck.isEmpty();
    return redEmpty || blueEmpty;
  }

  /**
   * Calculates and returns the winner of the game.
   * Winner is determined by total score across all rows.
   * A player scores a row if their row score exceeds the opponent's row score.
   *
   * @return winner color, or null if the game is tied or not over
   */
  @Override
  public PlayerColor getWinner() {
    if (!gameOver) {
      return null;
    }
    int redScore = calculateTotalScore(PlayerColor.RED);
    int blueScore = calculateTotalScore(PlayerColor.BLUE);

    if (redScore > blueScore) {
      return PlayerColor.RED;
    } else if (blueScore > redScore) {
      return PlayerColor.BLUE;
    }
    return null;
  }

  /**
   * Calculates the total score for a player across all rows.
   * A player scores a row only if their row score is strictly greater
   * than their opponent's row score in that row.
   *
   * @param player the player to calculate score for
   * @return total score across all rows
   */
  private int calculateTotalScore(PlayerColor player) {
    int totalScore = 0;
    for (int row = 0; row < board.getRows(); row++) {
      int redRowScore = getRowScore(row, PlayerColor.RED);
      int blueRowScore = getRowScore(row, PlayerColor.BLUE);

      // Player only scores this row if they have more points than opponent
      if (player == PlayerColor.RED && redRowScore > blueRowScore) {
        totalScore += redRowScore;
      } else if (player == PlayerColor.BLUE && blueRowScore > redRowScore) {
        totalScore += blueRowScore;
      }
    }
    return totalScore;
  }

  /**
   * Calculates the score for a specific row for a given player.
   * Score is the sum of (card value × (1 + pawn count)) for all cards
   * owned by the player in that row.
   *
   * @param row the row to calculate score for
   * @param player the player to calculate score for
   * @return the player's score in that row
   */
  public int getRowScore(int row, PlayerColor player) {
    int score = 0;
    for (int col = 0; col < board.getCols(); col++) {
      Cell cell = board.getCell(row, col);
      if (cell.getContent() == CellContent.CARD && cell.getPlayer() == player) {
        Card card = cell.getCard();
        if (card != null) {
          // Score = card value × (1 + number of pawns)
          score += card.getValue() * (1 + cell.getNumPawns());
        }
      }
    }
    return score;
  }


  @Override
  public Cell getCell(int row, int col) {
    return board.getCell(row, col);
  }

  @Override
  public List<Card> getPlayerHand(PlayerColor player) {
    List<Card> hand = player == PlayerColor.RED ? redHand : blueHand;
    return new ArrayList<>(hand); // Return defensive copy
  }

  @Override
  public boolean isLegalMove(int cardIndex, int row, int col) {
    if (gameOver) {
      return false;
    }
    List<Card> currentHand = getCurrentHand();
    if (cardIndex < 0 || cardIndex >= currentHand.size()) {
      return false;
    }
    if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols()) {
      return false;
    }
    Card card = currentHand.get(cardIndex);
    Cell cell = board.getCell(row, col);
    return isLegalPlacement(card, cell);
  }

  @Override
  public int getNumRows() {
    return board.getRows();
  }

  @Override
  public int getNumCols() {
    return board.getCols();
  }

  @Override
  public CellContent getCellContent(int row, int col) {
    return board.getCell(row, col).getContent();
  }

  @Override
  public PlayerColor getOwnerOfCell(int row, int col) {
    return board.getCell(row, col).getPlayer();
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
  public List<Card> getHand(PlayerColor player) {
    if (player == PlayerColor.RED) {
      return new ArrayList<>(redHand); // Return defensive copy
    } else {
      return new ArrayList<>(blueHand); // Return defensive copy
    }
  }

  @Override
  public int getTotalScore(PlayerColor player) {
    return calculateTotalScore(player);
  }

  @Override
  public int getRows() {
    return board.getRows();
  }

  @Override
  public int getCols() {
    return board.getCols();
  }
}