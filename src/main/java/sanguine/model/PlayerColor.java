package sanguine.model;

/**
 * represents the two possible colors fo the players.
 */
public enum PlayerColor {
  RED,
  BLUE;

  /**
   * returns the opposite color that the player has.
   *
   * @return opposite color that player has.
   *
   */
  public PlayerColor opposite() {
    return this == RED ? BLUE : RED;
  }
}
