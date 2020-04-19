package Views;

import java.util.HashMap;

public class ProductsPage extends Page {
    public ProductsPage(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPages = new HashMap<String, Page>();

    }
    private Page viewCategories(){
        return new Page("view categories" , this) {
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
    private Page filtering(){
        return new Page("filtering" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show available filters" , this);
                subPages.put("filter" , this);
                subPages.put("current filters" , this);
                subPages.put("disable filter" , this);

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
    private Page sorting(){
        return new Page("sorting" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show available sorts" , this);
                subPages.put("sort" , this);
                subPages.put("current sort" , this);
                subPages.put("disable sort" , this);

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
    private Page showProducts(){
        return new Page("show products" , this) {
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
