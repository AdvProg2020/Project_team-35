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
     * @param typeOfAccount
     */
    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber, String password, int typeOfAccount) {
        super(username, firstName, lastName, email, phoneNumber, password, typeOfAccount);
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
    public boolean isThereSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public Supporter getSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return supporter;
            }
        }
        return null;
    }
}
