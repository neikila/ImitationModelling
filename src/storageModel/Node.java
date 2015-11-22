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
    private double time;
    private Node previous;

    public Node(Point index) {
        previous = null;
        time = 0;
        neighbors = new ArrayList<>();
        this.index = index;
    }

    public boolean setIfLess(double newTime, Node newPrevious) {
        if (newTime < time) {
            time = newTime;
            previous = newPrevious;
            return true;
        }
        return false;
    }

    public double getTime() {
        return time;
    }

    public Node getPrevious() {
        return previous;
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

    public void replaceNeighbor(Node toDelete, Node toAdd) {
        neighbors.remove(toDelete);
        neighbors.add(toAdd);
    }

    public boolean isNeighborTo(Node node) {
        return neighbors.contains(node);
    }

    @Override
    public String toString() {
        return "Node: [" + index.x + ';' + index.y + "] Neighbors: " + neighbors.size();
    }
}
