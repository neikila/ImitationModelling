package storageModel;

import java.awt.*;

/**
 * Created by neikila on 23.11.15.
 */
public class Worker {
    private static int lastId = 0;
    private int id;
    private Point position;
    private boolean isFree;

    public Worker(Point position) {
        id = lastId++;
        this.position = position;
        isFree = true;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isFree() {
        return isFree;
    }

    public void action() {
        isFree = false;
    }
}
