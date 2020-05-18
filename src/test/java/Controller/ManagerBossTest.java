package Controller;

import Controller.Exceptions.*;
import Model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ManagerBossTest {

    LocalDateTime localDateTimeo = LocalDateTime.parse("2010-10-19T22:02:23");
    LocalDateTime localDateTimeoo = LocalDateTime.parse("2021-10-19T22:02:23");
    LocalDateTime localDateTime = LocalDateTime.parse("2120-10-19T22:02:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2220-10-19T22:02:23");
    LocalDateTime localDateTime3 = LocalDateTime.parse("2320-10-19T22:02:23");
    LocalDateTime localDateTime4 = LocalDateTime.parse("2420-10-19T22:02:23");


    @Test
    public void sortDiscountCodesWithField() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        ManagerBoss.sortDiscountCodesWithField("percent-a");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Percent - Ascending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode1);
        ManagerBoss.sortDiscountCodesWithField("percent-b");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Percent - Descending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode2);
        ManagerBoss.sortDiscountCodesWithField("maximum-a");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Maximum Amount - Ascending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode1);
        ManagerBoss.sortDiscountCodesWithField("maximum-b");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Maximum Amount - Descending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode2);
        ManagerBoss.sortDiscountCodesWithField("frequent-a");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Available Frequent - Ascending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode1);
        ManagerBoss.sortDiscountCodesWithField("frequent-b");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Available Frequent - Descending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode2);
        ManagerBoss.sortDiscountCodesWithField("startdate-a");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Start Date - Ascending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode1);
        ManagerBoss.sortDiscountCodesWithField("startdate-b");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Start Date - Descending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode2);
        ManagerBoss.sortDiscountCodesWithField("finaldate-a");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Final Date - Ascending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode1);
        ManagerBoss.sortDiscountCodesWithField("finaldate-b");
        Assert.assertEquals(DiscountCode.getCurrentSort(), "Discount Code Final Date - Descending");
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().get(0), discountCode2);


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void checkStartDateAndFinalDateForDiscountCode() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");


        try {
            ManagerBoss.checkStartDateAndFinalDateForDiscountCode(localDateTime, localDateTimeo);
        } catch (DateException e) {
            Assert.assertTrue(true);
            Assert.assertEquals(e.getMessage(), "The end time is before now! Are you OK?! :)");
        }

        try {
            ManagerBoss.checkStartDateAndFinalDateForDiscountCode(localDateTime4, localDateTimeoo);
            Assert.assertTrue(false);
        } catch (DateException e) {
            Assert.assertTrue(true);
            Assert.assertEquals(e.getMessage(), "The end time is before start time! Are you OK?! :)");
        }
        try {
            Assert.assertEquals(ManagerBoss.checkStartDateAndFinalDateForDiscountCode(localDateTime, localDateTime2), true);
        } catch (DateException e) {
            fail();
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);



    }

    @Test
    public void sortCategoryWithField() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Assert.assertEquals(Category.getCurrentSort(), "Nothing");
        ManagerBoss.sortCategoryWithField("name-a");
        Assert.assertEquals(Category.getAllCategories().get(0), category1);
        ManagerBoss.sortCategoryWithField("name-b");
        Assert.assertEquals(Category.getAllCategories().get(0), category2);
        category1.getCategoryProducts().add(product1);
        ManagerBoss.sortCategoryWithField("size-a");
        Assert.assertEquals(Category.getAllCategories().get(0), category2);
        ManagerBoss.sortCategoryWithField("size-b");
        Assert.assertEquals(Category.getAllCategories().get(0), category1);
        Assert.assertEquals(ManagerBoss.sortCategoryWithField("name-a"), true);
        Assert.assertEquals(ManagerBoss.sortCategoryWithField("asasa-a"), false);


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);



    }

    @Test
    public void sortAccountWithField() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Seller seller2 = new Seller("bbbbbb", "b", "b", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Assert.assertEquals(Account.getCurrentSort(), "Nothing");
        ManagerBoss.sortAccountWithField("name-a");
        Assert.assertEquals(Account.getCurrentSort(), "Account FullName - Ascending");
        Assert.assertEquals(Account.getAllAccounts().get(0), seller2);
        ManagerBoss.sortAccountWithField("name-b");
        Assert.assertEquals(Account.getCurrentSort(), "Account FullName - Descending");
        Assert.assertEquals(Account.getAllAccounts().get(0), manager);
        ManagerBoss.sortAccountWithField("username-a");
        Assert.assertEquals(Account.getCurrentSort(), "Account Username - Ascending");
        Assert.assertEquals(Account.getAllAccounts().get(0), customer);
        ManagerBoss.sortAccountWithField("username-b");
        Assert.assertEquals(Account.getCurrentSort(), "Account Username - Descending");
        Assert.assertEquals(Account.getAllAccounts().get(0), manager);


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(seller2);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }


    @Test
    public void deleteDiscountCodeWithCode() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        try {
            ManagerBoss.deleteDiscountCodeWithCode("code");
        } catch (DiscountNotExist discountNotExist) {
            Assert.assertTrue(true);
        }

        try {
            ManagerBoss.deleteDiscountCodeWithCode("code1");
            Assert.assertTrue(true);
        } catch (DiscountNotExist discountNotExist) {
            fail();
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);

        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);

    }

    @Test
    public void checkExistenceOfCustomerUsername() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        try {
            ManagerBoss.checkExistenceOfCustomerUsername("alpha");
        } catch (NotExistCustomerWithUserNameException e) {
            Assert.assertTrue(true);
        }
        try {
            ManagerBoss.checkExistenceOfCustomerUsername("a");
            Assert.assertTrue(true);
        } catch (NotExistCustomerWithUserNameException e) {
            fail();
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);



    }

    @Test
    public void acceptRequestWithId() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(seller1);

        try {
            Assert.assertEquals(ManagerBoss.acceptRequestWithId(1), true);
            Assert.assertEquals(Manager.getNewRequests().contains(sellerRegisterRequest), true);
            Assert.assertEquals(Manager.getCheckedRequests().contains(sellerRegisterRequest), false);

        } catch (NotValidRequestIdException e) {
            fail();
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void declineRequestWithId() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(seller1);
        try {
            Assert.assertEquals(ManagerBoss.declineRequestWithId(1), true);
            Assert.assertEquals(Manager.getNewRequests().contains(sellerRegisterRequest), false);
            Assert.assertEquals(Manager.getCheckedRequests().contains(sellerRegisterRequest), true);

        } catch (NotValidRequestIdException e) {

        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void sortRequestsWithField() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Seller seller2 = new Seller("bbbbbb", "b", "b", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Assert.assertEquals(Request.getCurrentSort(), "Nothing");
        SellerRegisterRequest sellerRegisterRequest1 = new SellerRegisterRequest(seller1);
        SellerRegisterRequest sellerRegisterRequest2 = new SellerRegisterRequest(seller2);
        ManagerBoss.sortRequestsWithField("id-a");
        Assert.assertEquals(Manager.getNewRequests().get(0), sellerRegisterRequest1);
        ManagerBoss.sortRequestsWithField("id-b");
        Assert.assertEquals(Manager.getNewRequests().get(0), sellerRegisterRequest2);
        Assert.assertEquals(ManagerBoss.sortRequestsWithField("df"), false);


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(seller2);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);



    }

    @Test
    public void getAllActiveUsers() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
         Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Assert.assertEquals(ManagerBoss.getAllActiveUsers().size(), 2);
        Manager manager1 = new Manager("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581");
        Assert.assertEquals(ManagerBoss.getAllActiveUsers().size(), 3);


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Manager.getAllManagers().remove(manager1);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void removeProductWithId() {


        try {
            ManagerBoss.removeProductWithId(25);
        } catch (ThereISNotProductWithIdException e) {
            Assert.assertTrue(true);
        }

        try {
                ManagerBoss.removeProductWithId(1);
                Assert.assertEquals(Product.getAllProducts().size(), 0);

        } catch (ThereISNotProductWithIdException e) {

        }
    }

    @Test
    public void startDeleteCategoryWithName() {
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Seller seller2 = new Seller("bbbbbb", "b", "b", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());

        Seller.getAllSellers().add(seller1);
        Seller.getAllSellers().add(seller2);
        try {
            ManagerBoss.startDeleteCategoryWithName("gf");
        } catch (ThereIsNotCategoryWithNameException e) {
            Assert.assertTrue(true);
        }

        try {
            ManagerBoss.startDeleteCategoryWithName("a");
            Assert.assertEquals(Category.getAllCategories().size(), 0);
        } catch (ThereIsNotCategoryWithNameException e) {
            fail();
        }

        Seller.getAllSellers().remove(seller1);
        Seller.getAllSellers().remove(seller2);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(seller2);
        Category.getAllCategories().remove(category1);


    }

    @Test
    public void deleteAccountWithUsername() {
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Seller seller2 = new Seller("bbbbbb", "b", "b", "mail@e.ir", "09100577581", "09100577581", "company");
        Customer customer = new Customer("a","a","a","a@a.a","1","1");

        try {
            ManagerBoss.deleteAccountWithUsername("dfgd");
        } catch (NotValidUserNameException e) {
            Assert.assertTrue(true);
        } catch (CantRemoveYourAccountException e) {
            fail();
        }
        Seller.getAllSellers().add(seller1);
        Seller.getAllSellers().add(seller2);
        Account.setOnlineAccount(seller2);
        try {
            ManagerBoss.deleteAccountWithUsername("aaaaaa");
            Assert.assertEquals(Seller.getAllSellers().size(), 1);
        } catch (NotValidUserNameException | CantRemoveYourAccountException e) {
            fail();
        }
        try {
            ManagerBoss.deleteAccountWithUsername("a");
            Assert.assertEquals(Customer.getAllCustomers().size(), 0);
        } catch (NotValidUserNameException | CantRemoveYourAccountException e) {
            fail();
        }
            Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");
        try {
            ManagerBoss.deleteAccountWithUsername("cccccc");
            Assert.assertEquals(Manager.getAllManagers().size(), 0);
        } catch (NotValidUserNameException | CantRemoveYourAccountException e) {
            fail();
        }
        Seller.getAllSellers().remove(seller1);
        Seller.getAllSellers().remove(seller2);
        Manager.getAllManagers().remove(manager);
        Customer.getAllCustomers().remove(customer);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);




    }

    @Test
    public void addNewCategory() {
        try {
            ManagerBoss.addNewCategory("a", new ArrayList<>());
        } catch (RepeatedCategoryNameException e) {
            Assert.assertTrue(true);
        }
        try {
            ManagerBoss.addNewCategory("aa", new ArrayList<>());
            Assert.assertEquals(Category.getAllCategories().size(), 5);
            Assert.assertEquals(ManagerBoss.addNewCategory("f", new ArrayList<>()), 0);
        } catch (RepeatedCategoryNameException e) {
            fail();
        }

        //category add but not removed

    }

    @Test
    public void editAttributeName() {
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("one", "1");
        hashMap.put("two", "2");
        Product product1 = new Product("a","a",23,seller1,10,category1,hashMap,null);
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("one");
        attributes.add("two");
        Category category2 = new Category("fff", new ArrayList<>());
        Category category3 = new Category("onee", attributes);
        category3.getCategoryProducts().add(product1);
        try {
            ManagerBoss.editAttributeName("fff", "s", "f");
        } catch (FieldDoesNotExist fieldDoesNotExist) {
            Assert.assertTrue(true);
        } catch (RepeatedCategoryAttributeException e) {
            fail();
        }

        try {
            ManagerBoss.editAttributeName("onee", "one", "two");
        } catch (FieldDoesNotExist fieldDoesNotExist) {
            fail();
        } catch (RepeatedCategoryAttributeException e) {
            Assert.assertTrue(true);
        }

        try {
            ManagerBoss.editAttributeName("onee", "one", "sdkjfsj");
        } catch (FieldDoesNotExist | RepeatedCategoryAttributeException fieldDoesNotExist) {
            fail();
        }



        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Category.getAllCategories().remove(category3);
        Product.getAllProducts().remove(product1);
        Account.getAllAccounts().remove(seller1);



    }

    @Test
    public void createDiscountCode() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ManagerBoss.createDiscountCode("code", localDateTime2, localDateTime, 1, 1, 1, list, 1);
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().size(), 3);
        list.add("-all");
        ManagerBoss.createDiscountCode("klsd", localDateTime2, localDateTime, 1, 1, 1, list, 1);
        Assert.assertEquals(DiscountCode.getAllDiscountCodes().size(), 4);
        Assert.assertEquals(ManagerBoss.createDiscountCode("sdsds", localDateTime2, localDateTime, 1, 1, 1, list, 1), true);



        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);
    }

    @Test
    public void addAttributeToCategory() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("salam");
        Category category3 = new Category("a", attributes);
        try {
            ManagerBoss.addAttributeToCategory("a", "salam");
        } catch (RepeatedCategoryAttributeException e) {
            Assert.assertTrue(true);
        }
        try {
            ManagerBoss.addAttributeToCategory("a", "hello");
            Assert.assertEquals(category1.specialAttributes.contains("hello"), true);
            Assert.assertEquals(ManagerBoss.addAttributeToCategory("a", "hey"), true);
        } catch (RepeatedCategoryAttributeException e) {
            fail();
        }



        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Category.getAllCategories().remove(category3);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);



    }

    @Test
    public void deleteAttributeFromCategory() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());

        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("salam");
        Category category3 = new Category("gggg", attributes);
        try {
            ManagerBoss.deleteAttributeFromCategory("gggg", "hey");
        } catch (FieldDoesNotExist e) {
            Assert.assertTrue(true);
        }
        try {
            Assert.assertEquals(ManagerBoss.deleteAttributeFromCategory("gggg", "salam"), true);
        } catch (FieldDoesNotExist fieldDoesNotExist) {

        }



        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Category.getAllCategories().remove(category3);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void testEditAttributeName() {
    }

    @Test
    public void checkCategoryExistence() throws ThereIsNotCategoryWithNameException {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("salam");
        Category category3 = new Category("gggg", attributes);
        Assert.assertEquals( ManagerBoss.checkCategoryExistence("gggg"), true);
        try {
            Assert.assertEquals( ManagerBoss.checkCategoryExistence("ffff"), true);
        }
        catch (ThereIsNotCategoryWithNameException e) {
            Assert.assertTrue(true);
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Category.getAllCategories().remove(category3);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void editCategoryName() {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Category category3 = new Category("gggg", new ArrayList<>());
        Assert.assertEquals(ManagerBoss.editCategoryName("gggg", "hhh"), true);



        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Category.getAllCategories().remove(category3);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);


    }

    @Test
    public void checkNewManagerUserName() throws RepeatedUserName {
        DiscountCode discountCode1 = new DiscountCode("code1", localDateTime2, localDateTime, 1, 1, 1, new ArrayList<>(), 1);
        DiscountCode discountCode2 = new DiscountCode("code2", localDateTime4, localDateTime3, 2, 2, 2, new ArrayList<>(), 2);
        Seller seller1 = new Seller("aaaaaa", "a", "a", "mail@e.ir", "09100577581", "09100577581", "company");
        Category category1 = new Category("a",new ArrayList<>());
        Category category2 = new Category("b",new ArrayList<>());
        Customer customer = new Customer("a","a","a","a@a.a","1","1");
        Product product1 = new Product("a","a",23,seller1,10,category1,null,null);
        Manager manager = new Manager("cccccc", "c", "c", "mail@e.ir", "09100577581", "09100577581");

        Assert.assertEquals(ManagerBoss.checkNewManagerUserName("ccc"), true);
        try {
            ManagerBoss.checkNewManagerUserName("cccccc");
        }
        catch (RepeatedUserName e) {
            Assert.assertTrue(true);
        }


        DiscountCode.getAllDiscountCodes().remove(discountCode1);
        DiscountCode.getAllDiscountCodes().remove(discountCode2);
        Customer.getAllCustomers().remove(customer);
        Category.getAllCategories().remove(category1);
        Category.getAllCategories().remove(category2);
        Product.getAllProducts().remove(product1);
        Manager.getAllManagers().remove(manager);
        Account.getAllAccounts().remove(seller1);
        Account.getAllAccounts().remove(manager);
        Account.getAllAccounts().remove(customer);

    }
}
