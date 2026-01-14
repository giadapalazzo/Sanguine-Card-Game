package sanguine.model;

/**
 * Represents a cell in the Sanguine game.
 */
public class Cell {
  private CellContent content;
  private PlayerColor player;
  private int numPawns;
  private Card card;
  private int row;
  private int col;

  /**
   * Constructor to initialize an empty cell.
   */
  public Cell() {
    this.content = CellContent.EMPTY;
    this.player = null;
    this.numPawns = 0;
    this.card = null;
  }

  /**
   * Initializes a pawn based on input.
   *
   * @param player which player this pawn goes to.
   * @param numPawns the number of pawns that go to this player.
   */
  public void initPawn(PlayerColor player, int numPawns) {
    if (numPawns < 1 || numPawns > 3) {
      throw new IllegalArgumentException("Pawn count has to be within 1 and 3");
    }
    this.content = CellContent.PAWNS;
    this.player = player;
    this.numPawns = numPawns;
    this.card = null;
  }

  /**
   * Returns the card in this cell.
   *
   * @return card in cell.
   */
  public Card getCard() {
    return card;
  }

  /**
   * Places a card on this cell, replacing any pawns.
   *
   * @param player the player placing the card
   * @param card the card being placed
   */
  public void placeCard(PlayerColor player, Card card) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card can't be null");
    }
    this.content = CellContent.CARD;
    this.player = player;
    this.numPawns = 0;
    this.card = card;
  }

  /**
   * Adds one pawn to this cell if it contains pawns.
   * Maximum of 3 pawns per cell.
   */
  public void addPawn() {
    if (this.content == CellContent.PAWNS && this.numPawns < 3) {
      this.numPawns++;
    }
  }

  /**
   * Converts the ownership of pawns to a new player.
   *
   * @param newPlayer the player taking ownership
   */
  public void convertPawns(PlayerColor newPlayer) {
    if (this.content == CellContent.PAWNS) {
      this.player = newPlayer;
    }
  }

  /**
   * Returns the value of the card on this cell.
   *
   * @return card value, or 0 if no card
   */
  public int getCardValue() {
    if (this.content == CellContent.CARD && this.card != null) {
      return this.card.getValue();
    }
    return 0;
  }

  /**
   * Returns the content of the cell.
   *
   * @return content of cell.
   */
  public CellContent getContent() {
    return content;
  }

  /**
   * Returns the player of this cell.
   *
   * @return the player of this cell.
   */
  public PlayerColor getPlayer() {
    return player;
  }

  /**
   * Returns the number of pawns on this cell.
   *
   * @return the number of pawns on this cell.
   */
  public int getNumPawns() {
    return numPawns;
  }

  /**
   * Returns the string version of what is in the cell.
   *
   * @return string version of what is in this specific cell.
   */
  public String toSymbol() {
    if (content == CellContent.EMPTY) {
      return "_";
    } else if (content == CellContent.PAWNS) {
      return Integer.toString(this.numPawns);
    } else if (content == CellContent.CARD) {
      if (this.player == PlayerColor.RED) {
        return "R";
      } else {
        return "B";
      }
    }
    return "?";
  }

  /**
   * Returns the string representation for display in the board.
   *
   * @return string representation of the cell
   */
  public String cellString() {
    return toSymbol();
  }

  /**
   * Checks if this cell is empty.
   *
   * @return true if empty, false otherwise
   */
  public boolean isEmpty() {
    return content == CellContent.EMPTY;
  }

  /**
   * Checks if this cell has pawns.
   *
   * @return true  if it has pawns, false otherwise
   */
  public boolean hasPawns() {
    return content == CellContent.PAWNS;
  }

  /**
   * Checks if this cell has a card.
   *
   * @return true if it has card, false otherwise
   */
  public boolean hasCard() {
    return content == CellContent.CARD;
  }

  /**
   * Checks if this cell is owned by the specified player.
   *
   * @param checkPlayer the player to check
   * @return true if owned by player, false otherwise
   */
  public boolean isOwnedBy(PlayerColor checkPlayer) {
    return this.player == checkPlayer
        && (content == CellContent.PAWNS || content == CellContent.CARD);
  }

  /**
   * Creates a deep copy of this cell.
   *
   * @return a new cell with the same state
   */
  public Cell copy() {
    Cell copy = new Cell();
    copy.content = this.content;
    copy.player = this.player;
    copy.numPawns = this.numPawns;
    copy.card = this.card;
    return copy;
  }

  /**
   * Sets position of cell.
   *
   * @param r row of cell.
   * @param c column of cell.
   */
  public void setPosition(int r, int c) {
    this.row = r;
    this.col = c;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
