package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Off {
    public static ArrayList<Off> allActiveOffs = new ArrayList<>();
    private static int offIdNumber;
    private int offId;
    private Date finalDate;
    private Date startDate;
    private ArrayList<Product> includedProducts;
    private double maximumAmountOfOff;
    private double offPercent;
    private ProductAndOffStatus offStatus;
    private Seller seller;

    public Off(Date finalDate, Date startDate, ArrayList<Product> includedProducts, double maximumAmountOfOff, double offPercent,Seller seller) {
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.includedProducts = includedProducts;
        this.maximumAmountOfOff = maximumAmountOfOff;
        this.offPercent = offPercent;
        allActiveOffs.add(this);
        this.seller = seller;
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
        for (Product product : includedProducts) {
            show.append("product id: "+product.getProductId()+"\n");
            show.append("product name: "+product.getName()+"\n");
            show.append("product inventory: "+product.getInventory()+"\n");
        }
        return show.toString();
    }

    public void setFinalDate(Date finalDate) {
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

    public Date getFinalDate() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
