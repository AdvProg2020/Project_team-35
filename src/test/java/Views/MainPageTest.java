package Views;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class MainPageTest  {
    @Test
    public void testShow() {
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.show());
    }
}