package Model;

public abstract class Request {
    protected RequestTypes requestTypes;
    protected StringBuilder request;
    protected Account requester;
    public Request(StringBuilder request , RequestTypes requestTypes , Account requester) {
        this.request = request;
        this.requestTypes = requestTypes;
        this.requester = requester;
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
