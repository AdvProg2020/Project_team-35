package Model;

import java.util.HashMap;

public class AddProductRequest extends Request {
   private Product product;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.product = product;
    }

    public String getDetails() {
        return "ADD PRODUCT Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

    }
    public void execute() {
        this.isDone = true;

        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
    }
    public String getRequestInfo() {
        return "  ADD PRODUCT Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();

    }
}
