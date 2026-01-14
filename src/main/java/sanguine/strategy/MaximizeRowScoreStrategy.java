package sanguine.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Card;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Strategy that tries to win rows by maximizing row scores.
 * Visits rows from top to bottom and attempts to make the current
 * player's row score greater than the opponent's.
 */
public class MaximizeRowScoreStrategy implements SanguineStrategy {

  @Override
  public Move chooseMove(ReadOnlySanguineModel model, PlayerColor player) {
    if (model.isGameOver()) {
      return null;
    }

    PlayerColor opponent = player.opposite();
    int handSize = model.getPlayerHand(player).size();

    for (int row = 0; row < model.getRows(); row++) {
      int currentRowScore = model.getRowScore(row, player);
      int opponentRowScore = model.getRowScore(row, opponent);

      if (currentRowScore <= opponentRowScore) {
        Move winningMove = findMoveToWinRow(
            model, player, opponent, row, handSize);
        if (winningMove != null) {
          return winningMove;
        }
      }
    }
    return null;
  }

  private Move findMoveToWinRow(ReadOnlySanguineModel model, PlayerColor player,
                                PlayerColor opponent, int row, int handSize) {
    int opponentRowScore = model.getRowScore(row, opponent);

    for (int cardIndex = 0; cardIndex < handSize; cardIndex++) {
      Card card = model.getPlayerHand(player).get(cardIndex);

      for (int col = 0; col < model.getCols(); col++) {
        if (model.isLegalMove(cardIndex, row, col)) {
          int currentRowScore = model.getRowScore(row, player);
          int newRowScore = currentRowScore + card.getValue();

          if (newRowScore > opponentRowScore) {
            return new Move(cardIndex, row, col);
          }
        }
      }
    }
    return null;
  }
}
