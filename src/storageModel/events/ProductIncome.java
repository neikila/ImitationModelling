package storageModel.events;

import storageModel.Product;

/**
 * Created by neikila on 22.11.15.
 */
public class ProductIncome extends Event {
    private Product product;
    private double weight;

    public ProductIncome(double date, EventType eventType, Product product, double weight) {
        super(date, eventType);
        this.product = product;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Product getProduct() {
        return product;
    }
}
