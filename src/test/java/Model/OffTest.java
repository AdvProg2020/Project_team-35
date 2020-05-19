package Model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Integer> result = new ArrayList<>();
        result.add(3);
        result.add(1);
        result.add(2);
        Assert.assertEquals(ids, result);
    }

    @Test
    public void testShowOff() {
        LocalDateTime localDateTime1 = LocalDateTime.parse("2000-10-19T22:02:23");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2020-10-19T22:02:23");
        ArrayList<Product> productsList = new ArrayList<>();
        Category category = new Category("labaniat",null);
        Product product = new Product("a", "mihan", 23, seller, 10, category, null, null);
        productsList.add(product);
        Off off2 = new Off(localDateTime2,localDateTime1,productsList,233,22,seller);
        Assert.assertEquals("id: "+4+"\n" + "start date: "+"2000-10-19T22:02:23"+"\nfinal date: "+"2020-10-19T22:02:23"+"\n"
            +"status: "+"FORMAKE"+"\n"+"percent: "+22.0+"\n"+"maximum: "+233.0+"\n"
            +"product id: " + 1 + "\n"+"product name: " + "a" + "\n"
            +"product inventory: " + 10 + "\n" , off2.showOff());
    }
}

