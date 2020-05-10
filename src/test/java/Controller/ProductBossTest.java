package Controller;

import Model.Customer;
import Model.Product;
import Model.Seller;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductBossTest {
    private Customer customer = new Customer("a","s","d","sdf","23","pass");
    private Product product = new Product("a","s",23,new Seller("a","s","ds","fd","sda","sd","sd"),7,null,null,null);
    @Test
    public void makeComment() {
        ProductBoss.makeComment("greate","qualtiy",product,customer);
        Assert.assertEquals(product.showComments(),null);
    }
}