package Views;

import Controller.CantRemoveYourAccountException;
import Controller.ManagerBoss;
import Controller.NotValidRequestIdException;
import Controller.NotValidUserNameException;
import Model.Account;
import Model.Manager;
import Model.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ManagerPage extends Page {
    public ManagerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("manage requests", this);
        subPages.put("manage users", this);

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
            public void show() {
                super.show();
            }
        };
    }

    private Page manageAllProducts() {
        return new Page("manage all products", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("remove", this);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
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
            public void show() {
                super.show();
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
            public void show() {
                super.show();
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
            public void show() {
                super.show();
            }
        };
    }

    private Page manageCategories() {
        return new Page("manage categories", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("edit", this);
                subPages.put("add", this);
                subPages.put("remove", this);
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
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
            public void show() {
//commands and back
            }
        };
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute() {
        show();
        Page nextPage = null;
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("manage requests")) {
            nextPage = manageRequests();
        }
        else if (command.equalsIgnoreCase("back")){
            nextPage = parentPage;
        }
        else if (command.equalsIgnoreCase("manage users")) {
            nextPage = manageUsers();
        }
        else {
            System.err.println("Invalid Command");
            this.execute();
        }
        nextPage.execute();
    }
}
