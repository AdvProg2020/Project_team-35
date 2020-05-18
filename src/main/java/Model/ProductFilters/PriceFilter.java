package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PriceFilter extends ProductFilter {
    double minimum;
    double maximum;
    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getPrice() >= this.minimum && product.getPrice() <= this.maximum)
                .collect(Collectors.toList());
    }

    public PriceFilter(double minimum, double maximum) {
        super("Price Filter");
        this.minimum = minimum;
        this.maximum = maximum;
    }
}
