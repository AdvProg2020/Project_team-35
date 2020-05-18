package Model;

public abstract class Log {
    protected static int numberOfLogs;
    protected int logId;
    //date;
    protected int orderNumber;

    public int getLogId() {
        return logId;
    }
    public int getOrderNumber(){
        return orderNumber;
    }
}
