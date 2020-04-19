package Views;

import java.util.HashMap;

public class ManagerPage extends Page {
    public ManagerPage(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subCommands = new HashMap<String, Page>();

    }
    private Page manageUsers(){
        return new Page("manage users",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view" , this);
                subPages.put("change type" , this);
                subPages.put("delete user" , this);
                subPages.put("create manager profile" , this);
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
    private Page manageAllProducts(){
        return new Page("manage all products" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("remove" , this);
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
    private Page createDiscountCode(){
        return new Page("create discount code" , this) {
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
        private Page viewDiscountCodes(){
        return new Page("view discount codes" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view discount codes", this);
                subPages.put("edit discount code" , this);
                subPages.put("remove discount code" , this);
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
        private Page manageRequests(){
        return new Page("manage requests",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("details" , this);
                subPages.put("accept",this);
                subPages.put("decline" , this);
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
        private Page manageCategories(){
        return new Page("manage categories",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("edit",this);
                subPages.put("add",this);
                subPages.put("remove" , this);

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
    private Page newEdit(){
        return new Page("edit manager data" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                //commands and back
            }

            @Override
            public void execute() {
                //commands and back
            }

            @Override
            public void show() {
//commands and back
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
