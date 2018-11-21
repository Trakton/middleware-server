package game;

public class GameConstants {
  // Board
  public static final int BOARD_WIDTH = 800;
  public static final int BOARD_HEIGHT = 600;

  // State
  public static final int TO_START_COUNTDOWN = 7;

  // Player
  public static final int PLAYER_SIZE = 40;
  public static final int INITIAL_PLAYER_1_X = PLAYER_SIZE;
  public static final int INITIAL_PLAYER_2_X = BOARD_WIDTH - PLAYER_SIZE * 2;
  public static final int INITIAL_PLAYER_Y = BOARD_HEIGHT / 2 - PLAYER_SIZE / 2;
  public static final int INITIAL_LIVES = 3;
  public static final int PLAYER_SPEED = 12;

  // Bullet
  public static final int BULLET_SPEED = 400;

  // Resources
  public static final String PLAYER_SPRITE = "src/main/resources/player.png";
  public static final String BULLET_SPRITE = "src/main/resources/bullet.png";

  // Topics

  public static final int GAME_STATUS_TOPIC = 1;
  public static final int MOVE_TOPIC = 2;
  public static final int FIRE_TOPIC = 3;
  public static final int USER_TOPIC = 4;
  public static final int SIGN_IN_TOPIC = 5;
}
