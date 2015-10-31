package analytics;

/**
 * Created by neikila on 01.11.15.
 */
public class Snapshot {
    private double time;
    private double queueSize;

    public double getTime() {
        return time;
    }

    public double getQueueSize() {
        return queueSize;
    }

    public Snapshot(double time, double queueSize) {

        this.time = time;
        this.queueSize = queueSize;
    }
}
