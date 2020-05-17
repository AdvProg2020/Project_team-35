package Model.ProductFilters;

import Model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InventoryFilter extends ProductFilter {

    @Override
    public ArrayList<Product> doThisFilterOnList(ArrayList<Product> inputList) {
        return (ArrayList<Product>) inputList.stream()
                .filter(product -> product.getInventory() >= 1)
                .collect(Collectors.toList());
    }

    public InventoryFilter() {
        super("Inventory Filter");
    }
}
