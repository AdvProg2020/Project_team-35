package Views;

public class CartPage extends Page {
    public CartPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1",new RegisteringPanel("registering panel",this));
    }
}
