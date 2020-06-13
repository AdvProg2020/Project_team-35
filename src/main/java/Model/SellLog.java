package Model;

import java.util.ArrayList;

public class SellLog extends Log {

    private double receivedAmount;
    private double offDiscountAmount;
    private ArrayList<Product> soldProducts;

    private Seller seller;
    private Customer buyer;
    private boolean isReceived;

    public SellLog(ArrayList<Product> soldProducts, Customer buyer,Seller seller) {
        this.soldProducts = soldProducts;
        this.buyer = buyer;
        this.seller = seller;
        seller.getSellLogs().add(this);
        offDiscountAmount = 0;
        addMoneyToSeller();

    }
    public void addMoneyToSeller(){
        for (Product product : soldProducts) {
            if (Off.isThereProduct(product)){
                if (seller.getSalableProducts().contains(product)){
                    Off off = seller.findOffWithAProduct(product);
                    offDiscountAmount+= off.countOff(product);
                    seller.setMoney(seller.getMoney()+off.countOff(product));
                }
            }
            else {
                if (seller.getSalableProducts().contains(product)){
                    offDiscountAmount+=product.getPrice();
                    seller.setMoney(seller.getMoney()+product.getPrice());
                }
            }
        }
    }

    public double getOffDiscountAmount() {
        return offDiscountAmount;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public Integer getNumberOfProducts(Product product){
        int i = 0;
        for (Product soldProduct : getSoldProducts()) {
            if (soldProduct.equals(product))
                i++;
        }
        return i;
    }
    public ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }
}
