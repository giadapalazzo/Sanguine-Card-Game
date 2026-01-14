package sanguine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import sanguine.model.Cell;
import sanguine.model.PlayerColor;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Panel that displays the game board with cells and row scores.
 */
public class BoardPanel extends JPanel {

  private final ReadOnlySanguineModel model;
  private final SanguineGameView parentView;
  private static final int CELL_SIZE = 60;

  /**
   * Constructs a new Board Panel.
   *
   * @param model the read-only model
   * @param parentView the parent view for selection
   */
  public BoardPanel(ReadOnlySanguineModel model, SanguineGameView parentView) {
    this.model = model;
    this.parentView = parentView;

    int width = model.getCols() *  CELL_SIZE + 100;
    int height = model.getRows() * CELL_SIZE + 50;
    setPreferredSize(new Dimension(width, height));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleClick(e.getX(), e.getY());
      }
    });
  }

  /**
   * Handles mouse clicks on board cells.
   *
   *
   * @param x the x-coordinate of the click
   *
   * @param y the y-coordinate of the click
   *
   */
  private void handleClick(int x, int y) {
    int row = (y - 25) / CELL_SIZE;
    int col = (x - 50) / CELL_SIZE;

    if (row >= 0 && row < model.getRows()
        && col >= 0 && col < model.getCols()) {
      if (parentView.getSelectedRow() == row
          && parentView.getSelectedCol() == col) {
        parentView.selectCell(-1, -1);
      } else {
        parentView.selectCell(row, col);
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics g2d = (Graphics2D) g;

    drawBoard((Graphics2D) g2d);
    drawRowScores((Graphics2D) g2d);

  }

  private void drawBoard(Graphics2D g2d) {
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        drawCell(g2d, row, col);
      }
    }
  }

  private void drawCell(Graphics2D g2d, int row, int col) {
    int x = 50 + col * CELL_SIZE;
    int y = 25 + row * CELL_SIZE;

    final Cell cell = model.getCell(row, col);

    if (row == parentView.getSelectedRow()
        && col == parentView.getSelectedCol()) {
      g2d.setColor(Color.CYAN);
    } else {
      g2d.setColor(Color.WHITE);
    }
    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);

    drawCellContent(g2d, cell, x, y);
  }

  private void drawCellContent(Graphics2D g2d, Cell cell, int x, int y) {

    if (cell.hasCard()) {
      g2d.setColor(cell.getPlayer() == PlayerColor.RED ? Color.RED : Color.BLUE);
      g2d.fillRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
      g2d.setColor(Color.WHITE);
      g2d.drawString(String.valueOf(cell.getCardValue()),
          x + CELL_SIZE / 2, y + CELL_SIZE / 2);
    } else if (cell.hasPawns()) {
      g2d.setColor(cell.getPlayer() == PlayerColor.RED ? Color.RED : Color.BLUE);
      g2d.drawString(String.valueOf(cell.getNumPawns()),
          x + CELL_SIZE / 2, y + CELL_SIZE / 2);
    }
  }

  private void drawRowScores(Graphics2D g2d) {
    for (int row = 0; row < model.getRows(); row++) {
      int y = 25 + row * CELL_SIZE + CELL_SIZE / 2;

      g2d.setColor(Color.RED);
      int redScore = model.getRowScore(row, PlayerColor.RED);
      g2d.drawString(String.valueOf(redScore), 10, y);

      g2d.setColor(Color.BLUE);
      int blueScore = model.getRowScore(row, PlayerColor.BLUE);
      int x = 50 + model.getCols() * CELL_SIZE + 10;
      g2d.drawString(String.valueOf(blueScore), x, y);
    }
  }

}
