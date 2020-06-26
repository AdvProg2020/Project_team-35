package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;

public class ProductPrivateFilter {
    public static ArrayList<Product> filter(ArrayList<Product> products , String filterFieldKey,String filterFieldValue){
        ArrayList<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getSpecialAttributes().keySet().contains(filterFieldKey) && product.getSpecialAttributes().get(filterFieldKey).equalsIgnoreCase(filterFieldValue)){
                newProducts.add(product);
            }
        }
        return newProducts;
    }
}
