package Controller;

import Model.Product;
import Views.GoodPage;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductBoss {
    public void startCreateProduct(String name, String company, double price, String sellerName, int inventory, String categoryName) {

    }
    public void startEditProduct(String productId, String name, String company, double price, String sellerName, int inventory, HashMap<String, String> specialAttributes) {

    }
    public void startRemoveProduct(int productId) {

    }
    public void startRateProductByBuyer(int productId, int rate) {

    }
    public void addCommentToOnlineProduct(String text) {

    }


    public static GoodPage goToGoodPage(int id){
        Product product = Product.getProductWithId(id);
        GoodPage goodPage =    GoodPage.getGoodPage(product);
        return goodPage;
    }

}
