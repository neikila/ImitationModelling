package storageModel;

import storageModel.events.*;
import storageModel.events.Event;
import storageModel.storageDetails.Section;

import java.awt.*;

/**
 * Created by neikila on 23.11.15.
 */
public class Worker {
    private static int lastId = 0;
    private int id;
    private Point position;
    private State state;
    private Storage storage;
    private Task task;

    public Worker(Point position, Storage storage) {
        id = lastId++;
        this.position = position;
        state = State.Free;
        this.storage = storage;
        task = null;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isFree() {
        return state.equals(State.Free);
    }

    public State getState() {
        return state;
    }

    public void moveTo(Point point) {
        position = point;
    }

    public Event nextState() {
        double delay;
        switch (state) {
            case Free:
                state = State.GetToLoad;
                delay = storage.getTimeDelay(position, task.from);
                return new PointAchieved(Model.time + delay, task.from, this);
            case GetToLoad:
                state = State.Loading;
                position = task.from;
                delay = 3.0;
                return new ProductLoaded(Model.time + delay, this);
            case Loading:
                state = State.GetToRelease;
                if (task.sectionFrom != null) {
                    task.sectionFrom.getProduct(task.amount);
                }
                delay = storage.getTimeDelay(position, task.to);
                return new PointAchieved(Model.time + delay, task.from, this);
            case GetToRelease:
                state = State.Releasing;
                position = task.to;
                delay = 3.0;
                return new ProductReleased(Model.time + delay, this);
            case Releasing:
                state = State.Free;
                if (task.sectionTo != null) {
                    task.sectionTo.addProduct(task.product, task.amount);
                    System.out.println(task.sectionTo.toString() + "\n" +
                            task.sectionTo.getProduct() + "\n" +
                            "Amount = " + task.sectionTo.getAmount());

                }
                break;
        }
        return null;
    }

    public enum State {
        GetToLoad,
        Loading,
        GetToRelease,
        Releasing,
        Free
    }

    private class Task {
        final Point from;
        final Point to;
        final Product product;
        final int amount;
        final Section sectionFrom;
        final Section sectionTo;

        public Task(Point from, Point to, Section sectionFrom, Section sectionTo, Product product, int amount) {
            this.from = from;
            this.to = to;
            this.sectionFrom = sectionFrom;
            this.sectionTo = sectionTo;
            this.product = product;
            this.amount = amount;
        }
    }

    public void handleProductEvent(Event event) {
        switch(event.getEventType()) {
            case ProductIncome:
                handleProductIncome((ProductIncome) event);
                break;
            case ProductRequest:
                handleProductRequest((ProductRequest) event);
                break;
        }
    }

    public void handleProductIncome(ProductIncome income) {
        Point from = storage.getEntrancePoint();
        System.out.println("from: " + from);
        double totalWeight = income.getAmount() * income.getProduct().getWeightOfUnit();
        Section section = storage.findSectionForProduct(income.getProduct(), totalWeight);
        if (section == null) {
            System.out.println("No place for product.\n" +
                    income.getProduct() + "\n" +
                    "Amount " + income.getAmount());
            return;
        } else {
            System.out.println(section.getIndex());
        }
        Point to = section.getPointAccess();
        System.out.println("To: " + to);
        double timeDelay = storage.getTimeDelay(from, to);
        System.out.println("Time delay = " + timeDelay);

        task = new Task(from, to, null, section, income.getProduct(), income.getAmount());
    }

    public void handleProductRequest(ProductRequest request) {
        Point to = storage.getExitPoint();
        System.out.println("To: " + to);
        Section section = storage.findSectionWithProduct(request.getProduct(), request.getAmount());
        if (section == null) {
            System.out.println("No such product.\n" +
                    request.getProduct() + "\n" +
                    "Amount " + request.getAmount());
            return;
        }
        Point from = section.getPointAccess();
        System.out.println("from: " + from);
        double timeDelay = storage.getTimeDelay(from, to);
        System.out.println("Time delay = " + timeDelay);

//        System.out.println("Got " + section.getProduct(request.getAmount()) + "\n" +
//                section + "\n" +
//                section.getProduct() + "\n" +
//                "Amount " + section.getAmount());

        task = new Task(from, to, section, null, request.getProduct(), request.getAmount());
    }
}
