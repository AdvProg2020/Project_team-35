package Controller;

import Controller.Exceptions.CantRemoveYourAccountException;
import Controller.Exceptions.NotValidRequestIdException;
import Controller.Exceptions.NotValidUserNameException;
import Controller.Exceptions.RepeatedCategoryNameException;
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

    public static int deleteAccountWithUsername(String username) throws NotValidUserNameException, CantRemoveYourAccountException {
        if (Account.isThereActiveAccountWithUserName(username)) {
            Account toRemove = Account.getActiveAccountWithUserName(username);
            if (Account.getOnlineAccount().equals(toRemove)) {
                throw new CantRemoveYourAccountException("You can not remove your account !");
            }
            else {
                Account.getAllAccounts().remove(toRemove);
                if (toRemove instanceof Manager) {
                    Manager.getAllManagers().remove(toRemove);
                }
                else if (toRemove instanceof Seller) {
                    Seller.getAllSellers().remove(toRemove);
                }
                else if (toRemove instanceof Customer) {
                    Customer.getAllCustomers().remove(toRemove);
                }
                return 0;
            }
        }
        else {
            throw new NotValidUserNameException("This username does'nt exist or has'nt accepted already.");
        }
    }

    public static int addNewCategory (String categoryName, ArrayList<String> specialAttributes) throws RepeatedCategoryNameException {
        if (Category.isThereCategoryWithName(categoryName)) {
            throw new RepeatedCategoryNameException("This name is repeated. Use another.");
        }
        else {
            Category category = new Category(categoryName, specialAttributes);
            Category.allCategories.add(category);
            return 0;
        }
    }
}
