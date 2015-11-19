package storageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class Section {
    private Point index;
    private Point size;
    private int level;
    private int id;
    private List<Direction> possibleDirections;

    public Section(Point index, Point size, int level, List<Direction> directionList) {
        this.index = index;
        this.size = size;
        this.level = level;
        this.possibleDirections = new ArrayList<>(directionList);
    }

    public Point getIndex() {
        return index;
    }

    public Point getSize() {
        return size;
    }

    public int getLevel() {
        return level;
    }
}
