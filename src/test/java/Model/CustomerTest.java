package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CustomerTest {
private Customer customer = new Customer("a","s","d","sdf","23","pass");
private Product product = new Product("a","s",23,new Seller("a","s","ds","fd","sda","sd","sd"),7,null,null,null);
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
        Assert.assertEquals(69,customer.getTotalPriceOFCart(),1);
    }
    @Test
    public void getListOFProductsAtCart() {
        HashMap<Product, Integer> map = new HashMap<>();
        map.put(product, 2);
        customer.cart.put(product, 2);
        Assert.assertEquals(customer.cart, map);
    }
    @Test
    public void getPaymentAmount() {
        customer.setPaymentAmount(10.0);
        Assert.assertEquals(10.0, customer.getPaymentAmount(), 1);
    }
    @Test
    public void isThereCustomerWithUsername() {
        Assert.assertTrue(Customer.isThereCustomerWithUsername("a"));
        Assert.assertFalse(Customer.isThereCustomerWithUsername("b"));
    }
    @Test
    public  void getCustomerWithName() {
        Assert.assertEquals(customer, Customer.getCustomerWithName("a"));
        Assert.assertNull(Customer.getCustomerWithName("b"));
    }
}