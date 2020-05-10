package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request {
   private Off off;

    public AddOffRequest(Seller seller, Off off) {
        super(seller);
        this.off = off;
    }

    public String getDetails() {
        return "ADD OFF Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

    }
    public void execute() {
        this.isDone = true;
        off.setOffStatus(ProductAndOffStatus.CONFIRMED);

    }
    public String getRequestInfo() {
        return "  ADD OFF Request --- UserName: " + seller.getUsername() + " --- RQId: " + this.getRequestId();
    }
}
