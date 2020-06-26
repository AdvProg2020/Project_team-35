package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private SimpleStringProperty code = new SimpleStringProperty();
    private LocalDateTime finalDate;
    private LocalDateTime startDate;
    private SimpleStringProperty startDateSimpleString = new SimpleStringProperty();
    private SimpleStringProperty finalDateSimpleString = new SimpleStringProperty();
    private SimpleDoubleProperty discountPercent = new SimpleDoubleProperty();
    private double maximumAvailableAmount;
    private int availableUseFrequent;
    private static String currentSort = "Nothing";
    public HashMap<Customer, Integer> includedBuyersAndUseFrequency;
    private double minimumTotalPriceForUse;

    public String getDetails() {
        String toReturn = "Code: " + this.code.get() + "\nStart Date: " + this.startDate.toString() + "     Expire Date: " + this.finalDate.toString()
                + "\nDiscount Percent: " + this.getDiscountPercent() + "     Maximum Discount: " + this.getMaximumAvailableAmount()
                + "\nMinimum Total Price For Use (-1 means it hasn't minimum): " + this.getMinimumTotalPriceForUse()
                + "    Usable Rate: " + this.getAvailableUseFrequent() + "   Included Customers | Use Rates:";
        for (Customer customer : this.includedBuyersAndUseFrequency.keySet()) {
            toReturn += "\n ** UserName: " + customer.getUsername() + " | Use Number: " + includedBuyersAndUseFrequency.get(customer);
        }
        return toReturn;
    }

    public DiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<Customer> includedCustomers, double minimumTotalPriceForUse) {
        this.code.set(code);
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.discountPercent.set(discountPercent);
        this.maximumAvailableAmount = maximumAvailableAmount;
        this.availableUseFrequent = availableUseFrequent;
        this.includedBuyersAndUseFrequency = new HashMap<>();
        this.minimumTotalPriceForUse = minimumTotalPriceForUse;
        for (Customer customer : includedCustomers) {
            this.includedBuyersAndUseFrequency.put(customer, 0);
            customer.discountCodes.add(this);
        }
        allDiscountCodes.add(this);
        DiscountCode.setCurrentSort("Nothing");
        startDateSimpleString.set(startDate.toString());
        finalDateSimpleString.set(finalDate.toString());
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
            if (discountCode.code.get().equals(code))
                return true;
        }
        return false;
    }

    public static DiscountCode getDiscountCodeWithCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.get().equals(code))
                return discountCode;
        }
        return null;
    }

    public String getId() {
        return code.get();
    }


    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public double getDiscountPercent() {
        return discountPercent.get();
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


    public double getMinimumTotalPriceForUse() {
        return minimumTotalPriceForUse;
    }


    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public String getStartDateSimpleString() {
        return startDateSimpleString.get();
    }

    public SimpleStringProperty startDateSimpleStringProperty() {
        return startDateSimpleString;
    }

    public String getFinalDateSimpleString() {
        return finalDateSimpleString.get();
    }

    public SimpleStringProperty finalDateSimpleStringProperty() {
        return finalDateSimpleString;
    }

    public SimpleDoubleProperty discountPercentProperty() {
        return discountPercent;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setStartDateSimpleString(String startDateSimpleString) {
        this.startDateSimpleString.set(startDateSimpleString);
    }

    public void setFinalDateSimpleString(String finalDateSimpleString) {
        this.finalDateSimpleString.set(finalDateSimpleString);
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent.set(discountPercent);
    }

    public void setMaximumAvailableAmount(double maximumAvailableAmount) {
        this.maximumAvailableAmount = maximumAvailableAmount;
    }

    public void setAvailableUseFrequent(int availableUseFrequent) {
        this.availableUseFrequent = availableUseFrequent;
    }

    public void setIncludedBuyersAndUseFrequency(HashMap<Customer, Integer> includedBuyersAndUseFrequency) {
        this.includedBuyersAndUseFrequency = includedBuyersAndUseFrequency;
    }

    public void setMinimumTotalPriceForUse(double minimumTotalPriceForUse) {
        this.minimumTotalPriceForUse = minimumTotalPriceForUse;
    }

    public HashMap<Customer, Integer> getIncludedBuyersAndUseFrequency() {
        return includedBuyersAndUseFrequency;
    }
}