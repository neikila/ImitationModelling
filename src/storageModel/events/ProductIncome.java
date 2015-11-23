package storageModel.events;

import storageModel.Product;

/**
 * Created by neikila on 22.11.15.
 */
public class ProductIncome extends Event {
    private Product product;
    private double amount;

    public ProductIncome(double date, EventType eventType, Product product, int amount) {
        super(date, eventType);
        this.product = product;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }
}
