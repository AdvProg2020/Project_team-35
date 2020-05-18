package Model;

import Views.MainPage;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Request {
    protected Seller seller;
    public Request(Seller seller) {
        requestIdNumber++;
        requestId = requestIdNumber;
        this.seller = seller;
        Manager.newRequests.add(this);
        Request.setCurrentSort("Nothing");
    }


    protected boolean isDone;
    private int requestId;
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
        return requestId;
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




}
