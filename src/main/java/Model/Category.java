package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Category {
    public static ArrayList<Category> allCategories = new ArrayList<>();
    private String categoryName;
    public ArrayList<Product> categoryProducts;
    public ArrayList<String> specialAttributes;

    public Category(String categoryName, ArrayList<String> specialAttributes) {
        this.categoryName = categoryName;
        this.specialAttributes = specialAttributes;
        categoryProducts = new ArrayList<>();
    }


    public static Category getCategoryByName(String name){
        for (Category category : allCategories) {
            if (category.getCategoryName().equalsIgnoreCase(name))
                return category;
        }
        return null;
    }
    public static boolean isThereCategoryWithName(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<String> getSpecialAttributes() {
        return specialAttributes;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public String getShortInfo() {
        return "CategoryName : " + this.getCategoryName() + " --- " + "ProductsNumber : " + this.categoryProducts.size();
    }

    public ArrayList<Product> getCategoryProducts() {
        return categoryProducts;
    }
    public boolean isThisAttributesForThisCategory(HashMap<String,String> attributes){
        /*ArrayList<String> attri = new ArrayList<>();
        for (String s : attributes.keySet()) {
            attri.add(s);
        }
        Collections.sort(attri);
        Collections.sort(specialAttributes);
        if (attri.equals(specialAttributes)){
            return true;
        }
        return false;

         */
        for (String s : attributes.keySet()) {
            if (specialAttributes==null)
                return false;
            if (!specialAttributes.contains(s)){
                return false;
            }
        }
        return true;
    }
    public int getSize() {
        return this.categoryProducts.size();
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
