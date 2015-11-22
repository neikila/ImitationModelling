package storageModel;

/**
 * Created by neikila on 22.11.15.
 */
public class Product {
    private String name;
    private int id;
    private static int lastId = 0;
    private double weightOfUnit;

    public Product(String name, double weightOfUnit) {
        this.name = name;
        this.id = lastId++;
        this.weightOfUnit = weightOfUnit;
    }

    public Product(String name) {
        this(name, 0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeightOfUnit() {
        return weightOfUnit;
    }
}
