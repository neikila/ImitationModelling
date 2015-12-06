package resource;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import storageModel.Product;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class XMLProductsParser extends XMLParser {

    public static final String PRODUCT = "product";

    public XMLProductsParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        super(fileName);
    }

    public List<Product> getProducts() {
        return getArrayOfSomethingFromElement(root, this::getProduct);
    }

    private Product getProduct(Element node) {
        String productName = node.
                getElementsByTagName("productName").
                item(0).
                getTextContent();
        double weight = Double.parseDouble(node.getElementsByTagName("weightOfSingleProduct").item(0).getTextContent());
        return new Product(productName, weight);
    }
}
