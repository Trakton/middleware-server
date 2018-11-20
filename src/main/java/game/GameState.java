package game;

public class GameState {

    public GameStates state;
    public int winner;
    public double countdown;

    public GameState(){
        state = GameStates.PENDING;
        countdown = GameConstants.TO_START_COUNTDOWN;
    }

    public void updateCountdown(){
        countdown = Math.max(countdown - GameLoop.deltaTime(), 0);
    }
}
