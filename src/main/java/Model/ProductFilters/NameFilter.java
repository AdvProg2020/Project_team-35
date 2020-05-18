package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NameFilter extends ProductFilter {
    String productName;
    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getName().equals(this.productName))
                .collect(Collectors.toList());
    }

    public NameFilter(String productName) {
        super("Name Filter");
        this.productName = productName;
    }
}
