package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;

public abstract class ProductFilter {
    public abstract ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList);
    protected static String filterName;

    public ProductFilter(String filterName) {
        ProductFilter.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }
}
