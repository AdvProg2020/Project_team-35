package Views;

import java.util.HashMap;

public class Search extends Page {
    public Search(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String  , String> filters = new HashMap<String, String>();
        subPages.put("register or login", new RegisteringPanel("Registering Panel", this));
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
