package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(Seller requester) {
        super();
        this.requester = requester;
    }
    private Seller requester;

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
