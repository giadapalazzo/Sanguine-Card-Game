package sanguine.view;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sanguine.controller.CardGameListener;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Main GUI view for the Sanguine game.
 * Implements all listeners in a single class
 */
public class SanguineGameView extends javax.swing.JFrame implements SanguineView {

  private final ReadOnlySanguineModel model;
  private HandPanel handPanel;
  private BoardPanel boardPanel;
  private CardGameListener listener;
  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Constructs a new Sanguine Game View.
   *
   * @param model the read-only model to display
   */
  public SanguineGameView(ReadOnlySanguineModel model) {
    this.model = model;
    setTitle("Sanguine Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    initComponents();
    pack();
  }

  /**
   * Initializes all GUI components.
   */
  private void initComponents() {
    handPanel = new HandPanel(model, this);
    boardPanel = new BoardPanel(model, this);

    add(handPanel, BorderLayout.NORTH);
    add(boardPanel, BorderLayout.CENTER);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        handleKeyPress(e);
      }
    });
    setFocusable(true);
  }

  /**
   * Handles keyboard input for confirm and pass actions.
   *
   * @param e the key event
   */
  private void handleKeyPress(KeyEvent e) {
    if (listener != null) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        listener.onConfirmMove();
        System.out.println("Key press: Confirm");
      } else if (e.getKeyCode() == KeyEvent.VK_P) {
        listener.onPass();
        System.out.println("Key press: Pass");
      }
    }
  }

  /**
   * Selects a card from the current player's hand.
   *
   * @param index the index of the card to select
   */
  public void selectCard(int index) {
    selectedCardIndex = index;
    // DON'T reset row/col when selecting a card - we need both!
    if (listener != null) {
      listener.onCardSelected(index);
      System.out.println("Card selected at index: " + index
          + ", player: " + model.getCurrentPlayer());
    }
    refresh();
  }

  /**
   * Selects a cell on the game board.
   *
   * @param row the row of the cell to select
   * @param col the column of the cell to select
   */
  public void selectCell(int row, int col) {
    selectedRow = row;
    selectedCol = col;
    // DON'T reset card index when selecting a cell - we need both!
    if (listener != null) {
      listener.onCellSelected(row, col);
      System.out.println("Cell Selected: " + row + ", " + col);
    }
    refresh();
  }

  public int getSelectedCardIndex() {
    return selectedCardIndex;
  }

  public int getSelectedRow() {
    return selectedRow;
  }

  public int getSelectedCol() {
    return selectedCol;
  }

  @Override
  public void refresh() {
    if (handPanel != null) {
      handPanel.repaint();
    }
    if (boardPanel != null) {
      boardPanel.repaint();
    }
    repaint();
  }

  @Override
  public void addClickListener(CardGameListener listener) {
    this.listener = listener;
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Sanguine Game",
        JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
  }

  @Override
  public void setSelectedCard(int cardIndex) {
    this.selectedCardIndex = cardIndex;
    refresh();
  }

  @Override
  public void setSelectedCell(int row, int col) {
    this.selectedRow = row;
    this.selectedCol = col;
    refresh();
  }

  @Override
  public void clearSelections() {
    this.selectedCardIndex = -1;
    this.selectedRow = -1;
    this.selectedCol = -1;
    refresh();
  }
}