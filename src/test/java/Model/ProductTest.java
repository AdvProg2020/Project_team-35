package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductTest {
   private Category category = new Category("labaniat",null);
    private Seller seller = new Seller("ali","hossein","amini","ali@ad.asd","23232","pass","mihan");
private Product product = new Product("a","mihan",23,seller,10,category,null);
  private Customer customer = new Customer("mohammad","moli","molis","sad@sad.s","2323","pass");
    @Test
    public void attributeShow() {
        HashMap<String ,String> a = new HashMap<>();
        a.put("weghit","23");
        a.put("color","red");
        product.setDescription("this is so good");
        ArrayList<Customer> as = new ArrayList<>();
        as.add(customer);
        product.setWhoBoughtThisGood(as);
        ArrayList<Rate> d = new ArrayList<>();
        d.add(new Rate(customer,2,product));
        product.setRatesList(d);
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        product.setSpecialAttributes(a);
        Assert.assertEquals(product.attributeShow(),a);
    }
}