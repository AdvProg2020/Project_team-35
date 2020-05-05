package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes;
    private String id;
    private String finalDate;
    private double discountPercent;
    private double maximumAvailableAmount;
    private int availableUseFrequent;
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;
    private static int discountCodeNumber;

    public void editDiscountCode(double discountPercent, double maximumAvailableDiscount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {

    }
    public boolean canUseDiscountCode(Customer customer) {
        return true;
    }
    public static void removeDiscountCode() {

    }
    public static boolean isThereDiscountCodeWithId(String id) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.id.equals(id))
                return true;
        }
        return false;
    }
    public DiscountCode getDiscountCodeWithId(String id) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.id.equals(id))
                return discountCode;
        }
        return null;
    }
    public static void deleteDiscountCode () {

    }
    public static DiscountCode getDiscountCodeById(String id){
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getId().equalsIgnoreCase(id))
                return discountCode;
        }
        return null;
    }

    public String getId() {
        return id;
    }


    public String getFinalDate() {
        return finalDate;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public double getMaximumAvailableAmount() {
        return maximumAvailableAmount;
    }

    public int getAvailableUseFrequent() {
        return availableUseFrequent;
    }
}
