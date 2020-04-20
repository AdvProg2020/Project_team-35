package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public ArrayList<DiscountCode> discountCodes;
    private double money;
    public ArrayList<BuyLog> buyLogs;
    public HashMap<Product, Integer> cart;
    public static int newOrderNumber;

    /**
     * a constructor for customer is equal to accounts but it has some lists new.
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        money =0.0;
        allCustomers.add(this);
        discountCodes = new ArrayList<DiscountCode>();
        buyLogs = new ArrayList<BuyLog>();
        newOrderNumber+=1;
    }

    public boolean isThereProductWithIdInCart(int id) {
        return true;
    }
    public double getTotalPriceOFCart() {
        return 0;
    }
    public String getListOfOrders() {
      return null;
    }
    public String getOrderWithId(int id) {
        return null;
    }
    public HashMap<Product, Integer> getListOFProductsAtCart() {
        return cart;
    }
    public boolean isThereOrderWithId(int id) {
        return true;
    }
    public boolean hasHeBoughtProductWithId(int productId) {
        return true;
    }
    public void increaseProductAtCart(Product toIncrease) {

    }
    public void decreaseProductAtCart(Product toIncrease) {

    }
    public double getCartTotalPrice() {
        return 0;
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
