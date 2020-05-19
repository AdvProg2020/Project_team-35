package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductTest {
    private Category category = new Category("labaniat", null);
    private Seller seller = new Seller("ali", "hossein", "amini", "ali@ad.asd", "23232", "pass", "mihan");
    private Product product = new Product("a", "mihan", 23, seller, 10, category, null, null);
    private Customer customer = new Customer("mohammad", "moli", "molis", "sad@sad.s", "2323", "pass");

    @Test
    public void attributeShow() {
        HashMap<String, String> a = new HashMap<>();
        a.put("weghit", "23");
        a.put("color", "red");
        product.setDescription("this is so good");
        ArrayList<Customer> as = new ArrayList<>();
        as.add(customer);
        product.setWhoBoughtThisGood(as);
        ArrayList<Rate> d = new ArrayList<>();
        d.add(new Rate(customer, 2, product));
        product.setRatesList(d);
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        product.setSpecialAttributes(a);
        Assert.assertEquals(product.attributeShow(), a);
    }

    @Test
    public void rateProduct() {
    }

    @Test
    public void getWhoBoughtThisGood() {
        ArrayList<Customer> a = new ArrayList<>();
        Customer customer1 = new Customer("asd", "ad", "Ad", "ad", "Asd", "ad");
        Customer customer2 = new Customer("asd", "ad", "Ad", "ad", "Asd", "ad");
        product.getWhoBoughtThisGood().add(customer2);
        product.getWhoBoughtThisGood().add(customer1);
        a.add(customer2);
        a.add(customer1);
        Assert.assertEquals(product.getWhoBoughtThisGood(), a);
    }

    @Test
    public void isThereProductWithId() {
        Assert.assertTrue(Product.isThereProductWithId(1));
        Assert.assertFalse(Product.isThereProductWithId(11));
    }

    @Test
    public void getProductWithId() {
        Assert.assertEquals(Product.getProductWithId(1), product);
        Assert.assertNotEquals(Product.getProductWithId(2), product);
        Product product1 = new Product("asd", "asd", 23, seller, 3, new Category("sad", null), null, "");
        Assert.assertNotEquals(Product.getProductWithId(2), product);
    }

    @Test
    public void getProductId() {
        Assert.assertEquals(product.getProductId(), 1);
        Assert.assertNotEquals(product.getProductId(), 2);
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getSeller() {
        Assert.assertEquals(product.getSeller(), seller);
        Seller seller1 = new Seller("ads", "sd", "sad", "ads", "asd", "ad", "das");
        Assert.assertNotEquals(product.getSeller(), seller1);
    }

    @Test
    public void getName() {
        Assert.assertEquals(product.getName(), "a");
        Assert.assertNotEquals("b", product.getName());
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
        ArrayList<Product> a = new ArrayList<>();
        a.add(product);
        Product product1 = new Product("bahar", "mihan", 2, seller, 100, category, null, null);
        a.add(product1);
        Assert.assertEquals(Product.getProductFieldForSort("name"), a);
        a.remove(product);
        a.add(product);
        Assert.assertEquals(Product.getProductFieldForSort("inventory"), a);
        a.remove(product1);
        a.add(product1);
        Assert.assertEquals(Product.getProductFieldForSort("price"), a);
        Rate rate = new Rate(customer, 233, product1);
        Rate rate1 = new Rate(customer, 43, product);
        a.remove(product);
        a.add(product);
        Assert.assertEquals(a, Product.getProductFieldForSort("rate"));
        product1.setReviewNumber(3);
        product.setReviewNumber(2);
        Assert.assertEquals(a, Product.getProductFieldForSort("reviewNumber"));
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
        Comment comment = new Comment(product, "salam", customer, "quality");
        Comment comment1 = new Comment(product, "malemane", customer, "pool");

        Assert.assertNotNull(product.showComments());
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
        ArrayList<Customer> a = new ArrayList<>();
        a.add(customer);
        Customer customer1 = new Customer("mohammad1", "moli", "molis", "sad@sad.s", "2323", "pass");
        Customer customer2 = new Customer("mohammad2", "moli", "molis", "sad@sad.s", "2323", "pass");

        a.add(customer1);
        a.add(customer2);
        ArrayList<Product> b = new ArrayList<>();
        b.add(product);
        BuyLog buyLog = new BuyLog(233,12,b,seller.getUsername());
        customer.getBuyLogs().add(buyLog);
        product.getWhoBoughtThisGood().add(customer1);
        product.getWhoBoughtThisGood().add(customer);
        product.getWhoBoughtThisGood().add(customer2);
        for (Customer customer3 : product.getWhoBoughtThisGood()) {
            System.out.println(customer3.getUsername());
        }
        Assert.assertEquals(null, product.sortBuyers("number"));
    }
}