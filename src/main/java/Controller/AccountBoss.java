package Controller;

import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;

import java.util.HashMap;

public class AccountBoss {
    /**
     * this is for check just one manager should register and repeated usernames can't register.
     * @param type
     * @param username
     * @throws MoreThanOneManagerException
     * @throws RepeatedUserName
     */
    public static void firstStepOfRegistering(String type , String username) throws MoreThanOneManagerException, RepeatedUserName {
        if (Manager.isThereAnyManager() && type.equals("manager")){
            throw new MoreThanOneManagerException("just one manager could register\nplease talk to manager for adding you");
        }
        else if (Account.isThereAccountWithUserName(username)){
            throw new RepeatedUserName("a user exists with this username");
        }

    }
    /**
     * this method is for new account with different rules and extract data from hashmap allPersonalInfo.
     * @param allPersonalInfo
     */
    public static void makeAccount(HashMap<String , String> allPersonalInfo ){
        String type = null;
        String email = null;
        String phone = null;
        String username = null;
        String name = null;
        String family = null;
        String company = null;
        String password = null;
        for (String s : allPersonalInfo.keySet()) {
            if (s.equals("type")){
                type = allPersonalInfo.get(s);
            }
            if (s.equals("username")){
                username = allPersonalInfo.get(s);
            }
            if (s.equals("password")){
                password = allPersonalInfo.get(s);
            }
            if (s.equals("name")){
                name = allPersonalInfo.get(s);
            }
            if (s.equals("family")){
                family = allPersonalInfo.get(s);
            }
            if (s.equals("email address")){
                email = allPersonalInfo.get(s);
            }
            if (s.equals("phone number")){
                phone = allPersonalInfo.get(s);
            }
            if (s.equals("company name")){
                company = allPersonalInfo.get(s);
            }
        }
        if (type.equals("manager")) {
            Manager manager = new Manager(username,name,family,email,phone ,password);
        }
        if (type.equals("seller")) {
            Seller seller = new Seller(username , name , family , email ,phone , password , company);
        }
        if (type.equals("customer")) {
            Customer customer = new Customer(username ,name , family , email , phone , password);
        }



    }
    public void startDeleteAccount(String username) {

    }
    public void startLogin(String username, String password) {

    }
    public static void startEditPersonalField(String username, String fieldName, String newValue) {

    }

}
