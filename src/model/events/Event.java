package model.events;

/**
 * Created by neikila on 31.10.15.
 */
public class Event {
    final protected double date;
    final protected EventType eventType;

    public Event(double date, EventType eventType) {
        this.date = date;
        this.eventType = eventType;
    }

    public double getDate() {
        return date;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "date=" + date +
                ", eventType=" + eventType +
                '}';
    }
}
