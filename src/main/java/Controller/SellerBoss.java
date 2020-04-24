package Controller;

import Model.Category;
import Model.Product;
import Model.SellLog;
import Model.Seller;

import java.util.ArrayList;

public class SellerBoss {
    public static double sellerCredit(Seller seller){
        return seller.getMoney();
    }
    public static ArrayList<String> showCategories(){
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.allCategories) {
            categories.add(category.getCategoryName());
        }
        return categories;
    }
    public static ArrayList<String> showHistoryOfSales(Seller seller){
        ArrayList<String> sales = new ArrayList<>();
        for (SellLog log : seller.getSellLogs()) {
            for (Product soldProduct : log.getSoldProducts()) {
                sales.add(soldProduct.getName());
            }
        }
        return sales;
    }
}
