package Model;

import javafx.beans.property.SimpleStringProperty;

public class SellerRegisterRequest extends Request {


    public SellerRegisterRequest(Seller seller) {
        super(seller, "Seller Register");
    }

    public static SimpleStringProperty requestType = new SimpleStringProperty("Seller Register");
    public String getRequestInfo () {
        return "  Seller Register Request --- username: "+seller.getUsername()+" --- RQId: " + this.getRequestId();
    }

    public boolean execute()  {
        Seller.allSellers.add(seller);
        this.isDone = true;
        return true;
    }

    /**
     * updated
     * @return
     */
    public String getDetails() {
        return "Seller Register Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();
    }

    public void decline() {
        Account.getAllAccounts().remove(this.getSeller());
    }
}
