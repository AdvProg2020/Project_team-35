package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String code;
    private LocalDateTime finalDate;
    private LocalDateTime startDate;
    private double discountPercent;
    private double maximumAvailableAmount;
    private int availableUseFrequent;
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;

    public String getDetails() {
        String toReturn =  "Code: " + this.code + "\nStart Date: " + this.startDate.toString() + "\nExpire Date: " + this.finalDate.toString()
                + "\nDiscount Percent: " + this.getDiscountPercent() + "\nMaximum Discount: " + this.getMaximumAvailableAmount()
                + "\nUsable Rate: " + this.getAvailableUseFrequent() + "\nIncluded Customers | Use Rates: \n";
        for (Customer customer : this.includedBuyersAndUseFrequency.keySet()) {
            toReturn += " ** UserName: " + customer.getUsername() + " | Use Number: " + includedBuyersAndUseFrequency.get(customer);
        }
        return toReturn;
    }
    public DiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {
        this.code = code;
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.discountPercent = discountPercent;
        this.maximumAvailableAmount = maximumAvailableAmount;
        this.availableUseFrequent = availableUseFrequent;
        this.includedBuyersAndUseFrequency = new HashMap<>();
        for (Customer customer : includedCustomers) {
            this.includedBuyersAndUseFrequency.put(customer, 0);
        }
        allDiscountCodes.add(this);
    }
    public String expireDateToString() {
        return finalDate.getYear() + "-" + finalDate.getMonth() + "-" + finalDate.getDayOfMonth();
    }

    public String getDiscountCodeInlineInfo() {
        return "Code: " + this.code + " --- Percent: " + this.discountPercent + " --- ExpireDate: " + this.expireDateToString();
    }
//    public void editDiscountCode(double discountPercent, double maximumAvailableDiscount, int availableUseFrequent, ArrayList<Customer> includedCustomers) {
//
//    }
//    public boolean canUseDiscountCode(Customer customer) {
//        return true;
//    }
//    public static void removeDiscountCode() {
//
//    }
    public static boolean isThereDiscountCodeWithCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code))
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
//    public static void deleteDiscountCode () {
//
//    }
//    public static DiscountCode getDiscountCodeById(String id){
//        for (DiscountCode discountCode : allDiscountCodes) {
//            if (discountCode.getId().equalsIgnoreCase(id))
//                return discountCode;
//        }
//        return null;
//    }

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

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

}
