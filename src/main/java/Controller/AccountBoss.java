package Controller;

import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;

public class AccountBoss {
    private static int numberOfManagerRegistering;
    /**
     * this method check just one manager register and no more and check validity of types and usernames.
     * @param type
     * @param userName
     */
    public static void firstStepOfRegistering(String type , String userName) throws ManagerMoreRegistering, UserNameRegisteringProblem {
        if (numberOfManagerRegistering!=0 && type.equalsIgnoreCase("manager")){
            throw new ManagerMoreRegistering("just one manager could register");
        }
        if (Account.isThereAccountWithUserName(userName)){
            throw new UserNameRegisteringProblem("user with this username exists");
        }

    }

    /**
     * this method just new account.
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param EMail
     * @param phoneNumber
     * @param type
     * @param companyName
     */
    public void startCreateAccount(String username, String password, String firstName, String lastName, String EMail, String phoneNumber, String type, String companyName) {
        if (type.equals("manager")){
            Manager manager = new Manager();
        }
        else if (type.equals("customer")){
            Customer customer = new Customer();
        }
        else if (type.equals("seller")){
            Seller seller = new Seller();
        }
    }
    public void startDeleteAccount(String username) {

    }
    public void startLogin(String username, String password) {

    }
    public void startEditPersonalField(String username, String fieldName, String newValue) {

    }

}
