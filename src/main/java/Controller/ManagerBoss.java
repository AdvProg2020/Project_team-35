package Controller;

import Model.*;

import java.util.ArrayList;

public class ManagerBoss {
    public static void acceptRequestWithId(int requestId) throws NotValidRequestIdException {
        if (Manager.isThereNewRequestWithId(requestId)) {
            Request request = Manager.getNewRequestWithId(requestId);
            request.execute();
            Manager.newRequests.remove(request);
            Manager.checkedRequests.add(request);
        }
        else {
            throw new NotValidRequestIdException("requestId is not Valid or is Checked");
        }
    }
    public static void declineRequestWithId(int requestId) throws NotValidRequestIdException {
        if (Manager.isThereNewRequestWithId(requestId)) {
            Request request = Manager.getNewRequestWithId(requestId);
            Manager.newRequests.remove(request);
            Manager.checkedRequests.add(request);
        }
        else {
            throw new NotValidRequestIdException("requestId is not Valid or is Checked");
        }
    }
    public static String getDetailsOfRequestWithId(int requestId) throws NotValidRequestIdException {
        if (Manager.isThereNewRequestWithId(requestId) || Manager.isThereCheckedRequestWithId(requestId)) {
            if (Manager.isThereNewRequestWithId(requestId)) {
                return Manager.getNewRequestWithId(requestId).getDetails();
            }
            else {
                return Manager.getCheckedRequestWithId(requestId).getDetails();
            }
        }
        else {
            throw new NotValidRequestIdException("requestId is not valid");
        }
    }

    public static String  getDetailsOfAccountWithUserName(String username) throws NotValidUserNameException {
        if (Account.isThereActiveAccountWithUserName(username)) {
            return Account.getActiveAccountWithUserName(username).getPersonalInfo();
        }
        else {
            throw new NotValidUserNameException("This username is not valid or has'nt accepted.");
        }
    }

    public static ArrayList<Account> getAllActiveUsers() {
        ArrayList<Account> allUsers = new ArrayList<>();
        allUsers.addAll(Manager.getAllManagers());
        allUsers.addAll(Customer.getAllCustomers());
        allUsers.addAll(Seller.getAllSellers());
        return allUsers;
    }
}
