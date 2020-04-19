package Views;

import java.util.HashMap;

public class UserPage extends Page {
    public UserPage(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPages = new HashMap<String, Page>();
        subPages.put("view personal info" , this);
        subPages.put("view personal info" , this);
        subPages.put("view personal info" , this);
        subPages.put("products", this);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute() {
        //commands and back
    }
}
