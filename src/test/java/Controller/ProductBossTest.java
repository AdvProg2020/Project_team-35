package Controller;

import Controller.Exceptions.InvalidFieldForSort;
import Model.Customer;
import Model.Product;
import Model.ProductAndOffStatus;
import Model.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ProductBossTest {
    private Seller seller = new Seller("a","s","ds","fd","sda","sd","sd");
    private Customer customer = new Customer("a","s","d","sdf","23","pass");
    private Product product = new Product("a","s",23,seller,7,null,null,null);
    @Test
    public void makeComment() {
        ProductBoss.makeComment("greate","qualtiy",product,customer);
        Assert.assertEquals(product.showComments(),null);
    }

    @Test
    public void goToGoodPage() {
    }

    @Test
    public void sortProduct() {
        Product product2 = new Product("lal","als",23444,seller,21,null,null,"");
        Product product1 = new Product("ad","afsa",354,seller,22,null,null,"");

product1.setProductStatus(ProductAndOffStatus.CONFIRMED);
product.setProductStatus(ProductAndOffStatus.FOREDIT);
product2.setProductStatus(ProductAndOffStatus.CONFIRMED);
        try {
            ArrayList<String> a = new ArrayList<>();
            a.add("ad");

            a.add("lal");
            Assert.assertEquals(ProductBoss.sortProduct("name"),a);
            for (String s : ProductBoss.sortProduct("name")) {
                System.out.println(s);
            }
        } catch (InvalidFieldForSort invalidFieldForSort) {
            Assert.assertEquals(invalidFieldForSort.getId(),1);
        }
    }

    @Test
    public void showAttributes() {
    }

    @Test
    public void compare() {
    }

    @Test
    public void testMakeComment() {
    }

    @Test
    public void showSummeryOfProductDetails() {
    }

    @Test
    public void showComments() {
    }

    @Test
    public void updateReviewNumberOfAProductPage() {
    }
}