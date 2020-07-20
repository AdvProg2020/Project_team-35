package Controller;

import Controller.Exceptions.ExistenceOfUserWithUsername;
import Controller.Exceptions.LoginWithoutLogout;
import Controller.Exceptions.PasswordValidity;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountBossTest {

    private PasswordValidity passwordValidity;

    @Test
    public void firstStepOfRegistering() {
    }

    @Test
    public void makeAccount() {
    }

    @Test
    public void showPersonalInfoInUserPage() {
        Seller seller = new Seller("asd","asd","asd","ads","ads","Asd","ads");
        Customer customer = new Customer("asd","asd","asd","ads","ads","Asd");
        Manager manager = new Manager("asd","asd","asd","ads","ads","Asd");
        Assert.assertEquals(AccountBoss.showPersonalInfoInUserPage(seller),seller.getPersonalInfo());
        Assert.assertEquals(AccountBoss.showPersonalInfoInUserPage(manager),manager.getPersonalInfo());
        Assert.assertEquals(AccountBoss.showPersonalInfoInUserPage(customer),customer.getPersonalInfo());
        Account account = new Account("aa","aa","aa","aa","aa","aa",12) {
            @Override
            public String getPersonalInfo() {
                return null;
            }

            @Override
            public String getShortInfo() {
                return null;
            }
        };
        assertEquals(AccountBoss.showPersonalInfoInUserPage(account),account.getPersonalInfo());
    }

    @Test
    public void showCompanyInfo() {
    }

    @Test
    public void checkUsernameExistenceInLogin() {
        Customer customer = new Customer("ali","asd","asd","ad,","asd","asd");

        Seller seller = new Seller("asd","asd","ads","asd","ads","asd","asd");

        try {
            Assert.assertTrue(AccountBoss.checkUsernameExistenceInLogin("ali"));
           Assert.assertTrue(AccountBoss.checkUsernameExistenceInLogin("rezazza"));
        } catch (ExistenceOfUserWithUsername existenceOfUserWithUsername) {
            Assert.assertEquals(existenceOfUserWithUsername.getId(),2,0);
        }
        try {
        Assert.assertTrue(AccountBoss.checkUsernameExistenceInLogin("rezazza"));
    } catch (ExistenceOfUserWithUsername existenceOfUserWithUsername) {
        Assert.assertEquals(existenceOfUserWithUsername.getId(),2,0);
    }


        Account.setOnlineAccount(customer);
        try {
            Assert.assertTrue(AccountBoss.checkUsernameExistenceInLogin("ali"));
        } catch (ExistenceOfUserWithUsername existenceOfUserWithUsername) {
            Assert.assertEquals(existenceOfUserWithUsername.getId(),2,0);
        }

        try {
            Assert.assertTrue(AccountBoss.checkUsernameExistenceInLogin("asd"));
        } catch (ExistenceOfUserWithUsername existenceOfUserWithUsername) {
            Assert.assertEquals(existenceOfUserWithUsername.getId(),2,0);
        }

    }

    @Test
    public void checkPasswordValidity() {
        try {
            Seller seller = new Seller("ali","dsa","dsa","asd","ads","hadi","asd");
            Assert.assertTrue(AccountBoss.checkPasswordValidity("ali","hadi"));
        } catch (PasswordValidity passwordValidity) {
            passwordValidity.printStackTrace();
        }
        Seller seller = new Seller("ali","asd","Asd","ads","ads","pal","s");
        try {
            Assert.assertTrue(AccountBoss.checkPasswordValidity("ali","hashem"));
        } catch (PasswordValidity validity) {
            Assert.assertEquals(validity.getId(),1);
        }

    }

    @Test
    public void startLogin() {
        Seller seller = new Seller("ali","asd","asd","asd","ad","pass","asd");
        Assert.assertTrue(AccountBoss.startLogin("ali","pass"));
    }

    @Test
    public void startEditPersonalField() {
    }

    @Test
    public void logout() {

        Seller seller = new Seller("asd","asd","asd","ads","ads","Asd","ads");
        Assert.assertTrue(AccountBoss.logout(seller));
    }
}