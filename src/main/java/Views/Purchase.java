package Views;

import java.util.HashMap;

public class Purchase extends Page{
    public Purchase(String name, Page parentPage) {
        super(name, parentPage);

    }
    private Page receiveInformation(){
        return new Page("receive information" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                // get info
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
    private Page discountCode(){
        return new Page("discount code" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                //get data
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

    private Page payment(){
        return new Page("payment" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                //get and handling
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
