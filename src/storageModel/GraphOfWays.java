package storageModel;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neikila on 21.11.15.
 */
public class GraphOfWays {
    public Map<Point, Node> nodes;
    public Point offset;
    public Point step;
    public Point size;

    public GraphOfWays(Storage storage) {
        nodes = new HashMap<>();
        step = storage.getBox();
        offset = new Point(step.x / 2, step.y / 2);
        int left;
        int right;
        int top;
        int bottom;

        List<Point> points = storage.getBoundPoints();
        left = right = points.get(0).x;
        top = bottom = points.get(0).y;

        for (int i = 1; i < points.size(); ++i) {
            Point temp = points.get(i);
            if (temp.x < left)
                left = temp.x;
            if (temp.x > right)
                right = temp.x;
            if (temp.y < bottom)
                bottom = temp.y;
            if (temp.y > top)
                top = temp.y;
        }

        size = new Point((top - bottom) / step.y, (right - left) / step.y);

        for (int y = 0; y < size.y; ++y) {
            for (int x = 0; x < size.x; ++x) {
                Point temp = new Point(offset.x + step.x * x, offset.y + step.y * y);
                if(storage.isEmpty(temp)) {
                    Point point = new Point(x, y);
                    nodes.put(point, new Node(point));
                }
            }
        }
    }

    void createWays() {
        nodes.values().forEach(this::setNeighbor);
    }

    void setNeighbor(Node el) {
        int x = el.getIndex().x;
        int y = el.getIndex().y;
        Node neighbor = null;
        if ((neighbor = nodes.get(new Point(x - 1, y))) != null) {
            el.addNeighbor(neighbor);
        }
        if ((neighbor = nodes.get(new Point(x, y - 1))) != null) {
            el.addNeighbor(neighbor);
        }
    }
}
