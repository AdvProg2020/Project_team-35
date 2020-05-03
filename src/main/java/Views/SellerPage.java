package Views;

import Controller.AccountBoss;
import Controller.Exceptions.*;
import Controller.NullProduct;
import Controller.SellerBoss;
import Model.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class SellerPage extends Page {
private HashMap<String , String> productInfo;
private HashMap<String , String> offInfoChanges;
    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view company information" , viewCompanyInformation());
        subPages.put("view sales history" , viewSalesHistory());
        subPages.put("view credit" , viewCredit());
        subPages.put("show categories" , viewCategory());
        subPages.put("manage products" , manageProducts());
        subPages.put("add product" , addProduct());
        subPages.put("remove product" , removeProduct());
        subPages.put("view offs" , viewOffs());



    }



    private Page viewCompanyInformation(){
        return new Page("view company information" , this) {
            @Override
            public void execute() {
                System.out.println("company information :");
                System.out.println( AccountBoss.showCompanyInfo((Seller) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }

    /**
     * it give online account to show history method of controller and get back a list of products name which seller had sold and go back to seller page.
     * @return
     */
    private Page viewSalesHistory(){
        return new Page("view sales history" , this) {
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
    private Page editOff(){
        return new Page("edit off" , this) {
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
                    }else if (command.equalsIgnoreCase("back")) {
                        nextPage = parentPage;
                    } else {
                        System.err.println("invalid command");
                        nextPage = this;
                    }
                    nextPage.execute();


            }
        };
    }

    private Page addOff(){
        return new Page("add off",this) {
            @Override
            public void execute() {
                System.out.println("do you want to back?(yes|no)");
                String decision = scanner.nextLine();
                if (decision.equalsIgnoreCase("yes")) {
                    parentPage.execute();
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
                            }
                        }
                    }
                    try {
                        SellerBoss.addOff(id, (Seller) Account.getOnlineAccount(), startDate, finalDate, (percent), (max));
                        System.out.println("your request successfully send to manager");
                        parentPage.execute();
                    } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber e) {
                        System.err.println(e.getMessage());
                        this.execute();
                    }
                }else {
                    System.err.println("invalid command");
                    parentPage.execute();
                }
            }
        };
    }

    private Page viewOffs(){
        return new Page("view offs" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view", new Page("view off",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("edit", new Page("edit off",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("add off", new Page("add off" , this) {
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
                Matcher matcher = getMatcher(command,regex);
                if (command.matches(regex)){
                    try {
                        System.out.println(SellerBoss.viewOff((Seller)Account.getOnlineAccount(),matcher.group(1)));
                    } catch (ThisIsNotYours thisIsNotYours) {
                        System.out.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;
                    }
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("edit off")){
                    nextPage = editOff();
                }else if (command.equalsIgnoreCase("add off")){
                        nextPage = addOff();
                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }
    private Page viewCredit(){
        return new Page("credit show" , this) {
            @Override
            public void execute() {
                System.out.println(SellerBoss.sellerCredit((Seller) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }
    private Page viewCategory(){
        return new Page("view categories" , this) {
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

    private Page editProduct(){
        return new Page("edit product", this) {
            @Override
            public void execute() {
                System.out.println(name);
                System.out.println("enter command:");
                String command = scanner.nextLine();
                String regex = "^edit (\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                HashMap<String , String> allEditions = new HashMap<>();
                if (command.matches(regex)){
                    System.out.println("enter data:(end edit|back|help)");
                    String type;
                    String change;
                    do {
                         type = scanner.next();
                         change = scanner.next();
                         allEditions.put(type,change);
                    }while (!(type.equalsIgnoreCase("end") && change.equalsIgnoreCase("edit")));
                    try {
                        try {
                            SellerBoss.editProduct(allEditions , matcher.group(1),(Seller)Account.getOnlineAccount());
                        } catch (NoMatchBetweenCategoryAndAttributes thisIsNotYours) {
                            System.err.println(thisIsNotYours.getMessage());
                        }
                    } catch (ThisIsNotYours | SoldProductsCanNotHaveChange | ThisAttributeIsNotForThisProduct thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());
                    }
                }
                else if (command.equalsIgnoreCase("back")){
                    parentPage.execute();
                }
                else {
                    System.err.println("invalid command");
                    this.execute();
                }
            }
        };
    }
    private Page manageProducts() {
        return new Page("manage products", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("product", new Page("view page" , this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("buyers", new Page("view buyers",this) {
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
                for (Product product : seller.getSalableProducts()) {
                    System.out.println(i+")"+product.getName());
                    i+=1;
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
                        System.out.println(SellerBoss.showProduct(matcher.group(1),(Seller)Account.getOnlineAccount()));
                        nextPage = parentPage;
                    } catch (ThisIsNotYours thisIsNotYours) {
                        System.err.println(thisIsNotYours.getMessage());
                        nextPage = parentPage;

                    }
                }
                regex = "^view buyers (\\d+)$";
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
                if (command.matches(regex)){
                    nextPage = parentPage;
                }
                try {
                    nextPage.execute();
                } catch (Exception e) {
                    System.err.println("invalid command");
                    this.execute();
                }
            }

        };
    }
    private Page addProduct(){
        return new Page("add product" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                System.out.println(name);
                this.subPages.put("name" , this);
                this.subPages.put("price",this);
                this.subPages.put("category" , this);
               this.subPages.put("inventory",this);
                this.subPages.put("company" , this);
                this.subPages.put("seller" , this);
                this.subPages.put("attributes" , this);

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
           S1 :     for (String s : subPages) {
                    while (true) {
                        if (s.equalsIgnoreCase("seller")) {
                            productInfo.put(s, Account.getOnlineAccount().getUsername());
                            break;
                        }
                        System.out.println(s + " : ");
                        if (!s.equalsIgnoreCase("attributes")) {
                            String command = scanner.nextLine();
                            if (command.equalsIgnoreCase("back")){
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
                                    if (command2.equalsIgnoreCase("back")){
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
                }
                else {
                    System.err.println("you should give data again");
                    nextPage.execute();
                }
            }
        };
    }

    private Page removeProduct(){
        return new Page("remove product" , this) {
            @Override
            public void execute() {
                String command = scanner.nextLine();
                String regex = "^remove (\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                if (command.matches(regex)){
                    try {
                        try {
                            SellerBoss.removeProduct(matcher.group(1),(Seller) Account.getOnlineAccount(),0);
                        } catch (NullProduct nullProduct) {
                            System.err.println(nullProduct.getMessage());
                            this.execute();
                        }
                    } catch (ThisIsNotYours | SoldProductsCanNotHaveChange thisIsNotYours) {
                       System.err.println(thisIsNotYours.getMessage());
                       this.execute();
                    }
                }
                else if (command.matches("back")){
                    parentPage.execute();
                }else {
                    System.err.println("invalid command");
                    this.execute();
                }
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
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("5")){
            nextPage = viewCompanyInformation();
        }
        else if (command.equalsIgnoreCase("6")){
            nextPage = viewSalesHistory();
        }else if (command.equalsIgnoreCase("2")){
            nextPage = viewCredit();
        }else if (command.equalsIgnoreCase("1")){
                nextPage = viewCategory();
        }else if (command.equalsIgnoreCase("3")){
                nextPage = manageProducts();
        }else if (command.equalsIgnoreCase("4")){
                nextPage = addProduct();
        }
        else if (command.equalsIgnoreCase("7")){
                nextPage = removeProduct();
        }else if (command.equalsIgnoreCase("8")){
            nextPage = viewOffs();
        }
        else if (command.equalsIgnoreCase("9")) {
            nextPage = parentPage;
        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }
}
