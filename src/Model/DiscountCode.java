package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes;
    private int discountCodeId;
    private String finalDate;
    private double discountPercent;
    private double maximumAvailableDiscount;
    private int availableUseFrequent;
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;
    private static int discountCodeNumber;

    public void editDiscountCode(double discountPercent, double maximumAvailableDiscount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {

    }
    public boolean canHeUseDiscountCode(Customer customer) {
        return true;
    }
    public static void removeDiscountCode() {

    }
    public static boolean isThereDiscountCodeWithId(String id) {
        return true;
    }
    public DiscountCode getDiscountCodeWithId(String id) {
        return null;
    }
    public static void deleteDiscountCode () {

    }

}
