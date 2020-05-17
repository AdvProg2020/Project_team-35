package Model;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

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
}
