package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SellerBoss {
    private static Date dateOfNow;

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

    public static ArrayList<String> showHistoryOfSales(Seller seller) {
        ArrayList<String> sales = new ArrayList<>();
        for (SellLog log : seller.getSellLogs()) {
            for (Product soldProduct : log.getSoldProducts()) {
                sales.add(soldProduct.getName());
            }
        }
        return sales;
    }

    public static String showProduct(String id, Seller seller) throws ThisIsNotYours {
        int productId = Integer.parseInt(id);
        Product product = Product.getProductWithId(productId);
        if (product == null) {
            throw new ThisIsNotYours("we don't have this one.",productId);
        }
        if (!seller.equals(product.getSeller())) {
            throw new ThisIsNotYours("this is not yours",productId);
        }
        return product.toString();
    }

    public static boolean addRequestProduct(String name, String price, String inventory, HashMap<String, String> attributes, String company, Category category, Seller seller) {
        double productPrice = Double.parseDouble(price);
        int number = Integer.parseInt(inventory);
        AddProductRequest addProductRequest = new AddProductRequest(seller,name,company,number,productPrice,category,attributes);
        return true;
    }

    public static ArrayList<String> showBuyers(String id, Seller seller) throws ThisIsNotYours {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (!product.getSeller().equals(seller)) {
            throw new ThisIsNotYours("this product belongs to another seller",iD);
        }
        ArrayList<String> buyers = new ArrayList<>();
        for (Customer customer : product.getWhoBoughtThisGood()) {
            buyers.add(customer.getUsername());
        }
        return buyers;
    }

    /**
     * it is more complete than add product i should check these two exceptions again
     *1 for null pointer
     * 2 for this is not yours
     * 3 for you almost sold
     * 4 for successfully remove
     * @param id
     * @param seller
     * @throws ThisIsNotYours
     * @throws SoldProductsCanNotHaveChange
     */
    public static boolean removeProduct(String id, Seller seller,int testIdentifier) throws ThisIsNotYours, SoldProductsCanNotHaveChange, NullProduct {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product == null) {
            testIdentifier = 1;
            throw new NullProduct("this product does not exist",1);
        } else if (!product.getSeller().equals(seller)) {
            testIdentifier = 2;
            throw new ThisIsNotYours("this is not yours",2);
        } else if (!seller.getSalableProducts().contains(product)) {
            testIdentifier = 3;
            throw new SoldProductsCanNotHaveChange("you almost sold this one",3);
        } else {
            testIdentifier = 4;
            Product.deleteProduct(product);
            return true;
        }
    }

    public static void editProduct(HashMap<String, String> allChanges, String id, Seller seller) throws ThisIsNotYours, SoldProductsCanNotHaveChange, ThisAttributeIsNotForThisProduct, NoMatchBetweenCategoryAndAttributes {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product == null) {
            throw new NullPointerException();
        } else if (!product.getSeller().equals(seller)) {
            throw new ThisIsNotYours("this is not yours",iD);
        } else if (!seller.getSalableProducts().contains(product)) {
            throw new SoldProductsCanNotHaveChange("you almost sold this one",iD);
        } else {
            if (!product.getSpecialAttributes().containsKey(allChanges.keySet())) {
                throw new ThisAttributeIsNotForThisProduct("invalid attribute which does not exist in this category.");
            } else {
                StringBuilder request = new StringBuilder();
                ///////////*************************************/****************88//////////////////////////////
                String name = null;
                String company = null;
                double price = -1.0;
                int inventory = -1;
                Category category = null;
                String status = null;
                if (allChanges.keySet().contains("name")) {
                        name = allChanges.get("name");
                }
                if (allChanges.keySet().contains("category")){
                    category = Category.getCategoryByName(allChanges.get("category"));
                }
                if (allChanges.keySet().contains("price")) {
                        price = Double.parseDouble(allChanges.get("price"));
                }
                if (allChanges.keySet().contains("inventory")) {
                        inventory = Integer.parseInt(allChanges.get("inventory"));
                }
                if (allChanges.keySet().contains("company")) {
                        company = allChanges.get("company");
                }
                HashMap<String, String> newChange = new HashMap<>();
                for (String s : allChanges.keySet())
                    if (!(s.equalsIgnoreCase("name") || s.equalsIgnoreCase("price") || s.equalsIgnoreCase("inventory") || s.equalsIgnoreCase("company") || s.equalsIgnoreCase("category"))) {
                        newChange.put(s, allChanges.get(s));
                    }
                if (category!=null){
                    if (!category.isThisAttributesForThisCategory(newChange)){
                        throw new NoMatchBetweenCategoryAndAttributes("these attributes can't be matched with category");
                    }
                }else{
                    if (!product.getCategory().isThisAttributesForThisCategory(newChange)){
                        throw new NoMatchBetweenCategoryAndAttributes("these attributes can't be matched with category");
                    }
                }
                EditProductRequest editProductRequest = new EditProductRequest(seller,product,name,company,price,inventory,newChange,category);
            }
        }
    }

    public static ArrayList<String> showOffs(Seller seller) {
        ArrayList<String> offs = new ArrayList<>();
        for (Off off : seller.getSellerOffs()) {
            offs.add(String.valueOf(off.getOffId()));
        }
        return offs;
    }

    public static String viewOff(Seller seller, String id) throws ThisIsNotYours {
        Off off = Off.getOffById(Integer.parseInt(id));
        if (off == null) {
            throw new ThisIsNotYours("this does not exist",Integer.parseInt(id));
        }
        if (!seller.getSellerOffs().contains(off)) {
            throw new ThisIsNotYours("this is not for you.",Integer.parseInt(id));
        }
        return off.showOff();
    }

    public static void editOff(Seller seller, Off off, HashMap<String, String> changes) throws ItIsNotCorrect, ParseException, TimeLimit, InputStringExceptNumber {
        String date = null;
        double maximum = -1.0;
        double percent = -1.0;
        Date date1 = off.getFinalDate();
        Date date2 = off.getStartDate();
        ProductAndOffStatus productAndOffStatus = null;
        String format = null;
        for (String s : changes.keySet()) {
            if (s.equalsIgnoreCase("startDate")){
                date2  = new SimpleDateFormat("dd//MM//yyyy").parse(changes.get(s));
            }
            if (s.equalsIgnoreCase("finalDate")) {
                dateOfNow = new Date();
                date = changes.get(s);
                date1 = new SimpleDateFormat("dd//MM/yyyy").parse(date);
                if (date1.before(dateOfNow)) {
                    throw new TimeLimit("this time is passed");
                }else if (date1.before(date2)){
                    throw new TimeLimit("finalize is sooner starting");
                }
            } else if (s.equalsIgnoreCase("maximumAmountOfOff")) {
                if (changes.get(s).matches("^\\d+.\\d+$")) {
                    maximum = Double.parseDouble(changes.get(s));
                }else {
                    throw new InputStringExceptNumber("max should be double");
                }
            } else if (s.equalsIgnoreCase("offPercent")) {
                if (changes.get(s).matches("\\d+.\\d+")) {
                    percent = Double.parseDouble(changes.get(s));
                }
                else
                    throw new InputStringExceptNumber("percent should be double");
            }
        }
        EditOffRequest editOffRequest = new EditOffRequest((Seller)Account.getOnlineAccount(),off,maximum,percent,date2,date1);


    }
    public static void addOff(ArrayList<Integer> id  , Seller seller  , String startDate , String finalDate,String percents , String maxs) throws ParseException, ThisIsNotYours, TimeLimit, InvalidNumber, InputStringExceptNumber {
           Date start = new SimpleDateFormat("dd//MM//yyyy").parse(startDate);
           Date finalDates = new SimpleDateFormat("dd//MM//yyyy").parse(finalDate);
           if (start.after(finalDates)){
               throw new TimeLimit("this time is wrong");
           }
           if (maxs.matches("^\\d+.\\d+$")){
               throw new InputStringExceptNumber("max has mistake");
           }
           if (percents.matches("^\\d+.\\d+$")){
               throw new InputStringExceptNumber("percent has mistake");
           }
           double max = Double.parseDouble(maxs);
           double percent = Double.parseDouble(percents);
           if (percent<0 || max <0){
               throw new InvalidNumber("you can't give negative");
           }
           ArrayList<Product> allProducts = new ArrayList<>();
        for (Integer integer : id) {
            if (!seller.getSalableProducts().contains(Product.getProductWithId(integer))){
                throw new ThisIsNotYours("this is not yours",integer);
            }
            allProducts.add(Product.getProductWithId(integer));
        }
            AddOffRequest addOffRequest = new AddOffRequest(seller,start,finalDates,percent,max,allProducts);
    }
}
