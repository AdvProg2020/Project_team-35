package Model.ProductFilters;

import Model.Product;
import Model.Seller;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SellerFilter extends ProductFilter {
    Seller productSeller;
    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getSeller().equals(this.productSeller))
                .collect(Collectors.toList());
    }

    public SellerFilter(Seller productSeller) {
        super("Seller Filter");
        this.productSeller = productSeller;
    }
}
