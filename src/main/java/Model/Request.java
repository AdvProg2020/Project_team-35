package Model;

public abstract class Request {
    public Request() {
        requestIdNumber++;
        requestId = requestIdNumber;
    }

    protected boolean isDone;
    private int requestId;
    public static int requestIdNumber;
    public abstract String getDetails();
    public abstract String getRequestInfo();
    public abstract void execute();

    public int getRequestId() {
        return requestId;
    }
}
