package Controller;

import Model.Customer;
import Model.Product;
import Model.Seller;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class CustomerBossTest extends TestCase {
    private Customer customer = new Customer("ali","reza","zare","ali@a.w","23","pass");
   private Product product = new Product("s","sd",23,new Seller("a","s","sd","asd","23","we","sd"),2,null,null);
    @Test
    public void testShowProductsInCart() {
        HashMap<Product,Integer> cart = new HashMap<>();
        cart.put(product,2);
        customer.setCart(cart);
        Assert.assertEquals(CustomerBoss.showProductsInCart(customer),"s\nwith id : 1\nwith number : 2\n");
    }
    @Test
    public void testShowTotalPrice(){
        HashMap<Product,Integer> cart = new HashMap<>();
        cart.put(product,2);
        customer.setCart(cart);
        Assert.assertEquals(46.0,CustomerBoss.showTotalCartPrice(customer),1);
    }
}