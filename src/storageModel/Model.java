package storageModel;

import resourse.XMLProductsParser;
import resourse.XMLStorageParser;
import storageModel.events.*;
import storageModel.events.Event;
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
            System.out.println(currentEvent.getEventType());
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                    handleProductIncome((ProductIncome) currentEvent);
                    break;
                case ProductRequest:
                    handleProductRequest((ProductRequest) currentEvent);
                    break;
            }
        }
    }

    private void handleProductIncome(ProductIncome income) {
        Point from = storage.getEntrancePoint();
        System.out.println("from: " + from);
        double totalWeight = income.getAmount() * income.getProduct().getWeightOfUnit();
        Section section = storage.findSectionForProduct(income.getProduct(), totalWeight);
        if (section == null) {
            System.out.println("No place for product.\n" +
                    "Product " + income.getProduct() + "\n" +
                    "Amount " + income.getAmount());
            return;
        }
        Point to = section.getPointAccess();
        System.out.println("To: " + to);
        double timeDelay = storage.getTimeDelay(from, to);
        System.out.println("Time delay = " + timeDelay);

        section.addProduct(income.getProduct(), income.getAmount());

        System.out.println(section.toString() + "\n" +
                income.getProduct() + "\n" +
                "Amount = " + income.getAmount());
    }

    private void handleProductRequest(ProductRequest request) {
        Point to = storage.getExitPoint();
        System.out.println("To: " + to);
        double totalWeight = request.getAmount() * request.getProduct().getWeightOfUnit();
        Section section = storage.findSectionForProduct(request.getProduct(), totalWeight);
        if (section == null) {
            System.out.println("No such product.\n" +
                    "Product " + request.getProduct() + "\n" +
                    "Amount " + request.getAmount());
            return;
        }
        Point from = section.getPointAccess();
        System.out.println("from: " + from);
        double timeDelay = storage.getTimeDelay(from, to);
        System.out.println("Time delay = " + timeDelay);

        System.out.println("Got " + section.getProduct(request.getAmount()) + "\n" +
                section + "\n" +
                "Product " + section.getProduct() + "\n" +
                "Amount " + section.getAmount());
    }
}
