package sanguine.player;

import sanguine.controller.PlayerActionListener;
import sanguine.model.PlayerColor;

/**
 * Interface to represent a player.
 */
public interface Player {

  /**
   * Returns the player color.
   */
  PlayerColor getColor();

  /**
   * Gives the player a listener object.
   */
  void addPlayerActionListener(PlayerActionListener listener);
}