package Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class EditProductRequestTest {
    Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
    Category category = new Category("labaniat",new ArrayList<>());
    Product product = new Product("a","mihan",23,seller1,10,category,new HashMap<>(),"null");
    EditProductRequest editProductRequest = new EditProductRequest(seller1, product, "a", "a", 1, 1, new HashMap<>(), category);


    @Test
    public void execute() {
        HashMap<String, String> newAttributes = new HashMap<>();
        newAttributes.put("salam", "chetori");
        EditProductRequest editProductRequest = new EditProductRequest(seller1, product, "a", "a", 1, 1, newAttributes, category);
        Assert.assertEquals(editProductRequest.execute(),true);

    }



    @Test
    public void testGetRequestInfo() {
        Assert.assertEquals("  Edit Product Request --- UserName: " + "a" + " --- RQId: " + 3, editProductRequest.getRequestInfo());

    }


}
