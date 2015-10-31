package model.game;

/**
 * Created by neikila on 01.11.15.
 */
public class Customer {
    private double setInQueue;
    private double getOutOfQueue;

    public Customer(double setInQueue) {
        this.setInQueue = setInQueue;
    }

    public double getSetInQueue() {
        return setInQueue;
    }

    public void getOutOfQueue(double time) {
        getOutOfQueue = time;
    }

    public double timeSpentInQueue() {
        return getOutOfQueue - setInQueue;
    }
}
