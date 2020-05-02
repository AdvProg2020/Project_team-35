package Views;

import Controller.ProductBoss;
import Controller.SellerBoss;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductsPage extends Page {
    public ProductsPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view categories",viewCategories());
        subPages.put("filtering",filtering());
        subPages.put("sorting",sorting());
        subPages.put("show products",showProduct());
        subPages.put("product page",enterToProductPage());

    }
    private Page viewCategories(){
        return new Page("view categories" , this) {
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
    private Page enterToProductPage(){
        return new Page("product" , this) {
            @Override
            public void execute() {
                System.out.println("enter your command:");
                String command = scanner.nextLine();
               Page nextPage = null;
               String regex = "^show product (\\d+)$";
                Matcher matcher = getMatcher(command,regex);
                if (command.matches(regex)){
                    int id =Integer.parseInt( matcher.group(1) );
                GoodPage goodPage = ProductBoss.goToGoodPage(id);
                nextPage = goodPage;
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
    private Page filtering(){
        return new Page("filtering" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show available filters" , this);
                subPages.put("filter" , this);
                subPages.put("current filters" , this);
                subPages.put("disable filter" , this);

            }

            @Override
            public void execute() {
                show();
                String command = scanner.nextLine();
                Page nextPage = null;
                if (command.equalsIgnoreCase("show available filters")){

                }else if (command.equalsIgnoreCase("filter")){

                }else if (command.equalsIgnoreCase("current filters")){

                }else if (command.equalsIgnoreCase("disable filter")){

                }else if (command.equalsIgnoreCase("back")){
                    nextPage = parentPage;
                }else if (command.equalsIgnoreCase("help")){

                }else {

                }
            }
            @Override
            public boolean show() {
              return   super.show();
            }
        };
    }
    private Page sorting(){
        return new Page("sorting" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show available sorts" , this);
                subPages.put("sort" , this);
                subPages.put("current sort" , this);
                subPages.put("disable sort" , this);

            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
              return   super.show();
            }
        };
    }
    private Page showProduct(){
        return new Page("show product" , this) {
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
             return    super.show();
            }
        };
    }

    @Override
    public boolean show() {
       return super.show();
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
