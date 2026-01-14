package sanguine.controller;

import sanguine.model.MutableSanguineModel;
import sanguine.model.PlayerColor;
import sanguine.player.MachinePlayer;
import sanguine.player.Player;
import sanguine.view.SanguineView;

/**
 * Controller for the Sanguine Game.
 * Mediates between the model, view, and player on behalf of one player.
 */
public class SanguineController implements CardGameListener,
    PlayerActionListener, ModelStatusListener {

  private final MutableSanguineModel model;
  private final Player player;
  private final SanguineView view;
  private final PlayerColor playerColor;

  private Integer selectedCardIndex;
  private Integer selectedRow;
  private Integer selectedCol;
  private boolean isMyTurn;

  /**
   * Constructor for controller.
   *
   * @param model the game model
   * @param player the player this controller represents
   * @param view the view for this player
   */
  public SanguineController(MutableSanguineModel model, Player player,
                            SanguineView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.playerColor = player.getColor();
    this.isMyTurn = false;

    // Register as listener for all events
    view.addClickListener(this);
    player.addPlayerActionListener(this);
    model.addModelStatusListener(this);
  }

  @Override
  public void onCardSelected(int cardIndex) {
    if (!isMyTurn) {
      System.out.println(playerColor + ": Not my turn, ignoring card selection");
      return;
    }
    System.out.println(playerColor + ": Card selected at index " + cardIndex);
    selectedCardIndex = cardIndex;

    view.setSelectedCard(cardIndex);
    view.refresh();
  }

  @Override
  public void onCellSelected(int row, int col) {
    if (!isMyTurn) {
      System.out.println(playerColor + ": Not my turn, ignoring cell selection");
      return;
    }
    System.out.println(playerColor + ": Cell selected at " + row + "," + col);
    selectedRow = row;
    selectedCol = col;

    // Update view to show selection
    view.setSelectedCell(row, col);
    view.refresh();
  }

  @Override
  public void onConfirmMove() {
    if (!isMyTurn) {
      System.out.println(playerColor + ": Not my turn, ignoring confirm");
      return;
    }

    System.out.println(playerColor + ": Attempting to confirm move");

    if (selectedCardIndex == null || selectedRow == null || selectedCol == null) {
      view.showMessage("Please select a card and a cell before confirming.");
      return;
    }

    try {
      System.out.println(playerColor + ": Placing card " + selectedCardIndex
          + " at " + selectedRow + "," + selectedCol);
      boolean success = model.placeCard(selectedCardIndex, selectedRow, selectedCol);
      if (success) {
        System.out.println(playerColor + ": Move successful!");
        clearSelections();
        view.clearSelections();
        view.refresh();
      } else {
        System.out.println(playerColor + ": Move failed!");
        view.showMessage("Invalid move!");
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println(playerColor + ": Exception: " + e.getMessage());
      view.showMessage("Invalid move: " + e.getMessage());
    }
  }

  @Override
  public void onPass() {
    if (!isMyTurn) {
      System.out.println(playerColor + ": Not my turn, ignoring pass");
      return;
    }

    System.out.println(playerColor + ": Passing turn");
    model.pass();
    clearSelections();
    view.clearSelections(); // Clear visual selection
    view.refresh();
  }


  @Override
  public void cardSelected(int cardIndex) {
    System.out.println(playerColor + ": Machine player selected card " + cardIndex);
    selectedCardIndex = cardIndex;
    view.setSelectedCard(cardIndex);
    view.refresh();
  }

  @Override
  public void cellSelected(int row, int col) {
    System.out.println(playerColor + ": Machine player selected cell " + row + "," + col);
    selectedRow = row;
    selectedCol = col;
    view.setSelectedCell(row, col);
    view.refresh();
  }

  @Override
  public void confirmMove() {
    System.out.println(playerColor + ": Machine player confirming move");
    onConfirmMove();
  }

  @Override
  public void passTurn() {
    System.out.println(playerColor + ": Machine player passing");
    onPass();
  }

  // ===== ModelStatusListener methods (from Model) =====

  @Override
  public void onTurnStart(PlayerColor currentPlayer) {
    isMyTurn = (currentPlayer == playerColor);

    System.out.println(playerColor + ": Turn notification received. Current player: "
        + currentPlayer + ", isMyTurn: " + isMyTurn);

    if (isMyTurn) {
      view.setTitle(playerColor + " - Your Turn");
      clearSelections();
      view.clearSelections();
    } else {
      view.setTitle(playerColor + " - Waiting...");
    }

    view.refresh();

    // If machine player and it's their turn, trigger their move
    if (isMyTurn && player instanceof MachinePlayer) {
      MachinePlayer machinePlayer = (MachinePlayer) player;
    }
  }

  @Override
  public void onGameOver(PlayerColor winner, int winningScore) {
    System.out.println(playerColor + ": Game over! Winner: " + winner);
    String message;
    if (winner == null) {
      message = "Game ended in a tie!";
    } else if (winner == playerColor) {
      message = "You won with " + winningScore + " points!";
    } else {
      message = "You lost. " + winner + " won with " + winningScore + " points.";
    }
    view.showMessage(message);
  }


  private void clearSelections() {
    selectedCardIndex = null;
    selectedRow = null;
    selectedCol = null;
  }
}