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
        return super.toString();
    }
    @Override
    public void deleteAccount() {

    }

    /**
     * this format is true
     * @return
     */
    @Override
    public String getPersonalInfo() {
        String toReturn = "Type: Manager\n" +
                "Username: "+getUsername()+"\n" +
                "Name: "+getFirstName()+"\n" +
                "Email: "+getEmail()+"\n" +
                "PhoneNumber: "+getPhoneNumber()+"\n";
        return toReturn;
    }


}
