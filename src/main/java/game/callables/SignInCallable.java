package game.callables;

import com.caio.middleware.Callable;
import com.caio.middleware.MiddlewareException;
import com.caio.middleware.proto.SignIn;
import game.Game;
import game.GameLoop;
import game.events.EventsProducer;
import java.io.IOException;

public class SignInCallable implements Callable<SignIn> {
  public void process(SignIn message) {
    if (Game.playerOneID == -1) {
      Game.playerOneID = message.getPlayer();
    } else {
      Game.playerTwoID = message.getPlayer();
      Game.game = new GameLoop(Game.playerOneID, Game.playerTwoID);
      try {
        EventsProducer.handleToStart();
      } catch (MiddlewareException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
