package Model.ProductFilters;

import Model.Category;
import Model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoryFilter extends ProductFilter {

    Category category;
    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getCategory().equals(this.category))
                .collect(Collectors.toList());
    }

    public CategoryFilter(Category category) {
        super("Category Filter");
        this.category = category;
    }
}
