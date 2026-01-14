package sanguine;

import java.io.FileNotFoundException;
import java.util.List;
import sanguine.controller.DeckReader;
import sanguine.controller.SanguineController;
import sanguine.model.Card;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineModelImpl;
import sanguine.player.HumanPlayer;
import sanguine.player.MachinePlayer;
import sanguine.player.Player;
import sanguine.strategy.FillFirstStrategy;
import sanguine.strategy.MaximizeRowScoreStrategy;
import sanguine.strategy.MinimizeOpponentScoreStrategy;
import sanguine.view.SanguineGameView;

/**
 * Main entry point for the Sanguine game GUI.
 * Creates a model and view for visual gameplay.
 */
public final class SanguineGame {
  /**
   * Main method to launch the game.
   *
   * @param args command line arguments
   *      [0] number of rows
   *      [1] number of columns
   *      [2] path to Red's deck file
   *      [3] path to Blue's deck file
   *      [4] Red player type (human/strategy1/strategy2/strategy3)
   *      [5] Blue player type (human/strategy1/strategy2/strategy3)
   */
  public static void main(String[] args) {
    if (args.length != 6) {
      System.err.println("Usage: java -jar sanguine.jar <rows> <cols> "
          + "<redDeck> <blueDeck> <redPlayer> <bluePlayer>");
      System.err.println("Player types: human, strategy1, strategy2, strategy3");
      System.exit(1);
    }
    try {
      int rows = Integer.parseInt(args[0]);
      int cols = Integer.parseInt(args[1]);
      String redDeckPath = args[2];
      String blueDeckPath = args[3];
      String redPlayerType = args[4];
      String bluePlayerType = args[5];

      List<Card> redDeck = DeckReader.readDeck(redDeckPath);
      List<Card> blueDeck = DeckReader.readDeck(blueDeckPath);
      int handSize = 5;

      SanguineModelImpl model = new SanguineModelImpl(rows, cols, redDeck, blueDeck, handSize);
      SanguineGameView viewRed = new SanguineGameView(model);
      SanguineGameView viewBlue = new SanguineGameView(model);

      viewRed.setLocation(100, 100);
      viewBlue.setLocation(800, 100);

      Player playerRed = createPlayer(redPlayerType, PlayerColor.RED, model);
      Player playerBlue = createPlayer(bluePlayerType, PlayerColor.BLUE, model);


      SanguineController controllerRed = new SanguineController(
          model, playerRed, viewRed);
      SanguineController controllerBlue = new SanguineController(
          model, playerBlue, viewBlue);

      viewRed.setVisible(true);
      viewBlue.setVisible(true);
      model.startGame();

    } catch (FileNotFoundException e) {
      System.err.println("Error: Could not find deck configuration file");
      e.printStackTrace();
      System.exit(1);
    } catch (NumberFormatException e) {
      System.err.println("Error: Rows and columns must be valid integers");
      e.printStackTrace();
      System.exit(1);
    } catch (Exception e) {
      System.err.println("Error initializing game: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static Player createPlayer(String playerType, PlayerColor color,
                                     SanguineModelImpl model) {
    Player player;

    switch (playerType.toLowerCase()) {
      case "human":
        player = new HumanPlayer(color);
        break;
      case "strategy1":
        player = new MachinePlayer(color, new FillFirstStrategy(), model);
        break;
      case "strategy2":
        player = new MachinePlayer(color, new MaximizeRowScoreStrategy(), model);
        break;
      case "strategy3":
        player = new MachinePlayer(color, new MinimizeOpponentScoreStrategy(), model);
        break;
      default:
        System.err.println("Unknown player type: " + playerType + ". Defaulting to human");
        player = new HumanPlayer(color);
        break;
    }

    // Register MachinePlayer as a listener to the model
    if (player instanceof MachinePlayer) {
      model.addModelStatusListener((MachinePlayer) player);
    }

    return player;
  }
}
