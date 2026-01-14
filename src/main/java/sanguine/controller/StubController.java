package sanguine.controller;

import sanguine.model.PlayerColor;

/**
 * stub controller for testing view interactions.
 * Simply prints messages when events occur.
 */
public class StubController implements CardGameListener {


  @Override
  public void onCardSelected(int cardIndex) {
    //message already printed by view

  }

  @Override
  public void onCellSelected(int row, int col) {
    //message already printed by view
  }

  @Override
  public void onConfirmMove() {
    //message already printed by view
  }

  @Override
  public void onPass() {
    //message already printed by view
  }
}
