package Model;

import java.util.ArrayList;
import java.util.HashMap;

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
        numberOfLogs++;
        orderNumber = numberOfLogs;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public ArrayList<Product> getBoughtProducts() {
        return boughtProducts;
    }
    public int getNumberOfProduct(Product product){
        int i =0;
        for (Product boughtProduct : getBoughtProducts()) {
            if (product.equals(boughtProduct))
                i++;
        }
        return i;
    }
    public HashMap<Product,Integer> historyOfBuys(){
        HashMap<Product,Integer> a = new HashMap<>();
        for (Product product : getBoughtProducts()) {
            a.put(product,getNumberOfProduct(product));
        }
        return a;
    }

}
