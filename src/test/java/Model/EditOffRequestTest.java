package Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EditOffRequestTest {

    @Test
    public void getDetails() {
        Seller seller1 = new Seller("a","a","s","a","1","1","1");
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
        EditOffRequest editOffRequest = new EditOffRequest(seller1, off, 1, 1, localDateTime, localDateTime2);
        Assert.assertEquals("Edit Off Request : \nRequestId: " + 1 + "\nCurrentStartDate: " + "2000-10-19"
                +" --- NewStartDate: " + "2320-10-19"
                + "\nFinal Date: " + "2320-10-19"
                + " --- NewFinalDate: " + "2000-10-19" + "\nMaximum Amount: " + 344.0
                +" --- NewMaximumAmount: " + 1.0
                + "\nPercent: " + 23.0 + " --- NewPercent: " + 1.0
                + "\nRequester Username: " + "a"
                + "\nIncluded Product Ids: " , editOffRequest.getDetails());

    }

    @Test
    public void getRequestInfo() {
        Seller seller1 = new Seller("a","a","s","a","1","1","1");
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
        EditOffRequest editOffRequest = new EditOffRequest(seller1, off, 1, 1, localDateTime, localDateTime2);
        Assert.assertEquals(editOffRequest.getRequestInfo(), "  Edit Off Request --- UserName: " + "a" + " --- RQId: " + 1);

    }

    @Test
    public void expireDateToString() {
        Seller seller1 = new Seller("a","a","s","a","1","1","1");
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
        EditOffRequest editOffRequest = new EditOffRequest(seller1, off, 1, 1, localDateTime, localDateTime2);
        Assert.assertEquals(editOffRequest.expireDateToString(), "2000-10-19");
        Assert.assertEquals(editOffRequest.startDateToString(), "2320-10-19");
    }

    @Test
    public void execute() {
        Seller seller1 = new Seller("a","a","s","a","1","1","1");
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        Off off = new Off(localDateTime,localDateTime2,new ArrayList<>(),344,23,seller1);
        EditOffRequest editOffRequest = new EditOffRequest(seller1, off, 1, 1, localDateTime, localDateTime2);
        Assert.assertEquals(editOffRequest.execute(), true);
    }
}
