package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CustomerTest {
private Customer customer = new Customer("a","s","d","sdf","23","pass");
private Product product = new Product("a","s",23,new Seller("a","s","ds","fd","sda","sd","sd"),7,null,null);
    @Test
    public void getMoney() {
        customer.setMoney(22.0);
        Assert.assertEquals(22.0,customer.getMoney(),1);
    }
    @Test
    public void getTotalPriceOfCartTest(){
        HashMap<Product,Integer> cart = new HashMap<>();
        cart.put(product,3);
        customer.setCart(cart);
        Assert.assertEquals(66,customer.getTotalPriceOFCart(),1);
    }
}