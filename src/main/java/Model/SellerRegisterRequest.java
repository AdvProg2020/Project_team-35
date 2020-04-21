package Model;

public class SellerRegisterRequest extends Request {

    public SellerRegisterRequest(StringBuilder request, RequestTypes requestTypes) {
        super(request, requestTypes);
    }

    public void execute() throws RequestProblemNotExistManager {
        if (!Manager.isThereAnyManager()){
            throw new RequestProblemNotExistManager("we don't have a manager yet please wait for a manager");
        }
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
