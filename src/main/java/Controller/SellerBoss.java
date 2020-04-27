package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerBoss {
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
    public static String showProduct(String id){
        int productId = Integer.parseInt(id);
      Product product =   Product.getProductWithId(productId);
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
}
