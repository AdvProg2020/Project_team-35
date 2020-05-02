package Model;

import java.util.ArrayList;
import java.util.Date;

public class EditOffRequest extends Request {
    private Off toEdit;
    private double newMaximumAmountOfOff;
    private double newOffPercent;
    private Date newDate;
    private Date newFinalDate;

    public EditOffRequest(Seller seller, Off toEdit, double newMaximumAmountOfOff, double newOffPercent, Date newDate , Date newFinalDate) {
        super(seller);
        this.toEdit = toEdit;
        this.newMaximumAmountOfOff = newMaximumAmountOfOff;
        this.newOffPercent = newOffPercent;
        this.newDate = newDate;
        this.newFinalDate = newFinalDate;
    }
    public String getDetails() {
        return "Seller Register Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();
    }
    public String getRequestInfo() {
        return "  EDIT OFF Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();

    }
    public void execute() {
            this.isDone = true;
            toEdit.setOffStatus(ProductAndOffStatus.CONFIRMED);
            if (newDate!=null){
                toEdit.setStartDate(newDate);
            }
            if (newMaximumAmountOfOff != -1.0){
        toEdit.setMaximumAmountOfOff(newMaximumAmountOfOff);
            }
            if (newOffPercent != -1.0){
                        toEdit.setOffPercent(newOffPercent);
            }
            if (newFinalDate!= null){
                toEdit.setFinalDate(newFinalDate);
            }

    }
}
