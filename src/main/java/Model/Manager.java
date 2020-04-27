package Model;

import java.util.ArrayList;

public class Manager extends Account {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    public static ArrayList<Request> newRequests;
    public static ArrayList<Request> checkedRequests;

    /**
     * a constructor for manager
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 1);
        newRequests = new ArrayList<Request>();
        checkedRequests = new ArrayList<>();
        allManagers.add(this);
    }

    public static ArrayList<Request> getNewRequests() {
        return newRequests;
    }

    public static ArrayList<Request> getCheckedRequests() {
        return checkedRequests;
    }

    /**
     * this method is updated
     *
     * @return
     */


    public static boolean isThereAnyManager() {
        return allManagers.size() > 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void deleteAccount() {

    }

    /**
     * this format is true
     *
     * @return
     */
    @Override
    public String getPersonalInfo() {
        return "Type: Manager\n" +
                "Username: " + getUsername() + "\n" +
                "Name: " + getFirstName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "PhoneNumber: " + getPhoneNumber() + "\n";
    }
}
