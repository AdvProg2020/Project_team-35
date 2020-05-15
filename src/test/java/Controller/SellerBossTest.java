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
    private Product product = new Product("milk", "mihan", 2200.0, seller, 3, new Category("labaniat", null), null, null);

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
        }
    }

    /**
     * this is not a complete test and it is not in coverage please complete it
     */
    @Test
    public void testEditOff() {
        HashMap<String, String> a = new HashMap<>();
        a.put("startDate", "");
        a.put("percent", "29");
        Off off = new Off(null, null, null, 23, 12, seller);
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        try {
            Assert.assertTrue(SellerBoss.editOff(seller, off, a));
        } catch (ItIsNotCorrect itIsNotCorrect) {
            itIsNotCorrect.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TimeLimit timeLimit) {
            timeLimit.printStackTrace();
        } catch (InputStringExceptNumber inputStringExceptNumber) {
            inputStringExceptNumber.printStackTrace();
        } catch (ThisIsNotReadyForEdit thisIsNotReadyForEdit) {
            thisIsNotReadyForEdit.printStackTrace();
        }
    }

    @Test
    public void testTestSellerCredit() {
        seller.setMoney(234);
        Assert.assertEquals(seller.getMoney(), SellerBoss.sellerCredit(seller), 1);
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

    /**
     * this one need sell log constructor
     */
    @Test
    public void testTestShowHistoryOfSales() {

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

    @Test
    public void testTestAddRequestProduct() {
        Category category = new Category("lak", null);
        Assert.assertEquals(SellerBoss.addRequestProduct("kal", "22", "2", null, "sd", category, seller, "great"), true);
    }

    public void testTestShowBuyers() {
    }


    @Test
    public void testTestEditProduct() {
        HashMap<String, String> changes = new HashMap<>();
        changes.put("kmlm", "22");
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

    public void testTestShowOffs() {

    }


}