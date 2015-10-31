package analytics;

import model.game.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 01.11.15.
 */
public class Analytics {
    private List <Snapshot> snapshots;
    private double averageWaitingInQueueTime;
    private double averageServingTime;

    public Analytics() {
        snapshots = new ArrayList<>();
    }

    public void saveSnapshot(Snapshot snapshot) {
        snapshots.add(snapshot);
    }

    public void countAverageWaitingInQueueTime(List <Customer> customers) {
        averageWaitingInQueueTime = 0;
        for (Customer customer: customers) {
            averageWaitingInQueueTime += customer.timeSpentInQueue();
        }
        averageWaitingInQueueTime /= customers.size();
    }

    public void countAverageServingTime(List <Customer> customers) {
        averageServingTime = 0;
        for (Customer customer: customers) {
            averageServingTime += customer.timeBeingServed();
        }
        averageServingTime /= customers.size();
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public double getAverageWaitingInQueueTime() {
        return averageWaitingInQueueTime;
    }

    public double getAverageServingTime() {
        return averageServingTime;
    }
}
