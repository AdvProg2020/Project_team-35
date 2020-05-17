package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductTest {
   private Category category = new Category("labaniat",null);
    private Seller seller = new Seller("ali","hossein","amini","ali@ad.asd","23232","pass","mihan");
private Product product = new Product("a","mihan",23,seller,10,category,null,null);
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

    @Test
    public void rateProduct() {
    }

    @Test
    public void getWhoBoughtThisGood() {
        ArrayList<Customer> a = new ArrayList<>();
        Customer customer1 = new Customer("asd","ad","Ad","ad","Asd","ad");
        Customer customer2 = new Customer("asd","ad","Ad","ad","Asd","ad");
       product.getWhoBoughtThisGood().add(customer2);
       product.getWhoBoughtThisGood().add(customer1);
       a.add(customer2);
       a.add(customer1);
        Assert.assertEquals(product.getWhoBoughtThisGood(),a);
    }

    @Test
    public void isThereProductWithId() {
        Assert.assertTrue(Product.isThereProductWithId(1));
        Assert.assertFalse(Product.isThereProductWithId(11));
    }

    @Test
    public void getProductWithId() {
    }

    @Test
    public void getProductId() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getSeller() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void getAverageOfRates() {
    }

    @Test
    public void deleteProduct() {
    }

    @Test
    public void getSpecialAttributes() {
    }

    @Test
    public void getCompany() {
    }

    @Test
    public void getPrice() {
    }

    @Test
    public void getInventory() {
    }

    @Test
    public void getCategory() {
    }

    @Test
    public void getAllProducts() {
    }

    @Test
    public void setProductStatus() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void setCompany() {
    }

    @Test
    public void setPrice() {
    }

    @Test
    public void setInventory() {
    }

    @Test
    public void setCategory() {
    }

    @Test
    public void setSpecialAttributes() {
    }

    @Test
    public void getProductFieldForSort() {
    }

    @Test
    public void showSummeryDetailsOfProduct() {
    }

    @Test
    public void testAttributeShow() {
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void setCommentsList() {
    }

    @Test
    public void setRatesList() {
    }

    @Test
    public void setWhoBoughtThisGood() {
    }

    @Test
    public void getCommentsList() {
    }

    @Test
    public void showComments() {
    }

    @Test
    public void getProductStatus() {
    }

    @Test
    public void getReviewNumber() {
    }

    @Test
    public void setReviewNumber() {
    }

    @Test
    public void sortBuyers() {
    }
}