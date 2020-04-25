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

    public Product(String name, String company, double price, Seller seller, Category category, HashMap<String, String> specialAttributes) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.specialAttributes = specialAttributes;
    }

    public static void rateProduct(int productId, int rate) {

    }
    public static boolean isThereProductWithId(String Id) {
        return true;
    }

    /**
     * this is updated.
     * @param productId
     * @return
     */
    public static Product getProductWithId(int productId){
        for (Product product : allProducts) {
            if (product.productId == productId)
                return product;
        }
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

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productStatus=" + productStatus.name() +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                ", seller=" + seller.getFirstName()+" "+seller.getLastName()+
                ", inventory=" + inventory +
                ", category=" + category.getCategoryName() +
                '}';
    }

    public String getName() {
        return name;
    }
}
