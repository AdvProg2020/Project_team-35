package Controller;

import Controller.Exceptions.*;
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



    public static void removeProductWithId(int productId) throws ThereISNotProductWithIdException {
        if (Product.isThereProductWithId(productId)) {
            Product toRemove = Product.getProductWithId(productId);
            Category category = toRemove.getCategory();
            category.getCategoryProducts().remove(toRemove);
            Seller seller = toRemove.getSeller();
            seller.getSalableProducts().remove(toRemove);
            Product.getAllProducts().remove(toRemove);
        }
        else {
            throw new ThereISNotProductWithIdException("There isn't product with requested id.");
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

    public static int startDeleteCategoryWithName(String categoryName) throws ThereIsNotCategoryWithNameException {
        if (Category.isThereCategoryWithName(categoryName)) {
            Category toDelete = Category.getCategoryByName(categoryName);
            Category.getAllCategories().remove(toDelete);
            deleteProductsOfCategoryAtProductClass(toDelete);
            deleteProductsOfCategoryAtSellerClass(toDelete);
            return 0;
        }
        else {
            throw new ThereIsNotCategoryWithNameException("There isn't any category with requested name.");
        }
    }

    private static void deleteProductsOfCategoryAtProductClass(Category toDelete) {
        int size = Product.getAllProducts().size();
        for(int i = 0; i < size; i++){
            Product product = Product.getAllProducts().get(i);
            if (product.getCategory().equals(toDelete)) {
                Product.getAllProducts().remove(product);
                i--;
                size--;
            }
        }
    }

    private static void deleteProductsOfCategoryAtSellerClass(Category toDelete) {
        int sellersNumber = Seller.getAllSellers().size();
        for (int sellerNumber = 0; sellerNumber < sellersNumber; sellerNumber++) {
            Seller seller = Seller.getAllSellers().get(sellerNumber);
            int sellerProductsNumber = seller.getSalableProducts().size();
            for (int sellerProductNumber = 0; sellerProductNumber < sellerProductsNumber; sellerProductNumber++) {
                Product product = seller.getSalableProducts().get(sellerProductNumber);
                if (product.getCategory().equals(toDelete)) {
                    seller.getSalableProducts().remove(product);
                    sellerProductNumber--;
                    sellerProductsNumber--;
                }
            }
        }
    }

    public static boolean checkNewManagerUserName(String username) throws RepeatedUserName {
        if (Account.isThereActiveAccountWithUserName(username)) {
            throw new RepeatedUserName("This username exist. Try another one.");
        }
        return true;
    }



}
