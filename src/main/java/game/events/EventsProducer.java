package game.events;

import com.caio.middleware.MiddlewareException;
import com.caio.middleware.proto.GameStatus;
import com.caio.middleware.proto.User;
import game.Game;
import game.GameConstants;
import game.GameLoop;
import game.entities.events.Event;
import game.entities.events.EventData;
import game.entities.events.EventTypes;
import java.io.IOException;

public class EventsProducer {
  public static void handleMoveUp(int player) {
    System.out.println(String.format("[%d] event: MOVE UP", player));
    Event event = new Event(EventTypes.MOVE_UP);
    event.data.put(EventData.PLAYER, Integer.toString(player));
    GameLoop.events.add(event);
  }

  public static void handleMoveDown(int player) {
    System.out.println(String.format("[%d] event: MOVE DOWN", player));
    Event event = new Event(EventTypes.MOVE_DOWN);
    event.data.put(EventData.PLAYER, Integer.toString(player));
    GameLoop.events.add(event);
  }

  public static void handleFire(int player) {
    System.out.println(String.format("[%d] event: FIRE", player));
    Event event = new Event(EventTypes.FIRE);
    event.data.put(EventData.PLAYER, Integer.toString(player));
    GameLoop.events.add(event);
  }

  public static void handleToStart() throws MiddlewareException, IOException {
    System.out.println("event: TO START");
    Event event = new Event(EventTypes.TO_START);
    GameLoop.events.add(event);

    // Midleware connection
    GameStatus message = GameStatus.newBuilder().setState(GameStatus.State.TO_START).build();
    Game.proxy.getTopic(GameConstants.GAME_STATUS_TOPIC).produce(message);
  }

  public static void handleStarted() throws MiddlewareException, IOException {
    System.out.println("event: STARTED");
    Event event = new Event(EventTypes.STARTED);
    GameLoop.events.add(event);

    // Midleware connection
    GameStatus message =
        GameStatus.newBuilder()
            .setState(GameStatus.State.STARTED)
            .setPlayerOne(Game.playerOneID)
            .setPlayerTwo(Game.playerTwoID)
            .build();
    Game.proxy.getTopic(GameConstants.GAME_STATUS_TOPIC).produce(message);
  }

  public static void handleOver(int winner) throws MiddlewareException, IOException {
    System.out.println(String.format("event: OVER winner: [%d]", winner));
    Event event = new Event(EventTypes.OVER);
    event.data.put(EventData.WINNER, Integer.toString(winner));
    GameLoop.events.add(event);

    // Midleware connection
    GameStatus message =
        GameStatus.newBuilder().setState(GameStatus.State.OVER).setWinner(winner).build();
    Game.proxy.getTopic(GameConstants.GAME_STATUS_TOPIC).produce(message);
  }

  public static void handleUser(int id, int lives) throws MiddlewareException, IOException {
    System.out.println(String.format("event: USER id: [%d] lives: [%d]", id, lives));
    Event event = new Event(EventTypes.USER);
    event.data.put(EventData.ID, Integer.toString(id));
    event.data.put(EventData.LIVES, Integer.toString(lives));
    GameLoop.events.add(event);

    // Midleware connection
    User message = User.newBuilder().setPlayer(id).setLives(lives).build();
    Game.proxy.getTopic(GameConstants.USER_TOPIC).produce(message);
  }
}
