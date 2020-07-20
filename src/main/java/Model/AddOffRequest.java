package Model;

import java.io.Serializable;

public class AddOffRequest extends Request implements Serializable {
   private Off off;

    public AddOffRequest(Seller seller, Off off) {
        super(seller, "Add Off");
        this.off = off;
    }

    public String getDetails() {
        return "Add Off Request : \nRequestId: " + this.getRequestId() + "\nStart Date: " + this.off.startDateToString()
                + "\nFinal Date: " + this.off.expireDateToString() + "\nMaximum Amount: " + this.off.getMaximumAmountOfOff()
                + "\nPercent: " + this.off.getOffPercent() + "\nRequester Username: " + this.seller.getUsername()
                + "\nIncluded Product Ids: " + this.off.getIncludedProductsId();

    }

    public boolean execute() {
        this.isDone = true;
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);
        return true;
    }

    public String getRequestInfo() {
        return "  Add Off Request --- Requester Username: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }
    public void decline() {
        this.off.setOffStatus(ProductAndOffStatus.DECLINED);
    }
}
