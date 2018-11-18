package game.events;

import game.GameLoop;
import game.entities.events.Event;
import game.entities.events.EventData;
import game.entities.events.EventTypes;

public class EventsProducer {
    static public void handleMoveUp(int player){
        Event event = new Event(EventTypes.MOVE_UP);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleMoveDown(int player){
        Event event = new Event(EventTypes.MOVE_DOWN);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleFire(int player){
        Event event = new Event(EventTypes.FIRE);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleToStart(){
        Event event = new Event(EventTypes.TO_START);
        GameLoop.events.add(event);
    }

    static public void handleStarted(){
        Event event = new Event(EventTypes.STARTED);
        GameLoop.events.add(event);
    }

    static public void handleOver(int winner){
        Event event = new Event(EventTypes.OVER);
        event.data.put(EventData.WINNER, Integer.toString(winner));
        GameLoop.events.add(event);
    }
}
