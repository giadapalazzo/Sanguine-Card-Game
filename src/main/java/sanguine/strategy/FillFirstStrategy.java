package sanguine.strategy;


import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Strategy that chooses the first valid card and location.
 * Iterates through cards from left to right, then cells top to bottom and left to right.
 */
public class FillFirstStrategy implements SanguineStrategy {

  @Override
  public Move chooseMove(ReadOnlySanguineModel model, PlayerColor player) {
    if (model.isGameOver()) {
      return null;
    }

    int handSize = model.getPlayerHand(player).size();
    for (int cardIndex = 0; cardIndex < handSize; cardIndex++) {
      for (int row = 0; row < model.getRows(); row++) {
        for (int col = 0; col < model.getCols(); col++) {
          if (model.isLegalMove(cardIndex, row, col)) {
            return new Move(cardIndex, row, col);
          }
        }
      }
    }
    return null;
  }
}
