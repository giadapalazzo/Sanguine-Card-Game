package sanguine.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.MockSanguineModel;
import sanguine.model.MockView;
import sanguine.model.PlayerColor;
import sanguine.player.HumanPlayer;
import sanguine.player.Player;



/**
 * Tests for the SanguineController class.
 */
public class SanguineControllerTest {

  private MockSanguineModel model;
  private MockView view;
  private Player player;
  private SanguineController controller;

  /**
   * sets up values before testing.
   */
  @Before
  public void setUp() {
    model = new MockSanguineModel();
    view = new MockView(model);
    player = new HumanPlayer(PlayerColor.RED);
    controller = new SanguineController(model, player, view);
  }

  @Test
  public void testControllerInitialization() {
    assertNotNull(controller);
  }

  @Test
  public void testCardSelectionOnMyTurn() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.onCardSelected(0);

    assertTrue(view.wasRefreshCalled());
    assertEquals(0, view.getSelectedCard());
  }

  @Test
  public void testCardSelectionNotMyTurn() {
    controller.onTurnStart(PlayerColor.BLUE);
    view.resetRefreshFlag();

    controller.onCardSelected(0);

    assertFalse(view.wasRefreshCalled());
  }

  @Test
  public void testCellSelectionOnMyTurn() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.onCellSelected(0, 0);

    assertTrue(view.wasRefreshCalled());
    assertEquals(0, view.getSelectedRow());
    assertEquals(0, view.getSelectedCol());
  }

  @Test
  public void testCellSelectionNotMyTurn() {
    controller.onTurnStart(PlayerColor.BLUE);
    view.resetRefreshFlag();

    controller.onCellSelected(0, 0);

    assertFalse(view.wasRefreshCalled());
  }

  @Test
  public void testConfirmMoveWithoutSelections() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onConfirmMove();

    assertTrue(view.getLastMessage().contains("select a card and a cell"));
  }

  @Test
  public void testConfirmMoveWithOnlyCard() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCardSelected(0);
    controller.onConfirmMove();

    assertTrue(view.getLastMessage().contains("select a card and a cell"));
  }

  @Test
  public void testConfirmMoveWithOnlyCell() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCellSelected(0, 0);
    controller.onConfirmMove();

    assertTrue(view.getLastMessage().contains("select a card and a cell"));
  }

  @Test
  public void testConfirmMoveWithBothSelections() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCardSelected(0);
    controller.onCellSelected(0, 0);
    controller.onConfirmMove();

    assertTrue(view.wasSelectionCleared());
  }

  @Test
  public void testPassTurn() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.onPass();

    assertTrue(view.wasRefreshCalled());
  }

  @Test
  public void testPassNotMyTurn() {
    controller.onTurnStart(PlayerColor.BLUE);
    view.resetRefreshFlag();

    controller.onPass();

    assertFalse(view.wasRefreshCalled());
  }

  @Test
  public void testTurnStartUpdatesTitle() {
    controller.onTurnStart(PlayerColor.RED);

    assertTrue(view.getTitle().contains("Your Turn"));

    controller.onTurnStart(PlayerColor.BLUE);

    assertTrue(view.getTitle().contains("Waiting"));
  }

  @Test
  public void testGameOverMessageWin() {
    controller.onGameOver(PlayerColor.RED, 50);

    assertTrue(view.getLastMessage().contains("won"));
    assertTrue(view.getLastMessage().contains("50"));
  }

  @Test
  public void testGameOverMessageLoss() {
    controller.onGameOver(PlayerColor.BLUE, 50);

    assertTrue(view.getLastMessage().contains("lost"));
    assertTrue(view.getLastMessage().contains("BLUE"));
  }

  @Test
  public void testGameOverMessageTie() {
    controller.onGameOver(null, 0);

    // Should handle null winner gracefully
    assertNotNull(view.getLastMessage());
  }

  @Test
  public void testSelectionsAreClearedAfterMove() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCardSelected(0);
    controller.onCellSelected(0, 0);
    controller.onConfirmMove();

    assertTrue(view.wasSelectionCleared());
  }

  @Test
  public void testMultipleCardSelections() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCardSelected(0);
    assertEquals(0, view.getSelectedCard());

    controller.onCardSelected(2);
    assertEquals(2, view.getSelectedCard());
  }

  @Test
  public void testMultipleCellSelections() {
    controller.onTurnStart(PlayerColor.RED);

    controller.onCellSelected(0, 0);
    assertEquals(0, view.getSelectedRow());
    assertEquals(0, view.getSelectedCol());

    controller.onCellSelected(1, 2);
    assertEquals(1, view.getSelectedRow());
    assertEquals(2, view.getSelectedCol());
  }

  @Test
  public void testPlayerActionListenerCardSelected() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.cardSelected(1);

    assertTrue(view.wasRefreshCalled());
    assertEquals(1, view.getSelectedCard());
  }

  @Test
  public void testPlayerActionListenerCellSelected() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.cellSelected(2, 1);

    assertTrue(view.wasRefreshCalled());
    assertEquals(2, view.getSelectedRow());
    assertEquals(1, view.getSelectedCol());
  }

  @Test
  public void testPlayerActionListenerConfirmMove() {
    controller.onTurnStart(PlayerColor.RED);

    controller.cardSelected(0);
    controller.cellSelected(0, 0);
    controller.confirmMove();

    assertTrue(view.wasSelectionCleared());
  }

  @Test
  public void testPlayerActionListenerPass() {
    controller.onTurnStart(PlayerColor.RED);
    view.resetRefreshFlag();

    controller.passTurn();

    assertTrue(view.wasRefreshCalled());
  }
}