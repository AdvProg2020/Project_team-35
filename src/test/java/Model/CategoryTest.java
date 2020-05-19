package Model;

import Controller.ManagerBoss;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CategoryTest {



    @Test
    public void getCategoryByName() {
        Category category = new Category("labaniat",null);
        Assert.assertEquals(Category.getCategoryByName("labaniat"), category);
        Assert.assertEquals(Category.getCategoryByName("sdsd"), null);
    }

    @Test
    public void isThereCategoryWithName() {
        Assert.assertEquals(Category.isThereCategoryWithName("labaniat"), true);
    }

    @Test
    public void getAllCategories() {
    }

    @Test
    public void getShortInfo() {
        Category category = new Category("labaniat",null);
        Assert.assertEquals("CategoryName : " + "labaniat" + " --- " + "ProductsNumber : " + 0, category.getShortInfo());

    }

    @Test
    public void isThisAttributesForThisCategory() {
        Category category = new Category("labaniat",null);
        Assert.assertEquals(category.isThisAttributesForThisCategory(new HashMap<>()), true);
        HashMap<String,String> attributes = new HashMap<>();
        attributes.put("hello", "5");
        ArrayList<String> categoryAttributes = new ArrayList<>();
        categoryAttributes.add("hello");
        Category category1 = new Category("salam",categoryAttributes);
        Category category2 = new Category("wwwwww",null);
        Assert.assertEquals(category1.isThisAttributesForThisCategory(attributes), true);
        Assert.assertEquals(category2.isThisAttributesForThisCategory(attributes), false);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("tttt", "5");
        Assert.assertEquals(category1.isThisAttributesForThisCategory(hashMap), false);
    }

    @Test
    public void sortCategory() {
        ArrayList<String> categoryAttributes = new ArrayList<>();
        categoryAttributes.add("hello");
        Category aaa = new Category("aaa",categoryAttributes);
        Category bbb = new Category("bbb",categoryAttributes);
        Category.sortCategory("name");
        Assert.assertEquals(Category.getAllCategories().get(0), aaa);
        Seller ccc = new Seller("ccc","a","s","sd","ds","sad","asd");
        Product ppp = new Product("ppp","mihan",23,ccc,10,bbb,null,null);
        bbb.getCategoryProducts().add(ppp);
        Category.sortCategory("productNumber");
        Assert.assertEquals(Category.getAllCategories().get(0), bbb);

    }

    @Test
    public void getCurrentSort() {
        Assert.assertEquals(Category.getCurrentSort(), "Nothing");
        ManagerBoss.sortCategoryWithField("name-a");
        Assert.assertEquals(Category.getCurrentSort(), "Category Name - Ascending");
    }
}
