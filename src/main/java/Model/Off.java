package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Off {
    public static ArrayList<Off> allActiveOffs;
    private static int offIdNumber;
    private int offId;
    private String finalDate;
    private ArrayList<Product> includedProducts;
    private double maximumAmountOfOff;
    private double offPercent;
    private ProductAndOffStatus offStatus;

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
        show.append("status: "+offStatus.name()+"\n");
        show.append("percent: "+offPercent+"\n"+"maximum: "+maximumAmountOfOff+"\n");
        for (Product product : includedProducts) {
            show.append("product id: "+product.getProductId()+"\n");
            show.append("product name: "+product.getName()+"\n");
            show.append("product inventory: "+product.getInventory()+"\n");
        }
        return show.toString();
    }

    public void setFinalDate(String finalDate) {
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

    public String getFinalDate() {
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

}
