package Views;

import Controller.*;
import Model.Account;
import Model.RequestProblemNotExistManager;

import java.util.HashMap;
import java.util.regex.Matcher;

import static Controller.AccountBoss.firstStepOfRegistering;
import static Controller.AccountBoss.makeAccount;

public class RegisteringPanel extends Page {
    private static HashMap<String, String> usernameAndPassword = new HashMap<>();
    private static HashMap<String, String> allPersonalInfo = new HashMap<String, String>();

    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);

        subPages.put("register" , this);
        subPages.put("login" , this);
        subPages.put("offs",this);
        subPages.put("products",this);
        subPages.put("search",this);

    }

    /**
     * this method is completed
     * @return
     */
    private Page login() {
        return new Page("login", this) {
            @Override
            public void execute() {
                AccountBoss.startLogin(usernameAndPassword.get("username"), usernameAndPassword.get("password"));
                System.out.println("login successfully");
                account = Account.getAccountWithUsername(usernameAndPassword.get("username"));
                if (parentPage.parentPage.name.equals("Main Menu")) {
                    MainPage mainPage = new MainPage();
                    mainPage.execute();
                }
                else {
                    parentPage.parentPage.execute();
                }

            }
        };
    }

    private Page loginGetPassword() {
        return new Page("get password for login", this) {
            @Override
            public void execute() {
                System.out.println("password:");
                String command = scanner.nextLine();
                if (command.equals("back")) {
                    parentPage.execute();
                }
                String regex = "(\\S+)";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    try {
                        String username = usernameAndPassword.get("username");
                        AccountBoss.checkPasswordValidity(username, matcher.group(1));
                        usernameAndPassword.put("password", matcher.group(1));
                        login().execute();
                    } catch (PasswordValidity e) {
                        System.out.println(e.getMessage());
                        this.execute();
                    }
                } else {
                    System.err.println("invalid command");
                    this.execute();
                }
            }
        };
    }

    private Page loginGetUsername() {
        return new Page("Login", this) {


            @Override
            public void execute() {
                System.out.println("print command:");
                String command = scanner.nextLine();
                if (command.equals("back")) {
                    parentPage.execute();
                } else {
                    String regex = "login (\\w+)";
                    Matcher matcher = getMatcher(command, regex);
                    matcher.matches();
                    if (command.matches(regex)) {
                        try {
                            AccountBoss.checkUsernameExistenceInLogin(matcher.group(1));
                            usernameAndPassword.put("username", matcher.group(1));
                            loginGetPassword().execute();
                        } catch (ExistenceOfUserWithUsername | LoginWithoutLogout e) {
                            System.out.println(e.getMessage());
                            this.execute();
                        }
                    } else {
                        System.err.println("invalid command");
                        this.execute();
                    }
                }
            }

        };
    }

    /**
     * for getting all personal info except username and make account and back to main page.
     * @return
     */
    private Page registerSecondPage() {

        return new Page("second page Of registering", this) {
            @Override
            public void execute() {
                setSubPages(subPages);
                for (String s : subPages.keySet()) {
                    while (true) {

                        System.out.println(s + ":");
                        String command = scanner.nextLine();
                        if (command.equals("back")) {
                            parentPage.execute();
                        }
                        if (checkFormatOfPersonalInformation(s, command)) {
                            allPersonalInfo.put(s, command);
                            break;
                        } else {
                            System.err.println("invalid format of data");
                            continue;
                        }
                    }
                }


                    makeAccount(allPersonalInfo);
                    System.out.println("successfully registered");
                    if (parentPage.parentPage.name.equalsIgnoreCase("UserPage")){
                        System.out.println("login:");
                       loginGetUsername().execute();
                    }
                    else {
                        parentPage.parentPage.execute();
                    }

            }

            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("password", this);
                subPages.put("name", this);
                subPages.put("family", this);
                subPages.put("email address", this);
                subPages.put("phone number", this);
                if (allPersonalInfo.values().contains("seller")) {
                    subPages.put("company name", this);
                }

            }
        };
    }

    /**
     * this is first step of register and get username and type and check validity.
     * @return
     */
    private Page RegisterUser() {
        return new Page("register", this) {


            @Override
            public void execute() {
                System.out.println("print command:");
                Page nextPage = null;
                String command = scanner.nextLine();
                String regex = "create account (manager|customer|seller) (\\w+)";
                Matcher matcher = getMatcher(command, "create account (manager|customer|seller) (\\w+)");
                matcher.matches();
                if (command.matches(regex)) {
                    try {
                        firstStepOfRegistering(matcher.group(1), matcher.group(2));
                        allPersonalInfo.put("type", matcher.group(1));
                        allPersonalInfo.put("username", matcher.group(2));
                        registerSecondPage().execute();

                    } catch (MoreThanOneManagerException | RepeatedUserName | RequestProblemNotExistManager e) {
                        System.err.println(e.getMessage());
                        this.execute();
                    }

                } else if (command.equals("back")) {
                    parentPage.execute();
                } else {
                    System.err.println("invalid command");
                    this.execute();
                }
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
        if (command.equals("register")) {
            nextPage = RegisterUser();
        } else if (command.equals("login")) {
            nextPage = loginGetUsername();

        } else if (command.equals("back")) {
            nextPage = parentPage;
        }
        nextPage.execute();

    }



}
