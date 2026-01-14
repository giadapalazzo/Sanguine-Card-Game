package sanguine.model;

import java.util.Objects;

/**
 * Represents a card in the Sanguine game.
 * Each card has a name, cost, value, and influence grid that determines
 * how it affects the board when played.
 */
public class Card {


  private final String name;
  private final int cost;
  private final int value;
  private final boolean[][] influenceGrid;

  /**
   * Constructs a Card with the specified attributes.
   *
   * @param name the name of the card (no spaces)
   * @param cost the cost to play the card (must be 1, 2, or 3)
   * @param value the value score of the card (must be positive)
   * @param influenceGrid a 5x5 grid indicating which cells are influenced
   * @throws IllegalArgumentException if cost is not between 1 and 3
   * @throws IllegalArgumentException if value is not positive
   * @throws IllegalArgumentException if influenceGrid is not 5x5
   */
  public Card(String name, int cost, int value, boolean[][] influenceGrid) {
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("Cost must be between 1 and 3");
    }
    if (value < 1) {
      throw new IllegalArgumentException("Value must be positive");
    }
    if (influenceGrid.length != 5 || influenceGrid[0].length != 5) {
      throw new IllegalArgumentException("Influence Grid must be 5 by 5");
    }
    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceGrid = copyGrid(influenceGrid);
  }

  /**
   * Returns the name of this card.
   *
   * @return the card's name
   */
  public String getName() {
    return name;
  }
  /**
   * Returns the cost to play this card.
   * The cost represents the number of pawns required to place this card.
   *
   * @return the card's cost (1, 2, or 3)
   */

  public int getCost() {
    return cost;
  }

  /**
   * Returns the value score of this card.
   * This value is used to calculate row scores and determine the winner.
   *
   * @return the card's value score
   */
  public int getValue() {
    return value;
  }

  /**
   * Checks if this card has influence at the specified position in its grid.
   *
   * @param row the row position in the influence grid (0-4)
   * @param col the column position in the influence grid (0-4)
   * @return true if the card influences that position, false otherwise
   */
  public boolean hasInfluenceAt(int row, int col) {
    return influenceGrid[row][col];
  }

  /**
   * Returns a copy of the influence grid for this card.
   * This is used by the red player to determine which cells are affected
   * when the card is placed.
   *
   * @return a 5x5 boolean array representing the influence grid
   */
  public boolean[][] getInfluenceGrid() {
    return copyGrid(influenceGrid);
  }

  /**
   * Returns a mirrored version of the influence grid.
   * This is used by the blue player, as their influence is mirrored
   * horizontally (across columns) compared to the red player.
   *
   * @return a 5x5 boolean array with columns mirrored
   */
  public boolean[][] getMirroredInfluence() {
    boolean[][] mirrored = new boolean[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        mirrored[r][c] = influenceGrid[r][4 - c];
      }
    }
    return mirrored;
  }
  /**
   * Creates a deep copy of a 5x5 boolean grid.
   * This ensures encapsulation by preventing external modification
   * of the card's internal influence grid.
   *
   * @param grid the grid to copy
   * @return a new grid with the same values
   */

  private boolean[][] copyGrid(boolean[][] grid) {
    boolean[][] copy = new boolean[5][5];
    for (int i = 0; i < 5; i++) {
      System.arraycopy(grid[i], 0, copy[i], 0, 5);
    }
    return copy;
  }

  /**
   * Checks if this card is equal to another object.
   * Two cards are equal if they have the same name, cost, value,
   * and influence grid.
   *
   * @param o the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card card = (Card) o;
    return cost == card.cost && value == card.value
        && name.equals(card.name)
        && gridEquals(influenceGrid, card.influenceGrid);
  }

  private boolean gridEquals(boolean[][] grid1, boolean[][] grid2) {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (grid1[i][j] != grid2[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cost, value);
  }
}

