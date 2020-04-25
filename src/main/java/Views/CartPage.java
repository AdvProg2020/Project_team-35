package Views;

public class CartPage extends Page {
    public CartPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("register or login", new RegisteringPanel("Registering Panel", this));
    }
}
