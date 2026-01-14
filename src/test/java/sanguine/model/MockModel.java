package sanguine.model;

import java.util.List;

/**
 * Mock Model for testing purposes.
 * Provides pre-configured game states.
 */
public class MockModel {

  /**
   * Creates a standard 3x5 game ready to play.
   */
  public static SanguineModelImpl createStandardGame() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    return new SanguineModelImpl(3, 5, deck, deck, 5);
  }

  /**
   * Creates a game with varied cards.
   */
  public static SanguineModelImpl createVariedGame() {
    List<Card> deck = MockCard.createVariedDeck(15);
    return new SanguineModelImpl(3, 5, deck, deck, 5);
  }

  /**
   * Creates a small game for quick testing.
   */
  public static SanguineModelImpl createSmallGame() {
    List<Card> deck = MockCard.createSimpleDeck(6);
    return new SanguineModelImpl(2, 3, deck, deck, 2);
  }

  /**
   * Creates a game where red can place cards immediately.
   */
  public static SanguineModelImpl createReadyToPlayGame() {
    List<Card> deck = MockCard.createSimpleDeck(15);
    // All cards cost 1, so red can play on first column immediately
    return new SanguineModelImpl(3, 5, deck, deck, 5);
  }

  /**
   * Creates a game with specific deck configurations.
   */
  public static SanguineModelImpl createGameWithDecks(List<Card> redDeck,
                                                      List<Card> blueDeck,
                                                      int handSize) {
    return new SanguineModelImpl(3, 5, redDeck, blueDeck, handSize);
  }
}