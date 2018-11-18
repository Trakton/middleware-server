package game.entities.events;

import java.util.HashMap;

public class Event {
    public EventTypes type;
    public HashMap<EventData, String> data;

    public Event(EventTypes type){
        this.type = type;

        data = new HashMap<EventData, String>();
    }
}
