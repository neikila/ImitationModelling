package storageModel.storageDetails;

import storageModel.Direction;
import storageModel.Product;

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
    private Product product;
    private int amount;
    private double maxWeightOfSection;

    public Section(Point index, Point size, int level, Direction direction,
                   int id, Polygon polygon, double maxWeightOfSection) {
        this.id = id;
        this.index = index;
        this.size = size;
        this.level = level;
        this.possibleDirection = direction;
        this.polygon = polygon;
        this.product = null;
        this.amount = 0;
        this.maxWeightOfSection = maxWeightOfSection;
    }

    public Section(Point index, Point size, int level, Direction direction, Polygon polygon, double maxWeightOfSection) {
        this(index, size, level, direction, lastId++, polygon, maxWeightOfSection);
    }

    public Section(Point index, Point size, int level, Polygon polygon, double maxWeightOfSection) {
        this(index, size, level, null, polygon, maxWeightOfSection);
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

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public double getMaxWeightOfSection() {
        return maxWeightOfSection;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProduct(int amount) {
        if (this.amount > amount) {
            this.amount -= amount;
            return amount;
        } else {
            amount = this.amount;
            this.amount = 0;
            product = null;
            return amount;
        }
    }

    public boolean isAcceptable(Product product, double weight) {
        return ((this.product == null || this.product == product) &&
                (maxWeightOfSection - product.getWeightOfUnit() * amount) > weight);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "id: " + getId() + "; index[" + getIndex().x + ";" + getIndex().y + ']';
    }
}
