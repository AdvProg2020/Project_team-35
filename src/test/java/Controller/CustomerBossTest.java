package Controller;

import Model.Customer;
import Model.DiscountCode;
import Model.Product;
import Model.Seller;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerBossTest extends TestCase {
    private Customer customer = new Customer("ali","reza","zare","ali@a.w","23","pass");
   private Product product = new Product("s","sd",23,new Seller("a","s","sd","asd","23","we","sd"),2,null,null,null);
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

    @Test
    public void testShowDiscountCodes() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        DiscountCode discountCode = new DiscountCode("1", LocalDateTime.MAX, LocalDateTime.MIN, 30, 40, 5, customers, -1);
        ArrayList<String> details = new ArrayList<>();
        details.add("id: 1");
        details.add("final date: " + LocalDateTime.MAX);
        details.add("discount percent: 30");
        details.add("maximum amount: 40");
        details.add("available use frequents: 5\n");
        Assert.assertEquals(details, CustomerBoss.showDiscountCodes(customer));
    }

    @Test
    public void testShowMoney() {
        customer.setMoney(10.0);
        Assert.assertEquals(10.0, CustomerBoss.showMoney(customer), 0);
    }
}