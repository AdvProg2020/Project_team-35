package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(StringBuilder request, RequestTypes requestTypes, Seller requester) {
        super(request, requestTypes,requester);
    }
    private Seller requester;

    public void execute()  {
        Seller.allSellers.add(requester);
    }

    /**
     * updated
     * @return
     */
    public StringBuilder getDetails() {
        return request;
    }
}
