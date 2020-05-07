package Views;

import Model.Product;
import Model.Seller;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoodPageTest {
    private Product product = new Product("a","s",23,new Seller("a","s","ds","fd","sda","sd","sd"),7,null,null);
    @Test
    public void execute(){
        GoodPage goodPage = new GoodPage("good page",new MainPage(),product);
        goodPage.execute();
    }

}