package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public ArrayList<DiscountCode> discountCodes;
    private double money;
    public ArrayList<BuyLog> buyLogs;
    public HashMap<Product, Integer> cart;
    public static int newOrderNumber = 0;

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
        super(username, firstName, lastName, email, phoneNumber, password,2);
        allCustomers.add(this);
        discountCodes = new ArrayList<DiscountCode>();
        buyLogs = new ArrayList<BuyLog>();
        cart = new HashMap<>();
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
    public String getPersonalInfo() {
        String toReturn = "Type: Customer\n" +
                "Username: %s\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "PhoneNumber: %s\n";
        toReturn = String.format(toReturn, this.getUsername(), this.getFirstName() + this.getLastName() + this.getEmail() + this.getPhoneNumber());
        return toReturn;
    }
    @Override
    public void editPersonalField(String field) {

    }
}
