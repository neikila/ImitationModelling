package storageModel.events;

import storageModel.Product;

/**
 * Created by neikila on 22.11.15.
 */
public class ProductRequest extends Event {
    private Product product;
    private int amount;

    public ProductRequest(double date, Product product, int amount) {
        super(date, EventType.ProductRequest);
        this.product = product;
        this.amount = amount;
    }

    public ProductRequest(double date) {
        this(date, null, 0);
    }

    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setContent(Product product, int amount) {
        this.product = product;
        this. amount = amount;
    }
}
