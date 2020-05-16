package Model;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class AccountTest {

    Seller seller = new Seller("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581", "company");
    @Test
    public void getIsConfirmedOrWaitForCheck() {
        Assert.assertEquals(seller.getIsConfirmedOrWaitForCheck(), "Wait For Accept" );
        Seller.allSellers.add(seller);
        Assert.assertEquals(seller.getIsConfirmedOrWaitForCheck(), "Accepted");
    }

    @Test
    public void getFullName() {
        Assert.assertEquals(seller.getFullName(), "namelastName");
    }

    @Test
    public void getCurrentSort() {
        Assert.assertEquals(Account.getCurrentSort(), "Nothing");
    }

    @Test
    public void getAccountWithUsername() {
        Account.allAccounts.add(seller);
        Assert.assertEquals(Account.getAccountWithUsername("username"), seller);
        Assert.assertEquals(Account.getAccountWithUsername("45"), null);
    }

    @Test
    public void isThereAccountWithUserName() {
        Account.allAccounts.add(seller);
        Assert.assertEquals(Account.isThereAccountWithUserName("username"), true);
        Assert.assertEquals(Account.isThereAccountWithUserName("df"), false);
    }

    @Test
    public void validatePassword() {
        Assert.assertEquals(seller.validatePassword("09100577581"), true);
        Assert.assertEquals(seller.validatePassword("09100577545"), false);
    }
}
