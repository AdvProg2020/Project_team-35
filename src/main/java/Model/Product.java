package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    public static ArrayList<Product> allProducts = new ArrayList<>();
    private int productId;
    private static int productNumber;
    private ProductAndOffStatus productStatus;
    private String name;
    private String company;
    private double price;
    private Seller seller;
    private  int inventory;
    private Category category;
    private ArrayList<Comment> commentsList;
    private ArrayList<Rate> ratesList;
    private HashMap<String, String> specialAttributes;
    private Product onlineProduct;
    private ArrayList<Customer> whoBoughtThisGood;
    //when the page of product is open.


    public Product(String name, String company, double price, Seller seller, int inventory, Category category, HashMap<String, String> specialAttributes) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.inventory = inventory;
        this.category = category;
        this.specialAttributes = specialAttributes;
        productNumber+=1;
        productId = productNumber;
        commentsList = new ArrayList<>();
        ratesList = new ArrayList<>();
        allProducts.add(this);
        seller.getSalableProducts().add(this);
    }

    public static void rateProduct(int productId, int rate) {

    }

    public ArrayList<Customer> getWhoBoughtThisGood() {
        return whoBoughtThisGood;
    }

    public static boolean isThereProductWithId(int id) {
        for (Product product : allProducts) {
            if (product.productId == id)
                return true;
        }
        return false;
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




    public void sortList(HashMap<String, Double> doubleList, HashMap<String, Integer> intList, HashMap<String, String> stringList) {

    }
    public void filterList(HashMap<String, Double> doubleList, HashMap<String, Integer> intList, HashMap<String, String> stringList) {

    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        String productInfo = null;
        productInfo = "name : "+name+"\n"
                +"company name : "+company+"\n"
                +"price : "+price+"\n"
                +"seller : "+seller.getPersonalInfo()+"\n"
                +"inventory : "+inventory+"\n"
                +"category : "+category.getCategoryName()+"\n"
                +"product id : "+productId+"\n";
        productInfo += "special Attributes : \n";
        for (String s : specialAttributes.keySet()) {
            productInfo = productInfo + s+" : "+specialAttributes.get(s)+"\n";
        }
        productInfo+= "comment List : \n";
        for (Comment comment : commentsList) {
            productInfo+= comment.getCommentInfo();
        }
        productInfo+= "rate list : \n";
        for (Rate rate : ratesList) {
            productInfo+= rate.getRateInfo();
        }

        return productInfo;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }
    public static void deleteProduct(Product product){
        allProducts.remove(product);
        product.getSeller().getSalableProducts().remove(product);
    }

    public HashMap<String, String> getSpecialAttributes() {
        return specialAttributes;
    }

    public String getCompany() {
        return company;
    }

    public double getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public Category getCategory() {
        return category;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setProductStatus(ProductAndOffStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSpecialAttributes(HashMap<String, String> specialAttributes) {
        this.specialAttributes = specialAttributes;
    }
    public String getProductFieldForSort(String field){
        if (field.equalsIgnoreCase("inventory")||field.equalsIgnoreCase("price")||field.equalsIgnoreCase("")){

        }
        return null;
    }
}
