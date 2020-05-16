package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Off {
    public static ArrayList<Off> allActiveOffs = new ArrayList<>();
    private static int offIdNumber;
    private int offId;
    private LocalDateTime finalDate;
    private  LocalDateTime startDate;
    private ArrayList<Product> includedProducts;
    private double maximumAmountOfOff;
    private double offPercent;
    private ProductAndOffStatus offStatus;
    private Seller seller;

    public Off(LocalDateTime finalDate, LocalDateTime startDate, ArrayList<Product> includedProducts, double maximumAmountOfOff, double offPercent, Seller seller) {
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.includedProducts = includedProducts;
        this.maximumAmountOfOff = maximumAmountOfOff;
        this.offPercent = offPercent;
        Off.allActiveOffs.add(this);
        this.seller = seller;
        seller.getSellerOffs().add(this);
        offIdNumber+=1;
        offId = offIdNumber;
    }
    public Seller getSeller() {
        return seller;
    }

    public int getOffId() {
        return offId;
    }
    public static Off getOffById(int id){
        for (Off activeOff : allActiveOffs) {
            if (activeOff.getOffId() == id)
                return activeOff;
        }
        return null;
    }
    public String showOff(){
        StringBuilder show = new StringBuilder();
        show.append("id: "+getOffId()+"\n");
        show.append("start date: "+getStartDate().toString()+"\nfinal date: "+getFinalDate().toString()+"\n");
        show.append("status: "+offStatus.name()+"\n");
        show.append("percent: "+offPercent+"\n"+"maximum: "+maximumAmountOfOff+"\n");
        if (includedProducts!=null) {
            for (Product product : includedProducts) {
                show.append("product id: " + product.getProductId() + "\n");
                show.append("product name: " + product.getName() + "\n");
                show.append("product inventory: " + product.getInventory() + "\n");
            }
        }

        return String.valueOf(show);
    }

    public ArrayList<Product> getIncludedProducts() {
        return includedProducts;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setMaximumAmountOfOff(double maximumAmountOfOff) {
        this.maximumAmountOfOff = maximumAmountOfOff;
    }

    public void setOffPercent(double offPercent) {
        this.offPercent = offPercent;
    }

    public void setOffStatus(ProductAndOffStatus offStatus) {
        this.offStatus = offStatus;
    }

    public  LocalDateTime getFinalDate() {
        return finalDate;
    }

    public double getMaximumAmountOfOff() {
        return maximumAmountOfOff;
    }

    public double getOffPercent() {
        return offPercent;
    }

    public ProductAndOffStatus getOffStatus() {
        return offStatus;
    }

    public  LocalDateTime getStartDate() {
        return startDate;
    }

    public int getNumberOfOffProduct(){
        return getIncludedProducts().size();
    }
    public void setStartDate( LocalDateTime startDate) {
        this.startDate = startDate;
    }


    public String expireDateToString() {
        return finalDate.getYear() + "-" + finalDate.getMonthValue() + "-" + finalDate.getDayOfMonth();
    }
    public String startDateToString() {
        return startDate.getYear() + "-" + startDate.getMonthValue() + "-" + startDate.getDayOfMonth();
    }

    public String getIncludedProductsId() {
        StringBuilder toReturn = new StringBuilder();
        for (Product product : this.includedProducts) {
            toReturn.append(product.getProductId());
            toReturn.append(" -- ");
        }
        return toReturn.toString();
    }

    public static ArrayList<Off> getAllActiveOffs() {
        return allActiveOffs;
    }

    public static ArrayList<Off> sorting(String field){
        if (field.equalsIgnoreCase("startDate")){
            Collections.sort(allActiveOffs,Comparator.comparing(Off::getStartDate));
        }else if (field.equalsIgnoreCase("finishDate")){
            Collections.sort(allActiveOffs,Comparator.comparing(Off::getFinalDate));
        }else if (field.equalsIgnoreCase("max")){
            Collections.sort(allActiveOffs,Comparator.comparing(Off::getMaximumAmountOfOff));
        }else if (field.equalsIgnoreCase("percent")){
            Collections.sort(allActiveOffs,Comparator.comparing(Off::getOffPercent));
        }else if (field.equalsIgnoreCase("number")){
            Collections.sort(allActiveOffs,Comparator.comparing(Off::getNumberOfOffProduct));
        }
        return allActiveOffs;
    }
    public static boolean isThereProduct(Product product){
        for (Off activeOff : allActiveOffs) {
            if (activeOff.getIncludedProducts().contains(product))
                return true;
        }
        return false;
    }
    public static ArrayList<Product> getAllProducts(){
        ArrayList<Product> result = new ArrayList<>();
        for (Off allActiveOff : allActiveOffs) {
            for (Product product : allActiveOff.getIncludedProducts()) {
                result.add(product);
            }
        }
        return result;
    }
    public static ArrayList<Product> sortAllProducts(String field){
        ArrayList<Product> a = getAllProducts();
        if (field.equalsIgnoreCase("name")){
            Collections.sort(a,Comparator.comparing(Product::getName));
        }else if (field.equalsIgnoreCase("price")){
            Collections.sort(a,Comparator.comparing(Product::getPrice));
        }else if (field.equalsIgnoreCase("rate")){
            Collections.sort(a,Comparator.comparing(Product::getAverageOfRates));
        }else if (field.equalsIgnoreCase("reviewNumber")){
            Collections.sort(a,Comparator.comparing(Product::getReviewNumber));
        }else if (field.equalsIgnoreCase("inventory")){
            Collections.sort(a,Comparator.comparing(Product::getInventory));
        }
        return a;
    }
}
