package Views;

import Controller.Exceptions.*;
import Controller.ManagerBoss;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ManagerPage extends Page {
    public ManagerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1", manageRequests());
        subPages.put("2", manageUsers());
        subPages.put("3" , manageCategories());
        subPages.put("4", manageAllProducts());
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
                ArrayList<Account> allActiveUsers = ManagerBoss.getAllActiveUsers();
                for (Account activeUser : allActiveUsers) {
                    System.out.println(activeUser.getShortInfo());
                }
                System.out.println("Enter Command : (-help for help)");
                String command = scanner.nextLine();
                if(command.equalsIgnoreCase("-help")) {
                    System.out.println("view/delete user [username] ---- create manager profile");
                }
                else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                else if (command.startsWith("view")) {
                    Matcher matcher = getMatcher(command, "^view (\\w+)$");
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        try {
                            System.out.println(ManagerBoss.getDetailsOfAccountWithUserName(username));
                        } catch (NotValidUserNameException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else {
                        System.err.println("Invalid Command");
                    }
                }
                else if(command.startsWith("delete user")) {
                    Matcher matcher = getMatcher(command, "^delete user (\\w+)$");
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        try {
                            ManagerBoss.deleteAccountWithUsername(username);
                        } catch (NotValidUserNameException | CantRemoveYourAccountException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else {
                        System.err.println("Invalid Command");
                    }
                }
                else if (command.equalsIgnoreCase("create manager profile")) {

                }
                else {
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
                }
                else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                else {
                    Matcher matcher = getMatcher(command, "^remove (\\d+)$");
                    if (matcher.matches()) {
                        try {
                            ManagerBoss.removeProductWithId(Integer.parseInt(matcher.group(1)));
                        } catch (ThereISNotProductWithIdException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else {
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
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
            }
        };
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
                super.execute();
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
                ArrayList<Request>  newRequests = Manager.getNewRequests();
                ArrayList<Request>  checkedRequests = Manager.getCheckedRequests();
                System.out.println("New Requests :");
                for (Request newRequest : newRequests) {
                    System.out.println(newRequest.getRequestInfo());
                }
                System.out.println("Checked Requests :");
                for (Request checkedRequest : checkedRequests) {
                    System.out.println(checkedRequest.getRequestInfo());
                }
                System.out.println("Enter Command : (-help for help)");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("-help")) {
                    System.out.println("Accept/Decline/details [RID]");
                }
                else if (command.startsWith("accept") || command.startsWith("decline") || command.startsWith("details")) {
                    Matcher matcher = getMatcher(command, "^(accept|decline|details)\\s+(\\d+)$");
                    if (matcher.matches()) {
                        int requestId = Integer.parseInt(matcher.group(2));
                        if (command.startsWith("accept")) {
                            try {
                                ManagerBoss.acceptRequestWithId(requestId);
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if (command.startsWith("decline")) {
                            try {
                                ManagerBoss.declineRequestWithId(requestId);
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if (command.startsWith("details")) {
                            try {
                                System.out.println("\n" + ManagerBoss.getDetailsOfRequestWithId(requestId) + "\n");
                            } catch (NotValidRequestIdException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    else {
                        System.err.println("Invalid Command");
                    }
                }
                else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                else {
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
                    System.out.println("add/edit/remove [categoryName]");
                }
                else if (command.equalsIgnoreCase("back")) {
                    parentPage.execute();
                }
                else {
                    Matcher matcher = getMatcher(command, "^(add|remove|edit)\\s+(\\w+)$");
                    if (matcher.matches()) {
                        String categoryName = matcher.group(2);
                        if (command.startsWith("add")) {
                            ArrayList<String> specialAttributes = categorySpecialAttributesScanner();
                            try {
                                ManagerBoss.addNewCategory(categoryName, specialAttributes);
                            } catch (RepeatedCategoryNameException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if (command.startsWith("remove")) {
                            try {
                                int result = ManagerBoss.startDeleteCategoryWithName(categoryName);
                            } catch (ThereIsNotCategoryWithNameException | NullPointerException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if (command.startsWith("edit")) {

                        }
                    }
                    else {
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

    private static ArrayList<String> categorySpecialAttributesScanner() {
        System.out.println("Enter every feature in a line. for end enter -end");
        ArrayList<String> specialAttributes = new ArrayList<>();
        while (true) {
            String feature = scanner.nextLine();
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
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute() {


        show();
        Page nextPage = null;
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("1")) {
            nextPage = manageRequests();
        }
        else if (command.equalsIgnoreCase("5")){
            nextPage = parentPage;
        }
        else if (command.equalsIgnoreCase("2")) {
            nextPage = manageUsers();
        }
        else if(command.equalsIgnoreCase("3")) {
            nextPage = manageCategories();
        }
        else if (command.equalsIgnoreCase("4")) {
            nextPage = manageAllProducts();
        }
        else {
            System.err.println("Invalid Command");
            this.execute();
        }
        nextPage.execute();
    }
}
