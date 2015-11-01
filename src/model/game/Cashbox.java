package model.game;

import main.Main;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 31.10.15.
 */
public class Cashbox {
    private boolean isFree;
    private double requestHandlingTime;
    private double delta;
    private RandomGenerator generator;

    private List<Customer> queue;

    public Cashbox(double requestHandlingTime, double error) {
        this.isFree = true;
        this.requestHandlingTime = requestHandlingTime;
        generator = new RandomGenerator();

        if (error > 1) {
            error = 1;
        }
        if (error < 0) {
            error = 0;
        }
        delta = requestHandlingTime * error;

        queue = new ArrayList<>(1);
    }

    public boolean isFree() {
        return isFree;
    }

    public void addCustomerToQueue(Customer customer) {
        queue.add(customer);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public double accept(double time) {
        isFree = false;
        queue.get(0).gotOutOfQueue(time);
        double timeToServe = generator.getMiddleTime(requestHandlingTime, delta);
        Main.out.println("\nAccepted.");
        Main.out.println("Time: " + time);
        Main.out.println("Time to serve: " + timeToServe);
        Main.out.println("Queue size: " + queue.size());
        return timeToServe;
    }

    public Customer release() {
        isFree = true;
        return queue.remove(0);
    }

    public int getQueueSize() {
        return queue.size();
    }
}
