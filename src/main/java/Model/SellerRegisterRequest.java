package Model;

public class SellerRegisterRequest extends Request {
    private StringBuilder requester;

    /**
     * added
     * @param requester
     */
    public SellerRegisterRequest(StringBuilder requester) {
        super(requester);
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
        return requester;
    }
}
