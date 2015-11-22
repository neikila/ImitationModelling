package storageModel;

/**
 * Created by neikila on 22.11.15.
 */
public class Product {
    private String name;
    private int id;
    private static int lastId = 0;

    public Product(String name) {
        this.name = name;
        this.id = lastId++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
