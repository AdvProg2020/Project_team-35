package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes;
    private String code;
    private LocalDateTime finalDate;
    private LocalDateTime startDate;
    private double discountPercent;
    private double maximumAvailableAmount;
    private int availableUseFrequent;
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;

    public DiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {
        this.code = code;
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.discountPercent = discountPercent;
        this.maximumAvailableAmount = maximumAvailableAmount;
        this.availableUseFrequent = availableUseFrequent;
        includedBuyersAndUseFrequency = new HashMap<>();
        for (Customer customer : includedCustomers) {
            this.includedBuyersAndUseFrequency.put(customer, 0);
        }
    }

    public void editDiscountCode(double discountPercent, double maximumAvailableDiscount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {

    }
    public boolean canUseDiscountCode(Customer customer) {
        return true;
    }
    public static void removeDiscountCode() {

    }
    public static boolean isThereDiscountCodeWithId(String id) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(id))
                return true;
        }
        return false;
    }
    public static DiscountCode getDiscountCodeWithCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code))
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
        return code;
    }


    public LocalDateTime getFinalDate() {
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
