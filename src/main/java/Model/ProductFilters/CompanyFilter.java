package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CompanyFilter extends ProductFilter {
    String companyName;
    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getCompany().equals(this.companyName))
                .collect(Collectors.toList());
    }

    public CompanyFilter(String companyName) {
        super("Company(Brand) Filter");
        this.companyName = companyName;
    }
}
