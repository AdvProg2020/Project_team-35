package Views;

import Controller.AccountBoss;
import Controller.MoreThanOneManagerException;
import Controller.RepeatedUserName;

import java.util.HashMap;
import java.util.regex.Matcher;

import static Controller.AccountBoss.firstStepOfRegistering;
import static Controller.AccountBoss.makeAccount;

public class RegisteringPanel extends Page {
    private AccountBoss accountBoss;
    private static HashMap<String , String> allPersonalInfo = new HashMap<String, String>();
    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);

        subPages.put("Register" , this);
        subPages.put("login" , this);

    }
    private Page loginUser(){
        return new Page("Login" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
            //commands and back
            }
            // sout hello world :)

            @Override
            public void execute() {

                //commands and back executes
            }

            @Override
            public void show() {
                super.show();
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
                    System.out.println(s+":");
                    String command = scanner.nextLine();
                    allPersonalInfo.put(s,command);
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

                   }catch (MoreThanOneManagerException e){
                       System.out.println(e.getMessage());
                   }catch (RepeatedUserName e){
                       System.out.println(e.getMessage());
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

       }else if (command.equals("back")){
                nextPage = parentPage;
       }
       nextPage.execute();

    }


}
