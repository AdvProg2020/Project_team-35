package Model;

import java.util.ArrayList;

public class SellLog extends Log {

    private double receivedAmount;
    private double offDiscountAmount;
    private ArrayList<Product> soldProducts;

    private String buyerName;
    private boolean isReceived;

    public SellLog(ArrayList<Product> soldProducts, String buyerName) {
        this.soldProducts = soldProducts;
        this.buyerName = buyerName;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }
}
