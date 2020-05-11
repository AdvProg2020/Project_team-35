package Model;

import Views.MainPage;

import java.util.ArrayList;

public abstract class Request {
    protected Seller seller;
    public Request(Seller seller) {
        requestIdNumber++;
        requestId = requestIdNumber;
        this.seller = seller;
        Manager.newRequests.add(this);
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
