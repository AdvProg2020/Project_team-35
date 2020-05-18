package Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiscountCodeTest {

    LocalDateTime localDateTime = LocalDateTime.parse("2320-10-19T22:02:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2000-10-19T22:02:23");
    DiscountCode discountCode = new DiscountCode("code", localDateTime, localDateTime2, 1, 1, 1, new ArrayList<>(), 1);
    @Test
    public void getDetails() {

    }

    @Test
    public void expireDateToString() {
    }

    @Test
    public void startDateToString() {
    }

    @Test
    public void getDiscountCodeInlineInfo() {
    }

    @Test
    public void isThereDiscountCodeWithCode() {
        Assert.assertEquals(DiscountCode.isThereDiscountCodeWithCode("code"), true);
    }

    @Test
    public void getDiscountCodeWithCode() {
        Assert.assertEquals(DiscountCode.getDiscountCodeWithCode("code"), discountCode);
    }

    @Test
    public void getId() {
        Assert.assertEquals(discountCode.getId(), "code");
    }

    @Test
    public void getFinalDate() {
        Assert.assertEquals(discountCode.getFinalDate(), localDateTime);
    }

    @Test
    public void getDiscountPercent() {
        Assert.assertEquals(discountCode.getDiscountPercent(), 1.0, 0);
    }

    @Test
    public void getMaximumAvailableAmount() {
        Assert.assertEquals(discountCode.getMaximumAvailableAmount(), 1.0, 0);
    }

    @Test
    public void getAvailableUseFrequent() {
        Assert.assertEquals(1, discountCode.getAvailableUseFrequent());
    }

    @Test
    public void getAllDiscountCodes() {
    }

    @Test
    public void getCurrentSort() {
        Assert.assertEquals("Nothing", DiscountCode.getCurrentSort());
    }

    @Test
    public void setCurrentSort() {
    }

    @Test
    public void getMinimumTotalPriceForUse() {
        Assert.assertEquals(1.0, discountCode.getMinimumTotalPriceForUse(), 0);
    }
}
