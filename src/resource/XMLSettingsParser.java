package resource;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by neikila on 19.11.15.
 */
public class XMLSettingsParser extends XMLParser {
    public static final String DEADLINE = "deadline";
    public static final String STOP_GENERATING = "stopGenerating";
    public static final String STORAGE_FILENAME = "storageFilename";
    public static final String PRODUCTS_FILENAME = "productsFilename";
    public static final String OUTPUT_FILE = "outputFile";
    public static final String MODEL_SETTINGS = "modelSettings";
    public static final String TO_CONSOLE = "toConsole";
    public static final String IS_DEBUG = "isDebug";

    public XMLSettingsParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        super(fileName);
    }

    public Double getDeadline() {
        return getDouble(root, DEADLINE);
    }

    public Double getStopGenerating() {
        return getDouble(root, STOP_GENERATING);
    }

    public String getStorageFilename() {
        return getString(root, STORAGE_FILENAME);
    }

    public String getProductsFilename() {
        return getString(root, PRODUCTS_FILENAME);
    }

    public String getOutputFile() {
        return getString(root, OUTPUT_FILE);
    }

    public String getModelSettingsFilename() {
        return getString(root, MODEL_SETTINGS);
    }

    public boolean getToConsole() {
        return getBoolean(root, TO_CONSOLE);
    }

    public boolean getIsDebug() {
        return getBoolean(root, IS_DEBUG);
    }
}