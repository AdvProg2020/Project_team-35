package Model;

public abstract class Request {
    protected RequestTypes requestTypes;
    protected StringBuilder request;
    public Request(StringBuilder request , RequestTypes requestTypes) {
        this.request = request;
        this.requestTypes = requestTypes;
    }

    public RequestTypes getRequestTypes() {
        return requestTypes;
    }

    public void setRequestTypes(RequestTypes requestTypes) {
        this.requestTypes = requestTypes;
    }

    public abstract StringBuilder getDetails();
    public abstract void execute() throws RequestProblemNotExistManager;
}
