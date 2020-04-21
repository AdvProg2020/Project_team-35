package Controller;

import Model.*;

import java.util.HashMap;

public class AccountBoss {
    /**
     * this is for checking that just one manager should register and repeated usernames can't register.
     * @param type
     * @param username
     * @throws MoreThanOneManagerException
     * @throws RepeatedUserName
     */
    public static void firstStepOfRegistering(String type, String username) throws MoreThanOneManagerException, RepeatedUserName, RequestProblemNotExistManager {
        if (Manager.isThereAnyManager() && type.equals("manager")){
            throw new MoreThanOneManagerException("only one manager could register\nplease send a request to manager for adding you");
        }
        if (!Manager.isThereAnyManager() && type.equals("seller")){
            throw new RequestProblemNotExistManager("you should wait because there isn't any manager yet.");
        }
        else if (Account.isThereAccountWithUserName(username)){
            throw new RepeatedUserName("this username already exists.");
        }

    }
    /**
     * this method is for new account with different rules and extract data from hashmap allPersonalInfo.
     * @param allPersonalInfo
     */
    public static void makeAccount(HashMap<String, String> allPersonalInfo ) {
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
            else if (s.equals("username")){
                username = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("password")){
                password = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("name")){
                name = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("family")){
                family = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("email address")){
                email = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("phone number")){
                phone = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");

            }
            else if (s.equals("company name")){
                company = allPersonalInfo.get(s);
                request.append(s + ":" + username + "\n");
            }
        }
        if (type.equals("manager")) {
            Manager manager = new Manager(username, name, family, email, phone, password);
        }
        if (type.equals("seller")) {
            Seller requester = new Seller(username, name, family, email, phone, password, company);
            SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(request, RequestTypes.SELLER_REGISTER, requester);
            Manager.newRequests.add(sellerRegisterRequest);
        }
        if (type.equals("customer")) {
            Customer customer = new Customer(username, name, family, email, phone, password);
        }



    }
    public void startDeleteAccount(String username) {

    }

    /**
     * this is for checking the validity of username. there should be an account with this username.
     * @param username
     */
    public static void checkUsernameExistenceInLogin(String username) throws ExistenceOfUserWithUsername {
        if (!Account.isThereAccountWithUserName(username)){
            throw new ExistenceOfUserWithUsername("this username doesn't exist.");
        }
    }

    /**
     * check is password correct or not.
     * @param username
     * @param password
     * @throws PasswordValidity
     */
    public static void checkPasswordValidity(  String username, String password) throws PasswordValidity {
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
