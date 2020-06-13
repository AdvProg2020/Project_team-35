package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog extends Log{
    private double purchaseAmount;
    private double OffDiscountMoney;
    public ArrayList<Product> boughtProducts;
    private boolean isDelivered;
    private Customer customer;
    private Seller seller;

    public BuyLog( double purchaseAmount,ArrayList<Product> boughtProducts, Seller seller , Customer customer) throws NoMoneyInCustomerPocket {
       this.purchaseAmount = purchaseAmount;
        this.boughtProducts = boughtProducts;
        this.customer = customer;
        this.seller = seller;
        numberOfLogs++;
        orderNumber = numberOfLogs;
        customer.getBuyLogs().add(this);
        reduceMoneyOfCustomerPocket();

    }
    public void reduceMoneyOfCustomerPocket() throws NoMoneyInCustomerPocket {
        double result = customer.getMoney()- purchaseAmount;
        if (result>0)
        customer.setMoney(result);
        else {
            throw new NoMoneyInCustomerPocket("no money");
        }
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

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}
