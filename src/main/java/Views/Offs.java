package Views;

import java.util.HashMap;

public class Offs extends Page{
    public Offs(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPages = new HashMap<String, Page>();

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
