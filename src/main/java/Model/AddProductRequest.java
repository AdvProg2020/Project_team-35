package Model;

import java.util.HashMap;

public class AddProductRequest extends Request {
   private Product product;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.product = product;
    }

    public String getDetails() {
        return "Add Product Request : \nRequestId: " + this.getRequestId() + "\nRequester Username: " + this.seller.getUsername()
                + "\nCategory: " + this.product.getCategory() + "\nPrice: " + this.product.getPrice() + "\nCompany: " + this.product.getCompany()
                + "\nInventory: " + this.product.getInventory();

    }
    public void execute() {
        this.isDone = true;
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        seller.getSalableProducts().add(product);
    }
    public String getRequestInfo() {
        return "  Add Product Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }

    public void decline() {
        this.product.setProductStatus(ProductAndOffStatus.DECLINED);
    }
}
