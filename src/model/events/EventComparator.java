package model.events;

import java.util.Comparator;

/**
 * Created by neikila on 31.10.15.
 */
public class EventComparator implements Comparator <Event> {

    @Override
    public int compare(Event o1, Event o2) {
        if (o1.date < o2.date) {
            return -1;
        } else {
            if (o1.date == o2.date) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
