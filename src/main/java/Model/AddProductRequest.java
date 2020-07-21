package Model;

import java.io.Serializable;

public class AddProductRequest extends Request implements Serializable {
   private Product product;

    public AddProductRequest(Seller seller, Product product) {
        super(seller, "Add Product");
        this.product = product;
    }

    public String getDetails() {
        return "Add Product Request : \nRequestId: " + this.getRequestId() + "\nRequester Username: " + this.seller.getUsername()
                + "\nCategory: " + this.product.getCategory().getCategoryName() + "\nPrice: " + this.product.getPrice() + "\nCompany: " + this.product.getCompany()
                + "\nInventory: " + this.product.getInventory();

    }
    public boolean execute() {
        this.isDone = true;
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        seller.getSalableProducts().add(product);
        return true;
    }

    public String getRequestInfo() {
        return "  Add Product Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }

    public void decline() {
        this.product.setProductStatus(ProductAndOffStatus.DECLINED);
    }
}
