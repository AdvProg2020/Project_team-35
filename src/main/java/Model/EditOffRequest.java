package Model;

import java.util.ArrayList;
import java.util.Date;

public class EditOffRequest extends Request {
    private Off toEdit;
    private double newMaximumAmountOfOff;
    private double newOffPercent;
    private ProductAndOffStatus newOffStatus;
    private Date newDate;

    public EditOffRequest(Seller seller, Off toEdit, double newMaximumAmountOfOff, double newOffPercent, ProductAndOffStatus newOffStatus, Date newDate) {
        super(seller);
        this.toEdit = toEdit;
        this.newMaximumAmountOfOff = newMaximumAmountOfOff;
        this.newOffPercent = newOffPercent;
        this.newOffStatus = newOffStatus;
        this.newDate = newDate;
    }
    public String getDetails() {
        return null;
    }
    public String getRequestInfo() {
        return null;
    }
    public void execute() {

    }
}
