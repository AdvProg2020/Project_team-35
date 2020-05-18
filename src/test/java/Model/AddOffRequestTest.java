package Model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddOffRequestTest {
    private Seller seller1 = new Seller("a","a","s","sd","ds","sad","asd");
    LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
    private  Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
    AddOffRequest request = new AddOffRequest(seller1, off);
    @Test
    public void getDetails() {
        Assert.assertEquals(request.getDetails() ,
                "Add Off Request : \nRequestId: " + request.getRequestId() + "\nStart Date: " + off.startDateToString()
                        + "\nFinal Date: " + off.expireDateToString() + "\nMaximum Amount: " + off.getMaximumAmountOfOff()
                        + "\nPercent: " + off.getOffPercent() + "\nRequester Username: " + request.getSeller().getUsername()
                        + "\nIncluded Product Ids: ");


    }

    @Test
    public void execute() {
    }

    @Test
    public void getRequestInfo() {
        Assert.assertEquals(request.getRequestInfo(), "  Add Off Request --- Requester Username: " + off.getSeller().getUsername() + " --- RQId: " + 1);
    }

    @Test
    public void decline() {

    }
}
