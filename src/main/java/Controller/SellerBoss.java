package Controller;
import Controller.Exceptions.*;
import Model.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerBoss {
    private static LocalDateTime dateOfNow;

    public static double sellerCredit(Seller seller) {
        return seller.getMoney();
    }

    public static ArrayList<String> showCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.allCategories) {
            categories.add(category.getCategoryName());
        }
        return categories;
    }
    public static ArrayList<Category> sortCategory(String field)  {
        return Category.sortCategory(field);
    }

    public static ArrayList<String> showHistoryOfSales(Seller seller) {
        ArrayList<String> sales = new ArrayList<>();
        for (SellLog log : seller.getSellLogs()) {
            for (Product soldProduct : log.getSoldProducts()) {
                sales.add(soldProduct.getName());
            }
        }
        return sales;
    }

    public static String showProduct(String id, Seller seller) throws ThisIsNotYours, NullProduct {
        int productId = Integer.parseInt(id);
        Product product = Product.getProductWithId(productId);
        if (product == null) {
            throw new NullProduct("we don't have this one.", 1);
        }
        if (!seller.equals(product.getSeller())) {
            throw new ThisIsNotYours("this is not yours", 2);
        }
        return product.toString();
    }

    public static ArrayList<String> getWithNameOfCategoryItsSpecials(String categoryName) throws ThereIsNotCategoryWithNameException {
        Category category = Category.getCategoryByName(categoryName);
        if (category==null){
            throw new ThereIsNotCategoryWithNameException("null",1);
        }
        return category.getSpecialAttributes();
    }
    public static boolean addRequestProduct(String name, String price, String inventory, HashMap<String, String> attributes, String company, String category1, Seller seller,String description) {

        Category category = Category.getCategoryByName(category1);
        double productPrice = Double.parseDouble(price);
        int number = Integer.parseInt(inventory);
        Product product = new Product(name,company,productPrice,seller,number,category,attributes,description);
        product.setProductStatus(ProductAndOffStatus.FORMAKE);
        if (category != null) {
            category.getCategoryProducts().add(product);
        }
        AddProductRequest addProductRequest = new AddProductRequest(seller ,product);
        return true;
    }

    public static ArrayList<String> showBuyers(String id, Seller seller) throws ThisIsNotYours, NullProduct {
        int iD = Integer.parseInt(id);
        Product product = getProduct(id, seller);
        ArrayList<String> buyers = new ArrayList<>();
        for (Customer customer : product.getWhoBoughtThisGood()) {
            buyers.add(customer.getUsername());
        }
        return buyers;
    }

    public static ArrayList<Product> sortProductForSpecialSeller(String field,Seller seller){
        ArrayList<Product>         result = Product.getProductFieldForSort(field);
       ArrayList<Product> sorted = new ArrayList<>();
        for (Product product : result) {
            if (seller.getSalableProducts().contains(product))
                sorted.add(product);
        }
        return sorted;
    }
    public static ArrayList<Customer>  sortBuyers(String id , Seller seller,String field) throws ThisIsNotYours, NullProduct {
        Product product = getProduct(id, seller);
        return product.sortBuyers(field);
    }

    private static Product getProduct(String id, Seller seller) throws ThisIsNotYours, NullProduct {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product==null){
            throw  new NullProduct("null product",1);
        }
        if (!product.getSeller().equals(seller)) {
            throw new ThisIsNotYours("this product belongs to another seller", 2);
        }
        return product;
    }

    /**
     * it is more complete than add product i should check these two exceptions again
     * 1 for null pointer
     * 2 for this is not yours
     * 3 for you almost sold
     * 4 for successfully remove
     *
     * @param id
     * @param seller
     * @throws ThisIsNotYours
     * @throws SoldProductsCanNotHaveChange
     */
    public static boolean removeProduct(String id, Seller seller) throws ThisIsNotYours, SoldProductsCanNotHaveChange, NullProduct {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product == null) {

            throw new NullProduct("this product does not exist", 1);
        } else if (!product.getSeller().equals(seller)) {

            throw new ThisIsNotYours("this is not yours", 2);
        } else if (!seller.getSalableProducts().contains(product)) {

            throw new SoldProductsCanNotHaveChange("you almost sold this one", 3);
        } else {
            Product.deleteProduct(product);
            return true;
        }
    }

    public static boolean editProduct(HashMap<String, String> allChanges, String id, Seller seller) throws ThisIsNotYours, SoldProductsCanNotHaveChange, ThisAttributeIsNotForThisProduct, NoMatchBetweenCategoryAndAttributes, ThereIsNotCategoryWithNameException, NullProduct, InvalidNumber {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product == null) {
            throw new NullProduct("null product",1);
        }else if (product.getProductStatus()==null){
            throw new NullProduct("null product",1);
        }
        else if (!product.getProductStatus().equals(ProductAndOffStatus.CONFIRMED)){
            throw new NullProduct("null product",1);
        }
        else if (!product.getSeller().equals(seller)) {
            throw new ThisIsNotYours("this is not yours", 2);
        } else if (!seller.getSalableProducts().contains(product)) {
            throw new SoldProductsCanNotHaveChange("you almost sold this one", 3);
        } else {
            {
                String name = null;
                String company = null;
                double price = -1.0;
                int inventory = -1;
                Category category = null;
                String status = null;
                if (allChanges.containsKey("name")) {
                    name = allChanges.get("name");
                }
                if (allChanges.containsKey("category")) {
                    category = Category.getCategoryByName(allChanges.get("category"));
                    if (category == null) {
                        throw new ThereIsNotCategoryWithNameException("this category does not exist",4);
                    }
                }
                if (allChanges.containsKey("price")) {
                    if (!allChanges.get("price").matches("^\\d+.?\\d+$")) {
                        throw new InvalidNumber("number should be",5);
                    }
                    price = Double.parseDouble(allChanges.get("price"));
                    if (price <= 0)
                        throw new InvalidNumber("number is invalid",5);
                }
                if (allChanges.containsKey("inventory")) {
                    if (!allChanges.get("inventory").matches("^\\d+$")) {
                        throw new InvalidNumber("number should be",5);
                    }
                    inventory = Integer.parseInt(allChanges.get("inventory"));
                    if (inventory <= 0)
                        throw new InvalidNumber("number is invalid",5);
                }
                if (allChanges.containsKey("company")) {
                    company = allChanges.get("company");
                }
                HashMap<String, String> newChange = new HashMap<>();
                for (String s : allChanges.keySet()) {
                    if (!(s.equalsIgnoreCase("name") || s.equalsIgnoreCase("price") || s.equalsIgnoreCase("inventory") || s.equalsIgnoreCase("company") || s.equalsIgnoreCase("category"))) {
                        newChange.put(s, allChanges.get(s));
                    }
                }
if (product.getSpecialAttributes()!=null) {
    if (newChange.keySet().size() != 0) {

        for (String s : newChange.keySet()) {
            if (!product.getSpecialAttributes().keySet().contains(s)) {
                throw new ThisAttributeIsNotForThisProduct("this is not valid attribute for this category", 6);
            }
        }
    }
}
                if (category != null) {
                    if (newChange.keySet().size() != 0) {
                        if (!category.isThisAttributesForThisCategory(newChange)) {
                            throw new NoMatchBetweenCategoryAndAttributes("these attributes can't be matched with category",7);
                        }
                    }
                } else {
                    if (newChange.keySet().size() != 0) {
                        if (!product.getCategory().isThisAttributesForThisCategory(newChange)) {
                            throw new NoMatchBetweenCategoryAndAttributes("these attributes can't be matched with category",7);
                        }
                    }
                }
                product.setProductStatus(ProductAndOffStatus.FOREDIT);
                EditProductRequest editProductRequest = new EditProductRequest(seller, product, name, company, price, inventory, newChange, category);
                return true;
            }
        }
    }

    public static ArrayList<Off> showOffs(Seller seller) {
      return seller.getSellerOffs();
    }

    public static ArrayList<Off> sortOffs(String field  , Seller seller){
       ArrayList<Off> totalSorted =  OffBoss.sortOff(field);
       ArrayList<Off> result  = new ArrayList<>();
        for (Off off : totalSorted) {
            if (off.getSeller().equals(seller))
                result.add(off);
        }
        return result;
    }

    public static String viewOff(Seller seller, String id) throws ThisIsNotYours, ThisOffNotExist {
        Off off = Off.getOffById(Integer.parseInt(id));
        if (off == null) {
            throw new ThisOffNotExist("this does not exist", 1);
        }
        if (!seller.equals(off.getSeller())) {
            throw new ThisIsNotYours("this is not for you.", 2);
        }
        return off.showOff();
    }

    public static boolean editOff(Seller seller, Off off, HashMap<String, String> changes) throws   TimeLimit, InputStringExceptNumber, ThisIsNotReadyForEdit {
        String date = null;
        double maximum = -1.0;
        double percent = -1.0;
        LocalDateTime date1 = off.getFinalDate();
        LocalDateTime date2 = off.getStartDate();
        ProductAndOffStatus productAndOffStatus = null;
        String format = null;
        if (!off.getOffStatus().equals(ProductAndOffStatus.CONFIRMED)) {
            throw new ThisIsNotReadyForEdit("this is not ready",1);
        }
        for (String s : changes.keySet()) {
            if (changes.get(s) != null && !changes.get(s).equalsIgnoreCase("\n") && !changes.get(s).equalsIgnoreCase("")) {
                if (s.equalsIgnoreCase("startDate")) {
                    date2 = LocalDateTime.parse(changes.get(s));
                }
                if (s.equalsIgnoreCase("finalDate")) {
                    dateOfNow = LocalDateTime.now();
                    date = changes.get(s);
                    date1 = LocalDateTime.parse(date);
                    if (date1.isBefore(dateOfNow)) {
                        throw new TimeLimit(2,"this time is passed");
                    } else if (date1.isBefore(date2)) {
                        throw new TimeLimit(2,"finalize is sooner starting");
                    }
                } else if (s.equalsIgnoreCase("maximumAmountOfOff")) {
                    if (changes.get(s).matches("^(\\d+)(.?)(\\d*)$")) {
                        maximum = Double.parseDouble(changes.get(s));
                    } else {
                        throw new InputStringExceptNumber("max should be double",3);
                    }
                } else if (s.equalsIgnoreCase("offPercent")) {
                    if (changes.get(s).matches("^(\\d+)(.?)(\\d*)$")) {
                        percent = Double.parseDouble(changes.get(s));
                    } else
                        throw new InputStringExceptNumber("percent should be double",3);
                }
            }
        }
        off.setOffStatus(ProductAndOffStatus.FOREDIT);
        EditOffRequest editOffRequest = new EditOffRequest((Seller) Account.getOnlineAccount(), off, maximum, percent, date2, date1);
        return true;

    }

    public static boolean addOff(ArrayList<Integer> id, Seller seller, String startDate, String finalDate, String percents, String maxs) throws ParseException, ThisIsNotYours, TimeLimit, InvalidNumber, InputStringExceptNumber, NullProduct, JustOneOffForEveryProduct {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime finalDates = LocalDateTime.parse(finalDate);

        if (start.isAfter(finalDates)) {
            throw new TimeLimit(1,"this time is wrong");
        }
        if (!maxs.matches("^(\\d+)(.?)(\\d*)$")) {
            throw new InputStringExceptNumber("max has mistake",2);
        }
        if (!percents.matches("^(\\d+)(.?)(\\d*)$")) {
            throw new InputStringExceptNumber("percent has mistake",2);
        }
        double max = Double.parseDouble(maxs);
        double percent = Double.parseDouble(percents);
        if (percent >100) {
            throw new InvalidNumber("you can't give negative",3);
        }
        ArrayList<Product> allProducts = new ArrayList<>();
        if (id!=null) {
            for (Integer integer : id) {
                if (seller.hasHeProductWithId(integer)!=2) {
                    throw new ThisIsNotYours("this is not yours", 4);
                }
                else if (Product.getProductWithId(integer)==null){
                    throw new NullProduct("null product",5);
                }else if (Off.isThereProduct(Product.getProductWithId(integer))){
                    throw new JustOneOffForEveryProduct("product is in another one",6);
                }
                allProducts.add(Product.getProductWithId(integer));
            }
        }
        Off off = new Off(finalDates, start, allProducts, max, percent, seller);
        off.setOffStatus(ProductAndOffStatus.FORMAKE);
        AddOffRequest addOffRequest = new AddOffRequest(seller, off);
        return true;
    }
}
