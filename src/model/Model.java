package model;

import analytics.Analytics;
import analytics.Snapshot;
import model.events.Event;
import model.events.customEvents.CustomerReleased;
import model.game.Cashbox;
import model.game.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 31.10.15.
 */
public class Model {
    private Settings settings;
    private EventsQueue queue;
    private double time;

    private Analytics analytics;

    private Cashbox cashbox;
    private List <Customer> customers;

    public Model(Settings settings, Analytics analytics) {
        this.analytics = analytics;
        this.settings = settings;
        this.time = 0;
        this.queue = new EventsQueue(settings);

        this.customers = new ArrayList<>(settings.getLimitSize());
        cashbox = new Cashbox(
                settings.getRequestHandlingTime(),
                settings.getRequestHandlingTimeError());
    }

    public void run() {
        Event currentEvent;
        while (queue.hasNext()) {
            currentEvent = queue.next();
            time = currentEvent.getDate();

            System.out.println();
            System.out.println("Time: " + time);
            System.out.println("Event: " + currentEvent.getEventType());

            dispatchEvent(currentEvent);

            analytics.saveSnapshot(new Snapshot(time, cashbox.getQueueSize()));
        }
        System.out.println("Finished");
    }

    private void dispatchEvent(Event event) {
        Customer customer;
        switch (event.getEventType()) {
            case CustomerIncome:
                // Добавление в очередь
                customer = new Customer(time);
                customers.add(customer);
                cashbox.addCustomerToQueue(customer);

                // Проверка, в случае если на кассе никого, то принять человека
                // Предполагаем, что касса пустует тогда и только тогда, когда нет никого в очереди
                if (cashbox.isFree()) {
                    queue.addEvent(new CustomerReleased(time + cashbox.accept(time)));
                }
                break;
            case CustomerReleased:
                // Освобождение кассы от клиента
                customer = cashbox.release();
                customer.gotOutOfCashbox(time);
                System.out.println("Customer spent in queue: " + customer.timeSpentInQueue());
                System.out.println("Customer was served in " + customer.timeBeingServed());

                // Если очередь не пуста, то принимаем его на кассе
                if (cashbox.hasNext()) {
                    queue.addEvent(new CustomerReleased(time + cashbox.accept(time)));
                }
                break;
        }
    }
}
