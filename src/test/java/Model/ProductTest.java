package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
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
        Assert.assertNotSame(product.attributeShow(),a);
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
        Product product = new Product("ads","sda",123,seller,2,category,null,"");


        Assert.assertTrue(Product.isThereProductWithId(product.getProductId()));
        Assert.assertFalse(Product.isThereProductWithId(1112));
    }

    @Test
    public void getProductWithId() {
        Product.getAllProducts().clear();
        Product product = new Product("ads","sda",123,seller,2,category,null,"");
        Assert.assertEquals(Product.getProductWithId(21), product);
        Assert.assertNotEquals(Product.getProductWithId(2), product);
        Product product1 = new Product("asd", "asd", 23, seller, 3, new Category("sad", null), null, "");
        Assert.assertNotEquals(Product.getProductWithId(2), product);
    }

    @Test
    public void getProductId() {
        Assert.assertEquals(product.getProductId(), 12);
        Assert.assertNotEquals(product.getProductId(), 2);
    }

    @Test
    public void testToString() {
        Product product = new Product("a","sda",123,seller,2,category,null,"");
        String productInfo = null;
        HashMap<String,String> a = new HashMap<>();
        a.put("color","red");
        product.setSpecialAttributes(a);
        ArrayList<Rate> b = new ArrayList<>();
        Rate rate1 = new Rate(customer,23,product);
        b.add(rate1);
        product.setRatesList(b);
        ArrayList<Comment> c = new ArrayList<>();
        Comment comment1 = new Comment(product,"sa",customer,"a");
        c.add(comment1);
        product.setCommentsList(c);
        productInfo = "name : " + product.getName() + "\n"
                + "company name : " + product.getCompany() + "\n"
                + "price : " + product.getPrice() + "\n"
                + "seller : " + seller.getUsername() + "\n"
                + "inventory : " + product.getInventory() + "\n";
        if (category!=null) {
            productInfo +="category : " + category.getCategoryName() + "\n";
        }
        productInfo+= "product id : " + product.getProductId() + "\n";
        productInfo += "special Attributes : \n";
        if (product.getSpecialAttributes() != null) {
            for (String s : product.getSpecialAttributes().keySet()) {
                productInfo = productInfo + s + " : " + product.getSpecialAttributes().get(s) + "\n";
            }
        }
        productInfo += "comment List : \n";
        for (Comment comment : product.getCommentsList()) {
            productInfo += comment.getCommentInfo();
        }
        productInfo += "rate list : \n";
        for (Rate rate : product.getRatesList()) {
            productInfo += rate.getRateInfo();
        }
        Assert.assertEquals(product.toString(),productInfo);

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
        Rate rate = new Rate(customer,4,product);
        Rate rate1 = new Rate(customer,2,product);
        Assert.assertEquals(3,product.getAverageOfRates(),1);
    }

    @Test
    public void deleteProduct() {
        ArrayList<Product> a = new ArrayList<>();
        a.add(product);
        ArrayList<Product> s = new ArrayList<>();
        Product product1 = new Product("ad","ads",231,seller,23,category,null,"");
        s.add(product1);
        Off off1 = new Off(null,null,s,233,24,seller);
        Off off = new Off(null,null,a,233,12,seller);
        Assert.assertTrue(Product.deleteProduct(product));
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
        Assert.assertTrue(product.setName("adss"));
    }

    @Test
    public void setCompany() {
        Assert.assertTrue(product.setCompany("ads"));
    }

    @Test
    public void setPrice() {
        Assert.assertTrue(product.setPrice(23));
        Assert.assertFalse(product.setPrice(-23));
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
        Product.getAllProducts().clear();
        Product product = new Product("a","sda",123,seller,2,category,null,"");
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
        Product product = new Product("a", "mihan", 23.0, seller, 10, category, null, "");
        String result = "description :\n" + "" + "\n" + "price :\n" + "23.0" + "\n" + "category :\n" + category.getCategoryName() + "\n" + "seller :\n" + seller.getUsername() + "\n" + "average :\n" + product.getAverageOfRates();

        Assert.assertEquals(result,product.showSummeryDetailsOfProduct());

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
        Comment comment = new Comment(product,"asd",customer,"sa");
        Comment comment1 = new Comment(product,"dsa",customer,"asd");
        ArrayList<Comment> a = new ArrayList<>();
        a.add(comment);
        a.add(comment1);
        product.setCommentsList(a);
        Assert.assertEquals(product.getCommentsList(),a);
    }

    @Test
    public void showComments() {
        Comment comment = new Comment(product, "salam", customer, "quality");
        Comment comment1 = new Comment(product, "malemane", customer, "pool");

        Assert.assertNotNull(product.showComments());
    }

    @Test
    public void getProductStatus() {
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        Assert.assertEquals(product.getProductStatus(),ProductAndOffStatus.CONFIRMED);
    }

    @Test
    public void getReviewNumber() {
        product.setReviewNumber(12);
        Assert.assertEquals(product.getReviewNumber(),12);
        Assert.assertNotEquals(product.getReviewNumber(),11);
    }

    @Test
    public void setReviewNumber() {
        Assert.assertTrue(product.setReviewNumber(12));
        Assert.assertFalse(product.setReviewNumber(-2));
    }

    @Test
    public void sortBuyers() {
        ArrayList<Customer> a = new ArrayList<>();
        a.add(customer);
        Customer customer1 = new Customer("mohammadzzz", "moli", "molis", "sad@sad.s", "2323", "pass");
        Customer customer2 = new Customer("mohammadz", "moli", "molis", "sad@sad.s", "2323", "pass");

        a.add(customer2);
        a.add(customer1);
        ArrayList<Product> b = new ArrayList<>();
        b.add(product);
        b.add(product);
        b.add(product);
       BuyLog buyLog = new BuyLog(233,12,b,seller.getUsername());
       buyLog.setDelivered(true);
        customer1.getBuyLogs().add(buyLog);


       product.getWhoBoughtThisGood().add(customer1);
        product.getWhoBoughtThisGood().add(customer);
        product.getWhoBoughtThisGood().add(customer2);


        for (Customer number : product.sortBuyers("username")) {
            System.out.println(number.getUsername());
        }
        Assert.assertEquals(a, product.sortBuyers("username"));


        ArrayList<Product> n = new ArrayList<>();
        n.add(product);
        n.add(product);

      BuyLog buyLog1 = new BuyLog(213,23,n,seller.getUsername());
      customer2.getBuyLogs().add(buyLog1);
        a.remove(customer1);
        a.remove(customer2);
        a.remove(customer);
        buyLog1.setDelivered(true);
        a.add(customer1);
        a.add(customer2);
        a.add(customer);
        for (Customer customer3 : a) {
            System.out.println(customer3.getUsername());
            System.out.println(customer3.getNumberOfBoughtProduct(product));
        }
        Assert.assertEquals(a,product.sortBuyers("number"));
    }

    @Test
    public void getRateList(){
        ArrayList<Rate> a = new ArrayList<>();
        Rate rate = new Rate(customer,23,product);
        a.add(rate);
        Assert.assertEquals(product.getRatesList(),a);
    }
}