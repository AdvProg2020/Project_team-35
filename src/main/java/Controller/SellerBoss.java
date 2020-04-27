package Controller;

import Model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SellerBoss {
    private static Date dateOfNow;
    public static double sellerCredit(Seller seller){
        return seller.getMoney();
    }
    public static ArrayList<String> showCategories(){
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.allCategories) {
            categories.add(category.getCategoryName());
        }
        return categories;
    }
    public static ArrayList<String> showHistoryOfSales(Seller seller){
        ArrayList<String> sales = new ArrayList<>();
        for (SellLog log : seller.getSellLogs()) {
            for (Product soldProduct : log.getSoldProducts()) {
                sales.add(soldProduct.getName());
            }
        }
        return sales;
    }
    public static String showProduct(String id , Seller seller) throws ThisIsNotYours {
        int productId = Integer.parseInt(id);
      Product product =   Product.getProductWithId(productId);
      if (product==null){
          throw new ThisIsNotYours("we don't have this one.");
      }
      if (!seller.equals(product.getSeller())){
          throw new ThisIsNotYours("this is not yours");
      }
      return product.toString();
    }
    public static void addRequestProduct(String name , String price , String inventory , HashMap<String , String > attributes , String company , Category category , Seller seller){
    double productPrice = Double.parseDouble(price);
    int number = Integer.parseInt(inventory);
    StringBuilder request = new StringBuilder();
    request.append("name:"+name+"\n"+"price:"+price+"\n"+"inventory:"+inventory+"\n");
    request.append("company:"+company+"\n"+"category:"+category.getCategoryName()+"\n");
        for (String s : attributes.keySet()) {
            request.append(s+":"+attributes.get(s)+"\n");
        }
      //  AddProductRequest addProductRequest = new AddProductRequest(request,RequestTypes.ADD_PRODUCT,seller);
    }
    public static ArrayList<String> showBuyers(String id , Seller seller) throws ThisIsNotYours {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (!product.getSeller().equals(seller)){
            throw new ThisIsNotYours("this product belongs to another seller");
        }
        ArrayList<String> buyers = new ArrayList<>();
        for (Customer customer : product.getWhoBoughtThisGood()) {
            buyers.add(customer.getUsername());
        }
        return buyers;
    }

    /**
     * it is more complete than add product i should check these two exceptions again
     * @param id
     * @param seller
     * @throws ThisIsNotYours
     * @throws SoldProductsCanNotHaveChange
     */
    public static void removeProduct(String id , Seller seller) throws ThisIsNotYours, SoldProductsCanNotHaveChange {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product==null){
            throw new NullPointerException();
        }else if (!product.getSeller().equals(seller)){
            throw new ThisIsNotYours("this is not yours");
        }else if (!seller.getSalableProducts().contains(product)){
            throw new SoldProductsCanNotHaveChange("you almost sold this one");
        }
        else {
            Product.deleteProduct(product);
        }
    }
    public static void editProduct(HashMap<String , String> allChanges , String id , Seller seller) throws ThisIsNotYours, SoldProductsCanNotHaveChange, ThisAttributeIsNotForThisProduct {
        int iD = Integer.parseInt(id);
        Product product = Product.getProductWithId(iD);
        if (product==null){
            throw new NullPointerException();
        }else if (!product.getSeller().equals(seller)){
            throw new ThisIsNotYours("this is not yours");
        }else if (!seller.getSalableProducts().contains(product)){
            throw new SoldProductsCanNotHaveChange("you almost sold this one");
        }else {
            if (!product.getSpecialAttributes().containsKey(allChanges.keySet())){
                throw new ThisAttributeIsNotForThisProduct("invalid attribute which does not exist in this category.");
            }
            else {
                StringBuilder request = new StringBuilder();
                ///////////*************************************/****************88//////////////////////////////
                request.append("edit product request");
                String name = null;
                String company = null;
                String price =  null;
                String inventory = null;
                if (allChanges.keySet().contains("name")){

                }if (allChanges.keySet().contains("price")){

                }if (allChanges.keySet().contains("inventory")){

                }if (allChanges.keySet().contains("company")){

                }
                HashMap<String , String> newChange = new HashMap<>();
                for (String s : allChanges.keySet())
                    if (!(s.equalsIgnoreCase("name") || s.equalsIgnoreCase("price") || s.equalsIgnoreCase("inventory") || s.equalsIgnoreCase("company"))) {
                        newChange.put(s, allChanges.get(s));
                    }
               // EditProductRequest editProductRequest = new EditProductRequest(request,RequestTypes.EDIT_PRODUCT,seller,product ,ProductAndOffStatus.FOREDIT,name,company,Double.parseDouble(price),Integer.parseInt(inventory),newChange);
            }
        }
    }
    public static ArrayList<String> showOffs(Seller seller){
        ArrayList<String> offs = new ArrayList<>();
        for (Off off : seller.getSellerOffs()) {
            offs.add(String.valueOf(off.getOffId()));
        }
        return offs;
    }
    public static String viewOff(Seller seller , String id) throws ThisIsNotYours {
        Off off = Off.getOffById(Integer.parseInt(id));
        if (off==null){
            throw new ThisIsNotYours("this does not exist");
        }
        if (!seller.getSellerOffs().contains(off)){
            throw new ThisIsNotYours("this is not for you.");
        }
        return off.showOff();
    }
    public static void editOff(Seller seller , Off off , HashMap<String , String> changes) throws ItIsNotCorrect, ParseException, TimeLimit {
     String date = null;
     double maximum = -1.0;
     double percent = -1.0;
        Date date1  = null;
        ProductAndOffStatus productAndOffStatus = null;
        String format = null;
        for (String s : changes.keySet()) {
            if (s.equalsIgnoreCase("finalDate")){
                dateOfNow = new Date();
                    date = changes.get(s);
                     date1 = new SimpleDateFormat("dd//MM/yyyy").parse(date);
                    if (date1.before(dateOfNow)){
                        throw new TimeLimit("this time is passed");
                    }
            } else if (s.equalsIgnoreCase("maximumAmountOfOff")) {
                maximum = Double.parseDouble(changes.get(s));
            } else if (s.equalsIgnoreCase("offPercent")) {
percent = Double.parseDouble(changes.get(s));
            }else if (s.equalsIgnoreCase("offStatus")){
                if (off.getOffStatus().equals(ProductAndOffStatus.CONFIRMED)){
                    throw new ItIsNotCorrect("this can't be done");
                }
format = changes.get(s);
if (format.equalsIgnoreCase("FOREDIT")){
productAndOffStatus = ProductAndOffStatus.FOREDIT;
} else if (format.equalsIgnoreCase("CONFIRMED")) {
    productAndOffStatus = ProductAndOffStatus.CONFIRMED;

}else if (format.equalsIgnoreCase("FORMAKE")){
    productAndOffStatus = ProductAndOffStatus.FORMAKE;
}
            }
        }
        EditOffRequest editOffRequest = new EditOffRequest(seller,off,maximum,percent,productAndOffStatus,date1);


    }
}
