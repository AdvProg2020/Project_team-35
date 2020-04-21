package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    public static ArrayList<Account> allAccounts = new ArrayList<Account>();
    private static ArrayList<Account> allLoggedAccounts = new ArrayList<>();
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private static Account onlineAccount;
    private static boolean isThereOnlineUser;
    protected boolean isThisAccountLogged;
    /**
     * account constructor
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        allAccounts.add(this);
    }


    public static ArrayList<Account> getAllLoggedAccounts() {
        return allLoggedAccounts;
    }

    public static void setAllLoggedAccounts(ArrayList<Account> allLoggedAccounts) {
        Account.allLoggedAccounts = allLoggedAccounts;
    }

    public static int getTypeOfAccount(Account account) {
        return 0;
    }

    /**
     * this method is updated
     * @param username
     * @return
     */
    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username))
                return account;
        }
        return null;
    }

    /**
     * this method check the allAccounts list and identify that we have an account with that username or not.
     * @param username
     * @return
     */
    public static boolean isThereAccountWithUserName(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username))
                return true;
        }
        return false;
    }

    public boolean isThisAccountLogged() {
        return isThisAccountLogged;
    }

    public void setThisAccountLogged(boolean thisAccountLogged) {
        isThisAccountLogged = thisAccountLogged;
    }

    public boolean validatePassword(String userPassword) {
        return true;
    }
    public abstract void deleteAccount();
    public abstract String getPersonalInfo();
    public abstract void editPersonalField(String field);
    public static int whatTypeIsOnline() {
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
