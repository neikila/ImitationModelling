package model;

import model.events.Event;
import model.events.customEvents.CustomerReleased;
import model.game.Cashbox;
import model.game.Customer;

/**
 * Created by neikila on 31.10.15.
 */
public class Model {
    private Settings settings;
    private EventsQueue queue;
    private double time;

    private Cashbox cashbox;

    public Model(Settings settings) {
        this.settings = settings;
        this.time = 0;
        this.queue = new EventsQueue(settings);

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
        }
        System.out.println("Finished");
    }

    private void dispatchEvent(Event event) {
        Customer customer;
        switch (event.getEventType()) {
            case CustomerIncome:
                // Добавление в очередь
                customer = new Customer(time);
                cashbox.addCustomerToQueue(customer);

                // Проверка, в случае если на кассе никого, то принять человека
                // Предполагаем, что касса пустует тогда и только тогда, когда нет никого в очереди
                if (cashbox.isFree()) {
                    queue.addEvent(new CustomerReleased(time + cashbox.accept()));
                }
                break;
            case CustomerReleased:
                // Освобождение кассы от клиента
                customer = cashbox.release();
                customer.getOutOfQueue(time);
                System.out.println("Customer spent in queue: " + customer.timeSpentInQueue());

                // Если очередь не пуста, то принимаем его на кассе
                if (cashbox.hasNext()) {
                    queue.addEvent(new CustomerReleased(time + cashbox.accept()));
                }
                break;
        }
    }
}
