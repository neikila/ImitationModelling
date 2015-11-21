package storageModel;

import java.awt.*;

/**
 * Created by neikila on 19.11.15.
 */
public class Section {
    static int lastId = 0;
    private Point index;
    private Point size;
    private int level;
    private int id;
    private Direction possibleDirection;

    public Section(Point index, Point size, int level, Direction direction, int id) {
        this.id = id;
        this.index = index;
        this.size = size;
        this.level = level;
        this.possibleDirection = direction;
    }

    public Section(Point index, Point size, int level, Direction direction) {
        this(index, size, level, direction, lastId++);
    }

    public Section(Point index, Point size, int level) {
        this(index, size, level, null);
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

    public Direction getPossibleDirection() {
        return possibleDirection;
    }

    public void setPossibleDirection(Direction possibleDirection) {
        this.possibleDirection = possibleDirection;
    }

    public int getId() {
        return id;
    }
}
