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

    /**
     * 2 exception if productId be invalid and if product number be 0.
     *
     * @param id
     * @param customer
     * @return
     * @throws NullProduct
     * @throws ProductIsFinished
     */
    public static boolean increaseNumber(int id, Customer customer, int increase) throws NullProduct, ProductIsFinished {
        Product product = Product.getProductWithId(id);
        if (product == null) {
            throw new NullProduct("this product does not exist", 1);
        }
        if (product.getInventory() == 0 && increase == 1) {
            throw new ProductIsFinished(2, "not enough product");
        }
        if (!customer.getListOFProductsAtCart().keySet().contains(product)) {
            throw new NullProduct("you don't have this in your cart", 3);
        }
        product.setInventory(product.getInventory() - increase);
        int number = customer.getListOFProductsAtCart().get(product);
        customer.getListOFProductsAtCart().put(product, number + increase);
        if (number + increase == 0 && increase == -1) {
            customer.getListOFProductsAtCart().remove(product);
        }
        return true;
    }

    /**
     * alireza add it
     *
     * @param customer
     * @return
     */
    public static String showProductsInCart(Customer customer) {
        String result = "";
        for (Product product : customer.getListOFProductsAtCart().keySet()) {
            result += product.getName() + "\nwith id : " + product.getProductId() + "\nwith number : " + customer.getListOFProductsAtCart().get(product) + "\n";
        }
        return result;
    }

    public static double showTotalCartPrice(Customer customer) {
            return customer.getTotalPriceOFCart();
    }

    public static boolean hasDiscountCodeWithId(Customer customer, String id) {
        for (DiscountCode discountCode : customer.discountCodes) {
            if (id.equals(discountCode.getId()))
                return true;
        }
        return false;
    }

    public static double getDiscountAmount(DiscountCode discountCode, double totalPriceOfCart) {
        return totalPriceOfCart * discountCode.getDiscountPercent() / 100.0;
    }

    public static void useDiscountCode(Customer customer, String id) {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithId(id);
        double discountAmount = getDiscountAmount(discountCode, customer.getTotalPriceOFCart());
        if (discountAmount > discountCode.getMaximumAvailableAmount()) {
            customer.setPaymentAmount(customer.getTotalPriceOFCart() - discountCode.getMaximumAvailableAmount());
        }
        else customer.setPaymentAmount(customer.getTotalPriceOFCart() - discountAmount);
        discountCode.includedBuyersAndUseFrequency.put(customer, discountCode.includedBuyersAndUseFrequency.get(customer) - 1);
    }

    public static void dontUseDiscountCode(Customer customer) {
        customer.setPaymentAmount(customer.getTotalPriceOFCart());
    }

    public static boolean doPayment(Customer customer) {
        ArrayList<Product> products = new ArrayList<>();
        if (customer.getMoney() < customer.getPaymentAmount())
            return false;
        else {
            customer.setMoney(customer.getMoney() - customer.getPaymentAmount());
            for (Seller seller : Seller.getAllSellers()) {
                for (Product product : customer.cart.keySet()) {
                    if (product.getSeller() == seller) {
                        products.add(product);
                    }

                    new SellLog(customer.getPaymentAmount(), customer.getTotalPriceOFCart() - customer.getPaymentAmount(), products, customer.getUsername());
                    new BuyLog(customer.getPaymentAmount(), customer.getTotalPriceOFCart() - customer.getPaymentAmount(), products, seller.getUsername());
                }
            }
            return true;
        }
    }

}
