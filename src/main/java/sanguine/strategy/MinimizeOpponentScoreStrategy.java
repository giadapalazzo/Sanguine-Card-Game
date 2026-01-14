package sanguine.strategy;

import java.util.List;
import sanguine.model.Card;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Strategy that tries to minimize the opponent's score.
 * Evaluates moves based on how much they prevent the opponent from scoring.
 */
public class MinimizeOpponentScoreStrategy implements SanguineStrategy {

  @Override
  public Move chooseMove(ReadOnlySanguineModel model, PlayerColor player) {
    List<Card> hand = model.getPlayerHand(player);

    if (hand.isEmpty()) {
      return null;
    }

    Move bestMove = null;
    int lowestOpponentScore = Integer.MAX_VALUE;

    PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;

    for (int cardIdx = 0; cardIdx < hand.size(); cardIdx++) {
      Card card = hand.get(cardIdx);

      for (int row = 0; row < model.getNumRows(); row++) {
        for (int col = 0; col < model.getNumCols(); col++) {

          if (model.isLegalMove(cardIdx, row, col)) {


            int opponentScoreInRow = calculateOpponentScoreInRow(
                model, row, opponent, card, col);

            if (opponentScoreInRow < lowestOpponentScore) {
              lowestOpponentScore = opponentScoreInRow;
              bestMove = new Move(cardIdx, row, col);
            }
          }
        }
      }
    }

    return bestMove;
  }

  /**
   * Calculates what the opponent's score would be in a row after placing a card.
   * This is a simulation - we don't actually modify the model.
   *
   * @param model the game model
   * @param row the row to evaluate
   * @param opponent the opponent's color
   * @param cardToPlace the card we're considering placing
   * @param col the column where we'd place it
   * @return the opponent's projected score in that row
   */
  private int calculateOpponentScoreInRow(ReadOnlySanguineModel model,
                                          int row,
                                          PlayerColor opponent,
                                          Card cardToPlace,
                                          int col) {
    int opponentScore = 0;

    // Count opponent's cards and pawns in this row
    for (int c = 0; c < model.getNumCols(); c++) {
      if (c == col) {

        continue;
      }

      PlayerColor cellOwner = model.getOwnerOfCell(row, c);

      if (cellOwner == opponent) {
        Card opponentCard = model.getCardAt(row, c);
        int pawnCount = model.getPawnCount(row, c);

        if (opponentCard != null) {
          opponentScore += opponentCard.getValue() * (1 + pawnCount);
        }
      }
    }

    return opponentScore;
  }
}
