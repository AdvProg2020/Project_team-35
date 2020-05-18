package Views;

import Controller.Exceptions.CategoryNull;
import Controller.Exceptions.InvalidFieldForSort;
import Controller.Exceptions.InvalidNumber;
import Controller.Exceptions.SellerShouldJustBe;
import Controller.MaxMinReplacement;
import Controller.OffBoss;
import Controller.ProductBoss;
import Controller.SellerBoss;
import Model.Category;
import Model.Off;
import Model.Product;
import Model.ProductFilters.ProductFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductsPage extends Page {
    private String sortFields;
    private ArrayList<Product> currentList;
    private ArrayList<String> available;

    public ProductsPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("3", viewCategories());
        subPages.put("2", filtering());
        subPages.put("1", sorting());
        subPages.put("4", showProduct());
        subPages.put("5", enterToProductPage());
        subPages.put("6", new RegisteringPanel("registering panel", this));

    }

    private Page viewCategories() {
        return new Page("view categories", this) {
            @Override
            public void execute() {
                System.out.println("categories:");
                for (String category : SellerBoss.showCategories()) {
                    System.out.println(category);
                }
                System.out.println("end");
                parentPage.execute();
            }
        };
    }

    private Page enterToProductPage() {
        return new Page("product", this) {
            @Override
            public void execute() {
                System.out.println("enter your command:");
                String command = scanner.nextLine();
                Page nextPage = null;
                String regex = "^show product (\\d+)$";
                Matcher matcher = getMatcher(command, regex);
                matcher.matches();
                if (command.matches(regex)) {
                    int id = Integer.parseInt(matcher.group(1));
                    GoodPage goodPage = null;
                    Product product = Product.getProductWithId(id);
                    if (product != null) {
                        nextPage = new GoodPage(product.getName(), this, product);
                    } else {
                        System.err.println("invalid id");
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

    private Page filtering() {
        return new Page("filtering", this) {
            private ArrayList<Product> sortedProducts = new ArrayList<>();

            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
                subPages.put("1", new Page("show available filters", this) {
                    @Override
                    public void execute() {
                        System.out.println("Category\nCompany\nInventory\nName\nPrice\nProduct\nSeller");
                        parentPage.execute();
                    }
                });

                subPages.put("2", new Page("filter", this) {
                    @Override
                    public void execute() {
                        String command = scanner.nextLine();
                        Page nextPage = null;
                        String field = "";
                        String amount1 = "";
                        String amount2 = "";
                        if (command.equalsIgnoreCase("Name")) {
                            String name = scanner.nextLine();
                            amount1 = name;
                            field = "Name";
                        } else if (command.equalsIgnoreCase("Category")) {
                            String category = scanner.nextLine();
                            field = "Category";
                            amount1 = category;
                        } else if (command.equalsIgnoreCase("Inventory")) {
                            field = "Inventory";
                        } else if (command.equalsIgnoreCase("Company")) {
                            String company = scanner.nextLine();
                            field = "Company";
                            amount1 = company;
                        } else if (command.equalsIgnoreCase("Price")) {
                            String min = scanner.nextLine();
                            String max = scanner.nextLine();
                            field = "Price";
                            amount1 = min;
                            amount2 = max;
                        } else if (command.equalsIgnoreCase("Seller")) {
                            String seller = scanner.nextLine();
                            field = "Seller";
                            amount1 = seller;
                        } else if (command.equalsIgnoreCase("back")) {
                            parentPage.execute();
                            return;
                        } else {
                            this.execute();
                            return;
                        }
                        try {
                            OffBoss.addFieldToFilterFields(field, amount1, amount2);
                        } catch (CategoryNull | InvalidNumber | MaxMinReplacement | SellerShouldJustBe categoryNull) {
                            categoryNull.printStackTrace();
                        }
                        parentPage.execute();
                    }
                });
                subPages.put("3", new Page("current filters", this) {
                    @Override
                    public void execute() {
                        System.out.println("filter fields: ");
                        if (OffBoss.getFields() != null) {
                            for (ProductFilter field : OffBoss.getFields()) {
                                System.out.println(field.getFilterName());
                            }
                        }
                        parentPage.execute();
                    }
                });
                subPages.put("4", new Page("disable filter", this) {
                    @Override
                    public void execute() {
                        Page nextPage = null;
                        System.out.println("enter a field:");
                        String field = scanner.nextLine();
                        if (!OffBoss.disableFilter(field)) {
                            System.err.println("invalid");
                            nextPage = this;
                        } else {
                            nextPage = parentPage;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("5", new Page("show products", this) {
                    @Override
                    public void execute() {
                        for (Product product : OffBoss.filterFields(Product.getAllProducts())) {
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

    private Page sorting() {
        return new Page("sorting", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                available = new ArrayList<>();
                available.add("price");
                available.add("name");
                available.add("rate");
                available.add("reviewNumber");
                available.add("inventory");

                sortFields = ("reviewNumber");
                subPages.put("3", new Page("show available", this) {
                    @Override
                    public void execute() {

                        for (Category category : Category.allCategories) {
                            for (String attribute : category.specialAttributes) {
                                available.add(attribute);
                            }
                        }
                        System.out.println("available sort fields :");
                        int i = 1;
                        for (String s : available) {
                            System.out.println(i + "]" + "**" + s + "**");
                            i++;
                        }
                        parentPage.execute();
                    }
                });
                subPages.put("4", new Page("sort", this) {
                    @Override
                    public void execute() {
                        System.out.println("(back|sort [sortField])");
                        String command = scanner.nextLine();
                        Page nextPage = null;
                        String regex = "^sort (\\S+)$";
                        Matcher matcher = getMatcher(command, regex);
                        matcher.matches();
                        if (command.matches(regex)) {
                            String field = matcher.group(1);
                            if (available.contains(field)) {

                                currentList = ProductBoss.sortProduct(field);
                                for (Product s : currentList) {
                                    System.out.println(s.getName());
                                }
                                nextPage = parentPage;

                            } else {
                                System.err.println("invalid field");
                                nextPage = this;
                            }
                        } else if (command.matches("back")) {
                            nextPage = parentPage;
                        } else {
                            System.err.println("invalid command");
                            nextPage = this;
                        }
                        nextPage.execute();
                    }
                });
                subPages.put("1", new Page("current sort", this) {
                    @Override
                    public void execute() {
                        System.out.println("sort fields : ");
                        System.out.println("---" + sortFields + "---");
                        parentPage.execute();
                    }
                });
                subPages.put("2", new Page("disable sort", this) {
                    @Override
                    public void execute() {
                        sortFields = ("reviewNumber");
                        System.out.println("disable successfully");
                        parentPage.execute();
                    }
                });

            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                return super.show();
            }
        };
    }

    private Page showProduct() {
        return new Page("show product", this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute() {
                super.execute();
            }


        };
    }



    @Override
    public void execute() {
        setSubPages(subPages);
        show();
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("2")){
                nextPage = viewCategories();
        }else if (command.equalsIgnoreCase("4")){
            nextPage = filtering();

        }else if (command.equalsIgnoreCase("1")){
            nextPage = sorting();

        }else if (command.equalsIgnoreCase("3")){
            nextPage = showProduct();

        }else if (command.equalsIgnoreCase("5")){
                nextPage = enterToProductPage();
        }else if (command.equals(String.valueOf(subPages.size()+1))){
            nextPage = parentPage;
        }else if (command.equalsIgnoreCase("help")){

        }
        else {
            System.err.println("invalid command");
            nextPage = this;
        }
        nextPage.execute();
    }
}
