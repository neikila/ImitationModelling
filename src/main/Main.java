package main;

import resource.XMLProductsParser;
import resource.XMLSettingsParser;
import resource.XMLStorageParser;
import storageModel.Model;

import java.io.PrintStream;

/**
 * Created by neikila on 31.10.15.
 */
public class Main {
    public static PrintStream out;

    public static void main(String[] args)  throws Exception {
        out = System.out;
        XMLSettingsParser settingsParser = new XMLSettingsParser("Settings.xml");
        Settings settings = new Settings(settingsParser);
        XMLStorageParser parser = new XMLStorageParser(settings.getStorageFilename());
        XMLProductsParser productsParser = new XMLProductsParser(settings.getProductsFilename());
        new Model(parser, productsParser, settings).run();
    }
}
