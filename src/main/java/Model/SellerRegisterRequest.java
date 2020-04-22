package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(StringBuilder request, RequestTypes requestTypes, Seller requester) {
        super(request, requestTypes);
        this.requester = requester;
    }
    private Seller requester;

    public void execute()  {
    }

    /**
     * updated
     * @return
     */
    public StringBuilder getDetails() {
        return request;
    }
}
