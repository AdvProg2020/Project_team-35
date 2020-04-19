package Model;

import java.util.ArrayList;

public class BuyLog extends Log{
    private double purchasedMoney;
    private double OffDiscountMoney;
    public ArrayList<Product> boughtProducts;
    private String sellerName;
    private boolean isDelivered;
}
