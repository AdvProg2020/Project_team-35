package Views;

import Controller.AccountBoss;
import Controller.SellerBoss;
import Model.Account;
import Model.Seller;

import java.util.HashMap;

public class SellerPage extends Page {

    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view company information" , this);
        subPages.put("view sales history" , this);
        subPages.put("view credit" , this);
        subPages.put("show categories" , this);
        subPages.put("manage products" , this);

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
            public void execute() {

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
    private Page viewCredit(){
        return new Page("credit show" , this) {
            @Override
            public void execute() {
                System.out.println(SellerBoss.sellerCredit((Seller) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }
    private Page viewCategory(){
        return new Page("category view" , this) {
            @Override
            public void execute() {
                System.out.println("categories:");
                for (String category : SellerBoss.showCategories()) {
                    System.out.println(category);
                }
                // i have doubt in here
                parentPage.execute();
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
        else if (command.equalsIgnoreCase("view sales history")){
            nextPage = viewSalesHistory();
        }else if (command.equalsIgnoreCase("view credit")){
            nextPage = viewCredit();
        }else if (command.equalsIgnoreCase("show categories")){
                nextPage = viewCategory();
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }
}
