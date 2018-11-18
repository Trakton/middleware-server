package game;

public class GameConstants {
    // Board
    static public final int BOARD_WIDTH = 800;
    static public final int BOARD_HEIGHT = 600;

    //State
    static public final int TO_START_COUNTDOWN = 7;

    //Player
    static public final int PLAYER_SIZE = 40;
    static public final int INITIAL_PLAYER_1_X = PLAYER_SIZE;
    static public final int INITIAL_PLAYER_2_X = BOARD_WIDTH - PLAYER_SIZE * 2;
    static public final int INITIAL_PLAYER_Y = BOARD_HEIGHT / 2 - PLAYER_SIZE / 2;
    static public final int INITIAL_LIVES = 3;
    static public final int PLAYER_SPEED = 12;

    //Bullet
    static public final int BULLET_SPEED = 400;

    //Resources
    static public final String PLAYER_SPRITE = "src/resources/player.png";
    static public final String BULLET_SPRITE = "src/resources/bullet.png";
}
