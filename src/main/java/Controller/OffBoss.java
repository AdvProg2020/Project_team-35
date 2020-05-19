package Controller;

import Controller.Exceptions.*;
import Model.*;
import Model.ProductFilters.*;

import java.util.ArrayList;

public class OffBoss {
    private static ArrayList<ProductFilter> fields = new ArrayList<>();
    public static Product ProductPageTransfer(int id) throws NullProduct, ProductIsNotConfirmed {
        Boss.removeExpiredOffsAndDiscountCodes();
        Product product = Product.getProductWithId(id);
        if (product==null){
            throw new NullProduct("this is not existed",1);
        }
        if (!product.getProductStatus().equals(ProductAndOffStatus.CONFIRMED)){
            throw new ProductIsNotConfirmed("this is not confirmed",2);
        }
        return product;
    }
    public void startCreateOff(ArrayList<Integer> includedProductsIds, double percent, double maximum, char date) {

    }
    public void startEditOff(int offId, ArrayList<Integer> newIncludedProducts, double newPercent, double newMaximum, char newDate) {

    }
    public void startRemoveOff(int OffId) {

    }
    public static ArrayList<Off> sortOff(String field){
        Boss.removeExpiredOffsAndDiscountCodes();
        return Off.sorting(field);
    }
    public static ArrayList<Product> sortProducts(String field){
        Boss.removeExpiredOffsAndDiscountCodes();
        ArrayList<Product> a = Off.sortAllProducts(field);
        return a;
    }
    public static Category checkCategory(String name) throws CategoryNull {
        Boss.removeExpiredOffsAndDiscountCodes();
        Category category = Category.getCategoryByName(name);
        if (category==null)
            throw new CategoryNull("null category",1);
        return category;
    }

    public static Seller checkSeller(String username) throws ExistenceOfUserWithUsername, SellerShouldJustBe {
        Boss.removeExpiredOffsAndDiscountCodes();
     Account account = Account.getAccountWithUsername(username);
     if (account==null){
         throw new ExistenceOfUserWithUsername("this is not a user",1);
     }else if (!(account instanceof Seller)){
         throw new SellerShouldJustBe("this is not seller",2);
     }
     return (Seller) account;
    }
    public static void addFieldToFilterFields(String field,String amount1 , String amount2) throws CategoryNull, InvalidNumber, MaxMinReplacement, SellerShouldJustBe {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (field.equalsIgnoreCase("Name")){
                fields.add(new NameFilter(amount1));
        }else if (field.equalsIgnoreCase("Category")){
            Category category = Category.getCategoryByName(amount1);
            if (category==null)
                throw new CategoryNull("null",1);
            fields.add(new CategoryFilter(category));
        }else if (field.equalsIgnoreCase("Inventory")){
            fields.add(new InventoryFilter());
        }else if (field.equalsIgnoreCase("Price")){

            if (!(amount1.matches("\\d+.?\\d+") && amount2.matches("\\d+.?\\d+"))){
                throw new InvalidNumber("invalid number format",1);
            }
            double number1 = Double.parseDouble(amount1);
            double  number2 = Double.parseDouble(amount2);
            if (number2<number1)
                throw new MaxMinReplacement("max is less than min",2);
            if (number1<0)
                number1=0;
            fields.add(new PriceFilter(number1,number2));
        }else if (field.equalsIgnoreCase("Company")){

            fields.add(new CompanyFilter(amount1));
        }else if (field.equalsIgnoreCase("Seller")){
            Seller seller = (Seller) Seller.getAccountWithUsername(amount1);
            if (seller==null)
                throw new SellerShouldJustBe("seller should be",1);
            fields.add(new SellerFilter(seller));
        }
    }
    public static ArrayList<Product> filterFields(ArrayList<Product> listOfProducts){
        Boss.removeExpiredOffsAndDiscountCodes();
        for (ProductFilter field : fields) {
            listOfProducts = field.doThisFilterOnList(listOfProducts);
        }
        return listOfProducts;
    }

    public static ArrayList<ProductFilter> getFields() {
        return fields;
    }
    public static boolean disableFilter(String field){
        Boss.removeExpiredOffsAndDiscountCodes();
        for (ProductFilter filter : fields) {
            if (filter.getFilterName().equalsIgnoreCase(field)) {
                fields.remove(filter);
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Product> getProducts(){
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
