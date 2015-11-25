package main;

import resourse.XMLProductsParser;
import resourse.XMLStorageParser;

import java.io.PrintStream;

/**
 * Created by neikila on 31.10.15.
 */
public class Main {
    public static PrintStream out;

    public static void main(String[] args)  throws Exception {
        out = System.out;
        XMLStorageParser parser = new XMLStorageParser("Storage.xml");
        XMLProductsParser productsParser = new XMLProductsParser("PossibleProducts.xml");
        new storageModel.Model(parser, productsParser).run();
    }
}
