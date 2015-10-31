package model.events.customEvents;

import model.events.Event;
import model.events.EventType;

/**
 * Created by neikila on 31.10.15.
 */
public class CustomerReleased extends Event {

    public CustomerReleased(double date) {
        super(date, EventType.CustomerReleased);
    }
}
