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
    }


    protected boolean isDone;
    private int requestId;
    public static int requestIdNumber;
    public abstract String getDetails();
    public abstract String getRequestInfo();
    public abstract void execute();
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
        if (this instanceof AddOffRequest) {
            return "Add Off";
        }
        if (this instanceof AddProductRequest) {
            return "Add Product";
        }
        if (this instanceof EditOffRequest) {
            return "Edit Off";
        }
        if (this instanceof EditProductRequest) {
            return "Edit Product";
        }
        return null;
    }




}
