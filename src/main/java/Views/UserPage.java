package Views;


import Controller.AccountBoss;
import Controller.Exceptions.NotValidFieldException;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UserPage extends Page {
    private String fieldName;
    private String fieldChange;
    public UserPage(String name, Page parentPage) {
        super(name, parentPage);
       subPages.put("view personal info" , viewPersonalInfo());
       subPages.put("products", this);
       subPages.put("1",new RegisteringPanel("registering panel",this));
       subPages.put("user account", new Page("user account",this) {
           @Override
           public void execute() {
               super.execute();
           }
       });
        subPages.put("logout", new Page("logout",this) {
            @Override
            public void execute() {
                super.execute();
            }
        });

    }
    private Page  viewPersonalInfo(){
        return new Page("view personal info" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("main page", new Page("main page",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("edit personal info", new Page("edit personal info",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
            }

            @Override
            public void execute() {
                 {
                    account = Account.getOnlineAccount();
                    System.out.println( AccountBoss.showPersonalInfoInUserPage(account));
                    setSubPages(subPages);
                    show();
                    Page nextPage = null;
                    String command = scanner.nextLine();
                   if (command.equalsIgnoreCase("1")){
                       nextPage = editPersonalInfoGetFieldName();
                   }
                   else if (command.equals(String.valueOf(subPages.size()+1))){
                       nextPage = parentPage;
                   }
                    try {
                        nextPage.execute();
                    } catch (Exception e) {
                        if (command.equalsIgnoreCase("2")) {
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
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute() {
        if (!Account.isIsThereOnlineUser()) {
                System.err.println("you should login first");
                RegisteringPanel registeringPanel = new RegisteringPanel("registering panel" , this);
                registeringPanel.execute();
        } else {
            show();
            String command = scanner.nextLine();
            Page nextPage = null;
            if (command.equals("3")) {
                nextPage = viewPersonalInfo();
            } else if (command.equals("products")) {

            }else if (command.equalsIgnoreCase("1")){
                    if (Account.getOnlineAccount() instanceof Manager){
                        nextPage = new ManagerPage("manager page",this);
                    }else if (Account.getOnlineAccount() instanceof Customer){
                        nextPage = new BuyerPage("customer page" , this);
                    }else if (Account.getOnlineAccount() instanceof Seller){
                        nextPage = new SellerPage("seller page" , this);
                    }
            }else if (command.equalsIgnoreCase("2")){
                nextPage = new MainPage();
                AccountBoss.logout(Account.getOnlineAccount());
                System.out.println("logout successfully");
            }
            else if (command.equals(String.valueOf(subPages.size()+1))) {
                nextPage = parentPage;
            }
            try {
                nextPage.execute();
            } catch (Exception e) {
                System.err.println("invalid command");
                this.execute();
            }
        }
    }
    private Page editPersonalInfoGetChange(){
        return new Page("edit field",this) {
            @Override
            public void execute() {
                System.out.println("enter new data:");
                String command = scanner.nextLine();
                Page nextPage = null;
                if (checkFormatOfPersonalInformation(fieldName , command)){
                    fieldChange = command;
                    try {
                        AccountBoss.startEditPersonalField(fieldName , fieldChange);
                        System.out.println("successfully changed");
                        nextPage = viewPersonalInfo();
                    } catch (NotValidFieldException e) {
                        System.err.println(e.getMessage());
                        this.execute();
                    }
                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }
                else {
                    System.err.println("invalid format");
                    nextPage = this;
                }
                nextPage.execute();

            }
        };
    }
    private Page editPersonalInfoGetFieldName(){
        return new Page("edit" , this) {
            @Override
            public void execute() {
                System.out.println("enter your command:");
                String command = scanner.nextLine();
                String regex = "^edit (\\w+)$";
                Page nextPage = null;
                Matcher matcher = getMatcher(command,regex);
                matcher.matches();
                if (command.matches(regex)){
                    fieldName = matcher.group(1);
                    nextPage = editPersonalInfoGetChange();
                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }
                else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                try {
                    nextPage.execute();
                }catch (Exception e){
                    e.printStackTrace();
                    this.execute();
                }
            }
        };
    }
}
