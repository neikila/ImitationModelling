package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import storageModel.Barrier;

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
    private Element root;
    private List<Point> wallPoints;
    private List<Barrier> barriers;

    public static final String BOUNDS = "bounds";
    public static final String BARRIERS = "barriers";

    public XMLParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        wallPoints = new ArrayList<>();

        File fXmlFile = new File("res/" + fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        root = doc.getDocumentElement();
        root.normalize();

        wallPoints = getArrayOfSomethingFromElement(root, BOUNDS, this::getPoint);
        barriers = getArrayOfSomethingFromElement(root, BARRIERS, this::getBarrier);
        System.out.println("Root element :" + root.getNodeName());
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public List<Point> getWallPoints() {
        return wallPoints;
    }

    private <T> List<T> getArrayOfSomethingFromElement (Element node, String tag, Function<Element, T> getter) {
        Node bounds = node.getElementsByTagName(tag).item(0);
        bounds.normalize();
        NodeList nList = bounds.getChildNodes();
        List <T> result = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); ++i) {
            if (nList.item(i).getNodeType() == Node.ELEMENT_NODE)
                result.add(getter.apply((Element) nList.item(i)));
        }
        return result;
    }

    private Point getPoint(Element node) {
        int x = Integer.parseInt(node.getAttribute("x"));
        int y = Integer.parseInt(node.getAttribute("y"));
        return new Point(x, y);
    }


    private Barrier getBarrier(Element node) {
        Polygon polygon = getPolygon((Element)node.getElementsByTagName("form").item(0));
        NodeList nList = node.getElementsByTagName("position");
        Point coordinate = null;
        if (nList.getLength() == 1) {
            coordinate = getPoint((Element) nList.item(0));
        }
        return new Barrier(coordinate, polygon);
    }

    private Polygon getPolygon(Element node) {
        NodeList nList = node.getChildNodes();
        int len = countPoints(nList);
        int x[] = new int[len];
        int y[] = new int[len];
        int counter = 0;
        for (int i = 0; i < nList.getLength(); ++i) {
            if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Point temp = getPoint((Element)nList.item(i));
                x[counter] = temp.x;
                y[counter] = temp.y;
                ++counter;
            }
        }
        return new Polygon(x, y, len);
    }

    private int countPoints(NodeList nList) {
        int counter = 0;
        for (int i = 0; i < nList.getLength(); ++i) {
            if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                ++counter;
            }
        }
        return counter;
    }

    private int[] toIntArray(List<Integer> list) {
        int[] result = new int[list.size()];
        Iterator <Integer> iterator = list.iterator();
        for (int i = 0; i < list.size(); ++i) {
            result[i] = iterator.next();
        }
        return result;
    }
}
