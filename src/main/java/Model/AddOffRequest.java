package Model;

import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request {
    private Date newStartDate;
    private Date newFinalDate;
    private double percent;
    private double max;
    private ArrayList<Product> products;

    public AddOffRequest(Seller seller, Date newStartDate, Date newFinalDate, double percent, double max, ArrayList<Product> products) {
        super(seller);
        this.newStartDate = newStartDate;
        this.newFinalDate = newFinalDate;
        this.percent = percent;
        this.max = max;
        this.products = products;
    }

    public String getDetails() {
        return null;
    }
    public void execute() {

    }
    public String getRequestInfo() {
        return null;
    }
}
