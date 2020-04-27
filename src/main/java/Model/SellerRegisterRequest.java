package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(Seller requester) {
        super();
        this.requester = requester;
    }
    private Seller requester;

    public String getRequestInfo () {
        return "  Seller Register Request --- UserName: " + requester.getUsername() + " --- RQId: " + this.getRequestId();
    }

    public void execute()  {
        Seller.allSellers.add(requester);
    }

    /**
     * updated
     * @return
     */
    public String getDetails() {
        return null;
    }
}
