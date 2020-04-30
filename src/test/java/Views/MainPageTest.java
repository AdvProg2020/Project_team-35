package Views;

import junit.framework.TestCase;
import org.junit.Assert;
import sun.rmi.rmic.Main;

public class MainPageTest extends TestCase {
    public void testShow() {
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.show());
    }
}