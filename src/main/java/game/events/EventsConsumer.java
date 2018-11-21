package game.events;

import game.GameLoop;
import game.GameStates;
import game.entities.events.Event;
import game.entities.events.EventData;

public class EventsConsumer {
  public static void consume() {
    Event event = GameLoop.events.peek();

    if (event == null) return;

    GameLoop.events.remove();

    switch (event.type) {
      case MOVE_UP:
        handleMoveUp(event);
        break;
      case MOVE_DOWN:
        handleMoveDown(event);
        break;
      case FIRE:
        handleFire(event);
        break;
      case TO_START:
        handleToStart(event);
        break;
      case STARTED:
        handleStarted(event);
        break;
      case OVER:
        handleOver(event);
        break;
      case USER:
        handleUser(event);
        break;
      default:
        break;
    }
  }

  static void handleMoveUp(Event event) {
    int player = Integer.parseInt(event.data.get(EventData.PLAYER));
    GameLoop.getPlayer(player).moveUp();
  }

  static void handleMoveDown(Event event) {
    int player = Integer.parseInt(event.data.get(EventData.PLAYER));
    GameLoop.getPlayer(player).moveDown();
  }

  static void handleFire(Event event) {
    int player = Integer.parseInt(event.data.get(EventData.PLAYER));
    GameLoop.getPlayer(player).fire();
  }

  static void handleToStart(Event event) {
    GameLoop.state.state = GameStates.TO_START;
  }

  static void handleStarted(Event event) {
    GameLoop.state.state = GameStates.STARTED;
  }

  static void handleOver(Event event) {
    int winner = Integer.parseInt(event.data.get(EventData.WINNER));
    GameLoop.state.winner = winner;
    GameLoop.state.state = GameStates.OVER;
  }

  static void handleUser(Event event) {
    int id = Integer.parseInt(event.data.get(EventData.ID));
    int lives = Integer.parseInt(event.data.get(EventData.LIVES));
    GameLoop.getPlayer(id).lives = lives;
  }
}
