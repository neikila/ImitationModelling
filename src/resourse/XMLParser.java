package resourse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Created by neikila on 19.11.15.
 */
public class XMLParser {
    protected Element root;

    public XMLParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File("res/" + fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        root = doc.getDocumentElement();
        root.normalize();
    }

    protected <T> List<T> getArrayOfSomethingFromElement (Element node, String tag, Function<Element, T> getter) {
        Node bounds = node.getElementsByTagName(tag).item(0);
        return getArrayOfSomethingFromElement((Element)bounds, getter);
    }

    protected <T> List<T> getArrayOfSomethingFromElement (Element bounds, Function<Element, T> getter) {
        bounds.normalize();
        NodeList nList = bounds.getChildNodes();
        List <T> result = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); ++i) {
            if (nList.item(i).getNodeType() == Node.ELEMENT_NODE)
                result.add(getter.apply((Element) nList.item(i)));
        }
        return result;
    }

    protected Point getPoint(Element node) {
        int x = Integer.parseInt(node.getAttribute("x"));
        int y = Integer.parseInt(node.getAttribute("y"));
        return new Point(x, y);
    }

    protected int[] toIntArray(List<Integer> list) {
        int[] result = new int[list.size()];
        Iterator <Integer> iterator = list.iterator();
        for (int i = 0; i < list.size(); ++i) {
            result[i] = iterator.next();
        }
        return result;
    }

    protected int countPoints(NodeList nList) {
        int counter = 0;
        for (int i = 0; i < nList.getLength(); ++i) {
            if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                ++counter;
            }
        }
        return counter;
    }
}
