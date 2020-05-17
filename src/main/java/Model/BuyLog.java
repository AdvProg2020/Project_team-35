package Model;

import java.util.ArrayList;

public class BuyLog extends Log{
    private double purchasedMoney;
    private double OffDiscountMoney;
    public ArrayList<Product> boughtProducts;
    private String sellerName;
    private boolean isDelivered;

    public BuyLog(double purchasedMoney, double offDiscountMoney, ArrayList<Product> boughtProducts, String sellerName) {
        this.purchasedMoney = purchasedMoney;
        OffDiscountMoney = offDiscountMoney;
        this.boughtProducts = boughtProducts;
        this.sellerName = sellerName;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public ArrayList<Product> getBoughtProducts() {
        return boughtProducts;
    }
}
