package Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class AddProductRequestTest {
    Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
    Category category = new Category("labaniat",null);
    Product product = new Product("a","mihan",23,seller1,10,category,null,null);
    AddProductRequest addProductRequest = new AddProductRequest(seller1, product);


    @Test
    public void getDetails() {
        Assert.assertEquals("Add Product Request : \nRequestId: " + 1 + "\nRequester Username: " + "a"
                + "\nCategory: " + "labaniat" + "\nPrice: " + 23.0 + "\nCompany: " + "mihan"
                + "\nInventory: " + 10, addProductRequest.getDetails());
    }

    @Test
    public void execute() {
        Assert.assertEquals(addProductRequest.execute(), true);
    }

    @Test
    public void getRequestInfo() {
        Assert.assertEquals("  Add Product Request --- UserName: " + "a" + " --- RQId: " + 3, addProductRequest.getRequestInfo());

    }
}
