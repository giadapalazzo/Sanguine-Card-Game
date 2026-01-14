package sanguine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import sanguine.controller.DeckReader;
import sanguine.model.Card;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineModelImpl;
import sanguine.view.SanguineTextView;


/**
 * Main entry point for the Sanguine game.
 */
public class Sanguine {
  /**
   * Main method to demonstrate game functionality.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java Sanguine <path-to-deck-config-file>");
      System.out.println("Please provide a deck configuration file path");
      return;
    }

    String configPath = args[0];

    File configFile = new File(configPath);
    if (!configFile.exists()) {
      System.out.println("Error: File not found at " + configPath);
      System.out.println("Please provide a deck configuration file path");
      return;
    }

    try {
      List<Card> deck = DeckReader.readDeck(configPath);

      if (deck.size() < 15) {
        System.out.println("Error: Deck must contain at least 15 cards for a 3x5 board");
        return;
      }

      // Create model and view
      SanguineModelImpl model = new SanguineModelImpl(3, 5, deck, deck, 5);
      SanguineTextView view = new SanguineTextView((SanguineModelImpl) model);

      System.out.println("Initial Board:");
      view.render();
      System.out.println();

      // Play automatically
      playAutomatically(model, view);

      // Show results
      displayGameResult(model);

    } catch (FileNotFoundException e) {
      System.out.println("Error: Could not read file at " + configPath);
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("Error initializing game: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Plays the game until no more moves can be made.
   */
  private static void playAutomatically(SanguineModelImpl model, SanguineTextView view) {
    while (!model.isGameOver()) {
      PlayerColor currentPlayer = model.getCurrentPlayer();
      boolean cardPlaced = tryToPlaceCard(model);

      if (cardPlaced) {
        System.out.println(currentPlayer + " placed a card");
      } else {
        model.pass();
        System.out.println(currentPlayer + " passed");
      }

      view.render();
      System.out.println();
    }
  }

  /**
   * Attempts to place a card for the current player.
   */
  private static boolean tryToPlaceCard(SanguineModelImpl model) {
    for (int cardIdx = 0; cardIdx < model.getHand(model.getCurrentPlayer()).size(); cardIdx++) {
      for (int row = 0; row < model.getBoard().getRows(); row++) {
        for (int col = 0; col < model.getBoard().getCols(); col++) {
          if (model.placeCard(cardIdx, row, col)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Displays the game result.
   */
  private static void displayGameResult(SanguineModelImpl model) {
    System.out.println("=== GAME OVER ===");
    PlayerColor winner = model.getWinner();

    if (winner == null) {
      System.out.println("The game ended in a tie!");
    } else {
      System.out.println("Winner: " + winner);
    }
  }
}
