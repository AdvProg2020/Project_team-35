package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Manager extends Account implements Serializable {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    public static ArrayList<Request> newRequests = new ArrayList<>();
    public static ArrayList<Request> checkedRequests = new ArrayList<>();
    private static double minimumMoneyInPocket;

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


    public static Request getNewRequestWithId(int id) {
        for (Request newRequest : newRequests) {
            if (newRequest.getRequestId() == id) {
                return newRequest;
            }
        }
        return null;
    }
    public static Request getCheckedRequestWithId(int id) {
        for (Request checkedRequest : checkedRequests) {
            if (checkedRequest.getRequestId() == id) {
                return checkedRequest;
            }
        }
        return null;
    }
    public static boolean isThereNewRequestWithId(int requestId) {
        return getNewRequestWithId(requestId) != null;
    }
    public static boolean isThereCheckedRequestWithId(int requestId) {
        return getCheckedRequestWithId(requestId) != null;
    }
    /**
     * this format is true
     *
     *
     */
    @Override
    public String getPersonalInfo() {
        return "Type: Manager\n" +
                "Username: " + getUsername() + "\n" +
                "Name: " + getFirstName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "PhoneNumber: " + getPhoneNumber();
    }
    @Override
    public String getShortInfo() {
        return "UserName : " + this.getUsername() + "  --  " + "Type : Manager" + " -- Condition: " + getIsConfirmedOrWaitForCheck();
    }

    public static ArrayList<Manager> getAllManagers() {
        return allManagers;
    }

    public static double getMinimumMoneyInPocket() {
        return minimumMoneyInPocket;
    }

    public static void setMinimumMoneyInPocket(double minimumMoneyInPocket) {
        Manager.minimumMoneyInPocket = minimumMoneyInPocket;
    }
}
