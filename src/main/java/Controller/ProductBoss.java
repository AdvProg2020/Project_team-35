package Controller;

import Controller.Exceptions.NullProduct;
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


    public static GoodPage goToGoodPage(int id) throws NullProduct {
        Product product = Product.getProductWithId(id);
        GoodPage goodPage =    GoodPage.getGoodPage(product);
        if (goodPage==null){
            throw new NullProduct("this product does not exist",1);
        }
        return goodPage;
    }
    public static ArrayList<Product> sortProduct(String field){
        ArrayList<Product> all = new ArrayList<>();
        all = Product.getAllProducts();
        for (int i =0;i<all.size()-1;i++){
            for (int j =0;j<all.size();j++){
                String field1 = null;
                String field2 = null;
                    if (field.equalsIgnoreCase("price")){

                    }
            }
        }
        return null;
    }
    public static String showSummeryOfProductDetails(Product product){
            return product.showSummeryDetailsOfProduct();
    }

}
