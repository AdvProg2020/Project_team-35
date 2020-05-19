package Controller;

import Controller.Exceptions.*;
import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ManagerBoss {
    public static boolean acceptRequestWithId(int requestId) throws NotValidRequestIdException {
        if (Manager.isThereNewRequestWithId(requestId)) {
            Request request = Manager.getNewRequestWithId(requestId);
            request.execute();
            Manager.newRequests.remove(request);
            Manager.checkedRequests.add(request);
            return true;
        }
        else {
            throw new NotValidRequestIdException("requestId is not Valid or is Checked");
        }
    }
    public static boolean declineRequestWithId(int requestId) throws NotValidRequestIdException {
        if (Manager.isThereNewRequestWithId(requestId)) {
            Request request = Manager.getNewRequestWithId(requestId);
            Manager.newRequests.remove(request);
            Manager.checkedRequests.add(request);
            request.decline();
            return true;
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
                Request.setCurrentSort("Request ID - Ascending");
            }
            if (field.charAt(3) == 'b') {
                Collections.sort(Manager.newRequests, Comparator.comparing(Request::getRequestId).reversed());
                Collections.sort(Manager.checkedRequests, Comparator.comparing(Request::getRequestId).reversed());
                Request.setCurrentSort("Request ID - Descending");
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



    public static boolean removeProductWithId(int productId) throws ThereISNotProductWithIdException {
        if (Product.isThereProductWithId(productId)) {
            Product toRemove = Product.getProductWithId(productId);
            Category category = toRemove.getCategory();
            category.getCategoryProducts().remove(toRemove);
            Seller seller = toRemove.getSeller();
            seller.getSalableProducts().remove(toRemove);
            Product.getAllProducts().remove(toRemove);
            return true;
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

    private static boolean deleteProductsOfCategoryAtProductClass(Category toDelete) {
        int size = Product.getAllProducts().size();
        for(int i = 0; i < size; i++){
            Product product = Product.getAllProducts().get(i);
            if (product.getCategory().equals(toDelete)) {
                Product.getAllProducts().remove(product);
                i--;
                size--;
            }
        }
        return true;
    }

    private static boolean deleteProductsOfCategoryAtSellerClass(Category toDelete) {
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
        return true;
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


    public static boolean addAttributeToCategory(String categoryName, String attribute) throws RepeatedCategoryAttributeException {
        Category category = Category.getCategoryByName(categoryName);
        if (category.specialAttributes.contains(attribute)) {
            throw new RepeatedCategoryAttributeException("The requested attribute is repeated. Try again.");
        }
        else {
            category.specialAttributes.add(attribute);
            return true;
        }
    }

    public static boolean deleteAttributeFromCategory(String categoryName, String attribute) throws FieldDoesNotExist {
        Category category = Category.getCategoryByName(categoryName);
        if (category.getSpecialAttributes().contains(attribute)) {
            category.getSpecialAttributes().remove(attribute);
            deleteAttributeFromProducts(category, attribute);
            return true;
        }
        else {
            throw new FieldDoesNotExist("The requested attribute does'nt exist. Try again.");
        }
    }
    private static boolean deleteAttributeFromProducts(Category category, String attribute) {
        for (Product product : category.getCategoryProducts()) {
            product.getSpecialAttributes().remove(attribute);
        }
        return true;
    }


    public static boolean editAttributeName(String categoryName, String previousAttributeName, String newAttributeName) throws FieldDoesNotExist, RepeatedCategoryAttributeException {
        Category category = Category.getCategoryByName(categoryName);
        ArrayList<String> specialAttributes = category.getSpecialAttributes();
        if (specialAttributes.contains(previousAttributeName)) {
            if (specialAttributes.contains(newAttributeName)) {
                throw new RepeatedCategoryAttributeException("This category has an attribute with your requested new name. Try again.");
            }
            specialAttributes.remove(previousAttributeName);
            specialAttributes.add(newAttributeName);
            editAttributeNameAtProducts(category, previousAttributeName, newAttributeName);
            return true;
        }
        else {
            throw new FieldDoesNotExist("This category has no attribute with requested name.");
        }
    }

    private static boolean editAttributeNameAtProducts(Category category, String previousAttributeName, String newAttributeName) {
        for (Product product : category.getCategoryProducts()) {
            HashMap<String, String> specialAttributes = product.getSpecialAttributes();
            if (specialAttributes.containsKey(previousAttributeName)) {
                String value = specialAttributes.get(previousAttributeName);
                specialAttributes.remove(previousAttributeName);
                specialAttributes.put(newAttributeName, value);
            }
        }
        return true;
    }


    public static boolean checkExistenceOfCustomerUsername(String customerUsername) throws NotExistCustomerWithUserNameException {
        if (!Customer.isThereCustomerWithUsername(customerUsername)) {
            throw new NotExistCustomerWithUserNameException("There is'nt any customer with requested username. Enter a customer username:");
        }
        return true;
    }

    public static boolean createDiscountCode(String code, LocalDateTime finalDate, LocalDateTime startDate, double discountPercent, double maximumAvailableAmount, int availableUseFrequent, ArrayList<String> includedCustomersUserNames, double minimumPriceForUse) {
        ArrayList<Customer> includedCustomers = new ArrayList<>();
        if (includedCustomersUserNames.contains("-all")) {
            includedCustomers.addAll(Customer.getAllCustomers());
        }
        else {
            for (String userName : includedCustomersUserNames) {
                includedCustomers.add(Customer.getCustomerWithName(userName));
            }
        }
        DiscountCode discountCode = new DiscountCode(code, finalDate, startDate, discountPercent, maximumAvailableAmount, availableUseFrequent, includedCustomers, minimumPriceForUse);
        for (Customer customer : includedCustomers) {
            customer.discountCodes.add(discountCode);
        }
        return true;
    }

    public static String checkAndGetDiscountCodeDetailsWithCode(String code) throws DiscountNotExist {
        if (DiscountCode.isThereDiscountCodeWithCode(code)) {
            return DiscountCode.getDiscountCodeWithCode(code).getDetails();
        }
        else {
            throw new DiscountNotExist("The requested discount code does'nt exist or expired.");
        }
    }

    public static boolean deleteDiscountCodeWithCode(String code) throws DiscountNotExist {
        if (DiscountCode.isThereDiscountCodeWithCode(code)) {
            DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);
            DiscountCode.getAllDiscountCodes().remove(discountCode);
            for (Customer customer : discountCode.includedBuyersAndUseFrequency.keySet()) {
                customer.discountCodes.remove(discountCode);
            }
            return true;
        }
        else {
            throw new DiscountNotExist("The requested discount code does'nt exist or expired.");
        }
    }



    public static boolean sortCategoryWithField(String field) {
            if (field.charAt(5) == 'a') {
                if (field.startsWith("name")) {
                    Collections.sort(Category.allCategories, Comparator.comparing(Category::getCategoryName));
                    Collections.sort(Category.allCategories, Comparator.comparing(Category::getCategoryName));
                    Category.setCurrentSort("Category Name - Ascending");
                }
                if (field.startsWith("size")) {
                    Collections.sort(Category.allCategories, Comparator.comparing(Category::getSize));
                    Collections.sort(Category.allCategories, Comparator.comparing(Category::getSize));
                    Category.setCurrentSort("Category Size - Aescending");
                }
                return true;
            }
        if (field.charAt(5) == 'b') {
            if (field.startsWith("name")) {
                Collections.sort(Category.allCategories, Comparator.comparing(Category::getCategoryName).reversed());
                Collections.sort(Category.allCategories, Comparator.comparing(Category::getCategoryName).reversed());
                Category.setCurrentSort("Category Name - Descending");
            }
            if (field.startsWith("size")) {
                Collections.sort(Category.allCategories, Comparator.comparing(Category::getSize).reversed());
                Collections.sort(Category.allCategories, Comparator.comparing(Category::getSize).reversed());
                Category.setCurrentSort("Category Size - Descending");
            }
            return true;
        }
        return false;
    }
    public static boolean sortAccountWithField(String field) {
        if (field.startsWith("name")) {
            if (field.charAt(5) == 'a') {
                Collections.sort(Account.allAccounts, Comparator.comparing(Account::getFullName));
                Account.setCurrentSort("Account FullName - Ascending");
            }
            if (field.charAt(5) == 'b') {
                Collections.sort(Account.allAccounts, Comparator.comparing(Account::getFullName).reversed());
                Account.setCurrentSort("Account FullName - Descending");
            }
            return true;
        }
        if (field.startsWith("username")) {
            if (field.charAt(9) == 'a') {
                Collections.sort(Account.allAccounts, Comparator.comparing(Account::getUsername));
                Account.setCurrentSort("Account Username - Ascending");
            }
            if (field.charAt(9) == 'b') {
                Collections.sort(Account.allAccounts, Comparator.comparing(Account::getUsername).reversed());
                Account.setCurrentSort("Account Username - Descending");
            }
            return true;
        }
        return false;
    }



    public static boolean sortDiscountCodesWithField(String field) {
        if (field.startsWith("percent")) {
            if (field.charAt(8) == 'a') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getDiscountPercent));
                DiscountCode.setCurrentSort("Discount Code Percent - Ascending");
            }
            if (field.charAt(8) == 'b') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getDiscountPercent).reversed());
                DiscountCode.setCurrentSort("Discount Code Percent - Descending");
            }
            return true;
        }
        if (field.startsWith("maximum")) {
            if (field.charAt(8) == 'a') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getMaximumAvailableAmount));
                DiscountCode.setCurrentSort("Discount Code Maximum Amount - Ascending");
            }
            if (field.charAt(8) == 'b') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getMaximumAvailableAmount).reversed());
                DiscountCode.setCurrentSort("Discount Code Maximum Amount - Descending");
            }
            return true;
        }
        if (field.startsWith("frequent")) {
            if (field.charAt(9) == 'a') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getAvailableUseFrequent));
                DiscountCode.setCurrentSort("Discount Code Available Frequent - Ascending");
            }
            if (field.charAt(9) == 'b') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::getAvailableUseFrequent).reversed());
                DiscountCode.setCurrentSort("Discount Code Available Frequent - Descending");
            }
            return true;
        }
        if (field.startsWith("startdate")) {
            if (field.charAt(10) == 'a') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::startDateToString));
                DiscountCode.setCurrentSort("Discount Code Start Date - Ascending");
            }
            if (field.charAt(10) == 'b') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::startDateToString).reversed());
                DiscountCode.setCurrentSort("Discount Code Start Date - Descending");
            }
        }
        if (field.startsWith("finaldate")) {
            if (field.charAt(10) == 'a') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::expireDateToString));
                DiscountCode.setCurrentSort("Discount Code Final Date - Ascending");
            }
            if (field.charAt(10) == 'b') {
                Collections.sort(DiscountCode.allDiscountCodes, Comparator.comparing(DiscountCode::expireDateToString).reversed());
                DiscountCode.setCurrentSort("Discount Code Final Date - Descending");
            }
        }

        return false;
    }


    public static boolean checkStartDateAndFinalDateForDiscountCode(LocalDateTime start, LocalDateTime end) throws DateException {
        if (end.isBefore(LocalDateTime.now())) {
            throw new DateException("The end time is before now! Are you OK?! :)");
        }
        if (end.isBefore(start)) {
            throw new DateException("The end time is before start time! Are you OK?! :)");
        }
        return true;
    }

}
