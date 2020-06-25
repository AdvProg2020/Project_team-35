package Controller;

import Controller.Exceptions.*;
import Model.*;
import Model.ProductFilters.*;

import java.util.ArrayList;
import java.util.HashMap;

public class OffBoss {
    private static ArrayList<ProductFilter> fields = new ArrayList<>();

    public static Product ProductPageTransfer(int id) throws NullProduct, ProductIsNotConfirmed {
        Boss.removeExpiredOffsAndDiscountCodes();
        Product product = Product.getProductWithId(id);
        if (product == null) {
            throw new NullProduct("this is not existed", 1);
        }
        if (!product.getProductStatus().equals(ProductAndOffStatus.CONFIRMED)) {
            throw new ProductIsNotConfirmed("this is not confirmed", 2);
        }
        return product;
    }



    public static ArrayList<Off> sortOff(String field) {
        Boss.removeExpiredOffsAndDiscountCodes();
        return Off.sorting(field);
    }

    public static ArrayList<Product> sortProducts(String field) {
        Boss.removeExpiredOffsAndDiscountCodes();
        ArrayList<Product> a = Off.sortAllProducts(field);
        return a;
    }

    public static Category checkCategory(String name) throws CategoryNull {
        Boss.removeExpiredOffsAndDiscountCodes();
        Category category = Category.getCategoryByName(name);
        if (category == null)
            throw new CategoryNull("null category", 1);
        return category;
    }

    public static Seller checkSeller(String username) throws ExistenceOfUserWithUsername, SellerShouldJustBe {
        Boss.removeExpiredOffsAndDiscountCodes();
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            throw new ExistenceOfUserWithUsername("this is not a user", 1);
        } else if (!(account instanceof Seller)) {
            throw new SellerShouldJustBe("this is not seller", 2);
        }
        return (Seller) account;
    }

    public static void addFieldToFilterFields(String field, String amount1, String amount2) throws CategoryNull, InvalidNumber, MaxMinReplacement, SellerShouldJustBe {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (field.equalsIgnoreCase("Name")) {
            fields.add(new NameFilter(amount1));
        } else if (field.equalsIgnoreCase("Category")) {
            Category category = Category.getCategoryByName(amount1);
            if (category == null)
                throw new CategoryNull("null", 1);
            fields.add(new CategoryFilter(category));
        } else if (field.equalsIgnoreCase("Inventory")) {
            fields.add(new InventoryFilter());
        } else if (field.equalsIgnoreCase("Price")) {

            if (!(amount1.matches("\\d+.?\\d+") && amount2.matches("\\d+.?\\d+"))) {
                throw new InvalidNumber("invalid number format", 1);
            }
            double number1 = Double.parseDouble(amount1);
            double number2 = Double.parseDouble(amount2);
            if (number2 < number1)
                throw new MaxMinReplacement("max is less than min", 2);
            if (number1 < 0)
                number1 = 0;
            fields.add(new PriceFilter(number1, number2));
        } else if (field.equalsIgnoreCase("Company")) {

            fields.add(new CompanyFilter(amount1));
        } else if (field.equalsIgnoreCase("Seller")) {
            Seller seller = (Seller) Seller.getAccountWithUsername(amount1);
            if (seller == null)
                throw new SellerShouldJustBe("seller should be", 1);
            fields.add(new SellerFilter(seller));
        }
    }

    public static ArrayList<Product> filterFields(ArrayList<Product> listOfProducts) {
        Boss.removeExpiredOffsAndDiscountCodes();
        for (ProductFilter field : fields) {
            listOfProducts = field.doThisFilterOnList(listOfProducts);
        }
        return listOfProducts;
    }
    public static ArrayList<Product> filtering (ArrayList<Product> listOfProducts, HashMap<String,String> filterFields){
        ArrayList<Product> newProducts = listOfProducts;
        for (String s : filterFields.keySet()) {
            System.out.println(s);
            if (s.equalsIgnoreCase("Name")){
                    NameFilter nameFilter = new NameFilter(filterFields.get(s));
                    newProducts = nameFilter.doThisFilterOnList(newProducts);
            }
            if (s.equalsIgnoreCase("Category")){
                System.out.println("yes");
                Category category = Category.getCategoryByName(filterFields.get(s));
                    CategoryFilter categoryFilter = new CategoryFilter(category);
                   newProducts =  categoryFilter.doThisFilterOnList(newProducts);




            }
            if (s.equalsIgnoreCase("Price")){
                int min = Integer.parseInt(filterFields.get(s).substring(0,filterFields.get(s).indexOf("-")));
                int max = Integer.parseInt(filterFields.get(s).substring(filterFields.get(s).indexOf("-")+1));
                PriceFilter priceFilter = new PriceFilter(min,max);
                newProducts = priceFilter.doThisFilterOnList(newProducts);

            }
            if (s.equalsIgnoreCase("Inventory")){
                InventoryFilter inventoryFilter = new InventoryFilter();
             newProducts = inventoryFilter.doThisFilterOnList(newProducts);
            }
            if (s.equalsIgnoreCase("Company")){
                CompanyFilter companyFilter = new CompanyFilter(filterFields.get(s));
             newProducts =    companyFilter.doThisFilterOnList(newProducts);
            }
        }
        return newProducts;
    }

    public static ArrayList<ProductFilter> getFields() {
        return fields;
    }

    public static boolean disableFilter(String field) {
        Boss.removeExpiredOffsAndDiscountCodes();
        for (ProductFilter filter : fields) {
            if (filter.getFilterName().equalsIgnoreCase(field)) {
                fields.remove(filter);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Product> getProducts() {
        Boss.removeExpiredOffsAndDiscountCodes();
        ArrayList<Product> a = new ArrayList<>();
        for (Off activeOff : Off.allActiveOffs) {
            for (Product product : activeOff.getIncludedProducts()) {
                a.add(product);
            }
        }
        return a;
    }
}
