package Model;

import Controller.NotEnoughMoney;
import Main.Main;
import javafx.scene.paint.Paint;
import sun.nio.cs.ext.ISO2022_KR;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction implements Serializable {
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
    private ArrayList<String> listOfMessages;
    public Auction(Seller seller,Product product,Double basicPrice, Date startTime, Date finalTime) {
        this.seller = seller;
        listOfMessages = new ArrayList<>();
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

    public void addAmountOfOfferedMoney(Customer customer , Double extraAmount) throws NotEnoughMoney {
        if (moneyWhichAreOffered.get(customer)+extraAmount>customer.getPocket()){
            throw new NotEnoughMoney("not enough money");
        }
        moneyWhichAreOffered.put(customer,moneyWhichAreOffered.get(customer)+extraAmount);
    }
    public void addCustomerToAuction(Customer customer , Double offeredMoney){
        moneyWhichAreOffered.put(customer,offeredMoney);
        listOfCustomersWhoAreInAuction.add(customer);
    }
    public Customer finishAuction() {
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
        product.setInventory(product.getInventory()-1);
        if (product.getInventory()==0){
            seller.getSalableProducts().remove(product);
        }
        String sellerBankID = seller.getNumberOfBankAccount();
        String CustomerBankID = customer.getNumberOfBankAccount();
        double moneyToSeller = money;
        seller.setPocket(seller.getPocket()+moneyToSeller);
        customer.setPocket(customer.getPocket()-moneyToSeller);
        customer.getAuctionProducts().add(product);
    }

    public static ArrayList<Auction> getAllActiveAuction() {
        return allActiveAuction;
    }
    public String showInfo(){
        String result = "id:"+id+"\nname of seller:"+seller.getUsername()+"\nname of product:"+product.getName()+"\nbase price:"+basicPrice+"\nnumber of participator:"+listOfCustomersWhoAreInAuction.size()+"\n";
        return result;
    }
    public static String showAllAuctionsInfo(){
        String result = "";
        for (Auction auction : Auction.getAllActiveAuction()) {
            result+=auction.showInfo();
        }
        return result;
    }
    public static boolean isThereAuctionWithThisProduct(Product product){
        for (Auction auction : allActiveAuction) {
            if (auction.getProduct().equals(product))
                return true;
        }
        return false;
    }
    public static Auction getAuctionByID(int ID){
        for (Auction auction : allActiveAuction) {
            if (auction.getId()==ID)
                return auction;
        }
        return null;
    }

    public ArrayList<String> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(ArrayList<String> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }
    public double howManyCustomerOffered(Customer customer){
        for (Customer customer1 : moneyWhichAreOffered.keySet()) {
            if (customer1.equals(customer)){
                return moneyWhichAreOffered.get(customer);
            }
        }
        return 0.0;
    }
}
