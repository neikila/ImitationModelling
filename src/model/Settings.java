package model;

/**
 * Created by neikila on 31.10.15.
 */
public class Settings {
    private int limitSize;
    private double requestTimeDelta;
    private double requestHandlingTime;

    public double getRequestTimeDelta() {
        return requestTimeDelta;
    }

    public double getRequestHandlingTime() {
        return requestHandlingTime;
    }

    public int getLimitSize() {
        return limitSize;
    }

    public Settings() {
        this.limitSize = 20;
        this.requestTimeDelta = 3;
        this.requestHandlingTime = 2;
    }
}
