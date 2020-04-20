package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    public static ArrayList<Product> allProducts;
    private int productId;
    private static int productNumber;
    private ProductAndOffStatus productStatus;
    private String name;
    private String company;
    private double price;
    private Seller seller;
    private int inventory;
    private Category category;
    private ArrayList<Comment> commentsList;
    private ArrayList<Rate> ratesList;
    private HashMap<String, String> specialAttributes;
    private Product onlineProduct;
    //when the page of product is open.
    public static void rateProduct(int productId, int rate) {

    }
    public static boolean isThereProductWithId(String Id) {
        return true;
    }
    public static Product getProductWithId(){
        return null;
    }
    private void updateProductAverageRate(int productId) {

    }
    public void editProduct(String newName, String newCompany, double newPrice, int inventory, HashMap<String, String> newSpecialAttributes) {

    }



    public void sortList(HashMap<String, Double> doubleList, HashMap<String, Integer> intList, HashMap<String, String> stringList) {

    }
    public void filterList(HashMap<String, Double> doubleList, HashMap<String, Integer> intList, HashMap<String, String> stringList) {

    }

}
