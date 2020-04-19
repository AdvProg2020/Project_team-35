package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    public static ArrayList<Account> allAccounts;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private static Account onlineAccount;
    private static boolean isThereOnlineUser;
    public static int getTypeOfAccount(Account account) {
        return 0;
    }
    public static Account getAccountWithUsername(String username) {
        return null;
    }
    public static boolean isThereAccountWithUserName(String username) {
        return true;
    }
    public boolean validatePassword(String userPassword) {
        return true;
    }
    public abstract void deleteAccount();
    public abstract void getPersonalInfo();
    public abstract void editPersonalField(String field);
    public static int whatTypeIsOnline() {
        return 0;
    }

}
