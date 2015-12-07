package storageModel;

import resource.XMLProductsParser;
import resource.XMLStorageParser;
import storageModel.events.Event;
import storageModel.events.*;
import storageModel.storageDetails.Section;
import utils.Output;

import java.awt.*;
import java.io.File;
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
    private PriorityQueue <Event> queueOfInOut;
    private double stopGenerating;
    private double deadline;
    public static double time = 0.0;

    private boolean isRequestExist;
    private boolean isIncomeExist;

    private Output out;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser) {
        out = new Output("log.txt", false);

        this.storage = new Storage(parser, out);
        possibleProducts = productsParser.getProducts();
        queue = new PriorityQueue<>(new Event.EventComparator());
        queueOfInOut = new PriorityQueue<>(new Event.EventComparator());
        worker = new Worker(new Point(0,0), storage, out);
        ProductIncome event = (ProductIncome) generateIncome();
        queue.add(event);
        queue.add(new ProductRequest(
                event.getDate() + 10,
                event.getProduct(),
                1 + new Random().nextInt(event.getAmount())
        ));
        isIncomeExist = true;
        isRequestExist = true;
        stopGenerating = 100;
        deadline = 150;
    }

    @Override
    public void run() {
        while (!queue.isEmpty() && time < deadline) {
            Event currentEvent = queue.poll();
            time = currentEvent.getDate();
            out.println(currentEvent);
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                    isIncomeExist = false;
                    generateOutProductEvents();
                    out.printProductEvent(currentEvent);
                    handleProductEvent(currentEvent);
                    break;
                case ProductRequest:
                    isRequestExist = false;
                    generateOutProductEvents();
                    out.printProductEvent(currentEvent);
                    handleProductEvent(currentEvent);
                    break;
                case PointAchieved:
                    Point point = ((PointAchieved)currentEvent).getPoint();
                    out.printPoint(point);
                    queue.add(((PointAchieved) currentEvent).getWorker().nextState());
                    break;
                case ProductLoaded:
                    Event event = ((ProductLoaded) currentEvent).getWorker().nextState();
                    queue.add(event);
                    break;
                case ProductReleased:
                    ((ProductReleased) currentEvent).getWorker().nextState();
                    if (queueOfInOut.size() > 0) {
                        out.println("From queue Out In");
                        worker.handleProductEvent(queueOfInOut.poll());
                        queue.add(worker.nextState());
                    }
                    break;
            }
        }
    }


    private Random random = new Random();
    private Random productRandom = new Random();

    private Event generateIncome() {
        double delta = 50.0;
        return new ProductIncome(time + random.nextInt((int)delta * 100) / 100.0,
                possibleProducts.get(productRandom.nextInt(possibleProducts.size())),
                random.nextInt(10) + 1
        );
    }

    private Event generateRequest() {
        double delta = 50.0;
        Section section = storage.getRandomSectionWithProduct();
        if (section != null) {
            return new ProductRequest(
                    time + random.nextInt((int) delta * 100) / 100.0,
                    section.getProduct(),
                    random.nextInt(section.getAmount()) + 1
            );
        } else {
            return null;
        }
    }

    private void handleProductEvent(Event currentEvent) {
        if (worker.isFree()) {
            worker.handleProductEvent(currentEvent);
            queue.add(worker.nextState());
        } else {
            out.println("To queue");
            queueOfInOut.add(currentEvent);
        }
    }

    private void generateOutProductEvents() {
        if (time < stopGenerating) {
            if (!isIncomeExist) {
                Event event = generateIncome();
                queue.add(event);
                out.debugPrintln("DEBUG: generate INCOME. DATE: " + event.getDate());
                isIncomeExist = true;
            }
            if (!isRequestExist) {
                Event request = generateRequest();
                if (request != null) {
                    queue.add(request);
                    out.debugPrintln("DEBUG: generate REQUEST. DATE: " + request.getDate());
                    isRequestExist = true;
                }
            }
        }
    }
}
