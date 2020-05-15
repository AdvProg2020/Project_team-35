package Model;

import java.util.*;

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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static ArrayList<Category> sortCategory(String field){
        if (field.equalsIgnoreCase("name")){
            Comparator<Category> nameCategory = new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                }
            };
            Collections.sort(allCategories,nameCategory);
        }else if (field.equalsIgnoreCase("productNumber")){
            Comparator<Category> productNumber = new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return -(o1.getCategoryProducts().size()-o2.getCategoryProducts().size());
                }
            };
            Collections.sort(allCategories,productNumber);
        }
        return allCategories;

    }
}

