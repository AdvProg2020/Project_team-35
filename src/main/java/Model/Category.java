package Model;

import java.util.ArrayList;
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

    public static void deleteCategoryAndSubProducts(String categoryName) {

    }
    public static Category getCategoryWithName(String name) {
        return null;
    }
    public void editCategory(String name, ArrayList<String> newSpecialAttributes) {

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
}
