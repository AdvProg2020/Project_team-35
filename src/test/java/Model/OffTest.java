package Model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class OffTest {

    @Test
    public void getSeller() {
    }

    @Test
    public void getOffId() {
    }

    @Test
    public void getOffById() {
    }

    @Test
    public void showOff() {
    }

    @Test
    public void getIncludedProducts() {
    }

    @Test
    public void setFinalDate() {
    }

    @Test
    public void setMaximumAmountOfOff() {
    }

    @Test
    public void setOffPercent() {
    }

    @Test
    public void setOffStatus() {
    }

    @Test
    public void getFinalDate() {
    }

    @Test
    public void getMaximumAmountOfOff() {
    }

    @Test
    public void getOffPercent() {
    }

    @Test
    public void getOffStatus() {
    }

    @Test
    public void getStartDate() {
    }

    @Test
    public void setStartDate() {
    }
private Seller seller = new Seller("a","a","s","sd","ds","sad","asd");
    @Test
    public void sorting() {
        LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime3 = LocalDateTime.parse("2002-10-19T22:02:23");

        Off off = new Off(localDateTime,localDateTime2,null,344,23,seller);
        Off off1 = new Off(localDateTime1,localDateTime3,null,450,12,seller);
        Off off2 = new Off(localDateTime,localDateTime2,null,233,22,seller);

        ArrayList<Off> a = (Off.sorting("max"));
        ArrayList<Integer > ids = new ArrayList<>();
        for (Off off3 : a) {
            ids.add(off3.getOffId());
        }
        Assert.assertEquals(ids,null);
    }
}