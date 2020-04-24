package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account {
    public static ArrayList<Seller> allSellers = new ArrayList<Seller>();
    private ArrayList<Off> sellerOffs;
    private ArrayList<SellLog> sellLogs;
    private String companyName;
    private double money;
    private ArrayList<Product> salableProducts;

    /**
     * a constructor for seller
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     * @param companyName
     */
    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password,3);
        this.companyName = companyName;
        sellerOffs = new ArrayList<Off>();
        sellLogs = new ArrayList<SellLog>();
        money = 0.0;
        salableProducts = new ArrayList<Product>();
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
    public void deleteAccount() {

    }
    @Override
    public String getPersonalInfo() {
        String toReturn = "Type: Seller\n" +
                "Username: "+getUsername()+"\n" +
                "Name: "+getFirstName()+"\n" +
                "Last Name: "+getLastName()+"\n"+
                "Email: "+getEmail()+"\n" +
                "PhoneNumber: "+getPhoneNumber()+"\n"+
                "Company: "+getCompanyName();
        return toReturn;
    }
    @Override
    public void editPersonalField(String field ,String changes) {

    }

    /**
     * update this method.
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
