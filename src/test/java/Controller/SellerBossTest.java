package Controller;

import Controller.Exceptions.NullProduct;
import Controller.Exceptions.SoldProductsCanNotHaveChange;
import Controller.Exceptions.ThisIsNotYours;
import Model.Category;
import Model.Product;
import Model.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerBossTest {
private  Seller seller = new Seller("ali","reza","ghazan","ali@ali.a","2323","paass","mihan");
private   Product product = new Product("milk","mihan",2200.0,seller,3,new Category("labaniat",null),null);
    @Test
    public void sellerCredit() {
        seller.setMoney(233.0);
        Assert.assertEquals(233.0,seller.getMoney(),0.1);
    }

    @Test
    public void showCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("sabzi");
        categories.add("labaniat");
        ArrayList<String> specialls = new ArrayList<>();
        Category category = new Category("sabzi" ,specialls );
        Category category1 = new Category("labaniat" , specialls);
        Category.allCategories.add(category);Category.allCategories.add(category1);
            Assert.assertArrayEquals(categories.toArray(),SellerBoss.showCategories().toArray());
    }

    @Test
    public void showHistoryOfSales() {
    }

    @Test
    public void showProduct() {
        Product product = new Product("milk","mihan",2200.0,seller,3,new Category("labaniat",null),null);
        HashMap<String , String> attributes = new HashMap<>();
        attributes.put("color","red");
        attributes.put("weight","23");
        product.setSpecialAttributes(attributes);
        System.out.println(product.toString());
        try {
            Assert.assertEquals(SellerBoss.showProduct("1",seller),product.toString());
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        }
    }

    @Test
    public void addRequestProduct() {
        Assert.assertTrue(SellerBoss.addRequestProduct("milk","2200.0","3",null,"mihan",new Category("labaniat",null),seller));
    }

    @Test
    public void showBuyers() {
    }

    @Test
    public void removeProduct() {
       int identifier =0;
        try {
            Assert.assertTrue(SellerBoss.removeProduct("2",seller,identifier));
        } catch (ThisIsNotYours thisIsNotYours) {
            System.out.println(thisIsNotYours.getMessage());
            Assert.assertEquals(2,thisIsNotYours.getId());
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            System.out.println(soldProductsCanNotHaveChange.getMessage());
            Assert.assertEquals(3,soldProductsCanNotHaveChange.getId());
        } catch (NullProduct nullProduct) {
            System.out.println(nullProduct.getMessage());
            Assert.assertEquals(1,nullProduct.getId());
        }
    }

    @Test
    public void editProduct() {
    }

    @Test
    public void showOffs() {
    }

    @Test
    public void viewOff() {
    }

    @Test
    public void editOff() {
    }

    @Test
    public void addOff() {
    }
}