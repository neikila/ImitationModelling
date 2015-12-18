package storageModel;

import main.Settings;
import resource.XMLModelSettingsParser;
import resource.XMLProductsParser;
import resource.XMLStorageParser;
import storageModel.events.Event;
import storageModel.events.*;
import utils.Output;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by neikila on 22.11.15.
 */
public class Model implements Runnable {
    private PriorityQueue <Event> queue;
    private Worker worker;
    private PriorityQueue <Event> queueOfInOut;
    private double deadline;
    public double time;

    final private Output out;

    final private EventGenerator generator;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser, XMLModelSettingsParser modelSettings, Settings settings) {
        out = settings.getOutput();

        Storage storage = new Storage(parser, out);
        queue = new PriorityQueue<>(new Event.EventComparator());
        queueOfInOut = new PriorityQueue<>(new Event.EventComparator());
        worker = new Worker(new Point(0,0), storage, out);
        List<Product> possibleProducts = productsParser.getProducts();
        generator = new EventGenerator(out, storage, queue, modelSettings, possibleProducts);

        time = 0.0;
        deadline = modelSettings.getDeadline();

        ProductIncome event = (ProductIncome) generator.generateIncome(time);
        queue.add(event);
        queue.add(new ProductRequest(
                event.getDate() + 10,
                event.getProduct(),
                1 + new Random().nextInt(event.getAmount())
        ));
    }

    @Override
    public void run() {
        while (!queue.isEmpty() && time < deadline) {
            generator.check(time);
            Event currentEvent = queue.poll();
            time = currentEvent.getDate();
            out.println(currentEvent);
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                    generator.setIncomeExist();
                    generator.generateOutProductEvents(time);
                    out.printProductEvent(currentEvent);
                    handleProductEvent(currentEvent);
                    break;
                case ProductRequest:
                    generator.setRequestExist();
                    generator.generateOutProductEvents(time);
                    out.printProductEvent(currentEvent);
                    handleProductEvent(currentEvent);
                    break;
                case PointAchieved:
                    Point point = ((PointAchieved)currentEvent).getPoint();
                    out.printPoint(point);
                    queue.add(((PointAchieved) currentEvent).getWorker().nextState(time));
                    break;
                case ProductLoaded:
                    Event event = ((ProductLoaded) currentEvent).getWorker().nextState(time);
                    queue.add(event);
                    break;
                case ProductReleased:
                    ((ProductReleased) currentEvent).getWorker().nextState(time);
                    if (queueOfInOut.size() > 0) {
                        out.println("From queue Out In");
                        worker.handleProductEvent(queueOfInOut.poll());
                        queue.add(worker.nextState(time));
                    }
                    break;
            }
        }
    }

    private void handleProductEvent(Event currentEvent) {
        if (worker.isFree()) {
            if (worker.handleProductEvent(currentEvent))
                queue.add(worker.nextState(time));
        } else {
            out.println("To queue");
            queueOfInOut.add(currentEvent);
        }
    }
}
