package Views;

import Controller.AccountBoss;
import Controller.SellerBoss;
import Model.Account;
import Model.Category;
import Model.Seller;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class SellerPage extends Page {
private HashMap<String , String> ProductInfo;
    public SellerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view company information" , this);
        subPages.put("view sales history" , this);
        subPages.put("view credit" , this);
        subPages.put("show categories" , this);
        subPages.put("manage products" , this);
        subPages.put("add product" , this);


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

    private Page removeProduct(){
        return new Page("remove product" , this) {
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
    private Page showCategories(){
        return new Page("show categories" , this) {
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
    private Page viewOffs(){
        return new Page("view offs" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("view",this);
                subPages.put("edit",this);
                subPages.put("add off",this);

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
        return new Page("category view" , this) {
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
    private Page viewProduct(){
        return new Page("view product" , this) {
            @Override
            public void execute() {

            }
        };
    }
    private Page manageProducts(){
        return new Page("manage products",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("product",this);
                subPages.put("buyers" , this);
                subPages.put("edit",this);
            }

            @Override
            public void execute() {
                setSubPages(subPages);
                show();
                System.out.println("enter command :");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^view (\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                if (command.matches(regex)){
                    SellerBoss.showProduct(matcher.group(1));
                    nextPage = parentPage;
                }
                regex = "^view buyers (\\d+)$";
                if (command.matches(regex)){

                }
                regex = "^edit (\\d+)$";
                if (command.matches(regex)){

                }
            }
        };
    }
    private Page addProduct(){
        return new Page("add product" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("name" , this);
                subPages.put("price",this);
                subPages.put("inventory",this);
                subPages.put("company" , this);
                subPages.put("seller" , this);
                subPages.put("category" , this);
                subPages.put("attributes" , this);

            }

            @Override
            public void execute() {
                HashMap<String , String> specialAttributes = new HashMap<>();
                Category category = null;
                ProductInfo = new HashMap<>();
                    setSubPages(subPages);
                for (String s : subPages.keySet()) {
                    while (true){
                        System.out.println(s+" : \n");

                        if (!s.equalsIgnoreCase("attributes")) {
                            String command = scanner.nextLine();
                            if (s.equalsIgnoreCase("category")){
                                category = Category.getCategoryByName(command);
                                if (category == null) {
                                    System.err.println("we don't have a category with this name");
                                    continue;
                                }
                            }
                            ProductInfo.put(s, command);
                            break;
                        }
                        else {
                            for (String attribute : Objects.requireNonNull(category).getSpecialAttributes()) {
                                System.out.println(attribute+ " : ");
                                String command2 = scanner.nextLine();
                                specialAttributes.put(attribute , command2);
                            }
                            break;
                        }

                    }
                    SellerBoss.addRequestProduct(ProductInfo.get("name"),ProductInfo.get("price"),ProductInfo.get("inventory"),specialAttributes , ProductInfo.get("company"),category,(Seller) Account.getOnlineAccount());
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
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("view company information")){
            nextPage = viewCompanyInformation();
        }
        else if (command.equalsIgnoreCase("view sales history")){
            nextPage = viewSalesHistory();
        }else if (command.equalsIgnoreCase("view credit")){
            nextPage = viewCredit();
        }else if (command.equalsIgnoreCase("show categories")){
                nextPage = viewCategory();
        }else if (command.equalsIgnoreCase("manage products")){
                nextPage = manageProducts();
        }else if (command.equalsIgnoreCase("add product")){

        }
        try {
            nextPage.execute();
        }catch (Exception e){
            System.err.println("invalid command");
            this.execute();
        }
    }
}
