package storageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class Node {
    private List<Node> neighbors;
    private Point index;

    public Node(Point index) {
        neighbors = new ArrayList<>();
        this.index = index;
    }

    public Point getIndex() {
        return index;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public boolean addNeighbor(Node node) {
        boolean toAdd = !neighbors.contains(node);
        if (toAdd) {
            neighbors.add(node);
        }
        return toAdd;
    }

    public boolean isNeighborTo(Node node) {
        return neighbors.contains(node);
    }
}
