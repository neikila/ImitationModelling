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
    private Polygon polygon;

    public Section(Point index, Point size, int level, Direction direction, int id, Polygon polygon) {
        this.id = id;
        this.index = index;
        this.size = size;
        this.level = level;
        this.possibleDirection = direction;
        this.polygon = polygon;
    }

    public Section(Point index, Point size, int level, Direction direction, Polygon polygon) {
        this(index, size, level, direction, lastId++, polygon);
    }

    public Section(Point index, Point size, int level, Polygon polygon) {
        this(index, size, level, null, polygon);
    }

    public Point getCenter() {
        int sumx = 0;
        int sumy = 0;
        for (int i = 0; i < polygon.npoints; ++i) {
            sumx += polygon.xpoints[i];
            sumy += polygon.ypoints[i];
        }
        sumx /= polygon.npoints;
        sumy /= polygon.npoints;
        return new Point(sumx, sumy);
    }

    public Polygon getPolygon() {
        return polygon;
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

    @Override
    public String toString() {
        return "id: " + getId() + "; index[" + getIndex().x + ";" + getIndex().y + ']';
    }
}
