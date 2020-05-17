package Model;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SellerTest {
    Seller seller = new Seller("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581", "company");

    @Test
    public void getUsername() {
        Assert.assertEquals(seller.getUsername(), "username");
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
    }

    @Test
    public void getAllSellers() {
        ArrayList<Seller> all = new ArrayList<>();
        all.add(seller);
        Seller.allSellers.add(seller);
        Assert.assertEquals(Seller.getAllSellers().get(0), all.get(0));
    }

    @Test
    public void getSalesHistory() {
    }

    @Test
    public void hasHeProductWithId() {
    }

    @Test
    public void isProductWithIdAtAnyOff() {
    }

    @Test
    public void deleteAccount() {
    }

    @Test
    public void setMoney() {
        Assert.assertTrue(seller.setMoney(21));
    }

    @Test
    public void testGetUsername() {
        Assert.assertEquals(seller.getUsername(),"username");
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
    public void getSalableProducts() {
        Product product = new Product("la","lala",23,seller,3,new Category("as",null),null,"");
        Product product1 = new Product("kal","nak",243,seller,4,new Category("kal",null),null,"");
        
        Assert.assertEquals(seller.getSalableProducts(),null);
    }

    @Test
    public void getSellerOffs() {
    }

    @Test
    public void testGetShortInfo() {
    }

    @Test
    public void testGetAllSellers() {
    }

    @Test
    public void addOffTest() {
    }
}
