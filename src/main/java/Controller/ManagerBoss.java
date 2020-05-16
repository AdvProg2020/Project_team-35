package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
            request.decline();
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

    public static boolean sortRequestsWithField(String field) {
        if (field.startsWith("id")) {
            if (field.charAt(3) == 'a') {
                Collections.sort(Manager.newRequests, Comparator.comparing(Request::getRequestId));
                Collections.sort(Manager.checkedRequests, Comparator.comparing(Request::getRequestId));
            }
            if (field.charAt(3) == 'b') {
                Collections.sort(Manager.newRequests, Comparator.comparing(Request::getRequestId).reversed());
                Collections.sort(Manager.checkedRequests, Comparator.comparing(Request::getRequestId).reversed());
            }
            return true;
        }
        return false;
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
            throw new ThereIsNotCategoryWithNameException("There isn't any category with requested name.",1);
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


    public static boolean checkCategoryExistence(String categoryName) throws ThereIsNotCategoryWithNameException {
        if (Category.isThereCategoryWithName(categoryName)) {
            return true;
        }
        else {
            throw new ThereIsNotCategoryWithNameException("There is'nt any category with requested name.Try again.",1);
        }
    }


    public static boolean editCategoryName(String previousName, String newName) {
        Category.getCategoryByName(previousName).setCategoryName(newName);
        return true;
    }


    public static void addAttributeToCategory(String categoryName, String attribute) throws RepeatedCategoryAttributeException {
        Category category = Category.getCategoryByName(categoryName);
        if (category.specialAttributes.contains(attribute)) {
            throw new RepeatedCategoryAttributeException("The requested attribute is repeated. Try again.");
        }
        else {
            category.specialAttributes.add(attribute);
        }
    }

    public static void deleteAttributeFromCategory(String categoryName, String attribute) throws FieldDoesNotExist {
        Category category = Category.getCategoryByName(categoryName);
        if (category.getSpecialAttributes().contains(attribute)) {
            category.getSpecialAttributes().remove(attribute);
            deleteAttributeFromProducts(category, attribute);
        }
        else {
            throw new FieldDoesNotExist("The requested attribute does'nt exist. Try again.");
        }
    }
    private static void deleteAttributeFromProducts(Category category, String attribute) {
        for (Product product : category.getCategoryProducts()) {
            product.getSpecialAttributes().remove(attribute);
        }
    }


    public static void editAttributeName(String categoryName, String previousAttributeName, String newAttributeName) throws FieldDoesNotExist, RepeatedCategoryAttributeException {
        Category category = Category.getCategoryByName(categoryName);
        ArrayList<String> specialAttributes = category.getSpecialAttributes();
        if (specialAttributes.contains(previousAttributeName)) {
            if (specialAttributes.contains(newAttributeName)) {
                throw new RepeatedCategoryAttributeException("This category has an attribute with your requested new name. Try again.");
            }
            specialAttributes.remove(previousAttributeName);
            specialAttributes.add(newAttributeName);
            editAttributeNameAtProducts(category, previousAttributeName, newAttributeName);
        }
        else {
            throw new FieldDoesNotExist("This category has no attribute with requested name.");
        }
    }

    private static void editAttributeNameAtProducts(Category category, String previousAttributeName, String newAttributeName) {
        for (Product product : category.getCategoryProducts()) {
            HashMap<String, String> specialAttributes = product.getSpecialAttributes();
            if (specialAttributes.containsKey(previousAttributeName)) {
                String value = specialAttributes.get(previousAttributeName);
                specialAttributes.remove(previousAttributeName);
                specialAttributes.put(newAttributeName, value);
            }
        }
    }


    public static void checkExistenceOfCustomerUsername(String customerUsername) throws NotExistCustomerWithUserNameException {
        if (!Customer.isThereCustomerWithUsername(customerUsername)) {
            throw new NotExistCustomerWithUserNameException("There is'nt any customer with requested username. Enter a customer username:");
        }
    }

    public static void createDiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<String> includedCustomersUserNames) {
        ArrayList<Customer> includedCustomers = new ArrayList<>();
        for (String userName : includedCustomersUserNames) {
            includedCustomers.add(Customer.getCustomerWithName(userName));
        }
        DiscountCode discountCode = new DiscountCode(code, finalDate, startDate, discountPercent, maximumAvailableAmount, availableUseFrequent, includedCustomers);
        for (Customer customer : includedCustomers) {
            customer.discountCodes.add(discountCode);
        }
    }

    public static String checkAndGetDiscountCodeDetailsWithCode(String code) throws DiscountNotExist {
        if (DiscountCode.isThereDiscountCodeWithCode(code)) {
            return DiscountCode.getDiscountCodeWithCode(code).getDetails();
        }
        else {
            throw new DiscountNotExist("The requested discount code does'nt exist or expired.");
        }
    }

    public static void deleteDiscountCodeWithCode(String code) throws DiscountNotExist {
        if (DiscountCode.isThereDiscountCodeWithCode(code)) {
            DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);
            DiscountCode.getAllDiscountCodes().remove(discountCode);
            for (Customer customer : discountCode.includedBuyersAndUseFrequency.keySet()) {
                customer.discountCodes.remove(discountCode);
            }
        }
        else {
            throw new DiscountNotExist("The requested discount code does'nt exist or expired.");
        }
    }
}
