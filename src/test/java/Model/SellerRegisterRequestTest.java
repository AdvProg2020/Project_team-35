package Model;


import org.junit.Assert;
import org.junit.Test;

public class SellerRegisterRequestTest {
    Seller seller = new Seller("username", "name", "lastName", "mail@e.ir", "09100577581", "09100577581", "company");
    SellerRegisterRequest request = new SellerRegisterRequest(seller);

    @Test
    public void getRequestInfo() {
        Assert.assertEquals(request.getRequestInfo(), "  Seller Register Request --- username: username --- RQId: 1");
    }

    @Test
    public void getDetails() {
        Assert.assertEquals(request.getDetails(), "Seller Register Request : \nRequestId: 1\n"+"Type: Seller\n" +
        "Username: username"+"\n" +
                "Name: name"+"\n" +
                "Last Name: lastName"+"\n"+
                "Email: mail@e.ir"+"\n" +
                "PhoneNumber: 09100577581"+"\n"+
                "Company: company"+"\n"+
                "password: 09100577581");
    }

    @Test
    public void testGetRequestInfo() {

    }

    @Test
    public void execute() {
        Assert.assertEquals(request.execute(), true);
    }
}
