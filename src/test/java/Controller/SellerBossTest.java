package Controller;

import Controller.Exceptions.*;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerBossTest {
    private Seller seller = new Seller("ali", "reza", "ghazan", "ali@ali.a", "2323", "paass", "mihan");
   // private Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);

    @Test
    public void sellerCredit() {
        seller.setMoney(233.0);
        Assert.assertEquals(233.0, seller.getMoney(), 0.1);
    }

    @Test
    public void showCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("sabzi");
        categories.add("labaniat");
        ArrayList<String> specialls = new ArrayList<>();
        Category category = new Category("sabzi", specialls);
        Category category1 = new Category("labaniat", specialls);
        Category.allCategories.add(category);
        Category.allCategories.add(category1);
        Assert.assertArrayEquals(categories.toArray(), SellerBoss.showCategories().toArray());
    }


    @Test
    public void showProduct() {
        Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("color", "red");
        attributes.put("weight", "23");
        product.setSpecialAttributes(attributes);
        System.out.println(product.toString());
        try {

            Assert.assertEquals(SellerBoss.showProduct("1", seller), product.toString());

        } catch (NullProduct | ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        }
    }


    @Test
    public void removeProduct() {
        int identifier = 0;
        Product product = new Product("Ads","Asd",3,new Seller("sad","ads","asd","Asd","sd","Asd","asd"),3,new Category("asd",null),null,"asd");

        try {
            Assert.assertTrue(SellerBoss.removeProduct("992", seller));
        } catch (ThisIsNotYours thisIsNotYours) {
            System.out.println(thisIsNotYours.getMessage());
            Assert.assertEquals(2, thisIsNotYours.getId());
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            System.out.println(soldProductsCanNotHaveChange.getMessage());
            Assert.assertEquals(3, soldProductsCanNotHaveChange.getId());
        } catch (NullProduct nullProduct) {
            System.out.println(nullProduct.getMessage());
            Assert.assertEquals(1, nullProduct.getId());
        }

        try {
            Assert.assertTrue(SellerBoss.removeProduct(String.valueOf(product.getProductId()), seller));
        } catch (ThisIsNotYours thisIsNotYours) {
            System.out.println(thisIsNotYours.getMessage());
            Assert.assertEquals(2, thisIsNotYours.getId());
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            System.out.println(soldProductsCanNotHaveChange.getMessage());
            Assert.assertEquals(3, soldProductsCanNotHaveChange.getId());
        } catch (NullProduct nullProduct) {
            System.out.println(nullProduct.getMessage());
            Assert.assertEquals(1, nullProduct.getId());
        }
        try {
            Product product1 = new Product("ad","Ad",23,seller,2,new Category("ads",null),null,null);
            seller.getSalableProducts().remove(product1);
            Assert.assertTrue(SellerBoss.removeProduct(String.valueOf(product1.getProductId()), seller));
        } catch (ThisIsNotYours thisIsNotYours) {
            System.out.println(thisIsNotYours.getMessage());
            Assert.assertEquals(2, thisIsNotYours.getId());
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            System.out.println(soldProductsCanNotHaveChange.getMessage());
            Assert.assertEquals(3, soldProductsCanNotHaveChange.getId());
        } catch (NullProduct nullProduct) {
            System.out.println(nullProduct.getMessage());
            Assert.assertEquals(1, nullProduct.getId());
        }
        try {
            Product product1 = new Product("ad","Ad",23,seller,2,new Category("ads",null),null,null);
           // seller.getSalableProducts().remove(product1);
            Assert.assertTrue(SellerBoss.removeProduct(String.valueOf(product1.getProductId()), seller));
        } catch (ThisIsNotYours thisIsNotYours) {
            System.out.println(thisIsNotYours.getMessage());
            Assert.assertEquals(2, thisIsNotYours.getId());
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            System.out.println(soldProductsCanNotHaveChange.getMessage());
            Assert.assertEquals(3, soldProductsCanNotHaveChange.getId());
        } catch (NullProduct nullProduct) {
            System.out.println(nullProduct.getMessage());
            Assert.assertEquals(1, nullProduct.getId());
        }
    }


    @Test
    public void viewOff() {
        LocalDateTime localDateTime = LocalDateTime.parse("2222-10-12T22:23:44");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2200-10-23T12:23:10");
        Off off = new Off(localDateTime, localDateTime1, null, 23, 12, new Seller("s", "s", "sd", "ads", "22", "asd", "asda"));
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        try {
            Assert.assertEquals(SellerBoss.viewOff(seller, "1"), "salam");
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (ThisOffNotExist thisOffNotExist) {
            Assert.assertEquals(thisOffNotExist.getId(), 1);
        }


    }


    @Test
    public void addOff() {
        Product product = new Product("ad","as",12,seller,2,new Category("Asd",null),null,"gerara");
        ArrayList<Product> a = new ArrayList<>();
        a.add(product);
        ArrayList<Integer> id = new ArrayList<>();
        id.add(product.getProductId());
        try {

            ArrayList<Product> as = new ArrayList<>();
            Product product1 = new Product("asd","sad",123,seller,12,new Category("asd",null),null,null);
            as.add(product1);
            Off off1 = new Off(null,null,null,343,12,seller);
            Assert.assertTrue(SellerBoss.addOff(id, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "23", "233"));
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (ParseException e) {
            Assert.assertNotNull(e);
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 4);
        } catch (TimeLimit timeLimit) {
            Assert.assertEquals(timeLimit.getId(), 1);
        } catch (InvalidNumber invalidNumber) {
            Assert.assertEquals(invalidNumber.getId(), 3);
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            Assert.assertEquals(inputStringExceptNumber.getId(), 2);
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            Assert.assertEquals(justOneOffForEveryProduct.getId(),6);
        }
        try {
            Assert.assertTrue(SellerBoss.addOff(null, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "120", "233"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            Assert.assertEquals(3,invalidNumber.getId(),0);
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }

        try {
            Assert.assertTrue(SellerBoss.addOff(null, seller, "2221-10-23T12:23:10", "2202-10-12T22:23:44", "23", "233"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            Assert.assertEquals(1,timeLimit.getId(),0);
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }
        try {
            Assert.assertTrue(SellerBoss.addOff(null, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "23", "23sad3"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
        Assert.assertEquals(2,inputStringExceptNumber.getId(),0);
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }

        id.add(product.getProductId());
        try {
            Assert.assertTrue(SellerBoss.addOff(id, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "23", "233"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }
        id.add(233);
        try {
            Assert.assertTrue(SellerBoss.addOff(id, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "23", "233"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }
        Product product1 = new Product("asd","asd",23,new Seller("ad","sad","asd","sad","Asd","ads","asd"),2,new Category("asd",null),null,"");
       id.clear();
       id.add(product1.getProductId());
        try {
            Assert.assertTrue(SellerBoss.addOff(id, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "23", "233"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            justOneOffForEveryProduct.printStackTrace();
        }

    }

    /*

    @Test
    public void testEditOff() {
        HashMap<String, String> a = new HashMap<>();
        a.put("startDate", "");
        a.put("percent", "29");
        Off off = new Off(null, null, null, 23, 12, seller);
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        try {
            Assert.assertTrue(SellerBoss.editOff(seller, off, a));
        }  catch (TimeLimit | InputStringExceptNumber | ThisIsNotReadyForEdit timeLimit) {
            timeLimit.printStackTrace();
        }
    }
*/
    @Test
    public void testTestSellerCredit() {

    }

    @Test
    public void testTestShowCategories() {
        Category category = new Category("n", null);
        Category category1 = new Category("l", null);
        Category.allCategories.add(category);
        Category.allCategories.add(category1);
        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> c = SellerBoss.showCategories();
        Assert.assertArrayEquals(new ArrayList[]{c}, new ArrayList[]{a});
    }



    @Test
    public void testTestShowProduct() {
        try {
            Assert.assertEquals(SellerBoss.showProduct("1", seller), "laklak");
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 1);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 2);
        }
    }

  /*  @Test
    public void testTestAddRequestProduct() {
        Category category = new Category("lak", null);
        Assert.assertEquals(SellerBoss.addRequestProduct("kal", "22", "2", null, "sd", "ategory", seller, "great"), true);
    }
*/
    @Test
    public void testTestShowBuyers() {
        Customer customer = new Customer("l","d","d","df","23","pass");
        Customer customer1 = new Customer("m","m","v","c","123","psd");
         Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);

        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        product.getWhoBoughtThisGood().add(customer);
        product.getWhoBoughtThisGood().add(customer1);
        Product product1 = new Product("sad","asd",23,new Seller("asd","asd","asd","dsf","sdf","gf","dgf"),3,new Category("lkdsa",null),null,"");
        try {
            ArrayList<String> a = new ArrayList<>();
            a.add(customer.getUsername());
            a.add(customer1.getUsername());
            Assert.assertEquals(SellerBoss.showBuyers("2",seller),a);
        } catch (ThisIsNotYours thisIsNotYours) {
          Assert.assertEquals( thisIsNotYours.getId(),2);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(),1);
        }
    }


    @Test
    public void testTestEditProduct() {
        try {
            Assert.assertEquals(SellerBoss.editProduct(null,"213213",seller),null);
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            soldProductsCanNotHaveChange.printStackTrace();
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            thisAttributeIsNotForThisProduct.printStackTrace();
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            noMatchBetweenCategoryAndAttributes.printStackTrace();
        } catch (ThereIsNotCategoryWithNameException e) {
            e.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        }
        Product product23=new Product("asd","Asd",23,seller,2,new Category("As",null),null,null);
        product23.setProductStatus(ProductAndOffStatus.FOREDIT);
        try {
            Assert.assertEquals(SellerBoss.editProduct(null,String.valueOf(product23.getProductId()),seller),null);
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            soldProductsCanNotHaveChange.printStackTrace();
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            thisAttributeIsNotForThisProduct.printStackTrace();
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            noMatchBetweenCategoryAndAttributes.printStackTrace();
        } catch (ThereIsNotCategoryWithNameException e) {
            e.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        }
        Product product = new Product("asd","Asd",23,new Seller("asd","Ad","Asd","Ads",",aasd","asd","asd"),3,new Category("asd",null),null,null);
        try {
            Assert.assertEquals(SellerBoss.editProduct(null,String.valueOf(product.getProductId()),seller),null);
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            soldProductsCanNotHaveChange.printStackTrace();
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            thisAttributeIsNotForThisProduct.printStackTrace();
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            noMatchBetweenCategoryAndAttributes.printStackTrace();
        } catch (ThereIsNotCategoryWithNameException e) {
            e.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        }
        HashMap<String, String> changes = new HashMap<>();
        changes.put("kmlm", "22");
       Product product11 = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);

        seller.getSalableProducts().remove(product);
        Product product1 = new Product("asd", "asd", 23, new Seller("asd", "asd", "ads", "asd", "sad", "ads", "asd"), 2, product.getCategory(), null, "");
        try {
            product.setProductStatus(ProductAndOffStatus.CONFIRMED);
            System.out.println(product.getProductStatus().name());
            Assert.assertTrue(SellerBoss.editProduct(changes, "1", seller));
            System.out.println(product.getProductStatus().name());

        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            Assert.assertEquals(soldProductsCanNotHaveChange.getId(), 3);
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            Assert.assertEquals(thisAttributeIsNotForThisProduct.getId(), 3);
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            Assert.assertEquals(noMatchBetweenCategoryAndAttributes.getId(), 7);
        } catch (ThereIsNotCategoryWithNameException e) {
            Assert.assertEquals(e.getId(), 4);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 1);
        } catch (InvalidNumber invalidNumber) {
            Assert.assertEquals(invalidNumber.getId(), 5);
        }
    }




    @Test
    public void testSellerCredit() {
        seller.setMoney(234);
        Assert.assertEquals(seller.getMoney(), SellerBoss.sellerCredit(seller), 1);
    }

    @Test
    public void testShowCategories() {


        ArrayList<String> a = new ArrayList<>();
        a.add("sabzi");
        a.add("labaniat");
        a.add("n");
        a.add("l");

        ArrayList<String> c = SellerBoss.showCategories();
        Assert.assertEquals(c,a);

    }

    @Test
    public void sortCategory() {
        Category category = new Category("ali",null);
        Category category1 =  new Category("lak",null);
        Category.getAllCategories().add(category);
        Category.getAllCategories().add(category1);
        Assert.assertEquals(SellerBoss.sortCategory("name"),null);
    }

    @Test
    public void showHistoryOfSales() {
        Customer customer = new Customer("asd","sad","sad","das","das","adasd");
        Product product = new Product("asd","das",23,seller,2,new Category("asd",null),null,null);
        ArrayList<Product> a = new ArrayList<>();
        Product product2 = new Product("sda","Asd",231,seller,2,new Category("asd",null),null,null);
        a.add(product);
        a.add(product2);
        a.add(product);
        Product product1 = new Product("asd","ads",34,seller,4,new Category("asd",null),null,null);
        //SellLog sellLog = new SellLog(a,customer,seller);
        //seller.getSellLogs().add(sellLog);
        ArrayList<String >b = new ArrayList<>();
        for (Product product3 : a) {
            b.add(product3.getName());
        }
        Assert.assertEquals(SellerBoss.showHistoryOfSales(seller),b);
        a.add(product1);
        //SellLog sellLog1 = new SellLog(a,customer,seller);
       //seller.getSellLogs().add(sellLog1);
       b.clear();
        for (SellLog log : seller.getSellLogs()) {
            for (Product soldProduct : log.getSoldProducts()) {
                b.add(soldProduct.getName());
            }
        }
        Assert.assertEquals(SellerBoss.showHistoryOfSales(seller),b);
    }

    @Test
    public void testShowProduct() {
        try {
            Assert.assertEquals(SellerBoss.showProduct("1", seller), "laklak");
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 1);
        }
        try {
        Assert.assertEquals(SellerBoss.showProduct("111", seller), "laklak");
    } catch (ThisIsNotYours thisIsNotYours) {
        Assert.assertEquals(thisIsNotYours.getId(), 2);
    } catch (NullProduct nullProduct) {
        Assert.assertEquals(nullProduct.getId(), 1);
    }
        try {
            Product product = new Product("asd","asd",23,seller,2,new Category("asd",null),null,"");
            System.out.println(product.getProductId());
            Assert.assertEquals(SellerBoss.showProduct("1", seller), product.toString());
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 1);
        }

    }

    @Test
    public void getWithNameOfCategoryItsSpecials() {
        Category category = new Category("ali",null);
        Category.getAllCategories().add(category);
        try {

            Assert.assertEquals(SellerBoss.getWithNameOfCategoryItsSpecials("ali"),null);
        } catch (ThereIsNotCategoryWithNameException e) {
            e.printStackTrace();
        }
        try {

            Assert.assertEquals(SellerBoss.getWithNameOfCategoryItsSpecials("gholam"),null);
        } catch (ThereIsNotCategoryWithNameException e) {
            Assert.assertEquals(e.getId(),1);
        }
    }

    @Test
    public void addRequestProduct() {
        Category category = new Category("lak", null);
        Assert.assertEquals(SellerBoss.addRequestProduct("kal", "22", "2", null, "sd", "ategory", seller, "great"), true);
    }

    @Test
    public void showBuyers() {
        Customer customer = new Customer("l","d","d","df","23","pass");
        Customer customer1 = new Customer("m","m","v","c","123","psd");
         Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);

        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        product.getWhoBoughtThisGood().add(customer);
        product.getWhoBoughtThisGood().add(customer1);
        System.out.println(product.getProductId());
        Product product1 = new Product("sad","asd",23,new Seller("asd","asd","asd","dsf","sdf","gf","dgf"),3,new Category("lkdsa",null),null,"");
        try {
            ArrayList<String> a = new ArrayList<>();
            for (Customer customer2 : product.getWhoBoughtThisGood()) {
                a.add(customer2.getUsername());
            }
            Assert.assertEquals(SellerBoss.showBuyers(String.valueOf(product.getProductId()),seller),a);
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals( thisIsNotYours.getId(),2);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(),1);
        }
    }

    @Test
    public void sortProductForSpecialSeller() {
        Product product = new Product("lak","kale",234,seller,3,new Category("pal",null),null,"");
        Product product1 = new Product("aaa","mam",23,seller,2,new Category("mak",null),null,"");
        Product product2 = new Product("bbb","pal",120,seller,3,new Category("pore",null),null,"");

        ArrayList<Product> a = new ArrayList<>();
        a.add(product1);a.add(product2);a.add(product);
        Assert.assertEquals(SellerBoss.sortProductForSpecialSeller("name",seller),a);

    }

    @Test
    public void sortBuyers() throws NoMoneyInCustomerPocket {
        Product product = new Product("asd","sad",23,seller,2,new Category("asd",null),null,null);

        Customer customer = new Customer( "asd1","sad","asd","asd","ASd","dsa");
        Customer customer1 = new Customer( "asd2","sad","asd","asd","ASd","dsa");
        Customer customer2 = new Customer( "asd3","sad","asd","asd","ASd","dsa");
ArrayList<Product> a = new ArrayList<>();
a.add(product);
        //BuyLog buyLog = new BuyLog(233,a,seller,customer);
        //BuyLog buyLog1 = new BuyLog(233,a,seller,customer1);
        //BuyLog buyLog2 = new BuyLog(233,a,seller,customer2);

        product.getWhoBoughtThisGood().add(customer);
        product.getWhoBoughtThisGood().add(customer1);
        product.getWhoBoughtThisGood().add(customer2);
        String field = "username";
        ArrayList<Customer> s = new ArrayList<>();
        s.add(customer);
        s.add(customer1);
        s.add(customer2);
        try {
            Assert.assertEquals(SellerBoss.sortBuyers(String.valueOf(product.getProductId()),seller,field),s);
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        }
    }



    @Test
    public void editProduct() {
        HashMap<String, String> changes = new HashMap<>();
        changes.put("kmlm", "22");
        Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);
        seller.getSalableProducts().remove(product);
        Product product1 = new Product("asd", "asd", 23, new Seller("asd", "asd", "ads", "asd", "sad", "ads", "asd"), 2, product.getCategory(), null, "");
        try {
            product.setProductStatus(ProductAndOffStatus.CONFIRMED);
            System.out.println(product.getProductStatus().name());
            Assert.assertTrue(SellerBoss.editProduct(changes, "1", seller));
            System.out.println(product.getProductStatus().name());

        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            Assert.assertEquals(soldProductsCanNotHaveChange.getId(), 3);
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            Assert.assertEquals(thisAttributeIsNotForThisProduct.getId(), 3);
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            Assert.assertEquals(noMatchBetweenCategoryAndAttributes.getId(), 7);
        } catch (ThereIsNotCategoryWithNameException e) {
            Assert.assertEquals(e.getId(), 4);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 1);
        } catch (InvalidNumber invalidNumber) {
            Assert.assertEquals(invalidNumber.getId(), 5);
        }
    }

    @Test
    public void showOffs() {
    }

    @Test
    public void sortOffs() {
        Product product = new Product("a","asd",23,seller,2,new Category("sad",null),null,null);
        Product product1 = new Product("b","asd",23,seller,2,new Category("sad",null),null,null);
        Product product2 = new Product("c","asd",23,seller,2,new Category("sad",null),null,null);

        ArrayList<Product> s = new ArrayList<>();
        s.add(product);
        Off off = new Off(null,null,s,233,22,seller);
        Off off1 = new Off(null,null,null,344,23,seller);
       ArrayList<Off> a = new ArrayList<>();
       Off.allActiveOffs = new ArrayList<>();
        Assert.assertEquals(SellerBoss.sortOffs("StartDate",seller),null);
    }

    @Test
    public void testViewOff() {
        LocalDateTime localDateTime = LocalDateTime.parse("2222-10-12T22:23:44");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2200-10-23T12:23:10");
        Off off = new Off(localDateTime, localDateTime1, null, 23, 12, new Seller("s", "s", "sd", "ads", "22", "asd", "asda"));
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        try {
            Assert.assertEquals(SellerBoss.viewOff(seller, "1"), "salam");
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 2);
        } catch (ThisOffNotExist thisOffNotExist) {
            Assert.assertEquals(thisOffNotExist.getId(), 1);
        }
    }

    @Test
    public void editOff() {
        HashMap<String, String> a = new HashMap<>();
        a.put("startDate", "2200-12-13T22");
        a.put("percent", "29");
        a.put("maximumAmountOfOff","88");
        Off off = new Off(null, null, null, 23, 12, seller);
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        try {
            Assert.assertTrue(SellerBoss.editOff(seller, off, a));
        }  catch (TimeLimit timeLimit) {
           Assert.assertEquals(timeLimit.getId(),2);
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            Assert.assertEquals(inputStringExceptNumber.getId(),3);
        } catch (ThisIsNotReadyForEdit thisIsNotReadyForEdit) {
            Assert.assertEquals(thisIsNotReadyForEdit.getId(),1);
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        }
    }

    @Test
    public void testAddOff() {
        try {
            Assert.assertTrue(SellerBoss.addOff(null, seller, "2201-10-23T12:23:10", "2202-10-12T22:23:44", "-23", "233"));
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (ParseException e) {
            Assert.assertNotNull(e);
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 4);
        } catch (TimeLimit timeLimit) {
            Assert.assertEquals(timeLimit.getId(), 1);
        } catch (InvalidNumber invalidNumber) {
            Assert.assertEquals(invalidNumber.getId(), 3);
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            Assert.assertEquals(inputStringExceptNumber.getId(), 2);
        } catch (JustOneOffForEveryProduct justOneOffForEveryProduct) {
            Assert.assertEquals(justOneOffForEveryProduct.getId(),6);
        }
    }

    @Test
    public void testEditProduct3(){
        Product product = new Product("ads","ad",34,seller,2,new Category("asd",null),null,null);
        product.setProductStatus(ProductAndOffStatus.CONFIRMED);
        HashMap<String , String> a = new HashMap<>();
        a.put("name","ali");
        try {
            Assert.assertTrue(SellerBoss.editProduct(a,String.valueOf(product.getProductId()),seller));
        } catch (ThisIsNotYours thisIsNotYours) {
            thisIsNotYours.printStackTrace();
        } catch (SoldProductsCanNotHaveChange soldProductsCanNotHaveChange) {
            soldProductsCanNotHaveChange.printStackTrace();
        } catch (ThisAttributeIsNotForThisProduct thisAttributeIsNotForThisProduct) {
            thisAttributeIsNotForThisProduct.printStackTrace();
        } catch (NoMatchBetweenCategoryAndAttributes noMatchBetweenCategoryAndAttributes) {
            noMatchBetweenCategoryAndAttributes.printStackTrace();
        } catch (ThereIsNotCategoryWithNameException e) {
            e.printStackTrace();
        } catch (NullProduct nullProduct) {
            nullProduct.printStackTrace();
        } catch (InvalidNumber invalidNumber) {
            invalidNumber.printStackTrace();
        }
    }
}