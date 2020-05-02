package Views;

import java.util.HashMap;
import java.util.Random;

public class MainPage extends Page {
    private User user;

    public MainPage() {
        super("Main Menu", null);
        HashMap<String , Page> subPages = new HashMap<String, Page>();
        this.subPages.put("4", new RegisteringPanel("Registering Panel",this));
        this.subPages.put("5",new ProductsPage("Product Page" , this));
        this.subPages.put("1" , new Offs("Offs Page" , this));
        this.subPages.put("2",new Search("Search Page" , this));
        this.subPages.put("6" , new CartPage("Cart Page",this));
        this.subPages.put("3" , new UserPage("UserPage",this));
        Random n = new Random();
        n.nextInt();
    }

    @Override
    public boolean show() {
       return super.show();
    }

    @Override
    public void execute() {
        super.execute();
    }

}
