package Views;

import Model.User;

import java.util.HashMap;

public class RegisteringPanel extends Page {
    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);
        HashMap<String , Page> subPage = new HashMap<String, Page>();

    }
    private Page loginUser(){
        return new Page("Login" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
            //commands and back
            }

            @Override
            public void execute() {
                changeStatusOfUser(user);
                //commands and back executes
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
            public void execute() {
                //execute commands;
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private void changeStatusOfUser(User user){
        user.setUserLogin(true);
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
