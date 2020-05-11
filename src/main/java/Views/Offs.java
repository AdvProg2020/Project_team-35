package Views;

import java.util.HashMap;

public class Offs extends Page{
    public Offs(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1",new RegisteringPanel("registering  panel",this));

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
            public boolean show() {
                return super.show();
            }
        };
    }

    @Override
    public boolean show() {
      return   super.show();
    }

    @Override
    public void execute() {
        super.execute();
    }
}
