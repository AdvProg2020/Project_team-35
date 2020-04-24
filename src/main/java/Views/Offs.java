package Views;

import java.util.HashMap;

public class Offs extends Page{
    public Offs(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("register or login", new RegisteringPanel("Registering Panel", this));
    }
    private Page showProduct(){
        return new Page("show product" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                super.execute();
            }
            @Override
            public void show() {
                super.show();
            }
        };
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
