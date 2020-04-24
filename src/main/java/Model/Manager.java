package Model;

import java.util.ArrayList;

public class Manager extends Account {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    public static ArrayList<Request> newRequests;

    /**
     * a constructor for manager
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     */
    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password,1);
        newRequests = new ArrayList<Request>();
        allManagers.add(this);
    }

    /**
     * this method is updated
     * @return
     */
    public static boolean isThereAnyManager() {
        if (allManagers.size()>0)
            return true;
        return false;
    }
    @Override
    public String toString() {
        return null;
    }
    @Override
    public void deleteAccount() {

    }
    @Override
    public String getPersonalInfo() {
        String toReturn = "Type: Manager\n" +
                "Username: %s\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "PhoneNumber: %s\n";
        toReturn = String.format(toReturn, this.getUsername(), this.getFirstName() + this.getLastName() + this.getEmail() + this.getPhoneNumber());
        return toReturn;
    }
    @Override
    public void editPersonalField(String field) {

    }

}
