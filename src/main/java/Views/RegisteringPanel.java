package Views;

import Controller.*;
import Controller.Exceptions.*;
import Model.Account;

import java.util.HashMap;
import java.util.regex.Matcher;

import static Controller.AccountBoss.firstStepOfRegistering;
import static Controller.AccountBoss.makeAccount;

public class RegisteringPanel extends Page {
    private static HashMap<String, String> usernameAndPassword = new HashMap<>();
    private static HashMap<String, String> allPersonalInfo = new HashMap<String, String>();

    public RegisteringPanel(String name, Page parentPage) {
        super(name, parentPage);

        subPages.put("4", registerUser());
        subPages.put("5", loginGetUsername());
        subPages.put("2", new Page("logout",this) {
            @Override
            public void execute() {
                Page nextPage = null;
                if (Account.getOnlineAccount()==null){
                    System.err.println("you should first login for logout");
                    nextPage = parentPage;
                }else {
                    AccountBoss.logout(Account.getOnlineAccount());
                    System.out.println("logout successfully");
                    nextPage = new MainPage();
                }
                nextPage.execute();
            }
        });
        subPages.put("offs", this);
        subPages.put("6",gotoProducts());
        subPages.put("search", this);

    }
    private Page gotoProducts(){
        return new Page("go to productsPage",this) {
            @Override
            public void execute() {
                new ProductsPage("productsPage",this.parentPage).execute();
            }
        };
    }

    /**
     * this method is completed
     *
     * @return
     */
    private Page login() {
        return new Page("login", this) {
            @Override
            public void execute() {
                Page nextPage = null;
                AccountBoss.startLogin(usernameAndPassword.get("username"), usernameAndPassword.get("password"));
                System.out.println("login successfully");
                account = Account.getAccountWithUsername(usernameAndPassword.get("username"));
                if (parentPage.parentPage.name.equals("Main Menu")) {
                    MainPage mainPage = new MainPage();
                    nextPage = mainPage;
                } else {
                  nextPage =  parentPage.parentPage;
                }
            nextPage.execute();
            }
        };
    }

    private Page loginGetPassword() {
        return new Page("get password for login", this) {
            @Override
            public void execute() {
                System.out.println("password:");
                Page nextPage = null;
                String command = scanner.nextLine();
                if (command.equals("back")) {
                    nextPage = parentPage;
                }else {
                    String regex = "(\\S+)";
                    Matcher matcher = getMatcher(command, regex);
                    matcher.matches();
                    if (command.matches(regex)) {
                        try {
                            String username = usernameAndPassword.get("username");
                            AccountBoss.checkPasswordValidity(username, matcher.group(1));
                            usernameAndPassword.put("password", matcher.group(1));
                            nextPage = login();
                        } catch (PasswordValidity e) {
                            System.out.println(e.getMessage());
                            nextPage = this;
                        }
                    } else {
                        System.err.println("invalid command");
                        nextPage = this;
                    }
                }
                nextPage.execute();
            }
        };
    }

    private Page loginGetUsername() {
        return new Page("Login", this) {


            @Override
            public void execute() {
                System.out.println("print command:");

                String command = scanner.nextLine();
                Page nextPage = null;
                if (command.equals("back")) {
                    nextPage = parentPage;
                } else {
                    String regex = "login (\\w+)";
                    Matcher matcher = getMatcher(command, regex);
                    matcher.matches();
                    if (command.matches(regex)) {
                        try {
                            AccountBoss.checkUsernameExistenceInLogin(matcher.group(1));
                            usernameAndPassword.put("username", matcher.group(1));
                          nextPage =  loginGetPassword();
                        } catch (ExistenceOfUserWithUsername | LoginWithoutLogout e) {
                            System.out.println(e.getMessage());
                            nextPage = this;
                        }
                    } else {
                        System.err.println("invalid command");
                        nextPage = this;
                    }
                }
                nextPage.execute();
            }

        };
    }

    /**
     * for getting all personal info except username and make account and back to main page.
     *
     * @return
     */
    private Page registerSecondPage() {

        return new Page("second page Of registering",registerUser()) {
            @Override
            public void execute() {
                setSubPages(subPages);
                Page nextPage = null;
                boolean isItBack = false;
              P1:  for (String s : subPages.keySet()) {
                    while (true) {

                        System.out.println(s + ":");
                        String command = scanner.nextLine();
                        if (command.equals("back")) {
                            nextPage = parentPage;
                            isItBack = true;
                            break P1;
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

            if (!isItBack) {
                makeAccount(allPersonalInfo);

                // System.out.println("successfully registered");
                if (parentPage.parentPage.name.equalsIgnoreCase("UserPage")) {
                    System.out.println("login:");
                    nextPage = loginGetUsername();
                } else {
                    nextPage = parentPage.parentPage;
                }
            }
                nextPage.execute();

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
     *
     * @return
     */
    private Page registerUser() {
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
                        nextPage = registerSecondPage();

                    } catch (MoreThanOneManagerException | RepeatedUserName | RequestProblemNotExistManager e) {
                        System.err.println(e.getMessage());
                        nextPage = this;
                    }

                } else if (command.equals("back")) {
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("help")){
                    System.out.println("create account [type of user] [username] *** back(go to "+parentPage.name+") *** help");
                    nextPage = this;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }

        };
    }



}
