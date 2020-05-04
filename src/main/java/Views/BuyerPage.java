
package Views;

import Controller.CustomerBoss;
import Model.Account;
import Model.Customer;

import java.util.HashMap;

public class BuyerPage extends Page {
    public BuyerPage(String name, Page parentPage) {
        super(name, parentPage);
        subPages.put("2", viewCart());
        subPages.put("4", viewOrders());
        subPages.put("3", viewBalance());
        subPages.put("1", viewDiscountCodes());

    }

    private Page viewCart(){
        return new Page("view cart",this) {
            @Override
            public void setSubPages(HashMap<String, Page> subPages) {
                subPages.put("show products", new Page("show products",this) {
                    @Override
                    public void execute() {
                        for (String s : CustomerBoss.showProductsInCart((Customer) Account.getOnlineAccount())) {
                            System.out.println(s);
                        }
                    }
                });
                subPages.put("view", new Page("view product",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("increase", new Page("increase inventory",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("decrease", new Page("decrease inventory",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("show total price", new Page("show total price",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
                subPages.put("purchase", new Page("purchase",this) {
                    @Override
                    public void execute() {
                        super.execute();
                    }
                });
            }

            @Override
            public void execute() {
                super.execute();
            }

            @Override
            public boolean show() {
                super.show();
                return false;
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
            public boolean show() {
                super.show();
                return false;
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
          public void execute() {
              for (String discountCodeInformation : CustomerBoss.showDiscountCodes((Customer) Account.getOnlineAccount())) {
                  System.out.println(discountCodeInformation);
              }
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
            public boolean show() {
                super.show();
                return false;
            }
        };
    }
    @Override
    public boolean show() {
        super.show();
        return false;
    }

    @Override
    public void execute( ) {
        setSubPages(subPages);
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
