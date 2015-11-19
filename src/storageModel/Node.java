package storageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class Node {
    public List<Node> neighbors;
    public Point index;
    public List<Integer> willBeBlockedAfter;

    public Node(Point index) {
        this.index = index;
        neighbors = new ArrayList<>();
        willBeBlockedAfter = new LinkedList<>();
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
