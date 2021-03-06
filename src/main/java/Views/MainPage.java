package Views;

import java.util.HashMap;
import java.util.Random;

public class MainPage extends Page {
    private User user;

    public MainPage() {
        super("Main Menu", null);
        HashMap<String , Page> subPages = new HashMap<String, Page>();
        this.subPages.put("register or login", new RegisteringPanel("Registering Panel",this));
        this.subPages.put("product",new ProductsPage("Product Page" , this));
        this.subPages.put("offs" , new Offs("Offs Page" , this));
        this.subPages.put("search",new Search("Search Page" , this));
        this.subPages.put("cart" , new CartPage("Cart Page",this));
        Random n = new Random();
        n.nextInt();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute() {
        super.execute();
    }

}
