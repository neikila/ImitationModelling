package model.events;

/**
 * Created by neikila on 31.10.15.
 */
public class CustomerIncome extends Event {

    public CustomerIncome(double date) {
        super(date, EventType.CustomerIncome);
    }
}
