package storageModel;

import resourse.XMLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 22.11.15.
 */
public class Model {
    private Storage storage;
    private List<Product> possibleProducts;

    public Model(XMLParser parser) {
        this.storage = new Storage(parser);
        possibleProducts = new ArrayList<>();
        possibleProducts.add(new Product("Steel"));
        possibleProducts.add(new Product("Wood"));
        possibleProducts.add(new Product("Cotton"));
        possibleProducts.add(new Product("Food"));
    }
}
