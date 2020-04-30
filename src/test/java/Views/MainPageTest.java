package Views;

import junit.framework.TestCase;
import org.junit.Assert;

public class MainPageTest extends TestCase {
    public void testShow() {
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.show());
    }
}