package Model;

import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;

public abstract class Log {
    public static int numberOfLogs;
    protected SimpleIntegerProperty logId = new SimpleIntegerProperty();
    protected LocalDateTime dateAndTime;
    protected  int orderNumber;
    public Log() {
        numberOfLogs+=1;
        logId.set(numberOfLogs);
        dateAndTime = LocalDateTime.now();
    }

    public static void setNumberOfLogs(int numberOfLogs) {
        Log.numberOfLogs = numberOfLogs;
    }

    public static int getNumberOfLogs() {
        return numberOfLogs;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getLogId() {
        return logId.get();
    }
    public int getOrderNumber(){
        return orderNumber;
    }
}
