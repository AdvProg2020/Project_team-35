package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.text.ParseException;
import java.util.ArrayList;

public class CustomerBoss {
    public static double showMoney(Customer customer) {
        return customer.getMoney();
    }

    public static ArrayList<String> showDiscountCodes(Customer customer) {
        ArrayList<String> discountCodesInformation = new ArrayList<>();
        for (DiscountCode discountCode : customer.discountCodes) {
            discountCodesInformation.add("id: " + discountCode.getId());
            discountCodesInformation.add("final date: " + discountCode.getFinalDate());
            discountCodesInformation.add("discount percent: " + discountCode.getDiscountPercent());
            discountCodesInformation.add("maximum amount: " + discountCode.getMaximumAvailableAmount());
            discountCodesInformation.add("available use frequents: " + discountCode.getAvailableUseFrequent());
        }
        return discountCodesInformation;
    }

    public static String showProductsInCart(Customer customer){
        String result = "";
        for (Product product : customer.getListOFProductsAtCart().keySet()) {
            result += product.getName()+"\nwith id : "+product.getProductId()+"\nwith number : "+customer.getListOFProductsAtCart().get(product)+"\n";
        }
        return result;
    }
}
