package sanguine.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import sanguine.model.Card;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Panel that displays the current player's hand of cards.
 */
public class HandPanel extends JPanel {

  private final ReadOnlySanguineModel model;
  private final SanguineGameView parentView;
  private static final int CARD_WIDTH = 80;
  private static final int CARD_HEIGHT = 120;
  private static final int CARD_SPACING = 10;
  private static final int INFLUENCE_CELL_SIZE = 10;
  private static final int TOP_MARGIN = 30;

  /**
   * Constructs a new Hand Panel.
   *
   * @param model the read-only model
   *
   * @param parentView the parent view for selection.
   */
  public HandPanel(ReadOnlySanguineModel model, SanguineGameView parentView) {
    this.model = model;
    this.parentView = parentView;
    setBackground(new Color(240, 240, 240));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleClick(e.getX(), e.getY());
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 180);
  }

  private void handleClick(int x, int y) {
    List<Card> hand = model.getPlayerHand(model.getCurrentPlayer());

    for (int i = 0; i < hand.size(); i++) {
      int cardX = 20 + i * (CARD_WIDTH + CARD_SPACING);
      int cardY = TOP_MARGIN + 10;

      if (x > cardX && x < cardX + CARD_WIDTH
          && y > cardY && y < cardY + CARD_HEIGHT) {
        if (parentView.getSelectedCardIndex() == i) {
          parentView.selectCard(-1);
        } else {
          parentView.selectCard(i);
        }
        return;
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    PlayerColor currentPlayer = model.getCurrentPlayer();
    g2d.setFont(new Font("Arial", Font.BOLD, 18));
    g2d.setColor(currentPlayer == PlayerColor.RED ? Color.RED : Color.BLUE);
    g2d.drawString(currentPlayer + "'s Turn", 20, 20);

    List<Card> hand = model.getPlayerHand(currentPlayer);
    for (int i = 0; i < hand.size(); i++) {
      int x = 20 + i * (CARD_WIDTH + CARD_SPACING);
      drawCard(g2d, hand.get(i), x, TOP_MARGIN + 10, i, currentPlayer);

    }
  }

  private void drawCard(Graphics2D g2d, Card card, int x, int y, int index, PlayerColor player) {
    if (index == parentView.getSelectedCardIndex()) {
      g2d.setColor(Color.CYAN);
    } else {
      g2d.setColor(Color.WHITE);
    }
    g2d.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);

    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2));
    g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);

    g2d.setFont(new Font("Arial", Font.BOLD, 12));
    g2d.setColor(Color.BLACK);
    g2d.drawString(card.getName(), x + 5, y + 15);

    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
    g2d.drawString("Cost: " + card.getCost(), x + 10, y + 30);
    g2d.drawString("Value: " + card.getValue(), x + 5, y + 45);

    drawInfluenceGrid(g2d, card, x + 5, y + 55, player);
  }

  private void drawInfluenceGrid(Graphics2D g2d, Card card, int x, int y, PlayerColor player) {
    boolean[][] grid = player == PlayerColor.RED
        ? card.getInfluenceGrid() : card.getMirroredInfluence();

    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        if (grid[r][c]) {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(x + c * INFLUENCE_CELL_SIZE, y + r * INFLUENCE_CELL_SIZE,
              INFLUENCE_CELL_SIZE - 1, INFLUENCE_CELL_SIZE - 1);
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
          g2d.drawRect(x + c * INFLUENCE_CELL_SIZE,
              y + r * INFLUENCE_CELL_SIZE,
              INFLUENCE_CELL_SIZE - 1, INFLUENCE_CELL_SIZE - 1);
        }
      }
    }
  }
}
