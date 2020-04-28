
package Views;

import Controller.CustomerBoss;
import Model.Account;
import Model.Customer;

import java.util.HashMap;

public class BuyerPage extends Page {
    public BuyerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("view personal info", this);
        subPages.put("view cart", this);
        subPages.put("view orders", this);
        subPages.put("view balance", this);
        subPages.put("view discount codes", this);

    }

    private Page viewCart(){
        return new Page("view cart",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show products" , this);
                subPages.put("view",this);
                subPages.put("increase" , this);
                subPages.put("decrease",this);
                subPages.put("show total price" , this);
                subPages.put("purchase" , this);
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
    private Page viewOrders(){
        return new Page("view orders" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show order" , this);
                subPages.put("rate" , this);
            }

            @Override
            public void execute( ) {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    private Page viewBalance(){
        return new Page("view balance" , this) {
            @Override
            public void execute() {
                System.out.println("balance:");
                System.out.println(CustomerBoss.showMoney((Customer) Account.getOnlineAccount()));
                parentPage.execute();
            }
        };
    }
    private Page viewDiscountCodes(){
      return new Page("view discount codes"  , this) {
          @Override
          public void setSubPages(HashMap<String, Page> subPages) {
              super.setSubPages(subPages);
          }

          @Override
          public void execute( ) {
              super.execute();
          }

          @Override
          public void show() {
              super.show();
          }
      }  ;
    }
    private Page logout(){
        return new Page("logout" , this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                super.setSubPages(subPages);
            }

            @Override
            public void execute( ) {
                super.execute();
            }

            @Override
            public void show() {
                super.show();
            }
        };
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void execute( ) {
        show();
        String command = scanner.nextLine();
        Page nextPage = null;
        if (command.equalsIgnoreCase("view cart"))
            nextPage = viewOrders();
        else if (command.equalsIgnoreCase("view orders"))
            nextPage = viewOrders();
        else if (command.equalsIgnoreCase("view balance"))
            nextPage = viewBalance();
        else if (command.equalsIgnoreCase("view discount codes"))
            nextPage = viewDiscountCodes();
        try {
            nextPage.execute();
        }catch (Exception e) {
            System.out.println("invalid command");
            this.execute();
        }
    }
}
