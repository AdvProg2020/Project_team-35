package Model;

public class SellerRegisterRequest extends Request {

    private String companyName;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    public SellerRegisterRequest( Seller seller,String companyName, String username, String firstName, String lastName, String email, String phoneNumber, String password) {
       super(seller);
        this.companyName = companyName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getRequestInfo () {
        return "  Seller Register Request "+" --- RQId: " + this.getRequestId();
    }

    public void execute()  {
        Seller seller1 = new Seller(username,firstName,lastName,email,phoneNumber,password,companyName);
        Seller.allSellers.add(seller1);
        this.isDone = true;
    }

    /**
     * updated
     * @return
     */
    public String getDetails() {
        return "Seller Register Request : \nRequestId: " + this.getRequestId() + "\n" + this.seller.getPersonalInfo();

    }
}
