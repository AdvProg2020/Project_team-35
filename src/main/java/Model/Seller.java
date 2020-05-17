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
        Product product = Product.getProductWithId(productId);
        if (product==null)
            return false;
        if (salableProducts.contains(product))
            return true;
        return false;
    }


    @Override
    public void deleteAccount() {

    }

    /**
     * just for test
     * @param money
     */
    public boolean setMoney(double money) {
        if (money<0)
            return false;
        this.money = money;
        return true;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPersonalInfo() {
        return "Type: Seller\n" +
                "Username: "+getUsername()+"\n" +
                "Name: "+getFirstName()+"\n" +
                "Last Name: "+getLastName()+"\n"+
                "Email: "+getEmail()+"\n" +
                "PhoneNumber: "+getPhoneNumber()+"\n"+
                "Company: "+getCompanyName()+"\n"+
                "password: "+getPassword();
    }

    /**
     * update this method.
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    public boolean setCompanyName(String companyName) {
        this.companyName = companyName;
        return true;
    }

    public double getMoney() {
        return money;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public ArrayList<Product> getSalableProducts() {
        return salableProducts;
    }

    public ArrayList<Off> getSellerOffs() {
        return sellerOffs;
    }
    @Override
    public String getShortInfo() {
        return "UserName : " + this.getUsername() + "  --  " + "Type : Seller" + " -- Condition: " + getIsConfirmedOrWaitForCheck();
    }

    public static ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public void addOffTest(Off off){
        sellerOffs.add(off);
    }
}
