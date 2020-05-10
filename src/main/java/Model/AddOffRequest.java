package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request {
    private LocalDateTime newStartDate;
    private LocalDateTime newFinalDate;
    private double percent;
    private double max;
    private ArrayList<Product> products;

    public AddOffRequest(Seller seller, LocalDateTime newStartDate, LocalDateTime newFinalDate, double percent, double max, ArrayList<Product> products) {
        super(seller);
        this.newStartDate = newStartDate;
        this.newFinalDate = newFinalDate;
        this.percent = percent;
        this.max = max;
        this.products = products;
    }

    public String getDetails() {
        return "ADD OFF Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

    }
    public void execute() {
        this.isDone = true;
        Off off = new Off(newFinalDate,newStartDate,products,max,percent,seller);
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
    }
    public String getRequestInfo() {
        return "  ADD OFF Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }
}
