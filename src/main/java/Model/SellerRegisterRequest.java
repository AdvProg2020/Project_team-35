package Model;

public class SellerRegisterRequest extends Request {


    public SellerRegisterRequest(Seller seller) {
        super(seller);
    }

    public String getRequestInfo () {
        return "  Seller Register Request --- username: "+seller.getUsername()+" --- RQId: " + this.getRequestId();
    }

    public void execute()  {
        Seller.allSellers.add(seller);
        this.isDone = true;
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
