package Views;
import Controller.AccountBoss;
import Controller.Exceptions.*;
import Controller.Exceptions.NullProduct;
import Controller.JustOneOffForEveryProduct;
import Controller.SellerBoss;
import Controller.Exceptions.ThisIsNotReadyForEdit;
import Controller.Exceptions.ThisOffNotExist;
import Model.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
public class SellerPage extends Page {
    private HashMap<String, String> productInfo;
    private HashMap<String, String> offInfoChanges;

    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("2", viewCompanyInformation());
        subPages.put("3", viewSalesHistory());
        subPages.put("4", viewCredit());
        subPages.put("5", viewCategory());
        subPages.put("6", manageProducts());
        subPages.put("7", addProduct());
        subPages.put("8", removeProduct());
        subPages.put("9", viewOffs());
        subPages.put("1", new RegisteringPanel("registering panel", this));

    }


    private Page viewCompanyInformation() {
        return new Page("view company information", this) {
            @Override
            public void execute() {
                System.out.println("company information :");
                System.out.println(AccountBoss.showCompanyInfo((Seller) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }

    /**
     * it give online account to show history method of controller and get back a list of products name which seller had sold and go back to seller page.
     *
     * @return
     */
    private Page viewSalesHistory() {
        return new Page("view sales history", this) {
            @Override
            public void execute() {
                System.out.println("sales history : ");
                for (String sale : SellerBoss.showHistoryOfSales((Seller) Account.getOnlineAccount())) {
                    System.out.println(sale);
                }
                parentPage.execute();
            }

        };
    }

    private Page editOff() {
        return new Page("edit off", this) {
            @Override
            public void execute() {
                System.out.println(name);
                System.out.println("enter command:(edit [off ID] *** back)");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^edit (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    System.out.println("enter\ntype : end\nchange : edit\nto stop process");
                    Off off = Off.getOffById(Integer.parseInt(matcher.group(1)));
                    Seller seller = (Seller) Account.getOnlineAccount();
                    {
                        String change = new String();
                        String type = new String();
                        offInfoChanges = new HashMap<>();
                        ArrayList<String> listOfTypes = new ArrayList<>();
                        listOfTypes.add("startDate");
                        listOfTypes.add("finalDate");
                        listOfTypes.add("offPercent");
                        listOfTypes.add("maximumAmountOfOff");
                        String[] lists = new String[0];
                        lists = listOfTypes.toArray(lists);
                        int i = 0;
                        do {
                            System.out.println(lists[i] + ":");
                            change = scanner.nextLine();
                            offInfoChanges.put(lists[i], change);
                            i += 1;
                        } while (i < 4);
                        try {

                            SellerBoss.editOff((Seller) Account.getOnlineAccount(), off, offInfoChanges);
                            nextPage = parentPage;
                            System.out.println("send request successfully");


                        } catch (ThisIsNotReadyForEdit | InputStringExceptNumber | ItIsNotCorrect | ParseException | TimeLimit itIsNotCorrect) {
                            System.out.println(itIsNotCorrect.getMessage());
                            nextPage = this;
                        }
                        nextPage = this;
                    }
                } else if (command.equalsIgnoreCase("back")) {
                    nextPage = parentPage;
                } else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();


            }
        };
    }

    private Page addOff() {
        return new Page("add off", this) {
            @Override
            public void execute() {
                System.out.println("do you want to back?(yes|no)");
                String decision = scanner.nextLine();
                Page nextPage = null;
                if (decision.equalsIgnoreCase("yes")) {
                    nextPage = parentPage;
                } else if (decision.equalsIgnoreCase("no")) {
                    System.out.println("enter data of off:");
                    System.out.println("start date:(yyyy-MM-ddThh:mm:ss)");
                    String startDate = scanner.nextLine();
                    System.out.println("final Date:(yyyy-MM-ddThh:mm:ss)");
                    String finalDate = scanner.nextLine();
                    System.out.println("percent:");
                    String percent = scanner.nextLine();
                    System.out.println("maximum:");
                    String max = scanner.nextLine();
                    System.out.println("products:(end add product | [productId])");
                    ArrayList<Integer> id = new ArrayList<>();
                    String command = null;
                    while (true) {
                        command = scanner.nextLine();
                        if (command.equalsIgnoreCase("end add product")) {
                            break;
                        } else {
                            if (command.matches("^\\d+$")) {
                                id.add(Integer.parseInt(command));
                            } else {
                                System.err.println("invalid format for id");
                            }
                        }
                    }
                    try {
                        SellerBoss.addOff(id, (Seller) Account.getOnlineAccount(), startDate, finalDate, (percent), (max));
                        System.out.println("your request successfully send to manager");
                        nextPage = parentPage;
                    } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber | NullProduct | JustOneOffForEveryProduct e) {
                        System.err.println(e.getMessage());
                        nextPage = this;
                    }
                } else {
                    System.err.println("invalid command");
                    nextPage = parentPage;
                }
                nextPage.execute();
            }
        };
    }

    private Page viewOffs() {
        return new Page("view offs", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view", new Page("view off", this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("edit", new Page("edit off", this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("add off", new Page("add off", this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });

            }

            private ArrayList<Off> sortOffList = new ArrayList<>();
            private String field = "number";
            @Override
            public void execute() {

                  sortOffList = SellerBoss.sortOffs(field,(Seller)Account.getOnlineAccount());

                System.out.println("your offs ids:");
                System.out.println(field);
                System.out.println("this list is from down to up");
                for (Off off : sortOffList) {
                    System.out.println(off.getOffId());
                }
                System.out.println("enter your command:");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^view off (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    try {
                        System.out.println(SellerBoss.viewOff((Seller) Account.getOnlineAccount(), matcher.group(1)));
                    } catch (ThisIsNotYours | ThisOffNotExist thisIsNotYours) {
                        System.out.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;
                    }
                    nextPage = parentPage;
                } else if (command.equalsIgnoreCase("edit off")) {
                    nextPage = editOff();
                } else if (command.equalsIgnoreCase("add off")) {
                    nextPage = addOff();
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("add off *** view off [off id] *** edit off *** back *** help\nsort off by [max|startDate|finishDate|percent|number] *** disable *** current field");
                    nextPage = this;
                } else if (command.equalsIgnoreCase("back")) {
                    nextPage = parentPage;
                } else if (command.equalsIgnoreCase("disable")){
                    field = "number";
                    nextPage = this;
                }else if (command.equalsIgnoreCase("current field")){
                    System.out.println("***     "+field+"        ***");
                    nextPage = this;
                }
                else {
                    regex = "^sort off by (startDate|finishDate|percent|max|number)$";
                  matcher = getMatcher(command,regex);
                  matcher.matches();
                    if (command.matches(regex)){
                        field = matcher.group(1);
                    }else {
                        System.err.println("invalid command");
                    }
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }

    private Page viewCredit() {
        return new Page("credit show", this) {
            @Override
            public void execute() {
                System.out.println(SellerBoss.sellerCredit((Seller) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }

    private Page viewCategory() {
        return new Page("view categories", this) {
         private    ArrayList<Category> list = new ArrayList<>();
         private String field = "name";
            @Override
            public void execute() {
                System.out.println("field of sort:"+field);
                System.out.println("categories:");
                list = SellerBoss.sortCategory(field);
                for (Category category : list) {
                    String size = "";
                    if (field.equalsIgnoreCase("productNumber")){
                        size = String.valueOf(category.getCategoryProducts().size());
                    }
                    System.out.println(category.getCategoryName()+"     "+size );
                }
                System.out.println("enter command:");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^sort (name|productNumber)$";
                Matcher matcher = getMatcher(command,regex);
                matcher.matches();
                if (command.matches(regex)){
                  field = matcher.group(1);
                    nextPage = this;
                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("help")){
                    System.out.println("(help *** back *** sort [name|productNumber])");
                    nextPage = this;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }

    private Page editProduct() {
        return new Page("edit product", this) {
            @Override
            public void execute() {
                System.out.println(name);
                System.out.println("enter command:");
                Page nextPage = null;
                String command = scanner.nextLine();
                String regex = "^edit (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                HashMap<String, String> allEditions = new HashMap<>();
                if (command.matches(regex)) {
                    System.out.println("enter data:(end edit|back|help)");
                    String type;
                    String change;
                    do {
                        System.out.println("type:");
                        type = scanner.nextLine();
                        System.out.println("change:");
                        change = scanner.nextLine();
                        if (!(type.equalsIgnoreCase("end") && change.equalsIgnoreCase("edit"))) {
                            allEditions.put(type, change);
                        }
                    } while (!(type.equalsIgnoreCase("end") && change.equalsIgnoreCase("edit")));
                    try {
                        SellerBoss.editProduct(allEditions, matcher.group(1), (Seller) Account.getOnlineAccount());
                        nextPage = parentPage;
                    } catch (NoMatchBetweenCategoryAndAttributes | ThisIsNotYours | SoldProductsCanNotHaveChange | ThisAttributeIsNotForThisProduct | ThereIsNotCategoryWithNameException | NullProduct | InvalidNumber thisIsNotYours) {
                        // System.err.println(thisIsNotYours.getMessage());
                        thisIsNotYours.printStackTrace();
                        nextPage = this;
                    }
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("(edit [productId] ---> {type:end & change:edit} <-> for ending edit *** you cant change category and set its special attributes in a a same time)");
                    nextPage = this;
                } else if (command.equalsIgnoreCase("back")) {
                    nextPage = parentPage;
                } else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }

    private Page manageProducts() {
        return new Page("manage products", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("product", new Page("view page", this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("buyers", new Page("view buyers", this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("edit product", editProduct());
            }

            private ArrayList<Customer> buyersList = new ArrayList<>();
            private String field = "name";
            private String productSortField = "reviewNumber";
            private ArrayList<Product> productList = new ArrayList<>();
            @Override
            public void execute() {
                productList = SellerBoss.sortProductForSpecialSeller(productSortField,(Seller)Account.getOnlineAccount());
                Seller seller = (Seller) Account.getOnlineAccount();
                int i = 1;
                System.out.println("products:");
                for (Product product : productList) {
                    System.out.println(i + ")" + product.getName());
                    i += 1;
                }
                setSubPages(subPages);
                System.out.println("enter command :(help|back|...)");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^view (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                if (command.matches(regex)) {
                    try {
                        matcher.matches();

                            System.out.println(SellerBoss.showProduct(matcher.group(1), (Seller) Account.getOnlineAccount()));

                    } catch (NullProduct|ThisIsNotYours thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());

                    }
                    nextPage = parentPage;
                }
                if (command.equalsIgnoreCase("disable buyers sort")){
                    field = "name";
                    nextPage = this;
                }
                if (command.equalsIgnoreCase("current buyers sort")){
                    System.out.println("***     "+field+"       ***");
                    nextPage = this;
                }
                regex = "^sort products by (name|reviewNumber|price|inventory)$";
                if (command.equalsIgnoreCase(regex)){
                    matcher = getMatcher(command,regex);
                    matcher.matches();
                    productSortField = matcher.group(1);
                    nextPage = this;
                }
                if (command.equalsIgnoreCase("disable saleable products sort")){
                    productSortField = "reviewNumber";
                    nextPage = this;
                }
                if (command.equalsIgnoreCase("current product sort")){
                    System.out.println("***     "+productSortField+"    ***");
                    nextPage = this;
                }
                regex = "^view buyers (\\d+)$";
                matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    System.out.println(field);
                        for (Customer customer : buyersList) {
                            System.out.println(customer.getUsername());
                        }
                    nextPage = this;
                }
                regex = "^sort buyers of (\\d+) by (username|number)$";
                if (command.matches(regex)){
                    matcher = getMatcher(command,regex);
                    matcher.matches();
                    try {
                        field = matcher.group(2);
                        buyersList = SellerBoss.sortBuyers(matcher.group(1),(Seller)Account.getOnlineAccount(),field);
                    } catch (ThisIsNotYours thisIsNotYours) {
                        thisIsNotYours.printStackTrace();
                    }
                    nextPage = this;
                }
                regex = "^edit product$";
                if (command.matches(regex)) {
                    nextPage = editProduct();
                }
                regex = "^back$";
                if (command.matches(regex)) {
                    nextPage = parentPage;
                }
                regex = "^help$";
                if (command.matches(regex)) {
                    System.out.println("back *** edit products *** view buyers [productId] *** view [productId]\nsort buyers of [productId] by [username|number] *** disable buyers sort *** current buyers sort\nsort products by [name|inventory|price|reviewNumber] *** disable saleable products sort *** current product sort");
                    nextPage = this;
                }
                try {
                    nextPage.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.execute();
                }
            }

        };
    }

    private Page addProduct() {
        return new Page("add product", this) {


            @Override
            public void execute() {
                System.out.println(name);
                HashMap<String, String> specialAttributes = new HashMap<>();
                Category category = null;
                ArrayList<String> subPages = new ArrayList<>();
                subPages.add("name");
                subPages.add("price");
                subPages.add("inventory");
                subPages.add("category");
                subPages.add("company");
                subPages.add("attributes");
                subPages.add("description");
                Page nextPage = null;
                productInfo = new HashMap<>();
                boolean itHasBack = false;
                ArrayList<String > specials = new ArrayList<>();
                boolean itHasCategory = false;
                S1:
                for (String s : subPages) {
                    while (true) {
                        if (s.equalsIgnoreCase("seller")) {
                            productInfo.put(s, Account.getOnlineAccount().getUsername());
                            break;
                        }
                        System.out.println(s + " : ");
                        if (!s.equalsIgnoreCase("attributes")) {
                            String command = scanner.nextLine();
                            if (command.equalsIgnoreCase("back")) {
                                nextPage = parentPage;
                                itHasBack = true;
                                break S1;
                            }

                            if (s.equalsIgnoreCase("category")) {
                                try {
                                    specials = SellerBoss.getWithNameOfCategoryItsSpecials(command);
                                    itHasCategory = true;
                                } catch (ThereIsNotCategoryWithNameException e) {
                                    e.printStackTrace();
                                    nextPage = this;
                                    break S1;
                                }
                            }
                            productInfo.put(s, command);
                            break;
                        } else {
                              {
                                  if (itHasCategory) {
                                      if (specials != null) {
                                          for (String attribute : specials) {
                                              System.out.println(attribute + " : ");
                                              String command2 = scanner.nextLine();
                                              if (command2.equalsIgnoreCase("back")) {
                                                  nextPage = parentPage;
                                                  itHasBack = true;
                                                  break S1;
                                              }
                                              specialAttributes.put(attribute, command2);
                                          }
                                      }
                                  }
                                break;
                            }
                        }

                    }
                }
                if (!itHasBack) {
                    if (itHasCategory) {
                        SellerBoss.addRequestProduct(productInfo.get("name"), productInfo.get("price"), productInfo.get("inventory"), specialAttributes, productInfo.get("company"), productInfo.get("category"), (Seller) Account.getOnlineAccount(), productInfo.get("description"));
                        System.out.println("we send request for manager");
                        nextPage = parentPage;
                    } else {
                        System.err.println("you should give data again");
                        nextPage = this;
                    }
                }
                nextPage.execute();
            }
        };
    }

    private Page removeProduct() {
        return new Page("remove product", this) {
            @Override
            public void execute() {
                System.out.println(name);
                Page nextPage = null;
                String command = scanner.nextLine();
                String regex = "^remove (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    try {
                        try {
                            SellerBoss.removeProduct(matcher.group(1), (Seller) Account.getOnlineAccount());
                            nextPage = parentPage;
                        } catch (NullProduct nullProduct) {
                            System.err.println(nullProduct.getMessage());
                            nextPage = this;
                        }
                    } catch (ThisIsNotYours | SoldProductsCanNotHaveChange thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());
                        nextPage = this;
                    }
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("remove [productId] *** back *** help");
                    nextPage = this;
                } else if (command.matches("back")) {
                    nextPage = parentPage;
                } else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }


}
