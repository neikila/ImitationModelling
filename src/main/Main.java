package main;

import analytics.Analytics;
import model.Settings;
import storageModel.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by neikila on 31.10.15.
 */
public class Main {
    public static PrintStream out;

    public static void main(String[] args)  throws Exception {
        out = System.out;
        Settings settings = new Settings();
        try {
            if (settings.printToFile()) {
                out = new PrintStream(new File(settings.getFileName()));
                System.out.println("Output to file");
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

        XMLParser parser = new XMLParser(settings.getXmlResourceFilename());
        Storage storage = new Storage(parser);
        storage.printAllWalls();
        storage.printAllBarriers();

//        List <Event> queue = null;
//        if (settings.isOutsideConditionEqual()) {
//            queue = new ArrayList<>(settings.getLimitSize());
//
//            RandomGenerator generator = new RandomGenerator();
//            double temp = 0;
//            for (int i = 0; i < settings.getLimitSize(); ++i) {
//                queue.add(new CustomerIncome(generator.getTime(temp, settings.getRequestTimeDelta())));
//                temp += settings.getRequestTimeDelta();
//            }
//        }
//
//        // Количество прогонов
//        int size = 10;
//        List<Analytics> data = new ArrayList<>(size);
//        for (int i = 0; i < size; ++i) {
//            out.println();
//            out.println("#################################");
//            out.println("Simulation number " + i);
//
//            Analytics analytics = new Analytics();
//            Model model = new Model(settings, analytics, queue);
//            model.run();
//            data.add(analytics);
//            analytics.print();
//        }
//
//        out.println();
//        out.println("#################################");
//        out.println("Results according to " + size + " simulations");
//        out.println("Average time in queue: " + countAverageInQueueTime(data));
//        out.println("Average time on cashbox: " + countAverageServingTime(data));
    }

    public static double countAverageServingTime(List <Analytics> data) {
        double averageTime = 0;
        for (Analytics analytic: data) {
            averageTime += analytic.getAverageServingTime();
        }
        averageTime /= data.size();
        return averageTime;
    }

    public static double countAverageInQueueTime(List <Analytics> data) {
        double averageTime = 0;
        for (Analytics analytic: data) {
            averageTime += analytic.getAverageWaitingInQueueTime();
        }
        averageTime /= data.size();
        return averageTime;
    }
}
