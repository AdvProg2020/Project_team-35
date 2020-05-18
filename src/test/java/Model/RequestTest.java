package Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void getCurrentSort() {
        Assert.assertEquals(Request.getCurrentSort(), "Nothing");
    }

    @Test
    public void getTypeOfRequest() {
        Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
        SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(seller1);
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
        AddOffRequest addOffRequest = new AddOffRequest(seller1, off);
        Category category = new Category("labaniat",null);
        Product product = new Product("a","mihan",23,seller1,10,category,null,null);
        AddProductRequest addProductRequest = new AddProductRequest(seller1, product);
        EditOffRequest editOffRequest = new EditOffRequest(seller1, off, 1, 1, localDateTime, localDateTime2);
        EditProductRequest editProductRequest = new EditProductRequest(seller1, product, "a", "a", 1, 1, new HashMap<>(), category);


        Assert.assertEquals(sellerRegisterRequest.getTypeOfRequest(), "Seller Register");
        Assert.assertEquals(addOffRequest.getTypeOfRequest(), "Add Off");
        Assert.assertEquals(addProductRequest.getTypeOfRequest(), "Add Product");
        Assert.assertEquals(editOffRequest.getTypeOfRequest(), "Edit Off");
        Assert.assertEquals(editProductRequest.getTypeOfRequest(), "Edit Product");


    }
}
