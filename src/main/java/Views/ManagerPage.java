package Views;

import Controller.AccountBoss;
import Controller.Exceptions.*;
import Controller.ManagerBoss;
import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ManagerPage extends Page {
    public ManagerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1", manageRequests());
        subPages.put("2", manageUsers());
        subPages.put("3", manageCategories());
        subPages.put("4", manageAllProducts());
        subPages.put("5", createDiscountCode());
        subPages.put("6", viewDiscountCodes());
        subPages.put("7", new RegisteringPanel("registering panel", this));
    }

    private Page manageUsers() {
        return new Page("manage users", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view", this);
                subPages.put("delete user", this);
                subPages.put("create manager profile", this);
            }

            @Override
            public void execute() {
                ArrayList<Account> allUsers = Account.getAllAccounts();
                for (Account activeUser : allUsers) {
                    System.out.println(activeUser.getShortInfo());
                }
                System.out.println("Enter Command : (-help for help)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("view/delete user [username] ---- create manager profile");
                } else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                } else if (command.startsWith("view")) {
                    Matcher matcher = getMatcher(command, "^view (\\w+)$");
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        try {
                            System.out.println(ManagerBoss.getDetailsOfAccountWithUserName(username));
                        } catch (NotValidUserNameException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.err.println("Invalid Command");
                    }
                } else if (command.startsWith("delete user")) {
                    Matcher matcher = getMatcher(command, "^delete user (\\w+)$");
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        try {
                            ManagerBoss.deleteAccountWithUsername(username);
                        } catch (NotValidUserNameException | CantRemoveYourAccountException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.err.println("Invalid Command");
                    }
                } else if (command.equalsIgnoreCase("create manager profile")) {
                    HashMap<String, String> allPersonalInfo = new HashMap<>();
                    allPersonalInfo.put("type", "manager");
                    String username = getInputInFormat("Username: ", "\\w+");
                    try {
                        ManagerBoss.checkNewManagerUserName(username);
                    } catch (RepeatedUserName repeatedUserName) {
                        System.out.println(repeatedUserName.getMessage());
                        this.execute();
                    }
                    allPersonalInfo.put("username", username);
                    AccountBoss.makeAccount(inputManagerData(allPersonalInfo));
                    System.out.println("New manager account added successfully.");
                } else {
                    System.err.println("Invalid Command");
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }


    private String getInputInFormat(String helpText, String regex) {
        System.out.println(helpText);
        boolean isValid;
        String input;
        do {
            input = scanner.nextLine();
            Matcher matcher = getMatcher(input, regex);
            isValid = matcher.matches();
            if (!isValid) {
                System.err.println("Invalid Format.");
            } else {
                break;
            }
        } while (true);
        return input;
    }

    private HashMap<String, String> inputManagerData(HashMap<String, String> personalInfo) {
        String password = getInputInFormat("Password:", "\\w+");
        personalInfo.put("password", password);
        String name = getInputInFormat("Name:", "\\w+");
        personalInfo.put("name", name);
        String familyName = getInputInFormat("FamilyName:", "\\w+");
        personalInfo.put("family", familyName);
        String email = getInputInFormat("Email:", "^(\\S+)@(\\S+)\\.(\\S+)$");
        personalInfo.put("email address", email);
        String phoneNumber = getInputInFormat("PhoneNumber:", "^\\d+$");
        personalInfo.put("phone number", phoneNumber);
        return personalInfo;
    }

    private Page manageAllProducts() {
        return new Page("manage all products", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {

            }

            @Override
            public void execute() {
                System.out.println("Enter Your Command : (-help for help)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("\nRemove [PID]\n");
                } else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                } else {
                    Matcher matcher = getMatcher(command, "^remove (\\d+)$");
                    if (matcher.matches()) {
                        try {
                            ManagerBoss.removeProductWithId(Integer.parseInt(matcher.group(1)));
                        } catch (ThereISNotProductWithIdException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.err.println("Invalid Command.");
                    }
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    private Page createDiscountCode() {
        return new Page("create discount code", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {

                String codeText = getInputInFormat("Enter code text (back for back to previous menu):", "^\\w+$");
                if (codeText.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                String discountPercent = getInputInFormat("Enter discount percent (back for back to first input):", "^(\\d{1,2}(\\.\\d+)?)|(back)$");
                if (discountPercent.equalsIgnoreCase("back")) {
                    this.execute();
                }
                String maximumDiscountAmount = getInputInFormat("Enter maximum discount amount (back for back to first input):", "^(\\d+(\\.\\d+)?)|(back)$");
                if (maximumDiscountAmount.equalsIgnoreCase("back")) {
                    this.execute();
                }
                String repeatRate = getInputInFormat("How many times a customer can use this code? (back for back to first input)", "^(\\d+)$");
                if (repeatRate.equalsIgnoreCase("back")) {
                    this.execute();
                }
                ArrayList<String> customersUserNames = listOfUsersForDiscountCodeScanner();
                if (customersUserNames.contains("-back")) {
                    this.execute();
                }
                LocalDateTime fullStartDate = null, fullFinalDate = null;
                while(true) {
                    String startDay = getInputInFormat("Enter start date in format [yyyy-mm-dd]:", "^[0-2][0-9]{3}-((0\\d)|(1[0-2]))-(([0-2]\\d)|(3[0-1]))$");
                    String finalDay = getInputInFormat("Enter final date in format [yyyy-mm-dd]:", "^[0-2][0-9]{3}-((0\\d)|(1[0-2]))-(([0-2]\\d)|(3[0-1]))$");
                    String startTime = getInputInFormat("Enter start time in format [hh:mm:ss]:", "^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$");
                    String finalTime = getInputInFormat("Enter final time in format [hh:mm:ss]:", "^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$");
                    try{
                        fullStartDate = LocalDateTime.parse(startDay + "T" + startTime);
                        fullFinalDate = LocalDateTime.parse(finalDay + "T" + finalTime);
                        break;
                    }
                    catch (java.time.format.DateTimeParseException e) {
                        int index = e.getMessage().indexOf("Invalid date");
                        System.err.println(e.getMessage().substring(index));
                    }
                }
                double percent = Double.parseDouble(discountPercent);
                double maximumAmount = Double.parseDouble(maximumDiscountAmount);
                int repeat = Integer.parseInt(repeatRate);
                ManagerBoss.createDiscountCode(codeText, fullFinalDate, fullStartDate, percent, maximumAmount, repeat, customersUserNames);
                System.out.println("Successful :)");
                parentPage.execute();
            }
            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }


    private static ArrayList<String> listOfUsersForDiscountCodeScanner() {
        System.out.println("Enter every username in a line. for end enter -end. for back enter -back");
        ArrayList<String> userNames = new ArrayList<>();
        while (true) {
            String username = scanner.nextLine();
            if (username.equalsIgnoreCase("-back")) {
                userNames.add(username);
                return userNames;
            }
            if (username.equalsIgnoreCase("-end")) {
                break;
            }
            try {
                ManagerBoss.checkExistenceOfCustomerUsername(username);
                userNames.add(username);
            } catch (NotExistCustomerWithUserNameException e) {
                System.out.println(e.getMessage());
            }
        }
        return userNames;
    }

    private Page viewDiscountCodes() {
        return new Page("view discount codes", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view discount codes", this);
                subPages.put("edit discount code", this);
                subPages.put("remove discount code", this);
            }

            @Override
            public void execute() {
                ArrayList<DiscountCode> discountCodes = DiscountCode.getAllDiscountCodes();
                System.out.println("Discount Codes:");
                for (DiscountCode discountCode : discountCodes) {
                    System.out.println(discountCode.getDiscountCodeInlineInfo());
                }
                System.out.println("Enter command: (-help for help)");
                String command = scanner.nextLine();
                Matcher matcher = getMatcher(command, "^(view|remove|edit) discount code (\\w+)$");
                if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                else if (command.equalsIgnoreCase("-help")) {
                    System.out.println("view/remove/edit discount code [code]");
                }
                else if (matcher.matches()) {
                    String code = matcher.group(2);
                    if (matcher.group(1).equalsIgnoreCase("view")) {
                        try {
                            System.out.println(ManagerBoss.checkAndGetDiscountCodeDetailsWithCode(code));
                        } catch (DiscountNotExist discountNotExist) {
                            System.out.println(discountNotExist.getMessage());
                        }
                    }
                    else if (matcher.group(1).equalsIgnoreCase("remove")) {
                        try {
                            ManagerBoss.deleteDiscountCodeWithCode(code);
                            System.out.println("Successful :)");
                        } catch (DiscountNotExist discountNotExist) {
                            System.out.println(discountNotExist.getMessage());
                        }
                    }
                    else if (matcher.group(1).equalsIgnoreCase("edit")) {

                    }
                }
                else {
                    System.err.println("Invalid command.");
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    private Page manageRequests() {
        return new Page("manage requests", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {

            }

            @Override
            public void execute() {
                ArrayList<Request> newRequests = Manager.getNewRequests();
                ArrayList<Request> checkedRequests = Manager.getCheckedRequests();
                System.out.println("New Requests:");
                for (Request newRequest : newRequests) {
                    System.out.println(newRequest.getRequestInfo());
                }
                System.out.println("Checked Requests:");
                for (Request checkedRequest : checkedRequests) {
                    System.out.println(checkedRequest.getRequestInfo());
                }
                System.out.println("Enter Command : (-help for help)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("Accept/Decline/details [RID]\nSort by id-a/-b (a for ascending b for else)");
                }
                else if (command.startsWith("sort by")) {
                    Matcher matcher = getMatcher(command, "^sort by (\\S+)$");
                    if (matcher.matches()) {
                        Matcher matcher1 = getMatcher(matcher.group(1), "^id-(a|b)$");
                        if (matcher1.matches()) {
                            ManagerBoss.sortRequestsWithField(matcher.group(1));
                        }
                        else {
                            System.err.println("Invalid Command.");
                        }
                    }
                    else {
                        System.err.println("Invalid command.");
                    }

                } else if (command.startsWith("accept") || command.startsWith("decline") || command.startsWith("details")) {
                    Matcher matcher = getMatcher(command, "^(accept|decline|details)\\s+(\\d+)$");
                    if (matcher.matches()) {
                        int requestId = Integer.parseInt(matcher.group(2));
                        if (command.startsWith("accept")) {
                            try {
                                ManagerBoss.acceptRequestWithId(requestId);
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (command.startsWith("decline")) {
                            try {
                                ManagerBoss.declineRequestWithId(requestId);
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (command.startsWith("details")) {
                            try {
                                System.out.println("\n" + ManagerBoss.getDetailsOfRequestWithId(requestId) + "\n");
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.err.println("Invalid Command");
                    }
                } else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                } else {
                    System.err.println("Invalid Command");
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    private Page manageCategories() {
        return new Page("manage categories", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
            }

            @Override
            public void execute() {
                ArrayList<Category> allCategories = new ArrayList<>();
                allCategories = Category.getAllCategories();
                //dont hand 2 above lines :)
                System.out.println("All Categories Are : ");
                for (Category category : allCategories) {
                    System.out.println(category.getShortInfo());
                }
                System.out.println("Enter Command : (-help for help)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("add/edit/remove [categoryName]\nsort by (name|size)-(a|b) (a for ascending b for else)");
                } else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                } else if(command.startsWith("sort by")) {
                    Matcher matcher = getMatcher(command, "^sort by (\\S+)$");
                    if (matcher.matches()) {
                        Matcher matcher1 = getMatcher(matcher.group(1), "^(name|size)-(a|b)$");
                        if (matcher1.matches()) {
                            ManagerBoss.sortCategoryWithField(matcher.group(1));
                        }
                        else {
                            System.err.println("Invalid Command.");
                        }
                    }
                    else {
                        System.err.println("Invalid Command.");
                    }
                }else if (command.startsWith("edit")) {
                    Matcher matcher = getMatcher(command, "^edit (\\w+)$");
                    if (matcher.matches()) {
                        try {
                            ManagerBoss.checkCategoryExistence(matcher.group(1));
                            editCategory(matcher.group(1)).execute();
                        } catch (ThereIsNotCategoryWithNameException e) {
                            System.out.println(e.getMessage());
                            this.execute();
                        }
                    } else {
                        System.err.println("Invalid Command.");
                    }
                } else {
                    Matcher matcher = getMatcher(command, "^(add|remove)\\s+(\\w+)$");
                    if (matcher.matches()) {
                        String categoryName = matcher.group(2);
                        if (command.startsWith("add")) {
                            ArrayList<String> specialAttributes = categorySpecialAttributesScanner();
                            if (specialAttributes.contains("-back")) {
                                this.execute();
                            }
                            try {
                                ManagerBoss.addNewCategory(categoryName, specialAttributes);
                            } catch (RepeatedCategoryNameException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (command.startsWith("remove")) {
                            try {
                                int result = ManagerBoss.startDeleteCategoryWithName(categoryName);
                            } catch (ThereIsNotCategoryWithNameException | NullPointerException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.err.println("Invalid Command.");
                    }
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }


    private Page editCategory(String categoryName) {
        return new Page("Edit Category", this) {

            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                System.out.println("Enter Command: (-help for help. back for back.)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("add/delete/rename attribute ---- edit name");
                } else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                } else if (command.equalsIgnoreCase("edit name")) {
                    String newName = getInputInFormat("Enter new categoryName:", "^\\w+$");
                    ManagerBoss.editCategoryName(categoryName, newName);
                    System.out.println("Successful :)");
                } else if (command.equalsIgnoreCase("add attribute")) {
                    String newAttribute = getInputInFormat("Enter new attribute:", "^\\w+$");
                    try {
                        ManagerBoss.addAttributeToCategory(categoryName, newAttribute);
                        System.out.println("Successful :)");
                    } catch (RepeatedCategoryAttributeException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (command.equalsIgnoreCase("delete attribute")) {
                    String toDeleteAttribute = getInputInFormat("Enter attribute to delete:", "^\\w+$");
                    try {
                        ManagerBoss.deleteAttributeFromCategory(categoryName, toDeleteAttribute);
                        System.out.println("Successful :)");
                    } catch (FieldDoesNotExist fieldDoesNotExist) {
                        System.out.println(fieldDoesNotExist.getMessage());
                    }
                } else if (command.equalsIgnoreCase("rename attribute")) {
                    String previousAttributeName = getInputInFormat("Enter attribute previous name:", "^\\w+$");
                    String newAttributeName = getInputInFormat("Enter attribute newName:", "^\\w+$");
                    try {
                        ManagerBoss.editAttributeName(categoryName, previousAttributeName, newAttributeName);
                        System.out.println("Successful :)");
                    } catch (FieldDoesNotExist | RepeatedCategoryAttributeException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.err.println("Invalid command.");
                }
                this.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
    }

    private static ArrayList<String> categorySpecialAttributesScanner() {
        System.out.println("Enter every feature in a line. for end enter -end. for back enter -back.");
        ArrayList<String> specialAttributes = new ArrayList<>();
        while (true) {
            String feature = scanner.nextLine();
            if (feature.equalsIgnoreCase("-back")) {
                specialAttributes.add(feature);
                return specialAttributes;
            }
            if (feature.equalsIgnoreCase("-end")) {
                break;
            }
            specialAttributes.add(feature);
        }
        return specialAttributes;
    }

    private Page newEdit() {
        return new Page("edit manager data", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                //commands and back
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
//commands and back
                return false;
            }
        };
    }


    @Override
    public void execute() {

        super.execute();

    }
}
