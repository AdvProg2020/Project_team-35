package Views;

import Controller.AccountBoss;
import Controller.FieldDoesNotExist;
import Controller.NotValidFieldException;
import Controller.UserNameChange;
import Model.Account;
import Model.Seller;

import java.util.HashMap;
import java.util.regex.Matcher;

public class SellerPage extends Page {
    private static String fieldName;
    private static String fieldChange;
    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view company information" , this);
        subPages.put("edit",this);

    }



    private Page viewCompanyInformation(){
        return new Page("view company information" , this) {
            @Override
            public void execute() {
                System.out.println("company information :");
                System.out.println( AccountBoss.showCompanyInfo((Seller) Account.getOnlineAccount()));
                parentPage.execute();
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
        show();
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("view company information")){
            nextPage = viewCompanyInformation();
        }
        else if (command.equalsIgnoreCase("edit personal info")){
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }
}
