package Views;

import java.util.HashMap;
import java.util.Random;

public class MainPage extends Page {
    private User user;

    public MainPage() {
        super("Main Menu", null);
        HashMap<String , Page> subPages = new HashMap<String, Page>();
        subPages.put("1", new RegisteringPanel("Registering Panel",this));
        subPages.put("product",new ProductsPage("Product Page" , this));
        subPages.put("offs" , new Offs("Offs Page" , this));
        Random n = new Random();
        n.nextInt();
        user = new User(n);
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
