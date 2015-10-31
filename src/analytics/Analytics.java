package analytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 01.11.15.
 */
public class Analytics {
    private List <Snapshot> snapshots;

    public Analytics() {
        snapshots = new ArrayList<>();
    }

    public void saveSnapshot(Snapshot snapshot) {
        snapshots.add(snapshot);
    }

    public double getAverageWaitingInQueue() {
        return 0;
    }
}
