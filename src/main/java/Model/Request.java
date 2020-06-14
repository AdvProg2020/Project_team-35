package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Request {
    protected Seller seller;
    public Request(Seller seller, String type) {
        requestIdNumber++;
        requestId = new SimpleIntegerProperty(requestIdNumber);
        this.seller = seller;
        Manager.newRequests.add(this);
        Request.setCurrentSort("Nothing");
        requestType.set(type);
        requesterUsername.set(seller.getUsername());
        situation.set("Unchecked");
    }


    protected boolean isDone;
    public SimpleStringProperty requesterUsername = new SimpleStringProperty();
    public SimpleIntegerProperty requestId;
    public SimpleStringProperty requestType = new SimpleStringProperty();
    public SimpleStringProperty situation = new SimpleStringProperty();

    public String getRequesterUsername() {
        return requesterUsername.get();
    }


    public int requestIdProperty() {
        return requestId.get();
    }

    public String getRequestType() {
        return requestType.get();
    }
    public String getSituation() {
        return situation.get();
    }

    public void setSituation(String situation) {
        this.situation.set(situation);
    }

    //    private int requestId;
    public static int requestIdNumber;
    public abstract String getDetails();
    public abstract String getRequestInfo();
    public abstract boolean execute();
    public abstract void decline();

    private static String currentSort = "Nothing";

    public static String getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(String currentSort) {
        Request.currentSort = currentSort;
    }

    public int getRequestId() {
        return requestId.get();
    }

    public Seller getSeller() {
        return seller;
    }
    public String getTypeOfRequest() {
        if (this instanceof SellerRegisterRequest) {
            return "Seller Register";
        }
        else if (this instanceof AddOffRequest) {
            return "Add Off";
        }
        else if (this instanceof AddProductRequest) {
            return "Add Product";
        }
        else if (this instanceof EditOffRequest) {
            return "Edit Off";
        }
        else {
            return "Edit Product";
        }
    }


    public static void resetRetRequestIdNumberToZero() {
        Request.requestIdNumber = 0;
    }
}
