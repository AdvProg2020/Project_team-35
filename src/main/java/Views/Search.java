package Views;

import java.util.HashMap;

public class Search extends Page {
    public Search(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1",new RegisteringPanel("registering panel",this));

    }

    @Override
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute() {
        super.execute();
    }
}
