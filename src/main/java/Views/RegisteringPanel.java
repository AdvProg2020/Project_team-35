package Views;

import Controller.AccountBoss;
import Controller.ManagerMoreRegistering;
import Controller.UserNameRegisteringProblem;
import Model.Account;

import java.util.HashMap;
import java.util.regex.Matcher;

public class RegisteringPanel extends Page {
    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("login" , loginUser());
        subPages.put("create account" , registerUser());
    }

    private Page loginUser(){
        return new Page("Login" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
            //commands and back
            }
            // sout hello world :)

            @Override
            public void execute(String command) {
                changeStatusOfUser(user);

            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private Page registerUser(){
        return new Page("registering" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                //commands and back
            }

            @Override
            public void execute(String command) {
                Page nextPage = null;
                Matcher matcher = getMatcher(command,"create account(manager|customer|seller) (\\w+)");
                if (matcher.matches()){
                    System.out.println("salam");
                }

            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private void changeStatusOfUser(User user){
       // user.setUserLogin(true);
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute(String command) {
        show();
        Page nextPage  = null;
        command = getScanner().nextLine();
        for (String s : subPages.keySet()) {
            Matcher matcher = getMatcher(command,"create account (manager|seller|customer) (\\w+)");
            if (command.matches(String.valueOf(matcher))){
                        try {
                            AccountBoss.firstStepOfRegistering(matcher.group(1) , matcher.group(2));
                        }catch (UserNameRegisteringProblem | ManagerMoreRegistering e ){
                            this.execute(null);
                        }
            }
            if (command.matches("login (\\S+)")){

            }
        }
        if (command.equals("back")){
            nextPage = parentPage;
        }

        try {
            nextPage.execute(command);
        }catch (Exception e){
            this.execute(null);
        }
    }
    private Page registeringSecondLevel(){
        return new Page("registering confirm",this) {
            @Override
            public void execute(String command) {
                for (String s : subPages.keySet()) {
                    System.out.println(s);
                    command = getScanner().nextLine();

                }

            }

            /**
             * this method is for set personal data of user and save it.
             * @param subPages
             */
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("name" , this);
                subPages.put("family name" , this);
                subPages.put("email address",this);
                subPages.put("phone number" , this);
            }
        };
    }
}
