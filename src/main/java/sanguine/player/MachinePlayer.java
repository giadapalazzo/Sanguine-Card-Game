package sanguine.player;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ModelStatusListener;
import sanguine.controller.PlayerActionListener;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.strategy.Move;
import sanguine.strategy.SanguineStrategy;

/**
 * Represents a machine player that uses a strategy to  make moves.
 * Automatically computes and publishes moves when it's their turn.
 */

public class MachinePlayer implements Player, ModelStatusListener {

  private final PlayerColor color;
  private final SanguineStrategy strategy;
  private final ReadOnlySanguineModel model;
  private final List<PlayerActionListener> listeners;

  /**
   * Constructs a  MachinePlayer with the given player color, strategy,
   * and read-only model. Initializes an empty list of action listeners.
   *
   * @param color     the color assigned to this player
   * @param strategy  the strategy used by this machine player to choose moves
   * @param model     the read-only game model this player observes
   */

  public MachinePlayer(PlayerColor color, SanguineStrategy strategy, ReadOnlySanguineModel model) {
    this.color = color;
    this.strategy = strategy;
    this.model = model;
    this.listeners = new ArrayList<>();
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    listeners.add(listener);
  }

  @Override
  public void onTurnStart(PlayerColor currentPlayer) {
    if (currentPlayer == this.color) {
      Move move = strategy.chooseMove(model, color);

      if (move != null) {
        for (PlayerActionListener listener : listeners) {
          listener.cardSelected(move.getCardIndex());
          listener.cellSelected(move.getRow(), move.getCol());
          listener.confirmMove();
        }
      } else {
        for (PlayerActionListener listener : listeners) {
          listener.passTurn();
        }
      }
    }
  }

  @Override
  public void onGameOver(PlayerColor winner, int winningScore) {
    //machine player doesn't need to do anything when game ends
  }
}
