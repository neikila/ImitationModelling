package main;

import resource.XMLModelSettingsParser;
import resource.XMLProductsParser;
import resource.XMLSettingsParser;
import resource.XMLStorageParser;
import storageModel.Model;
import utils.XMLStatisticOutput;

/**
 * Created by neikila on 31.10.15.
 */
public class Launcher {
    public static final String DEFAULT_SETTING_LOCATION = "Settings.xml";

    public static void main(String[] args)  throws Exception {
        XMLSettingsParser settingsParser;
        String settingsXML = DEFAULT_SETTING_LOCATION;
        if (args.length == 1) {
            settingsXML = args[0];
        }
        System.out.println("Settings are read from " + settingsXML);
        settingsParser = new XMLSettingsParser(settingsXML);
        Settings settings = new Settings(settingsParser);
        XMLModelSettingsParser modelParser = new XMLModelSettingsParser(settings.getModelSettingsFilename());
        XMLStorageParser storageParser = new XMLStorageParser(settings.getStorageFilename());
        XMLProductsParser productsParser = new XMLProductsParser(settings.getProductsFilename());
        Model model = new Model(storageParser, productsParser, modelParser, settings);
        model.run();
        Analyzer analyzer = model.getAnalyzer();
        XMLStatisticOutput output = new XMLStatisticOutput(settings.getStatisticOutput(), analyzer);
        output.print();
    }
}
