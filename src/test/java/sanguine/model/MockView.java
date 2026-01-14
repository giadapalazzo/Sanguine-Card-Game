package sanguine.model;

import sanguine.controller.CardGameListener;
import sanguine.view.SanguineView;

/**
 * Mock view for testing controllers.
 * Tracks method calls and stores values for verification.
 */
public class MockView implements SanguineView {

  private final ReadOnlySanguineModel model;
  private boolean refreshCalled = false;
  private boolean selectionCleared = false;
  private String lastMessage = "";
  private String title = "";
  private int selectedCard = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;
  private CardGameListener listener;

  /**
   * creates model from the Read Only Sanguine Model.
   */
  public MockView(ReadOnlySanguineModel model) {
    this.model = model;
  }

  @Override
  public void setVisible(boolean visible) {
    // Mock implementation - do nothing
  }

  @Override
  public void refresh() {
    refreshCalled = true;
  }

  @Override
  public void addClickListener(CardGameListener listener) {
    this.listener = listener;
  }

  @Override
  public void showMessage(String message) {
    this.lastMessage = message;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public void setSelectedCard(int cardIndex) {
    this.selectedCard = cardIndex;
  }

  @Override
  public void setSelectedCell(int row, int col) {
    this.selectedRow = row;
    this.selectedCol = col;
  }

  @Override
  public void clearSelections() {
    this.selectedCard = -1;
    this.selectedRow = -1;
    this.selectedCol = -1;
    this.selectionCleared = true;
  }

  // Test helper methods

  /**
   * check that refresh was called.
   */
  public boolean wasRefreshCalled() {
    return refreshCalled;
  }

  /**
   * reset the refresh check.
   */
  public void resetRefreshFlag() {
    refreshCalled = false;
  }

  /**
   * checks if the selection has been cleared.
   */
  public boolean wasSelectionCleared() {
    return selectionCleared;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public String getTitle() {
    return title;
  }

  public int getSelectedCard() {
    return selectedCard;
  }

  public int getSelectedRow() {
    return selectedRow;
  }

  public int getSelectedCol() {
    return selectedCol;
  }

  public CardGameListener getListener() {
    return listener;
  }
}