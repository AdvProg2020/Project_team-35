package Model;

import Main.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog extends Log implements Serializable {
    private double purchaseAmount;
    private double OffDiscountMoney;
    public ArrayList<Product> boughtProducts;
    private boolean isDelivered;
    private Customer customer;
    private Seller seller;
    private boolean isByPocket;

    public BuyLog( double purchaseAmount,ArrayList<Product> boughtProducts, Seller seller , Customer customer, boolean isByPocket) throws NoMoneyInCustomerPocket, IOException {
       this.purchaseAmount = purchaseAmount;
        this.boughtProducts = boughtProducts;
        this.customer = customer;
        this.seller = seller;
        this.isByPocket = isByPocket;
        numberOfLogs++;
        orderNumber = numberOfLogs;
        customer.getBuyLogs().add(this);
        reduceMoneyOfCustomerPocket();

    }
    public void reduceMoneyOfCustomerPocket() throws NoMoneyInCustomerPocket, IOException {
        if (isByPocket) {
            double result = customer.getPocket()- purchaseAmount;
            if (result>0)
                customer.setPocket(result);
            else {
                throw new NoMoneyInCustomerPocket("no money");
            }
        }else {
            String token = Main.sendAndGetMessage("getToken,"+customer.getUsername()+"-"+seller.getUsername());
            String response = Main.sendAndGetMessage("transfer,"+"{token,"+token+"}{receiptType,move}{money,"+purchaseAmount+"}{sourceID,"+customer.numberOfBankAccount+"}{destID,"+seller.numberOfBankAccount+"}{description,a}");
            Main.sendAndGetMessage("ThisIsOUrBill,"+response);
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

    public String getBuyLogInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product boughtProduct : boughtProducts) {
            stringBuilder.append(boughtProduct.getProductId() + " - ");
        }
        return "Seller: " + seller.getUsername() + "   Customer: " + customer.getUsername() +
                "\n Purchased Amount: " + purchaseAmount + "   Bought Products Ids: \n" + stringBuilder.toString();

    }
}
