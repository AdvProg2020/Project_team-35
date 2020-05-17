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
        try {
            Assert.assertTrue(SellerBoss.removeProduct("2", seller));
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
            Assert.assertEquals(SellerBoss.showBuyers("2",seller),null);
        } catch (ThisIsNotYours thisIsNotYours) {
          Assert.assertEquals( thisIsNotYours.getId(),2);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(),1);
        }
    }


    @Test
    public void testTestEditProduct() {
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
    }

    @Test
    public void testShowProduct() {
        try {
            Assert.assertEquals(SellerBoss.showProduct("1", seller), "laklak");
        } catch (ThisIsNotYours thisIsNotYours) {
            Assert.assertEquals(thisIsNotYours.getId(), 1);
        } catch (NullProduct nullProduct) {
            Assert.assertEquals(nullProduct.getId(), 2);
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
        Product product1 = new Product("sad","asd",23,new Seller("asd","asd","asd","dsf","sdf","gf","dgf"),3,new Category("lkdsa",null),null,"");
        try {
            Assert.assertEquals(SellerBoss.showBuyers("2",seller),null);
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
    public void sortBuyers() {
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
        Off off = new Off(null,null,null,233,22,seller);
        Assert.assertEquals(SellerBoss.sortOffs("name",seller),null);
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
        a.put("startDate", "");
        a.put("percent", "29");
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
}