package storageModel;

import main.Analyzer;
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
    private PriorityQueue <Event> queueOfInOut;
    final private EventGenerator generator;
    private double deadline;
    private double time;

    private Worker worker;

    final private Output out;
    final private Analyzer analyzer;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser, XMLModelSettingsParser modelSettings, Settings settings) {
        out = settings.getOutput();
        analyzer = new Analyzer();

        Storage storage = new Storage(parser, out);
        queue = new PriorityQueue<>(new Event.EventComparator());
        queueOfInOut = new PriorityQueue<>(new Event.EventComparator());
        worker = new Worker(new Point(0, 0), storage, out);
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
            analyzer.addToIntegralSumOfInputOutput(time, queueOfInOut);
            Event currentEvent = queue.poll();
            time = currentEvent.getDate();
            out.println(currentEvent);
            switch (currentEvent.getEventType()) {
                case ProductIncome:
                    generator.unsetIncomeExist();
                    generator.generateOutProductEvents(time);
                    out.printProductEvent(currentEvent);
                    handleProductEvent(currentEvent);
                    break;
                case ProductRequest:
                    generator.unsetRequestExist();
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
                        Event eventTemp = queueOfInOut.poll();
                        analyzer.pollEventFromQueue(eventTemp, time);
                        worker.handleProductEvent(eventTemp);
                        queue.add(worker.nextState(time));
                    }
                    break;
            }
        }
        analyzer.stopTimer(time);
    }

    private void handleProductEvent(Event currentEvent) {
        if (worker.isFree()) {
            if (worker.handleProductEvent(currentEvent)) {
                Event temp = worker.nextState(time);
                analyzer.saveTime(temp.getDate() - time);
                queue.add(temp);
            } else {
                analyzer.incrementRejected();
            }
        } else {
            out.println("To queue");
            queueOfInOut.add(currentEvent);
        }
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }
}
