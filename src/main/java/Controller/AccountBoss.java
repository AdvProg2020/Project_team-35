package Controller;

import Model.Account;
import Model.Manager;

public class AccountBoss {
    public static void firstStepOfRegistering(String type , String username) throws MoreThanOneManagerException, RepeatedUserName {
        if (Manager.isThereAnyManager() && type.equals("manager")){
            throw new MoreThanOneManagerException("just one manager could register\nplease talk to manager for adding you");
        }
        else if (Account.isThereAccountWithUserName(username)){
            throw new RepeatedUserName("a user exists with this username");
        }
    }
    public static void startCreateAccount(String username, String password, String firstName, String lastName, String EMail, String phoneNumber, String type, String companyName) {

    }
    public static void startDeleteAccount(String username) {

    }
    public static void startLogin(String username, String password) {

    }
    public static void startEditPersonalField(String username, String fieldName, String newValue) {

    }

}
