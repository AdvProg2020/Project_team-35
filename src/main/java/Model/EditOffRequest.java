package Model;

import java.time.LocalDateTime;

public class EditOffRequest extends Request {
    private Off toEdit;
    private double newMaximumAmountOfOff;
    private double newOffPercent;
    private LocalDateTime newDate;
    private LocalDateTime newFinalDate;

    public EditOffRequest(Seller seller, Off toEdit, double newMaximumAmountOfOff, double newOffPercent, LocalDateTime newDate , LocalDateTime newFinalDate) {
        super(seller, "Edit Off");
        this.toEdit = toEdit;
        this.newMaximumAmountOfOff = newMaximumAmountOfOff;
        this.newOffPercent = newOffPercent;
        this.newDate = newDate;
        this.newFinalDate = newFinalDate;
    }
    public String getDetails() {
        return "Edit Off Request : \nRequestId: " + this.getRequestId() + "\nCurrentStartDate: " + this.toEdit.startDateToString()
                +" --- NewStartDate: " + this.startDateToString()
                + "\nFinal Date: " + this.toEdit.expireDateToString()
                + " --- NewFinalDate: " + this.expireDateToString() + "\nMaximum Amount: " + this.toEdit.getMaximumAmountOfOff()
                +" --- NewMaximumAmount: " + this.getNewMaximumAmountOfOff()
                + "\nPercent: " + this.toEdit.getOffPercent() + " --- NewPercent: " + this.getNewOffPercent()
                + "\nRequester Username: " + this.seller.getUsername()
                + "\nIncluded Product Ids: " + this.toEdit.getIncludedProductsId();
    }
    public String getRequestInfo() {
        return "  Edit Off Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();

    }
    public boolean execute() {
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
            return true;
    }

    public void decline(){
        this.toEdit.setOffStatus(ProductAndOffStatus.CONFIRMED);
    }

    public String expireDateToString() {
        return newFinalDate.getYear() + "-" + newFinalDate.getMonthValue() + "-" + newFinalDate.getDayOfMonth();
    }
    public String startDateToString() {
        return newDate.getYear() + "-" + newDate.getMonthValue() + "-" + newDate.getDayOfMonth();
    }

    public double getNewMaximumAmountOfOff() {
        return newMaximumAmountOfOff;
    }

    public double getNewOffPercent() {
        return newOffPercent;
    }

}
