package game.callables;

import com.caio.middleware.Callable;
import com.caio.middleware.proto.Move;
import game.events.EventsProducer;

public class MoveCallable implements Callable<Move> {
  public void process(Move message) {
    if (Move.Direction.UP.equals(message.getDirection())) {
      EventsProducer.handleMoveUp(message.getPlayer());
    } else {
      EventsProducer.handleMoveDown(message.getPlayer());
    }
  }
}
