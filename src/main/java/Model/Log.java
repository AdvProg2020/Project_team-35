package Model;

public abstract class Log {
    public static int numberOfLogs;
    protected int logId;
    //date;
    protected  int orderNumber;
    public Log() {
        numberOfLogs+=1;
       logId = numberOfLogs;
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
        return logId;
    }
    public int getOrderNumber(){
        return orderNumber;
    }
}
