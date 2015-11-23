package storageModel;

import resourse.XMLProductsParser;
import resourse.XMLStorageParser;
import storageModel.events.Event;
import storageModel.events.EventComparator;
import storageModel.events.EventType;
import storageModel.events.ProductIncome;
import storageModel.storageDetails.Section;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by neikila on 22.11.15.
 */
public class Model implements Runnable {
    private Storage storage;
    private List<Product> possibleProducts;
    private PriorityQueue <Event> queue;
    private Worker worker;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser) {
        this.storage = new Storage(parser);
        possibleProducts = productsParser.getProducts();
        queue = new PriorityQueue<>(new EventComparator());
        worker = new Worker(new Point(0,0));
        queue.add(new ProductIncome(0, EventType.ProductIncome,
                possibleProducts.get(new Random().nextInt(possibleProducts.size())), 1));
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Event currentEvent = queue.poll();
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                    ProductIncome income = (ProductIncome) currentEvent;
                    Point from = storage.getEntrancePoint();
                    System.out.println("from: " + from);
                    double totalWeight = income.getAmount() * income.getProduct().getWeightOfUnit();
                    Section section = storage.findSectionForProduct(income.getProduct(), totalWeight);
                    Point to = new Point(section.getIndex());
                    switch (section.getPossibleDirection()) {
                        case Down:
                            to.translate(0, -1);
                            break;
                        case Up:
                            to.translate(0, 1);
                            break;
                        case Left:
                            to.translate(-1, 0);
                            break;
                        case Right:
                            to.translate(1, 0);
                            break;
                    }
                    System.out.println("To: " + to);
                    double timeDelay = storage.getTimeDelay(from, to);
                    System.out.println("Time delay = " + timeDelay);
                    break;
            }
        }
    }
}
