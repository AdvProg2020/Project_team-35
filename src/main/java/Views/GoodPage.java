package Views;

import java.util.HashMap;

public class GoodPage extends Page {
    public GoodPage(String name, Page parentPage) {
        super(name, parentPage);
    }
    private Page digest(){
        return new Page("digest" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("add to cart" , this);
                subPages.put("select seller" , this);

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
    private Page attributes(){
        return new Page("attributes" ,  this) {
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
    private Page compare(){
        return new Page("compare" , this) {
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
    private Page comments(){
        return new Page("Comments" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("Add comment" , this);
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

    @Override
    public void setSubPages(HashMap<String, Page> subPages) {
        super.setSubPages(subPages);
    }
}
