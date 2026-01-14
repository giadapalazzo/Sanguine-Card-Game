package sanguine.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sanguine.model.Card;

/**
 * Reads deck configuration files to create cards.
 * This class belongs n the model since it creates model objects (cards)
 * from external data sources.
 */
public class DeckReader {
  /**
   * Reads a deck configuration file and returns a list of cards.
   *
   * @param filePath Path to the configuration file
   * @return List of cards read from the file
   */
  public static List<Card> readDeck(String filePath)
      throws FileNotFoundException {
    File file = new File(filePath);
    Scanner scanner = new Scanner(file);
    List<Card> deck = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();

      String[] parts = line.split(" ");
      if (parts.length != 3) {
        scanner.close();
        throw new IllegalArgumentException("Invalid card header: " + line);
      }
      String name = parts[0];
      int cost = Integer.parseInt(parts[1]);
      int value = Integer.parseInt(parts[2]);
      boolean[][] influence = readInfluenceGrid(scanner);

      deck.add(new Card(name, cost, value, influence));
    }
    scanner.close();
    return deck;

  }
  
  private static boolean[][] readInfluenceGrid(Scanner scanner) {
    boolean[][] grid = new boolean[5][5];

    for (int row = 0; row < 5; row++) {
      if (!scanner.hasNextLine()) {
        throw new IllegalArgumentException("Incomplete influence grid");
      }
      String line = scanner.nextLine().trim();
      if (line.length() != 5) {
        throw new IllegalArgumentException("Invalid grid row: " + line);
      }
      for (int col = 0; col < 5; col++) {
        char c = line.charAt(col);
        if (c == 'I' || c == 'C') {
          grid[row][col] = true;
        } else if (c == 'X') {
          grid[row][col] = false;
        } else {
          throw new IllegalArgumentException("Invalid character: " + c);
        }
      }
    }
    return grid;
  }

}
