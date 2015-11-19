package storageModel;

import main.XMLParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class Storage {
    private List<Point> points;
    private List<Barrier> barriers;

    public Storage(XMLParser parser) {
        this.points = new ArrayList<>(parser.getWallPoints());
        this.barriers = new ArrayList<>(parser.getBarriers());
    }

    public void printAllWalls() {
        int size = points.size();
        for (int i = 0; i < size; ++i) {
            System.out.format("a = [%d:%d]; b = [%d:%d]%n",
                    points.get(i).x, points.get(i).y,
                    points.get((i + 1) % size).x, points.get((i + 1) % size).y
            );
        }
    }

    public void printAllBarriers() {
        for (Barrier barrier : barriers) {
            System.out.println(barrier);
        }
    }
}
