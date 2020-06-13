package Views;

import Controller.Exceptions.*;
import Controller.Exceptions.MaxMinReplacement;
import Controller.OffBoss;
import Model.Off;
import Model.Product;
import Model.ProductFilters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Offs extends Page{
    public Offs(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("1",new RegisteringPanel("registering  panel",this));
        subPages.put("2", goToProductPage());
        subPages.put("3",filtering());

    }
    private Page filtering(){
        return new Page("filter",this) {
            private ArrayList<Product> sortedProducts = new ArrayList<>();
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
                subPages.put("1", new Page("show available filters",this) {
                    @Override
                    public void execute() {
                        System.out.println("Category\nCompany\nInventory\nName\nPrice\nProduct\nSeller");
                        parentPage.execute();
                    }
                });

                subPages.put("2", new Page("filter",this) {
                    @Override
                    public void execute() {
                   String command =  scanner.nextLine();
                   Page nextPage = null;
                   String field = "";
                   String amount1 = "";
                   String amount2 = "";
                   if (command.equalsIgnoreCase("Name")){
                       String name = scanner.nextLine();
                        amount1 = name;
                        field = "Name";
                   }else if (command.equalsIgnoreCase("Category")){
                       String category = scanner.nextLine();
                       field = "Category";
                       amount1 = category;
                   }else if (command.equalsIgnoreCase("Inventory")){
                       field = "Inventory";
                   }else if (command.equalsIgnoreCase("Company")){
                       String company = scanner.nextLine();
                       field = "Company";
                       amount1 = company;
                   }else if (command.equalsIgnoreCase("Price")){
                       String min = scanner.nextLine();
                       String max = scanner.nextLine();
                       field = "Price";
                       amount1 = min;
                       amount2 = max;
                   }
                   else if (command.equalsIgnoreCase("Seller")){
                       String seller = scanner.nextLine();
                       field = "Seller";
                       amount1 = seller;
                   }else if (command.equalsIgnoreCase("back")){
                       parentPage.execute();
                       return;
                   }
                   else {
                       this.execute();
                       return;
                   }
                        try {
                            OffBoss.addFieldToFilterFields(field,amount1,amount2);
                        } catch (CategoryNull | InvalidNumber | MaxMinReplacement | SellerShouldJustBe categoryNull) {
                            categoryNull.printStackTrace();
                        }
                        parentPage.execute();
                    }
                });
                subPages.put("3", new Page("current filters",this) {
                    @Override
                    public void execute() {
                        System.out.println("filter fields: ");
                        if (OffBoss.getFields()!=null) {
                            for (ProductFilter field : OffBoss.getFields()) {
                                System.out.println(field.getFilterName());
                            }
                        }
                        parentPage.execute();
                    }
                });
                subPages.put("4", new Page("disable filter",this) {
                    @Override
                    public void execute() {
                        Page nextPage = null;
                        System.out.println("enter a field:");
                        String field = scanner.nextLine();
                        if (!OffBoss.disableFilter(field)){
                            System.err.println("invalid");
                            nextPage = this;
                        }else {
                            nextPage = parentPage;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("5", new Page("show products",this) {
                    @Override
                    public void execute() {
                        for (Product product : OffBoss.filterFields(OffBoss.getProducts())) {
                            System.out.println(product.getName());
                        }
                    }
                });
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
    private Page goToProductPage(){
        return new Page("go to product page",this) {
            @Override
            public void execute() {
                System.out.println("enter command:");
                Page nextPage = null;
                String command = scanner.nextLine();
                String regex = "^(\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                matcher.matches();
                if (command.matches(regex)){
                    String id = matcher.group(1);
                    try {
                        Product product = OffBoss.ProductPageTransfer(Integer.parseInt(id));
                        nextPage = new GoodPage(product.getName() , this,product);
                    } catch (NullProduct | ProductIsNotConfirmed nullProduct) {
                        nullProduct.printStackTrace();
                        nextPage = this;
                    }
                }
                else  if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("help")){
                    System.out.println("[productId] *** help *** back");
                    nextPage = this;
                }else {
                    System.err.println("invalid command");
                    nextPage = this;
                }
                nextPage.execute();
            }
        };
    }

    private ArrayList<Product> list = new ArrayList<>();
    private String field = "name";
    @Override
    public void execute() {
        if (field.equalsIgnoreCase("nothing"))
            list = Off.getAllProducts();
        else {
            list = OffBoss.sortProducts(field);
        }
        System.out.println("list of offs products:");
        System.out.println(field);
        for (Product product : list) {
            System.out.println(product.getName());
        }
        System.out.println("do you want to sort offs products list?(if yes just type yes)");
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("yes")){
            String command2 = scanner.nextLine();
            String regex = "(name|reviewNumber|price|inventory|rate)";
            Matcher matcher = getMatcher(command2,regex);
            matcher.matches();
            if (command2.matches(regex)){
                field = matcher.group(1);
                nextPage = this;
            }else if (command2.equalsIgnoreCase("disable")){
                field = "name";
                nextPage = this;
            }else if (field.equalsIgnoreCase("current field")){
                System.out.println(field);
                nextPage = this;
            }else if (command2.equalsIgnoreCase("help")){
                System.out.println("[name|price|inventory|reviewNumber|rate] *** back *** help *** disable *** current field");
                nextPage = this;
            }
            else if (command2.matches("back")){
                nextPage = parentPage;
            }
            else {
                System.err.println("invalid filed");
                nextPage = this;
            }
            nextPage.execute();
        }else {
            super.execute();
        }
    }
}
