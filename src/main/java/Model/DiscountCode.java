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
    private static String currentSort = "Nothing";
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;
    private double minimumTotalPriceForUse;

    public String getDetails() {
        String toReturn =  "Code: " + this.code + "\nStart Date: " + this.startDate.toString() + "\nExpire Date: " + this.finalDate.toString()
                + "\nDiscount Percent: " + this.getDiscountPercent() + "\nMaximum Discount: " + this.getMaximumAvailableAmount()
                +"Minimum Total Price For Use (-1 means it hasn't minimum): " + this.getMinimumTotalPriceForUse()
                + "\nUsable Rate: " + this.getAvailableUseFrequent() + "\nIncluded Customers | Use Rates: \n";
        for (Customer customer : this.includedBuyersAndUseFrequency.keySet()) {
            toReturn += " ** UserName: " + customer.getUsername() + " | Use Number: " + includedBuyersAndUseFrequency.get(customer);
        }
        return toReturn;
    }
    public DiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<Customer> includedCustomers, double minimumTotalPriceForUse) {
        this.code = code;
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.discountPercent = discountPercent;
        this.maximumAvailableAmount = maximumAvailableAmount;
        this.availableUseFrequent = availableUseFrequent;
        this.includedBuyersAndUseFrequency = new HashMap<>();
        this.minimumTotalPriceForUse = minimumTotalPriceForUse;
        for (Customer customer : includedCustomers) {
            this.includedBuyersAndUseFrequency.put(customer, 0);
        }
        allDiscountCodes.add(this);
    }
    public String expireDateToString() {
        return finalDate.getYear() + "-" + finalDate.getMonthValue() + "-" + finalDate.getDayOfMonth();
    }
    public String startDateToString() {
        return startDate.getYear() + "-" + startDate.getMonthValue() + "-" + startDate.getDayOfMonth();
    }
    public String getDiscountCodeInlineInfo() {
        return "Code: " + this.code + " --- Percent: " + this.discountPercent + " --- ExpireDate: " + this.expireDateToString();
    }

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

    public static String getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(String currentSort) {
        DiscountCode.currentSort = currentSort;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public double getMinimumTotalPriceForUse() {
        return minimumTotalPriceForUse;
    }

    public void setMinimumTotalPriceForUse(double minimumTotalPriceForUse) {
        this.minimumTotalPriceForUse = minimumTotalPriceForUse;
    }
}
