package main;

import resource.XMLSettingsParser;
import utils.Output;

/**
 * Created by neikila on 17.12.15.
 */
public class Settings {
    private final String storageFilename;
    private final String productsFilename;
    private final String modelSettingsFilename;
    private final String statisticOutputFilename;
    private final Output output;

    public Settings(XMLSettingsParser parser) {
        boolean isDebug = parser.getIsDebug();
        boolean toConsole = parser.getToConsole();
        storageFilename = parser.getStorageFilename();
        productsFilename = parser.getProductsFilename();
        String outputFile = parser.getOutputFile();
        modelSettingsFilename = parser.getModelSettingsFilename();
        output = new Output(outputFile,toConsole);
        output.setDebug(isDebug);
        statisticOutputFilename = parser.getStatisticFilename();
    }

    public Output getOutput() {
        return output;
    }

    public String getStorageFilename() {
        return storageFilename;
    }

    public String getProductsFilename() {
        return productsFilename;
    }

    public String getModelSettingsFilename() {
        return modelSettingsFilename;
    }

    public String getStatisticOutputFilename() {
        return statisticOutputFilename;
    }
}
