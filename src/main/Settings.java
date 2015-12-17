package main;

import resource.XMLSettingsParser;

/**
 * Created by neikila on 17.12.15.
 */
public class Settings {
    private final double deadline;
    private final double stopGenerating;
    private final String storageFilename;
    private final String productsFilename;
    private final String outputFile;
    private final boolean toConsole;
    private final boolean isDebug;

    public Settings(XMLSettingsParser parser) {
        isDebug = parser.getIsDebug();
        toConsole = parser.getToConsole();
        deadline = parser.getDeadline();
        stopGenerating = parser.getStopGenerating();
        storageFilename = parser.getStorageFilename();
        productsFilename = parser.getProductsFilename();
        outputFile = parser.getOutputFile();
    }

    public double getDeadline() {
        return deadline;
    }

    public double getStopGenerating() {
        return stopGenerating;
    }

    public String getStorageFilename() {
        return storageFilename;
    }

    public String getProductsFilename() {
        return productsFilename;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public boolean isToConsole() {
        return toConsole;
    }

    public boolean isDebug() {
        return isDebug;
    }
}
