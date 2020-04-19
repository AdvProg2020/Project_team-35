package Views;

import Model.Account;

import java.util.ArrayList;
import java.util.Random;

public class User {
    private static ArrayList<User> allUsers;
    private String type;
    private Random id;
    private Account account;

    public User(Random id) {
        this.id = id;
        allUsers.add(this);
    }
    public static User findUserById(Random id){
        for (User user : allUsers) {
            if (user.id.equals(id))
                return user;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
