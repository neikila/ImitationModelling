package model;

import model.events.Event;

/**
 * Created by neikila on 31.10.15.
 */
public class Model {
    private Settings settings;
    private EventsQueue queue;
    private double time;

    public Model(Settings settings) {
        this.settings = settings;
        this.time = 0;
        this.queue = new EventsQueue(settings);
    }

    public void run() {
        Event currentEvent;
        while (queue.hasNext()) {
            currentEvent = queue.next();

        }
    }

    private void dispatchEvent(Event event) {

    }
}
