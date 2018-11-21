package game;

import com.caio.middleware.MiddlewareException;
import com.caio.middleware.MiddlewareProxy;
import com.caio.middleware.proto.*;
import game.callables.FireCallable;
import game.callables.MoveCallable;
import game.callables.SignInCallable;
import java.io.IOException;

public class Game {

  public static int playerOneID = -1;
  public static int playerTwoID = -1;

  public static MiddlewareProxy proxy;
  public static GameLoop game;

  public static void main(String[] args) throws IOException, MiddlewareException {

    proxy = new MiddlewareProxy("localhost", 8080, 1);
    proxy.createTopic(GameConstants.SIGN_IN_TOPIC, SignIn.parser()).onMessage(new SignInCallable());
    proxy.createTopic(GameConstants.MOVE_TOPIC, Move.parser()).onMessage(new MoveCallable());
    proxy.createTopic(GameConstants.FIRE_TOPIC, Fire.parser()).onMessage(new FireCallable());
    proxy.createTopic(GameConstants.GAME_STATUS_TOPIC, GameStatus.parser());
    proxy.createTopic(GameConstants.USER_TOPIC, User.parser());
    proxy.start();
  }
}
