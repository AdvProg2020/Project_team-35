package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.util.HashMap;

public class AccountBoss {
    /**
     * this is for checking that just one manager should register and repeated usernames can't register.
     *
     * @param type
     * @param username
     * @throws MoreThanOneManagerException
     * @throws RepeatedUserName
     */
    public static void firstStepOfRegistering(String type, String username) throws MoreThanOneManagerException, RepeatedUserName, RequestProblemNotExistManager {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (Manager.isThereAnyManager() && type.equals("manager")) {
            throw new MoreThanOneManagerException("only one manager could register\nplease send a request to manager for adding you");
        }
        if (!Manager.isThereAnyManager() && type.equals("seller")) {
            throw new RequestProblemNotExistManager("you should wait because there isn't any manager yet.");
        } else if (Account.isThereAccountWithUserName(username)) {
            throw new RepeatedUserName("this username already exists.");
        }

    }

    /**
     * this method is for new account with different rules and extract data from hashmap allPersonalInfo.
     *
     * @param allPersonalInfo
     */
    public static void makeAccount(HashMap<String, String> allPersonalInfo) {
        Boss.removeExpiredOffsAndDiscountCodes();
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
            if (s.equals("type")) {
                type = allPersonalInfo.get(s);
            } else if (s.equals("username")) {
                username = allPersonalInfo.get(s);

            } else if (s.equals("password")) {
                password = allPersonalInfo.get(s);


            } else if (s.equals("name")) {
                name = allPersonalInfo.get(s);

            } else if (s.equals("family")) {
                family = allPersonalInfo.get(s);


            } else if (s.equals("email address")) {
                email = allPersonalInfo.get(s);


            } else if (s.equals("phone number")) {
                phone = allPersonalInfo.get(s);


            } else if (s.equals("company name")) {
                company = allPersonalInfo.get(s);

            }
        }
        if (type.equalsIgnoreCase("manager")) {
            Manager manager = new Manager(username, name, family, email, phone, password);
        }
        if (type.equals("seller")) {
            Seller seller = new Seller(username, name, family, email, phone, password, company);
            SellerRegisterRequest sellerRegisterRequest = new SellerRegisterRequest(seller);

        }
        if (type.equals("customer")) {
            Customer customer = new Customer(username, name, family, email, phone, password);
        }


    }

    public static String showPersonalInfoInUserPage(Account account) {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (Account.getTypeOfAccount(account) == 1) {
            Manager manager = (Manager) account;
            return manager.getPersonalInfo();
        } else if (Account.getTypeOfAccount(account) == 2) {
            Customer customer = (Customer) account;
            return customer.getPersonalInfo();
        } else if (Account.getTypeOfAccount(account) == 3) {
            Seller seller = (Seller) account;
            return seller.getPersonalInfo();
        }
        return null;
    }

    public static String showCompanyInfo(Seller seller) {
        return seller.getCompanyName();
    }

    /**
     * this is for checking validity of username. we should have an account with this username.
     * in first part if we have two online users it has problem.
     *
     * @param username
     */
    public static void  checkLoginWithLogOut(String username) throws LoginWithoutLogout {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (Account.isIsThereOnlineUser()) {
            throw new LoginWithoutLogout("first you should logout", 1);
        }
    }
    public static boolean checkUsernameExistenceInLogin(String username) throws ExistenceOfUserWithUsername, LoginWithoutLogout {
        Boss.removeExpiredOffsAndDiscountCodes();

        if (Account.getAccountWithUsername(username).isThisAccountLogged()){
            throw new LoginWithoutLogout("first logout",12);
        }
        if (!Account.isThereAccountWithUserName(username)) {
            throw new ExistenceOfUserWithUsername("this username doesn't exist.", 2);
        }
        if (Account.getAccountWithUsername(username) instanceof Seller && !Seller.getAllSellers().contains((Seller) Account.getAccountWithUsername(username))) {
            throw new ExistenceOfUserWithUsername("this username doesn't exist", 2);
        }
        return true;
    }

    /**
     * check is password correct or not.
     *
     * @param username
     * @param password
     * @throws PasswordValidity
     */
    public static boolean checkPasswordValidity(String username, String password) throws PasswordValidity {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (!Account.getAccountWithUsername(username).validatePassword(password)) {
            throw new PasswordValidity("this password is invalid", 1);
        }
        return true;
    }

    /**
     * this set an account situation to login and add it to logged accounts list.
     *
     * @param username
     * @param password
     */
    public static boolean startLogin(String username, String password) {
        Boss.removeExpiredOffsAndDiscountCodes();
        Account account = Account.getAccountWithUsername(username);
        account.setThisAccountLogged(true);
        Account.getAllLoggedAccounts().add(account);
        Account.setIsThereOnlineUser(true);
        Account.setOnlineAccount(account);
        return true;
    }

    public static void startEditPersonalField(String fieldName, String newValue, Account account) throws NotValidFieldException, InvalidNumber {
        Boss.removeExpiredOffsAndDiscountCodes();
        if (fieldName.equalsIgnoreCase("firstName")) {
            Account.getOnlineAccount().setFirstName(newValue);
        } else if (fieldName.equalsIgnoreCase("lastName")) {
            Account.getOnlineAccount().setLastName(newValue);
        } else if (fieldName.equalsIgnoreCase("email")) {
            Account.getOnlineAccount().setEmail(newValue);
        } else if (fieldName.equalsIgnoreCase("phoneNumber")) {
            Account.getOnlineAccount().setPhoneNumber(newValue);
        } else if (fieldName.equalsIgnoreCase("password")) {
            Account.getOnlineAccount().setPassword(newValue);
        } else if (fieldName.equalsIgnoreCase("money")) {
            if (account instanceof Customer) {
                Customer customer = (Customer) account;
                if (newValue.matches("^\\d+.?\\d+$")) {
                    customer.setMoney(Double.parseDouble(newValue));
                } else {
                    throw new InvalidNumber("invalid format for money", 4);
                }
            } else {
                throw new NotValidFieldException("money is valid just for customer");
            }
        } else if (fieldName.equalsIgnoreCase("companyName")) {
            if (Account.getOnlineAccount() instanceof Seller) {
                ((Seller) Account.getOnlineAccount()).setCompanyName(newValue);
            } else
                throw new NotValidFieldException("valid : username.lastName.email.password.phoneNumber.company(for sellers)\n");
        } else {
            throw new NotValidFieldException("valid : username.lastName.email.password.phoneNumber.company(for sellers)\n");
        }

    }

    public static boolean logout(Account account) {
        Boss.removeExpiredOffsAndDiscountCodes();
          Account.setIsThereOnlineUser(false);
        Account.setOnlineAccount(null);
        account.setThisAccountLogged(false);
        Account.getAllLoggedAccounts().remove(account);
        return true;
    }
}
