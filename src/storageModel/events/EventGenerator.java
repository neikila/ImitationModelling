package storageModel.events;

import resource.XMLModelSettingsParser;
import storageModel.Product;
import storageModel.Storage;
import storageModel.storageDetails.Section;
import utils.Output;

import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by neikila on 17.12.15.
 */
public class EventGenerator {
    private boolean stopGenerating;
    final private double stopTime;

    private boolean isRequestExist;
    private boolean isIncomeExist;

    private Random random;
    private Random productRandom;

    private Output output;

    private Storage storage;
    private List<Product> possibleProducts;
    private Queue<Event> queue;

    private int deltaIncome;
    private int deltaRequest;
    private int amountIncome;

    public EventGenerator(Output output, Storage storage, Queue<Event> queue, XMLModelSettingsParser parser, List<Product> possibleProducts) {
        this.output = output;
        this.possibleProducts = possibleProducts;
        random = new Random();
        productRandom = new Random();
        stopGenerating = false;
        isIncomeExist = false;
        isRequestExist = false;
        this.stopTime = parser.getStopGenerating();
        this.storage = storage;
        this.queue = queue;
        deltaIncome = parser.getIncomeDelta();
        deltaRequest = parser.getRequestDelta();
        amountIncome = parser.getIncomeAmount();
    }

    public void check(double time) {
        if (time > stopTime) {
            stopGenerating = true;
        }
    }

    public void unsetRequestExist() {
        isRequestExist = false;
    }

    public void unsetIncomeExist() {
        isIncomeExist = false;
    }

    public Event generateIncome(double time) {
        return new ProductIncome(time + random.nextInt((int)deltaIncome * 100) / 100.0,
                possibleProducts.get(productRandom.nextInt(possibleProducts.size())),
                random.nextInt(amountIncome) + 1
        );
    }

    public Event generateRequest(double time) {
        Section section = storage.getRandomSectionWithProduct();
        if (section != null) {
            return new ProductRequest(
                    time + random.nextInt((int) deltaRequest * 100) / 100.0,
                    section.getProduct(),
                    random.nextInt(section.getAmount()) + 1
            );
        } else {
            return null;
        }
    }

    public void generateOutProductEvents(double time) {
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
