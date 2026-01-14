package sanguine.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.MockModel;
import sanguine.model.SanguineModelImpl;

/**
 * tests for the Sanguine text view.
 */
public class SanguineTextViewTests {

  private SanguineModelImpl model;
  private SanguineTextView view;

  /**
   * sets up values before testing.
   */
  @Before
  public void setUp() {
    model = MockModel.createStandardGame();
    view = new SanguineTextView(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new SanguineTextView(null);
  }

  @Test
  public void testToStringNotNull() {
    String output = view.toString();
    assertNotNull("View output should not be null", output);
  }

  @Test
  public void testToStringContainsRows() {
    String output = view.toString();
    String[] lines = output.split("\n");
    assertEquals("Should have 3 rows", 3, lines.length);
  }

  @Test
  public void testInitialBoardShowsPawns() {
    String output = view.toString();
    assertTrue("Should contain 1s for pawns", output.contains("1"));
  }

  @Test
  public void testInitialBoardShowsScores() {
    String output = view.toString();
    assertTrue("Should contain 0s for initial scores", output.contains("0"));
  }

  @Test
  public void testBoardUpdatesAfterPlacement() {
    String before = view.toString();
    model.placeCard(0, 0, 0);
    String after = view.toString();
    assertNotEquals("Board should change after card placement", before, after);
  }

  @Test
  public void testBoardShowsRedCard() {
    model.placeCard(0, 0, 0);
    String output = view.toString();
    assertTrue("Should contain R for red card", output.contains("R"));
  }

  @Test
  public void testBoardShowsBlueCard() {
    model.placeCard(0, 0, 0);
    model.placeCard(0, 0, 4);
    String output = view.toString();
    assertTrue("Should contain B for blue card", output.contains("B"));
  }
}
