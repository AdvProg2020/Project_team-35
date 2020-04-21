package Model;

public abstract class Request {
    protected StringBuilder request;
    public Request(StringBuilder request) {
        this.request = request;
    }

    public abstract StringBuilder getDetails();
    public abstract void execute() throws RequestProblemNotExistManager;
}
