package sanguine.strategy;

import java.util.List;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Represents a strategy for choosing moves in Sanguine.
 */
public interface SanguineStrategy {
  /**
   * Chooses a move for the given player based on this strategy.
   * Returns null if no valid move exists.
   *
   * @param model the game model to analyze
   * @param player the player to choose a move for
   * @return a Move object, or null if no valid move exists
   */
  Move chooseMove(ReadOnlySanguineModel model, PlayerColor player);
}
