package Views;

import java.util.HashMap;

public class SellerPage extends Page {
    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPages = new HashMap<String, Page>();

    }
    private Page viewPersonalInfo(){
        return new Page("view personal info" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("edit" , this);
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
    private Page viewCompanyInformation(){
        return new Page("view company information" , this) {
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
    private Page viewSalesHistory(){
        return new Page("view sales history" , this) {
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
    private Page manageProducts(){
        return new Page("manage products" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view",this);
                subPages.put("view buyers" , this);
                subPages.put("edit" , this);

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
    private Page addProduct(){
        return new Page("add product" , this) {
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
    private Page removeProduct(){
        return new Page("remove product" , this) {
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
    private Page showCategories(){
        return new Page("show categories" , this) {
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
    private Page viewOffs(){
        return new Page("view offs" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view",this);
                subPages.put("edit",this);
                subPages.put("add off",this);

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
