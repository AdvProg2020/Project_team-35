package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(StringBuilder request, RequestTypes requestTypes) {
        super(request, requestTypes);
    }

    public void execute()  {
        Manager.newRequests.add(this) ;
    }

    /**
     * updated
     * @return
     */
    public StringBuilder getDetails() {
        return request;
    }
}
