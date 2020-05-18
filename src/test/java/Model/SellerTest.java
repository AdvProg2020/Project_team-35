package Model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerTest {
    Seller seller = new Seller("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581", "company");

    @Test
    public void testGetUsername() {
        Assert.assertEquals(seller.getUsername(),"username");
        Assert.assertNotEquals(seller.getUsername(),"mamad");
    }

    @Test
    public void getPersonalInfo() {
        Assert.assertEquals(seller.getPersonalInfo(), "Type: Seller\n" +
                "Username: username"+"\n" +
                "Name: name"+"\n" +
                "Last Name: lastName"+"\n"+
                "Email: mail@e.ir"+"\n" +
                "PhoneNumber: 09100577581"+"\n"+
                "Company: company"+"\n"+
                "password: 09100577581");
    }

    @Test
    public void getCompanyName() {
        Assert.assertEquals(seller.getCompanyName(), "company");
    }


    @Test
    public void getMoney() {
        Assert.assertEquals(seller.getMoney(), 0.0, 0);
    }

    @Test
    public void getShortInfo() {
        Assert.assertEquals(seller.getShortInfo(), "UserName : username" + "  --  " + "Type : Seller" + " -- Condition: Wait For Accept");
        Assert.assertNotEquals(seller.getShortInfo(),null);
    }

    /*@Test
    public void getAllSellers() {
        ArrayList<Seller> all = new ArrayList<>();
        all.add(seller);
        Seller.allSellers.add(seller);
        Assert.assertEquals(Seller.getAllSellers().get(0), all.get(0));
    }

     */

    @Test
    public void getSalesHistory() {
        HashMap<Product,Integer> a = new HashMap<>();
        Product product = new Product("as","asd",23,seller,2,new Category("ad",null),null,"");
       ArrayList<Product> s = new ArrayList<>();
       s.add(product);
        SellLog sellLog = new SellLog(s,null);
        a.put(product,sellLog.getNumberOfProducts(product));
        seller.getSellLogs().add(sellLog);
        Assert.assertEquals(seller.getSalesHistory(),a);
    }

    @Test
    public void testGetSellerOffs(){
       // Assert.assertEquals(seller.getSellerOffs(),null);
        Product product = new Product("ad","asd",23,seller,2,new Category("sad",null),null,"");
        ArrayList<Product>a = new ArrayList<>();
        a.add(product);
        Off off = new Off(null,null,a,200,12,seller);

        ArrayList<Off> b = new ArrayList<>();
        b.add(off);
        for (Off activeOff : Off.allActiveOffs) {
            System.out.println(activeOff.getOffId());
        }
        Assert.assertEquals(seller.getSellerOffs(),b);
    }
    @Test
    public void hasHeProductWithId() {
        Product product = new Product("a","s",23,seller,2,new Category("a",null),null,"");

        System.out.println(product.getProductId());
        Assert.assertEquals(seller.hasHeProductWithId(1),2);
        Assert.assertEquals(seller.hasHeProductWithId(11),5);
        Seller seller1 = new Seller("asd","asd","das","ads","Ad","dsa","Asd");
        Product product1 = new Product("ads","ad",23,seller1,2,new Category("asd",null),null,"");
        Assert.assertEquals(seller.hasHeProductWithId(product1.getProductId()),7);
    }



    @Test
    public void testSetSellerOffs(){
        ArrayList<Off> a = new ArrayList<>();
        Product product = new Product("asd","ads",23,seller,0,new Category("asd",null),null,"");
        ArrayList<Product> s = new ArrayList<>();
        s.add(product);
        Off off = new Off(null,null,s,231,12,seller);
        a.add(off);
        Assert.assertTrue(seller.setSellerOffs(a));
        Off off1 = null;
        a.add(off1);
        Assert.assertFalse(seller.setSellerOffs(a));
    }

    @Test
    public void setMoney() {
        Assert.assertTrue(seller.setMoney(21));
        Assert.assertFalse(seller.setMoney(-23));
    }



    @Test
    public void testGetPersonalInfo() {
        Assert.assertNotNull(seller.getPersonalInfo());
    }

    @Test
    public void testGetCompanyName() {
        seller.setCompanyName("ali");
        Assert.assertEquals(seller.getCompanyName(),"ali");
    }

    @Test
    public void setCompanyName() {
      Assert.assertTrue(seller.setCompanyName("mamanama"));
    }

    @Test
    public void testGetMoney() {
        seller.setMoney(243);
        Assert.assertEquals(seller.getMoney(),243,1);
    }

    @Test
    public void getSellLogs() {
        SellLog sellLog = new SellLog(null,null);
        seller.getSellLogs().add(sellLog);
        ArrayList<SellLog> sellLogs = new ArrayList<>();
        sellLogs.add(sellLog);
        Assert.assertEquals(seller.getSellLogs(),sellLogs);
    }
    @Test
    public void testSetSalableProducts(){
        Product product = new Product("la","lala",23,seller,3,new Category("as",null),null,"");
        Product product1 = new Product("kal","nak",243,seller,0,new Category("kal",null),null,"");

        ArrayList<Product> a = new ArrayList<>();
        a.add(product);
        Assert.assertTrue(seller.setSalableProducts(a));
        a.add(product1);
        Assert.assertFalse(seller.setSalableProducts(a));
    }

    @Test
    public void getSalableProducts() {
        Product product = new Product("la","lala",23,seller,3,new Category("as",null),null,"");
        Product product1 = new Product("kal","nak",243,seller,4,new Category("kal",null),null,"");

        ArrayList<Product> a = new ArrayList<>();
        a.add(product);
        a.add(product1);
        Assert.assertEquals(seller.getSalableProducts(),a);
    }





    @Test
    public void testGetAllSellers() {
        ArrayList<Seller> a = new ArrayList<>();
        a.add(seller);
        Seller.allSellers.add(seller);
        Assert.assertEquals(Seller.getAllSellers(),a);
    }



}
