package sanguine.player;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.PlayerActionListener;
import sanguine.model.PlayerColor;

/**
 * represents a human player who interacts through the view.
 * The view publishes events on its behalf.
 */
public class HumanPlayer implements Player {

  private final PlayerColor color;
  private final List<PlayerActionListener> listeners;

  /**
   * constructs the color of the human player and the listener.
   *
   * @param color color of the human player
   */
  public HumanPlayer(PlayerColor color) {
    this.color = color;
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
}
