package sanguine.model;

/**
 * Mock Card for testing purposes.
 * Provides easy card creation without file reading.
 */
public class MockCard {

  /**
   * Creates a simple card with cost 1, value 1, and no influence.
   */
  public static Card createSimpleCard() {
    boolean[][] noInfluence = new boolean[5][5];
    return new Card("Simple", 1, 1, noInfluence);
  }

  /**
   * Creates a card with specified cost and value, no influence.
   */
  public static Card createCard(String name, int cost, int value) {
    boolean[][] noInfluence = new boolean[5][5];
    return new Card(name, cost, value, noInfluence);
  }

  /**
   * Creates a card with cross-shaped influence pattern.
   */
  public static Card createCrossInfluenceCard() {
    boolean[][] crossInfluence = new boolean[5][5];
    crossInfluence[2][2] = true; // Center (card position)
    crossInfluence[1][2] = true; // Up
    crossInfluence[3][2] = true; // Down
    crossInfluence[2][1] = true; // Left
    crossInfluence[2][3] = true; // Right
    return new Card("Cross", 1, 2, crossInfluence);
  }

  /**
   * Creates a card with diagonal influence pattern.
   */
  public static Card createDiagonalInfluenceCard() {
    boolean[][] diagInfluence = new boolean[5][5];
    diagInfluence[2][2] = true; // Center
    diagInfluence[1][1] = true; // Top-left
    diagInfluence[1][3] = true; // Top-right
    diagInfluence[3][1] = true; // Bottom-left
    diagInfluence[3][3] = true; // Bottom-right
    return new Card("Diagonal", 2, 3, diagInfluence);
  }

  /**
   * Creates a card with right-side influence (for red player testing).
   */
  public static Card createRightInfluenceCard() {
    boolean[][] rightInfluence = new boolean[5][5];
    rightInfluence[2][2] = true; // Center
    rightInfluence[2][3] = true; // Right
    rightInfluence[2][4] = true; // Far right
    return new Card("Right", 1, 2, rightInfluence);
  }

  /**
   * Creates a high-cost, high-value card.
   */
  public static Card createPowerCard() {
    boolean[][] wideInfluence = new boolean[5][5];
    for (int r = 1; r <= 3; r++) {
      for (int c = 1; c <= 3; c++) {
        wideInfluence[r][c] = true;
      }
    }
    return new Card("Power", 3, 6, wideInfluence);
  }

  /**
   * Creates a deck with specified number of simple cards.
   */
  public static java.util.List<Card> createSimpleDeck(int size) {
    java.util.List<Card> deck = new java.util.ArrayList<>();
    for (int i = 0; i < size; i++) {
      deck.add(createCard("Card" + i, 1, 1));
    }
    return deck;
  }

  /**
   * Creates a varied deck with different costs and values.
   */
  public static java.util.List<Card> createVariedDeck(int size) {
    java.util.List<Card> deck = new java.util.ArrayList<>();
    for (int i = 0; i < size; i++) {
      int cost = (i % 3) + 1; // Cycles through 1, 2, 3
      int value = i + 1;
      deck.add(createCard("Card" + i, cost, value));
    }
    return deck;
  }
}