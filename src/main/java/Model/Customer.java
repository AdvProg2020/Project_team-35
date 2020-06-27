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
    private double paymentAmount = 0;

    /**
     * a constructor for customer is equal to accounts but it has some lists new.
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 2);
        discountCodes = new ArrayList<DiscountCode>();
        buyLogs = new ArrayList<BuyLog>();
        cart = new HashMap<>();
        allCustomers.add(this);
    }

    public boolean isThereProductWithIdInCart(int id) {
        for (Product product : cart.keySet()) {
            if (product.getProductId() == id)
                return true;
        }
        return false;
    }

    public double getTotalPriceOFCart() {
        double totalPrice = 0.0;
        for (Product product : cart.keySet()) {
            if (product.getPriceWithOffEffect()==-1)
            totalPrice += product.getPrice() * cart.get(product);
            else {
                totalPrice += product.getPriceWithOffEffect() * cart.get(product);
            }
        }
        return totalPrice;
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

    /**
     * alireza add for test
     * @param cart
     */
    public void setCart(HashMap<Product, Integer> cart) {
        this.cart = cart;
    }

    public boolean isThereOrderWithId(int id) {
        return true;
    }

    public boolean hasBoughtProductWithId(int productId) {
        for (BuyLog buyLog : buyLogs) {
            for (Product boughtProduct : buyLog.boughtProducts) {
                if (boughtProduct.getProductId() == productId)
                    return true;
            }
        }
        return false;
    }

    public void increaseProductAtCart(Product toIncrease) {

    }

    public void decreaseProductAtCart(Product toIncrease) {

    }



    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    @Override
    public String getPersonalInfo() {
        return "Type: Customer\n" +
                "Username: " + this.getUsername() + "\n" +
                "Name: " + this.getFirstName() + " " + this.getLastName() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "PhoneNumber: " + this.getPhoneNumber() + "\n";
    }
    @Override
    public String getShortInfo() {
        return "UserName : " + this.getUsername() + " --- " + "Type : Customer";
    }

    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public double getMoney() {
        return money;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    public Integer getNumberOfBoughtProduct(Product product){
        int number = 0;
        for (BuyLog log : buyLogs) {
            if (log.isDelivered()){
                for (Product boughtProduct : log.getBoughtProducts()) {
                    if (boughtProduct.equals(product))
                        number++;
                }
            }
        }
        return number;
    }

    public static boolean isThereCustomerWithUsername(String username){
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    public static Customer getCustomerWithName(String username){
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equalsIgnoreCase(username))
                return customer;
        }
        return null;
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }
}

