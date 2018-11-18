package game.events;

import game.GameLoop;
import game.entities.events.Event;
import game.entities.events.EventData;
import game.entities.events.EventTypes;

public class EventsProducer {
    static public void handleMoveUp(int player){
        System.out.println(String.format("[%d] event: MOVE UP", player));
        Event event = new Event(EventTypes.MOVE_UP);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleMoveDown(int player){
        System.out.println(String.format("[%d] event: MOVE DOWN", player));
        Event event = new Event(EventTypes.MOVE_DOWN);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleFire(int player){
        System.out.println(String.format("[%d] event: FIRE", player));
        Event event = new Event(EventTypes.FIRE);
        event.data.put(EventData.PLAYER, Integer.toString(player));
        GameLoop.events.add(event);
    }

    static public void handleToStart(){
        System.out.println("event: TO START");
        Event event = new Event(EventTypes.TO_START);
        GameLoop.events.add(event);
    }

    static public void handleStarted(){
        System.out.println("event: STARTED");
        Event event = new Event(EventTypes.STARTED);
        GameLoop.events.add(event);
    }

    static public void handleOver(int winner){
        System.out.println(String.format("event: OVER winner: [%d]", winner));
        Event event = new Event(EventTypes.OVER);
        event.data.put(EventData.WINNER, Integer.toString(winner));
        GameLoop.events.add(event);
    }

    static public void handleUser(int id, int lives){
        System.out.println(String.format("event: USER id: [%d] lives: [%d]", id, lives));
        Event event = new Event(EventTypes.USER);
        event.data.put(EventData.ID, Integer.toString(id));
        event.data.put(EventData.LIVES, Integer.toString(lives));
        GameLoop.events.add(event);
    }
}
