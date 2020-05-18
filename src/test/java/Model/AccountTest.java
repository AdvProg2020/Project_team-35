package Model;


import Controller.ManagerBoss;
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

    @Test
    public void isIsThereOnlineUser() {
        Assert.assertFalse(Account.isIsThereOnlineUser());
        Seller seller = new Seller("asd","dsa","Asd","Ads","ads","ads","asd");
        Account.setIsThereOnlineUser(true);
        Assert.assertTrue(Account.isIsThereOnlineUser());
    }

    @Test
    public void setIsThereOnlineUser() {
        Assert.assertTrue(Account.setIsThereOnlineUser(true));
    }

    @Test
    public void getOnlineAccount() {
        Seller seller = new Seller("ads","asd","Asd","Ads","asd","sd","ad");
       Account.setOnlineAccount(seller);
        Assert.assertEquals(Account.getOnlineAccount(),seller);
    }





    @Test
    public void getTypeOfAccount() {
        Seller seller = new Seller("ads","asd","Asd","Ads","asd","sd","ad");
        Customer customer = new Customer("as","asd","Asd","ads","sad","sad");
        Manager manager = new Manager("as","asd","Asd","ads","sad","sad");
        Assert.assertEquals(seller.getTypeOfAccount(),"Seller");
        Assert.assertEquals(customer.getTypeOfAccount(),"Customer");
        Assert.assertEquals(manager.getTypeOfAccount(),"Manager");
       Account account = new Account("Asd","ad","asd","asd","Asd","sadd",12) {
           @Override
           public String getPersonalInfo() {
               return null;
           }

           @Override
           public String getShortInfo() {
               return null;
           }
       };
        Assert.assertNull(account.getTypeOfAccount());


    }

    @Test
    public void testGetAccountWithUsername() {
    }

    @Test
    public void testIsThereAccountWithUserName() {
    }

    @Test
    public void setThisAccountLogged() {
    }

    @Test
    public void testValidatePassword() {
    }

    @Test
    public void getPersonalInfo() {
    }

    @Test
    public void getShortInfo() {
    }

    @Test
    public void setFirstName() {
    }

    @Test
    public void setLastName() {
    }

    @Test
    public void setEmail() {
    }

    @Test
    public void setPhoneNumber() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void isThereActiveAccountWithUserName() {
    }

    /*
    @Test
    public void getActiveAccountWithUserName() {
        Seller seller = new Seller("ads","asd","Asd","Ads","asd","sd","ad");
        Customer customer = new Customer("as","asd","Asd","ads","sad","sad");
        Manager manager = new Manager("as","asd","Asd","ads","sad","sad");
        ManagerBoss.getAllActiveUsers().add(seller);
        ManagerBoss.getAllActiveUsers().add(customer);
        for (Account activeUser : ManagerBoss.getAllActiveUsers()) {
            System.out.println(activeUser.getUsername());
        }
        Assert.assertEquals(Account.getActiveAccountWithUserName("as"),customer);
    }

     */

    @Test
    public void getAllAccounts() {
    }

    @Test
    public void testGetIsConfirmedOrWaitForCheck() {
    }

    @Test
    public void testGetTypeOfAccount() {
        Seller seller = new Seller("ads","asd","Asd","Ads","asd","sd","ad");
        Customer customer = new Customer("as","asd","Asd","ads","sad","sad");
        Manager manager = new Manager("as","asd","Asd","ads","sad","sad");

        Assert.assertEquals(Account.getTypeOfAccount(seller),3,0);
        Assert.assertEquals(Account.getTypeOfAccount(customer),2,0);
        Assert.assertEquals(Account.getTypeOfAccount(manager),1,0);

    }

    @Test
    public void testGetFullName() {
    }

    @Test
    public void testGetCurrentSort() {
    }
    @Test
    public void testWhatTypeIsOnline(){
        Seller seller = new Seller("ads","asd","Asd","Ads","asd","sd","ad");
        Customer customer = new Customer("as","asd","Asd","ads","sad","sad");
        Manager manager = new Manager("as","asd","Asd","ads","sad","sad");

        Account.setOnlineAccount(seller);
        Assert.assertEquals(Account.whatTypeIsOnline(),3);
        Account.setOnlineAccount(manager);
        Assert.assertEquals(Account.whatTypeIsOnline(),1);
        Account.setOnlineAccount(customer);
        Assert.assertEquals(Account.whatTypeIsOnline(),2);
    }

    @Test
    public void setCurrentSort() {
    }
}
