package Model;

import Controller.ManagerBoss;

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
    protected int typeOfAccount;
    private static String currentSort = "Nothing";
    /**
     * account constructor
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password , int typeOfAccount) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
        allAccounts.add(this);
        Account.setCurrentSort("Nothing");
    }

    public static boolean isIsThereOnlineUser() {
        return isThereOnlineUser;
    }

    public static boolean setIsThereOnlineUser(boolean isThereOnlineUser) {
        Account.isThereOnlineUser = isThereOnlineUser;
        return true;
    }

    /**
     * this is added i think we need it.
     * @return
     */
    public static Account getOnlineAccount() {
        return Account.onlineAccount;
    }

    /**
     * this is add for access of change online account.
     * @param onlineAccount
     */
    public static void setOnlineAccount(Account onlineAccount) {
        Account.onlineAccount = onlineAccount;
        if (onlineAccount!=null)
            setIsThereOnlineUser(true);
        else {
            setIsThereOnlineUser(false);
        }
    }

    public static ArrayList<Account> getAllLoggedAccounts() {
        return allLoggedAccounts;
    }

/*    public static void setAllLoggedAccounts(ArrayList<Account> allLoggedAccounts) {
        Account.allLoggedAccounts = allLoggedAccounts;
    }

 */
    public static int getTypeOfAccount(Account account) {
        return account.typeOfAccount;
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


    public void setThisAccountLogged(boolean thisAccountLogged) {
        isThisAccountLogged = thisAccountLogged;
    }

    /**
     * updated for login
     * @param userPassword
     * @return
     */
    public boolean validatePassword(String userPassword) {
        if (userPassword.equals(password))
            return true;
        return false;
    }
    public abstract String getPersonalInfo();
    public abstract String getShortInfo();


    public static int whatTypeIsOnline() {
        return Account.getTypeOfAccount(onlineAccount);
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isThereActiveAccountWithUserName(String username) {
        return ManagerBoss.getAllActiveUsers().contains(Account.getActiveAccountWithUserName(username));
    }
    public static Account getActiveAccountWithUserName(String username) {
        for (Account activeUser : ManagerBoss.getAllActiveUsers()) {
            if (activeUser.getUsername().equals(username)) {
                return activeUser;
            }
        }
        return null;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public String getIsConfirmedOrWaitForCheck() {
        if (this instanceof Manager) {
            if (Manager.getAllManagers().contains(this)) {
                return "Accepted";
            }
        }
        else if (this instanceof Customer) {
            if (Customer.getAllCustomers().contains(this)) {
                return "Accepted";
            }
        }
        else if (this instanceof Seller) {
            if (Seller.getAllSellers().contains(this)) {
                return "Accepted";
            }
        }
        return "Wait For Accept";
    }

    public String getTypeOfAccount() {
        if (this instanceof Manager) {
            return "Manager";
        }
        if (this instanceof Seller) {
            return "Seller";
        }
        if (this instanceof Customer) {
            return "Customer";
        }
        return null;
    }

    public String getFullName() {
        return this.getFirstName() + this.getLastName();
    }

    public static String getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(String currentSort) {
        Account.currentSort = currentSort;
    }
}
