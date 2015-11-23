package storageModel;

import resourse.XMLProductsParser;
import resourse.XMLStorageParser;
import storageModel.events.*;
import storageModel.events.Event;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by neikila on 22.11.15.
 */
public class Model implements Runnable {
    private Storage storage;
    private List<Product> possibleProducts;
    private PriorityQueue <Event> queue;
    private Worker worker;
    private PriorityQueue <Event> queueOfInOut;
    public static double time = 0.0;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser) {
        this.storage = new Storage(parser);
        possibleProducts = productsParser.getProducts();
        queue = new PriorityQueue<>(new EventComparator());
        queueOfInOut = new PriorityQueue<>(new EventComparator());
        worker = new Worker(new Point(0,0), storage);
        Product product = possibleProducts.get(new Random().nextInt(possibleProducts.size()));
        queue.add(new ProductIncome(0, product, 2));
        queue.add(new ProductRequest(1, product, 1));
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Event currentEvent = queue.poll();
            time = currentEvent.getDate();
            System.out.println(currentEvent);
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                case ProductRequest:
                    if (worker.isFree()) {
                        worker.handleProductEvent(currentEvent);
                        queue.add(worker.nextState());
                    } else {
                        System.out.println("To queue");
                        queueOfInOut.add(currentEvent);
                    }
                    break;
                case PointAchieved:
                    queue.add(((PointAchieved) currentEvent).getWorker().nextState());
                    break;
                case ProductLoaded:
                    Event event= ((ProductLoaded) currentEvent).getWorker().nextState();
                    queue.add(event);
                    break;
                case ProductReleased:
                    ((ProductReleased) currentEvent).getWorker().nextState();
                    if (queueOfInOut.size() > 0) {
                        System.out.println("From queue Out In");
                        worker.handleProductEvent(queueOfInOut.poll());
                        queue.add(worker.nextState());
                    }
                    break;
            }
        }
    }
}
