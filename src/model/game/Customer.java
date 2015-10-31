package model.game;

/**
 * Created by neikila on 01.11.15.
 */
public class Customer {
    // появился в очереди
    private double setInQueue;
    // покинул очередь и начался процесс обслуживания на кассе
    private double gotOutOfQueue;
    // покинул кассу
    private double gotOutOfCashbox;

    public Customer(double setInQueue) {
        this.setInQueue = setInQueue;
    }

    public double getSetInQueue() {
        return setInQueue;
    }

    public void gotOutOfQueue(double time) {
        gotOutOfQueue = time;
    }

    public void gotOutOfCashbox(double time) {
        gotOutOfCashbox = time;
    }

    public double timeSpentInQueue() {
        return gotOutOfQueue - setInQueue;
    }

    public double timeBeingServed() {
        return gotOutOfCashbox - gotOutOfQueue;
    }
}
