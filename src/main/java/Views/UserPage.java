package Views;


import Controller.AccountBoss;
import Model.Account;

import java.util.HashMap;

public class UserPage extends Page {
    public UserPage(String name, Page parentPage) {
        super(name, parentPage);
       subPages.put("view personal info" , this);
       subPages.put("products", this);

    }
    private Page  viewPersonalInfo(){
        return new Page("view personal info" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("main page" , this);
                subPages.put("user account" , this);
            }

            @Override
            public void execute() {
                if (!Account.isIsThereOnlineUser()) {
                    System.err.println("you should login first");
                    RegisteringPanel registeringPanel = new RegisteringPanel("RegisteringPanel", this);
                    registeringPanel.execute();
                } else {
                    account = Account.getOnlineAccount();
                    System.out.println( AccountBoss.showPersonalInfoInUserPage(account));
                    setSubPages(subPages);
                    show();
                    Page nextPage = null;
                    String command = scanner.nextLine();
                    if (command.equalsIgnoreCase("user account")) {

                        if (Account.getTypeOfAccount(account) == 1) {
                            ManagerPage managerPage = new ManagerPage("manager", this);
                            nextPage = managerPage;
                        } else if (Account.getTypeOfAccount(account) == 2) {
                            BuyerPage buyerPage = new BuyerPage("customer", this);
                            nextPage = buyerPage;
                        } else if (Account.getTypeOfAccount(account) == 3) {
                            SellerPage sellerPage = new SellerPage("seller", this);
                            nextPage = sellerPage;
                        }
                    }
                    try {
                        nextPage.execute();
                    } catch (Exception e) {
                        if (command.equalsIgnoreCase("main page")) {
                            MainPage mainPage = new MainPage();
                            mainPage.execute();
                        } else {
                            System.err.println("invalid command");
                            this.execute();
                        }
                    }

                }
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
        String command  = scanner.nextLine();
        Page nextPage  = null;
        if (command.equals("view personal info") ){
           nextPage = viewPersonalInfo();
        }
        else if (command.equals("products")){

        }else if (command.equals("back")){
            nextPage = parentPage;
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
                this.execute();
        }
    }
}
