package resource;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by neikila on 19.11.15.
 */
public class XMLModelSettingsParser extends XMLParser {
    public static final String DEADLINE = "deadline";
    public static final String STOP_GENERATING = "stopGenerating";

    public XMLModelSettingsParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        super(fileName);
    }

    public Double getDeadline() {
        return getDouble(root, DEADLINE);
    }

    public Double getStopGenerating() {
        return getDouble(root, STOP_GENERATING);
    }
}