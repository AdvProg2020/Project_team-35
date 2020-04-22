package Controller;

import Model.*;
import Views.MainPage;
import Views.RegisteringPanel;
import sun.applet.Main;

import java.util.HashMap;

public class AccountBoss {
    /**
     * this is for check just one manager should register and repeated usernames can't register.
     * @param type
     * @param username
     * @throws MoreThanOneManagerException
     * @throws RepeatedUserName
     */
    public static void firstStepOfRegistering(String type , String username) throws MoreThanOneManagerException, RepeatedUserName, RequestProblemNotExistManager {
        if (Manager.isThereAnyManager() && type.equals("manager")){
            throw new MoreThanOneManagerException("just one manager could register\nplease talk to manager for adding you");
        }
        if (!Manager.isThereAnyManager() && type.equals("seller")){
            throw new RequestProblemNotExistManager("you should wait because we don't have manager");
        }
        else if (Account.isThereAccountWithUserName(username)){
            throw new RepeatedUserName("a user exists with this username");
        }

    }
    /**
     * this method is for new account with different rules and extract data from hashmap allPersonalInfo.
     * @param allPersonalInfo
     */
    public static void makeAccount(HashMap<String , String> allPersonalInfo ) {
       StringBuilder request = new StringBuilder();
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
                request.append(s+":"+username+"\n");
            }
            if (s.equals("password")){
                password = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");

            }
            if (s.equals("name")){
                name = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");

            }
            if (s.equals("family")){
                family = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");

            }
            if (s.equals("email address")){
                email = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");

            }
            if (s.equals("phone number")){
                phone = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");

            }
            if (s.equals("company name")){
                company = allPersonalInfo.get(s);
                request.append(s+":"+username+"\n");
            }
        }
        if (type.equals("manager")) {
            Manager manager = new Manager(username,name,family,email,phone ,password);
        }
        if (type.equals("seller")) {
            SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(request,RequestTypes.SELLER_REGISTER);
          sellerRegisterRequest.execute();
        }
        if (type.equals("customer")) {
            Customer customer = new Customer(username ,name , family , email , phone , password);
        }



    }
    public void startDeleteAccount(String username) {

    }

    /**
     * this is for checking validity of username. we should have an account with this username.
     * in first part if we have two online users it has problem.
     * @param username
     */
    public static void checkUsernameExistenceInLogin(String username) throws ExistenceOfUserWithUsername, LoginWithoutLogout {
       if (Account.isIsThereOnlineUser()){
           throw new LoginWithoutLogout("first you should logout");
       }
        if (!Account.isThereAccountWithUserName(username)){
            throw new ExistenceOfUserWithUsername("we don't have a user with this username");
        }
    }

    /**
     * check is password correct or not.
     * @param username
     * @param password
     * @throws PasswordValidity
     */
    public static void checkPasswordValidity(  String username , String password) throws PasswordValidity {
        if (!Account.getAccountWithUsername(username).validatePassword(password)){
            throw new PasswordValidity("this password is invalid");
        }
    }

    /**
     * this set an account situation to login and add it to logged accounts list.
     * @param username
     * @param password
     */
    public static void startLogin(String username, String password) {
            Account account = Account.getAccountWithUsername(username);
            account.setThisAccountLogged(true);
            Account.getAllLoggedAccounts().add(account);
            Account.setOnlineAccount(account);
    }
    public static void startEditPersonalField(String username, String fieldName, String newValue) {

    }

}
