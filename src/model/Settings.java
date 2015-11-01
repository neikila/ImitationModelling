package model;

/**
 * Created by neikila on 31.10.15.
 */
public class Settings {
    private int limitSize;
    private double requestTimeDelta;
    private double requestHandlingTime;
    private double requestHandlingTimeError;
    private String fileName;

    public double getRequestTimeDelta() {
        return requestTimeDelta;
    }

    public double getRequestHandlingTime() {
        return requestHandlingTime;
    }

    public int getLimitSize() {
        return limitSize;
    }

    public double getRequestHandlingTimeError() { return requestHandlingTimeError; }

    public Settings() {
        limitSize = 20;
        requestTimeDelta = 3;
        requestHandlingTime = 4;
        requestHandlingTimeError = 0.5;
        fileName = "./result.txt";
    }

    public boolean printToFile() {
        return true;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isOutsideConditionEqual() {
        return false;
    }
}
