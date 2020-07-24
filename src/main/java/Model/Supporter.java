package Model;

import Model.Account;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Supporter extends Account implements Serializable {
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    /**
     * account constructor
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 5);
        allSupporters.add(this);
    }

    @Override
    public String getPersonalInfo() {
        return null;
    }

    @Override
    public String getShortInfo() {
        return null;
    }
    public static boolean isThereSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public static Supporter getSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return supporter;
            }
        }
        return null;
    }

    public static ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }
}
