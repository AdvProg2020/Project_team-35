package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction {
    private static ArrayList<Auction> allActiveAuction = new ArrayList<>();
    private Seller seller;
    private Date startTime;
    private Date finalTime;
    private static int numberOfAuction;
    private int id;
    private HashMap<Customer,Double> moneyWhichAreOffered;
    private ArrayList<Customer> listOfCustomersWhoAreInAuction;
    private Double basicPrice;
    private Product product;
    public Auction(Seller seller,Product product,Double basicPrice, Date startTime, Date finalTime) {
        this.seller = seller;
        this.startTime = startTime;
        this.finalTime = finalTime;
        this.product = product;
        this.basicPrice = basicPrice;
        numberOfAuction++;
        id = numberOfAuction;
        listOfCustomersWhoAreInAuction = new ArrayList<>();
        moneyWhichAreOffered = new HashMap<>();
        allActiveAuction.add(this);
    }

    public Seller getSeller() {
        return seller;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinalTime() {
        return finalTime;
    }

    public static int getNumberOfAuction() {
        return numberOfAuction;
    }

    public int getId() {
        return id;
    }

    public HashMap<Customer, Double> getMoneyWhichAreOffered() {
        return moneyWhichAreOffered;
    }

    public ArrayList<Customer> getListOfCustomersWhoAreInAuction() {
        return listOfCustomersWhoAreInAuction;
    }

    public Double getBasicPrice() {
        return basicPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void addAmountOfOfferedMoney(Customer customer , Double extraAmount){
        moneyWhichAreOffered.put(customer,moneyWhichAreOffered.get(customer)+extraAmount);
    }
    public void addCustomerToAuction(Customer customer , Double offeredMoney){
        moneyWhichAreOffered.put(customer,offeredMoney);
    }
    public Customer finishAuction(){
        int numberOfMaximum = 0;
        double max = 0;
        Customer customerOfMax = null;
        for (Customer customer : moneyWhichAreOffered.keySet()) {
            if (max<moneyWhichAreOffered.get(customer)){
                max = moneyWhichAreOffered.get(customer);
                customerOfMax = customer;
            }else if (max == moneyWhichAreOffered.get(customer)){
                numberOfMaximum++;
            }
        }
        if (numberOfMaximum==1){
            payment(customerOfMax,moneyWhichAreOffered.get(customerOfMax));
            return customerOfMax;
        }else {
            return null;
        }
    }
    public void payment(Customer customer , Double money){

    }
}
