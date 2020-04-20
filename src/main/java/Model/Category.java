package Model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public static ArrayList<Category> allCategories;
    private String categoryName;
    public ArrayList<Product> categoryProducts;
    public List<String> specialAttributes;
    public static boolean isThereCategoryWithName(String name) {
        return true;
    }
    public static void deleteCategoryAndSubProducts(String categoryName) {

    }
    public static Category getCategoryWithName(String name) {
        return null;
    }
    public void editCategory(String name, ArrayList<String> newSpecialAttributes) {

    }
}
