package Views;

import Controller.*;
import Model.Account;

import java.util.HashMap;
import java.util.regex.Matcher;

import static Controller.AccountBoss.firstStepOfRegistering;
import static Controller.AccountBoss.makeAccount;

public class RegisteringPanel extends Page {
    private AccountBoss accountBoss;
    private static HashMap<String , String> usernameAndPassword = new HashMap<>();
    private static HashMap<String , String> allPersonalInfo = new HashMap<String, String>();
    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);

        subPages.put("Register" , this);
        subPages.put("login" , this);

    }
    private Page login(){
        return new Page("login" , this) {
            @Override
            public void execute() {
                AccountBoss.startLogin(usernameAndPassword.get("username") , usernameAndPassword.get("password"));
                System.out.println("login successfully");
                account = Account.getAccountWithUsername(usernameAndPassword.get("username"));
                MainPage mainPage = new MainPage();
                mainPage.execute();

            }
        };
    }
    private Page loginGetPassword(){
        return new Page("get password for login" , this) {
            @Override
            public void execute() {
                System.out.println("password:");
                String command = scanner.nextLine();
                String regex = "(\\S+)";
                Matcher matcher = getMatcher(command , regex);
                matcher.matches();
                if (command.matches(regex)){
                    try {
                        String username = usernameAndPassword.get("username");
                        AccountBoss.checkPasswordValidity(username , matcher.group(1));
                        usernameAndPassword.put("password" , matcher.group(1));
                        login().execute();
                    }catch (PasswordValidity e){
                        System.out.println(e.getMessage());
                        this.execute();
                    }
                }
                else {
                    System.err.println("invalid command");
                    this.execute();
                }
            }
        };
    }
    private Page loginGetUsername(){
        return new Page("Login" , this) {


            @Override
            public void execute() {
                System.out.println("username:");
                String command = scanner.nextLine();
                String regex = "login (\\w+)";
                Matcher matcher = getMatcher(command,regex);
                matcher.matches();
                if (command.matches(regex)){
                    try {
                        AccountBoss.checkUsernameExistenceInLogin(matcher.group(1));
                        usernameAndPassword.put("username" , matcher.group(1));
                        loginGetPassword().execute();
                    }catch (ExistenceOfUserWithUsername e){
                        System.out.println(e.getMessage());
                        this.execute();
                    }
                }
                else {
                    System.err.println("invalid command");
                    this.execute();
                }
            }

        };
    }

    /**
     * for getting all personal info except username and make account and back to main page.
     * @return
     */
   private Page registerSecondPage(){
        return new Page("second page Of registering" , this) {
            @Override
            public void execute() {
                setSubPages(subPages);
                for (String s : subPages.keySet()) {
                    while (true) {

                        System.out.println(s + ":");
                        String command = scanner.nextLine();
                        if (checkFormatOfPersonalInformation(s , command)) {
                            allPersonalInfo.put(s, command);
                            break;
                        }
                        else {
                            System.err.println("invalid format of data");
                            continue;
                        }
                    }
                }
                System.out.println("successfully registered");
                makeAccount(allPersonalInfo);
                MainPage mainPage = new MainPage();
                mainPage.execute();

            }

            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("password",this);
                subPages.put("name",this);
                subPages.put("family" , this);
                subPages.put("email address",this);
                subPages.put("phone number",this);
                if (allPersonalInfo.values().contains("seller")){
                    subPages.put("company name" , this);
                }

            }
        };
   }

    /**
     * this is first step of register and get username and type and check validity.
     * @return
     */
    private Page RegisterUser(){
        return new Page("Register" , this) {


            @Override
            public void execute() {
                Page nextPage = null;
               String command = scanner.nextLine();
               String regex  = "create account (manager|customer|seller) (\\w+)";
                Matcher matcher = getMatcher(command , "create account (manager|customer|seller) (\\w+)");
                if (matcher.matches()){

                }
               if (command.matches(regex)){
                   try {
                       firstStepOfRegistering(matcher.group(1),matcher.group(2));
                       allPersonalInfo.put("type",matcher.group(1));
                       allPersonalInfo.put("username" , matcher.group(2));
                       registerSecondPage().execute();

                   }catch (MoreThanOneManagerException | RepeatedUserName e){
                       System.out.println(e.getMessage());
                       this.execute();
                   }

               }
               else {
                   System.err.println("invalid command");
                   this.execute();
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
       System.out.println(this.name);
        show();
        Page nextPage = null;
        String command = scanner.nextLine();
       if (command.equals("Register")){
            nextPage = RegisterUser();
       }
       else if (command.equals("login")){
           nextPage = loginGetUsername();

       }else if (command.equals("back")){
                nextPage = parentPage;
       }
       nextPage.execute();

    }

    /**
     * this method check email address and phone number be in a true form.
     * @param type
     * @param input
     * @return
     */
    private static boolean checkFormatOfPersonalInformation(String type , String input){

        if (type.equals("email address")){
            Matcher matcher = getMatcher(input,"(\\w+)@(\\w+)");
            if (matcher.matches()){
                return true;
            }
            return false;
        }
        else if (type.equals("phone number")){
            Matcher matcher = getMatcher(input , "\\d+");
            if (matcher.matches()){
                return true;
            }
            return false;
        }
        return true;
}

}
