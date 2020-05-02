package Model;

import java.util.HashMap;

public class AddProductRequest extends Request {
    private String name;
    private String company;
    private int inventory;
    private double price;
    private Category category;
    private HashMap<String,String> specials;

    public AddProductRequest(Seller seller, String name, String company, int inventory, double price, Category category, HashMap<String, String> specials) {
        super(seller);
        this.name = name;
        this.company = company;
        this.inventory = inventory;
        this.price = price;
        this.category = category;
        this.specials = specials;
    }

    public String getDetails() {
        return "ADD PRODUCT Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

    }
    public void execute() {
        this.isDone = true;
        Product product = new Product(name,company,price,seller,inventory,category,specials);
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
    }
    public String getRequestInfo() {
        return "  ADD PRODUCT Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();

    }
}
