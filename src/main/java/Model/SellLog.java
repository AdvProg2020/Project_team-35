package Model;

import java.util.ArrayList;

public class SellLog extends Log {

    private double receivedAmount;
    private double offDiscountAmount;
    private ArrayList<Product> soldProducts;

    private String buyerName;
    private boolean isReceived;

    public SellLog(double receivedAmount, double offDiscountAmount, ArrayList<Product> soldProducts, String buyerName) {
        this.receivedAmount = receivedAmount;
        this.offDiscountAmount = offDiscountAmount;
        this.soldProducts = soldProducts;
        this.buyerName = buyerName;
    }

    public ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }
}
