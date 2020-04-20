package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account {
    public static ArrayList<Seller> allSellers;
    private ArrayList<Off> sellerOffs;
    private ArrayList<SellLog> sellLogs;
    private String companyName;
    private double money;
    private ArrayList<Product> salableProducts;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.companyName = companyName;
        sellerOffs = new ArrayList<Off>();
        sellLogs = new ArrayList<SellLog>();
        money = 0.0;
        salableProducts = new ArrayList<Product>();
        allSellers.add(this);
    }

    public HashMap<Product, Integer> getSalesHistory() {
        return null;
    }
    public boolean hasHeProductWithId(int productId) {
        return true;
    }
    public boolean isProductWithIdAtAnyOff(int productId) {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
    @Override
    public void deleteAccount() {

    }
    @Override
    public void getPersonalInfo() {
        this.toString();
    }
    @Override
    public void editPersonalField(String field) {

    }
}
