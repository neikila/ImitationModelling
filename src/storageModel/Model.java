package storageModel;

import resourse.XMLProductsParser;
import resourse.XMLStorageParser;

import java.util.List;

/**
 * Created by neikila on 22.11.15.
 */
public class Model {
    private Storage storage;
    private List<Product> possibleProducts;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser) {
        this.storage = new Storage(parser);
        possibleProducts = productsParser.getProducts();
        System.out.println("Test");
    }
}
