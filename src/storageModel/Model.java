package storageModel;

import main.Settings;
import resource.XMLModelSettingsParser;
import resource.XMLProductsParser;
import resource.XMLStorageParser;
import storageModel.events.Event;
import storageModel.events.*;
import storageModel.storageDetails.Section;
import utils.Output;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by neikila on 22.11.15.
 */
public class Model implements Runnable {
    private List<Product> possibleProducts;
    private PriorityQueue <Event> queue;
    private Worker worker;
    private PriorityQueue <Event> queueOfInOut;
    private double deadline;
    public static double time = 0.0;

    final private Output out;

    final private EventGenerator generator;

    public Model(XMLStorageParser parser, XMLProductsParser productsParser, XMLModelSettingsParser modelSettings, Settings settings) {
        out = settings.getOutput();

        Storage storage = new Storage(parser, out);
        possibleProducts = productsParser.getProducts();
        queue = new PriorityQueue<>(new Event.EventComparator());
        queueOfInOut = new PriorityQueue<>(new Event.EventComparator());
        worker = new Worker(new Point(0,0), storage, out);
        generator = new EventGenerator(out, storage, queue, modelSettings);

        ProductIncome event = (ProductIncome) generator.generateIncome(time);
        queue.add(event);
        queue.add(new ProductRequest(
                event.getDate() + 10,
                event.getProduct(),
                1 + new Random().nextInt(event.getAmount())
        ));

        deadline = modelSettings.getDeadline();
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

    private void handleProductEvent(Event currentEvent) {
        if (worker.isFree()) {
            if (worker.handleProductEvent(currentEvent))
                queue.add(worker.nextState());
        } else {
            out.println("To queue");
            queueOfInOut.add(currentEvent);
        }
    }


    public class EventGenerator {
        private boolean stopGenerating;
        final private double stopTime;

        private boolean isRequestExist;
        private boolean isIncomeExist;

        private Random random;
        private Random productRandom;

        private Output output;

        private Storage storage;
        private Queue<Event> queue;

        public EventGenerator(Output output, Storage storage, Queue<Event> queue, XMLModelSettingsParser parser) {
            this.output = output;
            random = new Random();
            productRandom = new Random();
            stopGenerating = false;
            isIncomeExist = false;
            isRequestExist = false;
            this.stopTime = parser.getStopGenerating();
            this.storage = storage;
            this.queue = queue;
        }

        public void check(double time) {
            if (time > stopTime) {
                stopGenerating = true;
            }
        }

        public void setRequestExist() {
            isRequestExist = false;
        }

        public void setIncomeExist() {
            isIncomeExist = false;
        }

        public Event generateIncome(double time) {
            double delta = 50.0;
            return new ProductIncome(time + random.nextInt((int)delta * 100) / 100.0,
                    possibleProducts.get(productRandom.nextInt(possibleProducts.size())),
                    random.nextInt(10) + 1
            );
        }

        private Event generateRequest(double time) {
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

        private void generateOutProductEvents(double time) {
            if (!stopGenerating) {
                if (!isIncomeExist) {
                    Event event = generateIncome(time);
                    queue.add(event);
                    output.debugPrintln("DEBUG: generate INCOME. DATE: " + event.getDate());
                    isIncomeExist = true;
                }
                if (!isRequestExist) {
                    Event request = generateRequest(time);
                    if (request != null) {
                        queue.add(request);
                        output.debugPrintln("DEBUG: generate REQUEST. DATE: " + request.getDate());
                        isRequestExist = true;
                    }
                }
            }
        }
    }
}
