package Views;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuyerPageTest {
    private BuyerPage buyerPage = new BuyerPage("buyer",new MainPage());
    @Test
    public void show() {
    }

    @Test
    public void execute() {
        buyerPage.execute();
    }
}