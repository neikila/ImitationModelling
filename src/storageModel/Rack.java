package storageModel;

import java.awt.*;

/**
 * Created by neikila on 19.11.15.
 */
public class Rack {
    private Point coordinate;
    private Point size;
    private int levels;
    private double maxWeightPerSection;
    private Point sectionSize;

    public Rack(Point coordinate, Point size, int levels, double maxWeightPerSection, Point sectionSize) {
        this.coordinate = coordinate;
        this.size = size;
        this.levels = levels;
        this.maxWeightPerSection = maxWeightPerSection;
        this.sectionSize = sectionSize;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public Point getSize() {
        return size;
    }

    public int getLevels() {
        return levels;
    }

    public double getMaxWeightPerSection() {
        return maxWeightPerSection;
    }

    public Point getSectionSize() {
        return sectionSize;
    }
}
