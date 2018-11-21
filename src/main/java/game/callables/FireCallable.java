package game.callables;

import com.caio.middleware.Callable;
import com.caio.middleware.proto.Fire;
import game.events.EventsProducer;

public class FireCallable implements Callable<Fire> {
  public void process(Fire message) {
    EventsProducer.handleFire(message.getPlayer());
  }
}
