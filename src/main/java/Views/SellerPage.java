package Views;

import Controller.AccountBoss;
import Controller.Exceptions.*;
import Controller.Exceptions.NullProduct;
import Controller.SellerBoss;
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
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^edit (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                if (command.matches(regex)) {
                    Off off = Off.getOffById(Integer.parseInt(matcher.group(1)));
                    Seller seller = (Seller) Account.getOnlineAccount();
                    {
                        String change = new String();
                        String type = new String();
                        offInfoChanges = new HashMap<>();
                        do {
                            type = scanner.nextLine();
                            change = scanner.nextLine();
                            offInfoChanges.put(type, change);
                        } while (!(type.equalsIgnoreCase("end") && change.equalsIgnoreCase("edit")));
                        try {
                            try {
                                SellerBoss.editOff((Seller) Account.getOnlineAccount(), off, offInfoChanges);
                            } catch (InputStringExceptNumber inputStringExceptNumber) {
                                System.err.println(inputStringExceptNumber.getMessage());
                                nextPage = this;
                            }
                            System.out.println("edit successfully");
                        } catch (ItIsNotCorrect | ParseException | TimeLimit itIsNotCorrect) {
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
                    System.out.println("start date:");
                    String startDate = scanner.nextLine();
                    System.out.println("final Date:");
                    String finalDate = scanner.nextLine();
                    System.out.println("percent:");
                    String percent = scanner.nextLine();
                    System.out.println("maximum:");
                    String max = scanner.nextLine();
                    System.out.println("products:");
                    ArrayList<Integer> id = new ArrayList<>();
                    String command = null;
                    while (true) {
                        command = scanner.nextLine();
                        if (command.equalsIgnoreCase("end add product")) {
                            break;
                        } else {
                            if (command.matches("^\\d+$")) {
                                id.add(Integer.parseInt(command));
                            }else {
                                System.err.println("invalid format for id");
                            }
                        }
                    }
                    try {
                        SellerBoss.addOff(id, (Seller) Account.getOnlineAccount(), startDate, finalDate, (percent), (max));
                        System.out.println("your request successfully send to manager");
                        nextPage = parentPage;
                    } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber e) {
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

            @Override
            public void execute() {
                setSubPages(subPages);
                show();
                for (String off : SellerBoss.showOffs((Seller) Account.getOnlineAccount())) {
                    System.out.println(off);
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
                    } catch (ThisIsNotYours thisIsNotYours) {
                        System.out.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;
                    }
                    nextPage = parentPage;
                } else if (command.equalsIgnoreCase("edit off")) {
                    nextPage = editOff();
                } else if (command.equalsIgnoreCase("add off")) {
                    nextPage = addOff();
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
            @Override
            public void execute() {
                System.out.println("categories:");
                for (String category : SellerBoss.showCategories()) {
                    System.out.println(category);
                }
                // i have doubt in here
                parentPage.execute();
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
                    } catch (NoMatchBetweenCategoryAndAttributes | ThisIsNotYours | SoldProductsCanNotHaveChange | ThisAttributeIsNotForThisProduct | ThereIsNotCategoryWithNameException thisIsNotYours) {
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

            @Override
            public void execute() {
                Seller seller = (Seller) Account.getOnlineAccount();
                int i = 1;
                System.out.println("products:");
                for (Product product : seller.getSalableProducts()) {
                    System.out.println(i + ")" + product.getName());
                    i += 1;
                }
                setSubPages(subPages);
                show();
                System.out.println("enter command :");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^view (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                if (command.matches(regex)) {
                    try {
                        matcher.matches();
                        System.out.println(SellerBoss.showProduct(matcher.group(1), (Seller) Account.getOnlineAccount()));
                        nextPage = parentPage;
                    } catch (ThisIsNotYours thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;

                    }
                }
                regex = "^view buyers (\\d+)$";
                matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    try {
                        for (String buyer : SellerBoss.showBuyers(matcher.group(1), (Seller) Account.getOnlineAccount())) {
                            System.out.println(buyer);
                        }
                    } catch (ThisIsNotYours thisIsNotYours) {
                        System.out.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;
                    }
                    nextPage = parentPage;
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
                    System.out.println("back *** edit products *** view buyers [productId] *** view [productId]");
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
            public void setSubPages(HashMap<String, Page> subPages) {
                System.out.println(name);
                this.subPages.put("name", this);
                this.subPages.put("price", this);
                this.subPages.put("category", this);
                this.subPages.put("inventory", this);
                this.subPages.put("company", this);
                this.subPages.put("seller", this);
                this.subPages.put("attributes", this);

            }

            @Override
            public void execute() {
                HashMap<String, String> specialAttributes = new HashMap<>();
                Category category = null;
                ArrayList<String> subPages = new ArrayList<>();
                subPages.add("name");
                subPages.add("price");
                subPages.add("inventory");
                subPages.add("category");
                subPages.add("company");
                subPages.add("attributes");
                Page nextPage = null;
                productInfo = new HashMap<>();
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
                                break S1;
                            }
                            if (s.equalsIgnoreCase("category")) {
                                category = Category.getCategoryByName(command);
                                if (category == null) {
                                    System.err.println("we don't have a category with this name");
                                    continue;
                                }
                            }
                            productInfo.put(s, command);
                            break;
                        } else {
                            if (category == null) {
                                System.err.println("we don't have this category.");
                                break S1;

                            } else {
                                for (String attribute : category.getSpecialAttributes()) {
                                    System.out.println(attribute + " : ");
                                    String command2 = scanner.nextLine();
                                    if (command2.equalsIgnoreCase("back")) {
                                        nextPage = parentPage;
                                        break S1;
                                    }
                                    specialAttributes.put(attribute, command2);
                                }
                                break;
                            }
                        }

                    }
                }
                if (category != null) {
                    SellerBoss.addRequestProduct(productInfo.get("name"), productInfo.get("price"), productInfo.get("inventory"), specialAttributes, productInfo.get("company"), category, (Seller) Account.getOnlineAccount());
                    System.out.println("we send request for manager");
                    parentPage.execute();
                } else {
                    System.err.println("you should give data again");
                    nextPage.execute();
                }
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
                            SellerBoss.removeProduct(matcher.group(1), (Seller) Account.getOnlineAccount(), 0);
                            nextPage = parentPage;
                        } catch (NullProduct nullProduct) {
                            System.err.println(nullProduct.getMessage());
                            nextPage = this;
                        }
                    } catch (ThisIsNotYours | SoldProductsCanNotHaveChange thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());
                        nextPage = this;
                    }
                }else if (command.equalsIgnoreCase("help")){
                    System.out.println("remove [productId] *** back *** help");
                    nextPage = this;
                }
                else if (command.matches("back")) {
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
