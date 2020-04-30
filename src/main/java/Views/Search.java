package Views;

import java.util.HashMap;

public class Search extends Page {
    public Search(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String  , String> filters = new HashMap<String, String>();

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
