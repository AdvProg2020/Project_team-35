package Model;

public abstract class Request {
    public Request() {
        requestIdNumber++;
        requestId = requestIdNumber;
    }


    private int requestId;
    public static int requestIdNumber;
    public abstract String getDetails();
    public abstract void execute();
}
