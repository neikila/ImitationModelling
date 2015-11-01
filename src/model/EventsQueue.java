package model;

import model.events.customEvents.CustomerIncome;
import model.events.Event;
import model.events.EventComparator;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 31.10.15.
 */
public class EventsQueue {
    private List<Event> queue;

    public EventsQueue(Settings settings) {
        this.queue = new ArrayList<>(settings.getLimitSize());

        RandomGenerator generator = new RandomGenerator();
        double temp = 0;
        for (int i = 0; i < settings.getLimitSize(); ++i) {
            queue.add(new CustomerIncome(generator.getTime(temp, settings.getRequestTimeDelta())));
            temp += settings.getRequestTimeDelta();
        }
    }

    public EventsQueue(List <Event> queue) {
        this.queue = new ArrayList<>(queue);
    }

    public void addEvent(Event event) {
        queue.add(event);
        queue.sort(new EventComparator());
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public Event next() {
        return queue.remove(0);
    }
}
